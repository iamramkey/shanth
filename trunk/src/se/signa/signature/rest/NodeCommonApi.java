/**
 * Copyright SIGNA AB, STOCKHOLM, SWEDEN
 */
package se.signa.signature.rest;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import se.signa.signature.dbo.CommandImpl;
import se.signa.signature.dio.NodeInput;
import se.signa.signature.dio.PositiveResult;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.gen.dba.CommandDba;
import se.signa.signature.gen.dba.CommandTypeDba;
import se.signa.signature.gen.dba.NodeDba;
import se.signa.signature.gen.dbo.Command;
import se.signa.signature.gen.dbo.CommandType;
import se.signa.signature.gen.dbo.Node;

public class NodeCommonApi extends StigWebApi
{

	private static Logger logger = Logger.getLogger(NodeCommonApi.class);

	public StigWebResult executeCommand(NodeInput input, String commandTypeBk)
	{
		input.validate();
		input.checkRules();

		Node node = NodeDba.getI().fetchByPk(input.id);
		CommandType commandType = CommandTypeDba.getI().fetchByBk(commandTypeBk);
		String commandName = node.getNodName() + " " + commandType.getCtpCode() + " " + new DateTime();
		Command command = new CommandImpl(input.id, commandType.getCtpId(), commandName);
		CommandDba.getI().create(command, input.usrId);
		logger.debug("Created command entry");

		String notification = "Node " + commandTypeBk + " is successful for : " + node.getNodName();
		logger.info(notification);
		return new PositiveResult(notification);
	}
}
