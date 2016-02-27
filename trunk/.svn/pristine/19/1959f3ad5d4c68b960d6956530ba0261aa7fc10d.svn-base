/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.HealthCheckerSettingsDbaImpl;
import se.signa.signature.dbo.HealthCheckerSettingsImpl;
import se.signa.signature.gen.dbo.HealthCheckerSettings;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class HealthCheckerSettingsDba extends SignatureDba<HealthCheckerSettings>
{
	private static HealthCheckerSettingsDbaImpl INSTANCE;

	public static HealthCheckerSettingsDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new HealthCheckerSettingsDbaImpl();
		return INSTANCE;
	}

	public HealthCheckerSettingsDba()
	{
		tableName = "health_checker_settings";
		tablePrefix = "hcs";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Hcs Id");
		columns.add("Hcs Name");
		columns.add("Jbt Id");
		columns.add("Hcs Freq Secs");
		columns.add("Hcs User Name");
		columns.add("Hcs Dttm");
		return columns;
	}

	@Override
	public HealthCheckerSettings createEmptyDbo()
	{
		return new HealthCheckerSettingsImpl();
	}

	@Override
	public void checkDuplicates(HealthCheckerSettings dbo)
	{
		checkDuplicateGM(dbo.getHcsName(), "hcs_name", dbo.getPk());
		checkDuplicateGM(dbo.getHcsBk(), "hcs_bk", dbo.getPk());
	}

	@Override
	public int create(HealthCheckerSettings healthCheckerSettings, int usrId)
	{
		return create(null, healthCheckerSettings, usrId);
	}

	public int create(Connection connection, HealthCheckerSettings healthCheckerSettings, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("hcs_id", "health_checker_settings");
		String bk = healthCheckerSettings.getDisplayField();

		String query = " insert into health_checker_settings values(?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, healthCheckerSettings.getHcsName(), healthCheckerSettings.getJbtId(), healthCheckerSettings.getHcsFreqSecs(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(HealthCheckerSettings healthCheckerSettings, int usrId)
	{
		update(null, healthCheckerSettings, usrId);
	}

	public void update(Connection connection, HealthCheckerSettings healthCheckerSettings, int usrId)
	{
		String query = " update health_checker_settings set  hcs_name = ? , jbt_id = ? , hcs_freq_secs = ? , hcs_modified_usr_id = ? , hcs_modified_dttm = ?  where hcs_id = ?  ";
		Object[] paramValues = new Object[] { healthCheckerSettings.getHcsName(), healthCheckerSettings.getJbtId(), healthCheckerSettings.getHcsFreqSecs(), usrId, new DateTime(), healthCheckerSettings.getHcsId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into health_checker_settings values(?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { healthCheckerSettings.getHcsId(), healthCheckerSettings.getHcsName(), healthCheckerSettings.getJbtId(), healthCheckerSettings.getHcsFreqSecs(), healthCheckerSettings.getHcsBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<HealthCheckerSettings> fetchAll()
	{
		String query = " select * from health_checker_settings ";
		return RefDBHelper.getDB().fetchList(query, HealthCheckerSettingsImpl.class);
	}

	public List<HealthCheckerSettings> fetchAuditRowsByPk(int id)
	{
		String query = " select * from health_checker_settings where hcs_id=? order by hcs_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, HealthCheckerSettingsImpl.class);
	}

	public HealthCheckerSettings fetchByPk(int hcsId)
	{
		return fetchByPk(null, hcsId);
	}

	public HealthCheckerSettings fetchByPk(Connection connection, int hcsId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from health_checker_settings where hcs_id=? ", hcsId, HealthCheckerSettingsImpl.class);
	}

	public HealthCheckerSettings fetchByBk(String hcsBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from health_checker_settings where hcs_bk=? ", hcsBk, HealthCheckerSettingsImpl.class);
	}

	public HealthCheckerSettings fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from health_checker_settings where hcs_name=? ", displayField, HealthCheckerSettingsImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("hcs_name", "health_checker_settings");
	}

}