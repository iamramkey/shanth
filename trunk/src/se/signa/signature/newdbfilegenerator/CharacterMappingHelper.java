package se.signa.signature.newdbfilegenerator;

import java.util.HashMap;
import java.util.Map;

public class CharacterMappingHelper
{
	private Map<String, String> variableDeclarationMap;
	private Map<String, String> rsGetMethod;
	private Map<String, String> getGetMethod;
	Map<String, String> getDateTypeForCreate;

	public CharacterMappingHelper()
	{
		variableDeclarationMap = new HashMap<String, String>();
		variableDeclarationMap.put("int", "int");
		variableDeclarationMap.put("NUMBER", "Integer");
		variableDeclarationMap.put("NUMBER_FK", "Integer");
		variableDeclarationMap.put("VARCHAR2", "String");
		variableDeclarationMap.put("CHAR", "boolean");
		variableDeclarationMap.put("TIMESTAMP(6)", "DateTime");
		variableDeclarationMap.put("LONG", "Long");
		variableDeclarationMap.put("decimal", "double");

		rsGetMethod = new HashMap<String, String>();
		rsGetMethod.put("int", "rs.getInt(\"<>\")");
		rsGetMethod.put("NUMBER", "rs.getInt(\"<>\")");
		rsGetMethod.put("NUMBER_FK", "getInteger(rs.getInt(\"<>\"))");
		rsGetMethod.put("VARCHAR2", "rs.getString(\"<>\")");
		rsGetMethod.put("CHAR", "getBoolean(rs.getString(\"<>\"))");
		rsGetMethod.put("TIMESTAMP(6)", "getDateTime(rs.getTimestamp(\"<>\"))");
		rsGetMethod.put("LONG", "rs.getLong(\"<>\")");
		rsGetMethod.put("decimal", "rs.getDouble(\"<>\")");

		getGetMethod = new HashMap<String, String>();
		getGetMethod.put("int", "int");
		getGetMethod.put("NUMBER", "Integer");
		getGetMethod.put("NUMBER_FK", "Integer");
		getGetMethod.put("VARCHAR2", "String");
		getGetMethod.put("CHAR", "boolean");
		getGetMethod.put("TIMESTAMP(6)", "DateTime");
		getGetMethod.put("LONG", "Long");
		getGetMethod.put("decimal", "double");

		getDateTypeForCreate = new HashMap<String, String>();
		getDateTypeForCreate.put("int", "int");
		getDateTypeForCreate.put("NUMBER", "int");
		getDateTypeForCreate.put("NUMBER_FK", "int");
		getDateTypeForCreate.put("VARCHAR2", "String");
		getDateTypeForCreate.put("CHAR", "boolean");
		getDateTypeForCreate.put("TIMESTAMP(6)", "DateTime");
		getDateTypeForCreate.put("LONG", "Long");
		getDateTypeForCreate.put("decimal", "double");
	}

	public String getVariableDeclaration(String dataType)
	{
		return variableDeclarationMap.get(dataType);
	}

	public String getRsGetMethod(String dataType, String variableName)
	{
		String output = rsGetMethod.get(dataType);
		return output.replace("<>", variableName);
	}

	public String getGetSetMethod(String dataType)
	{
		return getGetMethod.get(dataType);
	}

	public String getDataTypeForCreate(String dataType)
	{
		return getDateTypeForCreate.get(dataType);
	}
}
