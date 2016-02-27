/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.PendingMissingJobSettingsDbaImpl;
import se.signa.signature.dbo.PendingMissingJobSettingsImpl;
import se.signa.signature.gen.dbo.PendingMissingJobSettings;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class PendingMissingJobSettingsDba extends SignatureDba<PendingMissingJobSettings>
{
	private static PendingMissingJobSettingsDbaImpl INSTANCE;

	public static PendingMissingJobSettingsDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new PendingMissingJobSettingsDbaImpl();
		return INSTANCE;
	}

	public PendingMissingJobSettingsDba()
	{
		tableName = "pending_missing_job_settings";
		tablePrefix = "pem";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Pem Id");
		columns.add("Pem Name");
		columns.add("Pem Frequency Secs");
		columns.add("Pem Pending Missing P Jbt Id");
		columns.add("Pem Pending Missing Jbt Id");
		columns.add("Pem User Name");
		columns.add("Pem Dttm");
		return columns;
	}

	@Override
	public PendingMissingJobSettings createEmptyDbo()
	{
		return new PendingMissingJobSettingsImpl();
	}

	@Override
	public void checkDuplicates(PendingMissingJobSettings dbo)
	{
		checkDuplicateGM(dbo.getPemName(), "pem_name", dbo.getPk());
		checkDuplicateGM(dbo.getPemBk(), "pem_bk", dbo.getPk());
	}

	@Override
	public int create(PendingMissingJobSettings pendingMissingJobSettings, int usrId)
	{
		return create(null, pendingMissingJobSettings, usrId);
	}

	public int create(Connection connection, PendingMissingJobSettings pendingMissingJobSettings, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("pem_id", "pending_missing_job_settings");
		String bk = pendingMissingJobSettings.getDisplayField();

		String query = " insert into pending_missing_job_settings values(?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, pendingMissingJobSettings.getPemName(), pendingMissingJobSettings.getPemFrequencySecs(), pendingMissingJobSettings.getPemPendingMissingPJbtId(), pendingMissingJobSettings.getPemPendingMissingJbtId(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(PendingMissingJobSettings pendingMissingJobSettings, int usrId)
	{
		update(null, pendingMissingJobSettings, usrId);
	}

	public void update(Connection connection, PendingMissingJobSettings pendingMissingJobSettings, int usrId)
	{
		String query = " update pending_missing_job_settings set  pem_name = ? , pem_frequency_secs = ? , pem_pending_missing_p_jbt_id = ? , pem_pending_missing_jbt_id = ? , pem_modified_usr_id = ? , pem_modified_dttm = ?  where pem_id = ?  ";
		Object[] paramValues = new Object[] { pendingMissingJobSettings.getPemName(), pendingMissingJobSettings.getPemFrequencySecs(), pendingMissingJobSettings.getPemPendingMissingPJbtId(), pendingMissingJobSettings.getPemPendingMissingJbtId(), usrId, new DateTime(), pendingMissingJobSettings.getPemId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into pending_missing_job_settings values(?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { pendingMissingJobSettings.getPemId(), pendingMissingJobSettings.getPemName(), pendingMissingJobSettings.getPemFrequencySecs(), pendingMissingJobSettings.getPemPendingMissingPJbtId(), pendingMissingJobSettings.getPemPendingMissingJbtId(), pendingMissingJobSettings.getPemBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<PendingMissingJobSettings> fetchAll()
	{
		String query = " select * from pending_missing_job_settings ";
		return RefDBHelper.getDB().fetchList(query, PendingMissingJobSettingsImpl.class);
	}

	public List<PendingMissingJobSettings> fetchAuditRowsByPk(int id)
	{
		String query = " select * from pending_missing_job_settings where pem_id=? order by pem_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, PendingMissingJobSettingsImpl.class);
	}

	public PendingMissingJobSettings fetchByPk(int pemId)
	{
		return fetchByPk(null, pemId);
	}

	public PendingMissingJobSettings fetchByPk(Connection connection, int pemId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from pending_missing_job_settings where pem_id=? ", pemId, PendingMissingJobSettingsImpl.class);
	}

	public PendingMissingJobSettings fetchByBk(String pemBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_missing_job_settings where pem_bk=? ", pemBk, PendingMissingJobSettingsImpl.class);
	}

	public PendingMissingJobSettings fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_missing_job_settings where pem_name=? ", displayField, PendingMissingJobSettingsImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("pem_name", "pending_missing_job_settings");
	}

}