/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.SfsSettingsDbaImpl;
import se.signa.signature.dbo.SfsSettingsImpl;
import se.signa.signature.gen.dbo.SfsSettings;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class SfsSettingsDba extends SignatureDba<SfsSettings>
{
	private static SfsSettingsDbaImpl INSTANCE;

	public static SfsSettingsDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new SfsSettingsDbaImpl();
		return INSTANCE;
	}

	public SfsSettingsDba()
	{
		tableName = "sfs_settings";
		tablePrefix = "sfs";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Sfs Id");
		columns.add("Sfs Name");
		columns.add("Sfs Server Address");
		columns.add("Sfs Port");
		columns.add("Sfs Username");
		columns.add("Sfs Password");
		columns.add("Sfs Directory");
		columns.add("Jbt Id");
		columns.add("Sfs User Name");
		columns.add("Sfs Dttm");
		return columns;
	}

	@Override
	public SfsSettings createEmptyDbo()
	{
		return new SfsSettingsImpl();
	}

	@Override
	public void checkDuplicates(SfsSettings dbo)
	{
		checkDuplicateGM(dbo.getSfsName(), "sfs_name", dbo.getPk());
		checkDuplicateGM(dbo.getSfsBk(), "sfs_bk", dbo.getPk());
	}

	@Override
	public int create(SfsSettings sfsSettings, int usrId)
	{
		return create(null, sfsSettings, usrId);
	}

	public int create(Connection connection, SfsSettings sfsSettings, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("sfs_id", "sfs_settings");
		String bk = sfsSettings.getDisplayField();

		String query = " insert into sfs_settings values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, sfsSettings.getSfsName(), sfsSettings.getSfsServerAddress(), sfsSettings.getSfsPort(), sfsSettings.getSfsUsername(), sfsSettings.getSfsPassword(), sfsSettings.getSfsDirectory(), sfsSettings.getJbtId(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(SfsSettings sfsSettings, int usrId)
	{
		update(null, sfsSettings, usrId);
	}

	public void update(Connection connection, SfsSettings sfsSettings, int usrId)
	{
		String query = " update sfs_settings set  sfs_name = ? , sfs_server_address = ? , sfs_port = ? , sfs_username = ? , sfs_password = ? , sfs_directory = ? , jbt_id = ? , sfs_modified_usr_id = ? , sfs_modified_dttm = ?  where sfs_id = ?  ";
		Object[] paramValues = new Object[] { sfsSettings.getSfsName(), sfsSettings.getSfsServerAddress(), sfsSettings.getSfsPort(), sfsSettings.getSfsUsername(), sfsSettings.getSfsPassword(), sfsSettings.getSfsDirectory(), sfsSettings.getJbtId(), usrId, new DateTime(), sfsSettings.getSfsId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into sfs_settings values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { sfsSettings.getSfsId(), sfsSettings.getSfsName(), sfsSettings.getSfsServerAddress(), sfsSettings.getSfsPort(), sfsSettings.getSfsUsername(), sfsSettings.getSfsPassword(), sfsSettings.getSfsDirectory(), sfsSettings.getJbtId(), sfsSettings.getSfsBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<SfsSettings> fetchAll()
	{
		String query = " select * from sfs_settings ";
		return RefDBHelper.getDB().fetchList(query, SfsSettingsImpl.class);
	}

	public List<SfsSettings> fetchAuditRowsByPk(int id)
	{
		String query = " select * from sfs_settings where sfs_id=? order by sfs_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, SfsSettingsImpl.class);
	}

	public SfsSettings fetchByPk(int sfsId)
	{
		return fetchByPk(null, sfsId);
	}

	public SfsSettings fetchByPk(Connection connection, int sfsId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from sfs_settings where sfs_id=? ", sfsId, SfsSettingsImpl.class);
	}

	public SfsSettings fetchByBk(String sfsBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from sfs_settings where sfs_bk=? ", sfsBk, SfsSettingsImpl.class);
	}

	public SfsSettings fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from sfs_settings where sfs_name=? ", displayField, SfsSettingsImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("sfs_name", "sfs_settings");
	}

}