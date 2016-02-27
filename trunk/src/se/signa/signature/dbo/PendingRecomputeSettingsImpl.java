/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.gen.dbo.PendingRecomputeSettings;

public class PendingRecomputeSettingsImpl extends PendingRecomputeSettings
{
	public PendingRecomputeSettingsImpl()
	{
	}

	public PendingRecomputeSettingsImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

}