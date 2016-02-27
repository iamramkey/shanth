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

import se.signa.signature.dio.ServerSettingsFetchInput;
import se.signa.signature.dio.ServerSettingsFetchResult;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.gen.dba.ServerSettingsDba;
import se.signa.signature.gen.dbo.ServerSettings;

@Path("/serversettingsfetch")
public class ServerSettingsFetchApi extends StigWebApi
{
	private static Logger logger = Logger.getLogger(ServerSettingsFetchApi.class);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult serverSettingsFetch(ServerSettingsFetchInput input)
	{
		input.validate();
		input.checkRules();

		ServerSettings serverSettings = ServerSettingsDba.getI().fetchBySesCode(input.sesCode);
		String notification = "Server Settings fetch is complete";
		logger.info(notification);
		return new ServerSettingsFetchResult(notification, serverSettings);
	}
}
