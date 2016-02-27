/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.RunnerDbaImpl;
import se.signa.signature.dbo.RunnerImpl;
import se.signa.signature.gen.dbo.Runner;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class RunnerDba extends SignatureDba<Runner>
{
	private static RunnerDbaImpl INSTANCE;

	public static RunnerDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new RunnerDbaImpl();
		return INSTANCE;
	}

	public RunnerDba()
	{
		tableName = "runner";
		tablePrefix = "run";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Run Id");
		columns.add("Run Name");
		columns.add("Run Type");
		columns.add("Run Package");
		columns.add("Run User Name");
		columns.add("Run Dttm");
		return columns;
	}

	@Override
	public Runner createEmptyDbo()
	{
		return new RunnerImpl();
	}

	@Override
	public void checkDuplicates(Runner dbo)
	{
		checkDuplicateGM(dbo.getRunName(), "run_name", dbo.getPk());
		checkDuplicateGM(dbo.getRunBk(), "run_bk", dbo.getPk());
	}

	@Override
	public int create(Runner runner, int usrId)
	{
		return create(null, runner, usrId);
	}

	public int create(Connection connection, Runner runner, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("run_id", "runner");
		String bk = runner.getDisplayField();

		String query = " insert into runner values(?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, runner.getRunName(), runner.getRunType(), runner.getRunPackage(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(Runner runner, int usrId)
	{
		update(null, runner, usrId);
	}

	public void update(Connection connection, Runner runner, int usrId)
	{
		String query = " update runner set  run_name = ? , run_type = ? , run_package = ? , run_modified_usr_id = ? , run_modified_dttm = ?  where run_id = ?  ";
		Object[] paramValues = new Object[] { runner.getRunName(), runner.getRunType(), runner.getRunPackage(), usrId, new DateTime(), runner.getRunId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into runner values(?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { runner.getRunId(), runner.getRunName(), runner.getRunType(), runner.getRunPackage(), runner.getRunBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<Runner> fetchAll()
	{
		String query = " select * from runner ";
		return RefDBHelper.getDB().fetchList(query, RunnerImpl.class);
	}

	public List<Runner> fetchAuditRowsByPk(int id)
	{
		String query = " select * from runner where run_id=? order by run_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, RunnerImpl.class);
	}

	public Runner fetchByPk(int runId)
	{
		return fetchByPk(null, runId);
	}

	public Runner fetchByPk(Connection connection, int runId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from runner where run_id=? ", runId, RunnerImpl.class);
	}

	public Runner fetchByBk(String runBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from runner where run_bk=? ", runBk, RunnerImpl.class);
	}

	public Runner fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from runner where run_name=? ", displayField, RunnerImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("run_name", "runner");
	}

}