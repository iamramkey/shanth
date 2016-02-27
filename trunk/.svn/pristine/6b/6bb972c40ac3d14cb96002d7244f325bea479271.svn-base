package se.signa.signature.rest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import se.signa.signature.common.SignatureException;
import se.signa.signature.dio.ListResult;
import se.signa.signature.dio.StigWebInput;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.helpers.DBHelper.ExecuteSelectStamentResult;
import se.signa.signature.helpers.RefDBHelper;

@Path("/customervendorlist")
public class CustomerVendorListApi extends StigWebApi
{
	private static Logger logger = Logger.getLogger(CustomerVendorListApi.class);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult customerVendorList(StigWebInput input)
	{
		input.validate();
		input.checkRules();
		ExecuteSelectStamentResult result = null;
		List<String> customerVendorList = new ArrayList<String>();
		try
		{

			String query = " select distinct(acc_company_name) from customer_vendor acc,deal del where del.acc_id = acc.acc_id order by acc_company_name ";
			result = RefDBHelper.getDB().execute(query);
			while (result.next())
			{
				String customerVendor = result.getRs().getString("acc_company_name");
				customerVendorList.add(customerVendor);
			}
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
		finally
		{
			if (result != null)
				result.close();
		}
		String notification = "Customer Vendor List is complete";
		logger.info(notification);
		return new ListResult(notification, customerVendorList);
	}
}
