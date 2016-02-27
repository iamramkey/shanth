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
import se.signa.signature.dio.BDBMarginTrendChartResult;
import se.signa.signature.dio.BusinessDashboardInput;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.helpers.DBHelper.ExecuteSelectStamentResult;
import se.signa.signature.helpers.RefDBHelper;
import se.signa.signature.helpers.UsageDBHelper;

@Path("/bdbmargintrendchart")
public class BDBMarginTrendChartApi extends StigWebApi
{
	private static Logger logger = Logger.getLogger(BDBMarginTrendChartApi.class);
	private static DecimalFormat dfr = new DecimalFormat("#.####");

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult bdbMarginTrendChart(BusinessDashboardInput input)
	{
		input.validate();
		input.checkRules();

		dfr.setRoundingMode(RoundingMode.CEILING);

		List<Object[]> dataList = new LinkedList<Object[]>();

		getMargin(dataList);

		String notification = "Margin Trend rendered successfully !!!";
		logger.info(notification);
		return new BDBMarginTrendChartResult(notification, dataList);
	}

	public DateTime getDateTime(Timestamp timestamp)
	{
		if (timestamp == null)
			return null;
		return new DateTime(timestamp);
	}

	private String getMargin(List<Object[]> dataList)
	{
		ExecuteSelectStamentResult result = null;
		String carrierName = null;
		try
		{
			String refName = RefDBHelper.getDB().getUserName();

			//			String query = "select evt_dttm , (((IN_REV_AMT/IN_REV_BILLED_USAGE) - (In_deal_Rate)) + ";
			//			query = query + "( (out_deal_rate) - OUT_REV_AMT/OUT_REV_BILLED_USAGE )) as margin from margin_summary ";
			//			query = query + " where in_deal_id = ? or out_deal_id = ? order by evt_dttm";

			/*String query = "select cur_name ,m1.evt_dttm as evt_dttm, (in_margin - out_margin) as margin from ";
			query = query + "(select cur_name ,evt_dttm, sum(( (IN_REV_AMT /(IN_REV_BILLED_USAGE/60.000)) - IN_DEAL_RATE ) * IN_REV_BILLED_USAGE) in_margin , in_deal_id ";
			query = query + " from margin_summary mar, " + refName + ".currency cur , " + refName + ".deal del where mar.in_deal_id = del.del_id and del.cur_id = cur.cur_id ";
			query = query + " group by in_deal_cur_id,cur_name,in_deal_id,evt_dttm)  m1, ";
			query = query + "(select sum(OUT_DEAL_RATE - (OUT_REV_AMT /(OUT_REV_BILLED_USAGE/60.000)) * OUT_REV_BILLED_USAGE) out_margin,out_deal_id ,evt_dttm ";
			query = query + "from margin_summary where out_deal_id is not null group by out_deal_id,evt_dttm)  m2 ";
			query = query + "where m1.in_deal_id = m2.out_deal_id and m1.evt_dttm = m2.evt_dttm order by evt_dttm ";*/

			String query = "select cur_name ,m1.evt_dttm as evt_dttm, sum((in_margin + out_margin)) as margin from ";
			query = query + "(select cur_name ,evt_dttm, sum(( IN_DEAL_RATE - (OUT_REV_AMT /(OUT_REV_BILLED_USAGE/60))) ";
			query = query + " * (IN_REV_BILLED_USAGE/60)) in_margin , in_deal_id  from margin_summary mar, " + refName + ".currency cur ,  ";
			query = query + "" + refName + ".deal del where mar.in_deal_id = del.del_id and del.cur_id = cur.cur_id group by ";
			query = query + "in_deal_cur_id,cur_name,in_deal_id,evt_dttm)  m1, (select sum(((IN_REV_AMT /(IN_REV_BILLED_USAGE/60.00)) - OUT_DEAL_RATE ) * (OUT_REV_BILLED_USAGE/60))";
			query = query + "out_margin,out_deal_id ,evt_dttm from margin_summary where out_deal_id is not null group by out_deal_id,evt_dttm)  m2 where m1.in_deal_id = m2.out_deal_id ";
			query = query + "and m1.evt_dttm = m2.evt_dttm and m1.evt_dttm <= sysdate and m1.evt_dttm >= trunc(add_months(sysdate,-5),'MM')  group by cur_name ,m1.evt_dttm  order by evt_dttm ";

			logger.debug("Query for margin trend chart : " + query);

			logger.info(query);

			result = UsageDBHelper.getDB().execute(query);
			while (result.next())
			{
				DateTime evtDttm = getDateTime(result.getRs().getTimestamp("evt_dttm"));
				Double margin = result.getRs().getDouble("margin");
				String curName = result.getRs().getString("cur_name");
				Object[] parsedDataArray = new Object[] { evtDttm.getMillis(), Math.round(margin) };
				dataList.add(parsedDataArray);
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

	public class Series
	{
		public String name;
		public List<Double> data;
	}
}
