/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.BixExporterSettingsDbaImpl;
import se.signa.signature.dbo.BixExporterSettingsImpl;
import se.signa.signature.gen.dbo.BixExporterSettings;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class BixExporterSettingsDba extends SignatureDba<BixExporterSettings>
{
	private static BixExporterSettingsDbaImpl INSTANCE;

	public static BixExporterSettingsDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new BixExporterSettingsDbaImpl();
		return INSTANCE;
	}

	public BixExporterSettingsDba()
	{
		tableName = "bix_exporter_settings";
		tablePrefix = "bes";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Bes Id");
		columns.add("Bes Name");
		columns.add("Bes Bix Exporter Jbt Id");
		columns.add("Bes Compressed Dir");
		columns.add("Bes User Name");
		columns.add("Bes Dttm");
		return columns;
	}

	@Override
	public BixExporterSettings createEmptyDbo()
	{
		return new BixExporterSettingsImpl();
	}

	@Override
	public void checkDuplicates(BixExporterSettings dbo)
	{
		checkDuplicateGM(dbo.getBesName(), "bes_name", dbo.getPk());
		checkDuplicateGM(dbo.getBesBk(), "bes_bk", dbo.getPk());
	}

	@Override
	public int create(BixExporterSettings bixExporterSettings, int usrId)
	{
		return create(null, bixExporterSettings, usrId);
	}

	public int create(Connection connection, BixExporterSettings bixExporterSettings, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("bes_id", "bix_exporter_settings");
		String bk = bixExporterSettings.getDisplayField();

		String query = " insert into bix_exporter_settings values(?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, bixExporterSettings.getBesName(), bixExporterSettings.getBesBixExporterJbtId(), bixExporterSettings.getBesCompressedDir(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(BixExporterSettings bixExporterSettings, int usrId)
	{
		update(null, bixExporterSettings, usrId);
	}

	public void update(Connection connection, BixExporterSettings bixExporterSettings, int usrId)
	{
		String query = " update bix_exporter_settings set  bes_name = ? , bes_bix_exporter_jbt_id = ? , bes_compressed_dir = ? , bes_modified_usr_id = ? , bes_modified_dttm = ?  where bes_id = ?  ";
		Object[] paramValues = new Object[] { bixExporterSettings.getBesName(), bixExporterSettings.getBesBixExporterJbtId(), bixExporterSettings.getBesCompressedDir(), usrId, new DateTime(), bixExporterSettings.getBesId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into bix_exporter_settings values(?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { bixExporterSettings.getBesId(), bixExporterSettings.getBesName(), bixExporterSettings.getBesBixExporterJbtId(), bixExporterSettings.getBesCompressedDir(), bixExporterSettings.getBesBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<BixExporterSettings> fetchAll()
	{
		String query = " select * from bix_exporter_settings ";
		return RefDBHelper.getDB().fetchList(query, BixExporterSettingsImpl.class);
	}

	public List<BixExporterSettings> fetchAuditRowsByPk(int id)
	{
		String query = " select * from bix_exporter_settings where bes_id=? order by bes_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, BixExporterSettingsImpl.class);
	}

	public BixExporterSettings fetchByPk(int besId)
	{
		return fetchByPk(null, besId);
	}

	public BixExporterSettings fetchByPk(Connection connection, int besId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from bix_exporter_settings where bes_id=? ", besId, BixExporterSettingsImpl.class);
	}

	public BixExporterSettings fetchByBk(String besBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from bix_exporter_settings where bes_bk=? ", besBk, BixExporterSettingsImpl.class);
	}

	public BixExporterSettings fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from bix_exporter_settings where bes_name=? ", displayField, BixExporterSettingsImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("bes_name", "bix_exporter_settings");
	}

}