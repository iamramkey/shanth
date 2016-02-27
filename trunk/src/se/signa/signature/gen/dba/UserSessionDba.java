/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.UserSessionDbaImpl;
import se.signa.signature.dbo.UserSessionImpl;
import se.signa.signature.gen.dbo.UserSession;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class UserSessionDba extends SignatureDba<UserSession>
{
	private static UserSessionDbaImpl INSTANCE;

	public static UserSessionDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new UserSessionDbaImpl();
		return INSTANCE;
	}

	public UserSessionDba()
	{
		tableName = "user_session";
		tablePrefix = "uss";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Uss Id");
		columns.add("Usl Id");
		columns.add("Uss Session Code");
		columns.add("Uss Last Txn Dttm");
		columns.add("Uss Name");
		columns.add("Uss User Name");
		columns.add("Uss Dttm");
		return columns;
	}

	@Override
	public UserSession createEmptyDbo()
	{
		return new UserSessionImpl();
	}

	@Override
	public void checkDuplicates(UserSession dbo)
	{
		checkDuplicateGM(dbo.getUssName(), "uss_name", dbo.getPk());
		checkDuplicateGM(dbo.getUssBk(), "uss_bk", dbo.getPk());
	}

	@Override
	public int create(UserSession userSession, int usrId)
	{
		return create(null, userSession, usrId);
	}

	public int create(Connection connection, UserSession userSession, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("uss_id", "user_session");
		String bk = userSession.getDisplayField();

		String query = " insert into user_session values(?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, userSession.getUslId(), userSession.getUssSessionCode(), userSession.getUssLastTxnDttm(), userSession.getUssName(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(UserSession userSession, int usrId)
	{
		update(null, userSession, usrId);
	}

	public void update(Connection connection, UserSession userSession, int usrId)
	{
		String query = " update user_session set  usl_id = ? , uss_session_code = ? , uss_last_txn_dttm = ? , uss_name = ? , uss_modified_usr_id = ? , uss_modified_dttm = ?  where uss_id = ?  ";
		Object[] paramValues = new Object[] { userSession.getUslId(), userSession.getUssSessionCode(), userSession.getUssLastTxnDttm(), userSession.getUssName(), usrId, new DateTime(), userSession.getUssId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into user_session values(?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { userSession.getUssId(), userSession.getUslId(), userSession.getUssSessionCode(), userSession.getUssLastTxnDttm(), userSession.getUssName(), userSession.getUssBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<UserSession> fetchAll()
	{
		String query = " select * from user_session ";
		return RefDBHelper.getDB().fetchList(query, UserSessionImpl.class);
	}

	public List<UserSession> fetchAuditRowsByPk(int id)
	{
		String query = " select * from user_session where uss_id=? order by uss_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, UserSessionImpl.class);
	}

	public UserSession fetchByPk(int ussId)
	{
		return fetchByPk(null, ussId);
	}

	public UserSession fetchByPk(Connection connection, int ussId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from user_session where uss_id=? ", ussId, UserSessionImpl.class);
	}

	public UserSession fetchByBk(String ussBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from user_session where uss_bk=? ", ussBk, UserSessionImpl.class);
	}

	public UserSession fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from user_session where uss_name=? ", displayField, UserSessionImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("uss_name", "user_session");
	}

}