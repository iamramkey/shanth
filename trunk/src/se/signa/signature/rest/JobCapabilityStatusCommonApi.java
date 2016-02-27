package se.signa.signature.rest;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import se.signa.signature.common.Constants;
import se.signa.signature.dbo.CommandImpl;
import se.signa.signature.dio.JobCapabilityInput;
import se.signa.signature.dio.PositiveResult;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.gen.dba.CommandDba;
import se.signa.signature.gen.dba.CommandTypeDba;
import se.signa.signature.gen.dba.JobCapabilityDba;
import se.signa.signature.gen.dbo.Command;
import se.signa.signature.gen.dbo.CommandType;
import se.signa.signature.gen.dbo.JobCapability;

public class JobCapabilityStatusCommonApi
{
	private static Logger logger = Logger.getLogger(JobCapabilityStopApi.class);

	protected StigWebResult jobCapabilityChangeStatus(JobCapabilityInput input, String jobCapabilityStatus)
	{
		input.validate();
		input.checkRules();

		JobCapability jobCapability = JobCapabilityDba.getI().fetchByPk(input.id);
		jobCapability.setJbcStatus(jobCapabilityStatus);
		JobCapabilityDba.getI().update(jobCapability, input.usrId);

		CommandType commandType = CommandTypeDba.getI().fetchByBk(Constants.COMMANDTYPE_NODECAPABILITYREFRESH);
		String commandName = jobCapability.getNod() + " " + commandType.getCtpCode() + " " + new DateTime();
		Command command = new CommandImpl(jobCapability.getNodId(), commandType.getCtpId(), commandName);
		CommandDba.getI().create(command, input.usrId);
		logger.debug("Created command entry");

		String notification = jobCapability.getJbt() + " in " + jobCapability.getNod() + " is now changed to " + jobCapabilityStatus;
		logger.info(notification);
		return new PositiveResult(notification);
	}
}
