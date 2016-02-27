/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.EmailSettingsDbaImpl;
import se.signa.signature.dbo.EmailSettingsImpl;
import se.signa.signature.gen.dbo.EmailSettings;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class EmailSettingsDba extends SignatureDba<EmailSettings>
{
	private static EmailSettingsDbaImpl INSTANCE;

	public static EmailSettingsDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new EmailSettingsDbaImpl();
		return INSTANCE;
	}

	public EmailSettingsDba()
	{
		tableName = "email_settings";
		tablePrefix = "ems";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Ems Id");
		columns.add("Ems Smtp Auth");
		columns.add("Ems Smtp Host");
		columns.add("Ems Smtp Port");
		columns.add("Ems Smtp Starttls Enable");
		columns.add("Ems User Name");
		columns.add("Ems Password");
		columns.add("Ems From Email");
		columns.add("Ems User Name");
		columns.add("Ems Dttm");
		return columns;
	}

	@Override
	public EmailSettings createEmptyDbo()
	{
		return new EmailSettingsImpl();
	}

	@Override
	public void checkDuplicates(EmailSettings dbo)
	{
		checkDuplicateGM(dbo.getEmsUserName(), "ems_user_name", dbo.getPk());
		checkDuplicateGM(dbo.getEmsBk(), "ems_bk", dbo.getPk());
	}

	@Override
	public int create(EmailSettings emailSettings, int usrId)
	{
		return create(null, emailSettings, usrId);
	}

	public int create(Connection connection, EmailSettings emailSettings, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("ems_id", "email_settings");
		String bk = emailSettings.getDisplayField();

		String query = " insert into email_settings values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, emailSettings.getEmsSmtpAuth(), emailSettings.getEmsSmtpHost(), emailSettings.getEmsSmtpPort(), emailSettings.getEmsSmtpStarttlsEnable(), emailSettings.getEmsUserName(), emailSettings.getEmsPassword(), emailSettings.getEmsFromEmail(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(EmailSettings emailSettings, int usrId)
	{
		update(null, emailSettings, usrId);
	}

	public void update(Connection connection, EmailSettings emailSettings, int usrId)
	{
		String query = " update email_settings set  ems_smtp_auth = ? , ems_smtp_host = ? , ems_smtp_port = ? , ems_smtp_starttls_enable = ? , ems_user_name = ? , ems_password = ? , ems_from_email = ? , ems_modified_usr_id = ? , ems_modified_dttm = ?  where ems_id = ?  ";
		Object[] paramValues = new Object[] { emailSettings.getEmsSmtpAuth(), emailSettings.getEmsSmtpHost(), emailSettings.getEmsSmtpPort(), emailSettings.getEmsSmtpStarttlsEnable(), emailSettings.getEmsUserName(), emailSettings.getEmsPassword(), emailSettings.getEmsFromEmail(), usrId, new DateTime(), emailSettings.getEmsId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into email_settings values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { emailSettings.getEmsId(), emailSettings.getEmsSmtpAuth(), emailSettings.getEmsSmtpHost(), emailSettings.getEmsSmtpPort(), emailSettings.getEmsSmtpStarttlsEnable(), emailSettings.getEmsUserName(), emailSettings.getEmsPassword(), emailSettings.getEmsFromEmail(), emailSettings.getEmsBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<EmailSettings> fetchAll()
	{
		String query = " select * from email_settings ";
		return RefDBHelper.getDB().fetchList(query, EmailSettingsImpl.class);
	}

	public List<EmailSettings> fetchAuditRowsByPk(int id)
	{
		String query = " select * from email_settings where ems_id=? order by ems_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, EmailSettingsImpl.class);
	}

	public EmailSettings fetchByPk(int emsId)
	{
		return fetchByPk(null, emsId);
	}

	public EmailSettings fetchByPk(Connection connection, int emsId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from email_settings where ems_id=? ", emsId, EmailSettingsImpl.class);
	}

	public EmailSettings fetchByBk(String emsBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from email_settings where ems_bk=? ", emsBk, EmailSettingsImpl.class);
	}

	public EmailSettings fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from email_settings where ems_user_name=? ", displayField, EmailSettingsImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("ems_user_name", "email_settings");
	}

}