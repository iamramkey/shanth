/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.ToolbarDbaImpl;
import se.signa.signature.dbo.ToolbarImpl;
import se.signa.signature.gen.dbo.Toolbar;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class ToolbarDba extends SignatureDba<Toolbar>
{
	private static ToolbarDbaImpl INSTANCE;

	public static ToolbarDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new ToolbarDbaImpl();
		return INSTANCE;
	}

	public ToolbarDba()
	{
		tableName = "toolbar";
		tablePrefix = "tbr";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Tbr Id");
		columns.add("Tbr Parent Tbr Id");
		columns.add("Tbr Order No");
		columns.add("Tbr Name");
		columns.add("Tbr Icon");
		columns.add("Tbr User Name");
		columns.add("Tbr Dttm");
		return columns;
	}

	@Override
	public Toolbar createEmptyDbo()
	{
		return new ToolbarImpl();
	}

	@Override
	public void checkDuplicates(Toolbar dbo)
	{
		checkDuplicateGM(dbo.getTbrName(), "tbr_name", dbo.getPk());
		checkDuplicateGM(dbo.getTbrBk(), "tbr_bk", dbo.getPk());
	}

	@Override
	public int create(Toolbar toolbar, int usrId)
	{
		return create(null, toolbar, usrId);
	}

	public int create(Connection connection, Toolbar toolbar, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("tbr_id", "toolbar");
		String bk = toolbar.getDisplayField();

		String query = " insert into toolbar values(?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, toolbar.getTbrParentTbrId(), toolbar.getTbrOrderNo(), toolbar.getTbrName(), toolbar.getTbrIcon(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(Toolbar toolbar, int usrId)
	{
		update(null, toolbar, usrId);
	}

	public void update(Connection connection, Toolbar toolbar, int usrId)
	{
		String query = " update toolbar set  tbr_parent_tbr_id = ? , tbr_order_no = ? , tbr_name = ? , tbr_icon = ? , tbr_modified_usr_id = ? , tbr_modified_dttm = ?  where tbr_id = ?  ";
		Object[] paramValues = new Object[] { toolbar.getTbrParentTbrId(), toolbar.getTbrOrderNo(), toolbar.getTbrName(), toolbar.getTbrIcon(), usrId, new DateTime(), toolbar.getTbrId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into toolbar values(?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { toolbar.getTbrId(), toolbar.getTbrParentTbrId(), toolbar.getTbrOrderNo(), toolbar.getTbrName(), toolbar.getTbrIcon(), toolbar.getTbrBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<Toolbar> fetchAll()
	{
		String query = " select * from toolbar ";
		return RefDBHelper.getDB().fetchList(query, ToolbarImpl.class);
	}

	public List<Toolbar> fetchAuditRowsByPk(int id)
	{
		String query = " select * from toolbar where tbr_id=? order by tbr_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, ToolbarImpl.class);
	}

	public Toolbar fetchByPk(int tbrId)
	{
		return fetchByPk(null, tbrId);
	}

	public Toolbar fetchByPk(Connection connection, int tbrId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from toolbar where tbr_id=? ", tbrId, ToolbarImpl.class);
	}

	public Toolbar fetchByBk(String tbrBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from toolbar where tbr_bk=? ", tbrBk, ToolbarImpl.class);
	}

	public Toolbar fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from toolbar where tbr_name=? ", displayField, ToolbarImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("tbr_name", "toolbar");
	}

}