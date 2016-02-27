/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.SmsSettingsDbaImpl;
import se.signa.signature.dbo.SmsSettingsImpl;
import se.signa.signature.gen.dbo.SmsSettings;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class SmsSettingsDba extends SignatureDba<SmsSettings>
{
	private static SmsSettingsDbaImpl INSTANCE;

	public static SmsSettingsDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new SmsSettingsDbaImpl();
		return INSTANCE;
	}

	public SmsSettingsDba()
	{
		tableName = "sms_settings";
		tablePrefix = "sms";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Sms Id");
		columns.add("Sms Name");
		columns.add("Sms Smtp Host");
		columns.add("Sms Smtp Port");
		columns.add("Sms User Name");
		columns.add("Sms Password");
		columns.add("Sms User Name");
		columns.add("Sms Dttm");
		return columns;
	}

	@Override
	public SmsSettings createEmptyDbo()
	{
		return new SmsSettingsImpl();
	}

	@Override
	public void checkDuplicates(SmsSettings dbo)
	{
		checkDuplicateGM(dbo.getSmsName(), "sms_name", dbo.getPk());
		checkDuplicateGM(dbo.getSmsBk(), "sms_bk", dbo.getPk());
	}

	@Override
	public int create(SmsSettings smsSettings, int usrId)
	{
		return create(null, smsSettings, usrId);
	}

	public int create(Connection connection, SmsSettings smsSettings, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("sms_id", "sms_settings");
		String bk = smsSettings.getDisplayField();

		String query = " insert into sms_settings values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, smsSettings.getSmsName(), smsSettings.getSmsSmtpHost(), smsSettings.getSmsSmtpPort(), smsSettings.getSmsUserName(), smsSettings.getSmsPassword(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(SmsSettings smsSettings, int usrId)
	{
		update(null, smsSettings, usrId);
	}

	public void update(Connection connection, SmsSettings smsSettings, int usrId)
	{
		String query = " update sms_settings set  sms_name = ? , sms_smtp_host = ? , sms_smtp_port = ? , sms_user_name = ? , sms_password = ? , sms_modified_usr_id = ? , sms_modified_dttm = ?  where sms_id = ?  ";
		Object[] paramValues = new Object[] { smsSettings.getSmsName(), smsSettings.getSmsSmtpHost(), smsSettings.getSmsSmtpPort(), smsSettings.getSmsUserName(), smsSettings.getSmsPassword(), usrId, new DateTime(), smsSettings.getSmsId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into sms_settings values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { smsSettings.getSmsId(), smsSettings.getSmsName(), smsSettings.getSmsSmtpHost(), smsSettings.getSmsSmtpPort(), smsSettings.getSmsUserName(), smsSettings.getSmsPassword(), smsSettings.getSmsBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<SmsSettings> fetchAll()
	{
		String query = " select * from sms_settings ";
		return RefDBHelper.getDB().fetchList(query, SmsSettingsImpl.class);
	}

	public List<SmsSettings> fetchAuditRowsByPk(int id)
	{
		String query = " select * from sms_settings where sms_id=? order by sms_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, SmsSettingsImpl.class);
	}

	public SmsSettings fetchByPk(int smsId)
	{
		return fetchByPk(null, smsId);
	}

	public SmsSettings fetchByPk(Connection connection, int smsId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from sms_settings where sms_id=? ", smsId, SmsSettingsImpl.class);
	}

	public SmsSettings fetchByBk(String smsBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from sms_settings where sms_bk=? ", smsBk, SmsSettingsImpl.class);
	}

	public SmsSettings fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from sms_settings where sms_name=? ", displayField, SmsSettingsImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("sms_name", "sms_settings");
	}

}