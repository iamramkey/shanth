/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.gen.dbo.PendingRaterFileSettings;

public class PendingRaterFileSettingsImpl extends PendingRaterFileSettings
{
	public PendingRaterFileSettingsImpl()
	{
	}

	public PendingRaterFileSettingsImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

}