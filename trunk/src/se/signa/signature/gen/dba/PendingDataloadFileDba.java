/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.PendingDataloadFileDbaImpl;
import se.signa.signature.dbo.PendingDataloadFileImpl;
import se.signa.signature.gen.dbo.PendingDataloadFile;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class PendingDataloadFileDba extends SignatureDba<PendingDataloadFile>
{
	private static PendingDataloadFileDbaImpl INSTANCE;

	public static PendingDataloadFileDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new PendingDataloadFileDbaImpl();
		return INSTANCE;
	}

	public PendingDataloadFileDba()
	{
		tableName = "pending_dataload_file";
		tablePrefix = "pdf";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Pdf Id");
		columns.add("Fil Id");
		columns.add("Acc Id");
		columns.add("Pdf Name");
		columns.add("Pdf Table Name");
		columns.add("Pdf Poll Fl");
		columns.add("Job Id");
		columns.add("Pdf User Name");
		columns.add("Pdf Dttm");
		return columns;
	}

	@Override
	public PendingDataloadFile createEmptyDbo()
	{
		return new PendingDataloadFileImpl();
	}

	@Override
	public void checkDuplicates(PendingDataloadFile dbo)
	{
		checkDuplicateGM(dbo.getPdfName(), "pdf_name", dbo.getPk());
		checkDuplicateGM(dbo.getPdfBk(), "pdf_bk", dbo.getPk());
	}

	@Override
	public int create(PendingDataloadFile pendingDataloadFile, int usrId)
	{
		return create(null, pendingDataloadFile, usrId);
	}

	public int create(Connection connection, PendingDataloadFile pendingDataloadFile, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("pdf_id", "pending_dataload_file");
		String bk = pendingDataloadFile.getDisplayField();

		String query = " insert into pending_dataload_file values(?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, pendingDataloadFile.getFilId(), pendingDataloadFile.getAccId(), pendingDataloadFile.getPdfName(), pendingDataloadFile.getPdfTableName(), pendingDataloadFile.getPdfPollFl(), pendingDataloadFile.getJobId(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(PendingDataloadFile pendingDataloadFile, int usrId)
	{
		update(null, pendingDataloadFile, usrId);
	}

	public void update(Connection connection, PendingDataloadFile pendingDataloadFile, int usrId)
	{
		String query = " update pending_dataload_file set  fil_id = ? , acc_id = ? , pdf_name = ? , pdf_table_name = ? , pdf_poll_fl = ? , job_id = ? , pdf_modified_usr_id = ? , pdf_modified_dttm = ?  where pdf_id = ?  ";
		Object[] paramValues = new Object[] { pendingDataloadFile.getFilId(), pendingDataloadFile.getAccId(), pendingDataloadFile.getPdfName(), pendingDataloadFile.getPdfTableName(), pendingDataloadFile.getPdfPollFl(), pendingDataloadFile.getJobId(), usrId, new DateTime(), pendingDataloadFile.getPdfId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into pending_dataload_file values(?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { pendingDataloadFile.getPdfId(), pendingDataloadFile.getFilId(), pendingDataloadFile.getAccId(), pendingDataloadFile.getPdfName(), pendingDataloadFile.getPdfTableName(), pendingDataloadFile.getPdfPollFl(), pendingDataloadFile.getJobId(), pendingDataloadFile.getPdfBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<PendingDataloadFile> fetchAll()
	{
		String query = " select * from pending_dataload_file ";
		return RefDBHelper.getDB().fetchList(query, PendingDataloadFileImpl.class);
	}

	public List<PendingDataloadFile> fetchAuditRowsByPk(int id)
	{
		String query = " select * from pending_dataload_file where pdf_id=? order by pdf_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, PendingDataloadFileImpl.class);
	}

	public PendingDataloadFile fetchByPk(int pdfId)
	{
		return fetchByPk(null, pdfId);
	}

	public PendingDataloadFile fetchByPk(Connection connection, int pdfId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from pending_dataload_file where pdf_id=? ", pdfId, PendingDataloadFileImpl.class);
	}

	public PendingDataloadFile fetchByBk(String pdfBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_dataload_file where pdf_bk=? ", pdfBk, PendingDataloadFileImpl.class);
	}

	public PendingDataloadFile fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_dataload_file where pdf_name=? ", displayField, PendingDataloadFileImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("pdf_name", "pending_dataload_file");
	}

}