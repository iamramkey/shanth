/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.HealthCheckDbaImpl;
import se.signa.signature.dbo.HealthCheckImpl;
import se.signa.signature.gen.dbo.HealthCheck;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class HealthCheckDba extends SignatureDba<HealthCheck>
{
	private static HealthCheckDbaImpl INSTANCE;

	public static HealthCheckDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new HealthCheckDbaImpl();
		return INSTANCE;
	}

	public HealthCheckDba()
	{
		tableName = "health_check";
		tablePrefix = "hch";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Hch Id");
		columns.add("Hch Name");
		columns.add("Hch Package");
		columns.add("Hch Repeat Interval Hrs");
		columns.add("Jbt Id");
		columns.add("Atp Id");
		columns.add("Hch Extra1");
		columns.add("Hch Extra2");
		columns.add("Hch Extra3");
		columns.add("Hch User Name");
		columns.add("Hch Dttm");
		return columns;
	}

	@Override
	public HealthCheck createEmptyDbo()
	{
		return new HealthCheckImpl();
	}

	@Override
	public void checkDuplicates(HealthCheck dbo)
	{
		checkDuplicateGM(dbo.getHchName(), "hch_name", dbo.getPk());
		checkDuplicateGM(dbo.getHchBk(), "hch_bk", dbo.getPk());
	}

	@Override
	public int create(HealthCheck healthCheck, int usrId)
	{
		return create(null, healthCheck, usrId);
	}

	public int create(Connection connection, HealthCheck healthCheck, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("hch_id", "health_check");
		String bk = healthCheck.getDisplayField();

		String query = " insert into health_check values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, healthCheck.getHchName(), healthCheck.getHchPackage(), healthCheck.getHchRepeatIntervalHrs(), healthCheck.getJbtId(), healthCheck.getAtpId(), healthCheck.getHchExtra1(), healthCheck.getHchExtra2(), healthCheck.getHchExtra3(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(HealthCheck healthCheck, int usrId)
	{
		update(null, healthCheck, usrId);
	}

	public void update(Connection connection, HealthCheck healthCheck, int usrId)
	{
		String query = " update health_check set  hch_name = ? , hch_package = ? , hch_repeat_interval_hrs = ? , jbt_id = ? , atp_id = ? , hch_extra1 = ? , hch_extra2 = ? , hch_extra3 = ? , hch_modified_usr_id = ? , hch_modified_dttm = ?  where hch_id = ?  ";
		Object[] paramValues = new Object[] { healthCheck.getHchName(), healthCheck.getHchPackage(), healthCheck.getHchRepeatIntervalHrs(), healthCheck.getJbtId(), healthCheck.getAtpId(), healthCheck.getHchExtra1(), healthCheck.getHchExtra2(), healthCheck.getHchExtra3(), usrId, new DateTime(), healthCheck.getHchId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into health_check values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { healthCheck.getHchId(), healthCheck.getHchName(), healthCheck.getHchPackage(), healthCheck.getHchRepeatIntervalHrs(), healthCheck.getJbtId(), healthCheck.getAtpId(), healthCheck.getHchExtra1(), healthCheck.getHchExtra2(), healthCheck.getHchExtra3(), healthCheck.getHchBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<HealthCheck> fetchAll()
	{
		String query = " select * from health_check ";
		return RefDBHelper.getDB().fetchList(query, HealthCheckImpl.class);
	}

	public List<HealthCheck> fetchAuditRowsByPk(int id)
	{
		String query = " select * from health_check where hch_id=? order by hch_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, HealthCheckImpl.class);
	}

	public HealthCheck fetchByPk(int hchId)
	{
		return fetchByPk(null, hchId);
	}

	public HealthCheck fetchByPk(Connection connection, int hchId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from health_check where hch_id=? ", hchId, HealthCheckImpl.class);
	}

	public HealthCheck fetchByBk(String hchBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from health_check where hch_bk=? ", hchBk, HealthCheckImpl.class);
	}

	public HealthCheck fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from health_check where hch_name=? ", displayField, HealthCheckImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("hch_name", "health_check");
	}

}