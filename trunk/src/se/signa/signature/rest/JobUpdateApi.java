/**
 * Copyright SIGNA AB, STOCKHOLM, SWEDEN
 */
package se.signa.signature.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import se.signa.signature.common.Constants;
import se.signa.signature.common.NegativeResultException;
import se.signa.signature.dio.JobUpdateInput;
import se.signa.signature.dio.NegativeResult;
import se.signa.signature.dio.PositiveResult;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.gen.dba.JobDba;
import se.signa.signature.gen.dba.PendingJobDba;
import se.signa.signature.gen.dbo.Job;
import se.signa.signature.gen.dbo.PendingJob;

@Path("/jobupdate")
public class JobUpdateApi extends StigWebApi
{

	private static Logger logger = Logger.getLogger(JobUpdateApi.class);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult jobUpdate(JobUpdateInput input)
	{
		input.validate();
		input.checkRules();
		Job job = JobDba.getI().fetchByPk(input.jobId);
		PendingJob pendingJob = PendingJobDba.getI().fetchByJobId(job.getJobId());
		if (pendingJob==null || !job.getJobStatus().equalsIgnoreCase(Constants.JOBSTATUS_PENDING))
		{
			NegativeResult nr = new NegativeResult(Constants.ErrCode.INPUT_FIELD_INVALID, "For the given Job Id : " + input.jobId + " status is not pending ", "jobId");
			throw new NegativeResultException(nr);
		}
		pendingJob.setPjbPriority(input.jobPriority);
		job.setJobPriority(input.jobPriority);
		PendingJobDba.getI().update(pendingJob, input.usrId);
		JobDba.getI().update(job, input.usrId);

		String notification = "Job Priority is updated for the Job Name: " + job.getJobName();
		logger.info(notification);
		return new PositiveResult(notification);
	}
}
