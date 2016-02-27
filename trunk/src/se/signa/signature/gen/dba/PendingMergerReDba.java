/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.PendingMergerReDbaImpl;
import se.signa.signature.dbo.PendingMergerReImpl;
import se.signa.signature.gen.dbo.PendingMergerRe;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class PendingMergerReDba extends SignatureDba<PendingMergerRe>
{
	private static PendingMergerReDbaImpl INSTANCE;

	public static PendingMergerReDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new PendingMergerReDbaImpl();
		return INSTANCE;
	}

	public PendingMergerReDba()
	{
		tableName = "pending_merger_re";
		tablePrefix = "pmr";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Pmr Id");
		columns.add("Pmr Name");
		columns.add("Pmr Partition");
		columns.add("Pmr Jbt Id");
		columns.add("Pmr User Name");
		columns.add("Pmr Dttm");
		return columns;
	}

	@Override
	public PendingMergerRe createEmptyDbo()
	{
		return new PendingMergerReImpl();
	}

	@Override
	public void checkDuplicates(PendingMergerRe dbo)
	{
		checkDuplicateGM(dbo.getPmrName(), "pmr_name", dbo.getPk());
		checkDuplicateGM(dbo.getPmrBk(), "pmr_bk", dbo.getPk());
	}

	@Override
	public int create(PendingMergerRe pendingMergerRe, int usrId)
	{
		return create(null, pendingMergerRe, usrId);
	}

	public int create(Connection connection, PendingMergerRe pendingMergerRe, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("pmr_id", "pending_merger_re");
		String bk = pendingMergerRe.getDisplayField();

		String query = " insert into pending_merger_re values(?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, pendingMergerRe.getPmrName(), pendingMergerRe.getPmrPartition(), pendingMergerRe.getPmrJbtId(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(PendingMergerRe pendingMergerRe, int usrId)
	{
		update(null, pendingMergerRe, usrId);
	}

	public void update(Connection connection, PendingMergerRe pendingMergerRe, int usrId)
	{
		String query = " update pending_merger_re set  pmr_name = ? , pmr_partition = ? , pmr_jbt_id = ? , pmr_modified_usr_id = ? , pmr_modified_dttm = ?  where pmr_id = ?  ";
		Object[] paramValues = new Object[] { pendingMergerRe.getPmrName(), pendingMergerRe.getPmrPartition(), pendingMergerRe.getPmrJbtId(), usrId, new DateTime(), pendingMergerRe.getPmrId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into pending_merger_re values(?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { pendingMergerRe.getPmrId(), pendingMergerRe.getPmrName(), pendingMergerRe.getPmrPartition(), pendingMergerRe.getPmrJbtId(), pendingMergerRe.getPmrBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<PendingMergerRe> fetchAll()
	{
		String query = " select * from pending_merger_re ";
		return RefDBHelper.getDB().fetchList(query, PendingMergerReImpl.class);
	}

	public List<PendingMergerRe> fetchAuditRowsByPk(int id)
	{
		String query = " select * from pending_merger_re where pmr_id=? order by pmr_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, PendingMergerReImpl.class);
	}

	public PendingMergerRe fetchByPk(int pmrId)
	{
		return fetchByPk(null, pmrId);
	}

	public PendingMergerRe fetchByPk(Connection connection, int pmrId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from pending_merger_re where pmr_id=? ", pmrId, PendingMergerReImpl.class);
	}

	public PendingMergerRe fetchByBk(String pmrBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_merger_re where pmr_bk=? ", pmrBk, PendingMergerReImpl.class);
	}

	public PendingMergerRe fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_merger_re where pmr_name=? ", displayField, PendingMergerReImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("pmr_name", "pending_merger_re");
	}

}