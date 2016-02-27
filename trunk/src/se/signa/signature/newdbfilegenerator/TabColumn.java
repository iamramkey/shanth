package se.signa.signature.newdbfilegenerator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import se.signa.signature.common.SignatureDbo;
import se.signa.signature.common.SignatureException;
import se.signa.signature.dio.MasterSaveInput;

public class TabColumn extends SignatureDbo
{

	public String columnName;
	public String dataType;
	public String nullable;

	public TabColumn(ResultSet rs) throws SQLException
	{
		columnName = rs.getString("COLUMN_NAME");
		dataType = rs.getString("DATA_TYPE");
		nullable = rs.getString("NULLABLE");
	}

	@Override
	public String getDisplayField()
	{
		return columnName;
	}

	@Override
	public int getPk()
	{
		throw new SignatureException("Not Implemented");
	}

	@Override
	public void setPk(int pk)
	{
		throw new SignatureException("Not Implemented");
	}

	@Override
	public String getBk()
	{
		throw new SignatureException("Not Implemented");
	}

	@Override
	public List<String> toStringList()
	{
		throw new SignatureException("Not Implemented");
	}

	@Override
	public List<String> toAuditStringList()
	{
		throw new SignatureException("Not Implemented");
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
		// TODO Auto-generated method stub

	}

}