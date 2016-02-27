package se.signa.signature.rest;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import se.signa.signature.common.SignatureException;
import se.signa.signature.dio.BDBStatBoardResult;
import se.signa.signature.dio.BusinessDashboardInput;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.helpers.DBHelper.ExecuteSelectStamentResult;
import se.signa.signature.helpers.RefDBHelper;
import se.signa.signature.helpers.UsageDBHelper;

@Path("/bdbstatboard")
public class BDBStatBoardApi extends StigWebApi
{
	private static Logger logger = Logger.getLogger(BDBStatBoardApi.class);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult bdbStatBoard(BusinessDashboardInput input)
	{
		input.validate();
		input.checkRules();

		DateTime now = (new DateTime()).minusDays(1);
		DateTime oneWeekBefore = now.minusDays(7);
		DateTime twoWeekBefore = now.minusDays(14);

		String activeDeals = getActiveDeals();
		String newDeals = getNewDeals();
		String volumeTrend = getVolumeTrend(now, oneWeekBefore, twoWeekBefore);
		String marginTrend = getMarginTrend(now, oneWeekBefore, twoWeekBefore);

		String notification = "Business Statblock rendered successfully !!!";
		logger.info(notification);

		return new BDBStatBoardResult(notification, activeDeals, newDeals, volumeTrend, marginTrend);
	}

