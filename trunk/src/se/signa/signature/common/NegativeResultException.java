package se.signa.signature.common;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import se.signa.signature.dio.NegativeResult;

public class NegativeResultException extends WebApplicationException
{
	private static final long serialVersionUID = 1L;

	public NegativeResultException(NegativeResult nr)
	{
		super(Response.status(200).entity(nr).type("application/json").build());
	}
}