package se.signa.signature.newdbfilegenerator;

import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PACKAGE;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PROJECT_BASEPATH;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PROJECT_IMPL_DBOFOLDERPATH;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PROJECT_IMPL_DBO_FILE;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PROJECT_IMPL_DBO_FILE_EXTENSION;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PROJECT_IMPL_DBO_PACKAGE;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PROJECT_IMPL_GEN_PACKAGE;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.log4j.Logger;

public class DboImplGenerator
{
	private static Logger logger = Logger.getLogger(DboImplGenerator.class);

	public static void generateDboImpl(String tableName) throws IOException
	{
		File dBoFolder = new File(PROJECT_BASEPATH + PROJECT_IMPL_DBOFOLDERPATH);
		String dBoPackage = PACKAGE + PROJECT_IMPL_DBO_PACKAGE;
		File dBo = new File(dBoFolder, StringHelper.underScoreToCamelCase(tableName) + PROJECT_IMPL_DBO_FILE + PROJECT_IMPL_DBO_FILE_EXTENSION);

		if (dBo.exists())
		{
			logger.debug(StringHelper.underScoreToCamelCase(tableName) + PROJECT_IMPL_DBO_FILE + PROJECT_IMPL_DBO_FILE_EXTENSION + " File already exists");
			return;
		}
		dBo.createNewFile();
		PrintWriter writer = new PrintWriter(dBo, "UTF-8");
		writer.println("/**");
		writer.println("* Copyright SIGNA AB, STOCKHOLM, SWEDEN");
		writer.println("*/");
		writer.println();
		writer.println("package " + dBoPackage + ";");
		writer.println("");
		writer.println("import " + PACKAGE + PROJECT_IMPL_GEN_PACKAGE + PROJECT_IMPL_DBO_PACKAGE + "." + StringHelper.underScoreToCamelCase(tableName) + ";");
		writer.println("");
		writer.println("import java.sql.ResultSet;");
		writer.println("");
		writer.println("public class " + StringHelper.underScoreToCamelCase(tableName) + PROJECT_IMPL_DBO_FILE + " extends " + StringHelper.underScoreToCamelCase(tableName));
		writer.println("{");
		writer.println("\t" + "public " + StringHelper.underScoreToCamelCase(tableName) + PROJECT_IMPL_DBO_FILE + "()");
		writer.println("\t" + "{");
		writer.println("\t" + "}");
		writer.println("");
		writer.println("\t" + "public " + StringHelper.underScoreToCamelCase(tableName) + PROJECT_IMPL_DBO_FILE + "(ResultSet rs)");
		writer.println("\t" + "{");
		writer.println("\t" + "\t" + "populateFromResultSet(rs);");
		writer.println("\t" + "}");
		writer.println("");
		writer.print("}");

		writer.close();
		logger.debug(StringHelper.underScoreToCamelCase(tableName) + PROJECT_IMPL_DBO_FILE + PROJECT_IMPL_DBO_FILE_EXTENSION + " File is created");

	}

}
