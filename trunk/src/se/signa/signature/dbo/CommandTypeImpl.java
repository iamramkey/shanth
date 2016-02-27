/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.gen.dbo.CommandType;

public class CommandTypeImpl extends CommandType
{
	public CommandTypeImpl()
	{
	}

	public CommandTypeImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

}