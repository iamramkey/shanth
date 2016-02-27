/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.JobTypeDbaImpl;
import se.signa.signature.dbo.JobTypeImpl;
import se.signa.signature.gen.dbo.JobType;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class JobTypeDba extends SignatureDba<JobType>
{
	private static JobTypeDbaImpl INSTANCE;

	public static JobTypeDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new JobTypeDbaImpl();
		return INSTANCE;
	}

	public JobTypeDba()
	{
		tableName = "job_type";
		tablePrefix = "jbt";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Jbt Id");
		columns.add("Jbt Name");
		columns.add("Jbt Type");
		columns.add("Jbt Code");
		columns.add("Run Id");
		columns.add("Jbt Priority");
		columns.add("Jbt User Name");
		columns.add("Jbt Dttm");
		return columns;
	}

	@Override
	public JobType createEmptyDbo()
	{
		return new JobTypeImpl();
	}

	@Override
	public void checkDuplicates(JobType dbo)
	{
		checkDuplicateGM(dbo.getJbtName(), "jbt_name", dbo.getPk());
		checkDuplicateGM(dbo.getJbtBk(), "jbt_bk", dbo.getPk());
	}

	@Override
	public int create(JobType jobType, int usrId)
	{
		return create(null, jobType, usrId);
	}

	public int create(Connection connection, JobType jobType, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("jbt_id", "job_type");
		String bk = jobType.getDisplayField();

		String query = " insert into job_type values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, jobType.getJbtName(), jobType.getJbtType(), jobType.getJbtCode(), jobType.getRunId(), jobType.getJbtPriority(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(JobType jobType, int usrId)
	{
		update(null, jobType, usrId);
	}

	public void update(Connection connection, JobType jobType, int usrId)
	{
		String query = " update job_type set  jbt_name = ? , jbt_type = ? , jbt_code = ? , run_id = ? , jbt_priority = ? , jbt_modified_usr_id = ? , jbt_modified_dttm = ?  where jbt_id = ?  ";
		Object[] paramValues = new Object[] { jobType.getJbtName(), jobType.getJbtType(), jobType.getJbtCode(), jobType.getRunId(), jobType.getJbtPriority(), usrId, new DateTime(), jobType.getJbtId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into job_type values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { jobType.getJbtId(), jobType.getJbtName(), jobType.getJbtType(), jobType.getJbtCode(), jobType.getRunId(), jobType.getJbtPriority(), jobType.getJbtBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<JobType> fetchAll()
	{
		String query = " select * from job_type ";
		return RefDBHelper.getDB().fetchList(query, JobTypeImpl.class);
	}

	public List<JobType> fetchAuditRowsByPk(int id)
	{
		String query = " select * from job_type where jbt_id=? order by jbt_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, JobTypeImpl.class);
	}

	public JobType fetchByPk(int jbtId)
	{
		return fetchByPk(null, jbtId);
	}

	public JobType fetchByPk(Connection connection, int jbtId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from job_type where jbt_id=? ", jbtId, JobTypeImpl.class);
	}

	public JobType fetchByBk(String jbtBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from job_type where jbt_bk=? ", jbtBk, JobTypeImpl.class);
	}

	public JobType fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from job_type where jbt_name=? ", displayField, JobTypeImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("jbt_name", "job_type");
	}

}