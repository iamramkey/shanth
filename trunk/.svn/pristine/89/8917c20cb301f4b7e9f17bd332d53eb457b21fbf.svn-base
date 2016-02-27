/**
 * Copyright SIGNA AB, STOCKHOLM, SWEDEN
 */

package se.signa.signature.helpers;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import se.signa.signature.common.SignatureException;

public abstract class DBHelper
{

	protected static final String QT = "'";
	protected static final String QTRUE = "'Y'";
	protected static final String QFALSE = "'N'";
	protected static final String QET = "''";
	protected static final String QNULL = "null";
	protected static final String Q = Pattern.quote("?");
	protected static final String QDTTM = "dd-MMM-yy hh.mm.ss.SSS aa";

	protected Logger logger;

	protected Connection connection;
	protected String url;
	protected String driverClassString;
	protected String username;
	protected String password;
	protected String sid;
	protected Properties properties;
	protected DateTimeFormatter dtf;
	protected String connectionString;

	//protected Statement statement;

	protected DBHelper() throws SignatureException
	{
		logger = Logger.getLogger(DBHelper.class);
		loadProperties();
		loadDataFromProperties();
	}

	private void loadDataFromProperties()
	{
		url = properties.getProperty("url");
		driverClassString = properties.getProperty("driverClassString");
		username = properties.getProperty("username");
		password = CryptographicHelper.hashDecrypt(properties.getProperty("password"));
		sid = properties.getProperty("sid");
		connectionString = username + "/" + password + "@" + sid;
		System.out.println("Initilizing..." + username);
		initialize(url, driverClassString, username, password);
		System.out.println("Initialized");
		dtf = DateTimeFormat.forPattern(QDTTM);
	}

	private void loadProperties() throws SignatureException
	{
		try
		{
			properties = new Properties();
			InputStream inStream = getClass().getClassLoader().getResourceAsStream(getCfgFile());
			properties.load(inStream);
		}
		catch (Exception e)
		{
			logger.error(e);
			throw new SignatureException(e);
		}
	}

	protected abstract String getCfgFile();

	// ----------------------- private methods for initializing and connection

	@SuppressWarnings({ "rawtypes", "unused" })
	private void initialize(String url, String driverClassString, String username, String password) throws SignatureException
	{
		try
		{
			Class driverClass = Class.forName(driverClassString);
			connection = DriverManager.getConnection(url, username, password);
		}
		catch (Exception e)
		{
			logger.error(e);
			throw new SignatureException(e);
		}
	}

	// ---------------------------- public methods for query execution
	public String getConnectionString()
	{
		return connectionString;
	}

	// ----------------------------  fetch Count methods
	public int fetchCount(String query, Object paramValue)
	{
		return fetchCountLocal(null, query, new Object[] { paramValue });
	}

	public int fetchCount(String query, Object[] paramValues)
	{
		return fetchCountLocal(null, query, paramValues);
	}

	public int fetchCount(String query)
	{
		return fetchCountLocal(null, query, null);
	}

	// ----------------------------  execute methods
	public ExecuteSelectStamentResult execute(String query)
	{
		return executeSelectStatement(null, query, null);
	}

	public ExecuteSelectStamentResult execute(String query, Object paramValue) throws SignatureException
	{
		return executeSelectStatement(null, query, new Object[] { paramValue });
	}

	public ExecuteSelectStamentResult execute(String query, Object[] paramValues) throws SignatureException
	{
		return executeSelectStatement(null, query, paramValues);
	}

	// ----------------------------  execute update methods
	public int executeUpdate(String query)
	{
		return executeUpdateLocal(null, query, null);
	}

	public int executeUpdate(String query, Object paramValue) throws SignatureException
	{
		return executeUpdateLocal(null, query, new Object[] { paramValue });
	}

	public int executeUpdate(String query, Object[] paramValues) throws SignatureException
	{
		return executeUpdateLocal(null, query, paramValues);
	}

	// ----------------------------  execute update one row methods
	public int executeUpdateOneRow(String query, Object paramValue) throws SignatureException
	{
		return executeUpdateOneRowLocal(null, query, new Object[] { paramValue });
	}

	public int executeUpdateOneRow(String query, Object[] paramValues) throws SignatureException
	{
		return executeUpdateOneRowLocal(null, query, paramValues);
	}

	//------------------------------ local methods
	protected int executeUpdateLocal(Connection localConnection, String query, Object[] paramValues)
	{
		return executeUpdateStatement(localConnection, query, paramValues);
	}

	protected int executeUpdateOneRowLocal(Connection localConnection, String query, Object[] paramValues)
	{
		int returnCount = executeUpdateLocal(localConnection, query, paramValues);
		if (returnCount != 1)
			throw prepareException(" Expected to affect one row but affected " + returnCount, query, paramValues);
		return 1;
	}

