/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.PendingCcDbaImpl;
import se.signa.signature.dbo.PendingCcImpl;
import se.signa.signature.gen.dbo.PendingCc;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class PendingCcDba extends SignatureDba<PendingCc>
{
	private static PendingCcDbaImpl INSTANCE;

	public static PendingCcDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new PendingCcDbaImpl();
		return INSTANCE;
	}

	public PendingCcDba()
	{
		tableName = "pending_cc";
		tablePrefix = "pcc";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Pcc Id");
		columns.add("Pcc Name");
		columns.add("Pcc Table Name");
		columns.add("Pcc Poll Fl");
		columns.add("Job Id");
		columns.add("Pcc User Name");
		columns.add("Pcc Dttm");
		return columns;
	}

	@Override
	public PendingCc createEmptyDbo()
	{
		return new PendingCcImpl();
	}

	@Override
	public void checkDuplicates(PendingCc dbo)
	{
		checkDuplicateGM(dbo.getPccName(), "pcc_name", dbo.getPk());
		checkDuplicateGM(dbo.getPccBk(), "pcc_bk", dbo.getPk());
	}

	@Override
	public int create(PendingCc pendingCc, int usrId)
	{
		return create(null, pendingCc, usrId);
	}

	public int create(Connection connection, PendingCc pendingCc, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("pcc_id", "pending_cc");
		String bk = pendingCc.getDisplayField();

		String query = " insert into pending_cc values(?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, pendingCc.getPccName(), pendingCc.getPccTableName(), pendingCc.getPccPollFl(), pendingCc.getJobId(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(PendingCc pendingCc, int usrId)
	{
		update(null, pendingCc, usrId);
	}

	public void update(Connection connection, PendingCc pendingCc, int usrId)
	{
		String query = " update pending_cc set  pcc_name = ? , pcc_table_name = ? , pcc_poll_fl = ? , job_id = ? , pcc_modified_usr_id = ? , pcc_modified_dttm = ?  where pcc_id = ?  ";
		Object[] paramValues = new Object[] { pendingCc.getPccName(), pendingCc.getPccTableName(), pendingCc.getPccPollFl(), pendingCc.getJobId(), usrId, new DateTime(), pendingCc.getPccId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into pending_cc values(?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { pendingCc.getPccId(), pendingCc.getPccName(), pendingCc.getPccTableName(), pendingCc.getPccPollFl(), pendingCc.getJobId(), pendingCc.getPccBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<PendingCc> fetchAll()
	{
		String query = " select * from pending_cc ";
		return RefDBHelper.getDB().fetchList(query, PendingCcImpl.class);
	}

	public List<PendingCc> fetchAuditRowsByPk(int id)
	{
		String query = " select * from pending_cc where pcc_id=? order by pcc_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, PendingCcImpl.class);
	}

	public PendingCc fetchByPk(int pccId)
	{
		return fetchByPk(null, pccId);
	}

	public PendingCc fetchByPk(Connection connection, int pccId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from pending_cc where pcc_id=? ", pccId, PendingCcImpl.class);
	}

	public PendingCc fetchByBk(String pccBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_cc where pcc_bk=? ", pccBk, PendingCcImpl.class);
	}

	public PendingCc fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_cc where pcc_name=? ", displayField, PendingCcImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("pcc_name", "pending_cc");
	}

}