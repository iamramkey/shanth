/**
 * Copyright SIGNA AB, STOCKHOLM, SWEDEN
 */

package se.signa.signature.helpers;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import se.signa.signature.common.Constants;
import se.signa.signature.common.SignatureDbo;
import se.signa.signature.common.SignatureException;

public class RefDBHelper extends DBHelper
{

	private static RefDBHelper INSTANCE = null;

	// this is a singleton class- single entry point
	public synchronized static RefDBHelper getDB() throws SignatureException
	{
		if (INSTANCE == null)
			INSTANCE = new RefDBHelper();
		return INSTANCE;
	}

	protected RefDBHelper() throws SignatureException
	{
		super();
		logger = Logger.getLogger(RefDBHelper.class);
		logger.info("Reference database initialised");
	}

	@Override
	protected String getCfgFile()
	{
		return "db.cfg";
	}

	// ---------------------------- public methods for query execution

	// ---------------------------- fetch Object methods
	@SuppressWarnings({ "rawtypes" })
	public <T extends SignatureDbo> T fetchObject(String query, Class returnClazz) throws SignatureException
	{
		return fetchObjectLocal(null, query, null, returnClazz);
	}

	@SuppressWarnings({ "rawtypes" })
	public <T extends SignatureDbo> T fetchObject(Connection externalConnection, String query, Class returnClazz) throws SignatureException
	{
		return fetchObjectLocal(externalConnection, query, null, returnClazz);
	}

	@SuppressWarnings({ "rawtypes" })
	public <T extends SignatureDbo> T fetchObject(String query, Object paramValue, Class returnClazz) throws SignatureException
	{
		return fetchObjectLocal(null, query, new Object[] { paramValue }, returnClazz);
	}

	@SuppressWarnings({ "rawtypes" })
	public <T extends SignatureDbo> T fetchObject(Connection externalConnection, String query, Object paramValue, Class returnClazz) throws SignatureException
	{
		return fetchObjectLocal(externalConnection, query, new Object[] { paramValue }, returnClazz);
	}

	@SuppressWarnings({ "rawtypes" })
	public <T extends SignatureDbo> T fetchObject(String query, Object[] paramValues, Class returnClazz) throws SignatureException
	{
		return fetchObjectLocal(null, query, paramValues, returnClazz);
	}

	@SuppressWarnings({ "rawtypes" })
	public <T extends SignatureDbo> T fetchObject(Connection externalConnection, String query, Object[] paramValues, Class returnClazz) throws SignatureException
	{
		return fetchObjectLocal(externalConnection, query, paramValues, returnClazz);
	}

	@SuppressWarnings({ "rawtypes" })
	public <T extends SignatureDbo> T fetchObjectIfExists(String query, Object paramValue, Class returnClazz) throws SignatureException
	{
		return fetchObjectIfExistsLocal(null, query, new Object[] { paramValue }, returnClazz);
	}

	@SuppressWarnings({ "rawtypes" })
	public <T extends SignatureDbo> T fetchObjectIfExists(String query, Object[] paramValues, Class returnClazz) throws SignatureException
	{
		return fetchObjectIfExistsLocal(null, query, paramValues, returnClazz);
	}

	@SuppressWarnings({ "rawtypes" })
	public <T extends SignatureDbo> T fetchObjectIfExists(String query, Class returnClazz) throws SignatureException
	{
		return fetchObjectIfExistsLocal(null, query, null, returnClazz);
	}

	// ---------------------------- fetch List methods
	@SuppressWarnings({ "rawtypes" })
	public <T extends SignatureDbo> List<T> fetchList(String query, Class returnClazz) throws SignatureException
	{
		return fetchListLocal(null, query, null, returnClazz);
	}

	@SuppressWarnings("rawtypes")
	public <T extends SignatureDbo> List<T> fetchList(String query, Object paramValue, Class returnClazz) throws SignatureException
	{
		return fetchListLocal(null, query, new Object[] { paramValue }, returnClazz);
	}

	@SuppressWarnings("rawtypes")
	public <T extends SignatureDbo> List<T> fetchList(String query, Object[] paramValues, Class returnClazz) throws SignatureException
	{
		return fetchListLocal(null, query, paramValues, returnClazz);
	}

