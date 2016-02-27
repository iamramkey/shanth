/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.PendingRpErrorFileDbaImpl;
import se.signa.signature.dbo.PendingRpErrorFileImpl;
import se.signa.signature.gen.dbo.PendingRpErrorFile;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class PendingRpErrorFileDba extends SignatureDba<PendingRpErrorFile>
{
	private static PendingRpErrorFileDbaImpl INSTANCE;

	public static PendingRpErrorFileDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new PendingRpErrorFileDbaImpl();
		return INSTANCE;
	}

	public PendingRpErrorFileDba()
	{
		tableName = "pending_rp_error_file";
		tablePrefix = "pef";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Pef Id");
		columns.add("Fil Id");
		columns.add("Fas Id");
		columns.add("Pef Error Count");
		columns.add("Pef Poll Fl");
		columns.add("Pef Name");
		columns.add("Pef User Name");
		columns.add("Pef Dttm");
		return columns;
	}

	@Override
	public PendingRpErrorFile createEmptyDbo()
	{
		return new PendingRpErrorFileImpl();
	}

	@Override
	public void checkDuplicates(PendingRpErrorFile dbo)
	{
		checkDuplicateGM(dbo.getPefName(), "pef_name", dbo.getPk());
		checkDuplicateGM(dbo.getPefBk(), "pef_bk", dbo.getPk());
	}

	@Override
	public int create(PendingRpErrorFile pendingRpErrorFile, int usrId)
	{
		return create(null, pendingRpErrorFile, usrId);
	}

	public int create(Connection connection, PendingRpErrorFile pendingRpErrorFile, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("pef_id", "pending_rp_error_file");
		String bk = pendingRpErrorFile.getDisplayField();

		String query = " insert into pending_rp_error_file values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, pendingRpErrorFile.getFilId(), pendingRpErrorFile.getFasId(), pendingRpErrorFile.getPefErrorCount(), pendingRpErrorFile.getPefPollFl(), pendingRpErrorFile.getPefName(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(PendingRpErrorFile pendingRpErrorFile, int usrId)
	{
		update(null, pendingRpErrorFile, usrId);
	}

	public void update(Connection connection, PendingRpErrorFile pendingRpErrorFile, int usrId)
	{
		String query = " update pending_rp_error_file set  fil_id = ? , fas_id = ? , pef_error_count = ? , pef_poll_fl = ? , pef_name = ? , pef_modified_usr_id = ? , pef_modified_dttm = ?  where pef_id = ?  ";
		Object[] paramValues = new Object[] { pendingRpErrorFile.getFilId(), pendingRpErrorFile.getFasId(), pendingRpErrorFile.getPefErrorCount(), pendingRpErrorFile.getPefPollFl(), pendingRpErrorFile.getPefName(), usrId, new DateTime(), pendingRpErrorFile.getPefId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into pending_rp_error_file values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { pendingRpErrorFile.getPefId(), pendingRpErrorFile.getFilId(), pendingRpErrorFile.getFasId(), pendingRpErrorFile.getPefErrorCount(), pendingRpErrorFile.getPefPollFl(), pendingRpErrorFile.getPefName(), pendingRpErrorFile.getPefBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<PendingRpErrorFile> fetchAll()
	{
		String query = " select * from pending_rp_error_file ";
		return RefDBHelper.getDB().fetchList(query, PendingRpErrorFileImpl.class);
	}

	public List<PendingRpErrorFile> fetchAuditRowsByPk(int id)
	{
		String query = " select * from pending_rp_error_file where pef_id=? order by pef_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, PendingRpErrorFileImpl.class);
	}

	public PendingRpErrorFile fetchByPk(int pefId)
	{
		return fetchByPk(null, pefId);
	}

	public PendingRpErrorFile fetchByPk(Connection connection, int pefId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from pending_rp_error_file where pef_id=? ", pefId, PendingRpErrorFileImpl.class);
	}

	public PendingRpErrorFile fetchByBk(String pefBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_rp_error_file where pef_bk=? ", pefBk, PendingRpErrorFileImpl.class);
	}

	public PendingRpErrorFile fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_rp_error_file where pef_name=? ", displayField, PendingRpErrorFileImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("pef_name", "pending_rp_error_file");
	}

}