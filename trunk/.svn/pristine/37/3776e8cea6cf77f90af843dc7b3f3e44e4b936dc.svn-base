/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.FileParserSettingsDbaImpl;
import se.signa.signature.dbo.FileParserSettingsImpl;
import se.signa.signature.gen.dbo.FileParserSettings;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class FileParserSettingsDba extends SignatureDba<FileParserSettings>
{
	private static FileParserSettingsDbaImpl INSTANCE;

	public static FileParserSettingsDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new FileParserSettingsDbaImpl();
		return INSTANCE;
	}

	public FileParserSettingsDba()
	{
		tableName = "file_parser_settings";
		tablePrefix = "fas";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Fas Id");
		columns.add("Fas Name");
		columns.add("Fas Target Dir");
		columns.add("Fas Parser Jbt Id");
		columns.add("Fas Process Duplicate");
		columns.add("Fas User Name");
		columns.add("Fas Dttm");
		return columns;
	}

	@Override
	public FileParserSettings createEmptyDbo()
	{
		return new FileParserSettingsImpl();
	}

	@Override
	public void checkDuplicates(FileParserSettings dbo)
	{
		checkDuplicateGM(dbo.getFasName(), "fas_name", dbo.getPk());
		checkDuplicateGM(dbo.getFasBk(), "fas_bk", dbo.getPk());
	}

	@Override
	public int create(FileParserSettings fileParserSettings, int usrId)
	{
		return create(null, fileParserSettings, usrId);
	}

	public int create(Connection connection, FileParserSettings fileParserSettings, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("fas_id", "file_parser_settings");
		String bk = fileParserSettings.getDisplayField();

		String query = " insert into file_parser_settings values(?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, fileParserSettings.getFasName(), fileParserSettings.getFasTargetDir(), fileParserSettings.getFasParserJbtId(), fileParserSettings.getFasProcessDuplicate(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(FileParserSettings fileParserSettings, int usrId)
	{
		update(null, fileParserSettings, usrId);
	}

	public void update(Connection connection, FileParserSettings fileParserSettings, int usrId)
	{
		String query = " update file_parser_settings set  fas_name = ? , fas_target_dir = ? , fas_parser_jbt_id = ? , fas_process_duplicate = ? , fas_modified_usr_id = ? , fas_modified_dttm = ?  where fas_id = ?  ";
		Object[] paramValues = new Object[] { fileParserSettings.getFasName(), fileParserSettings.getFasTargetDir(), fileParserSettings.getFasParserJbtId(), fileParserSettings.getFasProcessDuplicate(), usrId, new DateTime(), fileParserSettings.getFasId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into file_parser_settings values(?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { fileParserSettings.getFasId(), fileParserSettings.getFasName(), fileParserSettings.getFasTargetDir(), fileParserSettings.getFasParserJbtId(), fileParserSettings.getFasProcessDuplicate(), fileParserSettings.getFasBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<FileParserSettings> fetchAll()
	{
		String query = " select * from file_parser_settings ";
		return RefDBHelper.getDB().fetchList(query, FileParserSettingsImpl.class);
	}

	public List<FileParserSettings> fetchAuditRowsByPk(int id)
	{
		String query = " select * from file_parser_settings where fas_id=? order by fas_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, FileParserSettingsImpl.class);
	}

	public FileParserSettings fetchByPk(int fasId)
	{
		return fetchByPk(null, fasId);
	}

	public FileParserSettings fetchByPk(Connection connection, int fasId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from file_parser_settings where fas_id=? ", fasId, FileParserSettingsImpl.class);
	}

	public FileParserSettings fetchByBk(String fasBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from file_parser_settings where fas_bk=? ", fasBk, FileParserSettingsImpl.class);
	}

	public FileParserSettings fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from file_parser_settings where fas_name=? ", displayField, FileParserSettingsImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("fas_name", "file_parser_settings");
	}

}