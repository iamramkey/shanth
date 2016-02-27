/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.gen.dbo.ExtractionPollerSettings;

public class ExtractionPollerSettingsImpl extends ExtractionPollerSettings
{
	public ExtractionPollerSettingsImpl()
	{
	}

	public ExtractionPollerSettingsImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

}