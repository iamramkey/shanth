/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.gen.dbo.Filetbl;

public class FiletblImpl extends Filetbl
{
	public FiletblImpl()
	{
	}

	public FiletblImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

}