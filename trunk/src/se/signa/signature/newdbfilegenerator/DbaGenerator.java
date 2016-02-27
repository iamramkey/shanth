package se.signa.signature.newdbfilegenerator;

import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PACKAGE;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PACKAGE_AUD_HELPER;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PACKAGE_GEN_DBA_COMMON;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PACKAGE_GEN_JAVA_UTIL_ARRAYLIST;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PACKAGE_GEN_JAVA_UTIL_LIST;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PACKAGE_GEN_PK_HELPERS;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PACKAGE_JODA_DATE_TIME;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PACKAGE_REF_HELPER;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PACKAGE_SQL_CONNECTION;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PROJECT_AVPDBHELPER;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PROJECT_BASEPATH;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PROJECT_GEN_DBAFOLDERPATH;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PROJECT_IMPL_DBA_FILE;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PROJECT_IMPL_DBA_PACKAGE;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PROJECT_IMPL_DBO_FILE;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PROJECT_IMPL_DBO_FILE_EXTENSION;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PROJECT_IMPL_DBO_PACKAGE;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PROJECT_IMPL_GEN_PACKAGE;
import static se.signa.signature.newdbfilegenerator.StringHelper.underScoreToCamelCase;
import static se.signa.signature.newdbfilegenerator.StringHelper.underScoreToCamelCaseDisplay;
import static se.signa.signature.newdbfilegenerator.StringHelper.underScoreToCamelCaseVariableName;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.apache.log4j.Logger;

public class DbaGenerator
{
	private static Logger logger = Logger.getLogger(DbaGenerator.class);

	public static void generateDba(String tableName, String prefix, List<TabColumn> columns, String displayField, List<String> uniqueColumns) throws IOException
	{
		File genDbaFolder = new File(PROJECT_BASEPATH + PROJECT_GEN_DBAFOLDERPATH);
		String genDbaPackage = PACKAGE + PROJECT_IMPL_GEN_PACKAGE + PROJECT_IMPL_DBA_PACKAGE;
		File genDba = new File(genDbaFolder, underScoreToCamelCase(tableName) + "Dba" + PROJECT_IMPL_DBO_FILE_EXTENSION);

		if (genDba.exists())
			genDba.delete();
		genDba.createNewFile();

		PrintWriter writer = new PrintWriter(genDba, "UTF-8");
		writeCopyright(writer);
		writeImportAndPackage(tableName, genDbaPackage, writer);
		writeClassBegin(tableName, writer);
		writeGetIMethod(tableName, writer);
		writeConstructor(tableName, prefix, writer);
		writeGetColumns(tableName, prefix, columns, writer);
		writeCreateEmptyDbo(tableName, prefix, columns, writer);
		writeCheckDuplicates(tableName, prefix, columns, writer, uniqueColumns);
		writeCreateMethod(tableName, prefix, columns, writer);
		writeUpdateMethod(tableName, prefix, columns, writer);
		writeFetchAllMethod(tableName, writer);
		writeFetchAuditRowsByPkMethod(tableName, prefix, writer);
		writeFetchByIdMethod(tableName, prefix, writer);
		writeFetchByBkMethod(tableName, prefix, writer);
		writeFetchByDisplayField(tableName, displayField, writer);
		writeFetchStringListMethod(tableName, displayField, writer);

		writeClassEnd(writer);
		writer.close();
		logger.debug(underScoreToCamelCase(tableName) + PROJECT_IMPL_DBO_FILE_EXTENSION + " File is created");

	}

	private static void writeCopyright(PrintWriter writer)
	{
		writer.println("/**");
		writer.println("* Copyright SIGNA AB, STOCKHOLM, SWEDEN");
		writer.println("*/");
	}

	private static void writeClassBegin(String tableName, PrintWriter writer)
	{
		writer.println("public abstract class " + underScoreToCamelCase(tableName) + "Dba" + " extends " + PROJECT_AVPDBHELPER + "<" + underScoreToCamelCase(tableName) + ">");
		writer.println("{");
		writer.println("\t" + "private static " + underScoreToCamelCase(tableName) + PROJECT_IMPL_DBA_FILE + " INSTANCE" + ";");
		writer.println("");
	}

	private static void writeClassEnd(PrintWriter writer)
	{
		writer.print("}");
	}

