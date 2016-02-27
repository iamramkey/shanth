package se.signa.signature.rest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import se.signa.signature.common.SignatureException;
import se.signa.signature.dio.BDBChangesInTrafficListResult;
import se.signa.signature.dio.BusinessDashboardInput;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.helpers.DBHelper.ExecuteSelectStamentResult;
import se.signa.signature.helpers.RefDBHelper;
import se.signa.signature.helpers.UsageDBHelper;

@Path("/bdbchangesintrafficlist")
public class BDBChangeInTrafficListApi extends StigWebApi
{
	private static Logger logger = Logger.getLogger(BDBChangeInTrafficListApi.class);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult ddbChangesInTrafficList(BusinessDashboardInput input)
	{
		input.validate();
		input.checkRules();

		List<ChangesInTrafficData> changesInTrafficDataList = new ArrayList<ChangesInTrafficData>();

		getChangeInTraffic(changesInTrafficDataList);
		//getChangeInTraffic("O", "desc", changesInTrafficDataList);

		String notification = "Changes in traffic List Rendered Successfully !!!";
		logger.info(notification);
		return new BDBChangesInTrafficListResult(notification, changesInTrafficDataList);
	}

	private void getChangeInTraffic(List<ChangesInTrafficData> changesInTrafficDataList)
	{

		ExecuteSelectStamentResult result = null;
		try
		{
			String query = getActualUsageQuery();

			result = UsageDBHelper.getDB().execute(query);
			while (result.next())
			{
				Double changeInTraffic = result.getRs().getDouble("change_in_trf");
				String dbgName = result.getRs().getString("dbg_name");
				String dealName = result.getRs().getString("deal_name");
				int delId = result.getRs().getInt("del_id");
				String accName = result.getRs().getString("acc_company_name");
				String direction = result.getRs().getString("direction");
				ChangesInTrafficData changesInTrafficData = new ChangesInTrafficData();
				changesInTrafficData.data = delId + "-" + accName + "-" + dbgName;
				changesInTrafficData.period = String.valueOf(Math.ceil(changeInTraffic)) + "%";

				changesInTrafficData.labelColor = "#67809F";
				if (direction.contains("I"))
				{
					changesInTrafficData.color = "#2C3E50";
					changesInTrafficData.direction = "IN";
					changesInTrafficData.icon = "icon-arrow-right";
				}
				else
				{
					changesInTrafficData.color = "#2C3E50";
					changesInTrafficData.direction = "OUT";
					changesInTrafficData.icon = "icon-arrow-left";
				}
				changesInTrafficData.deal = dealName;
				changesInTrafficData.account = accName;
				changesInTrafficDataList.add(changesInTrafficData);
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

	private String getActualUsageQuery()
	{

		String query = "";
		String refName = RefDBHelper.getDB().getUserName();
		//if (direction.contains("I"))
		//{

		query = " select * from ( ";
		query = query + " select * from ( ";
		query = query + " select round(((((m1.rev_billed_usage / 7) - (m2.rev_billed_usage / 7)) / (m2.rev_billed_usage / 7)) * 100)) as change_in_trf,m1.dbg_name as dbg_name , ";
		query = query + " m1.del_id as del_id , m1.del_name as deal_name,m1.acc_company_name as acc_company_name, 'In' as direction  from ";
		query = query + " (select sum(in_rev_billed_usage) as rev_billed_usage, dbg.dbg_name, del.del_id , del.del_name ";
		query = query + " , acc.acc_company_name from margin_summary msu , " + refName + ".deal_band_group dbg , " + refName + ".deal del , ";
		query = query + " " + refName + ".customer_vendor acc where msu.in_dbg_id = dbg.dbg_id and msu.in_deal_id = del.del_id and ";
		query = query + " del.acc_id = acc.acc_id and msu.evt_dttm >= (sysdate -8) and msu.evt_dttm < (sysdate-1)  group by dbg.dbg_name,del.del_name,del.del_id,acc.acc_company_name ) m1, ";
		query = query + " (select sum(in_rev_billed_usage) as rev_billed_usage,dbg.dbg_name,del.del_id ";
		query = query + " , acc.acc_company_name from margin_summary msu , " + refName + ".deal_band_group dbg , " + refName + ".deal del , ";
		query = query + " " + refName + ".customer_vendor acc where msu.in_dbg_id = dbg.dbg_id and msu.in_deal_id = del.del_id and ";
		query = query + " del.acc_id = acc.acc_id and msu.evt_dttm > (sysdate -15) and msu.evt_dttm < (sysdate -8) group by dbg.dbg_name,del.del_id ,acc.acc_company_name ";
		query = query + " ) m2 where  m1.del_id=m2.del_id and m1.dbg_name=m2.dbg_name order by change_in_trf asc ";
		query = query + " ) outerm1 ";
		query = query + " union ";
		query = query + " select * from ( ";
		query = query + " select round(((((m1.rev_billed_usage / 7) - (m2.rev_billed_usage / 7)) / (m2.rev_billed_usage / 7)) * 100)) as change_in_trf,m1.dbg_name as dbg_name , ";
		query = query + " m1.del_id as del_id , m1.del_name as deal_name,m1.acc_company_name as acc_company_name, 'Out' as direction  from ";
		query = query + " (select sum(out_rev_billed_usage) as rev_billed_usage,dbg.dbg_name,del.del_id ,del.del_name ";
		query = query + " , acc.acc_company_name from margin_summary msu , " + refName + ".deal_band_group dbg , " + refName + ".deal del , ";
		query = query + " " + refName + ".customer_vendor acc where msu.out_dbg_id = dbg.dbg_id and msu.out_deal_id = del.del_id and ";
		query = query + " del.acc_id = acc.acc_id and msu.evt_dttm >= (sysdate -8) and msu.evt_dttm < (sysdate-1)  group by dbg.dbg_name,del.del_id,del.del_name,acc.acc_company_name ) m1, ";
		query = query + " (select sum(out_rev_billed_usage) as rev_billed_usage,dbg.dbg_name,del.del_id ";
		query = query + " , acc.acc_company_name from margin_summary msu , " + refName + ".deal_band_group dbg , " + refName + ".deal del , ";
		query = query + " " + refName + ".customer_vendor acc where msu.out_dbg_id = dbg.dbg_id and msu.out_deal_id = del.del_id and ";
		query = query + " del.acc_id = acc.acc_id and msu.evt_dttm > (sysdate -15) and msu.evt_dttm < (sysdate -8) group by dbg.dbg_name,del.del_id,acc.acc_company_name ";
		query = query + " ) m2 where  m1.del_id=m2.del_id and m1.dbg_name=m2.dbg_name  order by change_in_trf desc ";
		query = query + " ) outerm2 ) final_tbl ";
		query = query + " where del_id in ( select del_id from " + refName + ".deal where del_to_dttm >= sysdate and round((sysdate) - to_date (to_char(del_from_dttm,'DD-MON-YYYY'),'DD-MON-YYYY'))>8 )  ";
		query = query + " order by abs(change_in_trf) desc ";

		logger.debug("Query for In Actual usage : " + query);

		//}

		/*else if (direction.contains("O"))
		{
			query = " select * from ( ";
			query = query + " select round(((((m1.rev_billed_usage / 7) - (m2.rev_billed_usage / 7)) / (m2.rev_billed_usage / 7)) * 100)) as change_in_trf,m1.dbg_name as dbg_name , ";
			query = query + " m1.del_id as del_id , m1.del_name as deal_name,m1.acc_company_name as acc_company_name  from ";
			query = query + " (select sum(out_rev_billed_usage) as rev_billed_usage,dbg.dbg_name,del.del_id ,del.del_name ";
			query = query + " , acc.acc_company_name from margin_summary msu , " + refName + ".deal_band_group dbg , " + refName + ".deal del , ";
			query = query + " " + refName + ".customer_vendor acc where msu.out_dbg_id = dbg.dbg_id and msu.out_deal_id = del.del_id and ";
			query = query + " del.acc_id = acc.acc_id and msu.evt_dttm > (sysdate -7) and msu.evt_dttm <= (sysdate)  group by dbg.dbg_name,del.del_id,del.del_name,acc.acc_company_name ) m1, ";
			query = query + " (select sum(out_rev_billed_usage) as rev_billed_usage,dbg.dbg_name,del.del_id ";
			query = query + " , acc.acc_company_name from margin_summary msu , " + refName + ".deal_band_group dbg , " + refName + ".deal del , ";
			query = query + " " + refName + ".customer_vendor acc where msu.out_dbg_id = dbg.dbg_id and msu.out_deal_id = del.del_id and ";
			query = query + " del.acc_id = acc.acc_id and msu.evt_dttm > (sysdate -14) and msu.evt_dttm <= (sysdate -7) group by dbg.dbg_name,del.del_id,acc.acc_company_name ";
			query = query + " ) m2 where  m1.del_id=m2.del_id and m1.dbg_name=m2.dbg_name and rownum <=20 order by change_in_trf asc ";
			query = query + " ) outerm1 ";
			query = query + " union ";
			query = query + " select * from ( ";
			query = query + " select round(((((m1.rev_billed_usage / 7) - (m2.rev_billed_usage / 7)) / (m2.rev_billed_usage / 7)) * 100)) as change_in_trf,m1.dbg_name as dbg_name , ";
			query = query + " m1.del_id as del_id , m1.del_name as deal_name,m1.acc_company_name as acc_company_name  from ";
			query = query + " (select sum(out_rev_billed_usage) as rev_billed_usage,dbg.dbg_name,del.del_id ,del.del_name ";
			query = query + " , acc.acc_company_name from margin_summary msu , " + refName + ".deal_band_group dbg , " + refName + ".deal del , ";
			query = query + " " + refName + ".customer_vendor acc where msu.out_dbg_id = dbg.dbg_id and msu.out_deal_id = del.del_id and ";
			query = query + " del.acc_id = acc.acc_id and msu.evt_dttm > (sysdate -7) and msu.evt_dttm <= (sysdate)  group by dbg.dbg_name,del.del_id,del.del_name,acc.acc_company_name ) m1, ";
			query = query + " (select sum(out_rev_billed_usage) as rev_billed_usage,dbg.dbg_name,del.del_id ";
			query = query + " , acc.acc_company_name from margin_summary msu , " + refName + ".deal_band_group dbg , " + refName + ".deal del , ";
			query = query + " " + refName + ".customer_vendor acc where msu.out_dbg_id = dbg.dbg_id and msu.out_deal_id = del.del_id and ";
			query = query + " ) m2 where  m1.del_id=m2.del_id and m1.dbg_name=m2.dbg_name and rownum <=20 order by change_in_trf desc ";
			query = query + " ) outerm2 ";
		
			logger.debug("Query for Out Actual usage : "+query);
		} */
		return query;

	}

	public class ChangesInTrafficData
	{
		public String icon;
		public String color;
		public String labelColor;
		public String data;
		public String period;
		public String direction;
		public String deal;
		public String account;
	}
}
