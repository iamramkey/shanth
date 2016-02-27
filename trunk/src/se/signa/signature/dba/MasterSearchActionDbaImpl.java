/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dba;

import java.util.List;

import se.signa.signature.common.Constants.PermissionsActions;
import se.signa.signature.dbo.MasterSearchActionImpl;
import se.signa.signature.gen.dba.MasterSearchActionDba;
import se.signa.signature.gen.dbo.MasterSearchAction;
import se.signa.signature.helpers.RefDBHelper;

public class MasterSearchActionDbaImpl extends MasterSearchActionDba
{

	public List<MasterSearchAction> fetchRowByMse(int mseId)
	{
		String query = " select * from master_search_action where mse_id =? and msa_type = ?";
		return RefDBHelper.getDB().fetchList(query, new Object[] { mseId, PermissionsActions.ROW }, MasterSearchActionImpl.class);
	}

	public List<MasterSearchAction> fetchHeaderByMse(int mseId)
	{
		String query = " select * from master_search_action where mse_id =? and msa_type = ?";
		return RefDBHelper.getDB().fetchList(query, new Object[] { mseId, PermissionsActions.HEADER }, MasterSearchActionImpl.class);
	}

}