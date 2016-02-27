package se.signa.signature.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import se.signa.signature.common.Constants;
import se.signa.signature.dio.PositiveResult;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.dio.UserModifyInput;
import se.signa.signature.gen.dba.UserTblDba;
import se.signa.signature.gen.dbo.UserTbl;

@Path("/useractivate")
public class UserActivateApi
{
	private static Logger logger = Logger.getLogger(LogInApi.class);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult userActivate(UserModifyInput input)
	{
		input.validate();
		input.checkRules();
		UserTbl userTbl = UserTblDba.getI().fetchByPk(input.id);
		userTbl.setUsrStatus(Constants.USER_JUSTACTIVED_STATUS);
		UserTblDba.getI().update(userTbl, input.usrId);
		String notification = "User Status Updated to Active for User Name : " + userTbl.getUsrName();
		logger.info(notification);
		return new PositiveResult(notification);
	}
}
