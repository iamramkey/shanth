/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.gen.dbo.AuditFile;

public class AuditFileImpl extends AuditFile
{
	public AuditFileImpl()
	{
	}

	public AuditFileImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

}