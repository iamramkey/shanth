/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.ExtractionDbaImpl;
import se.signa.signature.dbo.ExtractionImpl;
import se.signa.signature.gen.dbo.Extraction;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class ExtractionDba extends SignatureDba<Extraction>
{
	private static ExtractionDbaImpl INSTANCE;

	public static ExtractionDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new ExtractionDbaImpl();
		return INSTANCE;
	}

	public ExtractionDba()
	{
		tableName = "extraction";
		tablePrefix = "etr";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Etr Id");
		columns.add("Del Id");
		columns.add("Acc Id");
		columns.add("Fil Id");
		columns.add("Job Id");
		columns.add("Jbt Id");
		columns.add("Etr Row No");
		columns.add("Etr Schedule Dttm");
		columns.add("Etr Name");
		columns.add("Etr From Dttm");
		columns.add("Etr To Dttm");
		columns.add("Status");
		columns.add("Etr User Name");
		columns.add("Etr Dttm");
		return columns;
	}

	@Override
	public Extraction createEmptyDbo()
	{
		return new ExtractionImpl();
	}

	@Override
	public void checkDuplicates(Extraction dbo)
	{
		checkDuplicateGM(dbo.getEtrName(), "etr_name", dbo.getPk());
		checkDuplicateGM(dbo.getEtrBk(), "etr_bk", dbo.getPk());
	}

	@Override
	public int create(Extraction extraction, int usrId)
	{
		return create(null, extraction, usrId);
	}

	public int create(Connection connection, Extraction extraction, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("etr_id", "extraction");
		String bk = extraction.getDisplayField();

		String query = " insert into extraction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, extraction.getDelId(), extraction.getAccId(), extraction.getFilId(), extraction.getJobId(), extraction.getJbtId(), extraction.getEtrRowNo(), extraction.getEtrScheduleDttm(), extraction.getEtrName(), extraction.getEtrFromDttm(), extraction.getEtrToDttm(), extraction.getStatus(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(Extraction extraction, int usrId)
	{
		update(null, extraction, usrId);
	}

	public void update(Connection connection, Extraction extraction, int usrId)
	{
		String query = " update extraction set  del_id = ? , acc_id = ? , fil_id = ? , job_id = ? , jbt_id = ? , etr_row_no = ? , etr_schedule_dttm = ? , etr_name = ? , etr_from_dttm = ? , etr_to_dttm = ? , status = ? , etr_modified_usr_id = ? , etr_modified_dttm = ?  where etr_id = ?  ";
		Object[] paramValues = new Object[] { extraction.getDelId(), extraction.getAccId(), extraction.getFilId(), extraction.getJobId(), extraction.getJbtId(), extraction.getEtrRowNo(), extraction.getEtrScheduleDttm(), extraction.getEtrName(), extraction.getEtrFromDttm(), extraction.getEtrToDttm(), extraction.getStatus(), usrId, new DateTime(), extraction.getEtrId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into extraction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { extraction.getEtrId(), extraction.getDelId(), extraction.getAccId(), extraction.getFilId(), extraction.getJobId(), extraction.getJbtId(), extraction.getEtrRowNo(), extraction.getEtrScheduleDttm(), extraction.getEtrName(), extraction.getEtrFromDttm(), extraction.getEtrToDttm(), extraction.getStatus(), extraction.getEtrBk(), usrId, usrId,
				new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<Extraction> fetchAll()
	{
		String query = " select * from extraction ";
		return RefDBHelper.getDB().fetchList(query, ExtractionImpl.class);
	}

	public List<Extraction> fetchAuditRowsByPk(int id)
	{
		String query = " select * from extraction where etr_id=? order by etr_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, ExtractionImpl.class);
	}

	public Extraction fetchByPk(int etrId)
	{
		return fetchByPk(null, etrId);
	}

	public Extraction fetchByPk(Connection connection, int etrId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from extraction where etr_id=? ", etrId, ExtractionImpl.class);
	}

	public Extraction fetchByBk(String etrBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from extraction where etr_bk=? ", etrBk, ExtractionImpl.class);
	}

	public Extraction fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from extraction where etr_name=? ", displayField, ExtractionImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("etr_name", "extraction");
	}

}