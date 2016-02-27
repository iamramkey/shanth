package se.signa.signature.rest;

import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import se.signa.signature.common.SignatureException;
import se.signa.signature.dio.DDBStatBoardResult;
import se.signa.signature.dio.DashboardInput;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.gen.dba.DealDba;
import se.signa.signature.gen.dbo.Deal;
import se.signa.signature.helpers.DBHelper.ExecuteSelectStamentResult;
import se.signa.signature.helpers.RefDBHelper;
import se.signa.signature.helpers.UsageDBHelper;

@Path("/ddbstatboard")
public class DDBStatBoardApi extends StigWebApi
{
	private static Logger logger = Logger.getLogger(DDBStatBoardApi.class);
	private static DecimalFormat dfr = new DecimalFormat("#.####");
	private static DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(Locale.US);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult ddbStatBoard(DashboardInput input)
	{
		input.validate();
		input.checkRules();

		dfr.setRoundingMode(RoundingMode.CEILING);
		DecimalFormatSymbols symbols = dfr.getDecimalFormatSymbols();

		symbols.setGroupingSeparator(' ');
		df.setDecimalFormatSymbols(symbols);

		Deal deal = DealDba.getI().fetchBydelName(input.deal);
		int dealId = deal.getDelId();
		DateTime now = new DateTime();
		DateTime oneWeekBefore = now.minusWeeks(1);
		DateTime twoWeekBefore = now.minusWeeks(2);

		String currentMargin = getCurrentMargin(deal);
		String projectedMargin = getProjectedMargin(deal);
		VolumeTrendModel volumeTrendModel = new VolumeTrendModel();
		getVolumeTrend(dealId, volumeTrendModel, now, oneWeekBefore, twoWeekBefore);
		String marginTrend = getMarginTrend(dealId, now, oneWeekBefore, twoWeekBefore);

		String notification = "Statblock rendered successfully !!!";
		logger.info(notification);
		return new DDBStatBoardResult(notification, currentMargin, projectedMargin, volumeTrendModel.status, marginTrend, volumeTrendModel.icon);
	}

	private String getProjectedMargin(Deal deal)
	{
		Map<String, Double> commitedUsage = getCommitedUsage(deal);
		Double inCurrentUsage = getInCurrentUsage(deal);
		Double outCurrentUsage = getOutCurrentUsage(deal);
		String projectedMargin = getProjectedMargin(commitedUsage, inCurrentUsage, outCurrentUsage, deal);

		return projectedMargin;
	}

