/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dba;

import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.Constants;
import se.signa.signature.dbo.JobImpl;
import se.signa.signature.gen.dba.JobDba;
import se.signa.signature.gen.dbo.Job;
import se.signa.signature.helpers.RefDBHelper;

public class JobDbaImpl extends JobDba
{
	public List<Job> fetchAllJobs(String whereClause, Object[] whereParameters)
	{
		String query = " select * from JOB " + whereClause + " order by JOB_START_DTTM DESC ";
		return RefDBHelper.getDB().fetchList(query, whereParameters, JobImpl.class);
	}

	public int fetchCount(String whereClause, Object[] whereParameters)
	{
		String query = " from JOB " + whereClause;
		return RefDBHelper.getDB().fetchCount(query, whereParameters);
	}

	public void addJobIdFilter(String JobId, StringBuffer whereClause)
	{
		whereClause.append(" and JOB_ID " + JobId);
	}

	public void addJobStartDttmFrom(DateTime jobStartDttmFrom, StringBuffer whereClause, List<Object> whereParams)
	{
		whereClause.append(" and JOB_START_DTTM >= ? ");
		whereParams.add(jobStartDttmFrom);
	}

	public void addJobStartDttmTo(DateTime jobStartDttmTo, StringBuffer whereClause, List<Object> whereParams)
	{
		whereClause.append(" and JOB_START_DTTM <= ?");
		whereParams.add(jobStartDttmTo);
	}

	public void addRowCountFilter(int rowCount, StringBuffer whereClause)
	{
		whereClause.append(" and rownum < " + rowCount);
	}

	public void addJobStatusFilterForPending(StringBuffer whereClause, List<Object> whereParams)
	{
		whereClause.append(" and job_status = ?");
		whereParams.add(Constants.JOBSTATUS_PENDING);
	}

	public void addJobStatusFilterForNotPending(StringBuffer whereClause, List<Object> whereParams)
	{
		whereClause.append(" and job_status <> ?");
		whereParams.add(Constants.JOBSTATUS_PENDING);
	}

	public void addJobStatusFilterNotPending(StringBuffer whereClause, List<Object> whereParams)
	{
		whereClause.append(" and job_status = ?");
		whereParams.add(Constants.JOBSTATUS_PENDING);
	}

	public void addJbtCodeFilter(StringBuffer whereClause, List<Object> whereParams, String jbtCode)
	{
		whereClause.append(" and JBT_ID in ( select JBT_ID from JOB_TYPE where JBT_CODE = ? )");
		whereParams.add(jbtCode);
	}

	public String getDefaultWhereClause()
	{
		return " where job_id is not null ";
	}

	public List<JobMonitorQueryModel> fetchMonitorJobsByDttm(String whereClause, Object[] whereParameters)
	{
		String query = " select count(1) as cnt, jbt_name , job_status from JOB job, JOB_TYPE jbt " + whereClause;
		query += " group by job.job_status , jbt.jbt_name ";
		return RefDBHelper.getDB().fetchList(query, whereParameters, JobMonitorQueryModel.class);
	}

	public String getMonitorDefaultWhereClause()
	{
		return " where job.jbt_id = jbt.jbt_id";
	}

	public void addNodIdFilter(StringBuffer whereClause, List<Object> whereParams, int nodId)
	{
		whereClause.append(" and job.nod_id = ? ");
		whereParams.add(nodId);
	}

	public void addNodeNameFilter(String nodeName, StringBuffer whereClause, List<Object> whereParams)
	{
		whereClause.append(" and NOD_ID in ( select NOD_ID from NODE where NOD_NAME like ? )");
		whereParams.add("%" + nodeName + "%");
	}

	public void addJbtFilter(String jbt, StringBuffer whereClause, List<Object> whereParams)
	{
		whereClause.append(" and JBT_ID in ( select JBT_ID from JOB_TYPE where JBT_NAME like ? )");
		whereParams.add("%" + jbt + "%");
	}

	public void addStatusFilter(String status, StringBuffer whereClause, List<Object> whereParams)
	{
		whereClause.append(" and JOB_STATUS like ?");
		whereParams.add("%" + status + "%");
	}

	public void addJobExtra1Filter(String jobExtra1, StringBuffer whereClause, List<Object> whereParams)
	{
		whereClause.append(" and JOB_EXTRA1 like ?");
		whereParams.add("%" + jobExtra1 + "%");
	}

	public void addJobExtra2Filter(String jobExtra2, StringBuffer whereClause, List<Object> whereParams)
	{
		whereClause.append(" and JOB_EXTRA2 like ?");
		whereParams.add("%" + jobExtra2 + "%");
	}

	public void addJobExtra3Filter(String jobExtra3, StringBuffer whereClause, List<Object> whereParams)
	{
		whereClause.append(" and JOB_EXTRA3 like ?");
		whereParams.add("%" + jobExtra3 + "%");
	}

	public void addJobEndDttmFrom(DateTime endFromDttm, StringBuffer whereClause, List<Object> whereParams)
	{
		whereClause.append(" and JOB_END_DTTM >= ? ");
		whereParams.add(endFromDttm);
	}

	public void addJobEndDttmTo(DateTime endToDttm, StringBuffer whereClause, List<Object> whereParams)
	{
		whereClause.append(" and JOB_END_DTTM <= ? ");
		whereParams.add(endToDttm);
	}

	public void addJobCreatedDttmFrom(DateTime createdFromDttm, StringBuffer whereClause, List<Object> whereParams)
	{
		whereClause.append(" and JOB_CREATED_DTTM >= ?");
		whereParams.add(createdFromDttm);
	}

	public void addJobCreatedDttmTo(DateTime createdToDttm, StringBuffer whereClause, List<Object> whereParams)
	{
		whereClause.append(" and JOB_CREATED_DTTM <= ?");
		whereParams.add(createdToDttm);
	}

}