/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.RoleDbaImpl;
import se.signa.signature.dbo.RoleImpl;
import se.signa.signature.gen.dbo.Role;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class RoleDba extends SignatureDba<Role>
{
	private static RoleDbaImpl INSTANCE;

	public static RoleDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new RoleDbaImpl();
		return INSTANCE;
	}

	public RoleDba()
	{
		tableName = "role";
		tablePrefix = "rol";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Rol Id");
		columns.add("Rol Name");
		columns.add("Rol User Name");
		columns.add("Rol Dttm");
		return columns;
	}

	@Override
	public Role createEmptyDbo()
	{
		return new RoleImpl();
	}

	@Override
	public void checkDuplicates(Role dbo)
	{
		checkDuplicateGM(dbo.getRolName(), "rol_name", dbo.getPk());
		checkDuplicateGM(dbo.getRolBk(), "rol_bk", dbo.getPk());
	}

	@Override
	public int create(Role role, int usrId)
	{
		return create(null, role, usrId);
	}

	public int create(Connection connection, Role role, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("rol_id", "role");
		String bk = role.getDisplayField();

		String query = " insert into role values(?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, role.getRolName(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(Role role, int usrId)
	{
		update(null, role, usrId);
	}

	public void update(Connection connection, Role role, int usrId)
	{
		String query = " update role set  rol_name = ? , rol_modified_usr_id = ? , rol_modified_dttm = ?  where rol_id = ?  ";
		Object[] paramValues = new Object[] { role.getRolName(), usrId, new DateTime(), role.getRolId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into role values(?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { role.getRolId(), role.getRolName(), role.getRolBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<Role> fetchAll()
	{
		String query = " select * from role ";
		return RefDBHelper.getDB().fetchList(query, RoleImpl.class);
	}

	public List<Role> fetchAuditRowsByPk(int id)
	{
		String query = " select * from role where rol_id=? order by rol_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, RoleImpl.class);
	}

	public Role fetchByPk(int rolId)
	{
		return fetchByPk(null, rolId);
	}

	public Role fetchByPk(Connection connection, int rolId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from role where rol_id=? ", rolId, RoleImpl.class);
	}

	public Role fetchByBk(String rolBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from role where rol_bk=? ", rolBk, RoleImpl.class);
	}

	public Role fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from role where rol_name=? ", displayField, RoleImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("rol_name", "role");
	}

}