/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.LastPolledDbaImpl;
import se.signa.signature.dbo.LastPolledImpl;
import se.signa.signature.gen.dbo.LastPolled;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class LastPolledDba extends SignatureDba<LastPolled>
{
	private static LastPolledDbaImpl INSTANCE;

	public static LastPolledDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new LastPolledDbaImpl();
		return INSTANCE;
	}

	public LastPolledDba()
	{
		tableName = "last_polled";
		tablePrefix = "lpo";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Lpo Id");
		columns.add("Jbt Id");
		columns.add("Lpo Name");
		columns.add("Lpo Last Polled Dttm");
		columns.add("Lpo User Name");
		columns.add("Lpo Dttm");
		return columns;
	}

	@Override
	public LastPolled createEmptyDbo()
	{
		return new LastPolledImpl();
	}

	@Override
	public void checkDuplicates(LastPolled dbo)
	{
		checkDuplicateGM(dbo.getLpoName(), "lpo_name", dbo.getPk());
		checkDuplicateGM(dbo.getLpoBk(), "lpo_bk", dbo.getPk());
	}

	@Override
	public int create(LastPolled lastPolled, int usrId)
	{
		return create(null, lastPolled, usrId);
	}

	public int create(Connection connection, LastPolled lastPolled, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("lpo_id", "last_polled");
		String bk = lastPolled.getDisplayField();

		String query = " insert into last_polled values(?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, lastPolled.getJbtId(), lastPolled.getLpoName(), lastPolled.getLpoLastPolledDttm(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(LastPolled lastPolled, int usrId)
	{
		update(null, lastPolled, usrId);
	}

	public void update(Connection connection, LastPolled lastPolled, int usrId)
	{
		String query = " update last_polled set  jbt_id = ? , lpo_name = ? , lpo_last_polled_dttm = ? , lpo_modified_usr_id = ? , lpo_modified_dttm = ?  where lpo_id = ?  ";
		Object[] paramValues = new Object[] { lastPolled.getJbtId(), lastPolled.getLpoName(), lastPolled.getLpoLastPolledDttm(), usrId, new DateTime(), lastPolled.getLpoId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into last_polled values(?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { lastPolled.getLpoId(), lastPolled.getJbtId(), lastPolled.getLpoName(), lastPolled.getLpoLastPolledDttm(), lastPolled.getLpoBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<LastPolled> fetchAll()
	{
		String query = " select * from last_polled ";
		return RefDBHelper.getDB().fetchList(query, LastPolledImpl.class);
	}

	public List<LastPolled> fetchAuditRowsByPk(int id)
	{
		String query = " select * from last_polled where lpo_id=? order by lpo_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, LastPolledImpl.class);
	}

	public LastPolled fetchByPk(int lpoId)
	{
		return fetchByPk(null, lpoId);
	}

	public LastPolled fetchByPk(Connection connection, int lpoId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from last_polled where lpo_id=? ", lpoId, LastPolledImpl.class);
	}

	public LastPolled fetchByBk(String lpoBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from last_polled where lpo_bk=? ", lpoBk, LastPolledImpl.class);
	}

	public LastPolled fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from last_polled where lpo_name=? ", displayField, LastPolledImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("lpo_name", "last_polled");
	}

}