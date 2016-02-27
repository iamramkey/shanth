package se.signa.signature.rest;

import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import se.signa.signature.common.SignatureException;
import se.signa.signature.dio.BDBVolumeAndRevenueChartResult;
import se.signa.signature.dio.BusinessDashboardInput;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.helpers.DBHelper.ExecuteSelectStamentResult;
import se.signa.signature.helpers.UsageDBHelper;

@Path("/bdbvolumeandrevenuechart")
public class BDBVolumeAndRevenueChartApi extends StigWebApi
{
	private static Logger logger = Logger.getLogger(BDBVolumeAndRevenueChartApi.class);
	private static DecimalFormat dfr = new DecimalFormat("#.####");
	private static Map<Integer, String> months = new HashMap<Integer, String>();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult bdbVolumeAndRevenueChart(BusinessDashboardInput input)
	{
		input.validate();
		input.checkRules();

		dfr.setRoundingMode(RoundingMode.CEILING);

		List<String> categories = new ArrayList<String>();
		getMonths();

		List<Series> volumeSeriesList = new ArrayList<Series>();
		List<Series> revenueSeriesList = new ArrayList<Series>();

		populate(categories, "I", volumeSeriesList, revenueSeriesList);
		populate(categories, "O", volumeSeriesList, revenueSeriesList);

		Collections.reverse(categories);
		//		Collections.reverse(volumeSeriesList);
		//		Collections.reverse(revenueSeriesList);
		String notification = "Volume and revenue chart rendered successfully !!!";
		logger.info(notification);
		return new BDBVolumeAndRevenueChartResult(notification, categories, volumeSeriesList, revenueSeriesList);
	}

	private void getMonths()
	{
		LocalDate date = new LocalDate(2015, 1, 1);
		DateTimeFormatter formatter = DateTimeFormat.forPattern("MMM");
		for (int i = 1; i <= 12; i++)
		{
			months.put(i, formatter.print(date).toUpperCase());
			date = date.plusMonths(1);
		}
	}

	private void populate(List<String> categories, String revCode, List<Series> volumeSeriesList, List<Series> revenueSeriesList)
	{
		ExecuteSelectStamentResult result = null;
		try
		{
			int counter = 0;
			Series volumeSeries = new Series();
			if (revCode.equalsIgnoreCase("I"))
				volumeSeries.name = "IN";
			else
				volumeSeries.name = "OUT";

			volumeSeries.data = new ArrayList<Double>();

			Series revenueSeries = new Series();
			if (revCode.equalsIgnoreCase("I"))
				revenueSeries.name = "IN";
			else
				revenueSeries.name = "OUT";

			revenueSeries.data = new ArrayList<Double>();

			String query = getPopulateQuery(revCode);
			result = UsageDBHelper.getDB().execute(query);

			while (result.next())
			{
				if (counter == 6)
					continue;

				int month = result.getRs().getInt("month");
				String mon = months.get(month);
				if (!categories.contains(mon))
					categories.add(mon);
				Long evtUsage = result.getRs().getLong("rev_billed_usage");
				Double dealAmount = result.getRs().getDouble("deal_amt");

				if (evtUsage != null && dealAmount != null)
				{
					volumeSeries.data.add((double) evtUsage);
					revenueSeries.data.add(dealAmount);
				}
				else
				{
					volumeSeries.data.add(0d);
					revenueSeries.data.add(0d);
				}

				counter++;
			}

			Collections.reverse(volumeSeries.data);
			Collections.reverse(revenueSeries.data);
			volumeSeriesList.add(volumeSeries);
			revenueSeriesList.add(revenueSeries);
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

	private String getPopulateQuery(String direction)
	{
		String query = null;
		if (direction.equalsIgnoreCase("I"))
		{
			logger.debug("Query for in volume and revenue chart : " + query);
			query = " select round(sum(IN_REV_BILLED_USAGE/60.00)) as rev_billed_usage , round(sum(in_dealamt)) as deal_amt,  extract(month from  evt_dttm) as month, extract(year from  evt_dttm) from margin_summary ";
			query = query + " where in_deal_id !=0 and evt_dttm <= sysdate ";
			query = query + " group by  extract(month from  evt_dttm), extract(year from  evt_dttm) order by extract(year from  evt_dttm) desc,extract(month from  evt_dttm) desc ";

		}
		else
		{
			query = " select round(sum(OUT_REV_BILLED_USAGE/60.00)) as rev_billed_usage , round(sum(out_dealamt)) as deal_amt,  extract(month from  evt_dttm)  as month, extract(year from  evt_dttm) from margin_summary ";
			query = query + " where out_deal_id !=0 and evt_dttm <= sysdate ";
			query = query + " group by  extract(month from  evt_dttm), extract(year from  evt_dttm) order by extract(year from  evt_dttm) desc,extract(month from  evt_dttm) desc ";

			logger.debug("Query for out volume and revenue chart : " + query);
		}
		return query;
	}

	public class Series
	{
		public String name;
		public List<Double> data;
	}
}
