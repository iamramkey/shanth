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
import se.signa.signature.dio.BDBTopDestinationsListResult;
import se.signa.signature.dio.BusinessDashboardInput;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.helpers.DBHelper.ExecuteSelectStamentResult;
import se.signa.signature.helpers.RefDBHelper;
import se.signa.signature.helpers.UsageDBHelper;

@Path("/bdbtopdestinationslist")
public class BDBTopDestinationsListApi extends StigWebApi
{
	private static Logger logger = Logger.getLogger(BDBTopDestinationsListApi.class);
	private static DecimalFormat dfr = new DecimalFormat("#.####");
	private static DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(Locale.US);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult ddbTopDestinationsList(BusinessDashboardInput input)
	{
		input.validate();
		input.checkRules();

		dfr.setRoundingMode(RoundingMode.CEILING);
		DecimalFormatSymbols symbols = dfr.getDecimalFormatSymbols();

		symbols.setGroupingSeparator(' ');
		df.setDecimalFormatSymbols(symbols);

		List<TopDestinationsData> topDestinationsDataList = new ArrayList<TopDestinationsData>();
		getTopDestinations(topDestinationsDataList);

		String notification = "Top Destinations List Rendered Successfully !!!";
		logger.info(notification);
		return new BDBTopDestinationsListResult(notification, topDestinationsDataList);
	}

	private void getTopDestinations(List<TopDestinationsData> topDestinationsDataList)
	{
		getTopDestinationByVol(topDestinationsDataList, "I");
		getTopDestinationByVol(topDestinationsDataList, "O");
		getTopDestinationByMar(topDestinationsDataList, "I");
		getTopDestinationByMar(topDestinationsDataList, "O");
		getTopDestinationByRev(topDestinationsDataList, "I");
		getTopDestinationByRev(topDestinationsDataList, "O");

	}

