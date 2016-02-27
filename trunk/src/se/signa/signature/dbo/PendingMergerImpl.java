/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.gen.dbo.PendingMerger;

public class PendingMergerImpl extends PendingMerger
{
	public PendingMergerImpl()
	{
	}

	public PendingMergerImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

}