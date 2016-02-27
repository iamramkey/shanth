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
import se.signa.signature.dio.JobCapabilityInput;
import se.signa.signature.dio.StigWebResult;

@Path("/jobcapabilitystart")
public class JobCapabilityStartApi extends JobCapabilityStatusCommonApi
{

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult jobCapabilityStart(JobCapabilityInput input)
	{
		return jobCapabilityChangeStatus(input, Constants.JOBTYPESTATUS_RUNNING);
	}
}
