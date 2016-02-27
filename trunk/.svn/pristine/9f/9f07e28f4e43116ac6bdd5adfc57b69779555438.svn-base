/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.UserPasswordSettingsDbaImpl;
import se.signa.signature.dbo.UserPasswordSettingsImpl;
import se.signa.signature.gen.dbo.UserPasswordSettings;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class UserPasswordSettingsDba extends SignatureDba<UserPasswordSettings>
{
	private static UserPasswordSettingsDbaImpl INSTANCE;

	public static UserPasswordSettingsDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new UserPasswordSettingsDbaImpl();
		return INSTANCE;
	}

	public UserPasswordSettingsDba()
	{
		tableName = "user_password_settings";
		tablePrefix = "ups";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Ups Id");
		columns.add("Ups Name");
		columns.add("Ups Max Attempts");
		columns.add("Ups Max Inactive Days");
		columns.add("Ups Min Length");
		columns.add("Ups Reset Interval");
		columns.add("Ups Reset Warning Interval");
		columns.add("Ups User Name");
		columns.add("Ups Dttm");
		return columns;
	}

	@Override
	public UserPasswordSettings createEmptyDbo()
	{
		return new UserPasswordSettingsImpl();
	}

	@Override
	public void checkDuplicates(UserPasswordSettings dbo)
	{
		checkDuplicateGM(dbo.getUpsName(), "ups_name", dbo.getPk());
		checkDuplicateGM(dbo.getUpsBk(), "ups_bk", dbo.getPk());
	}

	@Override
	public int create(UserPasswordSettings userPasswordSettings, int usrId)
	{
		return create(null, userPasswordSettings, usrId);
	}

	public int create(Connection connection, UserPasswordSettings userPasswordSettings, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("ups_id", "user_password_settings");
		String bk = userPasswordSettings.getDisplayField();

		String query = " insert into user_password_settings values(?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, userPasswordSettings.getUpsName(), userPasswordSettings.getUpsMaxAttempts(), userPasswordSettings.getUpsMaxInactiveDays(), userPasswordSettings.getUpsMinLength(), userPasswordSettings.getUpsResetInterval(), userPasswordSettings.getUpsResetWarningInterval(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(UserPasswordSettings userPasswordSettings, int usrId)
	{
		update(null, userPasswordSettings, usrId);
	}

	public void update(Connection connection, UserPasswordSettings userPasswordSettings, int usrId)
	{
		String query = " update user_password_settings set  ups_name = ? , ups_max_attempts = ? , ups_max_inactive_days = ? , ups_min_length = ? , ups_reset_interval = ? , ups_reset_warning_interval = ? , ups_modified_usr_id = ? , ups_modified_dttm = ?  where ups_id = ?  ";
		Object[] paramValues = new Object[] { userPasswordSettings.getUpsName(), userPasswordSettings.getUpsMaxAttempts(), userPasswordSettings.getUpsMaxInactiveDays(), userPasswordSettings.getUpsMinLength(), userPasswordSettings.getUpsResetInterval(), userPasswordSettings.getUpsResetWarningInterval(), usrId, new DateTime(), userPasswordSettings.getUpsId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into user_password_settings values(?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { userPasswordSettings.getUpsId(), userPasswordSettings.getUpsName(), userPasswordSettings.getUpsMaxAttempts(), userPasswordSettings.getUpsMaxInactiveDays(), userPasswordSettings.getUpsMinLength(), userPasswordSettings.getUpsResetInterval(), userPasswordSettings.getUpsResetWarningInterval(), userPasswordSettings.getUpsBk(), usrId, usrId,
				new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<UserPasswordSettings> fetchAll()
	{
		String query = " select * from user_password_settings ";
		return RefDBHelper.getDB().fetchList(query, UserPasswordSettingsImpl.class);
	}

	public List<UserPasswordSettings> fetchAuditRowsByPk(int id)
	{
		String query = " select * from user_password_settings where ups_id=? order by ups_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, UserPasswordSettingsImpl.class);
	}

	public UserPasswordSettings fetchByPk(int upsId)
	{
		return fetchByPk(null, upsId);
	}

	public UserPasswordSettings fetchByPk(Connection connection, int upsId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from user_password_settings where ups_id=? ", upsId, UserPasswordSettingsImpl.class);
	}

	public UserPasswordSettings fetchByBk(String upsBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from user_password_settings where ups_bk=? ", upsBk, UserPasswordSettingsImpl.class);
	}

	public UserPasswordSettings fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from user_password_settings where ups_name=? ", displayField, UserPasswordSettingsImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("ups_name", "user_password_settings");
	}

}