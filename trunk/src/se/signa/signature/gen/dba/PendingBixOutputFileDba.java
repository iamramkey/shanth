/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.PendingBixOutputFileDbaImpl;
import se.signa.signature.dbo.PendingBixOutputFileImpl;
import se.signa.signature.gen.dbo.PendingBixOutputFile;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class PendingBixOutputFileDba extends SignatureDba<PendingBixOutputFile>
{
	private static PendingBixOutputFileDbaImpl INSTANCE;

	public static PendingBixOutputFileDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new PendingBixOutputFileDbaImpl();
		return INSTANCE;
	}

	public PendingBixOutputFileDba()
	{
		tableName = "pending_bix_output_file";
		tablePrefix = "pbf";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Pbf Id");
		columns.add("Fil Id");
		columns.add("Acc Id");
		columns.add("Pbf Name");
		columns.add("Pbf Exported Job Id");
		columns.add("Pbf User Name");
		columns.add("Pbf Dttm");
		return columns;
	}

	@Override
	public PendingBixOutputFile createEmptyDbo()
	{
		return new PendingBixOutputFileImpl();
	}

	@Override
	public void checkDuplicates(PendingBixOutputFile dbo)
	{
		checkDuplicateGM(dbo.getPbfName(), "pbf_name", dbo.getPk());
		checkDuplicateGM(dbo.getPbfBk(), "pbf_bk", dbo.getPk());
	}

	@Override
	public int create(PendingBixOutputFile pendingBixOutputFile, int usrId)
	{
		return create(null, pendingBixOutputFile, usrId);
	}

	public int create(Connection connection, PendingBixOutputFile pendingBixOutputFile, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("pbf_id", "pending_bix_output_file");
		String bk = pendingBixOutputFile.getDisplayField();

		String query = " insert into pending_bix_output_file values(?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, pendingBixOutputFile.getFilId(), pendingBixOutputFile.getAccId(), pendingBixOutputFile.getPbfName(), pendingBixOutputFile.getPbfExportedJobId(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(PendingBixOutputFile pendingBixOutputFile, int usrId)
	{
		update(null, pendingBixOutputFile, usrId);
	}

	public void update(Connection connection, PendingBixOutputFile pendingBixOutputFile, int usrId)
	{
		String query = " update pending_bix_output_file set  fil_id = ? , acc_id = ? , pbf_name = ? , pbf_exported_job_id = ? , pbf_modified_usr_id = ? , pbf_modified_dttm = ?  where pbf_id = ?  ";
		Object[] paramValues = new Object[] { pendingBixOutputFile.getFilId(), pendingBixOutputFile.getAccId(), pendingBixOutputFile.getPbfName(), pendingBixOutputFile.getPbfExportedJobId(), usrId, new DateTime(), pendingBixOutputFile.getPbfId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into pending_bix_output_file values(?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { pendingBixOutputFile.getPbfId(), pendingBixOutputFile.getFilId(), pendingBixOutputFile.getAccId(), pendingBixOutputFile.getPbfName(), pendingBixOutputFile.getPbfExportedJobId(), pendingBixOutputFile.getPbfBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<PendingBixOutputFile> fetchAll()
	{
		String query = " select * from pending_bix_output_file ";
		return RefDBHelper.getDB().fetchList(query, PendingBixOutputFileImpl.class);
	}

	public List<PendingBixOutputFile> fetchAuditRowsByPk(int id)
	{
		String query = " select * from pending_bix_output_file where pbf_id=? order by pbf_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, PendingBixOutputFileImpl.class);
	}

	public PendingBixOutputFile fetchByPk(int pbfId)
	{
		return fetchByPk(null, pbfId);
	}

	public PendingBixOutputFile fetchByPk(Connection connection, int pbfId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from pending_bix_output_file where pbf_id=? ", pbfId, PendingBixOutputFileImpl.class);
	}

	public PendingBixOutputFile fetchByBk(String pbfBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_bix_output_file where pbf_bk=? ", pbfBk, PendingBixOutputFileImpl.class);
	}

	public PendingBixOutputFile fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_bix_output_file where pbf_name=? ", displayField, PendingBixOutputFileImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("pbf_name", "pending_bix_output_file");
	}

}