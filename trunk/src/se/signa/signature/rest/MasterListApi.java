package se.signa.signature.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.common.SignatureDbo;
import se.signa.signature.dio.MasterListInput;
import se.signa.signature.dio.MasterListResult;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.helpers.DbaInstanceHelper;

@Path("/masterlist")
public class MasterListApi
{
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult list(MasterListInput input)
	{
		input.validate();
		input.checkRules();

		String prefix = input.prefix;
		if (prefix.length() > 3)
			prefix = prefix.substring(prefix.length() - 3).toLowerCase();
		SignatureDba<SignatureDbo> dba = DbaInstanceHelper.getI().getDbaByPrefix(prefix);
		List<String> list = dba.fetchStringList();

		return new MasterListResult(input.prefix + " List completed with " + list.size() + " rows", list);
	}

}
