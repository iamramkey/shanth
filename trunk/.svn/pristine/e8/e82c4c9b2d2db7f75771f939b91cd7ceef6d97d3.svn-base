/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.PendingRecomputeSettingsDbaImpl;
import se.signa.signature.dbo.PendingRecomputeSettingsImpl;
import se.signa.signature.gen.dbo.PendingRecomputeSettings;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class PendingRecomputeSettingsDba extends SignatureDba<PendingRecomputeSettings>
{
	private static PendingRecomputeSettingsDbaImpl INSTANCE;

	public static PendingRecomputeSettingsDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new PendingRecomputeSettingsDbaImpl();
		return INSTANCE;
	}

	public PendingRecomputeSettingsDba()
	{
		tableName = "pending_recompute_settings";
		tablePrefix = "prc";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Prc Id");
		columns.add("Prc Name");
		columns.add("Prc Batch Size");
		columns.add("Prc Frequency Secs");
		columns.add("Prc Poller Jbt Id");
		columns.add("Prc Rerater Jbt Id");
		columns.add("Prc User Name");
		columns.add("Prc Dttm");
		return columns;
	}

	@Override
	public PendingRecomputeSettings createEmptyDbo()
	{
		return new PendingRecomputeSettingsImpl();
	}

	@Override
	public void checkDuplicates(PendingRecomputeSettings dbo)
	{
		checkDuplicateGM(dbo.getPrcName(), "prc_name", dbo.getPk());
		checkDuplicateGM(dbo.getPrcBk(), "prc_bk", dbo.getPk());
	}

	@Override
	public int create(PendingRecomputeSettings pendingRecomputeSettings, int usrId)
	{
		return create(null, pendingRecomputeSettings, usrId);
	}

	public int create(Connection connection, PendingRecomputeSettings pendingRecomputeSettings, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("prc_id", "pending_recompute_settings");
		String bk = pendingRecomputeSettings.getDisplayField();

		String query = " insert into pending_recompute_settings values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, pendingRecomputeSettings.getPrcName(), pendingRecomputeSettings.getPrcBatchSize(), pendingRecomputeSettings.getPrcFrequencySecs(), pendingRecomputeSettings.getPrcPollerJbtId(), pendingRecomputeSettings.getPrcReraterJbtId(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(PendingRecomputeSettings pendingRecomputeSettings, int usrId)
	{
		update(null, pendingRecomputeSettings, usrId);
	}

	public void update(Connection connection, PendingRecomputeSettings pendingRecomputeSettings, int usrId)
	{
		String query = " update pending_recompute_settings set  prc_name = ? , prc_batch_size = ? , prc_frequency_secs = ? , prc_poller_jbt_id = ? , prc_rerater_jbt_id = ? , prc_modified_usr_id = ? , prc_modified_dttm = ?  where prc_id = ?  ";
		Object[] paramValues = new Object[] { pendingRecomputeSettings.getPrcName(), pendingRecomputeSettings.getPrcBatchSize(), pendingRecomputeSettings.getPrcFrequencySecs(), pendingRecomputeSettings.getPrcPollerJbtId(), pendingRecomputeSettings.getPrcReraterJbtId(), usrId, new DateTime(), pendingRecomputeSettings.getPrcId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into pending_recompute_settings values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { pendingRecomputeSettings.getPrcId(), pendingRecomputeSettings.getPrcName(), pendingRecomputeSettings.getPrcBatchSize(), pendingRecomputeSettings.getPrcFrequencySecs(), pendingRecomputeSettings.getPrcPollerJbtId(), pendingRecomputeSettings.getPrcReraterJbtId(), pendingRecomputeSettings.getPrcBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<PendingRecomputeSettings> fetchAll()
	{
		String query = " select * from pending_recompute_settings ";
		return RefDBHelper.getDB().fetchList(query, PendingRecomputeSettingsImpl.class);
	}

	public List<PendingRecomputeSettings> fetchAuditRowsByPk(int id)
	{
		String query = " select * from pending_recompute_settings where prc_id=? order by prc_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, PendingRecomputeSettingsImpl.class);
	}

	public PendingRecomputeSettings fetchByPk(int prcId)
	{
		return fetchByPk(null, prcId);
	}

	public PendingRecomputeSettings fetchByPk(Connection connection, int prcId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from pending_recompute_settings where prc_id=? ", prcId, PendingRecomputeSettingsImpl.class);
	}

	public PendingRecomputeSettings fetchByBk(String prcBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_recompute_settings where prc_bk=? ", prcBk, PendingRecomputeSettingsImpl.class);
	}

	public PendingRecomputeSettings fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_recompute_settings where prc_name=? ", displayField, PendingRecomputeSettingsImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("prc_name", "pending_recompute_settings");
	}

}