	private String getNewDeals()
	{

		ExecuteSelectStamentResult result = null;
		String activeDeals = null;
		try
		{
			String query = "select count(1) as count from deal  where  del_from_dttm >= add_months(sysdate,-1)  and del_from_dttm <=sysdate ";

			logger.debug("Query for statblock new deals : " + query);

			result = RefDBHelper.getDB().execute(query);
			if (result.next())
			{
				activeDeals = result.getRs().getString("count");
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
		return activeDeals;
	}

	private String getActiveDeals()
	{

		ExecuteSelectStamentResult result = null;
		String activeDeals = null;
		try
		{
			String query = "select count(1) as count from deal where (del_to_dttm+del_grace_period_days) >= sysdate and del_from_dttm <=sysdate";

			logger.debug("Query for statblock active deals : " + query);

			result = RefDBHelper.getDB().execute(query);
			if (result.next())
			{
				activeDeals = result.getRs().getString("count");
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
		return activeDeals;
	}

	private String getVolumeTrend(DateTime now, DateTime oneWeekBefore, DateTime twoWeekBefore)
	{
		String volumeTrend = null;

		logger.info("Volume trend : " + now);
		logger.info("Volume trend : " + oneWeekBefore);
		logger.info("Volume trend : " + twoWeekBefore);

		Double totalVolumeForNum = getVolumeForPeriod(oneWeekBefore, now);
		Double totalVolumeForDen = getVolumeForPeriod(twoWeekBefore, oneWeekBefore);
		Double volTrd = 0d;

		logger.info("Volume trend : vol1 " + totalVolumeForNum);
		logger.info("Volume trend : vol2" + totalVolumeForDen);

		if (totalVolumeForNum != 0d && totalVolumeForDen != 0d)
			volTrd = ((totalVolumeForNum - totalVolumeForDen) / totalVolumeForDen) * 100;
		if (volTrd >= 0)
			volumeTrend = "+" + Math.round(volTrd) + "% ";
		else
			volumeTrend = Math.round(volTrd) + "% ";
		return volumeTrend;
	}

	private String getMarginTrend(DateTime now, DateTime oneWeekBefore, DateTime twoWeekBefore)
	{
		String marginTrend = null;

		Double oneWeekMarginTrend = getMarginTrendForCurrentWeek();
		Double twoWeekMarginTrend = getMarginTrendForLastWeek();
		Double marTrd = 0d;

		logger.info("oneWeekMarginTrend " + oneWeekMarginTrend);
		logger.info("twoWeekMarginTrend " + twoWeekMarginTrend);

		if (oneWeekMarginTrend != 0d && twoWeekMarginTrend != 0d)
			marTrd = ((oneWeekMarginTrend - twoWeekMarginTrend) / twoWeekMarginTrend) * 100;

		logger.info("marTrd " + marTrd);

		if (marTrd >= 0)
			marginTrend = "+" + Math.round(marTrd) + "% ";
		else
			marginTrend = Math.round(marTrd) + "% ";
		return marginTrend;
	}

	private Double getMarginTrendForPeriod(DateTime fromDttm, DateTime toDttm)
	{
		ExecuteSelectStamentResult result = null;
		Double margin = 0d;
		try
		{
			String refName = RefDBHelper.getDB().getUserName();

			String query = "select cur_name, sum((in_margin + out_margin)) as margin from ";
			query = query + "(select cur_name ,evt_dttm, sum(( IN_DEAL_RATE - (OUT_REV_AMT /(OUT_REV_BILLED_USAGE/60))) ";
			query = query + " * (IN_REV_BILLED_USAGE/60)) in_margin , in_deal_id  from margin_summary mar, " + refName + ".currency cur ,  ";
			query = query + "" + refName + ".deal del where mar.in_deal_id = del.del_id and del.cur_id = cur.cur_id  and evt_dttm >= ? and evt_dttm <= ?  group by ";
			query = query + "in_deal_cur_id,cur_name,in_deal_id,evt_dttm)  m1, (select sum(((IN_REV_AMT /(IN_REV_BILLED_USAGE/60.00)) - OUT_DEAL_RATE ) * (OUT_REV_BILLED_USAGE/60))";
			query = query + "out_margin,out_deal_id ,evt_dttm from margin_summary where out_deal_id is not null and  evt_dttm >= ? and evt_dttm <= ?  group by out_deal_id,evt_dttm)  m2 where m1.in_deal_id = m2.out_deal_id ";
			query = query + "and m1.evt_dttm = m2.evt_dttm and m1.evt_dttm <= sysdate group by cur_name ";

			/*

			String query = "select cur_name , (in_margin - out_margin) as margin from ";
			query = query + "(select cur_name , sum(( IN_DEAL_RATE -  (OUT_REV_AMT /(OUT_REV_BILLED_USAGE/60.000)) ) * IN_REV_BILLED_USAGE) in_margin , in_deal_id ";
			query = query + " from margin_summary mar, " + refName + ".currency cur , " + refName + ".deal del where mar.in_deal_id = del.del_id and del.cur_id = cur.cur_id and (in_deal_id !=0 or out_deal_id !=0) ";
			query = query + " group by in_deal_cur_id,cur_name,in_deal_id)  m1, ";
			query = query + "(select sum(( (IN_REV_AMT /(IN_REV_BILLED_USAGE/60.000))  - OUT_DEAL_RATE  ) * OUT_REV_BILLED_USAGE) out_margin,out_deal_id ";
			query = query + "from margin_summary where evt_dttm >= ? and evt_dttm <= ? and (in_deal_id !=0 or out_deal_id !=0) group by out_deal_id)  m2 ";
			query = query + "where m1.in_deal_id = m2.out_deal_id  ";
			*/
			logger.debug("Query for statblock margin trend : " + query);

			result = UsageDBHelper.getDB().execute(query, new Object[] { fromDttm, toDttm, fromDttm, toDttm });
			while (result.next())
			{
				margin = result.getRs().getDouble("margin");
				String curName = result.getRs().getString("cur_name");
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
		return margin;
	}

	private Double getMarginTrendForCurrentWeek()
	{
		ExecuteSelectStamentResult result = null;
		Double margin = 0d;
		try
		{
			String refName = RefDBHelper.getDB().getUserName();

			String query = " select cur_name, sum((in_margin + out_margin)) as margin from ";
			query = query + "(select cur_name ,evt_dttm, sum(( IN_DEAL_RATE - (OUT_REV_AMT /(OUT_REV_BILLED_USAGE/60))) ";
			query = query + "* (IN_REV_BILLED_USAGE/60)) in_margin , in_deal_id  from margin_summary mar, " + refName + ".currency cur ,  ";
			query = query + " " + refName + ".deal del where mar.in_deal_id = del.del_id and del.cur_id = cur.cur_id  and evt_dttm >= (sysdate -8) and evt_dttm <= (sysdate-1)  and in_deal_id is not null  group by ";
			query = query + " in_deal_cur_id,cur_name,in_deal_id,evt_dttm)  m1, (select sum(((IN_REV_AMT /(IN_REV_BILLED_USAGE/60.00)) - OUT_DEAL_RATE ) * (OUT_REV_BILLED_USAGE/60)) ";
			query = query + " out_margin,out_deal_id ,evt_dttm from margin_summary where out_deal_id is not null  and  evt_dttm >= (sysdate -8) and evt_dttm <= (sysdate-1)   group by out_deal_id,evt_dttm)  m2 where m1.in_deal_id = m2.out_deal_id ";
			query = query + " and m1.evt_dttm = m2.evt_dttm  group by cur_name ";

			logger.info("Query for getting statblock margin trend with data filter : " + query);

			result = UsageDBHelper.getDB().execute(query);
			while (result.next())
			{
				margin = result.getRs().getDouble("margin");
				String curName = result.getRs().getString("cur_name");
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
		return margin;
	}

	private Double getMarginTrendForLastWeek()
	{
		ExecuteSelectStamentResult result = null;
		Double margin = 0d;
		try
		{
			String refName = RefDBHelper.getDB().getUserName();

			String query = " select cur_name, sum((in_margin + out_margin)) as margin from ";
			query = query + "(select cur_name ,evt_dttm, sum(( IN_DEAL_RATE - (OUT_REV_AMT /(OUT_REV_BILLED_USAGE/60))) ";
			query = query + "* (IN_REV_BILLED_USAGE/60)) in_margin , in_deal_id  from margin_summary mar, " + refName + ".currency cur ,  ";
			query = query + " " + refName + ".deal del where mar.in_deal_id = del.del_id and del.cur_id = cur.cur_id  and evt_dttm >= (sysdate -15) and evt_dttm <= (sysdate-8)  and in_deal_id is not null group by ";
			query = query + " in_deal_cur_id,cur_name,in_deal_id,evt_dttm)  m1, (select sum(((IN_REV_AMT /(IN_REV_BILLED_USAGE/60.00)) - OUT_DEAL_RATE ) * (OUT_REV_BILLED_USAGE/60)) ";
			query = query + " out_margin,out_deal_id ,evt_dttm from margin_summary where out_deal_id is not null  and  evt_dttm >= (sysdate -15) and evt_dttm <= (sysdate-8)   group by out_deal_id,evt_dttm)  m2 where m1.in_deal_id = m2.out_deal_id ";
			query = query + " and m1.evt_dttm = m2.evt_dttm  group by cur_name ";

			logger.info("Query for getting statblock margin trend with data filter : " + query);

			result = UsageDBHelper.getDB().execute(query);
			while (result.next())
			{
				margin = result.getRs().getDouble("margin");
				String curName = result.getRs().getString("cur_name");
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
		return margin;
	}

	private Double getVolumeForPeriod(DateTime fromDttm, DateTime toDttm)
	{

		ExecuteSelectStamentResult result = null;
		Double actualUsageForPeriod = null;
		try
		{
			String query = "select sum(in_rev_billed_usage)/60.00 as rev_billed_usage from margin_summary where ";
			query = query + " evt_dttm >= ? and evt_dttm <= ?  and (in_deal_id !=0 or out_deal_id !=0) ";

			logger.debug("Query for statblock volume trend : " + query);

			result = UsageDBHelper.getDB().execute(query, new Object[] { fromDttm, toDttm });
			if (result.next())
			{
				actualUsageForPeriod = result.getRs().getDouble("rev_billed_usage");
			}
			else
			{
				actualUsageForPeriod = 0d;
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
		return actualUsageForPeriod;
	}
}
