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
import se.signa.signature.dio.CDBVolumeRevenueResult;
import se.signa.signature.dio.CountryDashboardInput;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.helpers.DBHelper.ExecuteSelectStamentResult;
import se.signa.signature.helpers.RefDBHelper;
import se.signa.signature.helpers.UsageDBHelper;

@Path("/cdbvolumerevenue")
public class CDBVolumeRevenueApi extends StigWebApi
{
	private static Logger logger = Logger.getLogger(CDBVolumeRevenueApi.class);
	private static DecimalFormat dfr = new DecimalFormat("#.####");
	private static DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(Locale.US);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult cdbVolumeRevenue(CountryDashboardInput input)
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
		headers.add("Committed");
		headers.add("Total");
		while (oneWeekBefore.isBefore(now))
		{
			String date = dateFormat.format(now.toDate());
			headers.add(date);
			now = now.minusDays(1);
		}

		List<DailyVolumes> dailyVolumesList = new LinkedList<DailyVolumes>();

		getDailyVolumes(dailyVolumesList, "In", input.countryName);
		getDailyVolumes(dailyVolumesList, "Out", input.countryName);

		List<DailyRevenue> dailyRevenueList = new LinkedList<DailyRevenue>();

		getDailyRevenue(dailyRevenueList, "In", input.countryName);
		getDailyRevenue(dailyRevenueList, "Out", input.countryName);

