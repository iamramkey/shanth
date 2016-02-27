/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dba;

import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.dbo.FiletblImpl;
import se.signa.signature.gen.dba.FiletblDba;
import se.signa.signature.gen.dbo.Filetbl;
import se.signa.signature.helpers.RefDBHelper;

public class FiletblDbaImpl extends FiletblDba
{

	public List<Filetbl> fetchAllFiles(String whereClause, Object[] whereParameters)
	{
		String query = " select * from filetbl " + whereClause + " order by FIL_CREATED_DTTM DESC ";
		return RefDBHelper.getDB().fetchList(query, whereParameters, FiletblImpl.class);
	}

	public int fetchCount(String whereClause, Object[] whereParameters)
	{
		String query = " from filetbl " + whereClause;
		return RefDBHelper.getDB().fetchCount(query, whereParameters);
	}

	public String getDefaultWhereClause()
	{
		return " where fil_id is not null ";
	}

	public void addFileNameFilter(String filName, StringBuffer whereClause, List<Object> whereParams)
	{
		whereClause.append(" and fil_name like '%" + filName + "%'");
		//		whereParams.add(filName);
	}

	public void addFilDttmFrom(DateTime filDttmFrom, StringBuffer whereClause, List<Object> whereParams)
	{
		whereClause.append(" and FIL_CREATED_DTTM >= ? ");
		whereParams.add(filDttmFrom);
	}

	public void addFilDttmTo(DateTime filDttmTo, StringBuffer whereClause, List<Object> whereParams)
	{
		whereClause.append(" and FIL_CREATED_DTTM <= ?");
		whereParams.add(filDttmTo);
	}

	public void addRowCountFilter(int rowCount, StringBuffer whereClause)
	{
		whereClause.append(" and rownum < " + rowCount);
	}

}