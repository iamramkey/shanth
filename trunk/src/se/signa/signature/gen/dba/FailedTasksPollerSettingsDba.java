/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.FailedTasksPollerSettingsDbaImpl;
import se.signa.signature.dbo.FailedTasksPollerSettingsImpl;
import se.signa.signature.gen.dbo.FailedTasksPollerSettings;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class FailedTasksPollerSettingsDba extends SignatureDba<FailedTasksPollerSettings>
{
	private static FailedTasksPollerSettingsDbaImpl INSTANCE;

	public static FailedTasksPollerSettingsDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new FailedTasksPollerSettingsDbaImpl();
		return INSTANCE;
	}

	public FailedTasksPollerSettingsDba()
	{
		tableName = "failed_tasks_poller_settings";
		tablePrefix = "fap";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Fap Id");
		columns.add("Fap Name");
		columns.add("Fap Freq Secs");
		columns.add("Fap Retry Count");
		columns.add("Fap Lookback Days");
		columns.add("Fap Poller Jbt Id");
		columns.add("Fap User Name");
		columns.add("Fap Dttm");
		return columns;
	}

	@Override
	public FailedTasksPollerSettings createEmptyDbo()
	{
		return new FailedTasksPollerSettingsImpl();
	}

	@Override
	public void checkDuplicates(FailedTasksPollerSettings dbo)
	{
		checkDuplicateGM(dbo.getFapName(), "fap_name", dbo.getPk());
		checkDuplicateGM(dbo.getFapBk(), "fap_bk", dbo.getPk());
	}

	@Override
	public int create(FailedTasksPollerSettings failedTasksPollerSettings, int usrId)
	{
		return create(null, failedTasksPollerSettings, usrId);
	}

	public int create(Connection connection, FailedTasksPollerSettings failedTasksPollerSettings, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("fap_id", "failed_tasks_poller_settings");
		String bk = failedTasksPollerSettings.getDisplayField();

		String query = " insert into failed_tasks_poller_settings values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, failedTasksPollerSettings.getFapName(), failedTasksPollerSettings.getFapFreqSecs(), failedTasksPollerSettings.getFapRetryCount(), failedTasksPollerSettings.getFapLookbackDays(), failedTasksPollerSettings.getFapPollerJbtId(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(FailedTasksPollerSettings failedTasksPollerSettings, int usrId)
	{
		update(null, failedTasksPollerSettings, usrId);
	}

	public void update(Connection connection, FailedTasksPollerSettings failedTasksPollerSettings, int usrId)
	{
		String query = " update failed_tasks_poller_settings set  fap_name = ? , fap_freq_secs = ? , fap_retry_count = ? , fap_lookback_days = ? , fap_poller_jbt_id = ? , fap_modified_usr_id = ? , fap_modified_dttm = ?  where fap_id = ?  ";
		Object[] paramValues = new Object[] { failedTasksPollerSettings.getFapName(), failedTasksPollerSettings.getFapFreqSecs(), failedTasksPollerSettings.getFapRetryCount(), failedTasksPollerSettings.getFapLookbackDays(), failedTasksPollerSettings.getFapPollerJbtId(), usrId, new DateTime(), failedTasksPollerSettings.getFapId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into failed_tasks_poller_settings values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { failedTasksPollerSettings.getFapId(), failedTasksPollerSettings.getFapName(), failedTasksPollerSettings.getFapFreqSecs(), failedTasksPollerSettings.getFapRetryCount(), failedTasksPollerSettings.getFapLookbackDays(), failedTasksPollerSettings.getFapPollerJbtId(), failedTasksPollerSettings.getFapBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<FailedTasksPollerSettings> fetchAll()
	{
		String query = " select * from failed_tasks_poller_settings ";
		return RefDBHelper.getDB().fetchList(query, FailedTasksPollerSettingsImpl.class);
	}

	public List<FailedTasksPollerSettings> fetchAuditRowsByPk(int id)
	{
		String query = " select * from failed_tasks_poller_settings where fap_id=? order by fap_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, FailedTasksPollerSettingsImpl.class);
	}

	public FailedTasksPollerSettings fetchByPk(int fapId)
	{
		return fetchByPk(null, fapId);
	}

	public FailedTasksPollerSettings fetchByPk(Connection connection, int fapId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from failed_tasks_poller_settings where fap_id=? ", fapId, FailedTasksPollerSettingsImpl.class);
	}

	public FailedTasksPollerSettings fetchByBk(String fapBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from failed_tasks_poller_settings where fap_bk=? ", fapBk, FailedTasksPollerSettingsImpl.class);
	}

	public FailedTasksPollerSettings fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from failed_tasks_poller_settings where fap_name=? ", displayField, FailedTasksPollerSettingsImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("fap_name", "failed_tasks_poller_settings");
	}

}