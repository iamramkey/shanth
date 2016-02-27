/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import se.signa.signature.dio.MasterSaveInput;
import se.signa.signature.gen.dbo.Deal;

public class DealImpl extends Deal
{
	public DealImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

	@Override
	public String getDisplayField()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPk()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPk(int pk)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public String getBk()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> toStringList()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> toAuditStringList()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getProperty(String propertyName)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public Map<String, Object> populateTo()
	{
		// TODO Auto-generated method stub
		return null;
	}

}