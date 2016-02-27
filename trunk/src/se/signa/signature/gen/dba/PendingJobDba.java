/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.PendingJobDbaImpl;
import se.signa.signature.dbo.PendingJobImpl;
import se.signa.signature.gen.dbo.PendingJob;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class PendingJobDba extends SignatureDba<PendingJob>
{
	private static PendingJobDbaImpl INSTANCE;

	public static PendingJobDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new PendingJobDbaImpl();
		return INSTANCE;
	}

	public PendingJobDba()
	{
		tableName = "pending_job";
		tablePrefix = "pjb";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Pjb Id");
		columns.add("Job Id");
		columns.add("Jbt Id");
		columns.add("Pjb Name");
		columns.add("Pjb Extra1");
		columns.add("Pjb Extra2");
		columns.add("Pjb Extra3");
		columns.add("Pjb Priority");
		columns.add("Pjb User Name");
		columns.add("Pjb Dttm");
		return columns;
	}

	@Override
	public PendingJob createEmptyDbo()
	{
		return new PendingJobImpl();
	}

	@Override
	public void checkDuplicates(PendingJob dbo)
	{
		checkDuplicateGM(dbo.getPjbName(), "pjb_name", dbo.getPk());
		checkDuplicateGM(dbo.getPjbBk(), "pjb_bk", dbo.getPk());
	}

	@Override
	public int create(PendingJob pendingJob, int usrId)
	{
		return create(null, pendingJob, usrId);
	}

	public int create(Connection connection, PendingJob pendingJob, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("pjb_id", "pending_job");
		String bk = pendingJob.getDisplayField();

		String query = " insert into pending_job values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, pendingJob.getJobId(), pendingJob.getJbtId(), pendingJob.getPjbName(), pendingJob.getPjbExtra1(), pendingJob.getPjbExtra2(), pendingJob.getPjbExtra3(), pendingJob.getPjbPriority(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(PendingJob pendingJob, int usrId)
	{
		update(null, pendingJob, usrId);
	}

	public void update(Connection connection, PendingJob pendingJob, int usrId)
	{
		String query = " update pending_job set  job_id = ? , jbt_id = ? , pjb_name = ? , pjb_extra1 = ? , pjb_extra2 = ? , pjb_extra3 = ? , pjb_priority = ? , pjb_modified_usr_id = ? , pjb_modified_dttm = ?  where pjb_id = ?  ";
		Object[] paramValues = new Object[] { pendingJob.getJobId(), pendingJob.getJbtId(), pendingJob.getPjbName(), pendingJob.getPjbExtra1(), pendingJob.getPjbExtra2(), pendingJob.getPjbExtra3(), pendingJob.getPjbPriority(), usrId, new DateTime(), pendingJob.getPjbId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into pending_job values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { pendingJob.getPjbId(), pendingJob.getJobId(), pendingJob.getJbtId(), pendingJob.getPjbName(), pendingJob.getPjbExtra1(), pendingJob.getPjbExtra2(), pendingJob.getPjbExtra3(), pendingJob.getPjbPriority(), pendingJob.getPjbBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<PendingJob> fetchAll()
	{
		String query = " select * from pending_job ";
		return RefDBHelper.getDB().fetchList(query, PendingJobImpl.class);
	}

	public List<PendingJob> fetchAuditRowsByPk(int id)
	{
		String query = " select * from pending_job where pjb_id=? order by pjb_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, PendingJobImpl.class);
	}

	public PendingJob fetchByPk(int pjbId)
	{
		return fetchByPk(null, pjbId);
	}

	public PendingJob fetchByPk(Connection connection, int pjbId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from pending_job where pjb_id=? ", pjbId, PendingJobImpl.class);
	}

	public PendingJob fetchByBk(String pjbBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_job where pjb_bk=? ", pjbBk, PendingJobImpl.class);
	}

	public PendingJob fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_job where pjb_name=? ", displayField, PendingJobImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("pjb_name", "pending_job");
	}

}