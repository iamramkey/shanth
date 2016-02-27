/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.gen.dbo.Alert;

public class AlertImpl extends Alert
{
	public AlertImpl()
	{
	}

	public AlertImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

}