/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.gen.dbo.MasterSearchColumn;

public class MasterSearchColumnImpl extends MasterSearchColumn
{
	public MasterSearchColumnImpl()
	{
	}

	public MasterSearchColumnImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

}