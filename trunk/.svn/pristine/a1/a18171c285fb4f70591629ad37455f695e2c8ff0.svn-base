/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dba;

import java.util.List;

import se.signa.signature.dbo.MasterSearchFormImpl;
import se.signa.signature.gen.dba.MasterSearchFormDba;
import se.signa.signature.gen.dbo.MasterSearchForm;
import se.signa.signature.helpers.RefDBHelper;

public class MasterSearchFormDbaImpl extends MasterSearchFormDba
{

	public List<MasterSearchForm> fetchByMse(Integer mseId)
	{
		String query = " select * from master_search_form where mse_id =? ";
		return RefDBHelper.getDB().fetchList(query, mseId, MasterSearchFormImpl.class);
	}

}