	@SuppressWarnings({ "rawtypes" })
	public <T extends SignatureDbo> List<T> fetchList(Connection externalConnection, String query, Class returnClazz) throws SignatureException
	{
		return fetchListLocal(externalConnection, query, null, returnClazz);
	}

	@SuppressWarnings("rawtypes")
	public <T extends SignatureDbo> List<T> fetchList(Connection externalConnection, String query, Object paramValue, Class returnClazz) throws SignatureException
	{
		return fetchListLocal(externalConnection, query, new Object[] { paramValue }, returnClazz);
	}

	@SuppressWarnings("rawtypes")
	public <T extends SignatureDbo> List<T> fetchList(Connection externalConnection, String query, Object[] paramValues, Class returnClazz) throws SignatureException
	{
		return fetchListLocal(externalConnection, query, paramValues, returnClazz);
	}

	public List<String> fetchStringList(String tblColumn, String tblName)
	{
		String query = "select " + tblColumn + " from " + tblName;
		return fetchStringListQuery(tblColumn, query);
	}

	public List<String> fetchStringList(String tblColumn, String tblName, String whereClause)
	{
		String query = "select " + tblColumn + " from " + tblName + " where " + whereClause;
		return fetchStringListQuery(tblColumn, query);
	}

	private List<String> fetchStringListQuery(String tblColumn, String query)
	{
		List<String> stringList = new LinkedList<String>();
		ExecuteSelectStamentResult result = null;
		try
		{
			result = RefDBHelper.getDB().execute(query);
			while (result.next())
			{
				stringList.add(result.getRs().getString(tblColumn));
			}
			return stringList;
		}
		catch (Exception e)
		{
			throw new SignatureException(e);
		}
		finally
		{
			if (result != null)
				result.close();
		}
	}

	// ---------------------------- fetch Hash methods
	public Map<Integer, List<Object[]>> fetchHash(String query, int size) throws SignatureException
	{
		return fetchHashLocal(null, query, null, size);
	}

	public Map<Integer, List<Object[]>> fetchHash(String query, Object paramValue, int size) throws SignatureException
	{
		return fetchHashLocal(null, query, new Object[] { paramValue }, size);
	}

	// ----------------------------  execute methods
	public ExecuteSelectStamentResult execute(Connection externalConnection, String query)
	{
		return executeSelectStatement(externalConnection, query, null);
	}

	public ExecuteSelectStamentResult execute(Connection externalConnection, String query, Object paramValue) throws SignatureException
	{
		return executeSelectStatement(externalConnection, query, new Object[] { paramValue });
	}

	public ExecuteSelectStamentResult execute(Connection externalConnection, String query, Object[] paramValues) throws SignatureException
	{
		return executeSelectStatement(externalConnection, query, paramValues);
	}

	// ----------------------------  execute update methods
	public int executeUpdate(Connection externalConnection, String query)
	{
		return executeUpdateLocal(externalConnection, query, null);
	}

	public int executeUpdate(Connection externalConnection, String query, Object paramValue) throws SignatureException
	{
		return executeUpdateLocal(externalConnection, query, new Object[] { paramValue });
	}

	public int executeUpdate(Connection externalConnection, String query, Object[] paramValues) throws SignatureException
	{
		return executeUpdateLocal(externalConnection, query, paramValues);
	}

	// ----------------------------  execute update one row methods
	public int executeUpdateOneRow(Connection externalConnection, String query, Object paramValue) throws SignatureException
	{
		return executeUpdateOneRowLocal(externalConnection, query, new Object[] { paramValue });
	}

	public int executeUpdateOneRow(Connection externalConnection, String query, Object[] paramValues) throws SignatureException
	{
		return executeUpdateOneRowLocal(externalConnection, query, paramValues);
	}

	// ---------------------------- non commit connection methods
	public Connection getNonCommitConnection()
	{
		try
		{
			Class.forName(driverClassString);
			Connection nonCommitConnection = DriverManager.getConnection(url, username, password);
			//			logger.info(nonCommitConnection.toString());
			nonCommitConnection.setAutoCommit(false);
			return nonCommitConnection;
		}
		catch (Exception e)
		{
			logger.error(e);
			throw new SignatureException(e);
		}

	}

