/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.gen.dbo.MasterSearchAction;

public class MasterSearchActionImpl extends MasterSearchAction
{
	public MasterSearchActionImpl()
	{
	}

	public MasterSearchActionImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

}