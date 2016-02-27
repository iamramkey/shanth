/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.gen.dbo.PendingCorrelation;

public class PendingCorrelationImpl extends PendingCorrelation
{
	public PendingCorrelationImpl()
	{
	}

	public PendingCorrelationImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

}