/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.UserRolePermissionDbaImpl;
import se.signa.signature.dbo.UserRolePermissionImpl;
import se.signa.signature.gen.dbo.UserRolePermission;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class UserRolePermissionDba extends SignatureDba<UserRolePermission>
{
	private static UserRolePermissionDbaImpl INSTANCE;

	public static UserRolePermissionDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new UserRolePermissionDbaImpl();
		return INSTANCE;
	}

	public UserRolePermissionDba()
	{
		tableName = "user_role_permission";
		tablePrefix = "urp";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Urp Id");
		columns.add("Rol Id");
		columns.add("Urp Url");
		columns.add("Urp Action");
		columns.add("Urp Action Type");
		columns.add("Urp Code");
		columns.add("Urp User Name");
		columns.add("Urp Dttm");
		return columns;
	}

	@Override
	public UserRolePermission createEmptyDbo()
	{
		return new UserRolePermissionImpl();
	}

	@Override
	public void checkDuplicates(UserRolePermission dbo)
	{
		checkDuplicateGM(dbo.getUrpCode(), "urp_code", dbo.getPk());
		checkDuplicateGM(dbo.getUrpBk(), "urp_bk", dbo.getPk());
	}

	@Override
	public int create(UserRolePermission userRolePermission, int usrId)
	{
		return create(null, userRolePermission, usrId);
	}

	public int create(Connection connection, UserRolePermission userRolePermission, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("urp_id", "user_role_permission");
		String bk = userRolePermission.getDisplayField();

		String query = " insert into user_role_permission values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, userRolePermission.getRolId(), userRolePermission.getUrpUrl(), userRolePermission.getUrpAction(), userRolePermission.getUrpActionType(), userRolePermission.getUrpCode(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(UserRolePermission userRolePermission, int usrId)
	{
		update(null, userRolePermission, usrId);
	}

	public void update(Connection connection, UserRolePermission userRolePermission, int usrId)
	{
		String query = " update user_role_permission set  rol_id = ? , urp_url = ? , urp_action = ? , urp_action_type = ? , urp_code = ? , urp_modified_usr_id = ? , urp_modified_dttm = ?  where urp_id = ?  ";
		Object[] paramValues = new Object[] { userRolePermission.getRolId(), userRolePermission.getUrpUrl(), userRolePermission.getUrpAction(), userRolePermission.getUrpActionType(), userRolePermission.getUrpCode(), usrId, new DateTime(), userRolePermission.getUrpId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into user_role_permission values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { userRolePermission.getUrpId(), userRolePermission.getRolId(), userRolePermission.getUrpUrl(), userRolePermission.getUrpAction(), userRolePermission.getUrpActionType(), userRolePermission.getUrpCode(), userRolePermission.getUrpBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<UserRolePermission> fetchAll()
	{
		String query = " select * from user_role_permission ";
		return RefDBHelper.getDB().fetchList(query, UserRolePermissionImpl.class);
	}

	public List<UserRolePermission> fetchAuditRowsByPk(int id)
	{
		String query = " select * from user_role_permission where urp_id=? order by urp_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, UserRolePermissionImpl.class);
	}

	public UserRolePermission fetchByPk(int urpId)
	{
		return fetchByPk(null, urpId);
	}

	public UserRolePermission fetchByPk(Connection connection, int urpId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from user_role_permission where urp_id=? ", urpId, UserRolePermissionImpl.class);
	}

	public UserRolePermission fetchByBk(String urpBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from user_role_permission where urp_bk=? ", urpBk, UserRolePermissionImpl.class);
	}

	public UserRolePermission fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from user_role_permission where urp_code=? ", displayField, UserRolePermissionImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("urp_code", "user_role_permission");
	}

}