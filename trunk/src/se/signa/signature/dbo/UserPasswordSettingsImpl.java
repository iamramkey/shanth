/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.gen.dbo.UserPasswordSettings;

public class UserPasswordSettingsImpl extends UserPasswordSettings
{
	public UserPasswordSettingsImpl()
	{
	}

	public UserPasswordSettingsImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

}