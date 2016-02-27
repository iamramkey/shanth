/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.PendingRerateTransferFileDbaImpl;
import se.signa.signature.dbo.PendingRerateTransferFileImpl;
import se.signa.signature.gen.dbo.PendingRerateTransferFile;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class PendingRerateTransferFileDba extends SignatureDba<PendingRerateTransferFile>
{
	private static PendingRerateTransferFileDbaImpl INSTANCE;

	public static PendingRerateTransferFileDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new PendingRerateTransferFileDbaImpl();
		return INSTANCE;
	}

	public PendingRerateTransferFileDba()
	{
		tableName = "pending_rerate_transfer_file";
		tablePrefix = "prt";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Prt Id");
		columns.add("Fts Id");
		columns.add("Fil Id");
		columns.add("Prt Name");
		columns.add("Prt User Name");
		columns.add("Prt Dttm");
		return columns;
	}

	@Override
	public PendingRerateTransferFile createEmptyDbo()
	{
		return new PendingRerateTransferFileImpl();
	}

	@Override
	public void checkDuplicates(PendingRerateTransferFile dbo)
	{
		checkDuplicateGM(dbo.getPrtName(), "prt_name", dbo.getPk());
		checkDuplicateGM(dbo.getPrtBk(), "prt_bk", dbo.getPk());
	}

	@Override
	public int create(PendingRerateTransferFile pendingRerateTransferFile, int usrId)
	{
		return create(null, pendingRerateTransferFile, usrId);
	}

	public int create(Connection connection, PendingRerateTransferFile pendingRerateTransferFile, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("prt_id", "pending_rerate_transfer_file");
		String bk = pendingRerateTransferFile.getDisplayField();

		String query = " insert into pending_rerate_transfer_file values(?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, pendingRerateTransferFile.getFtsId(), pendingRerateTransferFile.getFilId(), pendingRerateTransferFile.getPrtName(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(PendingRerateTransferFile pendingRerateTransferFile, int usrId)
	{
		update(null, pendingRerateTransferFile, usrId);
	}

	public void update(Connection connection, PendingRerateTransferFile pendingRerateTransferFile, int usrId)
	{
		String query = " update pending_rerate_transfer_file set  fts_id = ? , fil_id = ? , prt_name = ? , prt_modified_usr_id = ? , prt_modified_dttm = ?  where prt_id = ?  ";
		Object[] paramValues = new Object[] { pendingRerateTransferFile.getFtsId(), pendingRerateTransferFile.getFilId(), pendingRerateTransferFile.getPrtName(), usrId, new DateTime(), pendingRerateTransferFile.getPrtId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into pending_rerate_transfer_file values(?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { pendingRerateTransferFile.getPrtId(), pendingRerateTransferFile.getFtsId(), pendingRerateTransferFile.getFilId(), pendingRerateTransferFile.getPrtName(), pendingRerateTransferFile.getPrtBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<PendingRerateTransferFile> fetchAll()
	{
		String query = " select * from pending_rerate_transfer_file ";
		return RefDBHelper.getDB().fetchList(query, PendingRerateTransferFileImpl.class);
	}

	public List<PendingRerateTransferFile> fetchAuditRowsByPk(int id)
	{
		String query = " select * from pending_rerate_transfer_file where prt_id=? order by prt_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, PendingRerateTransferFileImpl.class);
	}

	public PendingRerateTransferFile fetchByPk(int prtId)
	{
		return fetchByPk(null, prtId);
	}

	public PendingRerateTransferFile fetchByPk(Connection connection, int prtId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from pending_rerate_transfer_file where prt_id=? ", prtId, PendingRerateTransferFileImpl.class);
	}

	public PendingRerateTransferFile fetchByBk(String prtBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_rerate_transfer_file where prt_bk=? ", prtBk, PendingRerateTransferFileImpl.class);
	}

	public PendingRerateTransferFile fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_rerate_transfer_file where prt_name=? ", displayField, PendingRerateTransferFileImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("prt_name", "pending_rerate_transfer_file");
	}

}