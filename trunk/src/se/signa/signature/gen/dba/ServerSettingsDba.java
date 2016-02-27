/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.ServerSettingsDbaImpl;
import se.signa.signature.dbo.ServerSettingsImpl;
import se.signa.signature.gen.dbo.ServerSettings;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class ServerSettingsDba extends SignatureDba<ServerSettings>
{
	private static ServerSettingsDbaImpl INSTANCE;

	public static ServerSettingsDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new ServerSettingsDbaImpl();
		return INSTANCE;
	}

	public ServerSettingsDba()
	{
		tableName = "server_settings";
		tablePrefix = "ses";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Ses Id");
		columns.add("Ses Code");
		columns.add("Ses Value");
		columns.add("Ses User Name");
		columns.add("Ses Dttm");
		return columns;
	}

	@Override
	public ServerSettings createEmptyDbo()
	{
		return new ServerSettingsImpl();
	}

	@Override
	public void checkDuplicates(ServerSettings dbo)
	{
		checkDuplicateGM(dbo.getSesCode(), "ses_code", dbo.getPk());
		checkDuplicateGM(dbo.getSesBk(), "ses_bk", dbo.getPk());
	}

	@Override
	public int create(ServerSettings serverSettings, int usrId)
	{
		return create(null, serverSettings, usrId);
	}

	public int create(Connection connection, ServerSettings serverSettings, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("ses_id", "server_settings");
		String bk = serverSettings.getDisplayField();

		String query = " insert into server_settings values(?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, serverSettings.getSesCode(), serverSettings.getSesValue(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(ServerSettings serverSettings, int usrId)
	{
		update(null, serverSettings, usrId);
	}

	public void update(Connection connection, ServerSettings serverSettings, int usrId)
	{
		String query = " update server_settings set  ses_code = ? , ses_value = ? , ses_modified_usr_id = ? , ses_modified_dttm = ?  where ses_id = ?  ";
		Object[] paramValues = new Object[] { serverSettings.getSesCode(), serverSettings.getSesValue(), usrId, new DateTime(), serverSettings.getSesId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into server_settings values(?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { serverSettings.getSesId(), serverSettings.getSesCode(), serverSettings.getSesValue(), serverSettings.getSesBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<ServerSettings> fetchAll()
	{
		String query = " select * from server_settings ";
		return RefDBHelper.getDB().fetchList(query, ServerSettingsImpl.class);
	}

	public List<ServerSettings> fetchAuditRowsByPk(int id)
	{
		String query = " select * from server_settings where ses_id=? order by ses_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, ServerSettingsImpl.class);
	}

	public ServerSettings fetchByPk(int sesId)
	{
		return fetchByPk(null, sesId);
	}

	public ServerSettings fetchByPk(Connection connection, int sesId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from server_settings where ses_id=? ", sesId, ServerSettingsImpl.class);
	}

	public ServerSettings fetchByBk(String sesBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from server_settings where ses_bk=? ", sesBk, ServerSettingsImpl.class);
	}

	public ServerSettings fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from server_settings where ses_code=? ", displayField, ServerSettingsImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("ses_code", "server_settings");
	}

}