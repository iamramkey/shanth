/**
 * Copyright SIGNA AB, STOCKHOLM, SWEDEN
 */
package se.signa.signature.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import se.signa.signature.common.Constants;
import se.signa.signature.dbo.UserPasswordImpl;
import se.signa.signature.dio.NegativeResult;
import se.signa.signature.dio.PositiveResult;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.dio.UserChangePasswordInput;
import se.signa.signature.gen.dba.UserPasswordDba;
import se.signa.signature.gen.dba.UserTblDba;
import se.signa.signature.gen.dbo.UserPassword;
import se.signa.signature.gen.dbo.UserTbl;

@Path("/userchangepassword")
public class UserChangePasswordApi extends StigWebApi
{
	private static Logger logger = Logger.getLogger(UserChangePasswordApi.class);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult userChangePassword(UserChangePasswordInput input)
	{
		input.validate();
		input.checkRules();

		UserTbl user = UserTblDba.getI().fetchByPk(input.usrId);
		if (!user.getUsrPassword().toString().equalsIgnoreCase(input.usrPassword))
			return new NegativeResult(Constants.ErrCode.INPUT_FIELD_INVALID, "Incorrect password for the User :" + user.getUsrName(), "usrPassword");
		logger.debug("User password is matched");

		user.setUsrPassword(input.usrNewPassword);
		UserTblDba.getI().update(user, input.usrId);

		UserPassword userPassword = new UserPasswordImpl(user);
		UserPasswordDba.getI().create(userPassword, input.usrId);

		String notification = "Password : " + user.getUsrDisplayName() + " updated successfully";
		logger.info(notification);
		return new PositiveResult(notification);
	}
}
