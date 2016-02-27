/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.AlertTypeDbaImpl;
import se.signa.signature.dbo.AlertTypeImpl;
import se.signa.signature.gen.dbo.AlertType;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class AlertTypeDba extends SignatureDba<AlertType>
{
	private static AlertTypeDbaImpl INSTANCE;

	public static AlertTypeDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new AlertTypeDbaImpl();
		return INSTANCE;
	}

	public AlertTypeDba()
	{
		tableName = "alert_type";
		tablePrefix = "atp";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Atp Id");
		columns.add("Atp Code");
		columns.add("Atp Desc");
		columns.add("Atp Priority");
		columns.add("Atp Email");
		columns.add("Atp Sms");
		columns.add("Atp User Name");
		columns.add("Atp Dttm");
		return columns;
	}

	@Override
	public AlertType createEmptyDbo()
	{
		return new AlertTypeImpl();
	}

	@Override
	public void checkDuplicates(AlertType dbo)
	{
		checkDuplicateGM(dbo.getAtpCode(), "atp_code", dbo.getPk());
		checkDuplicateGM(dbo.getAtpBk(), "atp_bk", dbo.getPk());
	}

	@Override
	public int create(AlertType alertType, int usrId)
	{
		return create(null, alertType, usrId);
	}

	public int create(Connection connection, AlertType alertType, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("atp_id", "alert_type");
		String bk = alertType.getDisplayField();

		String query = " insert into alert_type values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, alertType.getAtpCode(), alertType.getAtpDesc(), alertType.getAtpPriority(), alertType.getAtpEmail(), alertType.getAtpSms(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(AlertType alertType, int usrId)
	{
		update(null, alertType, usrId);
	}

	public void update(Connection connection, AlertType alertType, int usrId)
	{
		String query = " update alert_type set  atp_code = ? , atp_desc = ? , atp_priority = ? , atp_email = ? , atp_sms = ? , atp_modified_usr_id = ? , atp_modified_dttm = ?  where atp_id = ?  ";
		Object[] paramValues = new Object[] { alertType.getAtpCode(), alertType.getAtpDesc(), alertType.getAtpPriority(), alertType.getAtpEmail(), alertType.getAtpSms(), usrId, new DateTime(), alertType.getAtpId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into alert_type values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { alertType.getAtpId(), alertType.getAtpCode(), alertType.getAtpDesc(), alertType.getAtpPriority(), alertType.getAtpEmail(), alertType.getAtpSms(), alertType.getAtpBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<AlertType> fetchAll()
	{
		String query = " select * from alert_type ";
		return RefDBHelper.getDB().fetchList(query, AlertTypeImpl.class);
	}

	public List<AlertType> fetchAuditRowsByPk(int id)
	{
		String query = " select * from alert_type where atp_id=? order by atp_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, AlertTypeImpl.class);
	}

	public AlertType fetchByPk(int atpId)
	{
		return fetchByPk(null, atpId);
	}

	public AlertType fetchByPk(Connection connection, int atpId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from alert_type where atp_id=? ", atpId, AlertTypeImpl.class);
	}

	public AlertType fetchByBk(String atpBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from alert_type where atp_bk=? ", atpBk, AlertTypeImpl.class);
	}

	public AlertType fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from alert_type where atp_code=? ", displayField, AlertTypeImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("atp_code", "alert_type");
	}

}