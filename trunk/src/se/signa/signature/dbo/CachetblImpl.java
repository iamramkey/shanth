/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.gen.dbo.Cachetbl;

public class CachetblImpl extends Cachetbl
{
	public CachetblImpl()
	{
	}

	public CachetblImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

}