	protected SignatureException prepareException(String prefix, String query, Object[] paramValues)
	{
		return prepareExceptionLocal(prefix, query, paramValues, null);
	}

	protected SignatureException prepareException(String query, Object[] paramValues)
	{
		return prepareExceptionLocal("", query, paramValues, null);
	}

	protected SignatureException prepareException(String query, Object[] paramValues, SQLException e)
	{
		return prepareExceptionLocal(null, query, paramValues, e);
	}

	private SignatureException prepareExceptionLocal(String prefix, String query, Object[] paramValues, SQLException e)
	{
		StringBuffer message = new StringBuffer();
		if (prefix != null)
			message.append(prefix);

		message.append(query);

		if (paramValues != null && paramValues.length > 0)
		{
			message.append(" failed with parameters ");

			for (int i = 0; i < paramValues.length; i++)
			{
				if (paramValues[i] == null)
				{
					message.append(" Param value ( " + i + " ) is null");
				}
				else
				{
					message.append(" Param value ( " + i + " ) is " + paramValues[i].toString());
				}
			}
		}
		if (e == null)
			return new SignatureException(message.toString());

		if (e.getMessage() != null)
			message.append(e.getMessage());

		logger.error(e);
		return new SignatureException(message.toString(), e);
	}

	protected void setParameters(PreparedStatement prepStmt, Object[] paramValues) throws SQLException
	{
		if (paramValues == null || paramValues.length <= 0)
			return;

		for (int i = 1; i <= paramValues.length; i++)
		{
			Object paramValue = paramValues[i - 1];
			//TODO: will this work for all nulls
			if (paramValue == null)
				prepStmt.setNull(i, Types.VARCHAR);
			else if (paramValue instanceof String)
				prepStmt.setString(i, (String) paramValue);
			else if (paramValue instanceof Integer)
				prepStmt.setInt(i, (Integer) paramValue);
			else if (paramValue instanceof Boolean)
				prepStmt.setBoolean(i, (Boolean) paramValue);
			else if (paramValue instanceof Long)
				prepStmt.setLong(i, (Long) paramValue);
			else if (paramValue instanceof Double)
				prepStmt.setDouble(i, (Double) paramValue);
			else if (paramValue instanceof DateTime)
				prepStmt.setTimestamp(i, new Timestamp(((DateTime) paramValue).getMillis()));
			else
				throw new SignatureException(" Invalid parameter type query");
		}
	}

	protected ExecuteSelectStamentResult executeSelectStatement(Connection localConnection, String query, Object[] paramValues)
	{
		try
		{
			PreparedStatement prepStmt = prepareStatement(localConnection, query, paramValues);
			ResultSet rs = prepStmt.executeQuery();
			return new ExecuteSelectStamentResult(rs, prepStmt);
		}
		catch (SQLException e)
		{
			throw prepareException(query, paramValues, e);
		}
	}

	protected int executeUpdateStatement(Connection localConnection, String query, Object[] paramValues)
	{
		try
		{
			PreparedStatement prepStmt = prepareStatement(localConnection, query, paramValues);
			int count = prepStmt.executeUpdate();
			prepStmt.close();
			return count;
		}
		catch (SQLException e)
		{
			throw prepareException(query, paramValues, e);
		}
	}

	private int fetchCountLocal(Connection externalConnection, String query, Object[] paramValues)
	{
		int count = 0;
		ExecuteSelectStamentResult result = null;
		try
		{
			result = executeSelectStatement(externalConnection, "select count(1) " + query, paramValues);
			while (result.next())
				count = result.getRs().getInt(1);
			return count;
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
	}

	private PreparedStatement prepareStatement(Connection localConnection, String query, Object[] paramValues) throws SQLException
	{
		if (localConnection == null)
			localConnection = connection;

		logger.trace("Executing query : " + query);
		PreparedStatement prepStmt = localConnection.prepareStatement(query);
		setParameters(prepStmt, paramValues);
		return prepStmt;
	}

	public class ExecuteSelectStamentResult
	{
		private ResultSet rs;
		private PreparedStatement prepStmt;

		public ExecuteSelectStamentResult(ResultSet rs, PreparedStatement prepStmt)
		{
			super();
			this.rs = rs;
			this.prepStmt = prepStmt;
		}

		public void close()
		{
			try
			{
				if (rs != null)
					rs.close();
				if (prepStmt != null)
					prepStmt.close();
			}
			catch (SQLException e)
			{
				throw new SignatureException(e);
			}

		}

		public boolean next() throws SQLException
		{
			return rs.next();
		}

		public ResultSet getRs()
		{
			return rs;
		}
	}
}
