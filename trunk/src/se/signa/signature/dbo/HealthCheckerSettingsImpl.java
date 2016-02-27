/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.gen.dbo.HealthCheckerSettings;

public class HealthCheckerSettingsImpl extends HealthCheckerSettings
{
	public HealthCheckerSettingsImpl()
	{
	}

	public HealthCheckerSettingsImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

}