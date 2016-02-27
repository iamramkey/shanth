/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.RoleCustomDbaImpl;
import se.signa.signature.dbo.RoleCustomImpl;
import se.signa.signature.gen.dbo.RoleCustom;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class RoleCustomDba extends SignatureDba<RoleCustom>
{
	private static RoleCustomDbaImpl INSTANCE;

	public static RoleCustomDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new RoleCustomDbaImpl();
		return INSTANCE;
	}

	public RoleCustomDba()
	{
		tableName = "role_custom";
		tablePrefix = "rcp";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Rcp Id");
		columns.add("Rol Id");
		columns.add("Tbi Id");
		columns.add("Rcp Code");
		columns.add("Rcp Background Action");
		columns.add("Rcp User Name");
		columns.add("Rcp Dttm");
		return columns;
	}

	@Override
	public RoleCustom createEmptyDbo()
	{
		return new RoleCustomImpl();
	}

	@Override
	public void checkDuplicates(RoleCustom dbo)
	{
		checkDuplicateGM(dbo.getRcpCode(), "rcp_code", dbo.getPk());
		checkDuplicateGM(dbo.getRcpBk(), "rcp_bk", dbo.getPk());
	}

	@Override
	public int create(RoleCustom roleCustom, int usrId)
	{
		return create(null, roleCustom, usrId);
	}

	public int create(Connection connection, RoleCustom roleCustom, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("rcp_id", "role_custom");
		String bk = roleCustom.getDisplayField();

		String query = " insert into role_custom values(?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, roleCustom.getRolId(), roleCustom.getTbiId(), roleCustom.getRcpCode(), roleCustom.getRcpBackgroundAction(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(RoleCustom roleCustom, int usrId)
	{
		update(null, roleCustom, usrId);
	}

	public void update(Connection connection, RoleCustom roleCustom, int usrId)
	{
		String query = " update role_custom set  rol_id = ? , tbi_id = ? , rcp_code = ? , rcp_background_action = ? , rcp_modified_usr_id = ? , rcp_modified_dttm = ?  where rcp_id = ?  ";
		Object[] paramValues = new Object[] { roleCustom.getRolId(), roleCustom.getTbiId(), roleCustom.getRcpCode(), roleCustom.getRcpBackgroundAction(), usrId, new DateTime(), roleCustom.getRcpId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into role_custom values(?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { roleCustom.getRcpId(), roleCustom.getRolId(), roleCustom.getTbiId(), roleCustom.getRcpCode(), roleCustom.getRcpBackgroundAction(), roleCustom.getRcpBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<RoleCustom> fetchAll()
	{
		String query = " select * from role_custom ";
		return RefDBHelper.getDB().fetchList(query, RoleCustomImpl.class);
	}

	public List<RoleCustom> fetchAuditRowsByPk(int id)
	{
		String query = " select * from role_custom where rcp_id=? order by rcp_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, RoleCustomImpl.class);
	}

	public RoleCustom fetchByPk(int rcpId)
	{
		return fetchByPk(null, rcpId);
	}

	public RoleCustom fetchByPk(Connection connection, int rcpId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from role_custom where rcp_id=? ", rcpId, RoleCustomImpl.class);
	}

	public RoleCustom fetchByBk(String rcpBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from role_custom where rcp_bk=? ", rcpBk, RoleCustomImpl.class);
	}

	public RoleCustom fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from role_custom where rcp_code=? ", displayField, RoleCustomImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("rcp_code", "role_custom");
	}

}