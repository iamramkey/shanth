/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.CachetblDbaImpl;
import se.signa.signature.dbo.CachetblImpl;
import se.signa.signature.gen.dbo.Cachetbl;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class CachetblDba extends SignatureDba<Cachetbl>
{
	private static CachetblDbaImpl INSTANCE;

	public static CachetblDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new CachetblDbaImpl();
		return INSTANCE;
	}

	public CachetblDba()
	{
		tableName = "cachetbl";
		tablePrefix = "cac";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Cac Id");
		columns.add("Cac Name");
		columns.add("Cac Type");
		columns.add("Cac Package");
		columns.add("Cac User Name");
		columns.add("Cac Dttm");
		return columns;
	}

	@Override
	public Cachetbl createEmptyDbo()
	{
		return new CachetblImpl();
	}

	@Override
	public void checkDuplicates(Cachetbl dbo)
	{
		checkDuplicateGM(dbo.getCacName(), "cac_name", dbo.getPk());
		checkDuplicateGM(dbo.getCacBk(), "cac_bk", dbo.getPk());
	}

	@Override
	public int create(Cachetbl cachetbl, int usrId)
	{
		return create(null, cachetbl, usrId);
	}

	public int create(Connection connection, Cachetbl cachetbl, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("cac_id", "cachetbl");
		String bk = cachetbl.getDisplayField();

		String query = " insert into cachetbl values(?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, cachetbl.getCacName(), cachetbl.getCacType(), cachetbl.getCacPackage(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(Cachetbl cachetbl, int usrId)
	{
		update(null, cachetbl, usrId);
	}

	public void update(Connection connection, Cachetbl cachetbl, int usrId)
	{
		String query = " update cachetbl set  cac_name = ? , cac_type = ? , cac_package = ? , cac_modified_usr_id = ? , cac_modified_dttm = ?  where cac_id = ?  ";
		Object[] paramValues = new Object[] { cachetbl.getCacName(), cachetbl.getCacType(), cachetbl.getCacPackage(), usrId, new DateTime(), cachetbl.getCacId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into cachetbl values(?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { cachetbl.getCacId(), cachetbl.getCacName(), cachetbl.getCacType(), cachetbl.getCacPackage(), cachetbl.getCacBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<Cachetbl> fetchAll()
	{
		String query = " select * from cachetbl ";
		return RefDBHelper.getDB().fetchList(query, CachetblImpl.class);
	}

	public List<Cachetbl> fetchAuditRowsByPk(int id)
	{
		String query = " select * from cachetbl where cac_id=? order by cac_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, CachetblImpl.class);
	}

	public Cachetbl fetchByPk(int cacId)
	{
		return fetchByPk(null, cacId);
	}

	public Cachetbl fetchByPk(Connection connection, int cacId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from cachetbl where cac_id=? ", cacId, CachetblImpl.class);
	}

	public Cachetbl fetchByBk(String cacBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from cachetbl where cac_bk=? ", cacBk, CachetblImpl.class);
	}

	public Cachetbl fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from cachetbl where cac_name=? ", displayField, CachetblImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("cac_name", "cachetbl");
	}

}