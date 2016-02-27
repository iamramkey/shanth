/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.CommandDbaImpl;
import se.signa.signature.dbo.CommandImpl;
import se.signa.signature.gen.dbo.Command;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class CommandDba extends SignatureDba<Command>
{
	private static CommandDbaImpl INSTANCE;

	public static CommandDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new CommandDbaImpl();
		return INSTANCE;
	}

	public CommandDba()
	{
		tableName = "command";
		tablePrefix = "cmd";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Cmd Id");
		columns.add("Nod Id");
		columns.add("Ctp Id");
		columns.add("Cmd Name");
		columns.add("Cmd Extra1");
		columns.add("Cmd Extra2");
		columns.add("Cmd Extra3");
		columns.add("Cmd User Name");
		columns.add("Cmd Dttm");
		return columns;
	}

	@Override
	public Command createEmptyDbo()
	{
		return new CommandImpl();
	}

	@Override
	public void checkDuplicates(Command dbo)
	{
		checkDuplicateGM(dbo.getCmdName(), "cmd_name", dbo.getPk());
		checkDuplicateGM(dbo.getCmdBk(), "cmd_bk", dbo.getPk());
	}

	@Override
	public int create(Command command, int usrId)
	{
		return create(null, command, usrId);
	}

	public int create(Connection connection, Command command, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("cmd_id", "command");
		String bk = command.getDisplayField();

		String query = " insert into command values(?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, command.getNodId(), command.getCtpId(), command.getCmdName(), command.getCmdExtra1(), command.getCmdExtra2(), command.getCmdExtra3(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(Command command, int usrId)
	{
		update(null, command, usrId);
	}

	public void update(Connection connection, Command command, int usrId)
	{
		String query = " update command set  nod_id = ? , ctp_id = ? , cmd_name = ? , cmd_extra1 = ? , cmd_extra2 = ? , cmd_extra3 = ? , cmd_modified_usr_id = ? , cmd_modified_dttm = ?  where cmd_id = ?  ";
		Object[] paramValues = new Object[] { command.getNodId(), command.getCtpId(), command.getCmdName(), command.getCmdExtra1(), command.getCmdExtra2(), command.getCmdExtra3(), usrId, new DateTime(), command.getCmdId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into command values(?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { command.getCmdId(), command.getNodId(), command.getCtpId(), command.getCmdName(), command.getCmdExtra1(), command.getCmdExtra2(), command.getCmdExtra3(), command.getCmdBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<Command> fetchAll()
	{
		String query = " select * from command ";
		return RefDBHelper.getDB().fetchList(query, CommandImpl.class);
	}

	public List<Command> fetchAuditRowsByPk(int id)
	{
		String query = " select * from command where cmd_id=? order by cmd_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, CommandImpl.class);
	}

	public Command fetchByPk(int cmdId)
	{
		return fetchByPk(null, cmdId);
	}

	public Command fetchByPk(Connection connection, int cmdId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from command where cmd_id=? ", cmdId, CommandImpl.class);
	}

	public Command fetchByBk(String cmdBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from command where cmd_bk=? ", cmdBk, CommandImpl.class);
	}

	public Command fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from command where cmd_name=? ", displayField, CommandImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("cmd_name", "command");
	}

}