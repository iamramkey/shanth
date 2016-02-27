/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.gen.dbo.UserRolePermission;

public class UserRolePermissionImpl extends UserRolePermission
{
	public UserRolePermissionImpl()
	{
	}

	public UserRolePermissionImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

}