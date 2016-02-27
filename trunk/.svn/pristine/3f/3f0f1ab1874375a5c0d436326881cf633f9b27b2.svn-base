package se.signa.signature.rest;

import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import se.signa.signature.common.SignatureException;
import se.signa.signature.dio.BDBShortfallWarningListResult;
import se.signa.signature.dio.BusinessDashboardInput;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.helpers.DBHelper.ExecuteSelectStamentResult;
import se.signa.signature.helpers.RefDBHelper;
import se.signa.signature.helpers.UsageDBHelper;

@Path("/bdbshortfallwarninglist")
public class BDBShortfallWarningListApi extends StigWebApi
{
	private static Logger logger = Logger.getLogger(BDBShortfallWarningListApi.class);
	private static DecimalFormat dfr = new DecimalFormat("#.####");
	private static DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(Locale.US);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult bdbShortfallWarningList(BusinessDashboardInput input)
	{
		input.validate();
		input.checkRules();

		dfr.setRoundingMode(RoundingMode.CEILING);
		DecimalFormatSymbols symbols = dfr.getDecimalFormatSymbols();

		symbols.setGroupingSeparator(' ');
		df.setDecimalFormatSymbols(symbols);

		List<ShortfallWarningData> shortfallWarningDataList = new ArrayList<ShortfallWarningData>();
		getShortFallWarning(shortfallWarningDataList, "I", "PER");
		getShortFallWarning(shortfallWarningDataList, "I", "VOL");
		getShortFallWarning(shortfallWarningDataList, "O", "PER");
		getShortFallWarning(shortfallWarningDataList, "O", "VOL");

		String notification = "Short fall warning List Rendered Successfully !!!";
		logger.info(notification);
		return new BDBShortfallWarningListResult(notification, shortfallWarningDataList);
	}

