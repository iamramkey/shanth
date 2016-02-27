/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.PendingRaterFileDbaImpl;
import se.signa.signature.dbo.PendingRaterFileImpl;
import se.signa.signature.gen.dbo.PendingRaterFile;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class PendingRaterFileDba extends SignatureDba<PendingRaterFile>
{
	private static PendingRaterFileDbaImpl INSTANCE;

	public static PendingRaterFileDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new PendingRaterFileDbaImpl();
		return INSTANCE;
	}

	public PendingRaterFileDba()
	{
		tableName = "pending_rater_file";
		tablePrefix = "pra";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Pra Id");
		columns.add("Pra Transfer Jbt Id");
		columns.add("Fil Id");
		columns.add("Acc Id");
		columns.add("Pra Name");
		columns.add("Pra User Name");
		columns.add("Pra Dttm");
		return columns;
	}

	@Override
	public PendingRaterFile createEmptyDbo()
	{
		return new PendingRaterFileImpl();
	}

	@Override
	public void checkDuplicates(PendingRaterFile dbo)
	{
		checkDuplicateGM(dbo.getPraName(), "pra_name", dbo.getPk());
		checkDuplicateGM(dbo.getPraBk(), "pra_bk", dbo.getPk());
	}

	@Override
	public int create(PendingRaterFile pendingRaterFile, int usrId)
	{
		return create(null, pendingRaterFile, usrId);
	}

	public int create(Connection connection, PendingRaterFile pendingRaterFile, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("pra_id", "pending_rater_file");
		String bk = pendingRaterFile.getDisplayField();

		String query = " insert into pending_rater_file values(?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, pendingRaterFile.getPraTransferJbtId(), pendingRaterFile.getFilId(), pendingRaterFile.getAccId(), pendingRaterFile.getPraName(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(PendingRaterFile pendingRaterFile, int usrId)
	{
		update(null, pendingRaterFile, usrId);
	}

	public void update(Connection connection, PendingRaterFile pendingRaterFile, int usrId)
	{
		String query = " update pending_rater_file set  pra_transfer_jbt_id = ? , fil_id = ? , acc_id = ? , pra_name = ? , pra_modified_usr_id = ? , pra_modified_dttm = ?  where pra_id = ?  ";
		Object[] paramValues = new Object[] { pendingRaterFile.getPraTransferJbtId(), pendingRaterFile.getFilId(), pendingRaterFile.getAccId(), pendingRaterFile.getPraName(), usrId, new DateTime(), pendingRaterFile.getPraId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into pending_rater_file values(?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { pendingRaterFile.getPraId(), pendingRaterFile.getPraTransferJbtId(), pendingRaterFile.getFilId(), pendingRaterFile.getAccId(), pendingRaterFile.getPraName(), pendingRaterFile.getPraBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<PendingRaterFile> fetchAll()
	{
		String query = " select * from pending_rater_file ";
		return RefDBHelper.getDB().fetchList(query, PendingRaterFileImpl.class);
	}

	public List<PendingRaterFile> fetchAuditRowsByPk(int id)
	{
		String query = " select * from pending_rater_file where pra_id=? order by pra_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, PendingRaterFileImpl.class);
	}

	public PendingRaterFile fetchByPk(int praId)
	{
		return fetchByPk(null, praId);
	}

	public PendingRaterFile fetchByPk(Connection connection, int praId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from pending_rater_file where pra_id=? ", praId, PendingRaterFileImpl.class);
	}

	public PendingRaterFile fetchByBk(String praBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_rater_file where pra_bk=? ", praBk, PendingRaterFileImpl.class);
	}

	public PendingRaterFile fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_rater_file where pra_name=? ", displayField, PendingRaterFileImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("pra_name", "pending_rater_file");
	}

}