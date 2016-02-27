/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.gen.dbo.PendingCrSettings;

public class PendingCrSettingsImpl extends PendingCrSettings
{
	public PendingCrSettingsImpl()
	{
	}

	public PendingCrSettingsImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

}