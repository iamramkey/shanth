/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.PendingMissingJobDbaImpl;
import se.signa.signature.dbo.PendingMissingJobImpl;
import se.signa.signature.gen.dbo.PendingMissingJob;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class PendingMissingJobDba extends SignatureDba<PendingMissingJob>
{
	private static PendingMissingJobDbaImpl INSTANCE;

	public static PendingMissingJobDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new PendingMissingJobDbaImpl();
		return INSTANCE;
	}

	public PendingMissingJobDba()
	{
		tableName = "pending_missing_job";
		tablePrefix = "pmi";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Pmi Id");
		columns.add("Pmi Name");
		columns.add("Pmi Pending Missing Jbt Id");
		columns.add("Pmi Pending Lookback From Min");
		columns.add("Pmi Pending Lookback To Min");
		columns.add("Pmi Missing Lookback From Min");
		columns.add("Pmi Missing Lookback To Min");
		columns.add("Pmi User Name");
		columns.add("Pmi Dttm");
		return columns;
	}

	@Override
	public PendingMissingJob createEmptyDbo()
	{
		return new PendingMissingJobImpl();
	}

	@Override
	public void checkDuplicates(PendingMissingJob dbo)
	{
		checkDuplicateGM(dbo.getPmiName(), "pmi_name", dbo.getPk());
		checkDuplicateGM(dbo.getPmiBk(), "pmi_bk", dbo.getPk());
	}

	@Override
	public int create(PendingMissingJob pendingMissingJob, int usrId)
	{
		return create(null, pendingMissingJob, usrId);
	}

	public int create(Connection connection, PendingMissingJob pendingMissingJob, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("pmi_id", "pending_missing_job");
		String bk = pendingMissingJob.getDisplayField();

		String query = " insert into pending_missing_job values(?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, pendingMissingJob.getPmiName(), pendingMissingJob.getPmiPendingMissingJbtId(), pendingMissingJob.getPmiPendingLookbackFromMin(), pendingMissingJob.getPmiPendingLookbackToMin(), pendingMissingJob.getPmiMissingLookbackFromMin(), pendingMissingJob.getPmiMissingLookbackToMin(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(PendingMissingJob pendingMissingJob, int usrId)
	{
		update(null, pendingMissingJob, usrId);
	}

	public void update(Connection connection, PendingMissingJob pendingMissingJob, int usrId)
	{
		String query = " update pending_missing_job set  pmi_name = ? , pmi_pending_missing_jbt_id = ? , pmi_pending_lookback_from_min = ? , pmi_pending_lookback_to_min = ? , pmi_missing_lookback_from_min = ? , pmi_missing_lookback_to_min = ? , pmi_modified_usr_id = ? , pmi_modified_dttm = ?  where pmi_id = ?  ";
		Object[] paramValues = new Object[] { pendingMissingJob.getPmiName(), pendingMissingJob.getPmiPendingMissingJbtId(), pendingMissingJob.getPmiPendingLookbackFromMin(), pendingMissingJob.getPmiPendingLookbackToMin(), pendingMissingJob.getPmiMissingLookbackFromMin(), pendingMissingJob.getPmiMissingLookbackToMin(), usrId, new DateTime(), pendingMissingJob.getPmiId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into pending_missing_job values(?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { pendingMissingJob.getPmiId(), pendingMissingJob.getPmiName(), pendingMissingJob.getPmiPendingMissingJbtId(), pendingMissingJob.getPmiPendingLookbackFromMin(), pendingMissingJob.getPmiPendingLookbackToMin(), pendingMissingJob.getPmiMissingLookbackFromMin(), pendingMissingJob.getPmiMissingLookbackToMin(), pendingMissingJob.getPmiBk(), usrId, usrId,
				new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<PendingMissingJob> fetchAll()
	{
		String query = " select * from pending_missing_job ";
		return RefDBHelper.getDB().fetchList(query, PendingMissingJobImpl.class);
	}

	public List<PendingMissingJob> fetchAuditRowsByPk(int id)
	{
		String query = " select * from pending_missing_job where pmi_id=? order by pmi_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, PendingMissingJobImpl.class);
	}

	public PendingMissingJob fetchByPk(int pmiId)
	{
		return fetchByPk(null, pmiId);
	}

	public PendingMissingJob fetchByPk(Connection connection, int pmiId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from pending_missing_job where pmi_id=? ", pmiId, PendingMissingJobImpl.class);
	}

	public PendingMissingJob fetchByBk(String pmiBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_missing_job where pmi_bk=? ", pmiBk, PendingMissingJobImpl.class);
	}

	public PendingMissingJob fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_missing_job where pmi_name=? ", displayField, PendingMissingJobImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("pmi_name", "pending_missing_job");
	}

}