/**
 * Copyright SIGNA AB, STOCKHOLM, SWEDEN
 */

package se.signa.signature.helpers.server;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import org.apache.log4j.Logger;

import se.signa.signature.common.Constants;
import se.signa.signature.common.SignatureException;
import se.signa.signature.gen.dba.ServerSettingsDba;
import se.signa.signature.gen.dbo.ServerSettings;

public class ServerLocker
{

	private static Logger logger = Logger.getLogger(ServerLocker.class);

	// Some class vars and a failsafe lock release.
	private File lockTempDir = null;
	private FileLock fileLock = null;
	private String lockName;

	public ServerLocker(String lockName)
	{
		this.lockName = lockName;
	}

	public boolean acquireLock()
	{
		try
		{
			String nodeName = "webserver";
			if (crossProcessLockAcquire(3000))
			{
				logger.debug("Acquired lock " + lockName + " by " + nodeName);

				Thread.sleep(1000);
				return true;
			}
			else
			{
				logger.debug("Failed to acquire lock " + lockName + " by " + nodeName);
				return false;
			}
		}
		catch (InterruptedException e)
		{
			throw new SignatureException(e);
		}
	}

	// Acquire - Returns success ( true/false )
	private boolean crossProcessLockAcquire(final long waitMS)
	{
		if (fileLock == null && lockName != null && waitMS > 0)
		{
			try
			{
				long dropDeadTime = System.currentTimeMillis() + waitMS;
				ServerSettings serverSettings = ServerSettingsDba.getI().fetchByDisplayField(Constants.dataDir);
				lockTempDir = new File(serverSettings.getSesValue(), Constants.LOCKDIR);
				if (!lockTempDir.exists())
					lockTempDir.mkdirs();
				File file = new File(lockTempDir, lockName + ".lock");
				if (!file.exists())
				{
					logger.debug("Creating lock file : " + lockName);
					if (!file.createNewFile())
						return false;
					else
						logger.debug("Created lock file : " + lockName);
				}
				RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
				FileChannel fileChannel = randomAccessFile.getChannel();
				while (System.currentTimeMillis() < dropDeadTime)
				{
					fileLock = fileChannel.tryLock();
					if (fileLock != null)
					{
						break;
					}
					Thread.sleep(250); // 4 attempts/sec
				}
			}
			catch (Exception e)
			{
				//logger.fatal(e);
				//throw new StigException(e);
			}
		}
		return fileLock == null ? false : true;
	}

	// Release
	public void releaseLock()
	{
		if (fileLock != null)
		{
			try
			{
				fileLock.release();
				fileLock = null;
			}
			catch (IOException e)
			{
				logger.fatal(e);
			}
		}
	}

	//    static
	//    {
	//	Runtime.getRuntime().addShutdownHook(new Thread()
	//	{
	//	    public void run()
	//	    {
	//		crossProcessLockRelease();
	//		logger.debug("Shutdown");
	//	    }
	//	});
	//    }

}
