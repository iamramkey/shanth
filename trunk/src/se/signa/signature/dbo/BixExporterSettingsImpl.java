/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.gen.dbo.BixExporterSettings;

public class BixExporterSettingsImpl extends BixExporterSettings
{
	public BixExporterSettingsImpl()
	{
	}

	public BixExporterSettingsImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

}