		String notification = "Volume and revenue details Rendered Successfully !!!";
		logger.info(notification);
		return new CDBVolumeRevenueResult(notification, headers, dailyVolumesList, dailyRevenueList);
	}

	private void getDailyVolumes(List<DailyVolumes> dailyVolumesList, String direction, String country)
	{
		DealTierLite dlt = getDealTiers(direction, "VOL", country);
		TrafficCount trc = getTrafficCount(direction, "VOL", country);
		DailyVolumes dailyVolumes = new DailyVolumes("All deals", dlt.dltDirection, dlt.dltToVol, trc.totalActual, trc.day1, trc.day2, trc.day3, trc.day4, trc.day5, trc.day6, trc.day7);
		dailyVolumesList.add(dailyVolumes);
	}

	private void getDailyRevenue(List<DailyRevenue> dailyRevenueList, String direction, String country)
	{
		DealTierLite dlt = getDealTiers(direction, "REV", country);
		TrafficCount trc = getTrafficCount(direction, "REV", country);
		DailyRevenue dailyRevenue = new DailyRevenue("All deals", dlt.dltDirection, dlt.dltToVol, trc.totalActual, trc.day1, trc.day2, trc.day3, trc.day4, trc.day5, trc.day6, trc.day7);
		dailyRevenueList.add(dailyRevenue);
	}

	private TrafficCount getTrafficCount(String direction, String filter, String country)
	{
		String query = "";
		if (filter.equalsIgnoreCase("VOL"))
			query = getVolumeTrafficCountQuery(direction);
		else
			query = getRevenueTrafficCountQuery(direction);

		ExecuteSelectStamentResult result = null;
		TrafficCount trafficCount = new TrafficCount();
		try
		{
			Double totalActual = 0d;
			Double[] dayUsage = new Double[7];
			result = UsageDBHelper.getDB().execute(query, country);
			if (result.next())
			{
				totalActual = result.getRs().getDouble("rev_billed_usage");
				DateTime now = new DateTime();
				DateTime oneWeekBefore = now.minusWeeks(1);
				int i = 0;
				while (oneWeekBefore.isBefore(now))
				{
					dayUsage[i] = getTrafficCountOnDate(direction, now.withTimeAtStartOfDay(), now.withTimeAtStartOfDay().plusDays(1).minusSeconds(1), filter, country);
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

	private String getRevenueTrafficCountQuery(String direction)
	{
		String refName = RefDBHelper.getDB().getUserName();
		String query = "";
		if (direction.contains("I"))
		{
			query = "select round(sum(IN_DEALAMT)) as rev_billed_usage from margin_summary where in_deal_id != 0 and in_deal_id is not null and in_bnd_id in (select bnd_id from ";
			query = query + " " + refName + ".area_connection arc, " + refName + ".country con, " + refName + ".area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ";
			query = query + " arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?) and evt_dttm <= sysdate " + " and in_deal_id in ( select del_id from " + refName + ".deal where del_to_dttm >=sysdate ) ";
		}
		else if (direction.contains("O"))
		{
			query = "select round(sum(OUT_DEALAMT)) as rev_billed_usage from margin_summary where out_deal_id != 0 and out_bnd_id in (select bnd_id from ";
			query = query + " " + refName + ".area_connection arc, " + refName + ".country con, " + refName + ".area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ";
			query = query + " arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?) and out_deal_id is not null and evt_dttm <= sysdate " + " and out_deal_id in ( select del_id from " + refName + ".deal where del_to_dttm >=sysdate ) ";
		}
		logger.debug("Query for getting usage : " + query);

		return query;
	}

	private String getVolumeTrafficCountQuery(String direction)
	{
		String refName = RefDBHelper.getDB().getUserName();

		String query = "";
		if (direction.contains("I"))
		{
			query = "select round(sum(in_rev_billed_usage)/60.00) as rev_billed_usage from margin_summary where in_deal_id != 0 and in_bnd_id in (select bnd_id from ";
			query = query + " " + refName + ".area_connection arc, " + refName + ".country con, " + refName + ".area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ";
			query = query + " arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?) and in_deal_id is not null and evt_dttm <= sysdate" + " and in_deal_id in ( select del_id from " + refName + ".deal where del_to_dttm >=sysdate ) ";
		}
		else if (direction.contains("O"))
		{
			query = "select round(sum(out_rev_billed_usage)/60.00) as rev_billed_usage from margin_summary where out_deal_id != 0 and out_bnd_id in (select bnd_id from ";
			query = query + " " + refName + ".area_connection arc, " + refName + ".country con, " + refName + ".area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ";
			query = query + " arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?) and out_deal_id is not null and evt_dttm <= sysdate " + " and out_deal_id in ( select del_id from " + refName + ".deal where del_to_dttm >=sysdate ) ";
		}
		logger.debug("Query for getting usage : " + query);

		return query;
	}

	private Double getTrafficCountOnDate(String direction, DateTime fromDttm, DateTime toDttm, String filter, String country)
	{
		String query = "";
		if (filter.equalsIgnoreCase("VOL"))
			query = getTrafficCountOnDateQuery(direction);
		else
			query = getRevenueTrafficCountOnDateQuery(direction);

		ExecuteSelectStamentResult result = null;
		Double totalActual = 0d;
		try
		{
			result = UsageDBHelper.getDB().execute(query, new Object[] { country, fromDttm, toDttm });
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
		String refName = RefDBHelper.getDB().getUserName();
		if (direction.contains("I"))
		{
			query = "select round(sum(in_rev_billed_usage)/60.00) as rev_billed_usage from margin_summary where in_deal_id != 0 and in_bnd_id in (select bnd_id from ";
			query = query + " " + refName + ".area_connection arc, " + refName + ".country con, " + refName + ".area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ";
			query = query + " arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?) and in_deal_id is not null and evt_dttm >= ? and evt_dttm <= ? ";

		}
		else if (direction.contains("O"))
		{
			query = "select round(sum(out_rev_billed_usage)/60.00) as rev_billed_usage from margin_summary where out_deal_id != 0 and out_bnd_id in (select bnd_id from ";
			query = query + " " + refName + ".area_connection arc, " + refName + ".country con, " + refName + ".area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ";
			query = query + " arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?) and out_deal_id is not null and evt_dttm >= ? and evt_dttm <= ? ";
		}
		logger.debug("Query for getting usage on date : " + query);
		return query;
	}

	private String getRevenueTrafficCountOnDateQuery(String direction)
	{
		String query = "";
		String refName = RefDBHelper.getDB().getUserName();
		if (direction.contains("I"))
		{
			query = "select round(sum(IN_DEALAMT)) as rev_billed_usage from margin_summary where in_deal_id != 0 and in_bnd_id in (select bnd_id from ";
			query = query + " " + refName + ".area_connection arc, " + refName + ".country con, " + refName + ".area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ";
			query = query + " arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?) and in_deal_id is not null and evt_dttm >= ? and evt_dttm <= ? ";
		}
		else if (direction.contains("O"))
		{
			query = "select round(sum(OUT_DEALAMT)) as rev_billed_usage from margin_summary where out_deal_id != 0 and out_bnd_id in (select bnd_id from ";
			query = query + " " + refName + ".area_connection arc, " + refName + ".country con, " + refName + ".area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ";
			query = query + " arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?) and out_deal_id is not null and evt_dttm >= ? and evt_dttm <= ? ";
		}
		logger.debug("Query for getting usage : " + query);

		return query;
	}

	private DealTierLite getDealTiers(String direction, String filter, String country)
	{

		String query = "";
		if (filter.equalsIgnoreCase("VOL"))
		{
			query = "select dlt_direction , sum(dlt_to_val) as dlt_to_val  from deal_tier where dlt_direction like ? and dlt_comm_fl = 'Y'  and del_id in ( select del_id from deal where del_to_dttm >=sysdate and del_id in (select del_id from deal_item where bnd_id in (select bnd_id from ";
			query = query + " area_connection arc, country con, area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ";
			query = query + " arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?)) ) group by dlt_direction";

		}
		else
		{
			query = "select dlt_direction, sum((dlt_to_val * (dlr.dlr_rate/1000000))) as dlt_to_val from deal_tier  dlt ";
			query = query + " left join deal_threshold_rate dlr on dlt.dlt_id=dlr.dlt_id ";
			query = query + " where dlt_direction like ? and dlt_comm_fl='Y' and dlt.del_id in ( select del_id from deal where del_to_dttm >=sysdate and del_id in (select del_id from deal_item where bnd_id in (select bnd_id from ";
			query = query + " area_connection arc, country con, area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ";
			query = query + " arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?))) group by dlt_direction";
		}
		logger.debug("Query for getting deal tier : " + query);

		ExecuteSelectStamentResult result = null;
		DealTierLite dealTier = new DealTierLite();
		try
		{
			result = RefDBHelper.getDB().execute(query, new Object[] { "%" + direction + "%", country });
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

		public TrafficCount()
		{
		}

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

	public class DailyVolumes
	{
		public String deal;
		public String direction;
		public String committed;
		public String total = "";
		public String date1 = "";
		public String date2 = "";
		public String date3 = "";
		public String date4 = "";
		public String date5 = "";
		public String date6 = "";
		public String date7 = "";

		public DailyVolumes(String deal, String direction, Double committed, Double total, Double date1, Double date2, Double date3, Double date4, Double date5, Double date6, Double date7)
		{
			this.deal = deal;
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
	}

	public class DailyRevenue
	{
		public String deal;
		public String direction;
		//		public String committed;
		public String total = "";
		public String date1 = "";
		public String date2 = "";
		public String date3 = "";
		public String date4 = "";
		public String date5 = "";
		public String date6 = "";
		public String date7 = "";

		public DailyRevenue(String deal, String direction, Double committed, Double total, Double date1, Double date2, Double date3, Double date4, Double date5, Double date6, Double date7)
		{
			this.deal = deal;
			this.direction = direction;
			//			if (committed != null)
			//				this.committed = String.valueOf(df.format(committed));
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

	public class DealTierLite
	{
		public Double dltToVol;
		public String dltDirection;

		public DealTierLite()
		{
		}

		public DealTierLite(ExecuteSelectStamentResult rs) throws SQLException
		{
			dltToVol = rs.getRs().getDouble("dlt_to_val");
			dltDirection = rs.getRs().getString("dlt_direction");
		}
	}
}
