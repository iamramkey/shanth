/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.ProcessDependancyDbaImpl;
import se.signa.signature.dbo.ProcessDependancyImpl;
import se.signa.signature.gen.dbo.ProcessDependancy;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class ProcessDependancyDba extends SignatureDba<ProcessDependancy>
{
	private static ProcessDependancyDbaImpl INSTANCE;

	public static ProcessDependancyDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new ProcessDependancyDbaImpl();
		return INSTANCE;
	}

	public ProcessDependancyDba()
	{
		tableName = "process_dependancy";
		tablePrefix = "pde";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Pde Id");
		columns.add("Pde Name");
		columns.add("Pde Master Jbt Id");
		columns.add("Pde Dependant Jbt Id");
		columns.add("Pde User Name");
		columns.add("Pde Dttm");
		return columns;
	}

	@Override
	public ProcessDependancy createEmptyDbo()
	{
		return new ProcessDependancyImpl();
	}

	@Override
	public void checkDuplicates(ProcessDependancy dbo)
	{
		checkDuplicateGM(dbo.getPdeName(), "pde_name", dbo.getPk());
		checkDuplicateGM(dbo.getPdeBk(), "pde_bk", dbo.getPk());
	}

	@Override
	public int create(ProcessDependancy processDependancy, int usrId)
	{
		return create(null, processDependancy, usrId);
	}

	public int create(Connection connection, ProcessDependancy processDependancy, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("pde_id", "process_dependancy");
		String bk = processDependancy.getDisplayField();

		String query = " insert into process_dependancy values(?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, processDependancy.getPdeName(), processDependancy.getPdeMasterJbtId(), processDependancy.getPdeDependantJbtId(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(ProcessDependancy processDependancy, int usrId)
	{
		update(null, processDependancy, usrId);
	}

	public void update(Connection connection, ProcessDependancy processDependancy, int usrId)
	{
		String query = " update process_dependancy set  pde_name = ? , pde_master_jbt_id = ? , pde_dependant_jbt_id = ? , pde_modified_usr_id = ? , pde_modified_dttm = ?  where pde_id = ?  ";
		Object[] paramValues = new Object[] { processDependancy.getPdeName(), processDependancy.getPdeMasterJbtId(), processDependancy.getPdeDependantJbtId(), usrId, new DateTime(), processDependancy.getPdeId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into process_dependancy values(?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { processDependancy.getPdeId(), processDependancy.getPdeName(), processDependancy.getPdeMasterJbtId(), processDependancy.getPdeDependantJbtId(), processDependancy.getPdeBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<ProcessDependancy> fetchAll()
	{
		String query = " select * from process_dependancy ";
		return RefDBHelper.getDB().fetchList(query, ProcessDependancyImpl.class);
	}

	public List<ProcessDependancy> fetchAuditRowsByPk(int id)
	{
		String query = " select * from process_dependancy where pde_id=? order by pde_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, ProcessDependancyImpl.class);
	}

	public ProcessDependancy fetchByPk(int pdeId)
	{
		return fetchByPk(null, pdeId);
	}

	public ProcessDependancy fetchByPk(Connection connection, int pdeId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from process_dependancy where pde_id=? ", pdeId, ProcessDependancyImpl.class);
	}

	public ProcessDependancy fetchByBk(String pdeBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from process_dependancy where pde_bk=? ", pdeBk, ProcessDependancyImpl.class);
	}

	public ProcessDependancy fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from process_dependancy where pde_name=? ", displayField, ProcessDependancyImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("pde_name", "process_dependancy");
	}

}