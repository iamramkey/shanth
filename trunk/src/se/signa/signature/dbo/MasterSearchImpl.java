/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.gen.dbo.MasterSearch;

public class MasterSearchImpl extends MasterSearch
{
	public MasterSearchImpl()
	{
	}

	public MasterSearchImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

}