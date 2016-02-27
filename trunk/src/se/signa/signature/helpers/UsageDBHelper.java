/**
 * Copyright SIGNA AB, STOCKHOLM, SWEDEN
 */

package se.signa.signature.helpers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import se.signa.signature.common.SignatureException;

public class UsageDBHelper extends DBHelper
{

	private static UsageDBHelper INSTANCE = null;

	// this is a singleton class- single entry point
	public synchronized static UsageDBHelper getDB() throws SignatureException
	{
		if (INSTANCE == null)
			INSTANCE = new UsageDBHelper();
		return INSTANCE;
	}

	private UsageDBHelper() throws SignatureException
	{
		super();
		logger = Logger.getLogger(UsageDBHelper.class);
		logger.info("Usage Database initialised");
	}

	@Override
	protected String getCfgFile()
	{
		return "usagedb.cfg";
	}

	// ---------------------------- public methods for query execution

	public boolean doesTableExist(String tableName) throws SignatureException
	{
		tableName = tableName.toUpperCase();
		String query = "select table_name from user_tables where table_name = ? ";
		boolean tableExists = false;
		ExecuteSelectStamentResult result = null;
		try
		{
			result = executeSelectStatement(null, query, new Object[] { tableName });
			if (result.next())
				tableExists = true;
		}
		catch (SQLException e)
		{
			return false;
		}
		finally
		{
			if (result != null)
				result.close();
		}
		return tableExists;
	}

	public List<String> getTables(String prefix)
	{
		prefix = prefix.toUpperCase();
		String query = "select tname from tab where tname like '" + prefix + "%'";
		List<String> tableNames = new ArrayList<String>();
		ExecuteSelectStamentResult result = null;
		try
		{
			result = executeSelectStatement(null, query, null);
			while (result.next())
				tableNames.add(result.getRs().getString("tname"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
		finally
		{
			if (result != null)
				result.close();
		}
		return tableNames;
	}
}
