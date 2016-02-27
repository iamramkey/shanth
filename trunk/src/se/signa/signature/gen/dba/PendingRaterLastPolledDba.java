/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.PendingRaterLastPolledDbaImpl;
import se.signa.signature.dbo.PendingRaterLastPolledImpl;
import se.signa.signature.gen.dbo.PendingRaterLastPolled;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class PendingRaterLastPolledDba extends SignatureDba<PendingRaterLastPolled>
{
	private static PendingRaterLastPolledDbaImpl INSTANCE;

	public static PendingRaterLastPolledDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new PendingRaterLastPolledDbaImpl();
		return INSTANCE;
	}

	public PendingRaterLastPolledDba()
	{
		tableName = "pending_rater_last_polled";
		tablePrefix = "rlp";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Rlp Id");
		columns.add("Jbt Id");
		columns.add("Acc Id");
		columns.add("Rlp Last Polled Dttm");
		columns.add("Rlp Name");
		columns.add("Rlp User Name");
		columns.add("Rlp Dttm");
		return columns;
	}

	@Override
	public PendingRaterLastPolled createEmptyDbo()
	{
		return new PendingRaterLastPolledImpl();
	}

	@Override
	public void checkDuplicates(PendingRaterLastPolled dbo)
	{
		checkDuplicateGM(dbo.getRlpName(), "rlp_name", dbo.getPk());
		checkDuplicateGM(dbo.getRlpBk(), "rlp_bk", dbo.getPk());
	}

	@Override
	public int create(PendingRaterLastPolled pendingRaterLastPolled, int usrId)
	{
		return create(null, pendingRaterLastPolled, usrId);
	}

	public int create(Connection connection, PendingRaterLastPolled pendingRaterLastPolled, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("rlp_id", "pending_rater_last_polled");
		String bk = pendingRaterLastPolled.getDisplayField();

		String query = " insert into pending_rater_last_polled values(?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, pendingRaterLastPolled.getJbtId(), pendingRaterLastPolled.getAccId(), pendingRaterLastPolled.getRlpLastPolledDttm(), pendingRaterLastPolled.getRlpName(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(PendingRaterLastPolled pendingRaterLastPolled, int usrId)
	{
		update(null, pendingRaterLastPolled, usrId);
	}

	public void update(Connection connection, PendingRaterLastPolled pendingRaterLastPolled, int usrId)
	{
		String query = " update pending_rater_last_polled set  jbt_id = ? , acc_id = ? , rlp_last_polled_dttm = ? , rlp_name = ? , rlp_modified_usr_id = ? , rlp_modified_dttm = ?  where rlp_id = ?  ";
		Object[] paramValues = new Object[] { pendingRaterLastPolled.getJbtId(), pendingRaterLastPolled.getAccId(), pendingRaterLastPolled.getRlpLastPolledDttm(), pendingRaterLastPolled.getRlpName(), usrId, new DateTime(), pendingRaterLastPolled.getRlpId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into pending_rater_last_polled values(?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { pendingRaterLastPolled.getRlpId(), pendingRaterLastPolled.getJbtId(), pendingRaterLastPolled.getAccId(), pendingRaterLastPolled.getRlpLastPolledDttm(), pendingRaterLastPolled.getRlpName(), pendingRaterLastPolled.getRlpBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<PendingRaterLastPolled> fetchAll()
	{
		String query = " select * from pending_rater_last_polled ";
		return RefDBHelper.getDB().fetchList(query, PendingRaterLastPolledImpl.class);
	}

	public List<PendingRaterLastPolled> fetchAuditRowsByPk(int id)
	{
		String query = " select * from pending_rater_last_polled where rlp_id=? order by rlp_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, PendingRaterLastPolledImpl.class);
	}

	public PendingRaterLastPolled fetchByPk(int rlpId)
	{
		return fetchByPk(null, rlpId);
	}

	public PendingRaterLastPolled fetchByPk(Connection connection, int rlpId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from pending_rater_last_polled where rlp_id=? ", rlpId, PendingRaterLastPolledImpl.class);
	}

	public PendingRaterLastPolled fetchByBk(String rlpBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_rater_last_polled where rlp_bk=? ", rlpBk, PendingRaterLastPolledImpl.class);
	}

	public PendingRaterLastPolled fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_rater_last_polled where rlp_name=? ", displayField, PendingRaterLastPolledImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("rlp_name", "pending_rater_last_polled");
	}

}