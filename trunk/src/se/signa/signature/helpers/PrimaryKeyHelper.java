/**
 * Copyright SIGNA AB, STOCKHOLM, SWEDEN
 */

package se.signa.signature.helpers;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import se.signa.signature.common.Constants;
import se.signa.signature.common.SignatureException;
import se.signa.signature.helpers.nextid.NextIdDBHelper;
import se.signa.signature.helpers.server.ServerLocker;

public class PrimaryKeyHelper
{

	private static int DEFAULT_BATCH_SIZE = 10;
	private static Logger logger = Logger.getLogger(PrimaryKeyHelper.class);
	private static PrimaryKeyHelper INSTANCE = null;

	private Map<String, ServerLocker> lockers;
	private Map<String, Integer> currentPriIds;
	private Map<String, Integer> allotedMaxPriIds;

	// this is a singleton class
	public synchronized static PrimaryKeyHelper getPKH() throws SignatureException
	{
		if (INSTANCE == null)
		{
			INSTANCE = new PrimaryKeyHelper();
		}
		return INSTANCE;
	}

	private PrimaryKeyHelper() throws SignatureException
	{
		lockers = new HashMap<String, ServerLocker>();
		currentPriIds = new HashMap<String, Integer>();
		allotedMaxPriIds = new HashMap<String, Integer>();
		logger.info("Primary Key Helper initialised");
	}

	public synchronized int getPrimaryKey(String column, String tableName)
	{
		return getPrimaryKey(column, tableName, DEFAULT_BATCH_SIZE);
	}

	public synchronized int getPrimaryKey(String column, String tableName, int batchSize)
	{
		ServerLocker serverLocker = getCurrentLocker(tableName);
		int currentPriId = currentPriIds.get(tableName);
		int allotedMaxPriId = allotedMaxPriIds.get(tableName);
		if (allotedMaxPriId <= currentPriId)
		{
			allocatePrimaryKey(column, tableName, batchSize);
			currentPriId = currentPriIds.get(tableName);
			allotedMaxPriId = allotedMaxPriIds.get(tableName);
		}
		currentPriId++;
		currentPriIds.put(tableName, currentPriId);
		return currentPriId++;

	}

	private ServerLocker getCurrentLocker(String tableName)
	{
		ServerLocker serverLocker = lockers.get(tableName);
		if (serverLocker == null)
		{
			serverLocker = new ServerLocker(Constants.PRIMARYKEYLOCK + tableName);
			lockers.put(tableName, serverLocker);
			currentPriIds.put(tableName, 0);
			allotedMaxPriIds.put(tableName, 0);
		}
		return serverLocker;
	}

	public synchronized void allocatePrimaryKey(String column, String tableName, int batchSize)
	{
		ServerLocker serverLocker = getCurrentLocker(tableName);
		try
		{
			while (!serverLocker.acquireLock())
			{
				Thread.sleep(Constants.PRIMARYKEYPOLLERSECS);
			}
			int allotedMaxPriId = NextIdDBHelper.getNextIdPlus(column, tableName, batchSize);
			int currentPriId = allotedMaxPriId - batchSize;
			currentPriIds.put(tableName, currentPriId);
			allotedMaxPriIds.put(tableName, allotedMaxPriId);
		}
		catch (InterruptedException e)
		{
			throw new SignatureException(e);
		}
		finally
		{
			serverLocker.releaseLock();
		}
	}

}