	private static void writeImportAndPackage(String tableName, String genDbaPackage, PrintWriter writer)
	{
		writer.println("package " + genDbaPackage + ";");
		writer.println("");
		writer.println("import " + PACKAGE_SQL_CONNECTION + ";");
		writer.println("import " + PACKAGE_GEN_JAVA_UTIL_ARRAYLIST + ";");
		writer.println("import " + PACKAGE_GEN_JAVA_UTIL_LIST + ";");
		writer.println("");
		writer.println("import " + PACKAGE_JODA_DATE_TIME + ";");
		writer.println("");
		writer.println("import " + PACKAGE + PACKAGE_GEN_DBA_COMMON + ";");
		writer.println("import " + PACKAGE + PROJECT_IMPL_DBA_PACKAGE + "." + underScoreToCamelCase(tableName) + PROJECT_IMPL_DBA_FILE + ";");
		writer.println("import " + PACKAGE + PROJECT_IMPL_DBO_PACKAGE + "." + underScoreToCamelCase(tableName) + PROJECT_IMPL_DBO_FILE + ";");
		writer.println("import " + PACKAGE + PROJECT_IMPL_GEN_PACKAGE + PROJECT_IMPL_DBO_PACKAGE + "." + underScoreToCamelCase(tableName) + ";");
		writer.println("import " + PACKAGE + PACKAGE_AUD_HELPER + ";");
		writer.println("import " + PACKAGE + PACKAGE_GEN_PK_HELPERS + ";");
		writer.println("import " + PACKAGE + PACKAGE_REF_HELPER + ";");
		writer.println("");
	}

	private static void writeGetIMethod(String tableName, PrintWriter writer)
	{
		writer.println("\t" + "public static " + underScoreToCamelCase(tableName) + PROJECT_IMPL_DBA_FILE + " getI()");
		writer.println("\t" + "{");
		writer.println("\t" + "\t" + "if (INSTANCE == null)");
		writer.println("\t" + "\t" + "\t" + "INSTANCE = new " + underScoreToCamelCase(tableName) + PROJECT_IMPL_DBA_FILE + "();");
		writer.println("\t" + "\t" + "return INSTANCE;");
		writer.println("\t" + "}");
		writer.println("");
	}

	private static void writeConstructor(String tableName, String prefix, PrintWriter writer)
	{
		writer.println("\tpublic " + underScoreToCamelCase(tableName) + "Dba()");
		writer.println("\t{");
		writer.println("\t\ttableName = \"" + tableName + "\";");
		writer.println("\t\ttablePrefix = \"" + prefix + "\";");
		writer.println("\t}");
		writer.println("");
	}

	private static void writeGetColumns(String tableName, String prefix, List<TabColumn> columns, PrintWriter writer)
	{
		writer.println("\tpublic List<String> getColumns()");
		writer.println("\t{");
		writer.println("\t\tList<String> columns = new ArrayList<String>();");
		for (TabColumn column : columns)
		{
			String columnName = column.columnName;
			if (!column.columnName.endsWith("_bk") && !column.columnName.endsWith("_modified_usr_id") && !column.columnName.endsWith("_modified_dttm"))
			{
				if (column.columnName.endsWith("_created_usr_id") || column.columnName.endsWith("_created_dttm"))
				{
					columnName = column.columnName.replace("_created", "");
					columnName = columnName.replace("_usr_id", "_user_name");
				}

				writer.println("\t\tcolumns.add(\"" + underScoreToCamelCaseDisplay(columnName) + "\");");
			}
		}
		writer.println("\t\treturn columns;");
		writer.println("\t}");
		writer.println("");
	}

	private static void writeCreateEmptyDbo(String tableName, String prefix, List<TabColumn> columns, PrintWriter writer)
	{
		writer.println("\t@Override");
		writer.println("\tpublic " + underScoreToCamelCase(tableName) + " createEmptyDbo()");
		writer.println("\t{");
		writer.println("\t\treturn new " + underScoreToCamelCase(tableName) + "Impl();");
		writer.println("\t}");
		writer.println("");
	}

