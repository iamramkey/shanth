/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.MasterSearchActionDbaImpl;
import se.signa.signature.dbo.MasterSearchActionImpl;
import se.signa.signature.gen.dbo.MasterSearchAction;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class MasterSearchActionDba extends SignatureDba<MasterSearchAction>
{
	private static MasterSearchActionDbaImpl INSTANCE;

	public static MasterSearchActionDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new MasterSearchActionDbaImpl();
		return INSTANCE;
	}

	public MasterSearchActionDba()
	{
		tableName = "master_search_action";
		tablePrefix = "msa";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Msa Id");
		columns.add("Mse Id");
		columns.add("Msa Type");
		columns.add("Msa Action");
		columns.add("Msa Name");
		columns.add("Msa Colour");
		columns.add("Msa Icon");
		columns.add("Msa Visible");
		columns.add("Msa Function");
		columns.add("Msa Param");
		columns.add("Msa Code");
		columns.add("Msa User Name");
		columns.add("Msa Dttm");
		return columns;
	}

	@Override
	public MasterSearchAction createEmptyDbo()
	{
		return new MasterSearchActionImpl();
	}

	@Override
	public void checkDuplicates(MasterSearchAction dbo)
	{
		checkDuplicateGM(dbo.getMsaCode(), "msa_code", dbo.getPk());
		checkDuplicateGM(dbo.getMsaBk(), "msa_bk", dbo.getPk());
	}

	@Override
	public int create(MasterSearchAction masterSearchAction, int usrId)
	{
		return create(null, masterSearchAction, usrId);
	}

	public int create(Connection connection, MasterSearchAction masterSearchAction, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("msa_id", "master_search_action");
		String bk = masterSearchAction.getDisplayField();

		String query = " insert into master_search_action values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, masterSearchAction.getMseId(), masterSearchAction.getMsaType(), masterSearchAction.getMsaAction(), masterSearchAction.getMsaName(), masterSearchAction.getMsaColour(), masterSearchAction.getMsaIcon(), masterSearchAction.getMsaVisible(), masterSearchAction.getMsaFunction(), masterSearchAction.getMsaParam(), masterSearchAction.getMsaCode(), bk,
				usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(MasterSearchAction masterSearchAction, int usrId)
	{
		update(null, masterSearchAction, usrId);
	}

	public void update(Connection connection, MasterSearchAction masterSearchAction, int usrId)
	{
		String query = " update master_search_action set  mse_id = ? , msa_type = ? , msa_action = ? , msa_name = ? , msa_colour = ? , msa_icon = ? , msa_visible = ? , msa_function = ? , msa_param = ? , msa_code = ? , msa_modified_usr_id = ? , msa_modified_dttm = ?  where msa_id = ?  ";
		Object[] paramValues = new Object[] { masterSearchAction.getMseId(), masterSearchAction.getMsaType(), masterSearchAction.getMsaAction(), masterSearchAction.getMsaName(), masterSearchAction.getMsaColour(), masterSearchAction.getMsaIcon(), masterSearchAction.getMsaVisible(), masterSearchAction.getMsaFunction(), masterSearchAction.getMsaParam(), masterSearchAction.getMsaCode(), usrId,
				new DateTime(), masterSearchAction.getMsaId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into master_search_action values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { masterSearchAction.getMsaId(), masterSearchAction.getMseId(), masterSearchAction.getMsaType(), masterSearchAction.getMsaAction(), masterSearchAction.getMsaName(), masterSearchAction.getMsaColour(), masterSearchAction.getMsaIcon(), masterSearchAction.getMsaVisible(), masterSearchAction.getMsaFunction(), masterSearchAction.getMsaParam(),
				masterSearchAction.getMsaCode(), masterSearchAction.getMsaBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<MasterSearchAction> fetchAll()
	{
		String query = " select * from master_search_action ";
		return RefDBHelper.getDB().fetchList(query, MasterSearchActionImpl.class);
	}

	public List<MasterSearchAction> fetchAuditRowsByPk(int id)
	{
		String query = " select * from master_search_action where msa_id=? order by msa_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, MasterSearchActionImpl.class);
	}

	public MasterSearchAction fetchByPk(int msaId)
	{
		return fetchByPk(null, msaId);
	}

	public MasterSearchAction fetchByPk(Connection connection, int msaId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from master_search_action where msa_id=? ", msaId, MasterSearchActionImpl.class);
	}

	public MasterSearchAction fetchByBk(String msaBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from master_search_action where msa_bk=? ", msaBk, MasterSearchActionImpl.class);
	}

	public MasterSearchAction fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from master_search_action where msa_code=? ", displayField, MasterSearchActionImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("msa_code", "master_search_action");
	}

}