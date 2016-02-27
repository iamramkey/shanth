package se.signa.signature.rest;

import java.math.RoundingMode;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import se.signa.signature.dio.DDBDealDetailsResult;
import se.signa.signature.dio.DashboardInput;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.gen.dba.DealDba;
import se.signa.signature.gen.dbo.Deal;
import se.signa.signature.helpers.DBHelper.ExecuteSelectStamentResult;
import se.signa.signature.helpers.RefDBHelper;

@Path("/ddbdealdetails")
public class DDBDealDetailsApi extends StigWebApi
{
	private static Logger logger = Logger.getLogger(DDBDealDetailsApi.class);
	private static DecimalFormat dfr = new DecimalFormat("#.####");
	private static DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(Locale.US);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult ddbDealDetails(DashboardInput input)
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

		String gracePeriod = "0 days";
		if (deal.getDelGracePeriodDays() != null && deal.getDelGracePeriodDays() > 0)
			gracePeriod = deal.getDelGracePeriodDays() + " days";
		String currency = "INR";

		List<DealDetails> dealDetailsList = new LinkedList<DealDetails>();
		populateDealDetails(dealDetailsList, deal, "In");
		populateDealDetails(dealDetailsList, deal, "Out");

		String notification = "Deal Details Rendered Successfully !!!";
		logger.info(notification);
		return new DDBDealDetailsResult(notification, carrier, dealId, currency, startDate, endDate, gracePeriod, dealDetailsList);
	}

	private void populateDealDetails(List<DealDetails> dealDetailsList, Deal deal, String direction)
	{
		List<DealBandGroupLite> dbgs = getDealBandGroups(deal, direction);
		for (DealBandGroupLite dbg : dbgs)
		{
			List<DealTierLite> dlts = getDealTiers(deal, direction, dbg);
			List<DealItemLite> dlis = getDealItems(deal, direction, dbg);
			for (int i = 0; i < dlts.size() - 1; i++)
			{
				DealTierLite dlt = dlts.get(i);
				DealTierLite nextDlt = dlts.get(i + 1);

				DealDetails outerRow = new DealDetails(dbg.dbgName, direction, dlt.dltNumber, dlt.dltToVol, null, null, null, null);
				ArrayList<DealDetails> dealDetailsInnerList = new ArrayList<DealDetails>();
				outerRow.dealDetailsList = dealDetailsInnerList;

				for (int j = 0; j < dlis.size(); j++)
				{
					DealItemLite dli = dlis.get(j);
					Double rate = getRate(dli, dlt);
					Double nextRate = getRate(dli, nextDlt);
					dealDetailsInnerList.add(new DealDetails(dli.band, direction, dlt.dltNumber, null, rate, nextRate, dli.from, dli.to));
				}
				dealDetailsList.add(outerRow);
			}
		}
	}

	private Double getRate(DealItemLite dli, DealTierLite dlt)
	{
		if (dlt.dltNumber == 1)
			return getBaseRate(dli);
		else
			return getThresholdRate(dli, dlt);
	}

	private Double getThresholdRate(DealItemLite dli, DealTierLite dlt)
	{
		String query = " select avg(dlr_rate)/1000000 from deal_threshold_rate where dli_id =  ? and dlt_id = ?";

		ExecuteSelectStamentResult result = null;
		Double rate = null;
		try
		{
			result = RefDBHelper.getDB().execute(query, new Object[] { dli.dliId, dlt.dltId });
			if (result.next())
				rate = result.getRs().getDouble(1);
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
		return rate;
	}

	private Double getBaseRate(DealItemLite dli)
	{
		String query = " select avg(dlb_rate)/1000000 from deal_base_rate where dli_id =  ? ";

		ExecuteSelectStamentResult result = null;
		Double rate = null;
		try
		{
			result = RefDBHelper.getDB().execute(query, new Object[] { dli.dliId });
			if (result.next())
				rate = result.getRs().getDouble(1);
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
		return rate;
	}

	private List<DealItemLite> getDealItems(Deal deal, String direction, DealBandGroupLite dbg)
	{
		String query = "select  bnd.bnd_name , dli.dli_id , dli.dli_from_dttm, dli.dli_to_dttm from deal_item dli, band bnd ";
		query = query + " where dli.bnd_id = bnd.bnd_id and dli.del_id = ? and dli_direction = ? and dli.dbg_id = ? ";
		ExecuteSelectStamentResult result = null;
		List<DealItemLite> dealItems = new ArrayList<DealItemLite>();
		try
		{
			result = RefDBHelper.getDB().execute(query, new Object[] { deal.getDelId(), direction, dbg.dbgId });
			while (result.next())
			{
				dealItems.add(new DealItemLite(result));
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
		return dealItems;
	}

	private List<DealTierLite> getDealTiers(Deal deal, String direction, DealBandGroupLite dbg)
	{
		String query = "select dlt_id, dlt_number , dlt_to_val  from deal_tier where del_id = ? and dlt_direction = ? and dbg_id = ? order by dlt_number ";

		logger.debug("Query for getting deal tiers : " + query);

		ExecuteSelectStamentResult result = null;
		List<DealTierLite> dealTiers = new ArrayList<DealTierLite>();
		try
		{
			result = RefDBHelper.getDB().execute(query, new Object[] { deal.getDelId(), direction, dbg.dbgId });
			while (result.next())
			{
				dealTiers.add(new DealTierLite(result));
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
		return dealTiers;
	}

	private List<DealBandGroupLite> getDealBandGroups(Deal deal, String direction)
	{
		String query = "select dbg.dbg_id as dbg_id , dbg.dbg_name from deal_band_group dbg ";
		query = query + " where dbg.del_id = ? and dbg.dbg_direction = ? order by dbg.dbg_id ";

		logger.debug("Query for getting deal band groups : " + query);

		ExecuteSelectStamentResult result = null;
		List<DealBandGroupLite> dbgs = new ArrayList<DealBandGroupLite>();
		try
		{
			result = RefDBHelper.getDB().execute(query, new Object[] { deal.getDelId(), direction });
			while (result.next())
			{
				dbgs.add(new DealBandGroupLite(result));
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
		return dbgs;
	}

	private String getCarrierName(Deal deal)
	{
		ExecuteSelectStamentResult result = null;
		String carrierName = null;
		try
		{
			String query = "select acc_company_name from customer_vendor where acc_id = ?";

			logger.debug("Query for getting carrier name : " + query);

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

	public class DealDetails
	{
		public String destination;
		public String direction;
		public int tier;
		public String volume = "";
		public String rate = "";
		public String nextRate = "";
		public String validFrom = "";
		public String validTo = "";

		public DealDetails(String destination, String direction, int tier, Double volume, Double rate, Double nextRate, String validFrom, String validTo)
		{
			this.destination = destination;
			this.direction = direction;
			this.tier = tier;
			if (volume != null)
				this.volume = String.valueOf(df.format(volume));
			if (rate != null)
				this.rate = String.valueOf(dfr.format(rate));
			if (nextRate != null)
				this.nextRate = String.valueOf(dfr.format(nextRate));
			if (validFrom != null)
				this.validFrom = validFrom;
			if (validTo != null)
				this.validTo = validTo;
		}

		public List<DealDetails> dealDetailsList;
	}

	public class DealBandGroupLite
	{
		public String dbgName;
		public int dbgId;

		public DealBandGroupLite(ExecuteSelectStamentResult rs) throws SQLException
		{
			dbgName = rs.getRs().getString("dbg_name");
			dbgId = rs.getRs().getInt("dbg_id");
		}
	}

	public class DealTierLite
	{
		public int dltNumber;
		public String dltId;
		public Double dltToVol;

		public DealTierLite(ExecuteSelectStamentResult rs) throws SQLException
		{
			dltNumber = rs.getRs().getInt("dlt_number");
			dltId = rs.getRs().getString("dlt_id");
			dltToVol = rs.getRs().getDouble("dlt_to_val");
		}
	}

	public class DealItemLite
	{
		public int dliId;
		public String band;
		public String from;
		public String to;
		DateFormat dateFormat = new SimpleDateFormat(Constants.DASH_BOARD_DTTM_FORMAT);

		public DealItemLite(ExecuteSelectStamentResult rs) throws SQLException
		{
			dliId = rs.getRs().getInt("dli_id");
			band = rs.getRs().getString("bnd_name");
			from = dateFormat.format(getDateTime(rs.getRs().getTimestamp("dli_from_dttm")).toDate());
			to = dateFormat.format(getDateTime(rs.getRs().getTimestamp("dli_to_dttm")).toDate());
		}

		public DateTime getDateTime(Timestamp timestamp)
		{
			if (timestamp == null)
				return null;
			return new DateTime(timestamp);
		}

	}
}
