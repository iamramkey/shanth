/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.PendingJobPollerSettingsDbaImpl;
import se.signa.signature.dbo.PendingJobPollerSettingsImpl;
import se.signa.signature.gen.dbo.PendingJobPollerSettings;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class PendingJobPollerSettingsDba extends SignatureDba<PendingJobPollerSettings>
{
	private static PendingJobPollerSettingsDbaImpl INSTANCE;

	public static PendingJobPollerSettingsDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new PendingJobPollerSettingsDbaImpl();
		return INSTANCE;
	}

	public PendingJobPollerSettingsDba()
	{
		tableName = "pending_job_poller_settings";
		tablePrefix = "pjs";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Pjs Id");
		columns.add("Pjs Name");
		columns.add("Pjs Batch Size");
		columns.add("Pjs Frequency Secs");
		columns.add("Pjs Poller Jbt Id");
		columns.add("Pjs User Name");
		columns.add("Pjs Dttm");
		return columns;
	}

	@Override
	public PendingJobPollerSettings createEmptyDbo()
	{
		return new PendingJobPollerSettingsImpl();
	}

	@Override
	public void checkDuplicates(PendingJobPollerSettings dbo)
	{
		checkDuplicateGM(dbo.getPjsName(), "pjs_name", dbo.getPk());
		checkDuplicateGM(dbo.getPjsBk(), "pjs_bk", dbo.getPk());
	}

	@Override
	public int create(PendingJobPollerSettings pendingJobPollerSettings, int usrId)
	{
		return create(null, pendingJobPollerSettings, usrId);
	}

	public int create(Connection connection, PendingJobPollerSettings pendingJobPollerSettings, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("pjs_id", "pending_job_poller_settings");
		String bk = pendingJobPollerSettings.getDisplayField();

		String query = " insert into pending_job_poller_settings values(?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, pendingJobPollerSettings.getPjsName(), pendingJobPollerSettings.getPjsBatchSize(), pendingJobPollerSettings.getPjsFrequencySecs(), pendingJobPollerSettings.getPjsPollerJbtId(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(PendingJobPollerSettings pendingJobPollerSettings, int usrId)
	{
		update(null, pendingJobPollerSettings, usrId);
	}

	public void update(Connection connection, PendingJobPollerSettings pendingJobPollerSettings, int usrId)
	{
		String query = " update pending_job_poller_settings set  pjs_name = ? , pjs_batch_size = ? , pjs_frequency_secs = ? , pjs_poller_jbt_id = ? , pjs_modified_usr_id = ? , pjs_modified_dttm = ?  where pjs_id = ?  ";
		Object[] paramValues = new Object[] { pendingJobPollerSettings.getPjsName(), pendingJobPollerSettings.getPjsBatchSize(), pendingJobPollerSettings.getPjsFrequencySecs(), pendingJobPollerSettings.getPjsPollerJbtId(), usrId, new DateTime(), pendingJobPollerSettings.getPjsId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into pending_job_poller_settings values(?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { pendingJobPollerSettings.getPjsId(), pendingJobPollerSettings.getPjsName(), pendingJobPollerSettings.getPjsBatchSize(), pendingJobPollerSettings.getPjsFrequencySecs(), pendingJobPollerSettings.getPjsPollerJbtId(), pendingJobPollerSettings.getPjsBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<PendingJobPollerSettings> fetchAll()
	{
		String query = " select * from pending_job_poller_settings ";
		return RefDBHelper.getDB().fetchList(query, PendingJobPollerSettingsImpl.class);
	}

	public List<PendingJobPollerSettings> fetchAuditRowsByPk(int id)
	{
		String query = " select * from pending_job_poller_settings where pjs_id=? order by pjs_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, PendingJobPollerSettingsImpl.class);
	}

	public PendingJobPollerSettings fetchByPk(int pjsId)
	{
		return fetchByPk(null, pjsId);
	}

	public PendingJobPollerSettings fetchByPk(Connection connection, int pjsId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from pending_job_poller_settings where pjs_id=? ", pjsId, PendingJobPollerSettingsImpl.class);
	}

	public PendingJobPollerSettings fetchByBk(String pjsBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_job_poller_settings where pjs_bk=? ", pjsBk, PendingJobPollerSettingsImpl.class);
	}

	public PendingJobPollerSettings fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_job_poller_settings where pjs_name=? ", displayField, PendingJobPollerSettingsImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("pjs_name", "pending_job_poller_settings");
	}

}