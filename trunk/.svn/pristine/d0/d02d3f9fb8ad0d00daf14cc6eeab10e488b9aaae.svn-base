/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.UserPasswordDbaImpl;
import se.signa.signature.dbo.UserPasswordImpl;
import se.signa.signature.gen.dbo.UserPassword;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class UserPasswordDba extends SignatureDba<UserPassword>
{
	private static UserPasswordDbaImpl INSTANCE;

	public static UserPasswordDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new UserPasswordDbaImpl();
		return INSTANCE;
	}

	public UserPasswordDba()
	{
		tableName = "user_password";
		tablePrefix = "usp";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Usp Id");
		columns.add("Usr Id");
		columns.add("Usp Password");
		columns.add("Usp Changed Dttm");
		columns.add("Usp Name");
		columns.add("Usp User Name");
		columns.add("Usp Dttm");
		return columns;
	}

	@Override
	public UserPassword createEmptyDbo()
	{
		return new UserPasswordImpl();
	}

	@Override
	public void checkDuplicates(UserPassword dbo)
	{
		checkDuplicateGM(dbo.getUspName(), "usp_name", dbo.getPk());
		checkDuplicateGM(dbo.getUspBk(), "usp_bk", dbo.getPk());
	}

	@Override
	public int create(UserPassword userPassword, int usrId)
	{
		return create(null, userPassword, usrId);
	}

	public int create(Connection connection, UserPassword userPassword, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("usp_id", "user_password");
		String bk = userPassword.getDisplayField();

		String query = " insert into user_password values(?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, userPassword.getUsrId(), userPassword.getUspPassword(), userPassword.getUspChangedDttm(), userPassword.getUspName(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(UserPassword userPassword, int usrId)
	{
		update(null, userPassword, usrId);
	}

	public void update(Connection connection, UserPassword userPassword, int usrId)
	{
		String query = " update user_password set  usr_id = ? , usp_password = ? , usp_changed_dttm = ? , usp_name = ? , usp_modified_usr_id = ? , usp_modified_dttm = ?  where usp_id = ?  ";
		Object[] paramValues = new Object[] { userPassword.getUsrId(), userPassword.getUspPassword(), userPassword.getUspChangedDttm(), userPassword.getUspName(), usrId, new DateTime(), userPassword.getUspId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into user_password values(?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { userPassword.getUspId(), userPassword.getUsrId(), userPassword.getUspPassword(), userPassword.getUspChangedDttm(), userPassword.getUspName(), userPassword.getUspBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<UserPassword> fetchAll()
	{
		String query = " select * from user_password ";
		return RefDBHelper.getDB().fetchList(query, UserPasswordImpl.class);
	}

	public List<UserPassword> fetchAuditRowsByPk(int id)
	{
		String query = " select * from user_password where usp_id=? order by usp_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, UserPasswordImpl.class);
	}

	public UserPassword fetchByPk(int uspId)
	{
		return fetchByPk(null, uspId);
	}

	public UserPassword fetchByPk(Connection connection, int uspId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from user_password where usp_id=? ", uspId, UserPasswordImpl.class);
	}

	public UserPassword fetchByBk(String uspBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from user_password where usp_bk=? ", uspBk, UserPasswordImpl.class);
	}

	public UserPassword fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from user_password where usp_name=? ", displayField, UserPasswordImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("usp_name", "user_password");
	}

}