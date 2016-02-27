/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.PendingRerateDsDbaImpl;
import se.signa.signature.dbo.PendingRerateDsImpl;
import se.signa.signature.gen.dbo.PendingRerateDs;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class PendingRerateDsDba extends SignatureDba<PendingRerateDs>
{
	private static PendingRerateDsDbaImpl INSTANCE;

	public static PendingRerateDsDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new PendingRerateDsDbaImpl();
		return INSTANCE;
	}

	public PendingRerateDsDba()
	{
		tableName = "pending_rerate_ds";
		tablePrefix = "prd";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Prd Id");
		columns.add("Prd Name");
		columns.add("Prd Batch Size");
		columns.add("Prd Frequency Secs");
		columns.add("Prd Timeout Secs");
		columns.add("Prd Rerate Poller Jbt Id");
		columns.add("Prd Rerate Dataload Jbt Id");
		columns.add("Prd User Name");
		columns.add("Prd Dttm");
		return columns;
	}

	@Override
	public PendingRerateDs createEmptyDbo()
	{
		return new PendingRerateDsImpl();
	}

	@Override
	public void checkDuplicates(PendingRerateDs dbo)
	{
		checkDuplicateGM(dbo.getPrdName(), "prd_name", dbo.getPk());
		checkDuplicateGM(dbo.getPrdBk(), "prd_bk", dbo.getPk());
	}

	@Override
	public int create(PendingRerateDs pendingRerateDs, int usrId)
	{
		return create(null, pendingRerateDs, usrId);
	}

	public int create(Connection connection, PendingRerateDs pendingRerateDs, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("prd_id", "pending_rerate_ds");
		String bk = pendingRerateDs.getDisplayField();

		String query = " insert into pending_rerate_ds values(?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, pendingRerateDs.getPrdName(), pendingRerateDs.getPrdBatchSize(), pendingRerateDs.getPrdFrequencySecs(), pendingRerateDs.getPrdTimeoutSecs(), pendingRerateDs.getPrdReratePollerJbtId(), pendingRerateDs.getPrdRerateDataloadJbtId(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(PendingRerateDs pendingRerateDs, int usrId)
	{
		update(null, pendingRerateDs, usrId);
	}

	public void update(Connection connection, PendingRerateDs pendingRerateDs, int usrId)
	{
		String query = " update pending_rerate_ds set  prd_name = ? , prd_batch_size = ? , prd_frequency_secs = ? , prd_timeout_secs = ? , prd_rerate_poller_jbt_id = ? , prd_rerate_dataload_jbt_id = ? , prd_modified_usr_id = ? , prd_modified_dttm = ?  where prd_id = ?  ";
		Object[] paramValues = new Object[] { pendingRerateDs.getPrdName(), pendingRerateDs.getPrdBatchSize(), pendingRerateDs.getPrdFrequencySecs(), pendingRerateDs.getPrdTimeoutSecs(), pendingRerateDs.getPrdReratePollerJbtId(), pendingRerateDs.getPrdRerateDataloadJbtId(), usrId, new DateTime(), pendingRerateDs.getPrdId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into pending_rerate_ds values(?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { pendingRerateDs.getPrdId(), pendingRerateDs.getPrdName(), pendingRerateDs.getPrdBatchSize(), pendingRerateDs.getPrdFrequencySecs(), pendingRerateDs.getPrdTimeoutSecs(), pendingRerateDs.getPrdReratePollerJbtId(), pendingRerateDs.getPrdRerateDataloadJbtId(), pendingRerateDs.getPrdBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<PendingRerateDs> fetchAll()
	{
		String query = " select * from pending_rerate_ds ";
		return RefDBHelper.getDB().fetchList(query, PendingRerateDsImpl.class);
	}

	public List<PendingRerateDs> fetchAuditRowsByPk(int id)
	{
		String query = " select * from pending_rerate_ds where prd_id=? order by prd_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, PendingRerateDsImpl.class);
	}

	public PendingRerateDs fetchByPk(int prdId)
	{
		return fetchByPk(null, prdId);
	}

	public PendingRerateDs fetchByPk(Connection connection, int prdId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from pending_rerate_ds where prd_id=? ", prdId, PendingRerateDsImpl.class);
	}

	public PendingRerateDs fetchByBk(String prdBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_rerate_ds where prd_bk=? ", prdBk, PendingRerateDsImpl.class);
	}

	public PendingRerateDs fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_rerate_ds where prd_name=? ", displayField, PendingRerateDsImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("prd_name", "pending_rerate_ds");
	}

}