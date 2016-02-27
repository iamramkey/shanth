package se.signa.signature.dio;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ViewAuditInput extends StigWebInput
{
	public int id;
	public String tableName;

	public void validate()
	{
		super.validate();
		mandate(id, "Primary Key", "id");
		mandate(tableName, "Table Name", "tableName");
	}

	public String camelCaseToUnderscore(String tableName)
	{
		String regex = "([a-z])([A-Z])";
		String replacement = "$1_$2";
		return tableName.replaceAll(regex, replacement).toLowerCase();
	}

}