	private void getShortFallWarning(List<ShortfallWarningData> shortfallWarningDataList, String direction, String filter)
	{

		ExecuteSelectStamentResult result = null;
		try
		{
			String query = getShortFallWarningQuery(direction);

			result = UsageDBHelper.getDB().execute(query);
			while (result.next())
			{
				Double commitedVolume = result.getRs().getDouble("commited");
				Double totalVolume = result.getRs().getDouble("total");
				Double oneWeekAvgVolume = result.getRs().getDouble("avg_one_week");
				int remainingDays = result.getRs().getInt("remaining_days");
				int dealId = result.getRs().getInt("deal_id");
				String dealName = result.getRs().getString("deal_name");
				String accName = result.getRs().getString("acc_name");
				String dbgName = result.getRs().getString("dbg_name");

				ShortfallWarningData shortfallWarningData = new ShortfallWarningData();

				shortfallWarningData.data = dealId + "-" + accName + "-" + dbgName;

				int month = 0;
				if (remainingDays > 0)
					month = remainingDays / 30;
				if (month > 1)
					shortfallWarningData.period = month + " months";
				else
					shortfallWarningData.period = remainingDays + " days";

				shortfallWarningData.deal = dealName;
				shortfallWarningData.account = accName;

				shortfallWarningData.labelColor = "#67809F";
				if (direction.contains("I"))
				{
					shortfallWarningData.color = "#E87E04";
					shortfallWarningData.primaryFilter = "In";
					shortfallWarningData.icon = "icon-arrow-right";
				}
				else
				{
					shortfallWarningData.color = "#E87E04";
					shortfallWarningData.primaryFilter = "Out";
					shortfallWarningData.icon = "icon-arrow-left";
				}

				if (filter.equalsIgnoreCase("PER"))
				{

					logger.info(dealId);
					logger.info(dbgName);
					logger.info(commitedVolume);
					logger.info(totalVolume);
					logger.info(oneWeekAvgVolume);

					Double sfwInPer = ((commitedVolume - (totalVolume + (oneWeekAvgVolume * remainingDays))) / commitedVolume) * 100;

					logger.info("sfwInPer " + sfwInPer);

					if (sfwInPer <= 0)
						continue;

					shortfallWarningData.subData = String.valueOf(df.format(Math.round(sfwInPer))) + "%";
					shortfallWarningData.secondaryFilter = "PER";

				}
				else
				{
					Double sfwInVol = (commitedVolume - (totalVolume + (oneWeekAvgVolume * remainingDays)));

					if (sfwInVol <= 0)
						continue;

					shortfallWarningData.subData = String.valueOf(df.format(Math.round(sfwInVol)));
					shortfallWarningData.secondaryFilter = "VOL";
				}

				shortfallWarningDataList.add(shortfallWarningData);
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

	private String getShortFallWarningQuery(String direction)
	{
		String query = "";
		String refName = RefDBHelper.getDB().getUserName();
		if (direction.contains("I"))
		{

			query = " select dlt.DLT_TO_VAL as commited, total, avg_one_week, round(to_date (to_char(del_to_dttm,'DD-MON-YYYY'),'DD-MON-YYYY') - (sysdate-2)) as remaining_days, deal_name, del.del_id as deal_id, acc_company_name as acc_name, dbg_name   from ( ";
			query = query + " select (m1.rev_billed_usage/60.00)/7 as avg_one_week, m2.rev_billed_usage/60.00 total,m1.dbg_name as dbg_name , ";
			query = query + " m1.del_id as del_id , m1.del_name as deal_name,m1.acc_company_name as acc_company_name, 'In' as direction, m1.dbg_id  from ";
			query = query + " (select sum(in_rev_billed_usage) as rev_billed_usage, dbg.dbg_name, del.del_id , del.del_name  ";
			query = query + " , acc.acc_company_name, dbg.dbg_id from margin_summary msu , " + refName + ".deal_band_group dbg , " + refName + ".deal del , ";
			query = query + " " + refName + ".customer_vendor acc where msu.in_dbg_id = dbg.dbg_id and msu.in_deal_id = del.del_id and  ";
			query = query + " del.acc_id = acc.acc_id and msu.evt_dttm >= (sysdate -8) and msu.evt_dttm < (sysdate-1)  group by dbg.dbg_name,del.del_name,del.del_id,acc.acc_company_name, dbg.dbg_id ";
			query = query + " ) m1, ";
			query = query + " (select sum(in_rev_billed_usage) as rev_billed_usage,dbg.dbg_name,del.del_id ";
			query = query + " , acc.acc_company_name, dbg.dbg_id from margin_summary msu , " + refName + ".deal_band_group dbg , " + refName + ".deal del , ";
			query = query + " " + refName + ".customer_vendor acc where msu.in_dbg_id = dbg.dbg_id and msu.in_deal_id = del.del_id and  ";
			query = query + " del.acc_id = acc.acc_id and msu.evt_dttm < (sysdate -1) group by dbg.dbg_name,del.del_id ,acc.acc_company_name, dbg.dbg_id  order by del_id desc ";
			query = query + " ) m2 where  m1.del_id=m2.del_id and m1.dbg_name=m2.dbg_name  ";
			query = query + " ) outerm1 ";
			query = query + " left join " + refName + ".deal_tier dlt on outerm1.dbg_id=dlt.dbg_id ";
			query = query + " left join " + refName + ".deal del on del.del_id=outerm1.del_id ";
			query = query + " where dlt_comm_fl='Y' and outerm1.del_id in ( select del_id from " + refName + ".deal where del_to_dttm >= sysdate and round((sysdate) - to_date (to_char(del_from_dttm,'DD-MON-YYYY'),'DD-MON-YYYY'))>8 ) ";

			/*	
				query = "select dlt.DLT_TO_VAL as commited, round(sum(m1.in_rev_billed_usage/60.00)) as total, round(sum(m2.in_rev_billed_usage/60.00)/7) as avg_one_week,  ";
				query = query + " round(to_date (to_char(del_to_dttm,'DD-MON-YYYY'),'DD-MON-YYYY') - sysdate) as remaining_days,";
				query = query + " del.del_name as deal_name ,del.del_id as deal_id ,acc.acc_company_name as acc_name,dbg.dbg_name as dbg_name ";
				query = query + " from margin_summary m1 ";
				query = query + " left join " + refName + ".deal del on m1.in_deal_id=del.del_id ";
				query = query + " left join " + refName + ".deal_tier dlt on del.del_id=dlt.del_id ";
				query = query + " left join " + refName + ".customer_vendor acc on del.acc_id=acc.acc_id ";
				query = query + " left join margin_summary m2 on m1.in_deal_id=m2.in_deal_id ";
				query = query + " left join " + refName + ".deal_band_group dbg on m1.in_dbg_id=dbg.dbg_id ";
				query = query + " where dlt.dlt_comm_fl='Y' and dlt_direction like'%I%' ";
				query = query + " and m2.evt_dttm <=sysdate and m2.evt_dttm > (sysdate -7) and del.del_id in ( select del_id from " + refName + ".deal where del_to_dttm >=sysdate ) ";
				query = query + " group by dlt.DLT_TO_VAL,del.del_name,round(to_date (to_char(del_to_dttm,'DD-MON-YYYY'),'DD-MON-YYYY') - sysdate), ";
				query = query + " del.del_name,acc.acc_company_name,dbg.dbg_name ,del.del_id";
			*/
			logger.debug("Query for in short fall warning list : " + query);
		}
		else if (direction.contains("O"))
		{
			query = " select dlt.DLT_TO_VAL as commited, total, avg_one_week, round(to_date (to_char(del_to_dttm,'DD-MON-YYYY'),'DD-MON-YYYY') - (sysdate-2)) as remaining_days, deal_name, del.del_id as deal_id, acc_company_name as acc_name, dbg_name   from ( ";
			query = query + " select (m1.rev_billed_usage/60.00)/7 as avg_one_week, m2.rev_billed_usage/60.00 total,m1.dbg_name as dbg_name , ";
			query = query + " m1.del_id as del_id , m1.del_name as deal_name,m1.acc_company_name as acc_company_name, 'Out' as direction, m1.dbg_id  from ";
			query = query + " (select sum(out_rev_billed_usage) as rev_billed_usage, dbg.dbg_name, del.del_id , del.del_name  ";
			query = query + " , acc.acc_company_name, dbg.dbg_id from margin_summary msu , " + refName + ".deal_band_group dbg , " + refName + ".deal del , ";
			query = query + " " + refName + ".customer_vendor acc where msu.out_dbg_id = dbg.dbg_id and msu.out_deal_id = del.del_id and  ";
			query = query + " del.acc_id = acc.acc_id and msu.evt_dttm >= (sysdate -8) and msu.evt_dttm < (sysdate-1)  group by dbg.dbg_name,del.del_name,del.del_id,acc.acc_company_name, dbg.dbg_id ";
			query = query + " ) m1, ";
			query = query + " (select sum(out_rev_billed_usage) as rev_billed_usage,dbg.dbg_name,del.del_id ";
			query = query + " , acc.acc_company_name, dbg.dbg_id from margin_summary msu , " + refName + ".deal_band_group dbg , " + refName + ".deal del , ";
			query = query + " " + refName + ".customer_vendor acc where msu.out_dbg_id = dbg.dbg_id and msu.out_deal_id = del.del_id and  ";
			query = query + " del.acc_id = acc.acc_id and msu.evt_dttm < (sysdate -1) group by dbg.dbg_name,del.del_id ,acc.acc_company_name, dbg.dbg_id  order by del_id desc ";
			query = query + " ) m2 where  m1.del_id=m2.del_id and m1.dbg_name=m2.dbg_name  ";
			query = query + " ) outerm1 ";
			query = query + " left join " + refName + ".deal_tier dlt on outerm1.dbg_id=dlt.dbg_id ";
			query = query + " left join " + refName + ".deal del on del.del_id=outerm1.del_id ";
			query = query + " where dlt_comm_fl='Y' and outerm1.del_id in ( select del_id from " + refName + ".deal where del_to_dttm >= sysdate and round((sysdate) - to_date (to_char(del_from_dttm,'DD-MON-YYYY'),'DD-MON-YYYY'))>8 ) ";

			logger.debug("Query for out short fall warning list : " + query);
		}
		return query;
	}

	public class ShortfallWarningData
	{
		public String icon;
		public String color;
		public String labelColor;
		public String data;
		public String subData;
		public String period;
		public String primaryFilter;
		public String secondaryFilter;
		public String deal;
		public String account;
	}
}
