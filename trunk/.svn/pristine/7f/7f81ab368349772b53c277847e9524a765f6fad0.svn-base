/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dba;

import java.util.List;

import se.signa.signature.gen.dba.RoleDba;
import se.signa.signature.helpers.RefDBHelper;

public class RoleDbaImpl extends RoleDba
{
	@Override
	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("rol_name", "role", "rol_id > 1");
	}
}