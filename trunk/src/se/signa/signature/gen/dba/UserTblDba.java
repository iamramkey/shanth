/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.UserTblDbaImpl;
import se.signa.signature.dbo.UserTblImpl;
import se.signa.signature.gen.dbo.UserTbl;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class UserTblDba extends SignatureDba<UserTbl>
{
	private static UserTblDbaImpl INSTANCE;

	public static UserTblDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new UserTblDbaImpl();
		return INSTANCE;
	}

	public UserTblDba()
	{
		tableName = "user_tbl";
		tablePrefix = "usr";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Usr Id");
		columns.add("Rol Id");
		columns.add("Usr Name");
		columns.add("Usr Password");
		columns.add("Usr Display Name");
		columns.add("Usr Email");
		columns.add("Usr Mobile Phone");
		columns.add("Usr Status");
		columns.add("Usr User Name");
		columns.add("Usr Dttm");
		return columns;
	}

	@Override
	public UserTbl createEmptyDbo()
	{
		return new UserTblImpl();
	}

	@Override
	public void checkDuplicates(UserTbl dbo)
	{
		checkDuplicateGM(dbo.getUsrName(), "usr_name", dbo.getPk());
		checkDuplicateGM(dbo.getUsrBk(), "usr_bk", dbo.getPk());
	}

	@Override
	public int create(UserTbl userTbl, int usrId)
	{
		return create(null, userTbl, usrId);
	}

	public int create(Connection connection, UserTbl userTbl, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("usr_id", "user_tbl");
		String bk = userTbl.getDisplayField();

		String query = " insert into user_tbl values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, userTbl.getRolId(), userTbl.getUsrName(), userTbl.getUsrPassword(), userTbl.getUsrDisplayName(), userTbl.getUsrEmail(), userTbl.getUsrMobilePhone(), userTbl.getUsrStatus(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(UserTbl userTbl, int usrId)
	{
		update(null, userTbl, usrId);
	}

	public void update(Connection connection, UserTbl userTbl, int usrId)
	{
		String query = " update user_tbl set  rol_id = ? , usr_name = ? , usr_password = ? , usr_display_name = ? , usr_email = ? , usr_mobile_phone = ? , usr_status = ? , usr_modified_usr_id = ? , usr_modified_dttm = ?  where usr_id = ?  ";
		Object[] paramValues = new Object[] { userTbl.getRolId(), userTbl.getUsrName(), userTbl.getUsrPassword(), userTbl.getUsrDisplayName(), userTbl.getUsrEmail(), userTbl.getUsrMobilePhone(), userTbl.getUsrStatus(), usrId, new DateTime(), userTbl.getUsrId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into user_tbl values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { userTbl.getUsrId(), userTbl.getRolId(), userTbl.getUsrName(), userTbl.getUsrPassword(), userTbl.getUsrDisplayName(), userTbl.getUsrEmail(), userTbl.getUsrMobilePhone(), userTbl.getUsrStatus(), userTbl.getUsrBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<UserTbl> fetchAll()
	{
		String query = " select * from user_tbl ";
		return RefDBHelper.getDB().fetchList(query, UserTblImpl.class);
	}

	public List<UserTbl> fetchAuditRowsByPk(int id)
	{
		String query = " select * from user_tbl where usr_id=? order by usr_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, UserTblImpl.class);
	}

	public UserTbl fetchByPk(int usrId)
	{
		return fetchByPk(null, usrId);
	}

	public UserTbl fetchByPk(Connection connection, int usrId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from user_tbl where usr_id=? ", usrId, UserTblImpl.class);
	}

	public UserTbl fetchByBk(String usrBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from user_tbl where usr_bk=? ", usrBk, UserTblImpl.class);
	}

	public UserTbl fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from user_tbl where usr_name=? ", displayField, UserTblImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("usr_name", "user_tbl");
	}

}