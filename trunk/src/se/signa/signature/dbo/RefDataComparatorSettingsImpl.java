/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.gen.dbo.RefDataComparatorSettings;

public class RefDataComparatorSettingsImpl extends RefDataComparatorSettings
{
	public RefDataComparatorSettingsImpl()
	{
	}

	public RefDataComparatorSettingsImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

}