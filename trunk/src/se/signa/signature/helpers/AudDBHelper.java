/**
 * Copyright SIGNA AB, STOCKHOLM, SWEDEN
 */

package se.signa.signature.helpers;

import org.apache.log4j.Logger;

import se.signa.signature.common.SignatureException;

public class AudDBHelper extends RefDBHelper
{

	private static AudDBHelper INSTANCE = null;

	// this is a singleton class- single entry point
	public synchronized static AudDBHelper getDB() throws SignatureException
	{
		if (INSTANCE == null)
			INSTANCE = new AudDBHelper();
		return INSTANCE;
	}

	private AudDBHelper() throws SignatureException
	{
		logger = Logger.getLogger(AudDBHelper.class);
		logger.info("Aud database initialised");
	}

	@Override
	protected String getCfgFile()
	{
		return "auddb.cfg";
	}
}
