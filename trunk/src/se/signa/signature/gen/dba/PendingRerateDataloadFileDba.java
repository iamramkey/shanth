/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.PendingRerateDataloadFileDbaImpl;
import se.signa.signature.dbo.PendingRerateDataloadFileImpl;
import se.signa.signature.gen.dbo.PendingRerateDataloadFile;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class PendingRerateDataloadFileDba extends SignatureDba<PendingRerateDataloadFile>
{
	private static PendingRerateDataloadFileDbaImpl INSTANCE;

	public static PendingRerateDataloadFileDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new PendingRerateDataloadFileDbaImpl();
		return INSTANCE;
	}

	public PendingRerateDataloadFileDba()
	{
		tableName = "pending_rerate_dataload_file";
		tablePrefix = "pre";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Pre Id");
		columns.add("Fil Id");
		columns.add("Acc Id");
		columns.add("Pre Table Name");
		columns.add("Pre Poll Fl");
		columns.add("Job Id");
		columns.add("Pre Name");
		columns.add("Pre User Name");
		columns.add("Pre Dttm");
		return columns;
	}

	@Override
	public PendingRerateDataloadFile createEmptyDbo()
	{
		return new PendingRerateDataloadFileImpl();
	}

	@Override
	public void checkDuplicates(PendingRerateDataloadFile dbo)
	{
		checkDuplicateGM(dbo.getPreName(), "pre_name", dbo.getPk());
		checkDuplicateGM(dbo.getPreBk(), "pre_bk", dbo.getPk());
	}

	@Override
	public int create(PendingRerateDataloadFile pendingRerateDataloadFile, int usrId)
	{
		return create(null, pendingRerateDataloadFile, usrId);
	}

	public int create(Connection connection, PendingRerateDataloadFile pendingRerateDataloadFile, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("pre_id", "pending_rerate_dataload_file");
		String bk = pendingRerateDataloadFile.getDisplayField();

		String query = " insert into pending_rerate_dataload_file values(?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, pendingRerateDataloadFile.getFilId(), pendingRerateDataloadFile.getAccId(), pendingRerateDataloadFile.getPreTableName(), pendingRerateDataloadFile.getPrePollFl(), pendingRerateDataloadFile.getJobId(), pendingRerateDataloadFile.getPreName(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(PendingRerateDataloadFile pendingRerateDataloadFile, int usrId)
	{
		update(null, pendingRerateDataloadFile, usrId);
	}

	public void update(Connection connection, PendingRerateDataloadFile pendingRerateDataloadFile, int usrId)
	{
		String query = " update pending_rerate_dataload_file set  fil_id = ? , acc_id = ? , pre_table_name = ? , pre_poll_fl = ? , job_id = ? , pre_name = ? , pre_modified_usr_id = ? , pre_modified_dttm = ?  where pre_id = ?  ";
		Object[] paramValues = new Object[] { pendingRerateDataloadFile.getFilId(), pendingRerateDataloadFile.getAccId(), pendingRerateDataloadFile.getPreTableName(), pendingRerateDataloadFile.getPrePollFl(), pendingRerateDataloadFile.getJobId(), pendingRerateDataloadFile.getPreName(), usrId, new DateTime(), pendingRerateDataloadFile.getPreId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into pending_rerate_dataload_file values(?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { pendingRerateDataloadFile.getPreId(), pendingRerateDataloadFile.getFilId(), pendingRerateDataloadFile.getAccId(), pendingRerateDataloadFile.getPreTableName(), pendingRerateDataloadFile.getPrePollFl(), pendingRerateDataloadFile.getJobId(), pendingRerateDataloadFile.getPreName(), pendingRerateDataloadFile.getPreBk(), usrId, usrId, new DateTime(),
				new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<PendingRerateDataloadFile> fetchAll()
	{
		String query = " select * from pending_rerate_dataload_file ";
		return RefDBHelper.getDB().fetchList(query, PendingRerateDataloadFileImpl.class);
	}

	public List<PendingRerateDataloadFile> fetchAuditRowsByPk(int id)
	{
		String query = " select * from pending_rerate_dataload_file where pre_id=? order by pre_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, PendingRerateDataloadFileImpl.class);
	}

	public PendingRerateDataloadFile fetchByPk(int preId)
	{
		return fetchByPk(null, preId);
	}

	public PendingRerateDataloadFile fetchByPk(Connection connection, int preId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from pending_rerate_dataload_file where pre_id=? ", preId, PendingRerateDataloadFileImpl.class);
	}

	public PendingRerateDataloadFile fetchByBk(String preBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_rerate_dataload_file where pre_bk=? ", preBk, PendingRerateDataloadFileImpl.class);
	}

	public PendingRerateDataloadFile fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_rerate_dataload_file where pre_name=? ", displayField, PendingRerateDataloadFileImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("pre_name", "pending_rerate_dataload_file");
	}

}