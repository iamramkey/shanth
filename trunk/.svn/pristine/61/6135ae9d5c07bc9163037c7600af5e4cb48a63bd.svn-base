/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.PendingDataloadSettingsDbaImpl;
import se.signa.signature.dbo.PendingDataloadSettingsImpl;
import se.signa.signature.gen.dbo.PendingDataloadSettings;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class PendingDataloadSettingsDba extends SignatureDba<PendingDataloadSettings>
{
	private static PendingDataloadSettingsDbaImpl INSTANCE;

	public static PendingDataloadSettingsDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new PendingDataloadSettingsDbaImpl();
		return INSTANCE;
	}

	public PendingDataloadSettingsDba()
	{
		tableName = "pending_dataload_settings";
		tablePrefix = "pds";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Pds Id");
		columns.add("Pds Name");
		columns.add("Pds Batch Size");
		columns.add("Pds Frequency Secs");
		columns.add("Pds Timeout Secs");
		columns.add("Pds Poller Jbt Id");
		columns.add("Pds Dataload Jbt Id");
		columns.add("Pds User Name");
		columns.add("Pds Dttm");
		return columns;
	}

	@Override
	public PendingDataloadSettings createEmptyDbo()
	{
		return new PendingDataloadSettingsImpl();
	}

	@Override
	public void checkDuplicates(PendingDataloadSettings dbo)
	{
		checkDuplicateGM(dbo.getPdsName(), "pds_name", dbo.getPk());
		checkDuplicateGM(dbo.getPdsBk(), "pds_bk", dbo.getPk());
	}

	@Override
	public int create(PendingDataloadSettings pendingDataloadSettings, int usrId)
	{
		return create(null, pendingDataloadSettings, usrId);
	}

	public int create(Connection connection, PendingDataloadSettings pendingDataloadSettings, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("pds_id", "pending_dataload_settings");
		String bk = pendingDataloadSettings.getDisplayField();

		String query = " insert into pending_dataload_settings values(?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, pendingDataloadSettings.getPdsName(), pendingDataloadSettings.getPdsBatchSize(), pendingDataloadSettings.getPdsFrequencySecs(), pendingDataloadSettings.getPdsTimeoutSecs(), pendingDataloadSettings.getPdsPollerJbtId(), pendingDataloadSettings.getPdsDataloadJbtId(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(PendingDataloadSettings pendingDataloadSettings, int usrId)
	{
		update(null, pendingDataloadSettings, usrId);
	}

	public void update(Connection connection, PendingDataloadSettings pendingDataloadSettings, int usrId)
	{
		String query = " update pending_dataload_settings set  pds_name = ? , pds_batch_size = ? , pds_frequency_secs = ? , pds_timeout_secs = ? , pds_poller_jbt_id = ? , pds_dataload_jbt_id = ? , pds_modified_usr_id = ? , pds_modified_dttm = ?  where pds_id = ?  ";
		Object[] paramValues = new Object[] { pendingDataloadSettings.getPdsName(), pendingDataloadSettings.getPdsBatchSize(), pendingDataloadSettings.getPdsFrequencySecs(), pendingDataloadSettings.getPdsTimeoutSecs(), pendingDataloadSettings.getPdsPollerJbtId(), pendingDataloadSettings.getPdsDataloadJbtId(), usrId, new DateTime(), pendingDataloadSettings.getPdsId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into pending_dataload_settings values(?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { pendingDataloadSettings.getPdsId(), pendingDataloadSettings.getPdsName(), pendingDataloadSettings.getPdsBatchSize(), pendingDataloadSettings.getPdsFrequencySecs(), pendingDataloadSettings.getPdsTimeoutSecs(), pendingDataloadSettings.getPdsPollerJbtId(), pendingDataloadSettings.getPdsDataloadJbtId(), pendingDataloadSettings.getPdsBk(), usrId,
				usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<PendingDataloadSettings> fetchAll()
	{
		String query = " select * from pending_dataload_settings ";
		return RefDBHelper.getDB().fetchList(query, PendingDataloadSettingsImpl.class);
	}

	public List<PendingDataloadSettings> fetchAuditRowsByPk(int id)
	{
		String query = " select * from pending_dataload_settings where pds_id=? order by pds_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, PendingDataloadSettingsImpl.class);
	}

	public PendingDataloadSettings fetchByPk(int pdsId)
	{
		return fetchByPk(null, pdsId);
	}

	public PendingDataloadSettings fetchByPk(Connection connection, int pdsId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from pending_dataload_settings where pds_id=? ", pdsId, PendingDataloadSettingsImpl.class);
	}

	public PendingDataloadSettings fetchByBk(String pdsBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_dataload_settings where pds_bk=? ", pdsBk, PendingDataloadSettingsImpl.class);
	}

	public PendingDataloadSettings fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_dataload_settings where pds_name=? ", displayField, PendingDataloadSettingsImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("pds_name", "pending_dataload_settings");
	}

}