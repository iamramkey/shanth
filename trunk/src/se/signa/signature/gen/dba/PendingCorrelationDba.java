/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.PendingCorrelationDbaImpl;
import se.signa.signature.dbo.PendingCorrelationImpl;
import se.signa.signature.gen.dbo.PendingCorrelation;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class PendingCorrelationDba extends SignatureDba<PendingCorrelation>
{
	private static PendingCorrelationDbaImpl INSTANCE;

	public static PendingCorrelationDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new PendingCorrelationDbaImpl();
		return INSTANCE;
	}

	public PendingCorrelationDba()
	{
		tableName = "pending_correlation";
		tablePrefix = "pco";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Pco Id");
		columns.add("Pco Name");
		columns.add("Pco Partition");
		columns.add("Pco User Name");
		columns.add("Pco Dttm");
		return columns;
	}

	@Override
	public PendingCorrelation createEmptyDbo()
	{
		return new PendingCorrelationImpl();
	}

	@Override
	public void checkDuplicates(PendingCorrelation dbo)
	{
		checkDuplicateGM(dbo.getPcoName(), "pco_name", dbo.getPk());
		checkDuplicateGM(dbo.getPcoBk(), "pco_bk", dbo.getPk());
	}

	@Override
	public int create(PendingCorrelation pendingCorrelation, int usrId)
	{
		return create(null, pendingCorrelation, usrId);
	}

	public int create(Connection connection, PendingCorrelation pendingCorrelation, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("pco_id", "pending_correlation");
		String bk = pendingCorrelation.getDisplayField();

		String query = " insert into pending_correlation values(?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, pendingCorrelation.getPcoName(), pendingCorrelation.getPcoPartition(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(PendingCorrelation pendingCorrelation, int usrId)
	{
		update(null, pendingCorrelation, usrId);
	}

	public void update(Connection connection, PendingCorrelation pendingCorrelation, int usrId)
	{
		String query = " update pending_correlation set  pco_name = ? , pco_partition = ? , pco_modified_usr_id = ? , pco_modified_dttm = ?  where pco_id = ?  ";
		Object[] paramValues = new Object[] { pendingCorrelation.getPcoName(), pendingCorrelation.getPcoPartition(), usrId, new DateTime(), pendingCorrelation.getPcoId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into pending_correlation values(?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { pendingCorrelation.getPcoId(), pendingCorrelation.getPcoName(), pendingCorrelation.getPcoPartition(), pendingCorrelation.getPcoBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<PendingCorrelation> fetchAll()
	{
		String query = " select * from pending_correlation ";
		return RefDBHelper.getDB().fetchList(query, PendingCorrelationImpl.class);
	}

	public List<PendingCorrelation> fetchAuditRowsByPk(int id)
	{
		String query = " select * from pending_correlation where pco_id=? order by pco_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, PendingCorrelationImpl.class);
	}

	public PendingCorrelation fetchByPk(int pcoId)
	{
		return fetchByPk(null, pcoId);
	}

	public PendingCorrelation fetchByPk(Connection connection, int pcoId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from pending_correlation where pco_id=? ", pcoId, PendingCorrelationImpl.class);
	}

	public PendingCorrelation fetchByBk(String pcoBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_correlation where pco_bk=? ", pcoBk, PendingCorrelationImpl.class);
	}

	public PendingCorrelation fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from pending_correlation where pco_name=? ", displayField, PendingCorrelationImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("pco_name", "pending_correlation");
	}

}