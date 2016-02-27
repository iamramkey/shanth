package se.signa.signature.rest;

import java.math.RoundingMode;
import java.sql.SQLException;
import java.sql.Timestamp;
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
import se.signa.signature.dio.BDBDealsCountResult;
import se.signa.signature.dio.BusinessDashboardInput;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.helpers.DBHelper.ExecuteSelectStamentResult;
import se.signa.signature.helpers.RefDBHelper;
import se.signa.signature.helpers.UsageDBHelper;

@Path("/bdbdealscount")
public class BDBDealsCountApi extends StigWebApi
{
	private static Logger logger = Logger.getLogger(BDBDealsCountApi.class);
	private static DecimalFormat dfr = new DecimalFormat("#.####");
	private static DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(Locale.US);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult bdbDealsCount(BusinessDashboardInput input)
	{
		input.validate();
		input.checkRules();

		dfr.setRoundingMode(RoundingMode.CEILING);
		DecimalFormatSymbols symbols = dfr.getDecimalFormatSymbols();

		symbols.setGroupingSeparator(' ');
		df.setDecimalFormatSymbols(symbols);

		List<NewDeals> newDealsList = new LinkedList<NewDeals>();
		getNewDealsData(newDealsList);

		List<ActiveDeals> activeDealsList = new LinkedList<ActiveDeals>();
		getActiveDealsData(activeDealsList);

		String notification = "Deals Count Rendered Successfully !!!";
		logger.info(notification);
		return new BDBDealsCountResult(notification, newDealsList, activeDealsList);
	}

