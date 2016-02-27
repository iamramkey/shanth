package se.signa.signature.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import se.signa.signature.dio.DealListInput;
import se.signa.signature.dio.ListResult;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.helpers.RefDBHelper;

@Path("/deallist")
public class DealListApi extends StigWebApi
{
	private static Logger logger = Logger.getLogger(DealListApi.class);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult dealList(DealListInput input)
	{
		input.validate();
		input.checkRules();
		String whereClause = " acc_id in (select acc_id from customer_vendor where acc_company_name = \'" + input.accName + "\') order by DEL_FROM_DTTM desc";
		List<String> dealList = RefDBHelper.getDB().fetchStringList("del_name", "deal", whereClause);
		String notification = "Deal List is complete";
		logger.info(notification);
		return new ListResult(notification, dealList);
	}
}
