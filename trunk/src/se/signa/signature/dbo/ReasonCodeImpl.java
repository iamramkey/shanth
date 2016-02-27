/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.gen.dbo.ReasonCode;

public class ReasonCodeImpl extends ReasonCode
{
	public ReasonCodeImpl()
	{
	}

	public ReasonCodeImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

}