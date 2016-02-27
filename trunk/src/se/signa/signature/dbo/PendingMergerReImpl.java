/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.gen.dbo.PendingMergerRe;

public class PendingMergerReImpl extends PendingMergerRe
{
	public PendingMergerReImpl()
	{
	}

	public PendingMergerReImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

}