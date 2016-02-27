/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.MasterSearchDbaImpl;
import se.signa.signature.dbo.MasterSearchImpl;
import se.signa.signature.gen.dbo.MasterSearch;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class MasterSearchDba extends SignatureDba<MasterSearch>
{
	private static MasterSearchDbaImpl INSTANCE;

	public static MasterSearchDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new MasterSearchDbaImpl();
		return INSTANCE;
	}

	public MasterSearchDba()
	{
		tableName = "master_search";
		tablePrefix = "mse";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Mse Id");
		columns.add("Mse Url");
		columns.add("Mse User Name");
		columns.add("Mse Dttm");
		return columns;
	}

	@Override
	public MasterSearch createEmptyDbo()
	{
		return new MasterSearchImpl();
	}

	@Override
	public void checkDuplicates(MasterSearch dbo)
	{
		checkDuplicateGM(dbo.getMseUrl(), "mse_url", dbo.getPk());
		checkDuplicateGM(dbo.getMseBk(), "mse_bk", dbo.getPk());
	}

	@Override
	public int create(MasterSearch masterSearch, int usrId)
	{
		return create(null, masterSearch, usrId);
	}

	public int create(Connection connection, MasterSearch masterSearch, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("mse_id", "master_search");
		String bk = masterSearch.getDisplayField();

		String query = " insert into master_search values(?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, masterSearch.getMseUrl(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(MasterSearch masterSearch, int usrId)
	{
		update(null, masterSearch, usrId);
	}

	public void update(Connection connection, MasterSearch masterSearch, int usrId)
	{
		String query = " update master_search set  mse_url = ? , mse_modified_usr_id = ? , mse_modified_dttm = ?  where mse_id = ?  ";
		Object[] paramValues = new Object[] { masterSearch.getMseUrl(), usrId, new DateTime(), masterSearch.getMseId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into master_search values(?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { masterSearch.getMseId(), masterSearch.getMseUrl(), masterSearch.getMseBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<MasterSearch> fetchAll()
	{
		String query = " select * from master_search ";
		return RefDBHelper.getDB().fetchList(query, MasterSearchImpl.class);
	}

	public List<MasterSearch> fetchAuditRowsByPk(int id)
	{
		String query = " select * from master_search where mse_id=? order by mse_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, MasterSearchImpl.class);
	}

	public MasterSearch fetchByPk(int mseId)
	{
		return fetchByPk(null, mseId);
	}

	public MasterSearch fetchByPk(Connection connection, int mseId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from master_search where mse_id=? ", mseId, MasterSearchImpl.class);
	}

	public MasterSearch fetchByBk(String mseBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from master_search where mse_bk=? ", mseBk, MasterSearchImpl.class);
	}

	public MasterSearch fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from master_search where mse_url=? ", displayField, MasterSearchImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("mse_url", "master_search");
	}

}