	private static void writeCheckDuplicates(String tableName, String prefix, List<TabColumn> columns, PrintWriter writer, List<String> uniqueColumns)
	{
		writer.println("\t@Override");
		writer.println("\tpublic void checkDuplicates(" + underScoreToCamelCase(tableName) + " dbo)");
		writer.println("\t{");
		for (String uniqueColumn : uniqueColumns)
		{
			String fieldName = underScoreToCamelCase(uniqueColumn);
			//			TODO : Uncomment once the getLable method is done.
			//			writer.println("\t\t" + "checkDuplicateGM(dbo.get" + fieldName + "(), getLabel(\"" + uniqueColumn + "\"), dbo.getPk());");
			writer.println("\t\t" + "checkDuplicateGM(dbo.get" + fieldName + "(), \"" + uniqueColumn + "\", dbo.getPk());");
		}
		writer.println("\t}");
		writer.println("");
	}

	private static void writeCreateMethod(String tableName, String prefix, List<TabColumn> columns, PrintWriter writer)
	{
		writer.println("\t@Override");
		writer.println("\tpublic int create(" + underScoreToCamelCase(tableName) + " " + underScoreToCamelCaseVariableName(tableName) + ", int usrId)");
		writer.println("\t{");
		writer.println("\t\treturn create(null, " + underScoreToCamelCaseVariableName(tableName) + ", usrId);");
		writer.println("\t}");
		writer.println("");

		writer.println("\tpublic int create(Connection connection, " + underScoreToCamelCase(tableName) + " " + underScoreToCamelCaseVariableName(tableName) + ", int usrId)");
		writer.println("\t{");
		writer.println("\t\tint maxId = PrimaryKeyHelper.getPKH().getPrimaryKey(\"" + prefix + "_id\", \"" + tableName + "\");");
		writer.println("\t\tString bk = " + underScoreToCamelCaseVariableName(tableName) + ".getDisplayField();");
		writer.println("");

		String questions = "";
		for (int i = 0; i < columns.size(); i++)
			questions = questions + "?,";
		questions = questions.substring(0, questions.length() - 1);
		writer.println("\t\t" + "String query = \" insert into " + tableName + " values(" + questions + ")\";");

		String inputParamValues = "";
		for (int i = 1; i < columns.size() - 5; i++)
			inputParamValues = inputParamValues + ", " + underScoreToCamelCaseVariableName(tableName) + ".get" + underScoreToCamelCase(columns.get(i).columnName) + "()";
		String lastParamValues = ", bk, usrId, null, new DateTime(), null";
		writer.println("\t\t" + "Object[] paramValues = new Object[] { maxId" + inputParamValues + lastParamValues + " };");
		writer.println("\t\t" + "RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);");
		writer.println("\t\t" + "AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);");
		writer.println("\t\treturn maxId;");
		writer.println("\t}");
		writer.println("");
	}

	private static void writeUpdateMethod(String tableName, String prefix, List<TabColumn> columns, PrintWriter writer)
	{
		writer.println("\t@Override");
		writer.println("\tpublic void update(" + underScoreToCamelCase(tableName) + " " + underScoreToCamelCaseVariableName(tableName) + ", int usrId)");
		writer.println("\t{");
		writer.println("\t\tupdate(null, " + underScoreToCamelCaseVariableName(tableName) + ", usrId);");
		writer.println("\t}");
		writer.println("");

		writer.println("\tpublic void update(Connection connection, " + underScoreToCamelCase(tableName) + " " + underScoreToCamelCaseVariableName(tableName) + ", int usrId)");
		writer.println("\t{");
		String sets = " set ";
		for (int i = 1; i < columns.size() - 5; i++)
			sets = sets + " " + columns.get(i).columnName + " = ? ,";
		sets = sets + " " + prefix + "_modified_usr_id = ? , " + prefix + "_modified_dttm = ? ";
		writer.println("\t" + "\t" + "String query = \" update " + tableName + sets + " where " + prefix + "_id = ?  \";");

		String inputParamValues = "";
		for (int i = 1; i < columns.size() - 5; i++)
			inputParamValues = inputParamValues + underScoreToCamelCaseVariableName(tableName) + ".get" + underScoreToCamelCase(columns.get(i).columnName) + "(), ";
		String lastParamValues = "usrId, new DateTime(), " + underScoreToCamelCaseVariableName(tableName) + ".get" + underScoreToCamelCase(prefix) + "Id()";

		writer.println("\t\tObject[] paramValues = new Object[] { " + inputParamValues + lastParamValues + " };");
		writer.println("\t\tRefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);");

		writer.println();
		String questions = "";
		for (int i = 0; i < columns.size(); i++)
			questions = questions + "?,";
		questions = questions.substring(0, questions.length() - 1);
		writer.println("\t\t" + "String insertQuery = \" insert into " + tableName + " values(" + questions + ")\";");

		String insertInputParamValues = "";
		for (int i = 0; i < columns.size() - 4; i++)
			insertInputParamValues = insertInputParamValues + underScoreToCamelCaseVariableName(tableName) + ".get" + underScoreToCamelCase(columns.get(i).columnName) + "() , ";
		String lastInsertParamValues = " usrId, usrId, new DateTime(), new DateTime()";
		writer.println("\t\t" + "Object[] insertParamValues = new Object[] { " + insertInputParamValues + lastInsertParamValues + " };");
		writer.println("\t\t" + "AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);");

		writer.println("\t}");
		writer.println("");
	}

