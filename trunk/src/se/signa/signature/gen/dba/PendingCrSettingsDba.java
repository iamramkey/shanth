/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.PendingCrSettingsDbaImpl;
import se.signa.signature.dbo.PendingCrSettingsImpl;
import se.signa.signature.gen.dbo.PendingCrSettings;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class PendingCrSettingsDba extends SignatureDba<PendingCrSettings>
{
	private static PendingCrSettingsDbaImpl INSTANCE;

	public static PendingCrSettingsDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new PendingCrSettingsDbaImpl();
		return INSTANCE;
	}

	public PendingCrSettingsDba()
	{
		tableName = "pending_cr_settings";
		tablePrefix = "pcp";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Pcp Id");
		columns.add("Pcp Name");
		columns.add("Pcp Batch Size");
		columns.add("Pcp Freq Secs");
		columns.add("Pcp Poller Jbt Id");
		columns.add("Pcp Correlator Jbt Id");
		columns.add("Pcp User Name");
		columns.add("Pcp Dttm");
		return columns;
	}

	@Override
	public PendingCrSettings createEmptyDbo()
	{
		return new PendingCrSettingsImpl();
	}

	@Override
	public void checkDuplicates(PendingCrSettings dbo)
	{
		checkDuplicateGM(dbo.getPcpName(), "pcp_name", dbo.getPk());
		checkDuplicateGM(dbo.getPcpBk(), "pcp_bk", dbo.getPk());
	}

	@Override
	public int create(PendingCrSettings pendingCrSettings, int usrId)
	{
		return create(null, pendingCrSettings, usrId);
	}

	public int create(Connection connection, PendingCrSettings pendingCrSettings, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("pcp_id", "pending_cr_settings");
		String bk = pendingCrSettings.getDisplayField();

		String query = " insert into pending_cr_settings values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, pendingCrSettings.getPcpName(), pendingCrSettings.getPcpBatchSize(), pendingCrSettings.getPcpFreqSecs(), pendingCrSettings.getPcpPollerJbtId(), pendingCrSettings.getPcpCorrelatorJbtId(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(PendingCrSettings pendingCrSettings, int usrId)
	{
		update(null, pendingCrSettings, usrId);
	}

	public void update(Connection connection, PendingCrSettings pendingCrSettings, int usrId)
	{
		String query = " update pending_cr_settings set  pcp_name = ? , pcp_batch_size = ? , pcp_freq_secs = ? , pcp_poller_jbt_id = ? , pcp_correlator_jbt_id = ? , pcp_modified_usr_id = ? , pcp_modified_dttm = ?  where pcp_id = ?  ";
		Object[] paramValues = new Object[] { pendingCrSettings.getPcpName(), pendingCrSettings.getPcpBatchSize(), pendingCrSettings.getPcpFreqSecs(), pendingCrSettings.getPcpPollerJbtId(), pendingCrSettings.getPcpCorrelatorJbtId(), usrId, new DateTime(), pendingCrSettings.getPcpId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into pending_cr_settings values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { pendingCrSettings.getPcpId(), pendingCrSettings.getPcpName(), pendingCrSettings.getPcpBatchSize(), pendingCrSettings.getPcpFreqSecs(), pendingCrSettings.getPcpPollerJbtId(), pendingCrSettings.getPcpCorrelatorJbtId(), pendingCrSettings.getPcpBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<PendingCrSettings> fetchAll()
	{
		String query = " select * from pending_cr_settings ";
		return RefDBHelper.getDB().fetchList(query, PendingCrSettingsImpl.class);
	}

	public List<PendingCrSettings> fetchAuditRowsByPk(int id)
	{
		String query = " select * from pending_cr_settings where pcp_id=? order by pcp_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, PendingCrSettingsImpl.class);
	}

	public PendingCrSettings fetchByPk(int pcpId)
	{
		return fetchByPk(null, pcpId);
	}

	public PendingCrSettings fetchByPk(Connection connection, int pcpId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from pending_cr_settings where pcp_id=? ", pcpId, PendingCrSettingsImpl.class);
	}

	public PendingCrSettings fetchByBk(String pcpBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_cr_settings where pcp_bk=? ", pcpBk, PendingCrSettingsImpl.class);
	}

	public PendingCrSettings fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_cr_settings where pcp_name=? ", displayField, PendingCrSettingsImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("pcp_name", "pending_cr_settings");
	}

}