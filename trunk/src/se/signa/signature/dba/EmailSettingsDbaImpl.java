/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dba;

import se.signa.signature.gen.dba.EmailSettingsDba;
import se.signa.signature.gen.dbo.EmailSettings;

public class EmailSettingsDbaImpl extends EmailSettingsDba
{
	public EmailSettings getEmailSettings(int emailSettingsId)
	{
		return fetchByPk(emailSettingsId);
	}

}