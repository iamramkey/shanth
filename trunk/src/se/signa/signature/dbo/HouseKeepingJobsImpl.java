/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.gen.dbo.HouseKeepingJobs;

public class HouseKeepingJobsImpl extends HouseKeepingJobs
{
	public HouseKeepingJobsImpl()
	{
	}

	public HouseKeepingJobsImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

}