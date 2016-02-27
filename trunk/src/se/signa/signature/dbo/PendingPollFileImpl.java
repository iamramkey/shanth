/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.gen.dbo.PendingPollFile;

public class PendingPollFileImpl extends PendingPollFile
{
	public PendingPollFileImpl()
	{
	}

	public PendingPollFileImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

}