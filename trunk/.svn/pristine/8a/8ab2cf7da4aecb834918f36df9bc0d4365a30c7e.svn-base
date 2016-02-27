/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.RaterSettingsDbaImpl;
import se.signa.signature.dbo.RaterSettingsImpl;
import se.signa.signature.gen.dbo.RaterSettings;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class RaterSettingsDba extends SignatureDba<RaterSettings>
{
	private static RaterSettingsDbaImpl INSTANCE;

	public static RaterSettingsDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new RaterSettingsDbaImpl();
		return INSTANCE;
	}

	public RaterSettingsDba()
	{
		tableName = "rater_settings";
		tablePrefix = "ras";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Ras Id");
		columns.add("Ras Name");
		columns.add("Ras Target Dir");
		columns.add("Ras Bix Target Dir");
		columns.add("Ras Fps Id");
		columns.add("Ras Rater Jbt Id");
		columns.add("Ras Re Rater Jbt Id");
		columns.add("Ras Dataload Jbt Id");
		columns.add("Ras User Name");
		columns.add("Ras Dttm");
		return columns;
	}

	@Override
	public RaterSettings createEmptyDbo()
	{
		return new RaterSettingsImpl();
	}

	@Override
	public void checkDuplicates(RaterSettings dbo)
	{
		checkDuplicateGM(dbo.getRasName(), "ras_name", dbo.getPk());
		checkDuplicateGM(dbo.getRasBk(), "ras_bk", dbo.getPk());
	}

	@Override
	public int create(RaterSettings raterSettings, int usrId)
	{
		return create(null, raterSettings, usrId);
	}

	public int create(Connection connection, RaterSettings raterSettings, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("ras_id", "rater_settings");
		String bk = raterSettings.getDisplayField();

		String query = " insert into rater_settings values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, raterSettings.getRasName(), raterSettings.getRasTargetDir(), raterSettings.getRasBixTargetDir(), raterSettings.getRasFpsId(), raterSettings.getRasRaterJbtId(), raterSettings.getRasReRaterJbtId(), raterSettings.getRasDataloadJbtId(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(RaterSettings raterSettings, int usrId)
	{
		update(null, raterSettings, usrId);
	}

	public void update(Connection connection, RaterSettings raterSettings, int usrId)
	{
		String query = " update rater_settings set  ras_name = ? , ras_target_dir = ? , ras_bix_target_dir = ? , ras_fps_id = ? , ras_rater_jbt_id = ? , ras_re_rater_jbt_id = ? , ras_dataload_jbt_id = ? , ras_modified_usr_id = ? , ras_modified_dttm = ?  where ras_id = ?  ";
		Object[] paramValues = new Object[] { raterSettings.getRasName(), raterSettings.getRasTargetDir(), raterSettings.getRasBixTargetDir(), raterSettings.getRasFpsId(), raterSettings.getRasRaterJbtId(), raterSettings.getRasReRaterJbtId(), raterSettings.getRasDataloadJbtId(), usrId, new DateTime(), raterSettings.getRasId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into rater_settings values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { raterSettings.getRasId(), raterSettings.getRasName(), raterSettings.getRasTargetDir(), raterSettings.getRasBixTargetDir(), raterSettings.getRasFpsId(), raterSettings.getRasRaterJbtId(), raterSettings.getRasReRaterJbtId(), raterSettings.getRasDataloadJbtId(), raterSettings.getRasBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<RaterSettings> fetchAll()
	{
		String query = " select * from rater_settings ";
		return RefDBHelper.getDB().fetchList(query, RaterSettingsImpl.class);
	}

	public List<RaterSettings> fetchAuditRowsByPk(int id)
	{
		String query = " select * from rater_settings where ras_id=? order by ras_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, RaterSettingsImpl.class);
	}

	public RaterSettings fetchByPk(int rasId)
	{
		return fetchByPk(null, rasId);
	}

	public RaterSettings fetchByPk(Connection connection, int rasId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from rater_settings where ras_id=? ", rasId, RaterSettingsImpl.class);
	}

	public RaterSettings fetchByBk(String rasBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from rater_settings where ras_bk=? ", rasBk, RaterSettingsImpl.class);
	}

	public RaterSettings fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from rater_settings where ras_name=? ", displayField, RaterSettingsImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("ras_name", "rater_settings");
	}

}