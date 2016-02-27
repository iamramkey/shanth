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
import org.joda.time.DateTime;
import org.joda.time.Days;

import se.signa.signature.common.SignatureException;
import se.signa.signature.dio.BDBRecentActivityListResult;
import se.signa.signature.dio.BusinessDashboardInput;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.gen.dba.DealDba;
import se.signa.signature.gen.dba.RecentActivityDba;
import se.signa.signature.gen.dbo.Deal;
import se.signa.signature.gen.dbo.RecentActivity;
import se.signa.signature.helpers.DBHelper.ExecuteSelectStamentResult;
import se.signa.signature.helpers.RefDBHelper;

@Path("/bdbrecentactivitylist")
public class BDBRecentActivityListApi extends StigWebApi
{
	private static Logger logger = Logger.getLogger(BDBRecentActivityListApi.class);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult ddbRecentActivityList(BusinessDashboardInput input)
	{
		input.validate();
		input.checkRules();

		List<String> recentActivityTypeList = new ArrayList<String>();
		getRecentActivityTypes(recentActivityTypeList);

		List<RecentActivityData> recentActivityList = new ArrayList<RecentActivityData>();

		logger.debug("Query for recent activity :  select * from recent_activity where rea_type = ? and rownum < 200");

		for (String recentActivityType : recentActivityTypeList)
		{
			int count = 0;
			List<RecentActivity> recentActivities = RecentActivityDba.getI().getRecentActivity(recentActivityType);
			for (RecentActivity recentActivity : recentActivities)
			{
				RecentActivityData recentActivityData = new RecentActivityData();
				recentActivityData.icon = "fa fa-bar-chart-o";
				int days = Days.daysBetween(recentActivity.getReaCreatedDttm().withTimeAtStartOfDay(), new DateTime().withTimeAtStartOfDay()).getDays();
				if (count == 0)
				{
					recentActivityData.color = "#D84A38";
				}
				else if (count == 1)
				{
					recentActivityData.color = "#35AA47";
				}
				else
				{
					recentActivityData.color = "#2C3E50";
				}
				recentActivityData.data = recentActivity.getReaDesc();

				int month = 0;
				if (days > 0)
					month = days / 30;
				if (month > 1)
					recentActivityData.period = month + " months";
				else
					recentActivityData.period = days + " days";

				recentActivityData.group = recentActivityType;
				Deal deal = DealDba.getI().fetchBydelId(recentActivity.getDelId());
				recentActivityData.deal = deal.getDelName();
				recentActivityData.account = getAccountName(deal);
				recentActivityList.add(recentActivityData);
			}
			count = count + 1;
			if (count > 2)
				count = 0;
		}

		String notification = "Recent Activity List Rendered Successfully !!!";
		logger.info(notification);
		return new BDBRecentActivityListResult(notification, recentActivityTypeList, recentActivityList);
	}

	private String getAccountName(Deal deal)
	{
		ExecuteSelectStamentResult result = null;
		String carrierName = null;
		try
		{
			String query = "select acc_company_name from customer_vendor where acc_id = ?";

			logger.debug("Query for getting carrier name : " + query);

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

	private void getRecentActivityTypes(List<String> recentActivityTypeList)
	{
		ExecuteSelectStamentResult result = null;
		try
		{
			String query = "select distinct(rea_type) as rea_type from recent_activity";

			result = RefDBHelper.getDB().execute(query);
			while (result.next())
			{
				String type = result.getRs().getString("rea_type");
				recentActivityTypeList.add(type);
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

	public class RecentActivityData
	{
		public String icon;
		public String color;
		public String data;
		public String period;
		public String group;
		public String deal;
		public String account;
	}
}
