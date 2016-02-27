package se.signa.signature.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import se.signa.signature.dio.JobSearchResult;
import se.signa.signature.dio.StigWebInput;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.gen.dba.JobDba;
import se.signa.signature.gen.dbo.Job;

@Path("/pendingjobsearch")
public class PendingJobSearchApi
{
	private static Logger logger = Logger.getLogger(PendingJobSearchApi.class);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult pendingJobSearch(StigWebInput input)
	{
		input.validate();
		input.checkRules();

		StringBuffer whereClause = new StringBuffer(JobDba.getI().getDefaultWhereClause());
		List<Object> whereParams = new ArrayList<Object>();
		JobDba.getI().addJobStatusFilterForPending(whereClause, whereParams);

		List<Job> jobList = JobDba.getI().fetchAllJobs(whereClause.toString(), whereParams.toArray());
		String notification = "Pending Job Search api excecuted successfully, Found : " + jobList.size() + " Rows";
		logger.info(notification);
		return new JobSearchResult(notification, jobList);
	}
}
