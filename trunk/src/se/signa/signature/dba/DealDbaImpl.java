/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dba;

import java.util.List;

import se.signa.signature.common.SignatureDbo;
import se.signa.signature.dbo.DealImpl;
import se.signa.signature.gen.dba.DealDba;
import se.signa.signature.gen.dbo.Deal;
import se.signa.signature.helpers.RefDBHelper;

public class DealDbaImpl extends DealDba
{
	public Deal fetchBydelId(int delId)
	{
		return RefDBHelper.getDB().fetchObjectIfExists(" select * from deal where del_id=? ", delId, DealImpl.class);
	}

	public Deal fetchBydelName(String delName)
	{
		return RefDBHelper.getDB().fetchObjectIfExists(" select * from deal where del_name= ? ", delName, DealImpl.class);
	}

	public List<Deal> fetchByWhereClause()
	{
		return RefDBHelper.getDB().fetchList(" select * from deal where del_to_dttm >=sysdate and  del_to_dttm <= sysdate +90 order by del_to_dttm asc  ", DealImpl.class);
	}

	@Override
	public List<? extends SignatureDbo> fetchAuditRowsByPk(int id)
	{
		return null;
	}

	@Override
	public SignatureDbo fetchByPk(int id)
	{
		return null;
	}

	@Override
	public SignatureDbo fetchByBk(String bk)
	{
		return null;
	}

	@Override
	public SignatureDbo fetchByDisplayField(String displayField)
	{
		return null;
	}

	@Override
	public List<String> getColumns()
	{
		return null;
	}

	@Override
	public List<? extends SignatureDbo> fetchAll()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List fetchStringList()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SignatureDbo createEmptyDbo()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(SignatureDbo dbo, int usrId)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(SignatureDbo dbo, int usrId)
	{
		// TODO Auto-generated method stub

	}
}