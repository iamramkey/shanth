/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dba;

import se.signa.signature.dbo.ServerSettingsImpl;
import se.signa.signature.gen.dba.ServerSettingsDba;
import se.signa.signature.gen.dbo.ServerSettings;
import se.signa.signature.helpers.RefDBHelper;

public class ServerSettingsDbaImpl extends ServerSettingsDba
{

	public ServerSettings fetchBySesCode(String sesCode)
	{
		return RefDBHelper.getDB().fetchObject(" select * from server_settings where ses_code = ? ", sesCode, ServerSettingsImpl.class);
	}

}