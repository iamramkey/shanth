/**
 * Copyright SIGNA AB, STOCKHOLM, SWEDEN
 */
package se.signa.signature.rest;

import java.io.File;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import se.signa.signature.common.Constants;
import se.signa.signature.dio.JobLogInput;
import se.signa.signature.dio.JobLogResult;
import se.signa.signature.dio.NegativeResult;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.gen.dba.JobDba;
import se.signa.signature.gen.dba.JobTypeDba;
import se.signa.signature.gen.dba.ServerSettingsDba;
import se.signa.signature.gen.dbo.Job;
import se.signa.signature.gen.dbo.JobType;
import se.signa.signature.gen.dbo.ServerSettings;
import se.signa.signature.helpers.FileHelper;

@Path("/joblog")
public class JobLogApi extends StigWebApi
{
	private static Logger logger = Logger.getLogger(JobLogApi.class);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult jobLog(JobLogInput input)
	{
		input.validate();
		input.checkRules();
		File file = null;
		ServerSettings serverSettings = ServerSettingsDba.getI().fetchByDisplayField(Constants.dataDir);
		Job job = JobDba.getI().fetchByPk(input.jobId);
		JobType jobType = JobTypeDba.getI().fetchByPk(job.getJbtId());
		if (jobType.getJbtCode().equalsIgnoreCase(Constants.JBT_CODE_STARTUP))
			file = new File(serverSettings.getSesValue() + Constants.STARTUPJOBSLOGDIR + input.jobId + ".log");
		else
			file = new File(serverSettings.getSesValue() + Constants.JOBSLOGDIR + input.jobId + ".log");
		String log;
		try
		{
			log = new FileHelper().getFileContentsForUi(file);
		}
		catch (Exception e)
		{
			return new NegativeResult(Constants.ErrCode.UNEXPECTED_ERROR, "Could not read log file at " + file.getAbsolutePath(), "jobId");
		}
		String notification = "Job log read successfully";
		logger.info(notification);
		String header = "Job Id : " + job.getJobId() + " JobType : " + jobType.getJbtName();
		return new JobLogResult(notification, log, job.getJobStatus(), header);
	}
}
