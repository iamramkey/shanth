/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.PendingRecomputeDbaImpl;
import se.signa.signature.dbo.PendingRecomputeImpl;
import se.signa.signature.gen.dbo.PendingRecompute;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class PendingRecomputeDba extends SignatureDba<PendingRecompute>
{
	private static PendingRecomputeDbaImpl INSTANCE;

	public static PendingRecomputeDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new PendingRecomputeDbaImpl();
		return INSTANCE;
	}

	public PendingRecomputeDba()
	{
		tableName = "pending_recompute";
		tablePrefix = "prr";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Prr Id");
		columns.add("Acc Id");
		columns.add("Del Id");
		columns.add("Prr Type");
		columns.add("Prr Priority");
		columns.add("Prr Name");
		columns.add("Prr User Name");
		columns.add("Prr Dttm");
		return columns;
	}

	@Override
	public PendingRecompute createEmptyDbo()
	{
		return new PendingRecomputeImpl();
	}

	@Override
	public void checkDuplicates(PendingRecompute dbo)
	{
		checkDuplicateGM(dbo.getPrrName(), "prr_name", dbo.getPk());
		checkDuplicateGM(dbo.getPrrBk(), "prr_bk", dbo.getPk());
	}

	@Override
	public int create(PendingRecompute pendingRecompute, int usrId)
	{
		return create(null, pendingRecompute, usrId);
	}

	public int create(Connection connection, PendingRecompute pendingRecompute, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("prr_id", "pending_recompute");
		String bk = pendingRecompute.getDisplayField();

		String query = " insert into pending_recompute values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, pendingRecompute.getAccId(), pendingRecompute.getDelId(), pendingRecompute.getPrrType(), pendingRecompute.getPrrPriority(), pendingRecompute.getPrrName(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(PendingRecompute pendingRecompute, int usrId)
	{
		update(null, pendingRecompute, usrId);
	}

	public void update(Connection connection, PendingRecompute pendingRecompute, int usrId)
	{
		String query = " update pending_recompute set  acc_id = ? , del_id = ? , prr_type = ? , prr_priority = ? , prr_name = ? , prr_modified_usr_id = ? , prr_modified_dttm = ?  where prr_id = ?  ";
		Object[] paramValues = new Object[] { pendingRecompute.getAccId(), pendingRecompute.getDelId(), pendingRecompute.getPrrType(), pendingRecompute.getPrrPriority(), pendingRecompute.getPrrName(), usrId, new DateTime(), pendingRecompute.getPrrId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into pending_recompute values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { pendingRecompute.getPrrId(), pendingRecompute.getAccId(), pendingRecompute.getDelId(), pendingRecompute.getPrrType(), pendingRecompute.getPrrPriority(), pendingRecompute.getPrrName(), pendingRecompute.getPrrBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<PendingRecompute> fetchAll()
	{
		String query = " select * from pending_recompute ";
		return RefDBHelper.getDB().fetchList(query, PendingRecomputeImpl.class);
	}

	public List<PendingRecompute> fetchAuditRowsByPk(int id)
	{
		String query = " select * from pending_recompute where prr_id=? order by prr_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, PendingRecomputeImpl.class);
	}

	public PendingRecompute fetchByPk(int prrId)
	{
		return fetchByPk(null, prrId);
	}

	public PendingRecompute fetchByPk(Connection connection, int prrId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from pending_recompute where prr_id=? ", prrId, PendingRecomputeImpl.class);
	}

	public PendingRecompute fetchByBk(String prrBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_recompute where prr_bk=? ", prrBk, PendingRecomputeImpl.class);
	}

	public PendingRecompute fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_recompute where prr_name=? ", displayField, PendingRecomputeImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("prr_name", "pending_recompute");
	}

}