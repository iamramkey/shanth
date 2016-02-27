/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.FtpSettingsDbaImpl;
import se.signa.signature.dbo.FtpSettingsImpl;
import se.signa.signature.gen.dbo.FtpSettings;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class FtpSettingsDba extends SignatureDba<FtpSettings>
{
	private static FtpSettingsDbaImpl INSTANCE;

	public static FtpSettingsDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new FtpSettingsDbaImpl();
		return INSTANCE;
	}

	public FtpSettingsDba()
	{
		tableName = "ftp_settings";
		tablePrefix = "ftp";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Ftp Id");
		columns.add("Ftp Server Address");
		columns.add("Ftp Username");
		columns.add("Ftp Password");
		columns.add("Ftp Directory");
		columns.add("Ftp User Name");
		columns.add("Ftp Dttm");
		return columns;
	}

	@Override
	public FtpSettings createEmptyDbo()
	{
		return new FtpSettingsImpl();
	}

	@Override
	public void checkDuplicates(FtpSettings dbo)
	{
		checkDuplicateGM(dbo.getFtpUsername(), "ftp_username", dbo.getPk());
		checkDuplicateGM(dbo.getFtpBk(), "ftp_bk", dbo.getPk());
	}

	@Override
	public int create(FtpSettings ftpSettings, int usrId)
	{
		return create(null, ftpSettings, usrId);
	}

	public int create(Connection connection, FtpSettings ftpSettings, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("ftp_id", "ftp_settings");
		String bk = ftpSettings.getDisplayField();

		String query = " insert into ftp_settings values(?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, ftpSettings.getFtpServerAddress(), ftpSettings.getFtpUsername(), ftpSettings.getFtpPassword(), ftpSettings.getFtpDirectory(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(FtpSettings ftpSettings, int usrId)
	{
		update(null, ftpSettings, usrId);
	}

	public void update(Connection connection, FtpSettings ftpSettings, int usrId)
	{
		String query = " update ftp_settings set  ftp_server_address = ? , ftp_username = ? , ftp_password = ? , ftp_directory = ? , ftp_modified_usr_id = ? , ftp_modified_dttm = ?  where ftp_id = ?  ";
		Object[] paramValues = new Object[] { ftpSettings.getFtpServerAddress(), ftpSettings.getFtpUsername(), ftpSettings.getFtpPassword(), ftpSettings.getFtpDirectory(), usrId, new DateTime(), ftpSettings.getFtpId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into ftp_settings values(?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { ftpSettings.getFtpId(), ftpSettings.getFtpServerAddress(), ftpSettings.getFtpUsername(), ftpSettings.getFtpPassword(), ftpSettings.getFtpDirectory(), ftpSettings.getFtpBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<FtpSettings> fetchAll()
	{
		String query = " select * from ftp_settings ";
		return RefDBHelper.getDB().fetchList(query, FtpSettingsImpl.class);
	}

	public List<FtpSettings> fetchAuditRowsByPk(int id)
	{
		String query = " select * from ftp_settings where ftp_id=? order by ftp_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, FtpSettingsImpl.class);
	}

	public FtpSettings fetchByPk(int ftpId)
	{
		return fetchByPk(null, ftpId);
	}

	public FtpSettings fetchByPk(Connection connection, int ftpId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from ftp_settings where ftp_id=? ", ftpId, FtpSettingsImpl.class);
	}

	public FtpSettings fetchByBk(String ftpBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from ftp_settings where ftp_bk=? ", ftpBk, FtpSettingsImpl.class);
	}

	public FtpSettings fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from ftp_settings where ftp_username=? ", displayField, FtpSettingsImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("ftp_username", "ftp_settings");
	}

}