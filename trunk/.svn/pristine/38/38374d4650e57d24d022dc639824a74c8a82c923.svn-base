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
import se.signa.signature.dio.PasswordSettingsFetchInput;
import se.signa.signature.dio.PasswordSettingsFetchResult;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.gen.dba.UserPasswordSettingsDba;
import se.signa.signature.gen.dbo.UserPasswordSettings;

@Path("/passwordsettingsfetch")
public class PasswordSettingsFetchApi extends StigWebApi
{
	private static Logger logger = Logger.getLogger(PasswordSettingsFetchApi.class);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult passwordSettingsFetch(PasswordSettingsFetchInput input)
	{
		input.validate();
		input.checkRules();
		UserPasswordSettings userPasswordSettings = UserPasswordSettingsDba.getI().fetchByDisplayField(Constants.STRONG_PASSWORD);

		String notification = "Password Settings fetch is complete";
		logger.info(notification);
		return new PasswordSettingsFetchResult(notification, userPasswordSettings);
	}
}