	private void getTopDestinationByRev(List<TopDestinationsData> topDestinationsDataList, String direction)
	{
		ExecuteSelectStamentResult result = null;
		try
		{
			//Use margin summary
			String query = getRevQuery(direction);

			int counter = 1;

			result = UsageDBHelper.getDB().execute(query, new Object[] {});
			while (result.next())
			{
				if (counter > 10)
					break;

				TopDestinationsData topDestinationData = new TopDestinationsData();
				String data = result.getRs().getString("bnd_name");
				String period = String.valueOf(df.format(result.getRs().getDouble("amount")));
				String dealName = result.getRs().getString("del_name");
				String accName = result.getRs().getString("acc_name");
				topDestinationData.data = data;
				topDestinationData.period = period;
				topDestinationData.color = "#2C3E50";
				topDestinationData.labelColor = "#67809F";
				topDestinationData.deal = dealName;
				topDestinationData.account = accName;

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
				topDestinationData.secondaryFilter = "REV";
				topDestinationsDataList.add(topDestinationData);
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

	private String getRevQuery(String direction)
	{
		String query = "";
		String refName = RefDBHelper.getDB().getUserName();
		if (direction.equalsIgnoreCase("I"))
		{
			query = " select round(sum(in_dealamt)) as amount, bnd_name,d1.del_name as del_name , c1.acc_company_name as acc_name from margin_summary m1 ";
			query = query + " left join " + refName + ".band b1 on m1.in_bnd_id=b1.bnd_id ";
			query = query + " left join " + refName + ".deal d1 on m1.in_deal_id=d1.del_id ";
			query = query + " left join " + refName + ".customer_vendor c1 on d1.acc_id=c1.acc_id ";
			query = query + " where (m1.in_deal_id is not null and m1.in_deal_id!=0) and evt_dttm <=sysdate and evt_dttm >= (sysdate - 7) ";
			query = query + " group by bnd_name,d1.del_name,c1.acc_company_name  order by sum(in_dealamt) desc";
		}
		else if (direction.equalsIgnoreCase("O"))
		{
			query = " select round(sum(out_dealamt)) as amount, bnd_name,d1.del_name as del_name , c1.acc_company_name as acc_name from margin_summary m1 ";
			query = query + " left join " + refName + ".band b1 on m1.out_bnd_id=b1.bnd_id ";
			query = query + " left join " + refName + ".deal d1 on m1.out_deal_id=d1.del_id ";
			query = query + " left join " + refName + ".customer_vendor c1 on d1.acc_id=c1.acc_id ";
			query = query + " where (m1.out_deal_id is not null and m1.out_deal_id!=0) and evt_dttm <=sysdate and evt_dttm >= (sysdate - 7)  ";
			query = query + " group by bnd_name,d1.del_name,c1.acc_company_name  order by sum(out_dealamt) desc";
		}

		return query;
	}

	private void getTopDestinationByMar(List<TopDestinationsData> topDestinationsDataList, String direction)
	{
		ExecuteSelectStamentResult result = null;
		try
		{
			//Use margin summary
			String query = getMarQuery(direction);

			int counter = 1;

			result = UsageDBHelper.getDB().execute(query, new Object[] {});
			while (result.next())
			{
				if (counter > 10)
					break;

				TopDestinationsData topDestinationData = new TopDestinationsData();
				String data = result.getRs().getString("bnd_name");
				String period = String.valueOf(df.format(result.getRs().getDouble("margin")));
				String dealName = result.getRs().getString("del_name");
				String accName = result.getRs().getString("acc_name");
				topDestinationData.data = data;
				topDestinationData.period = period;
				topDestinationData.color = "#2C3E50";
				topDestinationData.labelColor = "#67809F";
				topDestinationData.deal = dealName;
				topDestinationData.account = accName;

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
				topDestinationData.secondaryFilter = "MAR";
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

	private String getMarQuery(String direction)
	{
		String query = "";
		String refName = RefDBHelper.getDB().getUserName();
		if (direction.equalsIgnoreCase("I"))
		{
			query = " select round(sum(( IN_DEAL_RATE - (OUT_REV_AMT /(OUT_REV_BILLED_USAGE/60))) ";
			query = query + " * (IN_REV_BILLED_USAGE/60))) as margin, bnd_name,d1.del_name as del_name , c1.acc_company_name as acc_name from margin_summary m1 ";
			query = query + " left join " + refName + ".band b1 on m1.in_bnd_id=b1.bnd_id ";
			query = query + " left join " + refName + ".deal d1 on m1.in_deal_id=d1.del_id ";
			query = query + " left join " + refName + ".customer_vendor c1 on d1.acc_id=c1.acc_id ";
			query = query + " where (m1.in_deal_id is not null and m1.in_deal_id!=0) and evt_dttm <=sysdate and evt_dttm >= (sysdate - 7) ";
			query = query + " group by bnd_name,d1.del_name,c1.acc_company_name  order by sum(( IN_DEAL_RATE - (OUT_REV_AMT /(OUT_REV_BILLED_USAGE/60))) ";
			query = query + " * (IN_REV_BILLED_USAGE/60)) desc";
		}
		else if (direction.equalsIgnoreCase("O"))
		{
			query = " select round(sum(((IN_REV_AMT /(IN_REV_BILLED_USAGE)/60) - OUT_DEAL_RATE ) * (OUT_REV_BILLED_USAGE/60))) as margin, bnd_name,d1.del_name as del_name , c1.acc_company_name as acc_name from margin_summary m1 ";
			query = query + " left join " + refName + ".band b1 on m1.out_bnd_id=b1.bnd_id ";
			query = query + " left join " + refName + ".deal d1 on m1.out_deal_id=d1.del_id ";
			query = query + " left join " + refName + ".customer_vendor c1 on d1.acc_id=c1.acc_id ";
			query = query + " where (m1.out_deal_id is not null and m1.out_deal_id!=0) and evt_dttm <=sysdate and evt_dttm >= (sysdate - 7) ";
			query = query + " group by bnd_name,d1.del_name,c1.acc_company_name  order by sum(((IN_REV_AMT /(IN_REV_BILLED_USAGE)/60) - OUT_DEAL_RATE ) * (OUT_REV_BILLED_USAGE/60)) desc";
		}

		return query;
	}

	private void getTopDestinationByVol(List<TopDestinationsData> topDestinationsDataList, String direction)
	{
		ExecuteSelectStamentResult result = null;
		try
		{
			//Use margin summary
			String query = getVolQuery(direction);
			result = UsageDBHelper.getDB().execute(query, new Object[] {});

			int counter = 1;

			while (result.next())
			{

				if (counter > 10)
					break;

				TopDestinationsData topDestinationData = new TopDestinationsData();
				String data = result.getRs().getString("bnd_name");
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
				topDestinationData.secondaryFilter = "VOL";
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

			query = " select round(sum(IN_REV_BILLED_USAGE)/60.00) as usage , bnd_name from margin_summary m1  ";
			query = query + " left join " + refName + ".band b1 on m1.in_bnd_id=b1.bnd_id ";
			query = query + " left join " + refName + ".deal d1 on m1.in_deal_id=d1.del_id ";
			query = query + " left join " + refName + ".customer_vendor c1 on d1.acc_id=c1.acc_id ";
			query = query + " where (m1.in_deal_id is not null and m1.in_deal_id!=0) and evt_dttm <=sysdate and evt_dttm >= (sysdate - 7) ";
			query = query + " group by bnd_name order by sum(IN_REV_BILLED_USAGE)/60.00 desc";

			logger.debug("Query for top destination in volume : " + query);
		}
		else if (direction.equalsIgnoreCase("O"))
		{
			query = " select round(sum(OUT_REV_BILLED_USAGE)/60.00) as usage, bnd_name  from margin_summary m1 ";
			query = query + " left join " + refName + ".band b1 on m1.out_bnd_id=b1.bnd_id ";
			query = query + " left join " + refName + ".deal d1 on m1.out_deal_id=d1.del_id ";
			query = query + " left join " + refName + ".customer_vendor c1 on d1.acc_id=c1.acc_id ";
			query = query + " where (m1.out_deal_id is not null and m1.out_deal_id!=0) and evt_dttm <=sysdate and evt_dttm >= (sysdate - 7) ";
			query = query + " group by bnd_name order by sum(OUT_REV_BILLED_USAGE)/60.00 desc";

			logger.debug("Query for top destination out volume : " + query);
		}

		return query;
	}

	public class TopDestinationsData
	{
		public String icon;
		public String color;
		public String labelColor;
		public String data;
		public String period;
		public String primaryFilter;
		public String secondaryFilter;
		public String deal;
		public String account;
	}
}
