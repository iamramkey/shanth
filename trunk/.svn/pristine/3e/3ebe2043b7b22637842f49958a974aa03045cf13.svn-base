package se.signa.signature.rest;

import java.math.RoundingMode;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import se.signa.signature.common.SignatureException;
import se.signa.signature.dio.BDBDealNonDealTrafficChartResult;
import se.signa.signature.dio.BusinessDashboardInput;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.helpers.DBHelper.ExecuteSelectStamentResult;
import se.signa.signature.helpers.UsageDBHelper;

@Path("/bdbdealnondealtrafficchart")
public class BDBDealNonDealTrafficChartApi extends StigWebApi
{
	private static Logger logger = Logger.getLogger(BDBDealNonDealTrafficChartApi.class);
	private static DecimalFormat dfr = new DecimalFormat("#.####");

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult bdbDealNonDealTrafficChart(BusinessDashboardInput input)
	{
		input.validate();
		input.checkRules();

		dfr.setRoundingMode(RoundingMode.CEILING);

		List<Object[]> dealDataList = new LinkedList<Object[]>();
		List<Object[]> nonDealDataList = new LinkedList<Object[]>();

		getDealData(dealDataList);
		getNonDealData(nonDealDataList);

		//		populate(dealDataList,nonDealDataList);

		/*DateTime dateTime = DateTime.now().withTimeAtStartOfDay();
		
		Object[] parsedDataArray = new Object[] { dateTime.getMillis(), 0.7695 };
		Object[] parsedDataArray1 = new Object[] { dateTime.minusDays(1).getMillis(), 0.7648 };
		Object[] parsedDataArray2 = new Object[] { dateTime.minusDays(2).getMillis(), 0.7645 };
		Object[] parsedDataArray3 = new Object[] { dateTime.minusDays(3).getMillis(), 0.7638 };
		Object[] parsedDataArray4 = new Object[] { dateTime.minusDays(4).getMillis(), 0.7549 };
		Object[] parsedDataArray5 = new Object[] { dateTime.minusDays(5).getMillis(), 0.7562 };
		Object[] parsedDataArray6 = new Object[] { dateTime.minusDays(6).getMillis(), 0.7574 };
		Object[] parsedDataArray7 = new Object[] { dateTime.minusDays(7).getMillis(), 0.7543 };
		Object[] parsedDataArray8 = new Object[] { dateTime.minusDays(8).getMillis(), 0.7510 };
		Object[] parsedDataArray9 = new Object[] { dateTime.minusDays(9).getMillis(), 0.7498 };
		Object[] parsedDataArray10 = new Object[] { dateTime.minusDays(10).getMillis(), 0.7477 };
		Object[] parsedDataArray11 = new Object[] { dateTime.minusDays(11).getMillis(), 0.7492 };
		
		dealDataList.add(parsedDataArray);
		dealDataList.add(parsedDataArray1);
		dealDataList.add(parsedDataArray2);
		dealDataList.add(parsedDataArray3);
		dealDataList.add(parsedDataArray4);
		dealDataList.add(parsedDataArray5);
		dealDataList.add(parsedDataArray6);
		dealDataList.add(parsedDataArray7);
		dealDataList.add(parsedDataArray8);
		dealDataList.add(parsedDataArray9);
		dealDataList.add(parsedDataArray10);
		dealDataList.add(parsedDataArray11);
		
		DateTime dateTime1 = DateTime.now().withTimeAtStartOfDay();
		
		Object[] parsedDataArrayNonDeal = new Object[] { dateTime1.getMillis(), 0.6195 };
		Object[] parsedDataArrayNonDeal1 = new Object[] { dateTime1.minusDays(1).getMillis(), 0.6148 };
		Object[] parsedDataArrayNonDeal2 = new Object[] { dateTime1.minusDays(2).getMillis(), 0.5645 };
		Object[] parsedDataArrayNonDeal3 = new Object[] { dateTime1.minusDays(3).getMillis(), 0.5638 };
		Object[] parsedDataArrayNonDeal4 = new Object[] { dateTime1.minusDays(4).getMillis(), 0.5549 };
		Object[] parsedDataArrayNonDeal5 = new Object[] { dateTime1.minusDays(5).getMillis(), 0.6762 };
		Object[] parsedDataArrayNonDeal6 = new Object[] { dateTime1.minusDays(6).getMillis(), 0.6764 };
		Object[] parsedDataArrayNonDeal7 = new Object[] { dateTime1.minusDays(7).getMillis(), 0.6743 };
		Object[] parsedDataArrayNonDeal8 = new Object[] { dateTime1.minusDays(8).getMillis(), 0.6710 };
		Object[] parsedDataArrayNonDeal9 = new Object[] { dateTime1.minusDays(9).getMillis(), 0.64898 };
		Object[] parsedDataArrayNonDeal10 = new Object[] { dateTime1.minusDays(10).getMillis(), 0.6466 };
		Object[] parsedDataArrayNonDeal11 = new Object[] { dateTime1.minusDays(11).getMillis(), 0.6492 };
		
		nonDealDataList.add(parsedDataArrayNonDeal);
		nonDealDataList.add(parsedDataArrayNonDeal1);
		nonDealDataList.add(parsedDataArrayNonDeal2);
		nonDealDataList.add(parsedDataArrayNonDeal3);
		nonDealDataList.add(parsedDataArrayNonDeal4);
		nonDealDataList.add(parsedDataArrayNonDeal5);
		nonDealDataList.add(parsedDataArrayNonDeal6);
		nonDealDataList.add(parsedDataArrayNonDeal7);
		nonDealDataList.add(parsedDataArrayNonDeal8);
		nonDealDataList.add(parsedDataArrayNonDeal9);
		nonDealDataList.add(parsedDataArrayNonDeal10);
		nonDealDataList.add(parsedDataArrayNonDeal11);*/

		String notification = "Deal vs Non deal chart rendered successfully !!!";
		logger.info(notification);
		return new BDBDealNonDealTrafficChartResult(notification, dealDataList, nonDealDataList);
	}

