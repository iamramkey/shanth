/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.RefDataComparatorSettingsDbaImpl;
import se.signa.signature.dbo.RefDataComparatorSettingsImpl;
import se.signa.signature.gen.dbo.RefDataComparatorSettings;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class RefDataComparatorSettingsDba extends SignatureDba<RefDataComparatorSettings>
{
	private static RefDataComparatorSettingsDbaImpl INSTANCE;

	public static RefDataComparatorSettingsDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new RefDataComparatorSettingsDbaImpl();
		return INSTANCE;
	}

	public RefDataComparatorSettingsDba()
	{
		tableName = "ref_data_comparator_settings";
		tablePrefix = "rdc";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Rdc Id");
		columns.add("Rdc Name");
		columns.add("Jbt Id");
		columns.add("Rdc Re Rater Jbt Id");
		columns.add("Fas Id");
		columns.add("Fps Id");
		columns.add("Rdc User Name");
		columns.add("Rdc Dttm");
		return columns;
	}

	@Override
	public RefDataComparatorSettings createEmptyDbo()
	{
		return new RefDataComparatorSettingsImpl();
	}

	@Override
	public void checkDuplicates(RefDataComparatorSettings dbo)
	{
		checkDuplicateGM(dbo.getRdcName(), "rdc_name", dbo.getPk());
		checkDuplicateGM(dbo.getRdcBk(), "rdc_bk", dbo.getPk());
	}

	@Override
	public int create(RefDataComparatorSettings refDataComparatorSettings, int usrId)
	{
		return create(null, refDataComparatorSettings, usrId);
	}

	public int create(Connection connection, RefDataComparatorSettings refDataComparatorSettings, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("rdc_id", "ref_data_comparator_settings");
		String bk = refDataComparatorSettings.getDisplayField();

		String query = " insert into ref_data_comparator_settings values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, refDataComparatorSettings.getRdcName(), refDataComparatorSettings.getJbtId(), refDataComparatorSettings.getRdcReRaterJbtId(), refDataComparatorSettings.getFasId(), refDataComparatorSettings.getFpsId(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(RefDataComparatorSettings refDataComparatorSettings, int usrId)
	{
		update(null, refDataComparatorSettings, usrId);
	}

	public void update(Connection connection, RefDataComparatorSettings refDataComparatorSettings, int usrId)
	{
		String query = " update ref_data_comparator_settings set  rdc_name = ? , jbt_id = ? , rdc_re_rater_jbt_id = ? , fas_id = ? , fps_id = ? , rdc_modified_usr_id = ? , rdc_modified_dttm = ?  where rdc_id = ?  ";
		Object[] paramValues = new Object[] { refDataComparatorSettings.getRdcName(), refDataComparatorSettings.getJbtId(), refDataComparatorSettings.getRdcReRaterJbtId(), refDataComparatorSettings.getFasId(), refDataComparatorSettings.getFpsId(), usrId, new DateTime(), refDataComparatorSettings.getRdcId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into ref_data_comparator_settings values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { refDataComparatorSettings.getRdcId(), refDataComparatorSettings.getRdcName(), refDataComparatorSettings.getJbtId(), refDataComparatorSettings.getRdcReRaterJbtId(), refDataComparatorSettings.getFasId(), refDataComparatorSettings.getFpsId(), refDataComparatorSettings.getRdcBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<RefDataComparatorSettings> fetchAll()
	{
		String query = " select * from ref_data_comparator_settings ";
		return RefDBHelper.getDB().fetchList(query, RefDataComparatorSettingsImpl.class);
	}

	public List<RefDataComparatorSettings> fetchAuditRowsByPk(int id)
	{
		String query = " select * from ref_data_comparator_settings where rdc_id=? order by rdc_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, RefDataComparatorSettingsImpl.class);
	}

	public RefDataComparatorSettings fetchByPk(int rdcId)
	{
		return fetchByPk(null, rdcId);
	}

	public RefDataComparatorSettings fetchByPk(Connection connection, int rdcId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from ref_data_comparator_settings where rdc_id=? ", rdcId, RefDataComparatorSettingsImpl.class);
	}

	public RefDataComparatorSettings fetchByBk(String rdcBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from ref_data_comparator_settings where rdc_bk=? ", rdcBk, RefDataComparatorSettingsImpl.class);
	}

	public RefDataComparatorSettings fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from ref_data_comparator_settings where rdc_name=? ", displayField, RefDataComparatorSettingsImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("rdc_name", "ref_data_comparator_settings");
	}

}