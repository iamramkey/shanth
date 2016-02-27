/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.ToolbarItemDbaImpl;
import se.signa.signature.dbo.ToolbarItemImpl;
import se.signa.signature.gen.dbo.ToolbarItem;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class ToolbarItemDba extends SignatureDba<ToolbarItem>
{
	private static ToolbarItemDbaImpl INSTANCE;

	public static ToolbarItemDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new ToolbarItemDbaImpl();
		return INSTANCE;
	}

	public ToolbarItemDba()
	{
		tableName = "toolbar_item";
		tablePrefix = "tbi";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Tbi Id");
		columns.add("Tbr Id");
		columns.add("Tbi Order No");
		columns.add("Tbi Name");
		columns.add("Tbi Url");
		columns.add("Tbi Icon");
		columns.add("Tbi User Name");
		columns.add("Tbi Dttm");
		return columns;
	}

	@Override
	public ToolbarItem createEmptyDbo()
	{
		return new ToolbarItemImpl();
	}

	@Override
	public void checkDuplicates(ToolbarItem dbo)
	{
		checkDuplicateGM(dbo.getTbiName(), "tbi_name", dbo.getPk());
		checkDuplicateGM(dbo.getTbiBk(), "tbi_bk", dbo.getPk());
	}

	@Override
	public int create(ToolbarItem toolbarItem, int usrId)
	{
		return create(null, toolbarItem, usrId);
	}

	public int create(Connection connection, ToolbarItem toolbarItem, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("tbi_id", "toolbar_item");
		String bk = toolbarItem.getDisplayField();

		String query = " insert into toolbar_item values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, toolbarItem.getTbrId(), toolbarItem.getTbiOrderNo(), toolbarItem.getTbiName(), toolbarItem.getTbiUrl(), toolbarItem.getTbiIcon(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(ToolbarItem toolbarItem, int usrId)
	{
		update(null, toolbarItem, usrId);
	}

	public void update(Connection connection, ToolbarItem toolbarItem, int usrId)
	{
		String query = " update toolbar_item set  tbr_id = ? , tbi_order_no = ? , tbi_name = ? , tbi_url = ? , tbi_icon = ? , tbi_modified_usr_id = ? , tbi_modified_dttm = ?  where tbi_id = ?  ";
		Object[] paramValues = new Object[] { toolbarItem.getTbrId(), toolbarItem.getTbiOrderNo(), toolbarItem.getTbiName(), toolbarItem.getTbiUrl(), toolbarItem.getTbiIcon(), usrId, new DateTime(), toolbarItem.getTbiId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into toolbar_item values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { toolbarItem.getTbiId(), toolbarItem.getTbrId(), toolbarItem.getTbiOrderNo(), toolbarItem.getTbiName(), toolbarItem.getTbiUrl(), toolbarItem.getTbiIcon(), toolbarItem.getTbiBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<ToolbarItem> fetchAll()
	{
		String query = " select * from toolbar_item ";
		return RefDBHelper.getDB().fetchList(query, ToolbarItemImpl.class);
	}

	public List<ToolbarItem> fetchAuditRowsByPk(int id)
	{
		String query = " select * from toolbar_item where tbi_id=? order by tbi_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, ToolbarItemImpl.class);
	}

	public ToolbarItem fetchByPk(int tbiId)
	{
		return fetchByPk(null, tbiId);
	}

	public ToolbarItem fetchByPk(Connection connection, int tbiId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from toolbar_item where tbi_id=? ", tbiId, ToolbarItemImpl.class);
	}

	public ToolbarItem fetchByBk(String tbiBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from toolbar_item where tbi_bk=? ", tbiBk, ToolbarItemImpl.class);
	}

	public ToolbarItem fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from toolbar_item where tbi_name=? ", displayField, ToolbarItemImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("tbi_name", "toolbar_item");
	}

}