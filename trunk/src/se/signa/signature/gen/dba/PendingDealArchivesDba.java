/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.PendingDealArchivesDbaImpl;
import se.signa.signature.dbo.PendingDealArchivesImpl;
import se.signa.signature.gen.dbo.PendingDealArchives;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class PendingDealArchivesDba extends SignatureDba<PendingDealArchives>
{
	private static PendingDealArchivesDbaImpl INSTANCE;

	public static PendingDealArchivesDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new PendingDealArchivesDbaImpl();
		return INSTANCE;
	}

	public PendingDealArchivesDba()
	{
		tableName = "pending_deal_archives";
		tablePrefix = "pda";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Pda Id");
		columns.add("Del Id");
		columns.add("Pda Archive Date");
		columns.add("Pda Filepath");
		columns.add("Pda Name");
		columns.add("Pda User Name");
		columns.add("Pda Dttm");
		return columns;
	}

	@Override
	public PendingDealArchives createEmptyDbo()
	{
		return new PendingDealArchivesImpl();
	}

	@Override
	public void checkDuplicates(PendingDealArchives dbo)
	{
		checkDuplicateGM(dbo.getPdaName(), "pda_name", dbo.getPk());
		checkDuplicateGM(dbo.getPdaBk(), "pda_bk", dbo.getPk());
	}

	@Override
	public int create(PendingDealArchives pendingDealArchives, int usrId)
	{
		return create(null, pendingDealArchives, usrId);
	}

	public int create(Connection connection, PendingDealArchives pendingDealArchives, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("pda_id", "pending_deal_archives");
		String bk = pendingDealArchives.getDisplayField();

		String query = " insert into pending_deal_archives values(?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, pendingDealArchives.getDelId(), pendingDealArchives.getPdaArchiveDate(), pendingDealArchives.getPdaFilepath(), pendingDealArchives.getPdaName(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(PendingDealArchives pendingDealArchives, int usrId)
	{
		update(null, pendingDealArchives, usrId);
	}

	public void update(Connection connection, PendingDealArchives pendingDealArchives, int usrId)
	{
		String query = " update pending_deal_archives set  del_id = ? , pda_archive_date = ? , pda_filepath = ? , pda_name = ? , pda_modified_usr_id = ? , pda_modified_dttm = ?  where pda_id = ?  ";
		Object[] paramValues = new Object[] { pendingDealArchives.getDelId(), pendingDealArchives.getPdaArchiveDate(), pendingDealArchives.getPdaFilepath(), pendingDealArchives.getPdaName(), usrId, new DateTime(), pendingDealArchives.getPdaId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into pending_deal_archives values(?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { pendingDealArchives.getPdaId(), pendingDealArchives.getDelId(), pendingDealArchives.getPdaArchiveDate(), pendingDealArchives.getPdaFilepath(), pendingDealArchives.getPdaName(), pendingDealArchives.getPdaBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<PendingDealArchives> fetchAll()
	{
		String query = " select * from pending_deal_archives ";
		return RefDBHelper.getDB().fetchList(query, PendingDealArchivesImpl.class);
	}

	public List<PendingDealArchives> fetchAuditRowsByPk(int id)
	{
		String query = " select * from pending_deal_archives where pda_id=? order by pda_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, PendingDealArchivesImpl.class);
	}

	public PendingDealArchives fetchByPk(int pdaId)
	{
		return fetchByPk(null, pdaId);
	}

	public PendingDealArchives fetchByPk(Connection connection, int pdaId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from pending_deal_archives where pda_id=? ", pdaId, PendingDealArchivesImpl.class);
	}

	public PendingDealArchives fetchByBk(String pdaBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_deal_archives where pda_bk=? ", pdaBk, PendingDealArchivesImpl.class);
	}

	public PendingDealArchives fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_deal_archives where pda_name=? ", displayField, PendingDealArchivesImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("pda_name", "pending_deal_archives");
	}

}