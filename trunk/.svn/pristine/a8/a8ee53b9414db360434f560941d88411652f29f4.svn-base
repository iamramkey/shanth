/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dba;

import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.dbo.AuditFileImpl;
import se.signa.signature.gen.dba.AuditFileDba;
import se.signa.signature.gen.dbo.AuditFile;
import se.signa.signature.helpers.RefDBHelper;

public class AuditFileDbaImpl extends AuditFileDba
{

	public List<AuditFile> fetchAllAuditFilesByDttm(String whereClause, Object[] whereParameters)
	{
		String query = " select auf.* from FILETBL fil , AUDIT_FILE auf";
		query = query + " LEFT OUTER JOIN AUDIT_FILE pauf ON auf.auf_parent_auf_id = pauf.auf_id ";
		query = query + " LEFT OUTER JOIN FILETBL pfil ON pauf.fil_id = pfil.fil_id ";
		query = query + " where auf.FIL_ID=fil.FIL_ID " + whereClause + " order by auf.auf_created_dttm ";
		return RefDBHelper.getDB().fetchList(query, whereParameters, AuditFileImpl.class);
	}

	public void addFileNameFilter(String filName, StringBuffer whereClause, List<Object> whereParams)
	{
		whereClause.append(" and fil.FIL_NAME like ?");
		whereParams.add("%" + filName + "%");
	}

	public void addParentFileNameFilter(String filName, StringBuffer whereClause, List<Object> whereParams)
	{
		whereClause.append(" and pfil.FIL_NAME like ?");
		whereParams.add("%" + filName + "%");
	}

	public void addAufFilTypeFilter(String aufFilType, StringBuffer whereClause, List<Object> whereParams)
	{
		whereClause.append(" and auf.auf_file_type like ? ");
		whereParams.add("%" + aufFilType + "%");
	}

	public void addRowCountFilter(int rowCount, StringBuffer whereClause)
	{
		whereClause.append(" and rownum < " + rowCount);
	}

	public void addAufDttmFrom(DateTime fromDttm, StringBuffer whereClause, List<Object> whereParams)
	{
		whereClause.append(" and auf.auf_created_dttm >= ? ");
		whereParams.add(fromDttm);
	}

	public void addAufDttmTo(DateTime toDttm, StringBuffer whereClause, List<Object> whereParams)
	{
		whereClause.append(" and auf.auf_created_dttm <= ?");
		whereParams.add(toDttm);
	}

	public Object getDefaultWhereClause()
	{
		return " and auf.AUF_ID is not null";
	}

}