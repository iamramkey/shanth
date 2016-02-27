package se.signa.signature.rest;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.common.SignatureDbo;
import se.signa.signature.dio.MasterFetchInput;
import se.signa.signature.dio.MasterFetchResult;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.helpers.DbaInstanceHelper;

@Path("/masterfetch")
public class MasterFetchApi extends StigWebApi
{

	private static Logger logger = Logger.getLogger(MasterFetchApi.class);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult masterFetch(MasterFetchInput input)
	{
		input.validate();
		input.checkRules();

		SignatureDba dba = DbaInstanceHelper.getI().getDba(input.name);
		SignatureDbo dbo = dba.fetchByPk(input.id);
		Map<String, Object> data = dbo.populateTo();
		String notification = input.name + " row fetched for id : " + input.id;
		logger.info(notification);
		return new MasterFetchResult(notification, input.name, input.id, data);
	}

}
