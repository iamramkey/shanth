/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.gen.dbo.PendingRaterFile;

public class PendingRaterFileImpl extends PendingRaterFile
{
	public PendingRaterFileImpl()
	{
	}

	public PendingRaterFileImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

}