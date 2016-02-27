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
import se.signa.signature.dio.DDBMarginDetailsResult;
import se.signa.signature.dio.DashboardInput;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.gen.dba.DealDba;
import se.signa.signature.gen.dbo.Deal;
import se.signa.signature.helpers.DBHelper.ExecuteSelectStamentResult;
import se.signa.signature.helpers.RefDBHelper;
import se.signa.signature.helpers.UsageDBHelper;

@Path("/ddbmargindetails")
public class DDBMarginDetailsApi extends StigWebApi
{
	private static Logger logger = Logger.getLogger(DDBMarginDetailsApi.class);
	private static DecimalFormat dfr = new DecimalFormat("#.####");
	private static DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(Locale.US);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult ddbMarginDetails(DashboardInput input)
	{
		input.validate();
		input.checkRules();

		dfr.setRoundingMode(RoundingMode.CEILING);
		DecimalFormatSymbols symbols = dfr.getDecimalFormatSymbols();

		symbols.setGroupingSeparator(' ');
		df.setDecimalFormatSymbols(symbols);

		Deal deal = DealDba.getI().fetchBydelName(input.deal);
		DateFormat dateFormat = new SimpleDateFormat(Constants.DASH_BOARD_DTTM_FORMAT);

		String carrier = getCarrierName(deal);
		int dealId = deal.getDelId();
		String startDate = dateFormat.format(deal.getDelFromDttm().toDate());
		String endDate = dateFormat.format(deal.getDelToDttm().toDate());
		Integer gracePrd = deal.getDelGracePeriodDays();
		if (gracePrd == null)
			gracePrd = 0;
		String gracePeriod = gracePrd + " days";
		String currency = "INR";

		DateTime now = new DateTime();
		DateTime oneWeekBefore = now.minusWeeks(1);

		List<String> headers = new LinkedList<String>();
		headers.add("Destination Group");
		headers.add("Direction");
		headers.add("Total");
		while (oneWeekBefore.isBefore(now))
		{
			String date = dateFormat.format(now.toDate());
			headers.add(date);
			now = now.minusDays(1);
		}

		List<MarginDetails> marginDetailsList = new LinkedList<MarginDetails>();
		populateMarginDetails(marginDetailsList, deal, "In");
		populateMarginDetails(marginDetailsList, deal, "Out");

		String notification = "Margin Details Rendered Successfully !!!";
		logger.info(notification);
		return new DDBMarginDetailsResult(notification, carrier, dealId, currency, startDate, endDate, gracePeriod, headers, marginDetailsList);
	}

