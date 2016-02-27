/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.PendingFileTransferSettingsDbaImpl;
import se.signa.signature.dbo.PendingFileTransferSettingsImpl;
import se.signa.signature.gen.dbo.PendingFileTransferSettings;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class PendingFileTransferSettingsDba extends SignatureDba<PendingFileTransferSettings>
{
	private static PendingFileTransferSettingsDbaImpl INSTANCE;

	public static PendingFileTransferSettingsDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new PendingFileTransferSettingsDbaImpl();
		return INSTANCE;
	}

	public PendingFileTransferSettingsDba()
	{
		tableName = "pending_file_transfer_settings";
		tablePrefix = "pes";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Pes Id");
		columns.add("Pes Name");
		columns.add("Pes Batch Size");
		columns.add("Pes Frequency Secs");
		columns.add("Pes Timeout Secs");
		columns.add("Fts Id");
		columns.add("Pes Poller Jbt Id");
		columns.add("Pes Parser Jbt Id");
		columns.add("Pes User Name");
		columns.add("Pes Dttm");
		return columns;
	}

	@Override
	public PendingFileTransferSettings createEmptyDbo()
	{
		return new PendingFileTransferSettingsImpl();
	}

	@Override
	public void checkDuplicates(PendingFileTransferSettings dbo)
	{
		checkDuplicateGM(dbo.getPesName(), "pes_name", dbo.getPk());
		checkDuplicateGM(dbo.getPesBk(), "pes_bk", dbo.getPk());
	}

	@Override
	public int create(PendingFileTransferSettings pendingFileTransferSettings, int usrId)
	{
		return create(null, pendingFileTransferSettings, usrId);
	}

	public int create(Connection connection, PendingFileTransferSettings pendingFileTransferSettings, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("pes_id", "pending_file_transfer_settings");
		String bk = pendingFileTransferSettings.getDisplayField();

		String query = " insert into pending_file_transfer_settings values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, pendingFileTransferSettings.getPesName(), pendingFileTransferSettings.getPesBatchSize(), pendingFileTransferSettings.getPesFrequencySecs(), pendingFileTransferSettings.getPesTimeoutSecs(), pendingFileTransferSettings.getFtsId(), pendingFileTransferSettings.getPesPollerJbtId(), pendingFileTransferSettings.getPesParserJbtId(), bk, usrId, null,
				new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(PendingFileTransferSettings pendingFileTransferSettings, int usrId)
	{
		update(null, pendingFileTransferSettings, usrId);
	}

	public void update(Connection connection, PendingFileTransferSettings pendingFileTransferSettings, int usrId)
	{
		String query = " update pending_file_transfer_settings set  pes_name = ? , pes_batch_size = ? , pes_frequency_secs = ? , pes_timeout_secs = ? , fts_id = ? , pes_poller_jbt_id = ? , pes_parser_jbt_id = ? , pes_modified_usr_id = ? , pes_modified_dttm = ?  where pes_id = ?  ";
		Object[] paramValues = new Object[] { pendingFileTransferSettings.getPesName(), pendingFileTransferSettings.getPesBatchSize(), pendingFileTransferSettings.getPesFrequencySecs(), pendingFileTransferSettings.getPesTimeoutSecs(), pendingFileTransferSettings.getFtsId(), pendingFileTransferSettings.getPesPollerJbtId(), pendingFileTransferSettings.getPesParserJbtId(), usrId, new DateTime(),
				pendingFileTransferSettings.getPesId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into pending_file_transfer_settings values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { pendingFileTransferSettings.getPesId(), pendingFileTransferSettings.getPesName(), pendingFileTransferSettings.getPesBatchSize(), pendingFileTransferSettings.getPesFrequencySecs(), pendingFileTransferSettings.getPesTimeoutSecs(), pendingFileTransferSettings.getFtsId(), pendingFileTransferSettings.getPesPollerJbtId(),
				pendingFileTransferSettings.getPesParserJbtId(), pendingFileTransferSettings.getPesBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<PendingFileTransferSettings> fetchAll()
	{
		String query = " select * from pending_file_transfer_settings ";
		return RefDBHelper.getDB().fetchList(query, PendingFileTransferSettingsImpl.class);
	}

	public List<PendingFileTransferSettings> fetchAuditRowsByPk(int id)
	{
		String query = " select * from pending_file_transfer_settings where pes_id=? order by pes_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, PendingFileTransferSettingsImpl.class);
	}

	public PendingFileTransferSettings fetchByPk(int pesId)
	{
		return fetchByPk(null, pesId);
	}

	public PendingFileTransferSettings fetchByPk(Connection connection, int pesId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from pending_file_transfer_settings where pes_id=? ", pesId, PendingFileTransferSettingsImpl.class);
	}

	public PendingFileTransferSettings fetchByBk(String pesBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_file_transfer_settings where pes_bk=? ", pesBk, PendingFileTransferSettingsImpl.class);
	}

	public PendingFileTransferSettings fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_file_transfer_settings where pes_name=? ", displayField, PendingFileTransferSettingsImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("pes_name", "pending_file_transfer_settings");
	}

}