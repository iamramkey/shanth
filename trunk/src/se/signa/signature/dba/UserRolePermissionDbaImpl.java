/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dba;

import se.signa.signature.gen.dba.UserRolePermissionDba;
import se.signa.signature.helpers.RefDBHelper;

public class UserRolePermissionDbaImpl extends UserRolePermissionDba
{

	public int fetchUserRolePermission(Integer rolId, String entity, String action)
	{
		return RefDBHelper.getDB().fetchCount("from user_role_permission where rol_id = ? and upe_entity = ? and upe_action = ?", new Object[] { rolId, entity, action });
	}
}