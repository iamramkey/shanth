/**
 * Copyright SIGNA AB, STOCKHOLM, SWEDEN
 */

package se.signa.signature.helpers.nextid;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import se.signa.signature.common.SignatureException;
import se.signa.signature.helpers.DBHelper.ExecuteSelectStamentResult;
import se.signa.signature.helpers.RefDBHelper;

public class NextIdDBHelper
{

	private static Logger logger = Logger.getLogger(NextIdDBHelper.class);

	public synchronized static int getNextIdPlus(String column, String neiName, int batchSize)
	{
		logger.info("Next object Id for : " + neiName + " batch size : " + batchSize);
		NextId nextId = null;
		int nextNo = 1;
		String query = "select * from next_id where nei_name = ?";
		nextId = RefDBHelper.getDB().fetchObjectIfExists(query, neiName, NextId.class);
		if (nextId != null)
		{
			nextNo = nextId.getNeiNo() + batchSize;
			String updateQuery = " update next_id set nei_no = ? where nei_name = ? ";
			RefDBHelper.getDB().executeUpdateOneRow(updateQuery, new Object[] { nextNo, neiName });
			return nextNo;
		}
		logger.info("Next object Id row for : " + neiName + " does not exists, so creating");
		ExecuteSelectStamentResult result = null;
		try
		{
			String selectQuery = "select max( " + column + " ) as " + column + " from " + neiName;
			result = RefDBHelper.getDB().execute(selectQuery);
			if (result.next())
				nextNo = result.getRs().getInt(1) + batchSize;

			String insertQuery = " insert into next_id values (  ? , ? ) ";
			RefDBHelper.getDB().executeUpdateOneRow(insertQuery, new Object[] { neiName, nextNo });

			nextId = RefDBHelper.getDB().fetchObjectIfExists(query, neiName, NextId.class);

			if (nextId == null) // cant be null now..just sanity check
				throw new SignatureException("Next Id Object not available, Contact support team :" + neiName + " / " + batchSize);

			return nextNo;
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

}
