/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.PendingRaterFileSettingsDbaImpl;
import se.signa.signature.dbo.PendingRaterFileSettingsImpl;
import se.signa.signature.gen.dbo.PendingRaterFileSettings;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class PendingRaterFileSettingsDba extends SignatureDba<PendingRaterFileSettings>
{
	private static PendingRaterFileSettingsDbaImpl INSTANCE;

	public static PendingRaterFileSettingsDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new PendingRaterFileSettingsDbaImpl();
		return INSTANCE;
	}

	public PendingRaterFileSettingsDba()
	{
		tableName = "pending_rater_file_settings";
		tablePrefix = "prs";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Prs Id");
		columns.add("Prs Name");
		columns.add("Prs Batch Size");
		columns.add("Prs Frequency Secs");
		columns.add("Prs Timeout Secs");
		columns.add("Prs Poller Jbt Id");
		columns.add("Prs Transfer Jbt Id");
		columns.add("Prs Rater Jbt Id");
		columns.add("Prs Re Rater Jbt Id");
		columns.add("Prs User Name");
		columns.add("Prs Dttm");
		return columns;
	}

	@Override
	public PendingRaterFileSettings createEmptyDbo()
	{
		return new PendingRaterFileSettingsImpl();
	}

	@Override
	public void checkDuplicates(PendingRaterFileSettings dbo)
	{
		checkDuplicateGM(dbo.getPrsName(), "prs_name", dbo.getPk());
		checkDuplicateGM(dbo.getPrsBk(), "prs_bk", dbo.getPk());
	}

	@Override
	public int create(PendingRaterFileSettings pendingRaterFileSettings, int usrId)
	{
		return create(null, pendingRaterFileSettings, usrId);
	}

	public int create(Connection connection, PendingRaterFileSettings pendingRaterFileSettings, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("prs_id", "pending_rater_file_settings");
		String bk = pendingRaterFileSettings.getDisplayField();

		String query = " insert into pending_rater_file_settings values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, pendingRaterFileSettings.getPrsName(), pendingRaterFileSettings.getPrsBatchSize(), pendingRaterFileSettings.getPrsFrequencySecs(), pendingRaterFileSettings.getPrsTimeoutSecs(), pendingRaterFileSettings.getPrsPollerJbtId(), pendingRaterFileSettings.getPrsTransferJbtId(), pendingRaterFileSettings.getPrsRaterJbtId(),
				pendingRaterFileSettings.getPrsReRaterJbtId(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(PendingRaterFileSettings pendingRaterFileSettings, int usrId)
	{
		update(null, pendingRaterFileSettings, usrId);
	}

	public void update(Connection connection, PendingRaterFileSettings pendingRaterFileSettings, int usrId)
	{
		String query = " update pending_rater_file_settings set  prs_name = ? , prs_batch_size = ? , prs_frequency_secs = ? , prs_timeout_secs = ? , prs_poller_jbt_id = ? , prs_transfer_jbt_id = ? , prs_rater_jbt_id = ? , prs_re_rater_jbt_id = ? , prs_modified_usr_id = ? , prs_modified_dttm = ?  where prs_id = ?  ";
		Object[] paramValues = new Object[] { pendingRaterFileSettings.getPrsName(), pendingRaterFileSettings.getPrsBatchSize(), pendingRaterFileSettings.getPrsFrequencySecs(), pendingRaterFileSettings.getPrsTimeoutSecs(), pendingRaterFileSettings.getPrsPollerJbtId(), pendingRaterFileSettings.getPrsTransferJbtId(), pendingRaterFileSettings.getPrsRaterJbtId(),
				pendingRaterFileSettings.getPrsReRaterJbtId(), usrId, new DateTime(), pendingRaterFileSettings.getPrsId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into pending_rater_file_settings values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { pendingRaterFileSettings.getPrsId(), pendingRaterFileSettings.getPrsName(), pendingRaterFileSettings.getPrsBatchSize(), pendingRaterFileSettings.getPrsFrequencySecs(), pendingRaterFileSettings.getPrsTimeoutSecs(), pendingRaterFileSettings.getPrsPollerJbtId(), pendingRaterFileSettings.getPrsTransferJbtId(),
				pendingRaterFileSettings.getPrsRaterJbtId(), pendingRaterFileSettings.getPrsReRaterJbtId(), pendingRaterFileSettings.getPrsBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<PendingRaterFileSettings> fetchAll()
	{
		String query = " select * from pending_rater_file_settings ";
		return RefDBHelper.getDB().fetchList(query, PendingRaterFileSettingsImpl.class);
	}

	public List<PendingRaterFileSettings> fetchAuditRowsByPk(int id)
	{
		String query = " select * from pending_rater_file_settings where prs_id=? order by prs_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, PendingRaterFileSettingsImpl.class);
	}

	public PendingRaterFileSettings fetchByPk(int prsId)
	{
		return fetchByPk(null, prsId);
	}

	public PendingRaterFileSettings fetchByPk(Connection connection, int prsId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from pending_rater_file_settings where prs_id=? ", prsId, PendingRaterFileSettingsImpl.class);
	}

	public PendingRaterFileSettings fetchByBk(String prsBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_rater_file_settings where prs_bk=? ", prsBk, PendingRaterFileSettingsImpl.class);
	}

	public PendingRaterFileSettings fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_rater_file_settings where prs_name=? ", displayField, PendingRaterFileSettingsImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("prs_name", "pending_rater_file_settings");
	}

}