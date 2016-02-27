/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.HouseKeepingSettingsDbaImpl;
import se.signa.signature.dbo.HouseKeepingSettingsImpl;
import se.signa.signature.gen.dbo.HouseKeepingSettings;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class HouseKeepingSettingsDba extends SignatureDba<HouseKeepingSettings>
{
	private static HouseKeepingSettingsDbaImpl INSTANCE;

	public static HouseKeepingSettingsDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new HouseKeepingSettingsDbaImpl();
		return INSTANCE;
	}

	public HouseKeepingSettingsDba()
	{
		tableName = "house_keeping_settings";
		tablePrefix = "hks";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Hks Id");
		columns.add("Hks Name");
		columns.add("Hks Freq Secs");
		columns.add("Hks Jbt Id");
		columns.add("Hks User Name");
		columns.add("Hks Dttm");
		return columns;
	}

	@Override
	public HouseKeepingSettings createEmptyDbo()
	{
		return new HouseKeepingSettingsImpl();
	}

	@Override
	public void checkDuplicates(HouseKeepingSettings dbo)
	{
		checkDuplicateGM(dbo.getHksName(), "hks_name", dbo.getPk());
		checkDuplicateGM(dbo.getHksBk(), "hks_bk", dbo.getPk());
	}

	@Override
	public int create(HouseKeepingSettings houseKeepingSettings, int usrId)
	{
		return create(null, houseKeepingSettings, usrId);
	}

	public int create(Connection connection, HouseKeepingSettings houseKeepingSettings, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("hks_id", "house_keeping_settings");
		String bk = houseKeepingSettings.getDisplayField();

		String query = " insert into house_keeping_settings values(?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, houseKeepingSettings.getHksName(), houseKeepingSettings.getHksFreqSecs(), houseKeepingSettings.getHksJbtId(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(HouseKeepingSettings houseKeepingSettings, int usrId)
	{
		update(null, houseKeepingSettings, usrId);
	}

	public void update(Connection connection, HouseKeepingSettings houseKeepingSettings, int usrId)
	{
		String query = " update house_keeping_settings set  hks_name = ? , hks_freq_secs = ? , hks_jbt_id = ? , hks_modified_usr_id = ? , hks_modified_dttm = ?  where hks_id = ?  ";
		Object[] paramValues = new Object[] { houseKeepingSettings.getHksName(), houseKeepingSettings.getHksFreqSecs(), houseKeepingSettings.getHksJbtId(), usrId, new DateTime(), houseKeepingSettings.getHksId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into house_keeping_settings values(?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { houseKeepingSettings.getHksId(), houseKeepingSettings.getHksName(), houseKeepingSettings.getHksFreqSecs(), houseKeepingSettings.getHksJbtId(), houseKeepingSettings.getHksBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<HouseKeepingSettings> fetchAll()
	{
		String query = " select * from house_keeping_settings ";
		return RefDBHelper.getDB().fetchList(query, HouseKeepingSettingsImpl.class);
	}

	public List<HouseKeepingSettings> fetchAuditRowsByPk(int id)
	{
		String query = " select * from house_keeping_settings where hks_id=? order by hks_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, HouseKeepingSettingsImpl.class);
	}

	public HouseKeepingSettings fetchByPk(int hksId)
	{
		return fetchByPk(null, hksId);
	}

	public HouseKeepingSettings fetchByPk(Connection connection, int hksId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from house_keeping_settings where hks_id=? ", hksId, HouseKeepingSettingsImpl.class);
	}

	public HouseKeepingSettings fetchByBk(String hksBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from house_keeping_settings where hks_bk=? ", hksBk, HouseKeepingSettingsImpl.class);
	}

	public HouseKeepingSettings fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from house_keeping_settings where hks_name=? ", displayField, HouseKeepingSettingsImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("hks_name", "house_keeping_settings");
	}

}