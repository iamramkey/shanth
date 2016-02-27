/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.FileTransferSettingsDbaImpl;
import se.signa.signature.dbo.FileTransferSettingsImpl;
import se.signa.signature.gen.dbo.FileTransferSettings;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class FileTransferSettingsDba extends SignatureDba<FileTransferSettings>
{
	private static FileTransferSettingsDbaImpl INSTANCE;

	public static FileTransferSettingsDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new FileTransferSettingsDbaImpl();
		return INSTANCE;
	}

	public FileTransferSettingsDba()
	{
		tableName = "file_transfer_settings";
		tablePrefix = "fts";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Fts Id");
		columns.add("Fts Name");
		columns.add("Fts Target Dir");
		columns.add("Fts Transfer Jbt Id");
		columns.add("Fts Valid Mask");
		columns.add("Fts Rerate Mask");
		columns.add("Fts Stability Secs");
		columns.add("Fts Stability Retry");
		columns.add("Fps Id");
		columns.add("Fts User Name");
		columns.add("Fts Dttm");
		return columns;
	}

	@Override
	public FileTransferSettings createEmptyDbo()
	{
		return new FileTransferSettingsImpl();
	}

	@Override
	public void checkDuplicates(FileTransferSettings dbo)
	{
		checkDuplicateGM(dbo.getFtsName(), "fts_name", dbo.getPk());
		checkDuplicateGM(dbo.getFtsBk(), "fts_bk", dbo.getPk());
	}

	@Override
	public int create(FileTransferSettings fileTransferSettings, int usrId)
	{
		return create(null, fileTransferSettings, usrId);
	}

	public int create(Connection connection, FileTransferSettings fileTransferSettings, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("fts_id", "file_transfer_settings");
		String bk = fileTransferSettings.getDisplayField();

		String query = " insert into file_transfer_settings values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, fileTransferSettings.getFtsName(), fileTransferSettings.getFtsTargetDir(), fileTransferSettings.getFtsTransferJbtId(), fileTransferSettings.getFtsValidMask(), fileTransferSettings.getFtsRerateMask(), fileTransferSettings.getFtsStabilitySecs(), fileTransferSettings.getFtsStabilityRetry(), fileTransferSettings.getFpsId(), bk, usrId, null,
				new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(FileTransferSettings fileTransferSettings, int usrId)
	{
		update(null, fileTransferSettings, usrId);
	}

	public void update(Connection connection, FileTransferSettings fileTransferSettings, int usrId)
	{
		String query = " update file_transfer_settings set  fts_name = ? , fts_target_dir = ? , fts_transfer_jbt_id = ? , fts_valid_mask = ? , fts_rerate_mask = ? , fts_stability_secs = ? , fts_stability_retry = ? , fps_id = ? , fts_modified_usr_id = ? , fts_modified_dttm = ?  where fts_id = ?  ";
		Object[] paramValues = new Object[] { fileTransferSettings.getFtsName(), fileTransferSettings.getFtsTargetDir(), fileTransferSettings.getFtsTransferJbtId(), fileTransferSettings.getFtsValidMask(), fileTransferSettings.getFtsRerateMask(), fileTransferSettings.getFtsStabilitySecs(), fileTransferSettings.getFtsStabilityRetry(), fileTransferSettings.getFpsId(), usrId, new DateTime(),
				fileTransferSettings.getFtsId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into file_transfer_settings values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { fileTransferSettings.getFtsId(), fileTransferSettings.getFtsName(), fileTransferSettings.getFtsTargetDir(), fileTransferSettings.getFtsTransferJbtId(), fileTransferSettings.getFtsValidMask(), fileTransferSettings.getFtsRerateMask(), fileTransferSettings.getFtsStabilitySecs(), fileTransferSettings.getFtsStabilityRetry(),
				fileTransferSettings.getFpsId(), fileTransferSettings.getFtsBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<FileTransferSettings> fetchAll()
	{
		String query = " select * from file_transfer_settings ";
		return RefDBHelper.getDB().fetchList(query, FileTransferSettingsImpl.class);
	}

	public List<FileTransferSettings> fetchAuditRowsByPk(int id)
	{
		String query = " select * from file_transfer_settings where fts_id=? order by fts_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, FileTransferSettingsImpl.class);
	}

	public FileTransferSettings fetchByPk(int ftsId)
	{
		return fetchByPk(null, ftsId);
	}

	public FileTransferSettings fetchByPk(Connection connection, int ftsId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from file_transfer_settings where fts_id=? ", ftsId, FileTransferSettingsImpl.class);
	}

	public FileTransferSettings fetchByBk(String ftsBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from file_transfer_settings where fts_bk=? ", ftsBk, FileTransferSettingsImpl.class);
	}

	public FileTransferSettings fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from file_transfer_settings where fts_name=? ", displayField, FileTransferSettingsImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("fts_name", "file_transfer_settings");
	}

}