	public void commit(Connection externalConnection)
	{
		try
		{
			if (externalConnection != null)
				externalConnection.commit();
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public void rollback(Connection externalConnection)
	{
		try
		{
			if (externalConnection != null)
				externalConnection.rollback();
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public void close(Connection externalConnection)
	{
		try
		{
			if (externalConnection != null)
				externalConnection.close();
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	// ---------------------------- local methods
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T extends SignatureDbo> T fetchObjectLocal(Connection localConnection, String query, Object[] paramValues, Class returnClazz) throws SignatureException
	{

		ExecuteSelectStamentResult result = null;
		try
		{
			result = executeSelectStatement(localConnection, query, paramValues);

			if (result == null)
			{
				logger.info("Result null for query " + query);
				throw prepareException("Result null for query : ", query, paramValues);

			}
			if (!result.next())
				throw prepareException(" Could not find any row for Query :", query, paramValues);

			Constructor constructor = returnClazz.getConstructor(ResultSet.class);
			SignatureDbo rtrdbo = (SignatureDbo) constructor.newInstance(result.getRs());

			if (result.next())
				throw prepareException(" Found more than 1 row for Query :", query, paramValues);
			return (T) rtrdbo;
		}
		catch (Exception e)
		{
			throw new SignatureException(e);
		}
		finally
		{
			if (result != null)
				result.close();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <T extends SignatureDbo> List<T> fetchListLocal(Connection localConnection, String query, Object[] paramValues, Class returnClazz) throws SignatureException
	{
		ExecuteSelectStamentResult result = null;
		try
		{
			result = executeSelectStatement(localConnection, query, paramValues);
			if (result == null)
			{
				logger.info("Result is null " + query);
				return null;
			}

			Constructor constructor = returnClazz.getConstructor(ResultSet.class);
			List<T> rtrDbos = new LinkedList<T>();
			while (result.next())
			{
				SignatureDbo rtrDbo = (SignatureDbo) constructor.newInstance(result.getRs());
				rtrDbos.add((T) rtrDbo);
			}
			return rtrDbos;
		}
		catch (Exception e)
		{
			throw new SignatureException(e);
		}
		finally
		{
			if (result != null)
				result.close();
		}
	}

	private Map<Integer, List<Object[]>> fetchHashLocal(Connection localConnection, String query, Object[] paramValues, int size) throws SignatureException
	{
		ExecuteSelectStamentResult result = null;
		Map<Integer, List<Object[]>> hashes = new HashMap<Integer, List<Object[]>>();
		try
		{
			result = executeSelectStatement(localConnection, query, paramValues);
			while (result.next())
			{
				Object[] row = new Object[size];
				for (int i = 1; i <= size; i++)
					row[i - 1] = result.getRs().getObject(i);
				int key = computeHash(row);
				List<Object[]> otherRows = hashes.get(key);
				if (otherRows == null)
				{
					otherRows = new ArrayList<Object[]>();
					hashes.put(key, otherRows);
				}
				otherRows.add(row);
			}
			return hashes;
		}
		catch (Exception e)
		{
			throw new SignatureException(e);
		}
		finally
		{
			if (result != null)
				result.close();
		}
	}

	private int computeHash(Object[] row)
	{
		StringBuffer sb = new StringBuffer();
		for (Object o : row)
		{
			sb.append(Constants.KEYDELIMITER);
			if (o != null)
				sb.append(o.toString());
			else
				sb.append(Constants.NULLSTRING);
		}
		return sb.toString().hashCode();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <T extends SignatureDbo> T fetchObjectIfExistsLocal(Connection localConnection, String query, Object[] paramValues, Class returnClazz) throws SignatureException
	{

		ExecuteSelectStamentResult result = null;
		try
		{
			result = executeSelectStatement(localConnection, query, paramValues);

			if (result == null)
			{
				logger.info("Result null for query " + query);
				return null;
			}

			if (!result.next())
				return null;

			Constructor constructor = returnClazz.getConstructor(ResultSet.class);
			SignatureDbo rtrdbo = (SignatureDbo) constructor.newInstance(result.getRs());

			if (result.next())
				throw prepareException(" Found more than 1 row for Query :", query, paramValues);
			return (T) rtrdbo;
		}
		catch (Exception e)
		{
			throw new SignatureException(e);
		}
		finally
		{
			if (result != null)
				result.close();
		}
	}

	public String getUserName()
	{
		return username;
	}

}
