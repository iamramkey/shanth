/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.gen.dbo.PendingRecompute;

public class PendingRecomputeImpl extends PendingRecompute
{
	public PendingRecomputeImpl()
	{
	}

	public PendingRecomputeImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

}