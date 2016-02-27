/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.gen.dbo.Command;

public class CommandImpl extends Command
{
	public CommandImpl()
	{
	}

	public CommandImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

	public CommandImpl(int nodId, int ctpId, String cmdName)
	{
		setNodId(nodId);
		setCtpId(ctpId);
		setCmdName(cmdName);
	}

}