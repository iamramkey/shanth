/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.gen.dbo.PendingBixOutputFile;

public class PendingBixOutputFileImpl extends PendingBixOutputFile
{
	public PendingBixOutputFileImpl()
	{
	}

	public PendingBixOutputFileImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

}