/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.FilePollerSettingsDbaImpl;
import se.signa.signature.dbo.FilePollerSettingsImpl;
import se.signa.signature.gen.dbo.FilePollerSettings;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class FilePollerSettingsDba extends SignatureDba<FilePollerSettings>
{
	private static FilePollerSettingsDbaImpl INSTANCE;

	public static FilePollerSettingsDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new FilePollerSettingsDbaImpl();
		return INSTANCE;
	}

	public FilePollerSettingsDba()
	{
		tableName = "file_poller_settings";
		tablePrefix = "fps";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Fps Id");
		columns.add("Fps Name");
		columns.add("Fps Source Dir");
		columns.add("Fps Freq Secs");
		columns.add("Fps Poller Jbt Id");
		columns.add("Fps Transfer Jbt Id");
		columns.add("Fps User Name");
		columns.add("Fps Dttm");
		return columns;
	}

	@Override
	public FilePollerSettings createEmptyDbo()
	{
		return new FilePollerSettingsImpl();
	}

	@Override
	public void checkDuplicates(FilePollerSettings dbo)
	{
		checkDuplicateGM(dbo.getFpsName(), "fps_name", dbo.getPk());
		checkDuplicateGM(dbo.getFpsBk(), "fps_bk", dbo.getPk());
	}

	@Override
	public int create(FilePollerSettings filePollerSettings, int usrId)
	{
		return create(null, filePollerSettings, usrId);
	}

	public int create(Connection connection, FilePollerSettings filePollerSettings, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("fps_id", "file_poller_settings");
		String bk = filePollerSettings.getDisplayField();

		String query = " insert into file_poller_settings values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, filePollerSettings.getFpsName(), filePollerSettings.getFpsSourceDir(), filePollerSettings.getFpsFreqSecs(), filePollerSettings.getFpsPollerJbtId(), filePollerSettings.getFpsTransferJbtId(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(FilePollerSettings filePollerSettings, int usrId)
	{
		update(null, filePollerSettings, usrId);
	}

	public void update(Connection connection, FilePollerSettings filePollerSettings, int usrId)
	{
		String query = " update file_poller_settings set  fps_name = ? , fps_source_dir = ? , fps_freq_secs = ? , fps_poller_jbt_id = ? , fps_transfer_jbt_id = ? , fps_modified_usr_id = ? , fps_modified_dttm = ?  where fps_id = ?  ";
		Object[] paramValues = new Object[] { filePollerSettings.getFpsName(), filePollerSettings.getFpsSourceDir(), filePollerSettings.getFpsFreqSecs(), filePollerSettings.getFpsPollerJbtId(), filePollerSettings.getFpsTransferJbtId(), usrId, new DateTime(), filePollerSettings.getFpsId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into file_poller_settings values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { filePollerSettings.getFpsId(), filePollerSettings.getFpsName(), filePollerSettings.getFpsSourceDir(), filePollerSettings.getFpsFreqSecs(), filePollerSettings.getFpsPollerJbtId(), filePollerSettings.getFpsTransferJbtId(), filePollerSettings.getFpsBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<FilePollerSettings> fetchAll()
	{
		String query = " select * from file_poller_settings ";
		return RefDBHelper.getDB().fetchList(query, FilePollerSettingsImpl.class);
	}

	public List<FilePollerSettings> fetchAuditRowsByPk(int id)
	{
		String query = " select * from file_poller_settings where fps_id=? order by fps_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, FilePollerSettingsImpl.class);
	}

	public FilePollerSettings fetchByPk(int fpsId)
	{
		return fetchByPk(null, fpsId);
	}

	public FilePollerSettings fetchByPk(Connection connection, int fpsId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from file_poller_settings where fps_id=? ", fpsId, FilePollerSettingsImpl.class);
	}

	public FilePollerSettings fetchByBk(String fpsBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from file_poller_settings where fps_bk=? ", fpsBk, FilePollerSettingsImpl.class);
	}

	public FilePollerSettings fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from file_poller_settings where fps_name=? ", displayField, FilePollerSettingsImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("fps_name", "file_poller_settings");
	}

}