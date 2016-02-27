/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.ParsedFileDbaImpl;
import se.signa.signature.dbo.ParsedFileImpl;
import se.signa.signature.gen.dbo.ParsedFile;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class ParsedFileDba extends SignatureDba<ParsedFile>
{
	private static ParsedFileDbaImpl INSTANCE;

	public static ParsedFileDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new ParsedFileDbaImpl();
		return INSTANCE;
	}

	public ParsedFileDba()
	{
		tableName = "parsed_file";
		tablePrefix = "pfi";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Pfi Id");
		columns.add("Fil Id");
		columns.add("Pfi Checksum");
		columns.add("Pfi Name");
		columns.add("Pfi Dup Pfi Id");
		columns.add("Pfi User Name");
		columns.add("Pfi Dttm");
		return columns;
	}

	@Override
	public ParsedFile createEmptyDbo()
	{
		return new ParsedFileImpl();
	}

	@Override
	public void checkDuplicates(ParsedFile dbo)
	{
		checkDuplicateGM(dbo.getPfiName(), "pfi_name", dbo.getPk());
		checkDuplicateGM(dbo.getPfiBk(), "pfi_bk", dbo.getPk());
	}

	@Override
	public int create(ParsedFile parsedFile, int usrId)
	{
		return create(null, parsedFile, usrId);
	}

	public int create(Connection connection, ParsedFile parsedFile, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("pfi_id", "parsed_file");
		String bk = parsedFile.getDisplayField();

		String query = " insert into parsed_file values(?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, parsedFile.getFilId(), parsedFile.getPfiChecksum(), parsedFile.getPfiName(), parsedFile.getPfiDupPfiId(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(ParsedFile parsedFile, int usrId)
	{
		update(null, parsedFile, usrId);
	}

	public void update(Connection connection, ParsedFile parsedFile, int usrId)
	{
		String query = " update parsed_file set  fil_id = ? , pfi_checksum = ? , pfi_name = ? , pfi_dup_pfi_id = ? , pfi_modified_usr_id = ? , pfi_modified_dttm = ?  where pfi_id = ?  ";
		Object[] paramValues = new Object[] { parsedFile.getFilId(), parsedFile.getPfiChecksum(), parsedFile.getPfiName(), parsedFile.getPfiDupPfiId(), usrId, new DateTime(), parsedFile.getPfiId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into parsed_file values(?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { parsedFile.getPfiId(), parsedFile.getFilId(), parsedFile.getPfiChecksum(), parsedFile.getPfiName(), parsedFile.getPfiDupPfiId(), parsedFile.getPfiBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<ParsedFile> fetchAll()
	{
		String query = " select * from parsed_file ";
		return RefDBHelper.getDB().fetchList(query, ParsedFileImpl.class);
	}

	public List<ParsedFile> fetchAuditRowsByPk(int id)
	{
		String query = " select * from parsed_file where pfi_id=? order by pfi_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, ParsedFileImpl.class);
	}

	public ParsedFile fetchByPk(int pfiId)
	{
		return fetchByPk(null, pfiId);
	}

	public ParsedFile fetchByPk(Connection connection, int pfiId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from parsed_file where pfi_id=? ", pfiId, ParsedFileImpl.class);
	}

	public ParsedFile fetchByBk(String pfiBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from parsed_file where pfi_bk=? ", pfiBk, ParsedFileImpl.class);
	}

	public ParsedFile fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from parsed_file where pfi_name=? ", displayField, ParsedFileImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("pfi_name", "parsed_file");
	}

}