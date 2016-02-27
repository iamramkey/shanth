/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.ReferenceFilePollerSettingsDbaImpl;
import se.signa.signature.dbo.ReferenceFilePollerSettingsImpl;
import se.signa.signature.gen.dbo.ReferenceFilePollerSettings;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class ReferenceFilePollerSettingsDba extends SignatureDba<ReferenceFilePollerSettings>
{
	private static ReferenceFilePollerSettingsDbaImpl INSTANCE;

	public static ReferenceFilePollerSettingsDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new ReferenceFilePollerSettingsDbaImpl();
		return INSTANCE;
	}

	public ReferenceFilePollerSettingsDba()
	{
		tableName = "reference_file_poller_settings";
		tablePrefix = "rps";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Rps Id");
		columns.add("Rps Name");
		columns.add("Rps Source Dir");
		columns.add("Rps Target Dir");
		columns.add("Rps Freq Secs");
		columns.add("Rps Poller Jbt Id");
		columns.add("Rps Ref Data Comparator Jbt Id");
		columns.add("Rps Stability Secs");
		columns.add("Rps Stability Retry");
		columns.add("Rps User Name");
		columns.add("Rps Dttm");
		return columns;
	}

	@Override
	public ReferenceFilePollerSettings createEmptyDbo()
	{
		return new ReferenceFilePollerSettingsImpl();
	}

	@Override
	public void checkDuplicates(ReferenceFilePollerSettings dbo)
	{
		checkDuplicateGM(dbo.getRpsName(), "rps_name", dbo.getPk());
		checkDuplicateGM(dbo.getRpsBk(), "rps_bk", dbo.getPk());
	}

	@Override
	public int create(ReferenceFilePollerSettings referenceFilePollerSettings, int usrId)
	{
		return create(null, referenceFilePollerSettings, usrId);
	}

	public int create(Connection connection, ReferenceFilePollerSettings referenceFilePollerSettings, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("rps_id", "reference_file_poller_settings");
		String bk = referenceFilePollerSettings.getDisplayField();

		String query = " insert into reference_file_poller_settings values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, referenceFilePollerSettings.getRpsName(), referenceFilePollerSettings.getRpsSourceDir(), referenceFilePollerSettings.getRpsTargetDir(), referenceFilePollerSettings.getRpsFreqSecs(), referenceFilePollerSettings.getRpsPollerJbtId(), referenceFilePollerSettings.getRpsRefDataComparatorJbtId(), referenceFilePollerSettings.getRpsStabilitySecs(),
				referenceFilePollerSettings.getRpsStabilityRetry(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(ReferenceFilePollerSettings referenceFilePollerSettings, int usrId)
	{
		update(null, referenceFilePollerSettings, usrId);
	}

	public void update(Connection connection, ReferenceFilePollerSettings referenceFilePollerSettings, int usrId)
	{
		String query = " update reference_file_poller_settings set  rps_name = ? , rps_source_dir = ? , rps_target_dir = ? , rps_freq_secs = ? , rps_poller_jbt_id = ? , rps_ref_data_comparator_jbt_id = ? , rps_stability_secs = ? , rps_stability_retry = ? , rps_modified_usr_id = ? , rps_modified_dttm = ?  where rps_id = ?  ";
		Object[] paramValues = new Object[] { referenceFilePollerSettings.getRpsName(), referenceFilePollerSettings.getRpsSourceDir(), referenceFilePollerSettings.getRpsTargetDir(), referenceFilePollerSettings.getRpsFreqSecs(), referenceFilePollerSettings.getRpsPollerJbtId(), referenceFilePollerSettings.getRpsRefDataComparatorJbtId(), referenceFilePollerSettings.getRpsStabilitySecs(),
				referenceFilePollerSettings.getRpsStabilityRetry(), usrId, new DateTime(), referenceFilePollerSettings.getRpsId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into reference_file_poller_settings values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { referenceFilePollerSettings.getRpsId(), referenceFilePollerSettings.getRpsName(), referenceFilePollerSettings.getRpsSourceDir(), referenceFilePollerSettings.getRpsTargetDir(), referenceFilePollerSettings.getRpsFreqSecs(), referenceFilePollerSettings.getRpsPollerJbtId(), referenceFilePollerSettings.getRpsRefDataComparatorJbtId(),
				referenceFilePollerSettings.getRpsStabilitySecs(), referenceFilePollerSettings.getRpsStabilityRetry(), referenceFilePollerSettings.getRpsBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<ReferenceFilePollerSettings> fetchAll()
	{
		String query = " select * from reference_file_poller_settings ";
		return RefDBHelper.getDB().fetchList(query, ReferenceFilePollerSettingsImpl.class);
	}

	public List<ReferenceFilePollerSettings> fetchAuditRowsByPk(int id)
	{
		String query = " select * from reference_file_poller_settings where rps_id=? order by rps_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, ReferenceFilePollerSettingsImpl.class);
	}

	public ReferenceFilePollerSettings fetchByPk(int rpsId)
	{
		return fetchByPk(null, rpsId);
	}

	public ReferenceFilePollerSettings fetchByPk(Connection connection, int rpsId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from reference_file_poller_settings where rps_id=? ", rpsId, ReferenceFilePollerSettingsImpl.class);
	}

	public ReferenceFilePollerSettings fetchByBk(String rpsBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from reference_file_poller_settings where rps_bk=? ", rpsBk, ReferenceFilePollerSettingsImpl.class);
	}

	public ReferenceFilePollerSettings fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from reference_file_poller_settings where rps_name=? ", displayField, ReferenceFilePollerSettingsImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("rps_name", "reference_file_poller_settings");
	}

}