	public DateTime getDateTime(Timestamp timestamp)
	{
		if (timestamp == null)
			return null;
		return new DateTime(timestamp);
	}

	private void getNonDealData(List<Object[]> nonDealDataList)
	{
		ExecuteSelectStamentResult result = null;
		try
		{
			String query = " select evt_dttm , round(sum(IN_REV_BILLED_USAGE/60.000)) as rev_billed_usage  from margin_summary where in_deal_id = 0  and out_deal_id = 0 and evt_dttm <= sysdate and evt_dttm >= trunc(add_months(sysdate,-5),'MM') group by evt_dttm order by evt_dttm ";
			logger.debug("Query for non deal data : " + query);
			result = UsageDBHelper.getDB().execute(query);
			while (result.next())
			{
				DateTime evtDttm = getDateTime(result.getRs().getTimestamp("evt_dttm"));
				Double volume = result.getRs().getDouble("rev_billed_usage");
				Object[] parsedDataArray = new Object[] { evtDttm.getMillis(), Math.round(volume) };
				nonDealDataList.add(parsedDataArray);
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

	private void getDealData(List<Object[]> dealDataList)
	{
		ExecuteSelectStamentResult result = null;
		try
		{

			String query = " select evt_dttm , round(sum(IN_REV_BILLED_USAGE/60.000)) as rev_billed_usage  from margin_summary where (in_deal_id != 0  or out_deal_id != 0) and (evt_dttm <= sysdate  and evt_dttm >= trunc(add_months(sysdate,-5),'MM'))   group by evt_dttm order by evt_dttm ";
			logger.debug("Query for deal data : " + query);
			result = UsageDBHelper.getDB().execute(query);
			while (result.next())
			{
				DateTime evtDttm = getDateTime(result.getRs().getTimestamp("evt_dttm"));
				Double volume = result.getRs().getDouble("rev_billed_usage");
				Object[] parsedDataArray = new Object[] { evtDttm.getMillis(), Math.round(volume) };
				dealDataList.add(parsedDataArray);
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
}
