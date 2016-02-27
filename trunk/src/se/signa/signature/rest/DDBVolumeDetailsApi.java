package se.signa.signature.rest;

import java.math.RoundingMode;
import java.sql.SQLException;
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
import se.signa.signature.common.NegativeResultException;
import se.signa.signature.common.SignatureException;
import se.signa.signature.dio.DDBVolumeDetailsResult;
import se.signa.signature.dio.DashboardInput;
import se.signa.signature.dio.NegativeResult;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.gen.dba.DealDba;
import se.signa.signature.gen.dbo.Deal;
import se.signa.signature.helpers.DBHelper.ExecuteSelectStamentResult;
import se.signa.signature.helpers.RefDBHelper;
import se.signa.signature.helpers.UsageDBHelper;

@Path("/ddbvolumedetails")
public class DDBVolumeDetailsApi extends StigWebApi
{
	private static Logger logger = Logger.getLogger(DDBVolumeDetailsApi.class);
	private static DecimalFormat dfr = new DecimalFormat("#.####");
	private static DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(Locale.US);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult ddbVolumeDetails(DashboardInput input)
	{
		input.validate();
		input.checkRules();

		dfr.setRoundingMode(RoundingMode.CEILING);
		DecimalFormatSymbols symbols = dfr.getDecimalFormatSymbols();

		symbols.setGroupingSeparator(' ');
		df.setDecimalFormatSymbols(symbols);

		Deal deal = DealDba.getI().fetchBydelName(input.deal);
		if (deal == null)
			throw new NegativeResultException(new NegativeResult(Constants.ErrCode.INPUT_FIELD_INVALID, "Deal does not exist..!!", "deal"));

		DateFormat dateFormat = new SimpleDateFormat(Constants.DASH_BOARD_DTTM_FORMAT);

		String carrier = getCarrierName(deal);
		int dealId = deal.getDelId();
		String startDate = dateFormat.format(deal.getDelFromDttm().toDate());
		String endDate = dateFormat.format(deal.getDelToDttm().toDate());
		String gracePeriod = deal.getDelGracePeriodDays() + " days";
		String currency = "INR";

		DateTime now = new DateTime();
		DateTime oneWeekBefore = now.minusWeeks(1);

		List<String> headers = new LinkedList<String>();
		headers.add("Destination Group");
		headers.add("Direction");
		headers.add("Committed");
		headers.add("Total");
		while (oneWeekBefore.isBefore(now))
		{
			String date = dateFormat.format(now.toDate());
			headers.add(date);
			now = now.minusDays(1);
		}

		List<VolumeDetails> volumeDetailsList = new LinkedList<VolumeDetails>();
		populateVolumeDetails(volumeDetailsList, deal, "I");
		populateVolumeDetails(volumeDetailsList, deal, "O");

		String notification = "Volume Details Rendered Successfully !!!";
		logger.info(notification);
		return new DDBVolumeDetailsResult(notification, carrier, dealId, currency, startDate, endDate, gracePeriod, headers, volumeDetailsList);
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

	private void populateVolumeDetails(List<VolumeDetails> volumeDetailsList, Deal deal, String direction)
	{
		List<DealBandGroupLite> dbgs = getDealBandGroups(deal, direction);
		for (DealBandGroupLite dbg : dbgs)
		{
			DealTierLite dlt = getDealTiers(deal, direction, dbg);
			TrafficCount trc = getTrafficCount(deal, direction, dbg);
			List<VolumeDetails> volumeDetails = getBandVolumeDetails(deal, direction, dbg);
			VolumeDetails volumeDetailsOuter = new VolumeDetails(dbg.dbgName, dlt.dltDirection, dlt.dltToVol, trc.totalActual, trc.day1, trc.day2, trc.day3, trc.day4, trc.day5, trc.day6, trc.day7);
			volumeDetailsOuter.volumeDetailsList = volumeDetails;
			volumeDetailsList.add(volumeDetailsOuter);
		}
	}

	private List<VolumeDetails> getBandVolumeDetails(Deal deal, String direction, DealBandGroupLite dbg)
	{
		String query = getBandVolumeDetailQuery(direction);
		/*String query = "select  bnd.bnd_id as bnd_id, bnd.bnd_name  as bnd_name ,  sum(rev_billed_usage) ";
		query = query + " as rev_billed_usage from traffic_count trc , deal_item dli , ";
		query = query + " band bnd where trc.bnd_id = bnd.bnd_id and dli.bnd_id = ";
		query = query + " trc.bnd_id and dli.dbg_id = ? and rev_revenue_code like ? ";
		query = query + " and trc.del_id = ? group by bnd.bnd_id ,bnd.bnd_name "; */

		ExecuteSelectStamentResult result = null;
		List<VolumeDetails> volumeDetails = new LinkedList<VolumeDetails>();
		try
		{
			Double[] dayUage = new Double[7];
			result = UsageDBHelper.getDB().execute(query, new Object[] { dbg.dbgId, deal.getDelId() });
			while (result.next())
			{
				Double totalActual = result.getRs().getDouble("rev_billed_usage");
				String bandName = result.getRs().getString("bnd_name");
				int bndId = result.getRs().getInt("bnd_id");

				DateTime now = new DateTime();
				DateTime oneWeekBefore = now.minusWeeks(1);
				int i = 0;
				while (oneWeekBefore.isBefore(now))
				{
					dayUage[i] = getBandTrafficCountOnDate(deal, direction, bndId, now.withTimeAtStartOfDay(), now.withTimeAtStartOfDay().plusDays(1).minusSeconds(1));
					now = now.minusDays(1);
					i++;
				}

				volumeDetails.add(new VolumeDetails(bandName, "", 0d, totalActual, dayUage[0], dayUage[1], dayUage[2], dayUage[3], dayUage[4], dayUage[5], dayUage[6]));
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
		return volumeDetails;
	}

	private String getBandVolumeDetailQuery(String direction)
	{
		String query = "";
		String refName = RefDBHelper.getDB().getUserName();

		if (direction.contains("I"))
		{

			query = "select  bnd.bnd_id as bnd_id, bnd.bnd_name  as bnd_name ,  round(sum(in_rev_billed_usage)/60.00) ";
			query = query + " as rev_billed_usage from margin_summary mrc, " + refName + ".band bnd where mrc.in_bnd_id = bnd.bnd_id and mrc.in_dbg_id = ? ";
			query = query + "and mrc.in_deal_id = ? ";
			query = query + "and mrc.evt_dttm <= sysdate ";
			query = query + "group by bnd.bnd_id ,bnd.bnd_name ";

			logger.debug("Query for getting in band volume details : " + query);

		}
		else if (direction.contains("O"))
		{

			query = "select  bnd.bnd_id as bnd_id, bnd.bnd_name  as bnd_name ,  round(sum(out_rev_billed_usage)/60.00) ";
			query = query + " as rev_billed_usage from margin_summary mrc, " + refName + ".band bnd where mrc.out_bnd_id = bnd.bnd_id and mrc.out_dbg_id = ? ";
			query = query + "and mrc.out_deal_id = ? ";
			query = query + "and mrc.evt_dttm <= sysdate ";
			query = query + "group by bnd.bnd_id ,bnd.bnd_name ";

			logger.debug("Query for getting out band volume details : " + query);
		}

		return query;
	}

	private TrafficCount getTrafficCount(Deal deal, String direction, DealBandGroupLite dbg)
	{
		String query = getTrafficCountQuery(direction);
		/*String query = "select sum(rev_billed_usage) as rev_billed_usage from traffic_count trc , deal_item dli where dli.bnd_id = ";
		query = query + " trc.bnd_id and dli.dbg_id = ? and rev_revenue_code like ? ";
		query = query + " and trc.del_id = ?"; */
		ExecuteSelectStamentResult result = null;
		TrafficCount trafficCount = null;
		try
		{
			Double totalActual = 0d;
			Double[] dayUsage = new Double[7];
			result = UsageDBHelper.getDB().execute(query, new Object[] { deal.getDelId(), dbg.dbgId });
			if (result.next())
			{
				totalActual = result.getRs().getDouble("rev_billed_usage");
				DateTime now = new DateTime();
				DateTime oneWeekBefore = now.minusWeeks(1);
				int i = 0;
				while (oneWeekBefore.isBefore(now))
				{
					dayUsage[i] = getTrafficCountOnDate(deal, direction, dbg, now.withTimeAtStartOfDay(), now.withTimeAtStartOfDay().plusDays(1).minusSeconds(1));
					now = now.minusDays(1);
					i++;
				}

				trafficCount = new TrafficCount(totalActual, dayUsage[0], dayUsage[1], dayUsage[2], dayUsage[3], dayUsage[4], dayUsage[5], dayUsage[6]);
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
		return trafficCount;
	}

	private String getTrafficCountQuery(String direction)
	{
		String query = "";

		if (direction.contains("I"))
		{
			query = "select round(sum(in_rev_billed_usage)/60.00) as rev_billed_usage from margin_summary mrc ";
			query = query + " where mrc.in_deal_id = ? and mrc.in_dbg_id= ? and evt_dttm <=sysdate ";

			logger.debug("Query for getting in traffic count(usage) : " + query);

		}
		else if (direction.contains("O"))
		{

			query = "select round(sum(out_rev_billed_usage)/60.00) as rev_billed_usage from margin_summary mrc ";
			query = query + " where mrc.out_deal_id = ? and mrc.out_dbg_id= ? and evt_dttm <=sysdate ";

			logger.debug("Query for getting out traffic count(usage) : " + query);
		}
		return query;
	}

	private Double getTrafficCountOnDate(Deal deal, String direction, DealBandGroupLite dbg, DateTime fromDttm, DateTime toDttm)
	{
		String query = getTrafficCountOnDateQuery(direction);
		/*String query = "select sum(rev_billed_usage) as rev_billed_usage from traffic_count trc , deal_item dli where dli.bnd_id = ";
		query = query + " trc.bnd_id and dli.dbg_id = ? and rev_revenue_code like ? ";
		query = query + " and trc.del_id = ? and trc.evt_dttm >= ? and trc.evt_dttm <= ? ";*/
		ExecuteSelectStamentResult result = null;
		Double totalActual = 0d;
		try
		{
			result = UsageDBHelper.getDB().execute(query, new Object[] { dbg.dbgId, deal.getDelId(), fromDttm, toDttm });
			if (result.next())
			{
				totalActual = result.getRs().getDouble("rev_billed_usage");
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
		return totalActual;
	}

	private String getTrafficCountOnDateQuery(String direction)
	{
		String query = "";

		if (direction.contains("I"))
		{
			query = "select round(sum(in_rev_billed_usage)/60.00) as rev_billed_usage from margin_summary msu ";
			query = query + " where msu.in_dbg_id= ? and msu.in_deal_id = ? and msu.evt_dttm >= ? and msu.evt_dttm <= ? ";

			logger.debug("Query for getting in traffic count(usage) with date filter: " + query);
		}
		else if (direction.contains("O"))
		{
			query = "select round(sum(out_rev_billed_usage)/60.00) as rev_billed_usage from margin_summary msu ";
			query = query + " where msu.out_dbg_id= ? and msu.out_deal_id = ? and msu.evt_dttm >= ? and msu.evt_dttm <= ? ";

			logger.debug("Query for getting out traffic count(usage) with date filter: " + query);
		}
		return query;
	}

	private Double getBandTrafficCountOnDate(Deal deal, String direction, int bndId, DateTime fromDttm, DateTime toDttm)
	{
		String query = getBandTrafficCountOnDateQuery(direction);
		/*String query = "select sum(rev_billed_usage) as rev_billed_usage from traffic_count  where  ";
		query = query + " bnd_id = ? and rev_revenue_code like ? ";
		query = query + " and del_id = ? and evt_dttm >= ? and evt_dttm <= ? ";*/
		ExecuteSelectStamentResult result = null;
		Double totalActual = 0d;
		try
		{
			result = UsageDBHelper.getDB().execute(query, new Object[] { bndId, deal.getDelId(), fromDttm, toDttm });
			if (result.next())
			{
				totalActual = result.getRs().getDouble("rev_billed_usage");
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
		return totalActual;
	}

	private String getBandTrafficCountOnDateQuery(String direction)
	{
		String query = "";
		if (direction.contains("I"))
		{
			query = "select round(sum(in_rev_billed_usage)/60.00) as rev_billed_usage from margin_summary  where  ";
			query = query + " in_bnd_id = ?  ";
			query = query + " and in_deal_id = ? and evt_dttm >= ? and evt_dttm <= ? ";
		}
		else if (direction.contains("O"))
		{
			query = "select round(sum(out_rev_billed_usage)/60.00) as rev_billed_usage from margin_summary  where  ";
			query = query + " out_bnd_id = ?  ";
			query = query + " and out_deal_id = ? and evt_dttm >= ? and evt_dttm <= ? ";
		}

		logger.debug("Query for getting band traffic count(usage) with date filter: " + query);
		return query;
	}

	private List<DealBandGroupLite> getDealBandGroups(Deal deal, String direction)
	{
		String query = "select dbg.dbg_id as dbg_id , dbg.dbg_name from deal_band_group dbg ";
		query = query + " where dbg.del_id = ? and dbg.dbg_direction like ? order by dbg.dbg_id ";

		logger.debug("Query for getting deal band group :  " + query);

		ExecuteSelectStamentResult result = null;
		List<DealBandGroupLite> dbgs = new ArrayList<DealBandGroupLite>();
		try
		{
			result = RefDBHelper.getDB().execute(query, new Object[] { deal.getDelId(), "%" + direction + "%" });
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

	private DealTierLite getDealTiers(Deal deal, String direction, DealBandGroupLite dbg)
	{
		String query = "select dlt_direction , dlt_to_val  from deal_tier where del_id = ? and dlt_direction like ? and dbg_id = ? and dlt_comm_fl = 'Y' order by dlt_id ";

		logger.debug("Query for getting deal tiers : " + query);
		ExecuteSelectStamentResult result = null;
		DealTierLite dealTier = null;
		try
		{
			result = RefDBHelper.getDB().execute(query, new Object[] { deal.getDelId(), "%" + direction + "%", dbg.dbgId });
			if (result.next())
			{
				dealTier = new DealTierLite(result);
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
		return dealTier;
	}

	public class VolumeDetails
	{
		public String destinationGroup;
		public String direction = "";
		public String committed = "";
		public String total = "";
		public String date1 = "";
		public String date2 = "";
		public String date3 = "";
		public String date4 = "";
		public String date5 = "";
		public String date6 = "";
		public String date7 = "";

		public VolumeDetails(String destinationGroup, String direction, Double committed, Double total, Double date1, Double date2, Double date3, Double date4, Double date5, Double date6, Double date7)
		{
			this.destinationGroup = destinationGroup;
			this.direction = direction;
			if (committed != null)
				this.committed = String.valueOf(df.format(committed));
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

		public List<VolumeDetails> volumeDetailsList;
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

	public class TrafficCount
	{
		public Double totalActual;
		public Double day1;
		public Double day2;
		public Double day3;
		public Double day4;
		public Double day5;
		public Double day6;
		public Double day7;

		public TrafficCount(Double totalActual, Double day1, Double day2, Double day3, Double day4, Double day5, Double day6, Double day7)
		{
			this.totalActual = totalActual;
			this.day1 = day1;
			this.day2 = day2;
			this.day3 = day3;
			this.day4 = day4;
			this.day5 = day5;
			this.day6 = day6;
			this.day7 = day7;
		}
	}

	public class DealTierLite
	{
		public Double dltToVol;
		public String dltDirection;

		public DealTierLite(ExecuteSelectStamentResult rs) throws SQLException
		{
			dltToVol = rs.getRs().getDouble("dlt_to_val");
			dltDirection = rs.getRs().getString("dlt_direction");
		}
	}
}
