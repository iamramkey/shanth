/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.UserLoginDbaImpl;
import se.signa.signature.dbo.UserLoginImpl;
import se.signa.signature.gen.dbo.UserLogin;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class UserLoginDba extends SignatureDba<UserLogin>
{
	private static UserLoginDbaImpl INSTANCE;

	public static UserLoginDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new UserLoginDbaImpl();
		return INSTANCE;
	}

	public UserLoginDba()
	{
		tableName = "user_login";
		tablePrefix = "usl";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Usl Id");
		columns.add("Usr Id");
		columns.add("Usl Login Dttm");
		columns.add("Usl Logout Dttm");
		columns.add("Usl Name");
		columns.add("Usl Status");
		columns.add("Usl User Name");
		columns.add("Usl Dttm");
		return columns;
	}

	@Override
	public UserLogin createEmptyDbo()
	{
		return new UserLoginImpl();
	}

	@Override
	public void checkDuplicates(UserLogin dbo)
	{
		checkDuplicateGM(dbo.getUslName(), "usl_name", dbo.getPk());
		checkDuplicateGM(dbo.getUslBk(), "usl_bk", dbo.getPk());
	}

	@Override
	public int create(UserLogin userLogin, int usrId)
	{
		return create(null, userLogin, usrId);
	}

	public int create(Connection connection, UserLogin userLogin, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("usl_id", "user_login");
		String bk = userLogin.getDisplayField();

		String query = " insert into user_login values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, userLogin.getUsrId(), userLogin.getUslLoginDttm(), userLogin.getUslLogoutDttm(), userLogin.getUslName(), userLogin.getUslStatus(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(UserLogin userLogin, int usrId)
	{
		update(null, userLogin, usrId);
	}

	public void update(Connection connection, UserLogin userLogin, int usrId)
	{
		String query = " update user_login set  usr_id = ? , usl_login_dttm = ? , usl_logout_dttm = ? , usl_name = ? , usl_status = ? , usl_modified_usr_id = ? , usl_modified_dttm = ?  where usl_id = ?  ";
		Object[] paramValues = new Object[] { userLogin.getUsrId(), userLogin.getUslLoginDttm(), userLogin.getUslLogoutDttm(), userLogin.getUslName(), userLogin.getUslStatus(), usrId, new DateTime(), userLogin.getUslId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into user_login values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { userLogin.getUslId(), userLogin.getUsrId(), userLogin.getUslLoginDttm(), userLogin.getUslLogoutDttm(), userLogin.getUslName(), userLogin.getUslStatus(), userLogin.getUslBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<UserLogin> fetchAll()
	{
		String query = " select * from user_login ";
		return RefDBHelper.getDB().fetchList(query, UserLoginImpl.class);
	}

	public List<UserLogin> fetchAuditRowsByPk(int id)
	{
		String query = " select * from user_login where usl_id=? order by usl_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, UserLoginImpl.class);
	}

	public UserLogin fetchByPk(int uslId)
	{
		return fetchByPk(null, uslId);
	}

	public UserLogin fetchByPk(Connection connection, int uslId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from user_login where usl_id=? ", uslId, UserLoginImpl.class);
	}

	public UserLogin fetchByBk(String uslBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from user_login where usl_bk=? ", uslBk, UserLoginImpl.class);
	}

	public UserLogin fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from user_login where usl_name=? ", displayField, UserLoginImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("usl_name", "user_login");
	}

}