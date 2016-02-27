package se.signa.signature.dio;

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import org.joda.time.DateTime;

@XmlRootElement
public class MasterSaveInput extends StigWebInput
{
	public String name;
	public Map<String, Object> data;
	public int id;

	public void validate()
	{
		super.validate();
		super.mandate("data", "Data", "data");
		super.mandate(name, "Name", "name");
	}

	public String getString(String dataKey)
	{
		Object raw = data.get(dataKey);
		if (raw == null)
			return null;

		//TODO: a regex check
		return (String) raw;
	}

	//	public Integer getFk(Class dbaClazz, String dataKey)
	//	{
	//		Object raw = data.get(dataKey);
	//		if (raw == null)
	//			return null;
	//	
	//		String displayField = (String) raw;
	//		return DisplayFieldHelper.getI().getPk(dbaClazz, displayField);
	//	}

	public Double getDouble(String dataKey)
	{
		Object raw = data.get(dataKey);
		if (raw == null)
			return null;

		//TODO: a regex check
		return Double.parseDouble((String) raw);
	}

	public boolean getBoolean(String dataKey)
	{
		Object raw = data.get(dataKey);
		if (raw == null)
			return false;

		//TODO: a regex check
		return Boolean.parseBoolean((String) raw);
	}

	public DateTime getDate(String dataKey)
	{
		Object raw = data.get(dataKey);
		if (raw == null)
			return null;
		return getDate((String) raw, dataKey, dataKey);
	}

	public Integer getInteger(String dataKey)
	{
		Object raw = data.get(dataKey);
		if (raw == null)
			return null;

		//TODO: a regex check
		return Integer.parseInt((String) raw);
	}

	public Long getLong(String dataKey)
	{
		Object raw = data.get(dataKey);
		if (raw == null)
			return null;

		//TODO: a regex check
		return Long.parseLong((String) raw);
	}

}
