/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.AlertDbaImpl;
import se.signa.signature.dbo.AlertImpl;
import se.signa.signature.gen.dbo.Alert;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class AlertDba extends SignatureDba<Alert>
{
	private static AlertDbaImpl INSTANCE;

	public static AlertDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new AlertDbaImpl();
		return INSTANCE;
	}

	public AlertDba()
	{
		tableName = "alert";
		tablePrefix = "alt";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Alt Id");
		columns.add("Nod Id");
		columns.add("Atp Id");
		columns.add("Alt Name");
		columns.add("Alt Desc");
		columns.add("Alt Extra1");
		columns.add("Alt Extra2");
		columns.add("Alt Extra3");
		columns.add("Alt User Name");
		columns.add("Alt Dttm");
		return columns;
	}

	@Override
	public Alert createEmptyDbo()
	{
		return new AlertImpl();
	}

	@Override
	public void checkDuplicates(Alert dbo)
	{
		checkDuplicateGM(dbo.getAltName(), "alt_name", dbo.getPk());
		checkDuplicateGM(dbo.getAltBk(), "alt_bk", dbo.getPk());
	}

	@Override
	public int create(Alert alert, int usrId)
	{
		return create(null, alert, usrId);
	}

	public int create(Connection connection, Alert alert, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("alt_id", "alert");
		String bk = alert.getDisplayField();

		String query = " insert into alert values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, alert.getNodId(), alert.getAtpId(), alert.getAltName(), alert.getAltDesc(), alert.getAltExtra1(), alert.getAltExtra2(), alert.getAltExtra3(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(Alert alert, int usrId)
	{
		update(null, alert, usrId);
	}

	public void update(Connection connection, Alert alert, int usrId)
	{
		String query = " update alert set  nod_id = ? , atp_id = ? , alt_name = ? , alt_desc = ? , alt_extra1 = ? , alt_extra2 = ? , alt_extra3 = ? , alt_modified_usr_id = ? , alt_modified_dttm = ?  where alt_id = ?  ";
		Object[] paramValues = new Object[] { alert.getNodId(), alert.getAtpId(), alert.getAltName(), alert.getAltDesc(), alert.getAltExtra1(), alert.getAltExtra2(), alert.getAltExtra3(), usrId, new DateTime(), alert.getAltId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into alert values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { alert.getAltId(), alert.getNodId(), alert.getAtpId(), alert.getAltName(), alert.getAltDesc(), alert.getAltExtra1(), alert.getAltExtra2(), alert.getAltExtra3(), alert.getAltBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<Alert> fetchAll()
	{
		String query = " select * from alert ";
		return RefDBHelper.getDB().fetchList(query, AlertImpl.class);
	}

	public List<Alert> fetchAuditRowsByPk(int id)
	{
		String query = " select * from alert where alt_id=? order by alt_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, AlertImpl.class);
	}

	public Alert fetchByPk(int altId)
	{
		return fetchByPk(null, altId);
	}

	public Alert fetchByPk(Connection connection, int altId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from alert where alt_id=? ", altId, AlertImpl.class);
	}

	public Alert fetchByBk(String altBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from alert where alt_bk=? ", altBk, AlertImpl.class);
	}

	public Alert fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from alert where alt_name=? ", displayField, AlertImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("alt_name", "alert");
	}

}