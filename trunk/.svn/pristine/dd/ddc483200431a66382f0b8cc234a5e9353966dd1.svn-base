/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.FiletblDbaImpl;
import se.signa.signature.dbo.FiletblImpl;
import se.signa.signature.gen.dbo.Filetbl;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class FiletblDba extends SignatureDba<Filetbl>
{
	private static FiletblDbaImpl INSTANCE;

	public static FiletblDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new FiletblDbaImpl();
		return INSTANCE;
	}

	public FiletblDba()
	{
		tableName = "filetbl";
		tablePrefix = "fil";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Fil Id");
		columns.add("Fil Path");
		columns.add("Fil Name");
		columns.add("Fil Identifier");
		columns.add("Fil Type");
		columns.add("Job Id");
		columns.add("Fil Extra1");
		columns.add("Fil User Name");
		columns.add("Fil Dttm");
		return columns;
	}

	@Override
	public Filetbl createEmptyDbo()
	{
		return new FiletblImpl();
	}

	@Override
	public void checkDuplicates(Filetbl dbo)
	{
		checkDuplicateGM(dbo.getFilIdentifier(), "fil_identifier", dbo.getPk());
		checkDuplicateGM(dbo.getFilBk(), "fil_bk", dbo.getPk());
	}

	@Override
	public int create(Filetbl filetbl, int usrId)
	{
		return create(null, filetbl, usrId);
	}

	public int create(Connection connection, Filetbl filetbl, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("fil_id", "filetbl");
		String bk = filetbl.getDisplayField();

		String query = " insert into filetbl values(?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, filetbl.getFilPath(), filetbl.getFilName(), filetbl.getFilIdentifier(), filetbl.getFilType(), filetbl.getJobId(), filetbl.getFilExtra1(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(Filetbl filetbl, int usrId)
	{
		update(null, filetbl, usrId);
	}

	public void update(Connection connection, Filetbl filetbl, int usrId)
	{
		String query = " update filetbl set  fil_path = ? , fil_name = ? , fil_identifier = ? , fil_type = ? , job_id = ? , fil_extra1 = ? , fil_modified_usr_id = ? , fil_modified_dttm = ?  where fil_id = ?  ";
		Object[] paramValues = new Object[] { filetbl.getFilPath(), filetbl.getFilName(), filetbl.getFilIdentifier(), filetbl.getFilType(), filetbl.getJobId(), filetbl.getFilExtra1(), usrId, new DateTime(), filetbl.getFilId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into filetbl values(?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { filetbl.getFilId(), filetbl.getFilPath(), filetbl.getFilName(), filetbl.getFilIdentifier(), filetbl.getFilType(), filetbl.getJobId(), filetbl.getFilExtra1(), filetbl.getFilBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<Filetbl> fetchAll()
	{
		String query = " select * from filetbl ";
		return RefDBHelper.getDB().fetchList(query, FiletblImpl.class);
	}

	public List<Filetbl> fetchAuditRowsByPk(int id)
	{
		String query = " select * from filetbl where fil_id=? order by fil_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, FiletblImpl.class);
	}

	public Filetbl fetchByPk(int filId)
	{
		return fetchByPk(null, filId);
	}

	public Filetbl fetchByPk(Connection connection, int filId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from filetbl where fil_id=? ", filId, FiletblImpl.class);
	}

	public Filetbl fetchByBk(String filBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from filetbl where fil_bk=? ", filBk, FiletblImpl.class);
	}

	public Filetbl fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from filetbl where fil_identifier=? ", displayField, FiletblImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("fil_identifier", "filetbl");
	}

}