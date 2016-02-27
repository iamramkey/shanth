/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.gen.dbo.PendingCcSettings;

public class PendingCcSettingsImpl extends PendingCcSettings
{
	public PendingCcSettingsImpl()
	{
	}

	public PendingCcSettingsImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

}