	private void getActiveDealsData(List<ActiveDeals> newDealsList)
	{
		String refName = RefDBHelper.getDB().getUserName();
		ExecuteSelectStamentResult result = null;
		try
		{
			StringBuffer query = new StringBuffer();

			query.append(" select m1.acc_name,m1.deal_id,m1.valid_from,m1.valid_to,m1.in_deal_id ,   ");
			query.append(" m2.out_deal_id ,round(sum(m1.volume_in))  as volume_in , round(sum(m2.volume_out))  ");
			query.append(" as volume_out, round(sum(m1.committed_in)) as committed_in, round(sum(m2.committed_out))  ");
			query.append(" as committed_out , round(sum(m1.rev_in)) as rev_in, round(sum(m2.rev_out)) as rev_out  ");
			query.append(" from (select cust1.acc_company_name as acc_name, del1.del_id  as deal_id ,  ");
			query.append(" del1.del_from_dttm as valid_from , del1.del_to_dttm as valid_to,  ");
			query.append(" sum(in_rev_billed_usage)/60.00 as volume_in ,sum(dlt1.dlt_to_val) as  ");
			query.append(" committed_in,  sum(in_rev_amt) as rev_in , del1.del_id  as in_deal_id  ");
			query.append(" from " + refName + ".deal del1  ");
			query.append(" left join  " + refName + ".deal_tier dlt1  on dlt1.del_id=del1.del_id  ");
			query.append(" left join " + refName + ".customer_vendor cust1  on cust1.acc_id=del1.acc_id  ");
			query.append(" left join (select sum(in_rev_billed_usage) in_rev_billed_usage,sum(in_rev_amt) in_rev_amt, in_deal_id ,  ");
			query.append("  in_dbg_id from  margin_summary where evt_dttm <= sysdate group by in_deal_id , in_dbg_id ) m1  on m1.in_dbg_id = dlt1.dbg_id  ");
			query.append(" where dlt1.del_id=del1.del_id   and dlt1.dlt_comm_fl = 'Y'  ");
			query.append(" and dlt1.dlt_direction = 'In'  and  dlt1.del_id in  ");
			query.append(" (select del_id from " + refName + ".deal where  ");
			query.append(" (del_to_dttm + del_grace_period_days) >= sysdate)  ");
			query.append(" group by cust1.acc_company_name , del1.del_id ,  del1.del_from_dttm , del1.del_to_dttm ) m1,  ");
			query.append(" (select cust2.acc_company_name as acc_name, del2.del_id  as deal_id ,  del2.del_from_dttm as valid_from ,  ");
			query.append(" del2.del_to_dttm as valid_to, sum(out_rev_billed_usage)/60.00 as volume_out,  sum(dlt2.dlt_to_val) as committed_out ,  ");
			query.append(" sum(out_rev_amt) as rev_out , del2.del_id  as out_deal_id    from " + refName + ".deal del2  left join " + refName + ".deal_tier dlt2  on  ");
			query.append(" dlt2.del_id=del2.del_id  left join " + refName + ".customer_vendor cust2  on cust2.acc_id=del2.acc_id  ");
			query.append(" left join (select sum(out_rev_billed_usage) out_rev_billed_usage,sum(out_rev_amt) out_rev_amt, out_deal_id  ,  ");
			query.append(" out_dbg_id from  margin_summary where evt_dttm <= sysdate group by out_deal_id  , out_dbg_id ) m2 on m2.out_dbg_id = dlt2.dbg_id  ");
			query.append(" where dlt2.del_id=del2.del_id  and dlt2.dlt_comm_fl = 'Y' and dlt2.dlt_direction = 'Out' and  ");
			query.append(" dlt2.del_id in  (select del_id from " + refName + ".deal where     ");
			query.append(" (del_to_dttm + del_grace_period_days) >= sysdate and del_from_dttm <=sysdate )   ");
			query.append(" group by cust2.acc_company_name , del2.del_id  ,   del2.del_from_dttm , del2.del_to_dttm  ) m2  ");
			query.append(" where m1.deal_id=m2.deal_id  group by m1.acc_name,m1.deal_id,m1.valid_from,m1.valid_to,m1.in_deal_id, m2.out_deal_id  ");
			query.append(" order by in_deal_id  ");

			logger.debug("Query for active deal data : " + query.toString());

			DateFormat dateFormat = new SimpleDateFormat(Constants.DASH_BOARD_DTTM_FORMAT);
			result = UsageDBHelper.getDB().execute(query.toString());
			while (result.next())
			{
				String carrier = result.getRs().getString("acc_name");
				int dealId = result.getRs().getInt("deal_id");
				String validFrom = dateFormat.format(getDateTime(result.getRs().getTimestamp("valid_from")).toDate());
				String validTo = dateFormat.format(getDateTime(result.getRs().getTimestamp("valid_to")).toDate());
				Double inVolume = result.getRs().getDouble("volume_in");
				Double outVolume = result.getRs().getDouble("volume_out");
				Double committedIn = result.getRs().getDouble("committed_in");
				Double committedOut = result.getRs().getDouble("committed_out");
				Double revenueIn = result.getRs().getDouble("rev_in");
				Double revenueOut = result.getRs().getDouble("rev_out");

				ActiveDeals newDeals = new ActiveDeals(carrier, dealId, validFrom, validTo, inVolume, outVolume, committedIn, committedOut, revenueIn, revenueOut);
				newDealsList.add(newDeals);
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

	private void getNewDealsData(List<NewDeals> newDealsList)
	{
		String refName = RefDBHelper.getDB().getUserName();
		ExecuteSelectStamentResult result = null;
		try
		{
			StringBuffer query = new StringBuffer();

			query.append(" select m1.acc_name,m1.deal_id,m1.valid_from,m1.valid_to,m1.in_deal_id ,  ");
			query.append(" m2.out_deal_id ,round(sum(m1.volume_in))  as volume_in , round(sum(m2.volume_out))   ");
			query.append(" as volume_out, round(sum(m1.committed_in)) as committed_in, round(sum(m2.committed_out)) ");
			query.append(" as committed_out , round(sum(m1.rev_in)) as rev_in, round(sum(m2.rev_out)) as rev_out ");
			query.append(" from (select cust1.acc_company_name as acc_name, del1.del_id  as deal_id , ");
			query.append(" del1.del_from_dttm as valid_from , del1.del_to_dttm as valid_to,   ");
			query.append(" sum(in_rev_billed_usage)/60.00 as volume_in ,sum(dlt1.dlt_to_val) as ");
			query.append(" committed_in,  sum(in_rev_amt) as rev_in , del1.del_id  as in_deal_id     ");
			query.append(" from " + refName + ".deal del1  ");
			query.append(" left join  " + refName + ".deal_tier dlt1  on dlt1.del_id=del1.del_id ");
			query.append(" left join " + refName + ".customer_vendor cust1  on cust1.acc_id=del1.acc_id ");
			query.append(" left join (select sum(in_rev_billed_usage) in_rev_billed_usage,sum(in_rev_amt) in_rev_amt, in_deal_id , in_dlt_id , ");
			query.append(" in_dbg_id from  margin_summary where evt_dttm <= sysdate group by in_deal_id, in_dlt_id , in_dbg_id ) m1  on m1.in_dbg_id = dlt1.dbg_id ");
			query.append(" where dlt1.del_id=del1.del_id   and dlt1.dlt_comm_fl = 'Y' ");
			query.append(" and dlt1.dlt_direction = 'In'  and  dlt1.del_id in ");
			query.append(" (select del_id from " + refName + ".deal where del_from_dttm  >= add_months(sysdate,-1) ) ");
			query.append(" group by cust1.acc_company_name , del1.del_id ,  del1.del_from_dttm , del1.del_to_dttm ) m1, ");
			query.append(" (select cust2.acc_company_name as acc_name, del2.del_id  as deal_id ,  del2.del_from_dttm as valid_from , ");
			query.append(" del2.del_to_dttm as valid_to, sum(out_rev_billed_usage)/60.00 as volume_out,  sum(dlt2.dlt_to_val) as committed_out , ");
			query.append(" sum(out_rev_amt) as rev_out , del2.del_id as out_deal_id    from " + refName + ".deal del2  left join " + refName + ".deal_tier dlt2  on ");
			query.append(" dlt2.del_id=del2.del_id  left join " + refName + ".customer_vendor cust2  on cust2.acc_id=del2.acc_id ");
			query.append(" left join (select sum(out_rev_billed_usage) out_rev_billed_usage,sum(out_rev_amt) out_rev_amt, out_deal_id , out_dlt_id , ");
			query.append(" out_dbg_id from  margin_summary where evt_dttm <= sysdate group by out_deal_id , out_dlt_id , out_dbg_id ) m2 on m2.out_dbg_id = dlt2.dbg_id ");
			query.append(" where dlt2.del_id=del2.del_id  and dlt2.dlt_comm_fl = 'Y' and dlt2.dlt_direction = 'Out' and  ");
			query.append(" dlt2.del_id in  (select del_id from " + refName + ".deal where del_from_dttm  >= add_months(sysdate,-1)) and del_from_dttm <=sysdate ");
			query.append(" group by cust2.acc_company_name , del2.del_id  ,   del2.del_from_dttm , del2.del_to_dttm ) m2 ");
			query.append(" where m1.deal_id=m2.deal_id  group by m1.acc_name,m1.deal_id,m1.valid_from,m1.valid_to,m1.in_deal_id, m2.out_deal_id ");
			query.append(" order by in_deal_id  ");

			logger.debug("Query for new deal data : " + query.toString());

			DateFormat dateFormat = new SimpleDateFormat(Constants.DASH_BOARD_DTTM_FORMAT);
			result = UsageDBHelper.getDB().execute(query.toString());
			while (result.next())
			{
				String carrier = result.getRs().getString("acc_name");
				int dealId = result.getRs().getInt("deal_id");
				String validFrom = dateFormat.format(getDateTime(result.getRs().getTimestamp("valid_from")).toDate());
				String validTo = dateFormat.format(getDateTime(result.getRs().getTimestamp("valid_to")).toDate());
				Double inVolume = result.getRs().getDouble("volume_in");
				Double outVolume = result.getRs().getDouble("volume_out");
				Double committedIn = result.getRs().getDouble("committed_in");
				Double committedOut = result.getRs().getDouble("committed_out");
				Double revenueIn = result.getRs().getDouble("rev_in");
				Double revenueOut = result.getRs().getDouble("rev_out");

				NewDeals newDeals = new NewDeals(carrier, dealId, validFrom, validTo, inVolume, outVolume, committedIn, committedOut, revenueIn, revenueOut);
				newDealsList.add(newDeals);
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

	protected DateTime getDateTime(Timestamp timestamp)
	{
		if (timestamp == null)
			return null;
		return new DateTime(timestamp);
	}

	public class NewDeals
	{
		public String carrier;
		public int dealId;
		public String validFrom = "";
		public String validTo = "";
		public String inVolume = "";
		public String outVolume = "";
		public String committedIn = "";
		public String committedOut = "";
		public String revenueIn = "";
		public String revenueOut = "";

		public NewDeals(String carrier, int dealId, String validFrom, String validTo, Double inVolume, Double outVolume, Double committedIn, Double committedOut, Double revenueIn, Double revenueOut)
		{
			this.carrier = carrier;
			this.dealId = dealId;
			this.validFrom = validFrom;
			this.validTo = validTo;
			if (inVolume != null)
				this.inVolume = String.valueOf(df.format(inVolume));
			if (outVolume != null)
				this.outVolume = String.valueOf(df.format(outVolume));
			if (committedIn != null)
				this.committedIn = String.valueOf(df.format(committedIn));
			if (committedOut != null)
				this.committedOut = String.valueOf(df.format(committedOut));
			if (revenueIn != null)
				this.revenueIn = String.valueOf(df.format(revenueIn));
			if (revenueOut != null)
				this.revenueOut = String.valueOf(df.format(revenueOut));
		}
	}

	public class ActiveDeals
	{
		public String carrier;
		public int dealId;
		public String validFrom = "";
		public String validTo = "";
		public String inVolume = "";
		public String outVolume = "";
		public String committedIn = "";
		public String committedOut = "";
		public String revenueIn = "";
		public String revenueOut = "";

		public ActiveDeals(String carrier, int dealId, String validFrom, String validTo, Double inVolume, Double outVolume, Double committedIn, Double committedOut, Double revenueIn, Double revenueOut)
		{
			this.carrier = carrier;
			this.dealId = dealId;
			this.validFrom = validFrom;
			this.validTo = validTo;
			if (inVolume != null)
				this.inVolume = String.valueOf(df.format(inVolume));
			if (outVolume != null)
				this.outVolume = String.valueOf(df.format(outVolume));
			if (committedIn != null)
				this.committedIn = String.valueOf(df.format(committedIn));
			if (committedOut != null)
				this.committedOut = String.valueOf(df.format(committedOut));
			if (revenueIn != null)
				this.revenueIn = String.valueOf(df.format(revenueIn));
			if (revenueOut != null)
				this.revenueOut = String.valueOf(df.format(revenueOut));
		}
	}
}
