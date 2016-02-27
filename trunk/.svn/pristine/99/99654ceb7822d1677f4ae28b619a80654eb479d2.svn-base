package se.signa.signature.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import se.signa.signature.common.Constants;
import se.signa.signature.common.NegativeResultException;
import se.signa.signature.dba.JobDbaImpl;
import se.signa.signature.dba.JobMonitorQueryModel;
import se.signa.signature.dio.JobMonitorInput;
import se.signa.signature.dio.JobMonitorResult;
import se.signa.signature.dio.NegativeResult;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.gen.dba.JobDba;
import se.signa.signature.gen.dba.NodeDba;
import se.signa.signature.helpers.DisplayFieldHelper;

@Path("/jobmonitor")
public class JobMonitorApi
{
	private static Logger logger = Logger.getLogger(JobMonitorApi.class);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult jobMonitor(JobMonitorInput input)
	{
		long start = System.currentTimeMillis();
		logger.debug("Job Monitor api starting to fetch records");
		input.validate();
		input.checkRules();

		StringBuffer whereClause = new StringBuffer(JobDba.getI().getMonitorDefaultWhereClause());
		List<Object> whereParams = new ArrayList<Object>();

		if (!input.nodName.equalsIgnoreCase(Constants.ALL_NODES))
		{
			int nodId = DisplayFieldHelper.getI().getPk(NodeDba.class, input.nodName);
			JobDbaImpl.getI().addNodIdFilter(whereClause, whereParams, nodId);
		}

		DateTime fromDttm = addFromDttm(input, whereClause, whereParams);
		DateTime toDttm = addToDttm(input, whereClause, whereParams);

		if (fromDttm != null && toDttm != null & fromDttm.isAfter(toDttm))
			return new NegativeResult(Constants.ErrCode.INPUT_FIELD_INVALID, "To date must be > than from date", "jobStartDttmTo");

		List<JobMonitorQueryModel> jobList = JobDba.getI().fetchMonitorJobsByDttm(whereClause.toString(), whereParams.toArray());
		Map<String, JobMonitorModel> jmmData = new HashMap<String, JobMonitorModel>();
		for (JobMonitorQueryModel job : jobList)
		{
			JobMonitorModel jmm = jmmData.get(job.jbtName);
			if (jmm == null)
			{
				jmm = new JobMonitorModel();
				jmm.jbtName = job.jbtName;
				jmmData.put(job.jbtName, jmm);
			}
			if (job.jobStatus.equalsIgnoreCase(Constants.JOBSTATUS_CANCELLED))
				jmm.cancelled = job.count;
			else if (job.jobStatus.equalsIgnoreCase(Constants.JOBSTATUS_PENDING))
				jmm.pending = job.count;
			else if (job.jobStatus.equalsIgnoreCase(Constants.JOBSTATUS_RUNNING))
				jmm.running = job.count;
			else if (job.jobStatus.equalsIgnoreCase(Constants.JOBSTATUS_FAILED))
				jmm.failed = job.count;
			else if (job.jobStatus.equalsIgnoreCase(Constants.JOBSTATUS_COMPLETED))
				jmm.completed = job.count;
		}

		long end = System.currentTimeMillis();
		String notification = "Job monitor api excecuted successfully ,Found : " + jobList.size() + " Rows in " + (end - start) / 1000 + " s ";
		logger.info(notification);
		return new JobMonitorResult(notification, jmmData.values());
	}

	private DateTime addToDttm(JobMonitorInput input, StringBuffer whereClause, List<Object> whereParams)
	{
		DateTime toDttm = null;
		try
		{
			toDttm = DateTime.parse(input.jobStartDttmTo, Constants.dtf);
			toDttm = toDttm.plusDays(1).minusSeconds(1);
			JobDba.getI().addJobStartDttmTo(toDttm, whereClause, whereParams);
		}
		catch (Exception e)
		{
			String message = "Entered DateTime format is invalid,Use the following Format" + Constants.PARSE_INPUT_DTTM_FORMAT;
			NegativeResult nr = new NegativeResult(Constants.ErrCode.INPUT_FIELD_INVALID, message, "jobStartDttmTo");
			throw new NegativeResultException(nr);
		}
		return toDttm;
	}

	private DateTime addFromDttm(JobMonitorInput input, StringBuffer whereClause, List<Object> whereParams)
	{
		DateTime fromDttm = null;
		try
		{
			fromDttm = DateTime.parse(input.jobStartDttmFrom, Constants.dtf);
			JobDba.getI().addJobStartDttmFrom(fromDttm, whereClause, whereParams);
		}
		catch (Exception e)
		{
			String message = "Entered From DateTime format is invalid,Use the following Format " + Constants.PARSE_INPUT_DTTM_FORMAT;
			NegativeResult nr = new NegativeResult(Constants.ErrCode.INPUT_FIELD_INVALID, message, "jobStartDttmFrom");
			throw new NegativeResultException(nr);
		}
		return fromDttm;
	}

	public class JobMonitorModel
	{
		public String jbtName;
		public int cancelled;
		public int pending;
		public int running;
		public int failed;
		public int completed;

		public int getTotal()
		{
			return pending + running + failed + completed;
		}
	}
}
