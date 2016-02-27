/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.PendingCcSettingsDbaImpl;
import se.signa.signature.dbo.PendingCcSettingsImpl;
import se.signa.signature.gen.dbo.PendingCcSettings;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class PendingCcSettingsDba extends SignatureDba<PendingCcSettings>
{
	private static PendingCcSettingsDbaImpl INSTANCE;

	public static PendingCcSettingsDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new PendingCcSettingsDbaImpl();
		return INSTANCE;
	}

	public PendingCcSettingsDba()
	{
		tableName = "pending_cc_settings";
		tablePrefix = "pcs";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Pcs Id");
		columns.add("Pcs Name");
		columns.add("Pcs Batch Size");
		columns.add("Pcs Frequency Secs");
		columns.add("Pcs Timeout Secs");
		columns.add("Pcs Poller Jbt Id");
		columns.add("Pcs Cc Jbt Id");
		columns.add("Pcs User Name");
		columns.add("Pcs Dttm");
		return columns;
	}

	@Override
	public PendingCcSettings createEmptyDbo()
	{
		return new PendingCcSettingsImpl();
	}

	@Override
	public void checkDuplicates(PendingCcSettings dbo)
	{
		checkDuplicateGM(dbo.getPcsName(), "pcs_name", dbo.getPk());
		checkDuplicateGM(dbo.getPcsBk(), "pcs_bk", dbo.getPk());
	}

	@Override
	public int create(PendingCcSettings pendingCcSettings, int usrId)
	{
		return create(null, pendingCcSettings, usrId);
	}

	public int create(Connection connection, PendingCcSettings pendingCcSettings, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("pcs_id", "pending_cc_settings");
		String bk = pendingCcSettings.getDisplayField();

		String query = " insert into pending_cc_settings values(?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, pendingCcSettings.getPcsName(), pendingCcSettings.getPcsBatchSize(), pendingCcSettings.getPcsFrequencySecs(), pendingCcSettings.getPcsTimeoutSecs(), pendingCcSettings.getPcsPollerJbtId(), pendingCcSettings.getPcsCcJbtId(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(PendingCcSettings pendingCcSettings, int usrId)
	{
		update(null, pendingCcSettings, usrId);
	}

	public void update(Connection connection, PendingCcSettings pendingCcSettings, int usrId)
	{
		String query = " update pending_cc_settings set  pcs_name = ? , pcs_batch_size = ? , pcs_frequency_secs = ? , pcs_timeout_secs = ? , pcs_poller_jbt_id = ? , pcs_cc_jbt_id = ? , pcs_modified_usr_id = ? , pcs_modified_dttm = ?  where pcs_id = ?  ";
		Object[] paramValues = new Object[] { pendingCcSettings.getPcsName(), pendingCcSettings.getPcsBatchSize(), pendingCcSettings.getPcsFrequencySecs(), pendingCcSettings.getPcsTimeoutSecs(), pendingCcSettings.getPcsPollerJbtId(), pendingCcSettings.getPcsCcJbtId(), usrId, new DateTime(), pendingCcSettings.getPcsId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into pending_cc_settings values(?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { pendingCcSettings.getPcsId(), pendingCcSettings.getPcsName(), pendingCcSettings.getPcsBatchSize(), pendingCcSettings.getPcsFrequencySecs(), pendingCcSettings.getPcsTimeoutSecs(), pendingCcSettings.getPcsPollerJbtId(), pendingCcSettings.getPcsCcJbtId(), pendingCcSettings.getPcsBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<PendingCcSettings> fetchAll()
	{
		String query = " select * from pending_cc_settings ";
		return RefDBHelper.getDB().fetchList(query, PendingCcSettingsImpl.class);
	}

	public List<PendingCcSettings> fetchAuditRowsByPk(int id)
	{
		String query = " select * from pending_cc_settings where pcs_id=? order by pcs_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, PendingCcSettingsImpl.class);
	}

	public PendingCcSettings fetchByPk(int pcsId)
	{
		return fetchByPk(null, pcsId);
	}

	public PendingCcSettings fetchByPk(Connection connection, int pcsId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from pending_cc_settings where pcs_id=? ", pcsId, PendingCcSettingsImpl.class);
	}

	public PendingCcSettings fetchByBk(String pcsBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_cc_settings where pcs_bk=? ", pcsBk, PendingCcSettingsImpl.class);
	}

	public PendingCcSettings fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_cc_settings where pcs_name=? ", displayField, PendingCcSettingsImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("pcs_name", "pending_cc_settings");
	}

}