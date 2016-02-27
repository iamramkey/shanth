package se.signa.signature.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.common.SignatureDbo;
import se.signa.signature.dio.MasterSaveInput;
import se.signa.signature.dio.PositiveResult;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.helpers.DbaInstanceHelper;

@Path("/mastersave")
public class MasterSaveApi extends StigWebApi
{

	private static Logger logger = Logger.getLogger(MasterSaveApi.class);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult save(MasterSaveInput input)
	{
		logger.info("master save api starting for :" + input.name);
		input.validate();
		input.checkRules();

		SignatureDba dba = DbaInstanceHelper.getI().getDba(input.name);
		if (input.id > 0)
		{
			SignatureDbo dbo = dba.fetchByPk(input.id);
			dbo.populateFrom(input);
			dba.checkDuplicates(dbo);
			dba.update(dbo, input.usrId);
			String notification = input.name + " row of value : " + dbo.getDisplayField() + " was updated succesfully !";
			logger.info(notification);
			return new PositiveResult(notification);
		}

		SignatureDbo dbo = dba.createEmptyDbo();
		dbo.populateFrom(input);
		dba.checkDuplicates(dbo);
		dba.create(dbo, input.usrId);
		String notification = input.name + " row of value : " + dbo.getDisplayField() + "  was inserted succesfully ! ";
		logger.info(notification);
		return new PositiveResult(notification);
	}
}