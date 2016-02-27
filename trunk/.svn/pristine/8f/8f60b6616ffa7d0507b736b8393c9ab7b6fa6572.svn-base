package se.signa.signature.rest;

import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import se.signa.signature.common.SignatureException;
import se.signa.signature.dio.CDBTopUncommittedListResult;
import se.signa.signature.dio.CountryDashboardInput;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.helpers.DBHelper.ExecuteSelectStamentResult;
import se.signa.signature.helpers.RefDBHelper;
import se.signa.signature.helpers.UsageDBHelper;

@Path("/cdbtopuncommittedlist")
public class CDBTopUncommittedListApi extends StigWebApi
{
	private static Logger logger = Logger.getLogger(CDBTopUncommittedListApi.class);
	private static DecimalFormat dfr = new DecimalFormat("#.####");
	private static DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(Locale.US);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult cdbTopUncommittedList(CountryDashboardInput input)
	{
		input.validate();
		input.checkRules();

		dfr.setRoundingMode(RoundingMode.CEILING);
		DecimalFormatSymbols symbols = dfr.getDecimalFormatSymbols();

		symbols.setGroupingSeparator(' ');
		df.setDecimalFormatSymbols(symbols);

		List<TopUncommittedData> topDestinationsDataList = new ArrayList<TopUncommittedData>();
		getTopUncommitted(topDestinationsDataList, input.countryName);

		String notification = "Top Uncommitted List Rendered Successfully !!!";
		logger.info(notification);
		return new CDBTopUncommittedListResult(notification, topDestinationsDataList);
	}

	private void getTopUncommitted(List<TopUncommittedData> topDestinationsDataList, String country)
	{
		getTopUncommittedByVol(topDestinationsDataList, "I", country);
		getTopUncommittedByVol(topDestinationsDataList, "O", country);
	}

	private void getTopUncommittedByVol(List<TopUncommittedData> topDestinationsDataList, String direction, String country)
	{
		ExecuteSelectStamentResult result = null;
		try
		{
			//Use margin summary
			String query = getVolQuery(direction);
			result = UsageDBHelper.getDB().execute(query, new Object[] { country });

			int counter = 1;

			while (result.next())
			{

				if (counter > 10)
					break;

				TopUncommittedData topDestinationData = new TopUncommittedData();
				String data = result.getRs().getString("acc_company_name");
				String period = String.valueOf(df.format(result.getRs().getDouble("usage")));
				//String dealName = result.getRs().getString("del_name");
				//String accName = result.getRs().getString("acc_name");
				topDestinationData.data = data;
				topDestinationData.period = period;
				//topDestinationData.deal = dealName;
				//topDestinationData.account = accName;

				topDestinationData.color = "#2C3E50";
				topDestinationData.labelColor = "#67809F";

				if (direction.equalsIgnoreCase("I"))
				{
					topDestinationData.icon = "icon-arrow-right";
					topDestinationData.primaryFilter = "In";
				}
				else
				{
					topDestinationData.icon = "icon-arrow-left";
					topDestinationData.primaryFilter = "Out";
				}
				topDestinationsDataList.add(topDestinationData);

				counter++;
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
	}

	private String getVolQuery(String direction)
	{
		String query = "";
		String refName = RefDBHelper.getDB().getUserName();
		if (direction.equalsIgnoreCase("I"))
		{

			query = " select round(sum(IN_REV_BILLED_USAGE)/60.00) as usage , c1.acc_company_name acc_company_name from margin_summary m1  ";
			query = query + " left join " + refName + ".band b1 on m1.in_bnd_id=b1.bnd_id ";
			query = query + " left join " + refName + ".customer_vendor c1 on m1.in_acc_id=c1.acc_id  ";
			query = query + " where (m1.in_deal_id is not null and m1.in_deal_id = 0) and in_bnd_id in (select bnd_id from ";
			query = query + " " + refName + ".area_connection arc, " + refName + ".country con, " + refName + ".area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ";
			query = query + " arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?) and evt_dttm <=sysdate and evt_dttm >= (sysdate - 7) ";
			query = query + " group by c1.acc_company_name order by sum(IN_REV_BILLED_USAGE)/60.00 desc";

			logger.debug("Query for top destination in volume : " + query);
		}
		else if (direction.equalsIgnoreCase("O"))
		{
			query = " select round(sum(OUT_REV_BILLED_USAGE)/60.00) as usage, c1.acc_company_name acc_company_name from margin_summary m1 ";
			query = query + " left join " + refName + ".band b1 on m1.out_bnd_id=b1.bnd_id ";
			query = query + " left join " + refName + ".customer_vendor c1 on m1.out_acc_id=c1.acc_id ";
			query = query + " where (m1.out_deal_id is not null and m1.out_deal_id = 0) and out_bnd_id in (select bnd_id from ";
			query = query + " " + refName + ".area_connection arc, " + refName + ".country con, " + refName + ".area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ";
			query = query + " arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?) and evt_dttm <=sysdate and evt_dttm >= (sysdate - 7) ";
			query = query + " group by c1.acc_company_name order by sum(OUT_REV_BILLED_USAGE)/60.00 desc";

			logger.debug("Query for top destination out volume : " + query);
		}

		return query;
	}

	public class TopUncommittedData
	{
		public String icon;
		public String color;
		public String labelColor;
		public String data;
		public String period;
		public String primaryFilter;
		public String deal;
		public String account;
	}
}
