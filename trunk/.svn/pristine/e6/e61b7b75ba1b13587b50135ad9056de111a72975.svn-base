package se.signa.signature.rest;

import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import se.signa.signature.common.Constants;
import se.signa.signature.common.SignatureException;
import se.signa.signature.dio.BDBMarginResult;
import se.signa.signature.dio.BusinessDashboardInput;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.helpers.DBHelper.ExecuteSelectStamentResult;
import se.signa.signature.helpers.RefDBHelper;
import se.signa.signature.helpers.UsageDBHelper;

@Path("/bdbmargin")
public class BDBMarginApi extends StigWebApi
{
	private static Logger logger = Logger.getLogger(BDBMarginApi.class);
	private static DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(Locale.US);
	private static DecimalFormat dfr = new DecimalFormat("#.####");

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult bdbMargin(BusinessDashboardInput input)
	{
		input.validate();
		input.checkRules();

		dfr.setRoundingMode(RoundingMode.CEILING);
		DecimalFormatSymbols symbols = dfr.getDecimalFormatSymbols();

		symbols.setGroupingSeparator(' ');
		df.setDecimalFormatSymbols(symbols);

		DateFormat dateFormat = new SimpleDateFormat(Constants.DASH_BOARD_DTTM_FORMAT);

		DateTime now = new DateTime();
		DateTime oneWeekBefore = now.minusWeeks(1);

		List<String> headers = new LinkedList<String>();
		headers.add("Deal");
		headers.add("Direction");
		headers.add("Total");
		while (oneWeekBefore.isBefore(now))
		{
			String date = dateFormat.format(now.toDate());
			headers.add(date);
			now = now.minusDays(1);
		}

		List<Margin> marginList = new LinkedList<Margin>();
		populateMarginList(marginList, "In");
		populateMarginList(marginList, "Out");

		//		List<Margin> marginList = new LinkedList<Margin>();
		/*Margin margin = new Margin("All deals", "In ", "1 145 281", "6 363", "5 123", "6 029", "4 715", "5 255", "6 538", "6 916");
		marginList.add(margin);
		Margin margin1 = new Margin("All deals ", "Out ", "923 826", "5 136", "4 553", "4 541", "4 759", "5 103", "5 350", "5 011");
		marginList.add(margin1);*/

		String notification = "Margin details Rendered Successfully !!!";
		logger.info(notification);
		return new BDBMarginResult(notification, headers, marginList);
	}

