/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.RolePrivilegeDbaImpl;
import se.signa.signature.dbo.RolePrivilegeImpl;
import se.signa.signature.gen.dbo.RolePrivilege;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class RolePrivilegeDba extends SignatureDba<RolePrivilege>
{
	private static RolePrivilegeDbaImpl INSTANCE;

	public static RolePrivilegeDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new RolePrivilegeDbaImpl();
		return INSTANCE;
	}

	public RolePrivilegeDba()
	{
		tableName = "role_privilege";
		tablePrefix = "rop";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Rop Id");
		columns.add("Rol Id");
		columns.add("Tbi Id");
		columns.add("Rop Name");
		columns.add("Rop Index");
		columns.add("Rop View");
		columns.add("Rop New");
		columns.add("Rop Edit");
		columns.add("Rop Delete");
		columns.add("Rop User Name");
		columns.add("Rop Dttm");
		return columns;
	}

	@Override
	public RolePrivilege createEmptyDbo()
	{
		return new RolePrivilegeImpl();
	}

	@Override
	public void checkDuplicates(RolePrivilege dbo)
	{
		checkDuplicateGM(dbo.getRopName(), "rop_name", dbo.getPk());
		checkDuplicateGM(dbo.getRopBk(), "rop_bk", dbo.getPk());
	}

	@Override
	public int create(RolePrivilege rolePrivilege, int usrId)
	{
		return create(null, rolePrivilege, usrId);
	}

	public int create(Connection connection, RolePrivilege rolePrivilege, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("rop_id", "role_privilege");
		String bk = rolePrivilege.getDisplayField();

		String query = " insert into role_privilege values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, rolePrivilege.getRolId(), rolePrivilege.getTbiId(), rolePrivilege.getRopName(), rolePrivilege.getRopIndex(), rolePrivilege.getRopView(), rolePrivilege.getRopNew(), rolePrivilege.getRopEdit(), rolePrivilege.getRopDelete(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(RolePrivilege rolePrivilege, int usrId)
	{
		update(null, rolePrivilege, usrId);
	}

	public void update(Connection connection, RolePrivilege rolePrivilege, int usrId)
	{
		String query = " update role_privilege set  rol_id = ? , tbi_id = ? , rop_name = ? , rop_index = ? , rop_view = ? , rop_new = ? , rop_edit = ? , rop_delete = ? , rop_modified_usr_id = ? , rop_modified_dttm = ?  where rop_id = ?  ";
		Object[] paramValues = new Object[] { rolePrivilege.getRolId(), rolePrivilege.getTbiId(), rolePrivilege.getRopName(), rolePrivilege.getRopIndex(), rolePrivilege.getRopView(), rolePrivilege.getRopNew(), rolePrivilege.getRopEdit(), rolePrivilege.getRopDelete(), usrId, new DateTime(), rolePrivilege.getRopId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into role_privilege values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { rolePrivilege.getRopId(), rolePrivilege.getRolId(), rolePrivilege.getTbiId(), rolePrivilege.getRopName(), rolePrivilege.getRopIndex(), rolePrivilege.getRopView(), rolePrivilege.getRopNew(), rolePrivilege.getRopEdit(), rolePrivilege.getRopDelete(), rolePrivilege.getRopBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<RolePrivilege> fetchAll()
	{
		String query = " select * from role_privilege ";
		return RefDBHelper.getDB().fetchList(query, RolePrivilegeImpl.class);
	}

	public List<RolePrivilege> fetchAuditRowsByPk(int id)
	{
		String query = " select * from role_privilege where rop_id=? order by rop_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, RolePrivilegeImpl.class);
	}

	public RolePrivilege fetchByPk(int ropId)
	{
		return fetchByPk(null, ropId);
	}

	public RolePrivilege fetchByPk(Connection connection, int ropId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from role_privilege where rop_id=? ", ropId, RolePrivilegeImpl.class);
	}

	public RolePrivilege fetchByBk(String ropBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from role_privilege where rop_bk=? ", ropBk, RolePrivilegeImpl.class);
	}

	public RolePrivilege fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from role_privilege where rop_name=? ", displayField, RolePrivilegeImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("rop_name", "role_privilege");
	}

}