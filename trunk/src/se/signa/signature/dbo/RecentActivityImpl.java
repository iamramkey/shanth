/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.gen.dbo.RecentActivity;

public class RecentActivityImpl extends RecentActivity
{
	public RecentActivityImpl()
	{
	}

	public RecentActivityImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

}