	private void populateMarginList(List<Margin> marginList, String direction)
	{
		ExecuteSelectStamentResult result = null;
		try
		{
			String refName = RefDBHelper.getDB().getUserName();
			String query = null;
			if (direction.equalsIgnoreCase("In"))
			{
				/*query = "select cur_name , sum(( IN_DEAL_RATE - (OUT_REV_AMT /(OUT_REV_BILLED_USAGE/60))) ";
				query = query + " * (IN_REV_BILLED_USAGE/60)) as margin from margin_summary mar, " + refName + ".currency cur ,  ";
				query = query + "" + refName + ".deal del where mar.in_deal_id = del.del_id and in_deal_id != 0 and out_deal_id != 0 and del.cur_id = cur.cur_id and evt_dttm <= sysdate and evt_dttm >= trunc(add_months(sysdate,-6),'MM') group by ";
				query = query + "in_deal_cur_id,cur_name";*/

				query = " select cur_name , round(sum(( IN_DEAL_RATE - (OUT_REV_AMT /(OUT_REV_BILLED_USAGE/60))) ";
				query = query + "  * (IN_REV_BILLED_USAGE/60))) as margin  from margin_summary mar, " + refName + ".currency cur ,  ";
				query = query + " " + refName + ".deal del where mar.in_deal_id = del.del_id and del.cur_id = cur.cur_id and evt_dttm <= sysdate  group by  ";
				query = query + " cur_name  ";

				logger.debug("Query for in margin list without date filter : " + query);
			}
			else
			{
				/*query = "select cur_name , sum(((IN_REV_AMT /(IN_REV_BILLED_USAGE)/60) - OUT_DEAL_RATE ) * (OUT_REV_BILLED_USAGE/60)) ";
				query = query + " as margin from margin_summary mar, " + refName + ".currency cur ,  ";
				query = query + "" + refName + ".deal del where mar.out_deal_id = del.del_id and in_deal_id != 0 and out_deal_id != 0 and del.cur_id = cur.cur_id and evt_dttm <= sysdate and evt_dttm >= trunc(add_months(sysdate,-6),'MM') group by out_deal_cur_id,cur_name";
				*/

				query = "select round(sum(((IN_REV_AMT /(IN_REV_BILLED_USAGE)/60) - OUT_DEAL_RATE ) * (OUT_REV_BILLED_USAGE/60))) ";
				query = query + " as margin from margin_summary where out_deal_id is not null and evt_dttm <= sysdate  ";

				logger.debug("Query for out margin list without date filter : " + query);

			}

			result = UsageDBHelper.getDB().execute(query);
			while (result.next())
			{
				Double[] dayMargin = new Double[7];
				//				String curName = result.getRs().getString("cur_name");
				Double margin = result.getRs().getDouble("margin");
				DateTime now = new DateTime();
				DateTime oneWeekBefore = now.minusWeeks(1);
				int i = 0;
				while (oneWeekBefore.isBefore(now))
				{
					dayMargin[i] = getMarginOnDate(direction, now.withTimeAtStartOfDay(), now.withTimeAtStartOfDay().plusDays(1).minusSeconds(1));
					now = now.minusDays(1);
					i++;
				}
				marginList.add(new Margin("All deals", direction, margin, dayMargin[0], dayMargin[1], dayMargin[2], dayMargin[3], dayMargin[4], dayMargin[5], dayMargin[6]));
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

	private Double getMarginOnDate(String direction, DateTime fromDttm, DateTime toDttm)
	{
		String refName = RefDBHelper.getDB().getUserName();
		String query = null;
		if (direction.equalsIgnoreCase("In"))
		{
			/*query = "select cur_name , sum(( IN_DEAL_RATE - (OUT_REV_AMT /(OUT_REV_BILLED_USAGE/60))) ";
			query = query + " * (IN_REV_BILLED_USAGE/60)) as margin  from margin_summary mar, " + refName + ".currency cur ,  ";
			query = query + "" + refName + ".deal del where mar.in_deal_id = del.del_id and in_deal_id != 0 and out_deal_id != 0 and del.cur_id = cur.cur_id and evt_dttm >= ? and evt_dttm <= ? group by ";
			query = query + "in_deal_cur_id,cur_name";*/

			query = " select cur_name , round(sum(( IN_DEAL_RATE - (OUT_REV_AMT /(OUT_REV_BILLED_USAGE/60))) ";
			query = query + "  * (IN_REV_BILLED_USAGE/60))) as margin  from margin_summary mar, " + refName + ".currency cur ,  ";
			query = query + " " + refName + ".deal del where mar.in_deal_id = del.del_id and del.cur_id = cur.cur_id and evt_dttm >= ? and evt_dttm <= ? ";
			query = query + " group by cur_name  ";

			logger.debug("Query for in margin list with date filter : " + query);
		}
		else
		{
			/*query = "select cur_name , sum(((IN_REV_AMT /(IN_REV_BILLED_USAGE)/60) - OUT_DEAL_RATE ) * (OUT_REV_BILLED_USAGE/60)) ";
			query = query + " as margin from margin_summary mar, " + refName + ".currency cur ,  ";
			query = query + "" + refName + ".deal del where mar.out_deal_id = del.del_id and in_deal_id != 0 and out_deal_id != 0  and del.cur_id = cur.cur_id and evt_dttm >= ? and evt_dttm <= ? group by out_deal_cur_id,cur_name";
			*/

			query = "select round(sum(((IN_REV_AMT /(IN_REV_BILLED_USAGE)/60) - OUT_DEAL_RATE ) * (OUT_REV_BILLED_USAGE/60))) ";
			query = query + " as margin from margin_summary where out_deal_id is not null and evt_dttm >= ? and evt_dttm <= ? ";

			logger.debug("Query for out margin list with date filter : " + query);
		}

		ExecuteSelectStamentResult result = null;
		Double dayMargin = 0d;
		try
		{
			result = UsageDBHelper.getDB().execute(query, new Object[] { fromDttm, toDttm });
			if (result.next())
			{
				dayMargin = result.getRs().getDouble("margin");
				//				String curName = result.getRs().getString("cur_name");
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
		return dayMargin;
	}

	public class Margin
	{
		public String deal;
		public String direction;
		public String total = "";
		public String date1 = "";
		public String date2 = "";
		public String date3 = "";
		public String date4 = "";
		public String date5 = "";
		public String date6 = "";
		public String date7 = "";

		public Margin(String deal, String direction, Double total, Double date1, Double date2, Double date3, Double date4, Double date5, Double date6, Double date7)
		{
			this.deal = deal;
			this.direction = direction;
			if (total != null)
				this.total = String.valueOf(df.format(total));
			if (date1 != null)
				this.date1 = String.valueOf(df.format(date1));
			if (date2 != null)
				this.date2 = String.valueOf(df.format(date2));
			if (date3 != null)
				this.date3 = String.valueOf(df.format(date3));
			if (date4 != null)
				this.date4 = String.valueOf(df.format(date4));
			if (date5 != null)
				this.date5 = String.valueOf(df.format(date5));
			if (date6 != null)
				this.date6 = String.valueOf(df.format(date6));
			if (date7 != null)
				this.date7 = String.valueOf(df.format(date7));
		}
	}
}
