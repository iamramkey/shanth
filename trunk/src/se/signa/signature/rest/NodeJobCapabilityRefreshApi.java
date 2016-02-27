/**
 * Copyright SIGNA AB, STOCKHOLM, SWEDEN
 */
package se.signa.signature.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import se.signa.signature.common.Constants;
import se.signa.signature.dio.NodeInput;
import se.signa.signature.dio.StigWebResult;

@Path("/nodejobcapabilityrefresh")
public class NodeJobCapabilityRefreshApi extends NodeCommonApi
{

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult nodeJobCapabilityRefresh(NodeInput input)
	{
		return executeCommand(input, Constants.COMMANDTYPE_NODECAPABILITYREFRESH);
	}

}
