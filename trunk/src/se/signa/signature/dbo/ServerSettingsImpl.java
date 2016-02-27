/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.gen.dbo.ServerSettings;

public class ServerSettingsImpl extends ServerSettings
{
	public ServerSettingsImpl()
	{
	}

	public ServerSettingsImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

}