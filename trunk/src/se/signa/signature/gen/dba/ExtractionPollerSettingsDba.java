/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.ExtractionPollerSettingsDbaImpl;
import se.signa.signature.dbo.ExtractionPollerSettingsImpl;
import se.signa.signature.gen.dbo.ExtractionPollerSettings;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class ExtractionPollerSettingsDba extends SignatureDba<ExtractionPollerSettings>
{
	private static ExtractionPollerSettingsDbaImpl INSTANCE;

	public static ExtractionPollerSettingsDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new ExtractionPollerSettingsDbaImpl();
		return INSTANCE;
	}

	public ExtractionPollerSettingsDba()
	{
		tableName = "extraction_poller_settings";
		tablePrefix = "exs";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Exs Id");
		columns.add("Exs Name");
		columns.add("Exs Delay Mins");
		columns.add("Exs Frequency Secs");
		columns.add("Exs Poller Jbt Id");
		columns.add("Exs User Name");
		columns.add("Exs Dttm");
		return columns;
	}

	@Override
	public ExtractionPollerSettings createEmptyDbo()
	{
		return new ExtractionPollerSettingsImpl();
	}

	@Override
	public void checkDuplicates(ExtractionPollerSettings dbo)
	{
		checkDuplicateGM(dbo.getExsName(), "exs_name", dbo.getPk());
		checkDuplicateGM(dbo.getExsBk(), "exs_bk", dbo.getPk());
	}

	@Override
	public int create(ExtractionPollerSettings extractionPollerSettings, int usrId)
	{
		return create(null, extractionPollerSettings, usrId);
	}

	public int create(Connection connection, ExtractionPollerSettings extractionPollerSettings, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("exs_id", "extraction_poller_settings");
		String bk = extractionPollerSettings.getDisplayField();

		String query = " insert into extraction_poller_settings values(?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, extractionPollerSettings.getExsName(), extractionPollerSettings.getExsDelayMins(), extractionPollerSettings.getExsFrequencySecs(), extractionPollerSettings.getExsPollerJbtId(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(ExtractionPollerSettings extractionPollerSettings, int usrId)
	{
		update(null, extractionPollerSettings, usrId);
	}

	public void update(Connection connection, ExtractionPollerSettings extractionPollerSettings, int usrId)
	{
		String query = " update extraction_poller_settings set  exs_name = ? , exs_delay_mins = ? , exs_frequency_secs = ? , exs_poller_jbt_id = ? , exs_modified_usr_id = ? , exs_modified_dttm = ?  where exs_id = ?  ";
		Object[] paramValues = new Object[] { extractionPollerSettings.getExsName(), extractionPollerSettings.getExsDelayMins(), extractionPollerSettings.getExsFrequencySecs(), extractionPollerSettings.getExsPollerJbtId(), usrId, new DateTime(), extractionPollerSettings.getExsId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into extraction_poller_settings values(?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { extractionPollerSettings.getExsId(), extractionPollerSettings.getExsName(), extractionPollerSettings.getExsDelayMins(), extractionPollerSettings.getExsFrequencySecs(), extractionPollerSettings.getExsPollerJbtId(), extractionPollerSettings.getExsBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<ExtractionPollerSettings> fetchAll()
	{
		String query = " select * from extraction_poller_settings ";
		return RefDBHelper.getDB().fetchList(query, ExtractionPollerSettingsImpl.class);
	}

	public List<ExtractionPollerSettings> fetchAuditRowsByPk(int id)
	{
		String query = " select * from extraction_poller_settings where exs_id=? order by exs_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, ExtractionPollerSettingsImpl.class);
	}

	public ExtractionPollerSettings fetchByPk(int exsId)
	{
		return fetchByPk(null, exsId);
	}

	public ExtractionPollerSettings fetchByPk(Connection connection, int exsId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from extraction_poller_settings where exs_id=? ", exsId, ExtractionPollerSettingsImpl.class);
	}

	public ExtractionPollerSettings fetchByBk(String exsBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from extraction_poller_settings where exs_bk=? ", exsBk, ExtractionPollerSettingsImpl.class);
	}

	public ExtractionPollerSettings fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from extraction_poller_settings where exs_name=? ", displayField, ExtractionPollerSettingsImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("exs_name", "extraction_poller_settings");
	}

}