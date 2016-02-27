package se.signa.signature.newdbfilegenerator;

import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PACKAGE;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PACKAGE_GEN_DBA_COMMON;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PACKAGE_GEN_JAVA_UTIL_ARRAYLIST;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PACKAGE_GEN_JAVA_UTIL_HASHMAP;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PACKAGE_GEN_JAVA_UTIL_LIST;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PACKAGE_GEN_JAVA_UTIL_MAP;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PACKAGE_JODA_DATE_TIME;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PACKAGE_REF_HELPER;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PACKAGE_SQL_CONNECTION;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PROJECT_BASEPATH;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PROJECT_COMMON_FOLDERPATH;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PROJECT_IMPL_DBO_FILE_EXTENSION;
import static se.signa.signature.newdbfilegenerator.StringHelper.underScoreToCamelCase;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import org.apache.log4j.Logger;

public class DbaClassMapperGenerator
{
	private static Logger logger = Logger.getLogger(DboImplGenerator.class);

	public static void generateDIMFile(Map<String, String> tablesMap)
	{
		File folder = new File(PROJECT_BASEPATH + PROJECT_COMMON_FOLDERPATH);
		String packageName = PACKAGE + ".common";
		File file = new File(folder, "DbaClassMapper" + PROJECT_IMPL_DBO_FILE_EXTENSION);
		try
		{
			if (file.exists())
			{
				file.delete();
			}
			file.createNewFile();

			PrintWriter writer = new PrintWriter(file, "UTF-8");
			writeImportAndPackage(packageName, writer);
			writeClassBegin(writer);
			writeGetIMethod(writer);
			writeConstructor(writer);
			writePopulatePrefixClasses(writer, tablesMap);
			writePopulateClasses(writer, tablesMap);
			writeGetClassByTableName(writer);
			writeGetClassByPrefix(writer);
			writeClassEnd(writer);
			writer.close();
			logger.debug("Dba Class Mapper File is created");

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private static void writeClassBegin(PrintWriter writer)
	{
		writer.println("public class DbaClassMapper");
		writer.println("{");
		writer.println("\t" + "private static DbaClassMapper INSTANCE;");
		writer.println("\t" + "private static Map<String,String> prefixes = null;");
		writer.println("\t" + "private static Map<String,String> classes = null;");
		writer.println("");
	}

	private static void writeClassEnd(PrintWriter writer)
	{
		writer.print("}");
	}

	private static void writeImportAndPackage(String packageN, PrintWriter writer)
	{
		writer.println("package " + packageN + ";");
		writer.println("");

		writer.println("import " + PACKAGE + PACKAGE_GEN_DBA_COMMON + ";");
		writer.println("import " + PACKAGE + PACKAGE_REF_HELPER + ";");
		writer.println("");
		writer.println("import " + PACKAGE_SQL_CONNECTION + ";");
		writer.println("import " + PACKAGE_GEN_JAVA_UTIL_HASHMAP + ";");
		writer.println("import " + PACKAGE_GEN_JAVA_UTIL_MAP + ";");
		writer.println("import " + PACKAGE_GEN_JAVA_UTIL_LIST + ";");
		writer.println("import " + PACKAGE_GEN_JAVA_UTIL_ARRAYLIST + ";");
		writer.println("");
		writer.println("import " + PACKAGE_JODA_DATE_TIME + ";");
		writer.println("");
	}

	private static void writeGetIMethod(PrintWriter writer)
	{
		writer.println("\t" + "public static DbaClassMapper getI()");
		writer.println("\t" + "{");
		writer.println("\t" + "\t" + "if (INSTANCE == null)");
		writer.println("\t" + "\t" + "\t" + "INSTANCE = new DbaClassMapper();");
		writer.println("\t" + "\t" + "return INSTANCE;");
		writer.println("\t" + "}");
		writer.println("");
	}

	private static void writeConstructor(PrintWriter writer)
	{
		writer.println("\tprivate DbaClassMapper()");
		writer.println("\t{");
		writer.println("\t\tpopulatePrefixClasses();");
		writer.println("\t\tpopulateClasses();");
		writer.println("\t}");
		writer.println("");
	}

	private static void writePopulatePrefixClasses(PrintWriter writer, Map<String, String> tablesMap)
	{
		writer.println("\tprotected void populatePrefixClasses()");
		writer.println("\t{");
		writer.println("\t\tprefixes = new HashMap<String,String>();");
		for (String prefix : tablesMap.keySet())
		{
			String className = PACKAGE + ".gen.dba." + underScoreToCamelCase(tablesMap.get(prefix)) + "Dba";
			writer.println("\t\tprefixes.put(\"" + prefix + "\", \"" + className + "\");");
		}
		writer.println("\t}");
		writer.println("");
	}

	private static void writePopulateClasses(PrintWriter writer, Map<String, String> tablesMap)
	{
		writer.println("\tprotected void populateClasses()");
		writer.println("\t{");
		writer.println("\t\tclasses = new HashMap<String,String>();");
		for (String prefix : tablesMap.keySet())
		{
			String className = PACKAGE + ".gen.dba." + underScoreToCamelCase(tablesMap.get(prefix)) + "Dba";
			writer.println("\t\tclasses.put(\"" + tablesMap.get(prefix) + "\", \"" + className + "\");");
		}
		writer.println("\t}");
		writer.println("");
	}

	private static void writeGetClassByTableName(PrintWriter writer)
	{
		writer.println("\tpublic String getClassByTableName(String name)");
		writer.println("\t{");
		writer.println("\t\treturn classes.get(name);");
		writer.println("\t}");
		writer.println("");
	}

	private static void writeGetClassByPrefix(PrintWriter writer)
	{
		writer.println("\tpublic String getClassByPrefix(String name)");
		writer.println("\t{");
		writer.println("\t\treturn prefixes.get(name);");
		writer.println("\t}");
		writer.println("");
	}

}
