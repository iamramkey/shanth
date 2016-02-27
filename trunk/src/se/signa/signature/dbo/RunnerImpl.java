/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.gen.dbo.Runner;

public class RunnerImpl extends Runner
{
	public RunnerImpl()
	{
	}

	public RunnerImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

}