/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.gen.dbo.Job;

public class JobImpl extends Job
{
	public JobImpl()
	{
	}

	public JobImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

}