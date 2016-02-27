/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.JobDbaImpl;
import se.signa.signature.dbo.JobImpl;
import se.signa.signature.gen.dbo.Job;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class JobDba extends SignatureDba<Job>
{
	private static JobDbaImpl INSTANCE;

	public static JobDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new JobDbaImpl();
		return INSTANCE;
	}

	public JobDba()
	{
		tableName = "job";
		tablePrefix = "job";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Job Id");
		columns.add("Jbt Id");
		columns.add("Job Parent Job Id");
		columns.add("Nod Id");
		columns.add("Job Name");
		columns.add("Job Status");
		columns.add("Job Start Dttm");
		columns.add("Job End Dttm");
		columns.add("Job Retry Count");
		columns.add("Job Extra1");
		columns.add("Job Extra2");
		columns.add("Job Extra3");
		columns.add("Job Priority");
		columns.add("Job User Name");
		columns.add("Job Dttm");
		return columns;
	}

	@Override
	public Job createEmptyDbo()
	{
		return new JobImpl();
	}

	@Override
	public void checkDuplicates(Job dbo)
	{
		checkDuplicateGM(dbo.getJobName(), "job_name", dbo.getPk());
		checkDuplicateGM(dbo.getJobBk(), "job_bk", dbo.getPk());
	}

	@Override
	public int create(Job job, int usrId)
	{
		return create(null, job, usrId);
	}

	public int create(Connection connection, Job job, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("job_id", "job");
		String bk = job.getDisplayField();

		String query = " insert into job values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, job.getJbtId(), job.getJobParentJobId(), job.getNodId(), job.getJobName(), job.getJobStatus(), job.getJobStartDttm(), job.getJobEndDttm(), job.getJobRetryCount(), job.getJobExtra1(), job.getJobExtra2(), job.getJobExtra3(), job.getJobPriority(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(Job job, int usrId)
	{
		update(null, job, usrId);
	}

	public void update(Connection connection, Job job, int usrId)
	{
		String query = " update job set  jbt_id = ? , job_parent_job_id = ? , nod_id = ? , job_name = ? , job_status = ? , job_start_dttm = ? , job_end_dttm = ? , job_retry_count = ? , job_extra1 = ? , job_extra2 = ? , job_extra3 = ? , job_priority = ? , job_modified_usr_id = ? , job_modified_dttm = ?  where job_id = ?  ";
		Object[] paramValues = new Object[] { job.getJbtId(), job.getJobParentJobId(), job.getNodId(), job.getJobName(), job.getJobStatus(), job.getJobStartDttm(), job.getJobEndDttm(), job.getJobRetryCount(), job.getJobExtra1(), job.getJobExtra2(), job.getJobExtra3(), job.getJobPriority(), usrId, new DateTime(), job.getJobId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into job values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { job.getJobId(), job.getJbtId(), job.getJobParentJobId(), job.getNodId(), job.getJobName(), job.getJobStatus(), job.getJobStartDttm(), job.getJobEndDttm(), job.getJobRetryCount(), job.getJobExtra1(), job.getJobExtra2(), job.getJobExtra3(), job.getJobPriority(), job.getJobBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<Job> fetchAll()
	{
		String query = " select * from job ";
		return RefDBHelper.getDB().fetchList(query, JobImpl.class);
	}

	public List<Job> fetchAuditRowsByPk(int id)
	{
		String query = " select * from job where job_id=? order by job_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, JobImpl.class);
	}

	public Job fetchByPk(int jobId)
	{
		return fetchByPk(null, jobId);
	}

	public Job fetchByPk(Connection connection, int jobId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from job where job_id=? ", jobId, JobImpl.class);
	}

	public Job fetchByBk(String jobBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from job where job_bk=? ", jobBk, JobImpl.class);
	}

	public Job fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from job where job_name=? ", displayField, JobImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("job_name", "job");
	}

}