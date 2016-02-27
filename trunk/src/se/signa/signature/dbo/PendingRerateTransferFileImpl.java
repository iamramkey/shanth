/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.gen.dbo.PendingRerateTransferFile;

public class PendingRerateTransferFileImpl extends PendingRerateTransferFile
{
	public PendingRerateTransferFileImpl()
	{
	}

	public PendingRerateTransferFileImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

}