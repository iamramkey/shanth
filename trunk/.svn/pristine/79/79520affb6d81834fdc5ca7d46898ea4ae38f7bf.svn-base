/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.RecentActivityDbaImpl;
import se.signa.signature.dbo.RecentActivityImpl;
import se.signa.signature.gen.dbo.RecentActivity;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class RecentActivityDba extends SignatureDba<RecentActivity>
{
	private static RecentActivityDbaImpl INSTANCE;

	public static RecentActivityDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new RecentActivityDbaImpl();
		return INSTANCE;
	}

	public RecentActivityDba()
	{
		tableName = "recent_activity";
		tablePrefix = "rea";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Rea Id");
		columns.add("Del Id");
		columns.add("Job Id");
		columns.add("Rea Type");
		columns.add("Rea Desc");
		columns.add("Rea Name");
		columns.add("Rea User Name");
		columns.add("Rea Dttm");
		return columns;
	}

	@Override
	public RecentActivity createEmptyDbo()
	{
		return new RecentActivityImpl();
	}

	@Override
	public void checkDuplicates(RecentActivity dbo)
	{
		checkDuplicateGM(dbo.getReaName(), "rea_name", dbo.getPk());
		checkDuplicateGM(dbo.getReaBk(), "rea_bk", dbo.getPk());
	}

	@Override
	public int create(RecentActivity recentActivity, int usrId)
	{
		return create(null, recentActivity, usrId);
	}

	public int create(Connection connection, RecentActivity recentActivity, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("rea_id", "recent_activity");
		String bk = recentActivity.getDisplayField();

		String query = " insert into recent_activity values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, recentActivity.getDelId(), recentActivity.getJobId(), recentActivity.getReaType(), recentActivity.getReaDesc(), recentActivity.getReaName(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(RecentActivity recentActivity, int usrId)
	{
		update(null, recentActivity, usrId);
	}

	public void update(Connection connection, RecentActivity recentActivity, int usrId)
	{
		String query = " update recent_activity set  del_id = ? , job_id = ? , rea_type = ? , rea_desc = ? , rea_name = ? , rea_modified_usr_id = ? , rea_modified_dttm = ?  where rea_id = ?  ";
		Object[] paramValues = new Object[] { recentActivity.getDelId(), recentActivity.getJobId(), recentActivity.getReaType(), recentActivity.getReaDesc(), recentActivity.getReaName(), usrId, new DateTime(), recentActivity.getReaId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into recent_activity values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { recentActivity.getReaId(), recentActivity.getDelId(), recentActivity.getJobId(), recentActivity.getReaType(), recentActivity.getReaDesc(), recentActivity.getReaName(), recentActivity.getReaBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<RecentActivity> fetchAll()
	{
		String query = " select * from recent_activity ";
		return RefDBHelper.getDB().fetchList(query, RecentActivityImpl.class);
	}

	public List<RecentActivity> fetchAuditRowsByPk(int id)
	{
		String query = " select * from recent_activity where rea_id=? order by rea_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, RecentActivityImpl.class);
	}

	public RecentActivity fetchByPk(int reaId)
	{
		return fetchByPk(null, reaId);
	}

	public RecentActivity fetchByPk(Connection connection, int reaId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from recent_activity where rea_id=? ", reaId, RecentActivityImpl.class);
	}

	public RecentActivity fetchByBk(String reaBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from recent_activity where rea_bk=? ", reaBk, RecentActivityImpl.class);
	}

	public RecentActivity fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from recent_activity where rea_name=? ", displayField, RecentActivityImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("rea_name", "recent_activity");
	}

}