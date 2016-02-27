/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.gen.dbo.FileArchiverSettings;

public class FileArchiverSettingsImpl extends FileArchiverSettings
{
	public FileArchiverSettingsImpl()
	{
	}

	public FileArchiverSettingsImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

}