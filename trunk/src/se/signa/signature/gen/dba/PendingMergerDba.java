/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.PendingMergerDbaImpl;
import se.signa.signature.dbo.PendingMergerImpl;
import se.signa.signature.gen.dbo.PendingMerger;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class PendingMergerDba extends SignatureDba<PendingMerger>
{
	private static PendingMergerDbaImpl INSTANCE;

	public static PendingMergerDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new PendingMergerDbaImpl();
		return INSTANCE;
	}

	public PendingMergerDba()
	{
		tableName = "pending_merger";
		tablePrefix = "pme";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Pme Id");
		columns.add("Acc Id");
		columns.add("Pme Name");
		columns.add("Pme Partition");
		columns.add("Pms Rrp Jbt Id");
		columns.add("Pme User Name");
		columns.add("Pme Dttm");
		return columns;
	}

	@Override
	public PendingMerger createEmptyDbo()
	{
		return new PendingMergerImpl();
	}

	@Override
	public void checkDuplicates(PendingMerger dbo)
	{
		checkDuplicateGM(dbo.getPmeName(), "pme_name", dbo.getPk());
		checkDuplicateGM(dbo.getPmeBk(), "pme_bk", dbo.getPk());
	}

	@Override
	public int create(PendingMerger pendingMerger, int usrId)
	{
		return create(null, pendingMerger, usrId);
	}

	public int create(Connection connection, PendingMerger pendingMerger, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("pme_id", "pending_merger");
		String bk = pendingMerger.getDisplayField();

		String query = " insert into pending_merger values(?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, pendingMerger.getAccId(), pendingMerger.getPmeName(), pendingMerger.getPmePartition(), pendingMerger.getPmsRrpJbtId(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(PendingMerger pendingMerger, int usrId)
	{
		update(null, pendingMerger, usrId);
	}

	public void update(Connection connection, PendingMerger pendingMerger, int usrId)
	{
		String query = " update pending_merger set  acc_id = ? , pme_name = ? , pme_partition = ? , pms_rrp_jbt_id = ? , pme_modified_usr_id = ? , pme_modified_dttm = ?  where pme_id = ?  ";
		Object[] paramValues = new Object[] { pendingMerger.getAccId(), pendingMerger.getPmeName(), pendingMerger.getPmePartition(), pendingMerger.getPmsRrpJbtId(), usrId, new DateTime(), pendingMerger.getPmeId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into pending_merger values(?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { pendingMerger.getPmeId(), pendingMerger.getAccId(), pendingMerger.getPmeName(), pendingMerger.getPmePartition(), pendingMerger.getPmsRrpJbtId(), pendingMerger.getPmeBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<PendingMerger> fetchAll()
	{
		String query = " select * from pending_merger ";
		return RefDBHelper.getDB().fetchList(query, PendingMergerImpl.class);
	}

	public List<PendingMerger> fetchAuditRowsByPk(int id)
	{
		String query = " select * from pending_merger where pme_id=? order by pme_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, PendingMergerImpl.class);
	}

	public PendingMerger fetchByPk(int pmeId)
	{
		return fetchByPk(null, pmeId);
	}

	public PendingMerger fetchByPk(Connection connection, int pmeId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from pending_merger where pme_id=? ", pmeId, PendingMergerImpl.class);
	}

	public PendingMerger fetchByBk(String pmeBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_merger where pme_bk=? ", pmeBk, PendingMergerImpl.class);
	}

	public PendingMerger fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_merger where pme_name=? ", displayField, PendingMergerImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("pme_name", "pending_merger");
	}

}