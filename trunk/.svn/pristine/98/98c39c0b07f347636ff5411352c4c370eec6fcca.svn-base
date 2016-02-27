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

@Path("/userpasswordreset")
public class UserPasswordResetApi
{

	private static Logger logger = Logger.getLogger(LogInApi.class);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult userPasswordReset(UserModifyInput input)
	{
		input.validate();
		input.checkRules();
		UserTbl userTbl = UserTblDba.getI().fetchByPk(input.id);
		userTbl.setUsrPassword(Constants.USER_DEFAULT_PASSWORD);
		UserTblDba.getI().update(userTbl, input.usrId);
		String notification = "User Password reset is complete for User Name : " + userTbl.getUsrName() + " with default password : welcome";
		logger.info(notification);
		return new PositiveResult(notification);
	}

}
