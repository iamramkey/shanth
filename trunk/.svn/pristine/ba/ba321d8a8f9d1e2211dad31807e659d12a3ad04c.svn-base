/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.ScriptTblDbaImpl;
import se.signa.signature.dbo.ScriptTblImpl;
import se.signa.signature.gen.dbo.ScriptTbl;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class ScriptTblDba extends SignatureDba<ScriptTbl>
{
	private static ScriptTblDbaImpl INSTANCE;

	public static ScriptTblDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new ScriptTblDbaImpl();
		return INSTANCE;
	}

	public ScriptTblDba()
	{
		tableName = "script_tbl";
		tablePrefix = "srt";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Srt Id");
		columns.add("Srt Fil Name");
		columns.add("Srt Desc");
		columns.add("Srt User Name");
		columns.add("Srt Dttm");
		return columns;
	}

	@Override
	public ScriptTbl createEmptyDbo()
	{
		return new ScriptTblImpl();
	}

	@Override
	public void checkDuplicates(ScriptTbl dbo)
	{
		checkDuplicateGM(dbo.getSrtFilName(), "srt_fil_name", dbo.getPk());
		checkDuplicateGM(dbo.getSrtBk(), "srt_bk", dbo.getPk());
	}

	@Override
	public int create(ScriptTbl scriptTbl, int usrId)
	{
		return create(null, scriptTbl, usrId);
	}

	public int create(Connection connection, ScriptTbl scriptTbl, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("srt_id", "script_tbl");
		String bk = scriptTbl.getDisplayField();

		String query = " insert into script_tbl values(?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, scriptTbl.getSrtFilName(), scriptTbl.getSrtDesc(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(ScriptTbl scriptTbl, int usrId)
	{
		update(null, scriptTbl, usrId);
	}

	public void update(Connection connection, ScriptTbl scriptTbl, int usrId)
	{
		String query = " update script_tbl set  srt_fil_name = ? , srt_desc = ? , srt_modified_usr_id = ? , srt_modified_dttm = ?  where srt_id = ?  ";
		Object[] paramValues = new Object[] { scriptTbl.getSrtFilName(), scriptTbl.getSrtDesc(), usrId, new DateTime(), scriptTbl.getSrtId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into script_tbl values(?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { scriptTbl.getSrtId(), scriptTbl.getSrtFilName(), scriptTbl.getSrtDesc(), scriptTbl.getSrtBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<ScriptTbl> fetchAll()
	{
		String query = " select * from script_tbl ";
		return RefDBHelper.getDB().fetchList(query, ScriptTblImpl.class);
	}

	public List<ScriptTbl> fetchAuditRowsByPk(int id)
	{
		String query = " select * from script_tbl where srt_id=? order by srt_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, ScriptTblImpl.class);
	}

	public ScriptTbl fetchByPk(int srtId)
	{
		return fetchByPk(null, srtId);
	}

	public ScriptTbl fetchByPk(Connection connection, int srtId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from script_tbl where srt_id=? ", srtId, ScriptTblImpl.class);
	}

	public ScriptTbl fetchByBk(String srtBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from script_tbl where srt_bk=? ", srtBk, ScriptTblImpl.class);
	}

	public ScriptTbl fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from script_tbl where srt_fil_name=? ", displayField, ScriptTblImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("srt_fil_name", "script_tbl");
	}

}