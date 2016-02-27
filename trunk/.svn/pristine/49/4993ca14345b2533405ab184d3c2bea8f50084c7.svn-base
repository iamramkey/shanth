/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.CommandTypeDbaImpl;
import se.signa.signature.dbo.CommandTypeImpl;
import se.signa.signature.gen.dbo.CommandType;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class CommandTypeDba extends SignatureDba<CommandType>
{
	private static CommandTypeDbaImpl INSTANCE;

	public static CommandTypeDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new CommandTypeDbaImpl();
		return INSTANCE;
	}

	public CommandTypeDba()
	{
		tableName = "command_type";
		tablePrefix = "ctp";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Ctp Id");
		columns.add("Ctp Code");
		columns.add("Ctp Package");
		columns.add("Ctp User Name");
		columns.add("Ctp Dttm");
		return columns;
	}

	@Override
	public CommandType createEmptyDbo()
	{
		return new CommandTypeImpl();
	}

	@Override
	public void checkDuplicates(CommandType dbo)
	{
		checkDuplicateGM(dbo.getCtpCode(), "ctp_code", dbo.getPk());
		checkDuplicateGM(dbo.getCtpBk(), "ctp_bk", dbo.getPk());
	}

	@Override
	public int create(CommandType commandType, int usrId)
	{
		return create(null, commandType, usrId);
	}

	public int create(Connection connection, CommandType commandType, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("ctp_id", "command_type");
		String bk = commandType.getDisplayField();

		String query = " insert into command_type values(?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, commandType.getCtpCode(), commandType.getCtpPackage(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(CommandType commandType, int usrId)
	{
		update(null, commandType, usrId);
	}

	public void update(Connection connection, CommandType commandType, int usrId)
	{
		String query = " update command_type set  ctp_code = ? , ctp_package = ? , ctp_modified_usr_id = ? , ctp_modified_dttm = ?  where ctp_id = ?  ";
		Object[] paramValues = new Object[] { commandType.getCtpCode(), commandType.getCtpPackage(), usrId, new DateTime(), commandType.getCtpId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into command_type values(?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { commandType.getCtpId(), commandType.getCtpCode(), commandType.getCtpPackage(), commandType.getCtpBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<CommandType> fetchAll()
	{
		String query = " select * from command_type ";
		return RefDBHelper.getDB().fetchList(query, CommandTypeImpl.class);
	}

	public List<CommandType> fetchAuditRowsByPk(int id)
	{
		String query = " select * from command_type where ctp_id=? order by ctp_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, CommandTypeImpl.class);
	}

	public CommandType fetchByPk(int ctpId)
	{
		return fetchByPk(null, ctpId);
	}

	public CommandType fetchByPk(Connection connection, int ctpId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from command_type where ctp_id=? ", ctpId, CommandTypeImpl.class);
	}

	public CommandType fetchByBk(String ctpBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from command_type where ctp_bk=? ", ctpBk, CommandTypeImpl.class);
	}

	public CommandType fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from command_type where ctp_code=? ", displayField, CommandTypeImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("ctp_code", "command_type");
	}

}