	private String getCarrierName(Deal deal)
	{
		ExecuteSelectStamentResult result = null;
		String carrierName = null;
		try
		{
			String query = "select acc_company_name from customer_vendor where acc_id = ?";

			result = RefDBHelper.getDB().execute(query, deal.getAccId());
			while (result.next())
			{
				carrierName = result.getRs().getString("acc_company_name");
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
		return carrierName;
	}

	private void populateMarginDetails(List<MarginDetails> marginDetailsList, Deal deal, String direction)
	{
		getBandGroupMarginDetails(marginDetailsList, deal, direction);
	}

	private void getBandMarginDetails(List<MarginDetails> marginDetailsList, int dbgId, Deal deal, String direction)
	{
		ExecuteSelectStamentResult result = null;
		try
		{
			String query = null;
			if (direction.equalsIgnoreCase("In"))
			{
				query = "select in_bnd_id as bnd_id, round(sum(( IN_DEAL_RATE - (OUT_REV_AMT /(OUT_REV_BILLED_USAGE/60))) * (IN_REV_BILLED_USAGE/60))) as margin from margin_summary ";
				query = query + " where in_deal_id = ? and in_dbg_id = ? group by in_bnd_id order by in_bnd_id";

				logger.debug("Query for getting in band margin details : " + query);
			}
			else
			{
				query = "select out_bnd_id as bnd_id , round(sum(((IN_REV_AMT /(IN_REV_BILLED_USAGE/60)) - OUT_DEAL_RATE ) * (OUT_REV_BILLED_USAGE/60))) as margin from margin_summary ";
				query = query + " where out_deal_id = ? and out_dbg_id = ? group by out_bnd_id order by out_bnd_id";

				logger.debug("Query for getting out band margin details : " + query);
			}

			result = UsageDBHelper.getDB().execute(query, new Object[] { deal.getDelId(), dbgId });
			while (result.next())
			{

				Double[] dayMargin = new Double[7];
				int bndId = result.getRs().getInt("bnd_id");

				String bndName = getBndName(bndId);
				Double margin = result.getRs().getDouble("margin");

				logger.info("bnd_id : " + bndId);
				logger.info("bndName : " + bndName);
				logger.info("margin : " + margin);

				DateTime now = new DateTime();
				DateTime oneWeekBefore = now.minusWeeks(1);
				int i = 0;
				while (oneWeekBefore.isBefore(now))
				{
					dayMargin[i] = getBandMarginOnDate(deal, direction, bndId, now.withTimeAtStartOfDay(), now.withTimeAtStartOfDay().plusDays(1).minusSeconds(1));
					now = now.minusDays(1);
					i++;
				}
				marginDetailsList.add(new MarginDetails(bndId, bndName, "", margin, dayMargin[0], dayMargin[1], dayMargin[2], dayMargin[3], dayMargin[4], dayMargin[5], dayMargin[6]));
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

	private Double getBandMarginOnDate(Deal deal, String direction, int bndId, DateTime fromDttm, DateTime toDttm)
	{
		String query = null;
		if (direction.equalsIgnoreCase("In"))
		{
			query = "select round(sum(( IN_DEAL_RATE - (OUT_REV_AMT /(OUT_REV_BILLED_USAGE/60))) * (IN_REV_BILLED_USAGE/60))) as margin from margin_summary ";
			query = query + " where in_bnd_id = ? and in_deal_id = ? and evt_dttm >= ? and evt_dttm <= ? ";

			logger.debug("Query for getting in band margin with date filter : " + query);
		}
		else
		{
			query = "select  round(sum(((IN_REV_AMT /(IN_REV_BILLED_USAGE/60)) - OUT_DEAL_RATE ) * (OUT_REV_BILLED_USAGE/60))) as margin from margin_summary ";
			query = query + " where out_bnd_id = ? and out_deal_id = ? and evt_dttm >= ? and evt_dttm <= ? ";

			logger.debug("Query for getting out band margin with date filter : " + query);
		}
		ExecuteSelectStamentResult result = null;
		Double dayMargin = 0d;
		try
		{
			result = UsageDBHelper.getDB().execute(query, new Object[] { bndId, deal.getDelId(), fromDttm, toDttm });
			if (result.next())
			{
				dayMargin = result.getRs().getDouble("margin");
			}

			logger.info("bndId : " + bndId);
			logger.info("dayMargin : " + dayMargin);
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

	private void getBandGroupMarginDetails(List<MarginDetails> marginDetails, Deal deal, String direction)
	{
		ExecuteSelectStamentResult result = null;
		try
		{
			String query = null;
			if (direction.equalsIgnoreCase("In"))
			{
				query = "select in_dbg_id as dbg_id, round(sum(( IN_DEAL_RATE - (OUT_REV_AMT /(OUT_REV_BILLED_USAGE/60))) * (IN_REV_BILLED_USAGE/60))) as margin from margin_summary ";
				query = query + " where in_deal_id = ? and evt_dttm <= sysdate group by in_dbg_id order by in_dbg_id";

				logger.debug("Query for getting in band group margin: " + query);
			}
			else
			{
				query = "select out_dbg_id as dbg_id , round(sum(((IN_REV_AMT /(IN_REV_BILLED_USAGE/60)) - OUT_DEAL_RATE ) * (OUT_REV_BILLED_USAGE/60))) as margin from margin_summary ";
				query = query + " where out_deal_id = ? and evt_dttm <= sysdate group by out_dbg_id order by out_dbg_id";

				logger.debug("Query for getting out band group margin : " + query);
			}

			result = UsageDBHelper.getDB().execute(query, new Object[] { deal.getDelId() });
			while (result.next())
			{
				Double[] dayMargin = new Double[7];
				Integer dbgId = result.getRs().getInt("dbg_id");
				if (dbgId != null && dbgId != 0)
				{
					String dbgName = getDbgName(dbgId);
					Double margin = result.getRs().getDouble("margin");
					DateTime now = new DateTime();
					DateTime oneWeekBefore = now.minusWeeks(1);
					int i = 0;
					while (oneWeekBefore.isBefore(now))
					{
						dayMargin[i] = getMarginOnDate(deal, direction, dbgId, now.withTimeAtStartOfDay(), now.withTimeAtStartOfDay().plusDays(1).minusSeconds(1));
						now = now.minusDays(1);
						i++;
					}
					MarginDetails marginDetail = new MarginDetails(dbgId, dbgName, direction, margin, dayMargin[0], dayMargin[1], dayMargin[2], dayMargin[3], dayMargin[4], dayMargin[5], dayMargin[6]);
					List<MarginDetails> innerMarginDetailsList = new LinkedList<MarginDetails>();
					getBandMarginDetails(innerMarginDetailsList, marginDetail.id, deal, direction);
					marginDetail.marginDetailsList = innerMarginDetailsList;

					marginDetails.add(marginDetail);
				}
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

	private Double getMarginOnDate(Deal deal, String direction, int dbgId, DateTime fromDttm, DateTime toDttm)
	{
		String query = null;
		if (direction.equalsIgnoreCase("In"))
		{
			query = "select round(sum(( IN_DEAL_RATE - (OUT_REV_AMT /(OUT_REV_BILLED_USAGE/60))) * (IN_REV_BILLED_USAGE/60))) as margin from margin_summary ";
			query = query + " where in_dbg_id = ? and in_deal_id = ? and evt_dttm >= ? and evt_dttm <= ? ";

			logger.debug("Query for getting in band group margin with date filter : " + query);
		}
		else
		{
			query = "select  round(sum(((IN_REV_AMT /(IN_REV_BILLED_USAGE/60)) - OUT_DEAL_RATE ) * (OUT_REV_BILLED_USAGE/60))) as margin from margin_summary ";
			query = query + " where out_dbg_id = ? and out_deal_id = ? and evt_dttm >= ? and evt_dttm <= ? ";

			logger.debug("Query for getting out band group margin with date filter : " + query);
		}
		ExecuteSelectStamentResult result = null;
		Double dayMargin = 0d;
		try
		{
			result = UsageDBHelper.getDB().execute(query, new Object[] { dbgId, deal.getDelId(), fromDttm, toDttm });
			if (result.next())
			{
				dayMargin = result.getRs().getDouble("margin");
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

	private String getDbgName(int dbgId)
	{
		String query = "select dbg_name from deal_band_group where dbg_id = ? ";
		ExecuteSelectStamentResult result = null;
		String dbgName = null;
		try
		{
			result = RefDBHelper.getDB().execute(query, dbgId);
			if (result.next())
			{
				dbgName = result.getRs().getString("dbg_name");
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
		return dbgName;
	}

	private String getBndName(int bndId)
	{
		String query = "select bnd_name from band where bnd_id = ? ";
		ExecuteSelectStamentResult result = null;
		String bndName = null;
		try
		{
			result = RefDBHelper.getDB().execute(query, bndId);
			if (result.next())
			{
				bndName = result.getRs().getString("bnd_name");
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
		return bndName;
	}

	public class MarginDetails
	{
		public int id;
		public String destinationGroup;
		public String direction;
		public String total = "";
		public String date1 = "";
		public String date2 = "";
		public String date3 = "";
		public String date4 = "";
		public String date5 = "";
		public String date6 = "";
		public String date7 = "";

		public MarginDetails(int id, String destinationGroup, String direction, Double total, Double date1, Double date2, Double date3, Double date4, Double date5, Double date6, Double date7)
		{
			this.id = id;
			this.destinationGroup = destinationGroup;
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

		public List<MarginDetails> marginDetailsList;
	}
}
