/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.gen.dbo.ReRaterSettings;

public class ReRaterSettingsImpl extends ReRaterSettings
{
	public ReRaterSettingsImpl()
	{
	}

	public ReRaterSettingsImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

}