package se.signa.signature.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import se.signa.signature.dio.PositiveResult;
import se.signa.signature.dio.StigWebInput;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.gen.dba.UserLoginDba;
import se.signa.signature.gen.dba.UserSessionDba;
import se.signa.signature.gen.dbo.UserSession;

@Path("/logout")
public class LogOutApi extends StigWebApi
{
	private static Logger logger = Logger.getLogger(LogOutApi.class);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult logOut(StigWebInput input)
	{
		input.validate();
		input.checkRules();

		UserSession userSession = UserSessionDba.getI().getSessionUsingByCode(input.ussSessionCode);
		if (userSession != null)
		{
			UserLoginDba.getI().updateLogOutTimeForUslId(userSession.getUslId());
			UserSessionDba.getI().deleteByUssId(input.usrId);
		}

		String notif = "User with id " + input.usrId + " are successfully logged out";
		logger.info(notif);
		return new PositiveResult(notif);
	}
}
