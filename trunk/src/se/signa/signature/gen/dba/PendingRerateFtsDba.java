/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.PendingRerateFtsDbaImpl;
import se.signa.signature.dbo.PendingRerateFtsImpl;
import se.signa.signature.gen.dbo.PendingRerateFts;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class PendingRerateFtsDba extends SignatureDba<PendingRerateFts>
{
	private static PendingRerateFtsDbaImpl INSTANCE;

	public static PendingRerateFtsDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new PendingRerateFtsDbaImpl();
		return INSTANCE;
	}

	public PendingRerateFtsDba()
	{
		tableName = "pending_rerate_fts";
		tablePrefix = "prf";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Prf Id");
		columns.add("Prf Name");
		columns.add("Prf Batch Size");
		columns.add("Prf Frequency Secs");
		columns.add("Prf Timeout Secs");
		columns.add("Fts Id");
		columns.add("Prf Rerate Poller Jbt Id");
		columns.add("Prf Rerate Parser Jbt Id");
		columns.add("Prf User Name");
		columns.add("Prf Dttm");
		return columns;
	}

	@Override
	public PendingRerateFts createEmptyDbo()
	{
		return new PendingRerateFtsImpl();
	}

	@Override
	public void checkDuplicates(PendingRerateFts dbo)
	{
		checkDuplicateGM(dbo.getPrfName(), "prf_name", dbo.getPk());
		checkDuplicateGM(dbo.getPrfBk(), "prf_bk", dbo.getPk());
	}

	@Override
	public int create(PendingRerateFts pendingRerateFts, int usrId)
	{
		return create(null, pendingRerateFts, usrId);
	}

	public int create(Connection connection, PendingRerateFts pendingRerateFts, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("prf_id", "pending_rerate_fts");
		String bk = pendingRerateFts.getDisplayField();

		String query = " insert into pending_rerate_fts values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, pendingRerateFts.getPrfName(), pendingRerateFts.getPrfBatchSize(), pendingRerateFts.getPrfFrequencySecs(), pendingRerateFts.getPrfTimeoutSecs(), pendingRerateFts.getFtsId(), pendingRerateFts.getPrfReratePollerJbtId(), pendingRerateFts.getPrfRerateParserJbtId(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(PendingRerateFts pendingRerateFts, int usrId)
	{
		update(null, pendingRerateFts, usrId);
	}

	public void update(Connection connection, PendingRerateFts pendingRerateFts, int usrId)
	{
		String query = " update pending_rerate_fts set  prf_name = ? , prf_batch_size = ? , prf_frequency_secs = ? , prf_timeout_secs = ? , fts_id = ? , prf_rerate_poller_jbt_id = ? , prf_rerate_parser_jbt_id = ? , prf_modified_usr_id = ? , prf_modified_dttm = ?  where prf_id = ?  ";
		Object[] paramValues = new Object[] { pendingRerateFts.getPrfName(), pendingRerateFts.getPrfBatchSize(), pendingRerateFts.getPrfFrequencySecs(), pendingRerateFts.getPrfTimeoutSecs(), pendingRerateFts.getFtsId(), pendingRerateFts.getPrfReratePollerJbtId(), pendingRerateFts.getPrfRerateParserJbtId(), usrId, new DateTime(), pendingRerateFts.getPrfId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into pending_rerate_fts values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { pendingRerateFts.getPrfId(), pendingRerateFts.getPrfName(), pendingRerateFts.getPrfBatchSize(), pendingRerateFts.getPrfFrequencySecs(), pendingRerateFts.getPrfTimeoutSecs(), pendingRerateFts.getFtsId(), pendingRerateFts.getPrfReratePollerJbtId(), pendingRerateFts.getPrfRerateParserJbtId(), pendingRerateFts.getPrfBk(), usrId, usrId,
				new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<PendingRerateFts> fetchAll()
	{
		String query = " select * from pending_rerate_fts ";
		return RefDBHelper.getDB().fetchList(query, PendingRerateFtsImpl.class);
	}

	public List<PendingRerateFts> fetchAuditRowsByPk(int id)
	{
		String query = " select * from pending_rerate_fts where prf_id=? order by prf_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, PendingRerateFtsImpl.class);
	}

	public PendingRerateFts fetchByPk(int prfId)
	{
		return fetchByPk(null, prfId);
	}

	public PendingRerateFts fetchByPk(Connection connection, int prfId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from pending_rerate_fts where prf_id=? ", prfId, PendingRerateFtsImpl.class);
	}

	public PendingRerateFts fetchByBk(String prfBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_rerate_fts where prf_bk=? ", prfBk, PendingRerateFtsImpl.class);
	}

	public PendingRerateFts fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_rerate_fts where prf_name=? ", displayField, PendingRerateFtsImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("prf_name", "pending_rerate_fts");
	}

}