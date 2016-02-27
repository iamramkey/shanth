package se.signa.signature.newdbfilegenerator;

import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PACKAGE;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PROJECT_BASEPATH;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PROJECT_IMPL_DBAFOLDERPATH;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PROJECT_IMPL_DBA_FILE;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PROJECT_IMPL_DBA_PACKAGE;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PROJECT_IMPL_DBO_FILE_EXTENSION;
import static se.signa.signature.newdbfilegenerator.GeneratorConstants.PROJECT_IMPL_GEN_PACKAGE;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.log4j.Logger;

public class DbaImplGenerator
{
	private static Logger logger = Logger.getLogger(DboImplGenerator.class);

	public static void generateDbaImpl(String tableName) throws IOException
	{
		File dBaFolder = new File(PROJECT_BASEPATH + PROJECT_IMPL_DBAFOLDERPATH);
		String dBaPackage = PACKAGE + PROJECT_IMPL_DBA_PACKAGE;
		File dBa = new File(dBaFolder, StringHelper.underScoreToCamelCase(tableName) + PROJECT_IMPL_DBA_FILE + PROJECT_IMPL_DBO_FILE_EXTENSION);
		if (dBa.exists())
		{
			logger.debug(StringHelper.underScoreToCamelCase(tableName) + PROJECT_IMPL_DBA_FILE + PROJECT_IMPL_DBO_FILE_EXTENSION + " File already exists");
			return;
		}

		dBa.createNewFile();
		PrintWriter writer = new PrintWriter(dBa, "UTF-8");
		writer.println("/**");
		writer.println("* Copyright SIGNA AB, STOCKHOLM, SWEDEN");
		writer.println("*/");
		writer.println();

		writer.println("package " + dBaPackage + ";");
		writer.println("");
		writer.println("import " + PACKAGE + PROJECT_IMPL_GEN_PACKAGE + PROJECT_IMPL_DBA_PACKAGE + "." + StringHelper.underScoreToCamelCase(tableName) + "Dba" + ";");
		writer.println("");
		writer.println("public class " + StringHelper.underScoreToCamelCase(tableName) + PROJECT_IMPL_DBA_FILE + " extends " + StringHelper.underScoreToCamelCase(tableName) + "Dba");
		writer.println("{");
		writer.println("");
		writer.print("}");
		writer.close();
		logger.debug(StringHelper.underScoreToCamelCase(tableName) + PROJECT_IMPL_DBA_FILE + PROJECT_IMPL_DBO_FILE_EXTENSION + " File is created");
	}

}
