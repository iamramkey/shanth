/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.FileArchiverSettingsDbaImpl;
import se.signa.signature.dbo.FileArchiverSettingsImpl;
import se.signa.signature.gen.dbo.FileArchiverSettings;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class FileArchiverSettingsDba extends SignatureDba<FileArchiverSettings>
{
	private static FileArchiverSettingsDbaImpl INSTANCE;

	public static FileArchiverSettingsDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new FileArchiverSettingsDbaImpl();
		return INSTANCE;
	}

	public FileArchiverSettingsDba()
	{
		tableName = "file_archiver_settings";
		tablePrefix = "fia";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Fia Id");
		columns.add("Fia Name");
		columns.add("Fia Source Dir");
		columns.add("Fia Dest Dir");
		columns.add("Fia Type");
		columns.add("Fia Retention Days");
		columns.add("Fia User Name");
		columns.add("Fia Dttm");
		return columns;
	}

	@Override
	public FileArchiverSettings createEmptyDbo()
	{
		return new FileArchiverSettingsImpl();
	}

	@Override
	public void checkDuplicates(FileArchiverSettings dbo)
	{
		checkDuplicateGM(dbo.getFiaName(), "fia_name", dbo.getPk());
		checkDuplicateGM(dbo.getFiaBk(), "fia_bk", dbo.getPk());
	}

	@Override
	public int create(FileArchiverSettings fileArchiverSettings, int usrId)
	{
		return create(null, fileArchiverSettings, usrId);
	}

	public int create(Connection connection, FileArchiverSettings fileArchiverSettings, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("fia_id", "file_archiver_settings");
		String bk = fileArchiverSettings.getDisplayField();

		String query = " insert into file_archiver_settings values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, fileArchiverSettings.getFiaName(), fileArchiverSettings.getFiaSourceDir(), fileArchiverSettings.getFiaDestDir(), fileArchiverSettings.getFiaType(), fileArchiverSettings.getFiaRetentionDays(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(FileArchiverSettings fileArchiverSettings, int usrId)
	{
		update(null, fileArchiverSettings, usrId);
	}

	public void update(Connection connection, FileArchiverSettings fileArchiverSettings, int usrId)
	{
		String query = " update file_archiver_settings set  fia_name = ? , fia_source_dir = ? , fia_dest_dir = ? , fia_type = ? , fia_retention_days = ? , fia_modified_usr_id = ? , fia_modified_dttm = ?  where fia_id = ?  ";
		Object[] paramValues = new Object[] { fileArchiverSettings.getFiaName(), fileArchiverSettings.getFiaSourceDir(), fileArchiverSettings.getFiaDestDir(), fileArchiverSettings.getFiaType(), fileArchiverSettings.getFiaRetentionDays(), usrId, new DateTime(), fileArchiverSettings.getFiaId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into file_archiver_settings values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { fileArchiverSettings.getFiaId(), fileArchiverSettings.getFiaName(), fileArchiverSettings.getFiaSourceDir(), fileArchiverSettings.getFiaDestDir(), fileArchiverSettings.getFiaType(), fileArchiverSettings.getFiaRetentionDays(), fileArchiverSettings.getFiaBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<FileArchiverSettings> fetchAll()
	{
		String query = " select * from file_archiver_settings ";
		return RefDBHelper.getDB().fetchList(query, FileArchiverSettingsImpl.class);
	}

	public List<FileArchiverSettings> fetchAuditRowsByPk(int id)
	{
		String query = " select * from file_archiver_settings where fia_id=? order by fia_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, FileArchiverSettingsImpl.class);
	}

	public FileArchiverSettings fetchByPk(int fiaId)
	{
		return fetchByPk(null, fiaId);
	}

	public FileArchiverSettings fetchByPk(Connection connection, int fiaId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from file_archiver_settings where fia_id=? ", fiaId, FileArchiverSettingsImpl.class);
	}

	public FileArchiverSettings fetchByBk(String fiaBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from file_archiver_settings where fia_bk=? ", fiaBk, FileArchiverSettingsImpl.class);
	}

	public FileArchiverSettings fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from file_archiver_settings where fia_name=? ", displayField, FileArchiverSettingsImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("fia_name", "file_archiver_settings");
	}

}