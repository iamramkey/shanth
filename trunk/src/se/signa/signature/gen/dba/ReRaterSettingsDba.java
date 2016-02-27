/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.ReRaterSettingsDbaImpl;
import se.signa.signature.dbo.ReRaterSettingsImpl;
import se.signa.signature.gen.dbo.ReRaterSettings;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class ReRaterSettingsDba extends SignatureDba<ReRaterSettings>
{
	private static ReRaterSettingsDbaImpl INSTANCE;

	public static ReRaterSettingsDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new ReRaterSettingsDbaImpl();
		return INSTANCE;
	}

	public ReRaterSettingsDba()
	{
		tableName = "re_rater_settings";
		tablePrefix = "rrs";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Rrs Id");
		columns.add("Rrs Name");
		columns.add("Rrs Target Dir");
		columns.add("Rrs Bix Target Dir");
		columns.add("Rrs Fps Id");
		columns.add("Rrs Re Rater Jbt Id");
		columns.add("Rrs Dataload Jbt Id");
		columns.add("Rrs User Name");
		columns.add("Rrs Dttm");
		return columns;
	}

	@Override
	public ReRaterSettings createEmptyDbo()
	{
		return new ReRaterSettingsImpl();
	}

	@Override
	public void checkDuplicates(ReRaterSettings dbo)
	{
		checkDuplicateGM(dbo.getRrsName(), "rrs_name", dbo.getPk());
		checkDuplicateGM(dbo.getRrsBk(), "rrs_bk", dbo.getPk());
	}

	@Override
	public int create(ReRaterSettings reRaterSettings, int usrId)
	{
		return create(null, reRaterSettings, usrId);
	}

	public int create(Connection connection, ReRaterSettings reRaterSettings, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("rrs_id", "re_rater_settings");
		String bk = reRaterSettings.getDisplayField();

		String query = " insert into re_rater_settings values(?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, reRaterSettings.getRrsName(), reRaterSettings.getRrsTargetDir(), reRaterSettings.getRrsBixTargetDir(), reRaterSettings.getRrsFpsId(), reRaterSettings.getRrsReRaterJbtId(), reRaterSettings.getRrsDataloadJbtId(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(ReRaterSettings reRaterSettings, int usrId)
	{
		update(null, reRaterSettings, usrId);
	}

	public void update(Connection connection, ReRaterSettings reRaterSettings, int usrId)
	{
		String query = " update re_rater_settings set  rrs_name = ? , rrs_target_dir = ? , rrs_bix_target_dir = ? , rrs_fps_id = ? , rrs_re_rater_jbt_id = ? , rrs_dataload_jbt_id = ? , rrs_modified_usr_id = ? , rrs_modified_dttm = ?  where rrs_id = ?  ";
		Object[] paramValues = new Object[] { reRaterSettings.getRrsName(), reRaterSettings.getRrsTargetDir(), reRaterSettings.getRrsBixTargetDir(), reRaterSettings.getRrsFpsId(), reRaterSettings.getRrsReRaterJbtId(), reRaterSettings.getRrsDataloadJbtId(), usrId, new DateTime(), reRaterSettings.getRrsId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into re_rater_settings values(?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { reRaterSettings.getRrsId(), reRaterSettings.getRrsName(), reRaterSettings.getRrsTargetDir(), reRaterSettings.getRrsBixTargetDir(), reRaterSettings.getRrsFpsId(), reRaterSettings.getRrsReRaterJbtId(), reRaterSettings.getRrsDataloadJbtId(), reRaterSettings.getRrsBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<ReRaterSettings> fetchAll()
	{
		String query = " select * from re_rater_settings ";
		return RefDBHelper.getDB().fetchList(query, ReRaterSettingsImpl.class);
	}

	public List<ReRaterSettings> fetchAuditRowsByPk(int id)
	{
		String query = " select * from re_rater_settings where rrs_id=? order by rrs_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, ReRaterSettingsImpl.class);
	}

	public ReRaterSettings fetchByPk(int rrsId)
	{
		return fetchByPk(null, rrsId);
	}

	public ReRaterSettings fetchByPk(Connection connection, int rrsId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from re_rater_settings where rrs_id=? ", rrsId, ReRaterSettingsImpl.class);
	}

	public ReRaterSettings fetchByBk(String rrsBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from re_rater_settings where rrs_bk=? ", rrsBk, ReRaterSettingsImpl.class);
	}

	public ReRaterSettings fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from re_rater_settings where rrs_name=? ", displayField, ReRaterSettingsImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("rrs_name", "re_rater_settings");
	}

}