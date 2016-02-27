/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.CountryDbaImpl;
import se.signa.signature.dbo.CountryImpl;
import se.signa.signature.gen.dbo.Country;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class CountryDba extends SignatureDba<Country>
{
	private static CountryDbaImpl INSTANCE;

	public static CountryDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new CountryDbaImpl();
		return INSTANCE;
	}

	public CountryDba()
	{
		tableName = "country";
		tablePrefix = "ctr";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Ctr Id");
		columns.add("Ctr Iso Cd");
		columns.add("Ctr Name");
		columns.add("Ctr Delete Fl");
		columns.add("Ctr Dial Code");
		columns.add("Ctr User Name");
		columns.add("Ctr Dttm");
		return columns;
	}

	@Override
	public Country createEmptyDbo()
	{
		return new CountryImpl();
	}

	@Override
	public void checkDuplicates(Country dbo)
	{
		checkDuplicateGM(dbo.getCtrName(), "ctr_name", dbo.getPk());
		checkDuplicateGM(dbo.getCtrBk(), "ctr_bk", dbo.getPk());
	}

	@Override
	public int create(Country country, int usrId)
	{
		return create(null, country, usrId);
	}

	public int create(Connection connection, Country country, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("ctr_id", "country");
		String bk = country.getDisplayField();

		String query = " insert into country values(?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, country.getCtrIsoCd(), country.getCtrName(), country.getCtrDeleteFl(), country.getCtrDialCode(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(Country country, int usrId)
	{
		update(null, country, usrId);
	}

	public void update(Connection connection, Country country, int usrId)
	{
		String query = " update country set  ctr_iso_cd = ? , ctr_name = ? , ctr_delete_fl = ? , ctr_dial_code = ? , ctr_modified_usr_id = ? , ctr_modified_dttm = ?  where ctr_id = ?  ";
		Object[] paramValues = new Object[] { country.getCtrIsoCd(), country.getCtrName(), country.getCtrDeleteFl(), country.getCtrDialCode(), usrId, new DateTime(), country.getCtrId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into country values(?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { country.getCtrId(), country.getCtrIsoCd(), country.getCtrName(), country.getCtrDeleteFl(), country.getCtrDialCode(), country.getCtrBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<Country> fetchAll()
	{
		String query = " select * from country ";
		return RefDBHelper.getDB().fetchList(query, CountryImpl.class);
	}

	public List<Country> fetchAuditRowsByPk(int id)
	{
		String query = " select * from country where ctr_id=? order by ctr_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, CountryImpl.class);
	}

	public Country fetchByPk(int ctrId)
	{
		return fetchByPk(null, ctrId);
	}

	public Country fetchByPk(Connection connection, int ctrId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from country where ctr_id=? ", ctrId, CountryImpl.class);
	}

	public Country fetchByBk(String ctrBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from country where ctr_bk=? ", ctrBk, CountryImpl.class);
	}

	public Country fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from country where ctr_name=? ", displayField, CountryImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("ctr_name", "country");
	}

}