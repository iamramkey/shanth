package se.signa.signature.dba;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import se.signa.signature.common.SignatureDbo;
import se.signa.signature.common.SignatureException;
import se.signa.signature.dio.MasterSaveInput;

public class JobMonitorQueryModel extends SignatureDbo
{
	public int count;
	public String jbtName;
	public String jobStatus;

	public JobMonitorQueryModel(ResultSet rs)
	{
		try
		{
			count = rs.getInt("cnt");
			jbtName = rs.getString("jbt_name");
			jobStatus = rs.getString("job_status");
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}

	}

	@Override
	public String getDisplayField()
	{
		return jbtName;
	}

	@Override
	public int getPk()
	{
		return count;
	}

	@Override
	public void setPk(int pk)
	{
		throw new SignatureException("Not implemented");
	}

	@Override
	public String getBk()
	{
		return jbtName;
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