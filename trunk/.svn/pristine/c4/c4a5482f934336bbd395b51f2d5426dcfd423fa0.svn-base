/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.MasterSearchFormDbaImpl;
import se.signa.signature.dbo.MasterSearchFormImpl;
import se.signa.signature.gen.dbo.MasterSearchForm;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class MasterSearchFormDba extends SignatureDba<MasterSearchForm>
{
	private static MasterSearchFormDbaImpl INSTANCE;

	public static MasterSearchFormDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new MasterSearchFormDbaImpl();
		return INSTANCE;
	}

	public MasterSearchFormDba()
	{
		tableName = "master_search_form";
		tablePrefix = "msf";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Msf Id");
		columns.add("Mse Id");
		columns.add("Msf Order No");
		columns.add("Msf Identifier");
		columns.add("Msf Name");
		columns.add("Msf Mandatory");
		columns.add("Msf Type");
		columns.add("Msf Code");
		columns.add("Msf User Name");
		columns.add("Msf Dttm");
		return columns;
	}

	@Override
	public MasterSearchForm createEmptyDbo()
	{
		return new MasterSearchFormImpl();
	}

	@Override
	public void checkDuplicates(MasterSearchForm dbo)
	{
		checkDuplicateGM(dbo.getMsfCode(), "msf_code", dbo.getPk());
		checkDuplicateGM(dbo.getMsfBk(), "msf_bk", dbo.getPk());
	}

	@Override
	public int create(MasterSearchForm masterSearchForm, int usrId)
	{
		return create(null, masterSearchForm, usrId);
	}

	public int create(Connection connection, MasterSearchForm masterSearchForm, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("msf_id", "master_search_form");
		String bk = masterSearchForm.getDisplayField();

		String query = " insert into master_search_form values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, masterSearchForm.getMseId(), masterSearchForm.getMsfOrderNo(), masterSearchForm.getMsfIdentifier(), masterSearchForm.getMsfName(), masterSearchForm.getMsfMandatory(), masterSearchForm.getMsfType(), masterSearchForm.getMsfCode(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(MasterSearchForm masterSearchForm, int usrId)
	{
		update(null, masterSearchForm, usrId);
	}

	public void update(Connection connection, MasterSearchForm masterSearchForm, int usrId)
	{
		String query = " update master_search_form set  mse_id = ? , msf_order_no = ? , msf_identifier = ? , msf_name = ? , msf_mandatory = ? , msf_type = ? , msf_code = ? , msf_modified_usr_id = ? , msf_modified_dttm = ?  where msf_id = ?  ";
		Object[] paramValues = new Object[] { masterSearchForm.getMseId(), masterSearchForm.getMsfOrderNo(), masterSearchForm.getMsfIdentifier(), masterSearchForm.getMsfName(), masterSearchForm.getMsfMandatory(), masterSearchForm.getMsfType(), masterSearchForm.getMsfCode(), usrId, new DateTime(), masterSearchForm.getMsfId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into master_search_form values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { masterSearchForm.getMsfId(), masterSearchForm.getMseId(), masterSearchForm.getMsfOrderNo(), masterSearchForm.getMsfIdentifier(), masterSearchForm.getMsfName(), masterSearchForm.getMsfMandatory(), masterSearchForm.getMsfType(), masterSearchForm.getMsfCode(), masterSearchForm.getMsfBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<MasterSearchForm> fetchAll()
	{
		String query = " select * from master_search_form ";
		return RefDBHelper.getDB().fetchList(query, MasterSearchFormImpl.class);
	}

	public List<MasterSearchForm> fetchAuditRowsByPk(int id)
	{
		String query = " select * from master_search_form where msf_id=? order by msf_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, MasterSearchFormImpl.class);
	}

	public MasterSearchForm fetchByPk(int msfId)
	{
		return fetchByPk(null, msfId);
	}

	public MasterSearchForm fetchByPk(Connection connection, int msfId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from master_search_form where msf_id=? ", msfId, MasterSearchFormImpl.class);
	}

	public MasterSearchForm fetchByBk(String msfBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from master_search_form where msf_bk=? ", msfBk, MasterSearchFormImpl.class);
	}

	public MasterSearchForm fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from master_search_form where msf_code=? ", displayField, MasterSearchFormImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("msf_code", "master_search_form");
	}

}