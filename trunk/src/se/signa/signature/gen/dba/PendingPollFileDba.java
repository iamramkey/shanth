/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.PendingPollFileDbaImpl;
import se.signa.signature.dbo.PendingPollFileImpl;
import se.signa.signature.gen.dbo.PendingPollFile;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class PendingPollFileDba extends SignatureDba<PendingPollFile>
{
	private static PendingPollFileDbaImpl INSTANCE;

	public static PendingPollFileDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new PendingPollFileDbaImpl();
		return INSTANCE;
	}

	public PendingPollFileDba()
	{
		tableName = "pending_poll_file";
		tablePrefix = "ppf";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Ppf Id");
		columns.add("Fps Id");
		columns.add("Fil Id");
		columns.add("Ppf Name");
		columns.add("Ppf User Name");
		columns.add("Ppf Dttm");
		return columns;
	}

	@Override
	public PendingPollFile createEmptyDbo()
	{
		return new PendingPollFileImpl();
	}

	@Override
	public void checkDuplicates(PendingPollFile dbo)
	{
		checkDuplicateGM(dbo.getPpfName(), "ppf_name", dbo.getPk());
		checkDuplicateGM(dbo.getPpfBk(), "ppf_bk", dbo.getPk());
	}

	@Override
	public int create(PendingPollFile pendingPollFile, int usrId)
	{
		return create(null, pendingPollFile, usrId);
	}

	public int create(Connection connection, PendingPollFile pendingPollFile, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("ppf_id", "pending_poll_file");
		String bk = pendingPollFile.getDisplayField();

		String query = " insert into pending_poll_file values(?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, pendingPollFile.getFpsId(), pendingPollFile.getFilId(), pendingPollFile.getPpfName(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(PendingPollFile pendingPollFile, int usrId)
	{
		update(null, pendingPollFile, usrId);
	}

	public void update(Connection connection, PendingPollFile pendingPollFile, int usrId)
	{
		String query = " update pending_poll_file set  fps_id = ? , fil_id = ? , ppf_name = ? , ppf_modified_usr_id = ? , ppf_modified_dttm = ?  where ppf_id = ?  ";
		Object[] paramValues = new Object[] { pendingPollFile.getFpsId(), pendingPollFile.getFilId(), pendingPollFile.getPpfName(), usrId, new DateTime(), pendingPollFile.getPpfId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into pending_poll_file values(?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { pendingPollFile.getPpfId(), pendingPollFile.getFpsId(), pendingPollFile.getFilId(), pendingPollFile.getPpfName(), pendingPollFile.getPpfBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<PendingPollFile> fetchAll()
	{
		String query = " select * from pending_poll_file ";
		return RefDBHelper.getDB().fetchList(query, PendingPollFileImpl.class);
	}

	public List<PendingPollFile> fetchAuditRowsByPk(int id)
	{
		String query = " select * from pending_poll_file where ppf_id=? order by ppf_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, PendingPollFileImpl.class);
	}

	public PendingPollFile fetchByPk(int ppfId)
	{
		return fetchByPk(null, ppfId);
	}

	public PendingPollFile fetchByPk(Connection connection, int ppfId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from pending_poll_file where ppf_id=? ", ppfId, PendingPollFileImpl.class);
	}

	public PendingPollFile fetchByBk(String ppfBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_poll_file where ppf_bk=? ", ppfBk, PendingPollFileImpl.class);
	}

	public PendingPollFile fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_poll_file where ppf_name=? ", displayField, PendingPollFileImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("ppf_name", "pending_poll_file");
	}

}