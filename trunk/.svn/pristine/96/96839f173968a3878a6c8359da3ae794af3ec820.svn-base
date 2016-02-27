package se.signa.signature.rest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import se.signa.signature.common.SignatureException;
import se.signa.signature.dio.DDBFulfilmentChartResult;
import se.signa.signature.dio.DashboardInput;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.gen.dba.DealDba;
import se.signa.signature.gen.dbo.Deal;
import se.signa.signature.helpers.DBHelper.ExecuteSelectStamentResult;
import se.signa.signature.helpers.RefDBHelper;
import se.signa.signature.helpers.UsageDBHelper;

@Path("/ddbfulfilmentchart")
public class DDBFulfilmentChartApi extends StigWebApi
{
	private static Logger logger = Logger.getLogger(DDBFulfilmentChartApi.class);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult ddbFulfilmentChart(DashboardInput input)
	{
		input.validate();
		input.checkRules();

		Deal deal = DealDba.getI().fetchBydelName(input.deal);
		int dealId = deal.getDelId();
		List<Series> inSeriesList = new ArrayList<Series>();
		List<Series> outSeriesList = new ArrayList<Series>();

		List<String> inCategories = getDbgNames(dealId, "In");
		List<String> outCategories = getDbgNames(dealId, "Out");

		Series inCommitedSeries = new Series();
		inCommitedSeries.name = "Commited";
		inCommitedSeries.data = new ArrayList<Double>();

		Series inActualSeries = new Series();
		inActualSeries.name = "Actual";
		inActualSeries.data = new ArrayList<Double>();

		for (String dbgName : inCategories)
		{
			setActuals(inActualSeries.data, dealId, dbgName, "I");
			setCommitments(inCommitedSeries.data, dbgName, "I", dealId);
		}

		inSeriesList.add(inCommitedSeries);
		inSeriesList.add(inActualSeries);

		Series outCommitedSeries = new Series();
		outCommitedSeries.name = "Commited";
		outCommitedSeries.data = new ArrayList<Double>();

		Series outActualSeries = new Series();
		outActualSeries.name = "Revenue";
		outActualSeries.data = new ArrayList<Double>();

		for (String dbgName : outCategories)
		{
			logger.info(" Finding the traffic for deal band group...  ");
			setActuals(outActualSeries.data, dealId, dbgName, "O");
			setCommitments(outCommitedSeries.data, dbgName, "O", dealId);
		}

		outSeriesList.add(outCommitedSeries);
		outSeriesList.add(outActualSeries);

		String notification = "Fulfilment chart rendered successfully !!!";
		logger.info(notification);
		return new DDBFulfilmentChartResult(notification, inCategories, outCategories, inSeriesList, outSeriesList);
	}

	private List<String> getDbgNames(Integer delId, String direction)
	{
		List<String> dbgNames = new LinkedList<String>();
		ExecuteSelectStamentResult result = null;
		try
		{
			String query = " select dbg_name from deal_band_group  where del_id = ? and dbg_direction = ?";

			logger.debug("Query for getting dbg names : " + query);

			result = RefDBHelper.getDB().execute(query, new Object[] { delId, direction });
			while (result.next())
			{
				String dbgName = result.getRs().getString("dbg_name");
				dbgNames.add(dbgName);
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
		return dbgNames;
	}

	private void setCommitments(List<Double> data, String dbgName, String direction, int delId)
	{

		ExecuteSelectStamentResult result = null;
		try
		{
			String query = " select dlt.dlt_to_val as dlt_to_val from deal_tier dlt , ";
			query = query + " deal_band_group dbg where dbg.dbg_name = ? and dlt.dbg_id = dbg.dbg_id ";
			query = query + "and dlt.del_id = ? and dlt.dlt_comm_fl =  'Y' and dbg_direction like ? ";

			logger.debug("Query for getting committments : " + query);

			result = RefDBHelper.getDB().execute(query, new Object[] { dbgName, delId, "%" + direction + "%" });
			if (result.next())
			{
				double commitedUsage = result.getRs().getDouble("dlt_to_val");
				data.add(commitedUsage);
			}
			else
				data.add(0d);
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

	private void setActuals(List<Double> data, int dealId, String dbgName, String direction)
	{
		ExecuteSelectStamentResult result = null;
		String refName = RefDBHelper.getDB().getUserName();
		try
		{
			//TODO : Port it to margin summary

			//String query = "select sum(trc.rev_billed_usage) from traffic_count trc ,deal_band_group dbg, deal_item dli where";
			//query = query + "  dli.dbg_id = dbg.dbg_id and dli.bnd_id = trc.bnd_id and rev_revenue_code like ? and  trc.del_id = ? and dbg.dbg_name = ? ";

			String query = getActualsQuery(refName, direction);

			logger.debug("Executing select Query : " + query);

			logger.info("Actuals for the fulfilment charts " + dbgName + " for the direction " + direction + " deal ID " + dealId);

			Object[] paramValues = new Object[] { dealId, dbgName, dealId };
			result = UsageDBHelper.getDB().execute(query, paramValues);

			if (result.next())
			{
				long evtUsage = result.getRs().getLong("rev_billed_usage");
				data.add((double) evtUsage);
				logger.info(" Event Usage  " + evtUsage);
			}
			else
				data.add(0d);
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

	private String getActualsQuery(String refName, String direction)
	{
		String query = "";
		if (direction.contains("I"))
		{
			query = "select round(sum(IN_REV_BILLED_USAGE)/60.00) as rev_billed_usage from margin_summary mcs," + refName + ".deal_band_group dbg  where";
			query = query + "  mcs.in_deal_id = ? and dbg.dbg_name = ? and evt_dttm <= sysdate  and dbg.del_ID=? and mcs.in_dbg_id=dbg.dbg_id";

			logger.debug("Query for getting in actual usage data : " + query);

		}
		else if (direction.contains("O"))
		{
			query = "select round(sum(OUT_REV_BILLED_USAGE)/60.00) as rev_billed_usage from margin_summary mcs," + refName + ".deal_band_group dbg where";
			query = query + "  mcs.out_deal_id = ? and dbg.dbg_name = ? and evt_dttm <= sysdate and dbg.del_ID=? and mcs.out_dbg_id=dbg.dbg_id ";

			logger.debug("Query for getting out actual usage data : " + query);
		}
		return query;

	}

	public class Series
	{
		public String name;
		public List<Double> data;
	}

}
