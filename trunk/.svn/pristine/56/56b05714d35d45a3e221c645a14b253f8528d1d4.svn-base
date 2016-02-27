/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.HouseKeepingJobsDbaImpl;
import se.signa.signature.dbo.HouseKeepingJobsImpl;
import se.signa.signature.gen.dbo.HouseKeepingJobs;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class HouseKeepingJobsDba extends SignatureDba<HouseKeepingJobs>
{
	private static HouseKeepingJobsDbaImpl INSTANCE;

	public static HouseKeepingJobsDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new HouseKeepingJobsDbaImpl();
		return INSTANCE;
	}

	public HouseKeepingJobsDba()
	{
		tableName = "house_keeping_jobs";
		tablePrefix = "hkj";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Hkj Id");
		columns.add("Hkj Name");
		columns.add("Jbt Id");
		columns.add("Hkj User Name");
		columns.add("Hkj Dttm");
		return columns;
	}

	@Override
	public HouseKeepingJobs createEmptyDbo()
	{
		return new HouseKeepingJobsImpl();
	}

	@Override
	public void checkDuplicates(HouseKeepingJobs dbo)
	{
		checkDuplicateGM(dbo.getHkjName(), "hkj_name", dbo.getPk());
		checkDuplicateGM(dbo.getHkjBk(), "hkj_bk", dbo.getPk());
	}

	@Override
	public int create(HouseKeepingJobs houseKeepingJobs, int usrId)
	{
		return create(null, houseKeepingJobs, usrId);
	}

	public int create(Connection connection, HouseKeepingJobs houseKeepingJobs, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("hkj_id", "house_keeping_jobs");
		String bk = houseKeepingJobs.getDisplayField();

		String query = " insert into house_keeping_jobs values(?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, houseKeepingJobs.getHkjName(), houseKeepingJobs.getJbtId(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(HouseKeepingJobs houseKeepingJobs, int usrId)
	{
		update(null, houseKeepingJobs, usrId);
	}

	public void update(Connection connection, HouseKeepingJobs houseKeepingJobs, int usrId)
	{
		String query = " update house_keeping_jobs set  hkj_name = ? , jbt_id = ? , hkj_modified_usr_id = ? , hkj_modified_dttm = ?  where hkj_id = ?  ";
		Object[] paramValues = new Object[] { houseKeepingJobs.getHkjName(), houseKeepingJobs.getJbtId(), usrId, new DateTime(), houseKeepingJobs.getHkjId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into house_keeping_jobs values(?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { houseKeepingJobs.getHkjId(), houseKeepingJobs.getHkjName(), houseKeepingJobs.getJbtId(), houseKeepingJobs.getHkjBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<HouseKeepingJobs> fetchAll()
	{
		String query = " select * from house_keeping_jobs ";
		return RefDBHelper.getDB().fetchList(query, HouseKeepingJobsImpl.class);
	}

	public List<HouseKeepingJobs> fetchAuditRowsByPk(int id)
	{
		String query = " select * from house_keeping_jobs where hkj_id=? order by hkj_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, HouseKeepingJobsImpl.class);
	}

	public HouseKeepingJobs fetchByPk(int hkjId)
	{
		return fetchByPk(null, hkjId);
	}

	public HouseKeepingJobs fetchByPk(Connection connection, int hkjId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from house_keeping_jobs where hkj_id=? ", hkjId, HouseKeepingJobsImpl.class);
	}

	public HouseKeepingJobs fetchByBk(String hkjBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from house_keeping_jobs where hkj_bk=? ", hkjBk, HouseKeepingJobsImpl.class);
	}

	public HouseKeepingJobs fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from house_keeping_jobs where hkj_name=? ", displayField, HouseKeepingJobsImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("hkj_name", "house_keeping_jobs");
	}

}