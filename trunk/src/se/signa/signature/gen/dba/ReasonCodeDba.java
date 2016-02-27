/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.ReasonCodeDbaImpl;
import se.signa.signature.dbo.ReasonCodeImpl;
import se.signa.signature.gen.dbo.ReasonCode;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class ReasonCodeDba extends SignatureDba<ReasonCode>
{
	private static ReasonCodeDbaImpl INSTANCE;

	public static ReasonCodeDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new ReasonCodeDbaImpl();
		return INSTANCE;
	}

	public ReasonCodeDba()
	{
		tableName = "reason_code";
		tablePrefix = "rec";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Rec Id");
		columns.add("Rec Name");
		columns.add("Rec Code");
		columns.add("Rec User Name");
		columns.add("Rec Dttm");
		return columns;
	}

	@Override
	public ReasonCode createEmptyDbo()
	{
		return new ReasonCodeImpl();
	}

	@Override
	public void checkDuplicates(ReasonCode dbo)
	{
		checkDuplicateGM(dbo.getRecName(), "rec_name", dbo.getPk());
		checkDuplicateGM(dbo.getRecBk(), "rec_bk", dbo.getPk());
	}

	@Override
	public int create(ReasonCode reasonCode, int usrId)
	{
		return create(null, reasonCode, usrId);
	}

	public int create(Connection connection, ReasonCode reasonCode, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("rec_id", "reason_code");
		String bk = reasonCode.getDisplayField();

		String query = " insert into reason_code values(?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, reasonCode.getRecName(), reasonCode.getRecCode(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(ReasonCode reasonCode, int usrId)
	{
		update(null, reasonCode, usrId);
	}

	public void update(Connection connection, ReasonCode reasonCode, int usrId)
	{
		String query = " update reason_code set  rec_name = ? , rec_code = ? , rec_modified_usr_id = ? , rec_modified_dttm = ?  where rec_id = ?  ";
		Object[] paramValues = new Object[] { reasonCode.getRecName(), reasonCode.getRecCode(), usrId, new DateTime(), reasonCode.getRecId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into reason_code values(?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { reasonCode.getRecId(), reasonCode.getRecName(), reasonCode.getRecCode(), reasonCode.getRecBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<ReasonCode> fetchAll()
	{
		String query = " select * from reason_code ";
		return RefDBHelper.getDB().fetchList(query, ReasonCodeImpl.class);
	}

	public List<ReasonCode> fetchAuditRowsByPk(int id)
	{
		String query = " select * from reason_code where rec_id=? order by rec_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, ReasonCodeImpl.class);
	}

	public ReasonCode fetchByPk(int recId)
	{
		return fetchByPk(null, recId);
	}

	public ReasonCode fetchByPk(Connection connection, int recId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from reason_code where rec_id=? ", recId, ReasonCodeImpl.class);
	}

	public ReasonCode fetchByBk(String recBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from reason_code where rec_bk=? ", recBk, ReasonCodeImpl.class);
	}

	public ReasonCode fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from reason_code where rec_name=? ", displayField, ReasonCodeImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("rec_name", "reason_code");
	}

}