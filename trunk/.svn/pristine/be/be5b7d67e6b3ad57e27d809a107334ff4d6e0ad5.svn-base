/**
 * Copyright SIGNA AB, STOCKHOLM, SWEDEN
 */

package se.signa.signature.helpers.nextid;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import se.signa.signature.common.SignatureDbo;
import se.signa.signature.common.SignatureException;
import se.signa.signature.dio.MasterSaveInput;

public class NextId extends SignatureDbo
{
	private String neiName;
	private int neiNo;

	public NextId(ResultSet rs)
	{
		try
		{
			neiName = rs.getString("nei_name");
			neiNo = rs.getInt("nei_no");
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	//---------------------------------------

	public String getNeiName()
	{
		return neiName;
	}

	public void setNeiName(String neiName)
	{
		this.neiName = neiName;
	}

	public int getNeiNo()
	{
		return neiNo;
	}

	public void setNeiNo(int neiNo)
	{
		this.neiNo = neiNo;
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
	public Map<String, Object> populateTo()
	{
		throw new SignatureException("Not Implemented");
	}

	@Override
	public Object getProperty(String propertyName)
	{
		throw new SignatureException("Not Implemented");
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		throw new SignatureException("Not Implemented");
	}

}