	private static void writeFetchAllMethod(String tableName, PrintWriter writer)
	{
		writer.println("\t" + "public List<" + underScoreToCamelCase(tableName) + "> fetchAll()");
		writer.println("\t" + "{");
		writer.println("\t" + "\t" + "String query = \" select * from " + tableName + " \"" + ";");
		writer.println("\t" + "\t" + "return RefDBHelper.getDB().fetchList(query, " + underScoreToCamelCase(tableName) + "Impl.class);");
		writer.println("\t" + "}");
		writer.println("");
	}

	private static void writeFetchAuditRowsByPkMethod(String tableName, String prefix, PrintWriter writer)
	{
		writer.println("\t" + "public List<" + underScoreToCamelCase(tableName) + "> fetchAuditRowsByPk(int id)");
		writer.println("\t" + "{");
		writer.println("\t" + "\t" + "String query = \" select * from " + tableName + " where " + prefix + "_id=? order by " + prefix + "_created_dttm \";");
		writer.println("\t" + "\t" + "return AudDBHelper.getDB().fetchList(query, id, " + underScoreToCamelCase(tableName) + "Impl.class);");
		writer.println("\t" + "}");
		writer.println("");
	}

	private static void writeFetchByIdMethod(String tableName, String prefix, PrintWriter writer)
	{
		writer.println("\t" + "public " + underScoreToCamelCase(tableName) + " fetchByPk(int " + prefix + "Id)");
		writer.println("\t" + "{");
		writer.println("\t" + "\t" + "return fetchByPk(null, " + prefix + "Id);");
		writer.println("\t" + "}");
		writer.println("");

		writer.println("\t" + "public " + underScoreToCamelCase(tableName) + " fetchByPk(Connection connection, int " + prefix + "Id)");
		writer.println("\t" + "{");
		writer.println("\t" + "\t" + "return RefDBHelper.getDB().fetchObject(connection, \" select * from " + tableName + " where " + prefix + "_id=? \", " + prefix + "Id, " + underScoreToCamelCase(tableName) + "Impl.class);");
		writer.println("\t" + "}");
		writer.println("");
	}

	private static void writeFetchByBkMethod(String tableName, String prefix, PrintWriter writer)
	{
		writer.println("\t" + "public " + underScoreToCamelCase(tableName) + " fetchByBk(String " + prefix + "Bk)");
		writer.println("\t" + "{");
		writer.println("\t" + "\t" + "return RefDBHelper.getDB().fetchObject(\" select * from " + tableName + " where " + prefix + "_bk=? \", " + prefix + "Bk, " + underScoreToCamelCase(tableName) + "Impl.class);");
		writer.println("\t" + "}");
		writer.println("");
	}

	private static void writeFetchByDisplayField(String tableName, String displayField, PrintWriter writer)
	{
		writer.println("\tpublic " + underScoreToCamelCase(tableName) + " fetchByDisplayField(String displayField)");
		writer.println("\t{");
		writer.println("\t\treturn RefDBHelper.getDB().fetchObject(\" select * from " + tableName + " where " + displayField + "=? \",  displayField  ," + underScoreToCamelCase(tableName) + "Impl.class);");
		writer.println("\t}");
		writer.println("");
	}

	private static void writeFetchStringListMethod(String tableName, String displayField, PrintWriter writer)
	{
		writer.println("\tpublic List<String> fetchStringList()");
		writer.println("\t{");
		writer.println("\t\treturn RefDBHelper.getDB().fetchStringList(\"" + displayField + "\", \"" + tableName + "\");");
		writer.println("\t}");
		writer.println("");
	}

}
