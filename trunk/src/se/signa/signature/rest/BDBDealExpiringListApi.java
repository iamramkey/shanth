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
import se.signa.signature.dio.BDBDealExpiringListResult;
import se.signa.signature.dio.BusinessDashboardInput;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.gen.dba.DealDba;
import se.signa.signature.gen.dbo.Deal;
import se.signa.signature.helpers.DBHelper.ExecuteSelectStamentResult;
import se.signa.signature.helpers.RefDBHelper;

@Path("/bdbdealexpiringlist")
public class BDBDealExpiringListApi extends StigWebApi
{
	private static Logger logger = Logger.getLogger(BDBDealExpiringListApi.class);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult bdbDealExpiringList(BusinessDashboardInput input)
	{
		input.validate();
		input.checkRules();

		List<Deal> deals = DealDba.getI().fetchByWhereClause();

		logger.debug("Query for Deal expiring query :  select * from deal where del_to_dttm >=sysdate and  del_to_dttm <= sysdate +32  ");

		DateTime date = new DateTime();

		List<DealExpiringData> projectionDataList = new ArrayList<DealExpiringData>();

		for (Deal deal : deals)
		{
			logger.info(" Deal expiring " + deal.getDelId());

			DealExpiringData dealExpiringData = new DealExpiringData();
			dealExpiringData.icon = "icon-bulb";
			int expiringDays = Days.daysBetween(date, deal.getDelToDttm().withTimeAtStartOfDay()).getDays();
			if (expiringDays < 7)
			{
				dealExpiringData.color = "#E26A6A";
				dealExpiringData.labelColor = "#67809F";
			}
			else if (expiringDays >= 7 && expiringDays < 31)
			{
				dealExpiringData.color = "#E87E04";
				dealExpiringData.labelColor = "#67809F";
			}
			else
			{
				dealExpiringData.color = "#2C3E50";
				dealExpiringData.labelColor = "#67809F";
			}
			dealExpiringData.data = deal.getDelName();
			dealExpiringData.account = getAccountName(deal);
			/*
			int month = 0;
			if (expiringDays > 0)
				month = expiringDays / 30;
			if (month > 1)
				dealExpiringData.period = month + " months";
			else
				dealExpiringData.period = expiringDays + " days";
			*/

			dealExpiringData.period = (expiringDays + 2) + " days";
			dealExpiringData.deal = deal.getDelName();
			projectionDataList.add(dealExpiringData);
		}

		/*DealExpiringData projectionData1 = new DealExpiringData();
		projectionData1.icon = "fa fa-bar-chart-o";
		projectionData1.color = "#D84A38";
		projectionData1.data = "AT&T March 2015 to Sept 2015";
		projectionData1.period = "5 days";
		projectionData1.deal = "POST Luxembourg - nov 2013 - apr 2014 - 493";
		projectionDataList.add(projectionData1);
		
		DealExpiringData projectionData3 = new DealExpiringData();
		projectionData3.icon = "fa fa-bar-chart-o";
		projectionData3.color = "#D84A38";
		projectionData3.data = "Telenor Feb 2015 to Oct 2015";
		projectionData3.period = "30 days";
		projectionData3.deal = "POST Luxembourg - nov 2013 - apr 2014 - 493";
		projectionDataList.add(projectionData3);
		
		DealExpiringData projectionData2 = new DealExpiringData();
		projectionData2.icon = "fa fa-bar-chart-o";
		projectionData2.color = "#35AA47";
		projectionData2.data = "Belgacom Jan 2015 to Oct 2015)";
		projectionData2.period = "2 months";
		projectionData2.deal = "POST Luxembourg - nov 2013 - apr 2014 - 493";
		projectionDataList.add(projectionData2);
		
		DealExpiringData projectionData4 = new DealExpiringData();
		projectionData4.icon = "fa fa-bar-chart-o";
		projectionData4.color = "#578EBE";
		projectionData4.data = "Telstra Jan 2015 to Dec 2015";
		projectionData4.period = "3 months";
		projectionData4.deal = "POST Luxembourg - nov 2013 - apr 2014 - 493";
		projectionDataList.add(projectionData4);*/

		String notification = "Deal expiring List Rendered Successfully !!!";
		logger.info(notification);
		return new BDBDealExpiringListResult(notification, projectionDataList);
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

	public class DealExpiringData
	{
		public String icon;
		public String color;
		public String labelColor;
		public String data;
		public String period;
		public String deal;
		public String account;
	}
}
