/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.JobCapabilityDbaImpl;
import se.signa.signature.dbo.JobCapabilityImpl;
import se.signa.signature.gen.dbo.JobCapability;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class JobCapabilityDba extends SignatureDba<JobCapability>
{
	private static JobCapabilityDbaImpl INSTANCE;

	public static JobCapabilityDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new JobCapabilityDbaImpl();
		return INSTANCE;
	}

	public JobCapabilityDba()
	{
		tableName = "job_capability";
		tablePrefix = "jbc";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Jbc Id");
		columns.add("Jbt Id");
		columns.add("Nod Id");
		columns.add("Jbc Name");
		columns.add("Jbc Count");
		columns.add("Jbc Status");
		columns.add("Jbc User Name");
		columns.add("Jbc Dttm");
		return columns;
	}

	@Override
	public JobCapability createEmptyDbo()
	{
		return new JobCapabilityImpl();
	}

	@Override
	public void checkDuplicates(JobCapability dbo)
	{
		checkDuplicateGM(dbo.getJbcName(), "jbc_name", dbo.getPk());
		checkDuplicateGM(dbo.getJbcBk(), "jbc_bk", dbo.getPk());
	}

	@Override
	public int create(JobCapability jobCapability, int usrId)
	{
		return create(null, jobCapability, usrId);
	}

	public int create(Connection connection, JobCapability jobCapability, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("jbc_id", "job_capability");
		String bk = jobCapability.getDisplayField();

		String query = " insert into job_capability values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, jobCapability.getJbtId(), jobCapability.getNodId(), jobCapability.getJbcName(), jobCapability.getJbcCount(), jobCapability.getJbcStatus(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(JobCapability jobCapability, int usrId)
	{
		update(null, jobCapability, usrId);
	}

	public void update(Connection connection, JobCapability jobCapability, int usrId)
	{
		String query = " update job_capability set  jbt_id = ? , nod_id = ? , jbc_name = ? , jbc_count = ? , jbc_status = ? , jbc_modified_usr_id = ? , jbc_modified_dttm = ?  where jbc_id = ?  ";
		Object[] paramValues = new Object[] { jobCapability.getJbtId(), jobCapability.getNodId(), jobCapability.getJbcName(), jobCapability.getJbcCount(), jobCapability.getJbcStatus(), usrId, new DateTime(), jobCapability.getJbcId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into job_capability values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { jobCapability.getJbcId(), jobCapability.getJbtId(), jobCapability.getNodId(), jobCapability.getJbcName(), jobCapability.getJbcCount(), jobCapability.getJbcStatus(), jobCapability.getJbcBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<JobCapability> fetchAll()
	{
		String query = " select * from job_capability ";
		return RefDBHelper.getDB().fetchList(query, JobCapabilityImpl.class);
	}

	public List<JobCapability> fetchAuditRowsByPk(int id)
	{
		String query = " select * from job_capability where jbc_id=? order by jbc_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, JobCapabilityImpl.class);
	}

	public JobCapability fetchByPk(int jbcId)
	{
		return fetchByPk(null, jbcId);
	}

	public JobCapability fetchByPk(Connection connection, int jbcId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from job_capability where jbc_id=? ", jbcId, JobCapabilityImpl.class);
	}

	public JobCapability fetchByBk(String jbcBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from job_capability where jbc_bk=? ", jbcBk, JobCapabilityImpl.class);
	}

	public JobCapability fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from job_capability where jbc_name=? ", displayField, JobCapabilityImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("jbc_name", "job_capability");
	}

}