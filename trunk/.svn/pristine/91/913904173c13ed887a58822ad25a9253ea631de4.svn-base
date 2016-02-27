/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.MasterSearchColumnDbaImpl;
import se.signa.signature.dbo.MasterSearchColumnImpl;
import se.signa.signature.gen.dbo.MasterSearchColumn;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class MasterSearchColumnDba extends SignatureDba<MasterSearchColumn>
{
	private static MasterSearchColumnDbaImpl INSTANCE;

	public static MasterSearchColumnDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new MasterSearchColumnDbaImpl();
		return INSTANCE;
	}

	public MasterSearchColumnDba()
	{
		tableName = "master_search_column";
		tablePrefix = "mco";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Mco Id");
		columns.add("Mse Id");
		columns.add("Mco Order No");
		columns.add("Mco Header");
		columns.add("Mco Property");
		columns.add("Mco Code");
		columns.add("Mco User Name");
		columns.add("Mco Dttm");
		return columns;
	}

	@Override
	public MasterSearchColumn createEmptyDbo()
	{
		return new MasterSearchColumnImpl();
	}

	@Override
	public void checkDuplicates(MasterSearchColumn dbo)
	{
		checkDuplicateGM(dbo.getMcoCode(), "mco_code", dbo.getPk());
		checkDuplicateGM(dbo.getMcoBk(), "mco_bk", dbo.getPk());
	}

	@Override
	public int create(MasterSearchColumn masterSearchColumn, int usrId)
	{
		return create(null, masterSearchColumn, usrId);
	}

	public int create(Connection connection, MasterSearchColumn masterSearchColumn, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("mco_id", "master_search_column");
		String bk = masterSearchColumn.getDisplayField();

		String query = " insert into master_search_column values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, masterSearchColumn.getMseId(), masterSearchColumn.getMcoOrderNo(), masterSearchColumn.getMcoHeader(), masterSearchColumn.getMcoProperty(), masterSearchColumn.getMcoCode(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(MasterSearchColumn masterSearchColumn, int usrId)
	{
		update(null, masterSearchColumn, usrId);
	}

	public void update(Connection connection, MasterSearchColumn masterSearchColumn, int usrId)
	{
		String query = " update master_search_column set  mse_id = ? , mco_order_no = ? , mco_header = ? , mco_property = ? , mco_code = ? , mco_modified_usr_id = ? , mco_modified_dttm = ?  where mco_id = ?  ";
		Object[] paramValues = new Object[] { masterSearchColumn.getMseId(), masterSearchColumn.getMcoOrderNo(), masterSearchColumn.getMcoHeader(), masterSearchColumn.getMcoProperty(), masterSearchColumn.getMcoCode(), usrId, new DateTime(), masterSearchColumn.getMcoId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into master_search_column values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { masterSearchColumn.getMcoId(), masterSearchColumn.getMseId(), masterSearchColumn.getMcoOrderNo(), masterSearchColumn.getMcoHeader(), masterSearchColumn.getMcoProperty(), masterSearchColumn.getMcoCode(), masterSearchColumn.getMcoBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<MasterSearchColumn> fetchAll()
	{
		String query = " select * from master_search_column ";
		return RefDBHelper.getDB().fetchList(query, MasterSearchColumnImpl.class);
	}

	public List<MasterSearchColumn> fetchAuditRowsByPk(int id)
	{
		String query = " select * from master_search_column where mco_id=? order by mco_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, MasterSearchColumnImpl.class);
	}

	public MasterSearchColumn fetchByPk(int mcoId)
	{
		return fetchByPk(null, mcoId);
	}

	public MasterSearchColumn fetchByPk(Connection connection, int mcoId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from master_search_column where mco_id=? ", mcoId, MasterSearchColumnImpl.class);
	}

	public MasterSearchColumn fetchByBk(String mcoBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from master_search_column where mco_bk=? ", mcoBk, MasterSearchColumnImpl.class);
	}

	public MasterSearchColumn fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from master_search_column where mco_code=? ", displayField, MasterSearchColumnImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("mco_code", "master_search_column");
	}

}