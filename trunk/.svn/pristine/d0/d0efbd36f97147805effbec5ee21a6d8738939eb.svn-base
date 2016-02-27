package se.signa.signature.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import se.signa.signature.common.Constants;
import se.signa.signature.common.NegativeResultException;
import se.signa.signature.dio.JobSearchInput;
import se.signa.signature.dio.JobSearchResult;
import se.signa.signature.dio.NegativeResult;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.gen.dba.JobDba;
import se.signa.signature.gen.dbo.Job;
import se.signa.signature.helpers.DataValidator;

@Path("/jobsearch")
public class JobSearchApi
{
	private static Logger logger = Logger.getLogger(JobSearchApi.class);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult jobSearch(JobSearchInput input)
	{
		long start = System.currentTimeMillis();
		logger.debug("Server started Job search api..");

		input.validate();
		input.checkRules();

		StringBuffer whereClause = new StringBuffer();
		whereClause.append(JobDba.getI().getDefaultWhereClause());
		List<Object> whereParams = new ArrayList<Object>();
		JobDba.getI().addJbtCodeFilter(whereClause, whereParams, Constants.JBT_CODE_CHAIN);
		JobDba.getI().addJobStatusFilterForNotPending(whereClause, whereParams);

		if (DataValidator.validateMandatoryString(input.jobId))
			JobDba.getI().addJobIdFilter(input.jobId, whereClause);

		if (DataValidator.validateMandatoryString(input.nodeName))
			JobDba.getI().addNodeNameFilter(input.nodeName, whereClause, whereParams);

		if (DataValidator.validateMandatoryString(input.jbtName))
			JobDba.getI().addJbtFilter(input.jbtName, whereClause, whereParams);

		if (DataValidator.validateMandatoryString(input.jobStatus))
			JobDba.getI().addStatusFilter(input.jobStatus, whereClause, whereParams);

		if (DataValidator.validateMandatoryString(input.jobExtra1))
			JobDba.getI().addJobExtra1Filter(input.jobExtra1, whereClause, whereParams);

		if (DataValidator.validateMandatoryString(input.jobExtra2))
			JobDba.getI().addJobExtra2Filter(input.jobExtra2, whereClause, whereParams);

		if (DataValidator.validateMandatoryString(input.jobExtra3))
			JobDba.getI().addJobExtra3Filter(input.jobExtra3, whereClause, whereParams);

		int rowCount = Integer.parseInt(input.rowCount);
		JobDba.getI().addRowCountFilter(rowCount + 1, whereClause);

		DateTime fromDttm = addFromDttmFilter(input, whereClause, whereParams);
		DateTime toDttm = addtoDttmFilter(input, whereClause, whereParams);

		if (fromDttm != null && toDttm != null & fromDttm.isAfter(toDttm))
			return new NegativeResult(Constants.ErrCode.INPUT_FIELD_INVALID, "To date must be > than from date", "jobStartDttmTo");

		String whereClauseString = whereClause.toString();
		Object[] whereParamsArray = whereParams.toArray();
		List<Job> jobList = JobDba.getI().fetchAllJobs(whereClauseString, whereParamsArray);
		int totalCount = JobDba.getI().fetchCount(whereClauseString, whereParamsArray);

		long end = System.currentTimeMillis();
		logger.debug(" Job Search returned  " + jobList.size() + " Rows in " + (end - start) + " ms ");
		String notification = "Job search fetched : " + jobList.size() + " rows of " + totalCount + " in database";
		logger.info(notification);
		return new JobSearchResult(notification, jobList);
	}

	private DateTime addtoDttmFilter(JobSearchInput input, StringBuffer whereClause, List<Object> whereParams)
	{
		DateTime startToDttm = null;
		DateTime endToDttm = null;
		DateTime createdToDttm = null;
		try
		{
			startToDttm = DateTime.parse(input.jobStartDttmTo, Constants.dtf);
			startToDttm = startToDttm.plusDays(1).minusSeconds(1);
			JobDba.getI().addJobStartDttmTo(startToDttm, whereClause, whereParams);

			if (DataValidator.validateMandatoryString(input.jobEndDttmTo))
			{
				endToDttm = DateTime.parse(input.jobEndDttmTo, Constants.dtf);
				endToDttm = endToDttm.plusDays(1).minusSeconds(1);
				JobDba.getI().addJobEndDttmTo(endToDttm, whereClause, whereParams);
			}

			if (DataValidator.validateMandatoryString(input.jobCreatedDttmTo))
			{
				createdToDttm = DateTime.parse(input.jobCreatedDttmTo, Constants.dtf);
				createdToDttm = createdToDttm.plusDays(1).minusSeconds(1);
				JobDba.getI().addJobCreatedDttmTo(createdToDttm, whereClause, whereParams);
			}

		}
		catch (Exception e)
		{
			String message = "Entered To DateTime format is invalid,Use the following Format" + Constants.PARSE_INPUT_DTTM_FORMAT;
			NegativeResult nr = new NegativeResult(Constants.ErrCode.INPUT_FIELD_INVALID, message, "jobStartDttmTo");
			throw new NegativeResultException(nr);
		}
		return startToDttm;
	}

	private DateTime addFromDttmFilter(JobSearchInput input, StringBuffer whereClause, List<Object> whereParams)
	{
		DateTime startFromDttm = null;
		DateTime endFromDttm = null;
		DateTime createdFromDttm = null;
		try
		{
			startFromDttm = DateTime.parse(input.jobStartDttmFrom, Constants.dtf);
			JobDba.getI().addJobStartDttmFrom(startFromDttm, whereClause, whereParams);

			if (DataValidator.validateMandatoryString(input.jobEndDttmFrom))
			{
				endFromDttm = DateTime.parse(input.jobEndDttmFrom, Constants.dtf);
				JobDba.getI().addJobEndDttmFrom(endFromDttm, whereClause, whereParams);
			}

			if (DataValidator.validateMandatoryString(input.jobCreatedDttmFrom))
			{
				createdFromDttm = DateTime.parse(input.jobCreatedDttmFrom, Constants.dtf);
				JobDba.getI().addJobCreatedDttmFrom(createdFromDttm, whereClause, whereParams);
			}

		}
		catch (Exception e)
		{
			String message = "Entered From DateTime format is invalid,Use the following Format " + Constants.PARSE_INPUT_DTTM_FORMAT;
			NegativeResult nr = new NegativeResult(Constants.ErrCode.INPUT_FIELD_INVALID, message, "jobStartDttmFrom");
			throw new NegativeResultException(nr);
		}
		return startFromDttm;
	}
}
