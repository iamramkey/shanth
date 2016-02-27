/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.gen.dbo.EmailSettings;

public class EmailSettingsImpl extends EmailSettings
{
	public EmailSettingsImpl()
	{
	}

	public EmailSettingsImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

}