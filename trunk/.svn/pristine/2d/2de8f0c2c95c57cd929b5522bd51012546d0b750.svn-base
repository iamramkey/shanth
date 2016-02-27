/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.PendingBixOpFileSettingsDbaImpl;
import se.signa.signature.dbo.PendingBixOpFileSettingsImpl;
import se.signa.signature.gen.dbo.PendingBixOpFileSettings;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class PendingBixOpFileSettingsDba extends SignatureDba<PendingBixOpFileSettings>
{
	private static PendingBixOpFileSettingsDbaImpl INSTANCE;

	public static PendingBixOpFileSettingsDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new PendingBixOpFileSettingsDbaImpl();
		return INSTANCE;
	}

	public PendingBixOpFileSettingsDba()
	{
		tableName = "pending_bix_op_file_settings";
		tablePrefix = "pbs";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Pbs Id");
		columns.add("Pbs Name");
		columns.add("Pbs Batch Size");
		columns.add("Pbs Frequency Secs");
		columns.add("Pbs Timeout Secs");
		columns.add("Pbs Poller Jbt Id");
		columns.add("Pbs Bix Export Jbt Id");
		columns.add("Pbs User Name");
		columns.add("Pbs Dttm");
		return columns;
	}

	@Override
	public PendingBixOpFileSettings createEmptyDbo()
	{
		return new PendingBixOpFileSettingsImpl();
	}

	@Override
	public void checkDuplicates(PendingBixOpFileSettings dbo)
	{
		checkDuplicateGM(dbo.getPbsName(), "pbs_name", dbo.getPk());
		checkDuplicateGM(dbo.getPbsBk(), "pbs_bk", dbo.getPk());
	}

	@Override
	public int create(PendingBixOpFileSettings pendingBixOpFileSettings, int usrId)
	{
		return create(null, pendingBixOpFileSettings, usrId);
	}

	public int create(Connection connection, PendingBixOpFileSettings pendingBixOpFileSettings, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("pbs_id", "pending_bix_op_file_settings");
		String bk = pendingBixOpFileSettings.getDisplayField();

		String query = " insert into pending_bix_op_file_settings values(?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, pendingBixOpFileSettings.getPbsName(), pendingBixOpFileSettings.getPbsBatchSize(), pendingBixOpFileSettings.getPbsFrequencySecs(), pendingBixOpFileSettings.getPbsTimeoutSecs(), pendingBixOpFileSettings.getPbsPollerJbtId(), pendingBixOpFileSettings.getPbsBixExportJbtId(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(PendingBixOpFileSettings pendingBixOpFileSettings, int usrId)
	{
		update(null, pendingBixOpFileSettings, usrId);
	}

	public void update(Connection connection, PendingBixOpFileSettings pendingBixOpFileSettings, int usrId)
	{
		String query = " update pending_bix_op_file_settings set  pbs_name = ? , pbs_batch_size = ? , pbs_frequency_secs = ? , pbs_timeout_secs = ? , pbs_poller_jbt_id = ? , pbs_bix_export_jbt_id = ? , pbs_modified_usr_id = ? , pbs_modified_dttm = ?  where pbs_id = ?  ";
		Object[] paramValues = new Object[] { pendingBixOpFileSettings.getPbsName(), pendingBixOpFileSettings.getPbsBatchSize(), pendingBixOpFileSettings.getPbsFrequencySecs(), pendingBixOpFileSettings.getPbsTimeoutSecs(), pendingBixOpFileSettings.getPbsPollerJbtId(), pendingBixOpFileSettings.getPbsBixExportJbtId(), usrId, new DateTime(), pendingBixOpFileSettings.getPbsId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into pending_bix_op_file_settings values(?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { pendingBixOpFileSettings.getPbsId(), pendingBixOpFileSettings.getPbsName(), pendingBixOpFileSettings.getPbsBatchSize(), pendingBixOpFileSettings.getPbsFrequencySecs(), pendingBixOpFileSettings.getPbsTimeoutSecs(), pendingBixOpFileSettings.getPbsPollerJbtId(), pendingBixOpFileSettings.getPbsBixExportJbtId(), pendingBixOpFileSettings.getPbsBk(),
				usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<PendingBixOpFileSettings> fetchAll()
	{
		String query = " select * from pending_bix_op_file_settings ";
		return RefDBHelper.getDB().fetchList(query, PendingBixOpFileSettingsImpl.class);
	}

	public List<PendingBixOpFileSettings> fetchAuditRowsByPk(int id)
	{
		String query = " select * from pending_bix_op_file_settings where pbs_id=? order by pbs_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, PendingBixOpFileSettingsImpl.class);
	}

	public PendingBixOpFileSettings fetchByPk(int pbsId)
	{
		return fetchByPk(null, pbsId);
	}

	public PendingBixOpFileSettings fetchByPk(Connection connection, int pbsId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from pending_bix_op_file_settings where pbs_id=? ", pbsId, PendingBixOpFileSettingsImpl.class);
	}

	public PendingBixOpFileSettings fetchByBk(String pbsBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_bix_op_file_settings where pbs_bk=? ", pbsBk, PendingBixOpFileSettingsImpl.class);
	}

	public PendingBixOpFileSettings fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_bix_op_file_settings where pbs_name=? ", displayField, PendingBixOpFileSettingsImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("pbs_name", "pending_bix_op_file_settings");
	}

}