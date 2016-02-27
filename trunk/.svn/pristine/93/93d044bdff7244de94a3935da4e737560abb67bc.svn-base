/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.AuditFileDbaImpl;
import se.signa.signature.dbo.AuditFileImpl;
import se.signa.signature.gen.dbo.AuditFile;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class AuditFileDba extends SignatureDba<AuditFile>
{
	private static AuditFileDbaImpl INSTANCE;

	public static AuditFileDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new AuditFileDbaImpl();
		return INSTANCE;
	}

	public AuditFileDba()
	{
		tableName = "audit_file";
		tablePrefix = "auf";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Auf Id");
		columns.add("Fil Id");
		columns.add("Auf Name");
		columns.add("Auf File Type");
		columns.add("Auf Parent Auf Id");
		columns.add("Auf Checksum");
		columns.add("Auf Filesize");
		columns.add("Auf Child Count");
		columns.add("Auf User Name");
		columns.add("Auf Dttm");
		return columns;
	}

	@Override
	public AuditFile createEmptyDbo()
	{
		return new AuditFileImpl();
	}

	@Override
	public void checkDuplicates(AuditFile dbo)
	{
		checkDuplicateGM(dbo.getAufName(), "auf_name", dbo.getPk());
		checkDuplicateGM(dbo.getAufBk(), "auf_bk", dbo.getPk());
	}

	@Override
	public int create(AuditFile auditFile, int usrId)
	{
		return create(null, auditFile, usrId);
	}

	public int create(Connection connection, AuditFile auditFile, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("auf_id", "audit_file");
		String bk = auditFile.getDisplayField();

		String query = " insert into audit_file values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, auditFile.getFilId(), auditFile.getAufName(), auditFile.getAufFileType(), auditFile.getAufParentAufId(), auditFile.getAufChecksum(), auditFile.getAufFilesize(), auditFile.getAufChildCount(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(AuditFile auditFile, int usrId)
	{
		update(null, auditFile, usrId);
	}

	public void update(Connection connection, AuditFile auditFile, int usrId)
	{
		String query = " update audit_file set  fil_id = ? , auf_name = ? , auf_file_type = ? , auf_parent_auf_id = ? , auf_checksum = ? , auf_filesize = ? , auf_child_count = ? , auf_modified_usr_id = ? , auf_modified_dttm = ?  where auf_id = ?  ";
		Object[] paramValues = new Object[] { auditFile.getFilId(), auditFile.getAufName(), auditFile.getAufFileType(), auditFile.getAufParentAufId(), auditFile.getAufChecksum(), auditFile.getAufFilesize(), auditFile.getAufChildCount(), usrId, new DateTime(), auditFile.getAufId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into audit_file values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { auditFile.getAufId(), auditFile.getFilId(), auditFile.getAufName(), auditFile.getAufFileType(), auditFile.getAufParentAufId(), auditFile.getAufChecksum(), auditFile.getAufFilesize(), auditFile.getAufChildCount(), auditFile.getAufBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<AuditFile> fetchAll()
	{
		String query = " select * from audit_file ";
		return RefDBHelper.getDB().fetchList(query, AuditFileImpl.class);
	}

	public List<AuditFile> fetchAuditRowsByPk(int id)
	{
		String query = " select * from audit_file where auf_id=? order by auf_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, AuditFileImpl.class);
	}

	public AuditFile fetchByPk(int aufId)
	{
		return fetchByPk(null, aufId);
	}

	public AuditFile fetchByPk(Connection connection, int aufId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from audit_file where auf_id=? ", aufId, AuditFileImpl.class);
	}

	public AuditFile fetchByBk(String aufBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from audit_file where auf_bk=? ", aufBk, AuditFileImpl.class);
	}

	public AuditFile fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from audit_file where auf_name=? ", displayField, AuditFileImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("auf_name", "audit_file");
	}

}