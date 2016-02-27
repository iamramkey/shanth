/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.gen.dbo.FileParserSettings;

public class FileParserSettingsImpl extends FileParserSettings
{
	public FileParserSettingsImpl()
	{
	}

	public FileParserSettingsImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

}