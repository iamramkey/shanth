/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dba;

import java.util.List;

import se.signa.signature.dbo.MasterSearchColumnImpl;
import se.signa.signature.gen.dba.MasterSearchColumnDba;
import se.signa.signature.gen.dbo.MasterSearchColumn;
import se.signa.signature.helpers.RefDBHelper;

public class MasterSearchColumnDbaImpl extends MasterSearchColumnDba
{

	public List<MasterSearchColumn> fetchByMse(Integer mseId)
	{
		String query = " select * from master_search_column where mse_id = ? order by mco_order_no ";
		return RefDBHelper.getDB().fetchList(query, mseId, MasterSearchColumnImpl.class);
	}

}