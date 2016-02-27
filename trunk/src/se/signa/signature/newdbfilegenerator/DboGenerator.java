package se.signa.signature.newdbfilegenerator;

import static se.signa.signature.newdbfilegenerator.GeneratorConstants.DBOCLASS;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.DISPLAYFIELDHELPER;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.EXCEPTIONCLASS;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PACKAGE;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PACKAGE_GEN_JAVA_UTIL_ARRAYLIST;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PACKAGE_GEN_JAVA_UTIL_HASHMAP;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PACKAGE_GEN_JAVA_UTIL_LIST;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PACKAGE_GEN_JAVA_UTIL_MAP;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PROJECT_BASEPATH;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PROJECT_GEN_DBOFOLDERPATH;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PROJECT_IMPL_DBA_PACKAGE;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PROJECT_IMPL_DBO_FILE_EXTENSION;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PROJECT_IMPL_DBO_PACKAGE;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PROJECT_IMPL_GEN_PACKAGE;
import static se.signa.signature.newdbfilegenerator.StringHelper.underScoreToCamelCase;
import static se.signa.signature.newdbfilegenerator.StringHelper.underScoreToCamelCaseVariableName;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class DboGenerator
{

	private static Logger logger = Logger.getLogger(DboGenerator.class);

	public static void generateDbo(String tableName, String prefix, List<TabColumn> columns, Map<String, String> tablesMap, String displayField) throws IOException
	{
		File genDboFolder = new File(PROJECT_BASEPATH + PROJECT_GEN_DBOFOLDERPATH);
		String genDboPackage = PACKAGE + PROJECT_IMPL_GEN_PACKAGE + PROJECT_IMPL_DBO_PACKAGE;
		File genDbo = new File(genDboFolder, underScoreToCamelCase(tableName) + PROJECT_IMPL_DBO_FILE_EXTENSION);

		if (genDbo.exists())
			genDbo.delete();
		genDbo.createNewFile();

		PrintWriter writer = new PrintWriter(genDbo, "UTF-8");
		CharacterMappingHelper cmh = new CharacterMappingHelper();
		writeCopyright(writer);
		writeImportAndPackage(prefix, tableName, genDboPackage, columns, tablesMap, writer);
		writeClassBegin(tableName, writer);
		writeVariableInitialization(columns, cmh, writer);
		writeConstructor(writer);
		writeTry(writer);
		writeVariableDefinition(columns, cmh, writer);
		writeTryEnd(writer);
		writeCatch(writer);
		writePkMethod(prefix, writer);
		writeSetPkMethod(prefix, writer);
		writeBkMethod(prefix, writer);
		writeToStringList(prefix, columns, writer);
		writePopulteTo(prefix, columns, writer);
		writePopulteFrom(prefix, columns, tablesMap, writer);
		writeToAuditStringList(prefix, columns, writer);
		writeDisplayFieldMethod(prefix, displayField, writer);
		writeRelations(prefix, columns, tablesMap, writer);
		writeGetProperty(prefix, tablesMap, columns, cmh, writer);
		writeGetAndSetMethod(columns, cmh, writer);
		writeClassEnd(writer);

		writer.close();
		logger.debug(underScoreToCamelCase(tableName) + PROJECT_IMPL_DBO_FILE_EXTENSION + " File is created");

	}

	private static void writeCopyright(PrintWriter writer)
	{
		writer.println("/**");
		writer.println("* Copyright SIGNA AB, STOCKHOLM, SWEDEN");
		writer.println("*/");
		writer.println();
	}

	private static void writeImportAndPackage(String prefix, String tableName, String genDboPackage, List<TabColumn> columns, Map<String, String> tablesMap, PrintWriter writer) throws IOException
	{
		writeLine("package " + genDboPackage + ";", writer);
		writeLine("", writer);
		writeLine("import java.sql.ResultSet;", writer);
		writeLine("import java.sql.SQLException;", writer);
		writeLine("import " + PACKAGE_GEN_JAVA_UTIL_ARRAYLIST + ";", writer);
		writeLine("import " + PACKAGE_GEN_JAVA_UTIL_HASHMAP + ";", writer);
		writeLine("import " + PACKAGE_GEN_JAVA_UTIL_LIST + ";", writer);
		writeLine("import " + PACKAGE_GEN_JAVA_UTIL_MAP + ";", writer);

		writeLine("", writer);
		writeLine("import org.codehaus.jackson.map.annotate.JsonSerialize;", writer);
		writeLine("import org.joda.time.DateTime;", writer);
		writeLine("", writer);

		writeLine("import " + PACKAGE + ".common.Constants;", writer);
		writeLine("import " + PACKAGE + ".common." + "CustomDateSerializer" + ";", writer);
		writeLine("import " + PACKAGE + ".common." + DBOCLASS + ";", writer);
		writeLine("import " + PACKAGE + ".common." + EXCEPTIONCLASS + ";", writer);
		writeLine("import " + PACKAGE + ".dio.MasterSaveInput;", writer);

		writeImportsForRelations(prefix, tableName, genDboPackage, columns, tablesMap, writer);
		writeLine("import " + PACKAGE + ".helpers." + DISPLAYFIELDHELPER + ";", writer);
		writeLine("", writer);

		writeLine("", writer);

	}

	private static void writeImportsForRelations(String currentPrefix, String tableName, String genDboPackage, List<TabColumn> columns, Map<String, String> tablesMap, PrintWriter writer) throws IOException
	{
		List<String> dbaClassImportNames = new ArrayList<String>();
		for (TabColumn tabColumn : columns)
		{
			if (!tabColumn.dataType.equals("NUMBER_FK"))
				continue;
			if (!tabColumn.columnName.endsWith("_id"))
				continue;

			String prefix = tabColumn.columnName.replace("_id", "");
			if (prefix.length() <= 2)
				continue;

			if (prefix.equalsIgnoreCase(currentPrefix))
				continue;

			String shortPrefix = prefix.substring(prefix.length() - 3, prefix.length());
			String dbaClassNameBeforeConversion = tablesMap.get(shortPrefix);
			if (dbaClassNameBeforeConversion == null)
				continue;

			String dbaClassName = underScoreToCamelCase(dbaClassNameBeforeConversion);
			if (dbaClassImportNames.contains(dbaClassName))
				continue;

			writeLine("import " + PACKAGE + PROJECT_IMPL_GEN_PACKAGE + PROJECT_IMPL_DBA_PACKAGE + "." + dbaClassName + "Dba;", writer);
			dbaClassImportNames.add(dbaClassName);

		}

	}

	private static void writeClassBegin(String tableName, PrintWriter writer) throws IOException
	{
		String className = StringHelper.underScoreToCamelCase(tableName);
		writeLine("public abstract class " + className + " extends " + DBOCLASS, writer);
		writeLine("{", writer);
	}

	private static void writeVariableInitialization(List<TabColumn> columns, CharacterMappingHelper cmh, PrintWriter writer) throws IOException
	{
		for (TabColumn column : columns)
		{
			String data = column.dataType;

			if (column.nullable.equalsIgnoreCase("YES") && data.equalsIgnoreCase("int"))
				data = "integer";

			if (column.dataType.startsWith("TIMESTAMP"))
				writeLine("\t@JsonSerialize(using = CustomDateSerializer.class)", writer);

			writeLine("\tprivate " + cmh.getVariableDeclaration(data) + " " + StringHelper.underScoreToCamelCaseVariableName(column.columnName) + ";", writer);
		}
		writeLine("", writer);
	}

	private static void writeConstructor(PrintWriter writer) throws IOException
	{
		writeLine("\tprotected void populateFromResultSet(ResultSet rs)", writer);
		writeLine("\t{", writer);
	}

	private static void writeTry(PrintWriter writer) throws IOException
	{
		writeLine("\t\ttry", writer);
		writeLine("\t\t{", writer);
	}

	private static void writeVariableDefinition(List<TabColumn> columns, CharacterMappingHelper cmh, PrintWriter writer) throws IOException
	{
		for (TabColumn column : columns)
		{
			String lhs = "\t\t\t" + StringHelper.underScoreToCamelCaseVariableName(column.columnName) + " = ";

			String rhs = cmh.getRsGetMethod(column.dataType.trim(), column.columnName) + ";";
			writeLine(lhs + rhs, writer);
		}

	}

	private static void writeTryEnd(PrintWriter writer) throws IOException
	{
		writeLine("\t\t}", writer);
	}

	private static void writeCatch(PrintWriter writer) throws IOException
	{
		writeLine("\t\tcatch (SQLException e)", writer);
		writeLine("\t\t{", writer);
		writeLine("\t\t\tthrow new " + EXCEPTIONCLASS + "(e);", writer);
		writeLine("\t\t}", writer);
		writeLine("\t}", writer);
		writeLine("", writer);
	}

	private static void writePkMethod(String prefix, PrintWriter writer) throws IOException
	{
		writeLine("\tpublic int getPk()", writer);
		writeLine("\t{", writer);
		writeLine("\t\treturn " + underScoreToCamelCaseVariableName(prefix) + "Id;", writer);
		writeLine("\t}", writer);
		writeLine("", writer);
	}

	private static void writeSetPkMethod(String prefix, PrintWriter writer) throws IOException
	{
		writeLine("\tpublic void setPk(int pk)", writer);
		writeLine("\t{", writer);
		writeLine("\t\t " + underScoreToCamelCaseVariableName(prefix) + "Id = pk;", writer);
		writeLine("\t}", writer);
		writeLine("", writer);

	}

	private static void writeBkMethod(String prefix, PrintWriter writer) throws IOException
	{
		writeLine("\tpublic String getBk()", writer);
		writeLine("\t{", writer);
		writeLine("\t\treturn " + underScoreToCamelCaseVariableName(prefix) + "Bk;", writer);
		writeLine("\t}", writer);
		writeLine("", writer);
	}

	private static void writeDisplayFieldMethod(String prefix, String displayField, PrintWriter writer) throws IOException
	{
		writeLine("\tpublic String getDisplayField()", writer);
		writeLine("\t{", writer);
		writeLine("\t\treturn " + underScoreToCamelCaseVariableName(displayField) + ";", writer);
		writeLine("\t}", writer);
		writeLine("", writer);
	}

	private static void writeToStringList(String prefix, List<TabColumn> columns, PrintWriter writer) throws IOException
	{
		writeLine("\tpublic List<String> toStringList()", writer);
		writeLine("\t{", writer);
		writeLine("\t\tList<String> data = new ArrayList<String>();", writer);
		for (int i = 0; i < columns.size(); i++)
		{
			TabColumn column = columns.get(i);
			if (column.dataType.equals("TIMESTAMP(6)") && !column.columnName.endsWith("_modified_dttm"))
			{
				if (column.nullable.equalsIgnoreCase("Y"))
					writeLine("\t\tdata.add(" + underScoreToCamelCaseVariableName(column.columnName) + " == null ? Constants.NULLSTRING : Constants.dttf.print(" + underScoreToCamelCaseVariableName(column.columnName) + "));", writer);
				else
					writeLine("\t\tdata.add(Constants.dttf.print(" + underScoreToCamelCaseVariableName(column.columnName) + "));", writer);
			}
			else if (column.nullable.equalsIgnoreCase("Y") && !column.columnName.endsWith("_bk") && !column.columnName.endsWith("_modified_usr_id") && !column.columnName.endsWith("_modified_dttm") && !column.columnName.endsWith("_created_dttm") && !column.columnName.endsWith("_created_usr_id"))
				writeLine("\t\tdata.add(" + underScoreToCamelCaseVariableName(column.columnName) + " == null ? Constants.NULLSTRING : " + underScoreToCamelCaseVariableName(column.columnName) + ".toString());", writer);
			else if (column.dataType.equalsIgnoreCase("CHAR") && !column.columnName.endsWith("_bk") && !column.columnName.endsWith("_modified_usr_id") && !column.columnName.endsWith("_modified_dttm") && !column.columnName.endsWith("_created_dttm") && !column.columnName.endsWith("_created_usr_id"))
				writeLine("\t\tdata.add(String.valueOf(" + underScoreToCamelCaseVariableName(column.columnName) + "));", writer);
			else if (!column.columnName.endsWith("_bk") && !column.columnName.endsWith("_modified_usr_id") && !column.columnName.endsWith("_modified_dttm") && !column.columnName.endsWith("_created_dttm") && !column.columnName.endsWith("_created_usr_id"))
				writeLine("\t\tdata.add(" + underScoreToCamelCaseVariableName(column.columnName) + ".toString());", writer);

			if (column.columnName.endsWith("_created_usr_id"))
				writeLine("\t\tdata.add(get" + StringHelper.underScoreToCamelCase(prefix) + "CreatedUsr());", writer);

		}
		writeLine("\t\treturn data;", writer);
		writeLine("\t}", writer);
		writeLine("", writer);
	}

	private static void writePopulteTo(String prefix, List<TabColumn> columns, PrintWriter writer) throws IOException
	{
		writeLine("\t@Override", writer);
		writeLine("\tpublic Map<String, Object> populateTo()", writer);
		writeLine("\t{", writer);
		writeLine("\t\tMap<String, Object> data = new HashMap<String, Object>();", writer);
		writeLine("\t\tdata.put(\"id\", getPk());", writer);
		for (int i = 1; i < columns.size() - 5; i++)
		{
			TabColumn column = columns.get(i);
			if (column.dataType.equals("NUMBER_FK") && column.columnName.endsWith("_id"))
			{
				String fkprefix = column.columnName.replace("_id", "");
				if (fkprefix.equalsIgnoreCase("acc"))
					writeLine("\t\tdata.put(\"accId\", getAccId());", writer);
				else if (fkprefix.equalsIgnoreCase("del"))
					writeLine("\t\tdata.put(\"delId\", getDelId());", writer);
				else if (fkprefix.length() > 2 && !fkprefix.equalsIgnoreCase(prefix))
					writeLine("\t\tdata.put(\"" + underScoreToCamelCaseVariableName(fkprefix) + "\", get" + underScoreToCamelCase(fkprefix) + "());", writer);
				else
					writeLine("\t\tdata.put(\"+prefix+\", dono);", writer);
			}
			//			else if (column.dataType.equals("CHAR"))
			//				writeLine("\t\tdata.put(\"" + column.columnName + "\", String.valueOf(" + underScoreToCamelCaseVariableName(column.columnName) + "));", writer);
			else if (column.dataType.equals("TIMESTAMP(6)"))
				writeLine("\t\tdata.put(\"" + underScoreToCamelCaseVariableName(column.columnName) + "\", Constants.dttf.print(" + underScoreToCamelCaseVariableName(column.columnName) + "));", writer);
			else
				writeLine("\t\tdata.put(\"" + underScoreToCamelCaseVariableName(column.columnName) + "\", getString(" + underScoreToCamelCaseVariableName(column.columnName) + "));", writer);

		}
		writeLine("\t\treturn data;", writer);
		writeLine("\t}", writer);
		writeLine("", writer);
	}

	private static void writePopulteFrom(String prefix, List<TabColumn> columns, Map<String, String> tablesMap, PrintWriter writer) throws IOException
	{
		if (prefix.equalsIgnoreCase("auf"))
			System.out.println("debug");
		writeLine("\t@Override", writer);
		writeLine("\tpublic void populateFrom(MasterSaveInput input)", writer);
		writeLine("\t{", writer);
		writeLine("\t\tsetPk(input.id);", writer);
		for (int i = 1; i < columns.size() - 5; i++)
		{
			TabColumn column = columns.get(i);
			if (column.dataType.equals("NUMBER_FK") && column.columnName.endsWith("_id"))
			{
				String fkprefix = column.columnName.replace("_id", "");
				if (fkprefix.length() > 2 && !fkprefix.equalsIgnoreCase(prefix))
				{

					String shortPrefix = fkprefix.substring(fkprefix.length() - 3, fkprefix.length());

					if (tablesMap.get(shortPrefix) != null)
					{
						String getPkLine = "DisplayFieldHelper.getI().getPk(";
						getPkLine += underScoreToCamelCase(tablesMap.get(shortPrefix)) + "Dba.class, ";
						String colName = underScoreToCamelCaseVariableName(column.columnName);
						getPkLine += "input.getString(\"" + colName.substring(0, colName.length() - 2) + "\"))";

						writeLine("\t\tset" + underScoreToCamelCase(fkprefix) + "Id(" + getPkLine + ");", writer);

					}

				}
				else
				{
					if (tablesMap.get(fkprefix) != null)
					{
						String getPkLine = "DisplayFieldHelper.getI().getPk(";
						getPkLine += underScoreToCamelCase(tablesMap.get(fkprefix)) + "Dba.class, ";
						String colName = underScoreToCamelCaseVariableName(column.columnName);
						getPkLine += "input.getString(\"" + colName.substring(0, colName.length() - 2) + "\"))";

						writeLine("\t\tset" + underScoreToCamelCase(fkprefix) + "Id(" + getPkLine + ");", writer);
					}

				}
			}
			else if (column.dataType.equals("NUMBER"))
				writeLine("\t\tset" + underScoreToCamelCase(column.columnName) + "(input.getInteger(\"" + underScoreToCamelCaseVariableName(column.columnName) + "\"));", writer);
			else if (column.dataType.equals("TIMESTAMP(6)"))
				writeLine("\t\tset" + underScoreToCamelCase(column.columnName) + "(input.getDate(\"" + underScoreToCamelCaseVariableName(column.columnName) + "\"));", writer);
			else if (column.dataType.equals("CHAR"))
				writeLine("\t\tset" + underScoreToCamelCase(column.columnName) + "(input.getBoolean(\"" + underScoreToCamelCaseVariableName(column.columnName) + "\"));", writer);
			else if (column.dataType.equals("LONG"))
				writeLine("\t\tset" + underScoreToCamelCase(column.columnName) + "(input.getLong(\"" + underScoreToCamelCaseVariableName(column.columnName) + "\"));", writer);
			else
				writeLine("\t\tset" + underScoreToCamelCase(column.columnName) + "(input.getString(\"" + underScoreToCamelCaseVariableName(column.columnName) + "\"));", writer);

		}
		writeLine("\t}", writer);
		writeLine("", writer);
	}

	private static void writeToAuditStringList(String prefix, List<TabColumn> columns, PrintWriter writer) throws IOException
	{

		writeLine("\tpublic List<String> toAuditStringList()", writer);
		writeLine("\t{", writer);
		writeLine("\t\tList<String> auditData = new ArrayList<String>();", writer);

		writeLine("\t\tauditData.add(" + prefix + "Bk.toString());", writer);
		writeLine("\t\tauditData.add(get" + StringHelper.underScoreToCamelCase(prefix) + "CreatedUsr());", writer);
		writeLine("\t\tauditData.add(" + prefix + "ModifiedUsrId == null ? Constants.NULLSTRING : get" + StringHelper.underScoreToCamelCase(prefix) + "ModifiedUsr());", writer);
		writeLine("\t\tauditData.add(Constants.dttf.print(" + prefix + "CreatedDttm));", writer);
		writeLine("\t\tauditData.add(" + prefix + "ModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(" + prefix + "ModifiedDttm));", writer);

		writeLine("\t\treturn auditData;", writer);
		writeLine("\t}", writer);
		writeLine("", writer);
	}

	private static void writeRelations(String currentPrefix, List<TabColumn> columns, Map<String, String> tablesMap, PrintWriter writer) throws IOException
	{
		if (currentPrefix.equals("etr"))
			System.out.println("watchme");
		for (TabColumn tabColumn : columns)
		{
			if (!tabColumn.dataType.equals("NUMBER_FK"))
				continue;
			if (!tabColumn.columnName.endsWith("_id"))
				continue;

			String prefix = tabColumn.columnName.replace("_id", "");
			if (prefix.length() <= 2)
				continue;

			if (prefix.equalsIgnoreCase(currentPrefix))
				continue;
			String shortPrefix = prefix.substring(prefix.length() - 3, prefix.length());

			if (tablesMap.get(shortPrefix) == null)
				continue;

			writeLine("\tpublic String get" + underScoreToCamelCase(prefix) + "()", writer);
			writeLine("\t{", writer);
			String returnLine = "\t\treturn DisplayFieldHelper.getI().getDisplayField(";
			returnLine += underScoreToCamelCase(tablesMap.get(shortPrefix)) + "Dba.class, ";
			returnLine += underScoreToCamelCaseVariableName(prefix) + "Id);";
			writeLine(returnLine, writer);
			writeLine("\t}", writer);
			writeLine("", writer);

			//			writeLine("\tpublic void set" + underScoreToCamelCase(prefix) + "(String val)", writer);
			//			writeLine("\t{", writer);
			//			returnLine = "\t\tset" +underScoreToCamelCaseVariableName(prefix) + "Id = DisplayFieldHelper.getI().getPk(";
			//			returnLine += underScoreToCamelCase(tablesMap.get(shortPrefix)) + "Dba.class, val); ";
			//			writeLine(returnLine, writer);
			//			writeLine("\t}", writer);
			//			writeLine("", writer);
		}
	}

	private static void writeGetAndSetMethod(List<TabColumn> columns, CharacterMappingHelper cmh, PrintWriter writer) throws IOException
	{
		for (TabColumn tabColumn : columns)
		{
			String data = tabColumn.dataType;
			if (tabColumn.nullable.equalsIgnoreCase("YES") && tabColumn.dataType.equalsIgnoreCase("NUMBER"))
				data = "integer";

			String camelCaseVariableName = StringHelper.underScoreToCamelCaseVariableName(tabColumn.columnName);
			String camelCase = StringHelper.underScoreToCamelCase(tabColumn.columnName);
			if (data.equalsIgnoreCase("integer"))
			{
				writeLine("\tpublic " + cmh.getGetSetMethod(data.trim()) + " get" + camelCase + "()" + "\n\t{" + "\n\t\tif (" + camelCaseVariableName + " != null && " + camelCaseVariableName + " == 0)" + "\n\t\t\treturn null;" + "\n\t\treturn " + camelCaseVariableName + ";" + "\n\t}", writer);
				writeLine("", writer);
				writeLine("\tpublic void set" + camelCase + "(" + cmh.getGetSetMethod(data.trim()) + " " + camelCaseVariableName + ")" + "\n\t{" + "\n\t\tthis." + camelCaseVariableName + " = " + camelCaseVariableName + ";" + "\n\t}", writer);
				writeLine("", writer);
			}
			else
			{
				writeLine("\tpublic " + cmh.getGetSetMethod(data.trim()) + " get" + camelCase + "()" + "\n\t{" + "\n\t\treturn " + camelCaseVariableName + ";" + "\n\t}", writer);
				writeLine("", writer);
				writeLine("\tpublic void set" + camelCase + "(" + cmh.getGetSetMethod(data.trim()) + " " + camelCaseVariableName + ")" + "\n\t{" + "\n\t\tthis." + camelCaseVariableName + " = " + camelCaseVariableName + ";" + "\n\t}", writer);
				writeLine("", writer);
			}
		}
	}

	private static void writeGetProperty(String currentPrefix, Map<String, String> tablesMap, List<TabColumn> columns, CharacterMappingHelper cmh, PrintWriter writer) throws IOException
	{
		writeLine("\tpublic Object getProperty(String propertyName)", writer);
		writeLine("\t{", writer);

		for (TabColumn tabColumn : columns)
		{
			if (!tabColumn.dataType.equals("NUMBER_FK"))
				continue;
			if (!tabColumn.columnName.endsWith("_id"))
				continue;

			String prefix = tabColumn.columnName.replace("_id", "");
			if (prefix.length() <= 2)
				continue;

			if (prefix.equalsIgnoreCase(currentPrefix))
			{
				writeLine("\t\tif(propertyName.equals(\"id\"))", writer);
				writeLine("\t\t\treturn getPk();", writer);
				continue;
			}

			String shortPrefix = prefix.substring(prefix.length() - 3, prefix.length());

			if (tablesMap.get(shortPrefix) == null)
				continue;

			writeLine("\t\tif(propertyName.equals(\"" + underScoreToCamelCaseVariableName(prefix) + "\"))", writer);
			writeLine("\t\t\treturn get" + underScoreToCamelCase(prefix) + "();", writer);
		}

		for (TabColumn tabColumn : columns)
		{
			String camelCaseVariableName = StringHelper.underScoreToCamelCaseVariableName(tabColumn.columnName);
			String camelCase = StringHelper.underScoreToCamelCase(tabColumn.columnName);

			writeLine("\t\tif(propertyName.equals(\"" + camelCaseVariableName + "\"))", writer);
			writeLine("\t\t\treturn get" + camelCase + "();", writer);
		}

		writeLine("\t\tthrow new " + GeneratorConstants.EXCEPTIONCLASS + "(\"Property :\"+propertyName +\" not found \");", writer);

		writeLine("\t}", writer);
	}

	private static void writeClassEnd(PrintWriter writer) throws IOException
	{
		writeLine("}", writer);
	}

	private static void writeLine(String line, PrintWriter writer) throws IOException
	{
		writer.write(line);
		writer.write("\n");
	}

}
