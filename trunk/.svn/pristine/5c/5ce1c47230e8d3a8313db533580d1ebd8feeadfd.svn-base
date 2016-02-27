/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dba;

import se.signa.signature.dbo.MasterSearchImpl;
import se.signa.signature.gen.dba.MasterSearchDba;
import se.signa.signature.gen.dbo.MasterSearch;
import se.signa.signature.helpers.RefDBHelper;

public class MasterSearchDbaImpl extends MasterSearchDba
{

	public MasterSearch fetchByUrl(String url)
	{
		return RefDBHelper.getDB().fetchObjectIfExists(" select * from master_search where mse_url=? ", url, MasterSearchImpl.class);
	}

}