	private String getProjectedMargin(Map<String, Double> commitedUsage, Double inCurrentUsage, Double outCurrentUsage, Deal deal)
	{
		ExecuteSelectStamentResult result = null;
		String margin = "" + 0d;
		try
		{
			String refName = RefDBHelper.getDB().getUserName();

			Double inProjectedUsage = commitedUsage.get("In") - inCurrentUsage;
			Double outProjectedUsage = commitedUsage.get("Out") - outCurrentUsage;
			StringBuffer query = new StringBuffer();

			query.append(" select m1.cur_name cur_name , (m1.margin + m2.margin) as margin from (select cur_name , round((in_margin + out_margin)) as margin from ");
			query.append(" (select cur_name , sum(( IN_DEAL_RATE - (OUT_REV_AMT /(OUT_REV_BILLED_USAGE/60.000)) ) * IN_REV_BILLED_USAGE/60.00) in_margin , in_deal_id ");
			query.append(" from margin_summary mar, " + refName + ".currency cur , " + refName + ".deal del where mar.in_deal_id = del.del_id and del.cur_id = cur.cur_id and in_deal_id = ? and evt_dttm <= sysdate ");
			query.append(" group by in_deal_cur_id,cur_name,in_deal_id)  m1, ");
			query.append(" (select sum((IN_REV_AMT /(IN_REV_BILLED_USAGE/60.000) - OUT_DEAL_RATE ) * (OUT_REV_BILLED_USAGE/60.00)) out_margin,out_deal_id ");
			query.append(" from margin_summary where out_deal_id = ? and evt_dttm <= sysdate  group by out_deal_id)  m2 ");
			query.append(" where m1.in_deal_id = m2.out_deal_id) m1, ");
			query.append(" (select cur_name , round(((in_margin + out_margin)/7 * round((trunc(m1.del_to_dttm) - sysdate)))) as margin ");
			query.append(" from (select cur_name , sum(( IN_DEAL_RATE - (OUT_REV_AMT /(OUT_REV_BILLED_USAGE/60.000)) ) * IN_REV_BILLED_USAGE/60.00) in_margin , in_deal_id, del.del_to_dttm ");
			query.append(" from margin_summary mar, " + refName + ".currency cur , " + refName + ".deal del where mar.in_deal_id = del.del_id and del.cur_id = cur.cur_id and in_deal_id = ? and evt_dttm <= sysdate and evt_dttm >= (sysdate -7) ");
			query.append(" group by in_deal_cur_id,cur_name,in_deal_id, del_to_dttm)  m1, ");
			query.append(" (select sum((IN_REV_AMT /(IN_REV_BILLED_USAGE/60.000) - OUT_DEAL_RATE ) * (OUT_REV_BILLED_USAGE/60.00)) out_margin,out_deal_id ");
			query.append(" from margin_summary where out_deal_id = ? and evt_dttm <= sysdate and evt_dttm >= (sysdate -7) group by out_deal_id)  m2 ");
			query.append(" where m1.in_deal_id = m2.out_deal_id) m2  where m1.cur_name=m2.cur_name ");

			logger.debug("Query for getting statblock projected margin data : " + query);

			logger.info("getProjectedMargin:  " + query);

			result = UsageDBHelper.getDB().execute(query.toString(), new Object[] { deal.getDelId(), deal.getDelId(), deal.getDelId(), deal.getDelId() });
			if (result.next())
			{
				margin = String.valueOf(df.format(Math.round(result.getRs().getDouble("margin"))));
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

	private Double getInCurrentUsage(Deal deal)
	{
		ExecuteSelectStamentResult result = null;
		Double inCurrentUsage = null;
		try
		{

			String query = "select sum(IN_REV_BILLED_USAGE) as in_current_usage ";
			query = query + " from margin_summary where in_deal_id = ?";

			logger.debug("Query for getting statblock in current usage : " + query);

			result = UsageDBHelper.getDB().execute(query, new Object[] { deal.getDelId() });
			if (result.next())
			{
				inCurrentUsage = result.getRs().getDouble("in_current_usage");
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
		return inCurrentUsage;
	}

	private Double getOutCurrentUsage(Deal deal)
	{
		ExecuteSelectStamentResult result = null;
		Double outCurrentUsage = null;
		try
		{

			String query = "select sum(OUT_REV_BILLED_USAGE) as out_current_usage ";
			query = query + " from margin_summary where out_deal_id = ?";

			logger.debug("Query for getting statblock out current usage : " + query);

			result = UsageDBHelper.getDB().execute(query, new Object[] { deal.getDelId() });
			if (result.next())
			{
				outCurrentUsage = result.getRs().getDouble("out_current_usage");
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
		return outCurrentUsage;
	}

	private Map<String, Double> getCommitedUsage(Deal deal)
	{

		ExecuteSelectStamentResult result = null;
		Map<String, Double> commitedUsage = new HashMap<String, Double>();
		commitedUsage.put("In", 0d);
		commitedUsage.put("Out", 0d);
		try
		{
			String query = "select sum(dlt_to_val) as dlt_to_val, dlt_direction  ";
			query = query + "from deal_tier  where del_id = ? and dlt_comm_fl =  'Y' group by dlt_direction  ";

			logger.debug("Query for getting statblock committed usage : " + query);

			result = RefDBHelper.getDB().execute(query, new Object[] { deal.getDelId() });
			while (result.next())
			{
				Double value = result.getRs().getDouble("dlt_to_val");
				String key = result.getRs().getString("dlt_direction");
				commitedUsage.put(key, value);

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
		return commitedUsage;
	}

	private String getMarginTrend(int dealId, DateTime now, DateTime oneWeekBefore, DateTime twoWeekBefore)
	{
		String marginTrend = null;

		Double oneWeekMarginTrend = getMarginTrendForCurrentWeek(dealId);
		Double twoWeekMarginTrend = getMarginTrendForLastWeek(dealId);
		Double marTrd = 0d;

		logger.info("oneWeekMarginTrend : " + oneWeekMarginTrend);
		logger.info("twoWeekMarginTrend : " + twoWeekMarginTrend);

		if (oneWeekMarginTrend != 0d && twoWeekMarginTrend != 0d)
			marTrd = ((oneWeekMarginTrend - twoWeekMarginTrend) / twoWeekMarginTrend) * 100;

		logger.info("marTrd : " + marTrd);

		if (marTrd >= 0)
			marginTrend = "+" + Math.round(marTrd) + "% ";
		else
			marginTrend = Math.round(marTrd) + "% ";
		return marginTrend;
	}

	private void getVolumeTrend(int dealId, VolumeTrendModel volumeTrendModel, DateTime now, DateTime oneWeekBefore, DateTime twoWeekBefore)
	{
		String volumeTrendInQuery = getVolumeTrendQuery("I");
		String volumeTrendOutQuery = getVolumeTrendQuery("O");
		ExecuteSelectStamentResult result = null;
		int volumeInDays = 0;
		int volumeOutDays = 0;

		try
		{
			result = UsageDBHelper.getDB().execute(volumeTrendInQuery, new Object[] { dealId, dealId, dealId });
			if (result.next())
			{
				volumeInDays = result.getRs().getInt("volume_trend");
			}

			result = UsageDBHelper.getDB().execute(volumeTrendOutQuery, new Object[] { dealId, dealId, dealId });
			if (result.next())
			{
				volumeOutDays = result.getRs().getInt("volume_trend");
			}

			if (volumeInDays < -5 || volumeOutDays < -5)
			{
				volumeTrendModel.status = "Behind";
				volumeTrendModel.icon = "icon-arrow-down";
			}
			else if ((volumeInDays >= -5 || volumeOutDays >= -5) && (volumeInDays <= 5 || volumeOutDays <= 5))
			{
				volumeTrendModel.status = "On track";
				volumeTrendModel.icon = "icon-arrow-right";
			}
			else if (volumeInDays > 5 || volumeOutDays > 5)
			{
				volumeTrendModel.status = "Ahead";
				volumeTrendModel.icon = "icon-arrow-up";
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

	private String getVolumeTrendQuery(String direction)
	{
		String refName = RefDBHelper.getDB().getUserName();
		StringBuffer query = new StringBuffer();
		if (direction.contains("I"))
		{
			query.append(" select round((1-(reqVol/avgLast))) as volume_trend from ");
			query.append(" (select (sum(in_rev_billed_usage)/60.00  -  dlt_to_val )/ round(to_date (to_char(del_to_dttm,'DD-MON-YYYY'),'DD-MON-YYYY') - sysdate) ");
			query.append(" as reqVol, in_deal_id as del_id from margin_summary m left join (select sum(dlt_to_val) dlt_to_val, del_id from ");
			query.append(" " + refName + ".deal_tier where del_id= ? and dlt_comm_fl='Y' and dlt_direction='In' group by del_id) dlt ");
			query.append(" on m.in_deal_id=dlt.DEL_ID left join " + refName + ".deal del on m.in_deal_id=del.del_id ");
			query.append(" where in_deal_id = ? group by dlt_to_val,in_deal_id, round(to_date (to_char(del_to_dttm,'DD-MON-YYYY'),'DD-MON-YYYY') - sysdate)) m1 ");
			query.append(" left join (select round((sum(in_rev_billed_usage)/60.00)/7) as avgLast, in_deal_id as del_id from margin_summary where in_deal_id= ? and evt_dttm >= (sysdate -7) ");
			query.append(" and evt_dttm <=(sysdate -1 ) group by in_deal_id) m2 on m1.del_id=m2.del_id");
		}
		else if (direction.contains("O"))
		{
			query.append(" select round((1-(reqVol/avgLast))) as volume_trend from ");
			query.append(" (select (sum(out_rev_billed_usage)/60.00  -  dlt_to_val )/ round(to_date (to_char(del_to_dttm,'DD-MON-YYYY'),'DD-MON-YYYY') - sysdate) ");
			query.append(" as reqVol, out_deal_id as del_id from margin_summary m left join (select sum(dlt_to_val) dlt_to_val, del_id from ");
			query.append(" " + refName + ".deal_tier where del_id= ? and dlt_comm_fl='Y' and dlt_direction='In' group by del_id) dlt ");
			query.append(" on m.out_deal_id=dlt.DEL_ID left join " + refName + ".deal del on m.out_deal_id=del.del_id ");
			query.append(" where out_deal_id = ? group by dlt_to_val,out_deal_id, round(to_date (to_char(del_to_dttm,'DD-MON-YYYY'),'DD-MON-YYYY') - sysdate)) m1 ");
			query.append(" left join (select round((sum(out_rev_billed_usage)/60.00)/7) as avgLast, out_deal_id as del_id from margin_summary where out_deal_id= ? and evt_dttm >= (sysdate -7) ");
			query.append(" and evt_dttm <=(sysdate -1 ) group by out_deal_id) m2 on m1.del_id=m2.del_id");
		}
		logger.debug("Query for getting usage : " + query);

		return query.toString();
	}

	private Double getVolumeForPeriod(Integer dealId, DateTime fromDttm, DateTime toDttm)
	{

		ExecuteSelectStamentResult result = null;
		Double actualUsageForPeriod = null;
		try
		{
			String query = "select sum(in_rev_billed_usage) as rev_billed_usage from margin_summary where in_deal_id = ? ";
			query = query + " and evt_dttm >= ? and evt_dttm <= ?  ";

			logger.debug("Query for getting statblock volume trend with data filter : " + query);

			result = UsageDBHelper.getDB().execute(query, new Object[] { dealId, fromDttm, toDttm });
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

	private Double getMarginTrendForCurrentWeek(Integer dealId)
	{
		ExecuteSelectStamentResult result = null;
		Double margin = 0d;
		try
		{
			String refName = RefDBHelper.getDB().getUserName();

			String query = " select cur_name, sum((in_margin + out_margin)) as margin from ";
			query = query + "(select cur_name ,evt_dttm, sum(( IN_DEAL_RATE - (OUT_REV_AMT /(OUT_REV_BILLED_USAGE/60))) ";
			query = query + "* (IN_REV_BILLED_USAGE/60)) in_margin , in_deal_id  from margin_summary mar, " + refName + ".currency cur ,  ";
			query = query + " " + refName + ".deal del where mar.in_deal_id = del.del_id and del.cur_id = cur.cur_id  and evt_dttm >= (sysdate -8) and evt_dttm <= (sysdate-1)  and in_deal_id = ? group by ";
			query = query + " in_deal_cur_id,cur_name,in_deal_id,evt_dttm)  m1, (select sum(((IN_REV_AMT /(IN_REV_BILLED_USAGE/60.00)) - OUT_DEAL_RATE ) * (OUT_REV_BILLED_USAGE/60)) ";
			query = query + " out_margin,out_deal_id ,evt_dttm from margin_summary where out_deal_id = ? and  evt_dttm >= (sysdate -8) and evt_dttm <= (sysdate-1)   group by out_deal_id,evt_dttm)  m2 where m1.in_deal_id = m2.out_deal_id ";
			query = query + " and m1.evt_dttm = m2.evt_dttm  group by cur_name ";

			logger.debug("Query for getting statblock margin trend with data filter : " + query);

			result = UsageDBHelper.getDB().execute(query, new Object[] { dealId, dealId });
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

	private Double getMarginTrendForLastWeek(Integer dealId)
	{
		ExecuteSelectStamentResult result = null;
		Double margin = 0d;
		try
		{
			String refName = RefDBHelper.getDB().getUserName();

			String query = " select cur_name, sum((in_margin + out_margin)) as margin from ";
			query = query + "(select cur_name ,evt_dttm, sum(( IN_DEAL_RATE - (OUT_REV_AMT /(OUT_REV_BILLED_USAGE/60))) ";
			query = query + "* (IN_REV_BILLED_USAGE/60)) in_margin , in_deal_id  from margin_summary mar, " + refName + ".currency cur ,  ";
			query = query + " " + refName + ".deal del where mar.in_deal_id = del.del_id and del.cur_id = cur.cur_id  and evt_dttm >= (sysdate -15) and evt_dttm <= (sysdate-8)  and in_deal_id = ? group by ";
			query = query + " in_deal_cur_id,cur_name,in_deal_id,evt_dttm)  m1, (select sum(((IN_REV_AMT /(IN_REV_BILLED_USAGE/60.00)) - OUT_DEAL_RATE ) * (OUT_REV_BILLED_USAGE/60)) ";
			query = query + " out_margin,out_deal_id ,evt_dttm from margin_summary where out_deal_id = ? and  evt_dttm >= (sysdate -15) and evt_dttm <= (sysdate-8)   group by out_deal_id,evt_dttm)  m2 where m1.in_deal_id = m2.out_deal_id ";
			query = query + " and m1.evt_dttm = m2.evt_dttm  group by cur_name ";

			logger.debug("Query for getting statblock margin trend with data filter : " + query);

			result = UsageDBHelper.getDB().execute(query, new Object[] { dealId, dealId });
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

	private String getCurrentMargin(Deal deal)
	{
		ExecuteSelectStamentResult result = null;
		String margin = "" + 0d;
		try
		{
			String refName = RefDBHelper.getDB().getUserName();
			//			String query = "select  cur.cur_name as cur_name, ((sum((IN_REV_AMT/IN_REV_BILLED_USAGE) - (In_deal_Rate)) * sum((in_rev_billed_usage)/60.000000)) +  ";
			//			query = query + " (sum((out_rev_billed_usage)/60.00000)*(sum( (out_deal_rate) - OUT_REV_AMT/OUT_REV_BILLED_USAGE )))) as margin from margin_summary mar , ";
			//			query = query + "" + refName + ".deal del ," + refName + ".currency cur  where mar.in_deal_id = del.del_id and";
			//			query = query + "  del.cur_id = cur.cur_id and in_deal_id = ? or out_deal_id = ? group by cur.cur_name";

			String query = " select cur_name , (in_margin + out_margin) as margin from ";
			query = query + " (select cur_name , sum(( IN_DEAL_RATE - (OUT_REV_AMT /(OUT_REV_BILLED_USAGE/60.000)) ) * IN_REV_BILLED_USAGE/60.00) in_margin , in_deal_id ";
			query = query + " from margin_summary mar, " + refName + ".currency cur , " + refName + ".deal del where mar.in_deal_id = del.del_id and del.cur_id = cur.cur_id and in_deal_id = ? and evt_dttm <= sysdate ";
			query = query + " group by in_deal_cur_id,cur_name,in_deal_id)  m1, ";
			query = query + " (select sum((IN_REV_AMT /(IN_REV_BILLED_USAGE/60.000) - OUT_DEAL_RATE ) * (OUT_REV_BILLED_USAGE/60.00)) out_margin,out_deal_id ";
			query = query + " from margin_summary where out_deal_id = ? and evt_dttm <= sysdate  group by out_deal_id)  m2 ";
			query = query + " where m1.in_deal_id = m2.out_deal_id ";

			logger.debug("Query for getting statblock current margin : " + query);

			result = UsageDBHelper.getDB().execute(query, new Object[] { deal.getDelId(), deal.getDelId() });
			if (result.next())
			{
				margin = String.valueOf(df.format(Math.round(result.getRs().getDouble("margin"))));
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

	class VolumeTrendModel
	{
		public String status;
		public String icon;

		public VolumeTrendModel()
		{
		}
	}

}
