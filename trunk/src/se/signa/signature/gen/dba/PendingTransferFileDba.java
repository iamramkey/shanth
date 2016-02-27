/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.PendingTransferFileDbaImpl;
import se.signa.signature.dbo.PendingTransferFileImpl;
import se.signa.signature.gen.dbo.PendingTransferFile;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class PendingTransferFileDba extends SignatureDba<PendingTransferFile>
{
	private static PendingTransferFileDbaImpl INSTANCE;

	public static PendingTransferFileDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new PendingTransferFileDbaImpl();
		return INSTANCE;
	}

	public PendingTransferFileDba()
	{
		tableName = "pending_transfer_file";
		tablePrefix = "ptf";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Ptf Id");
		columns.add("Fts Id");
		columns.add("Fil Id");
		columns.add("Ptf Name");
		columns.add("Ptf User Name");
		columns.add("Ptf Dttm");
		return columns;
	}

	@Override
	public PendingTransferFile createEmptyDbo()
	{
		return new PendingTransferFileImpl();
	}

	@Override
	public void checkDuplicates(PendingTransferFile dbo)
	{
		checkDuplicateGM(dbo.getPtfName(), "ptf_name", dbo.getPk());
		checkDuplicateGM(dbo.getPtfBk(), "ptf_bk", dbo.getPk());
	}

	@Override
	public int create(PendingTransferFile pendingTransferFile, int usrId)
	{
		return create(null, pendingTransferFile, usrId);
	}

	public int create(Connection connection, PendingTransferFile pendingTransferFile, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("ptf_id", "pending_transfer_file");
		String bk = pendingTransferFile.getDisplayField();

		String query = " insert into pending_transfer_file values(?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, pendingTransferFile.getFtsId(), pendingTransferFile.getFilId(), pendingTransferFile.getPtfName(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(PendingTransferFile pendingTransferFile, int usrId)
	{
		update(null, pendingTransferFile, usrId);
	}

	public void update(Connection connection, PendingTransferFile pendingTransferFile, int usrId)
	{
		String query = " update pending_transfer_file set  fts_id = ? , fil_id = ? , ptf_name = ? , ptf_modified_usr_id = ? , ptf_modified_dttm = ?  where ptf_id = ?  ";
		Object[] paramValues = new Object[] { pendingTransferFile.getFtsId(), pendingTransferFile.getFilId(), pendingTransferFile.getPtfName(), usrId, new DateTime(), pendingTransferFile.getPtfId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into pending_transfer_file values(?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { pendingTransferFile.getPtfId(), pendingTransferFile.getFtsId(), pendingTransferFile.getFilId(), pendingTransferFile.getPtfName(), pendingTransferFile.getPtfBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<PendingTransferFile> fetchAll()
	{
		String query = " select * from pending_transfer_file ";
		return RefDBHelper.getDB().fetchList(query, PendingTransferFileImpl.class);
	}

	public List<PendingTransferFile> fetchAuditRowsByPk(int id)
	{
		String query = " select * from pending_transfer_file where ptf_id=? order by ptf_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, PendingTransferFileImpl.class);
	}

	public PendingTransferFile fetchByPk(int ptfId)
	{
		return fetchByPk(null, ptfId);
	}

	public PendingTransferFile fetchByPk(Connection connection, int ptfId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from pending_transfer_file where ptf_id=? ", ptfId, PendingTransferFileImpl.class);
	}

	public PendingTransferFile fetchByBk(String ptfBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_transfer_file where ptf_bk=? ", ptfBk, PendingTransferFileImpl.class);
	}

	public PendingTransferFile fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_transfer_file where ptf_name=? ", displayField, PendingTransferFileImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("ptf_name", "pending_transfer_file");
	}

}