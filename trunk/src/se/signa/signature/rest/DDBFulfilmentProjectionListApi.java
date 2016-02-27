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
import se.signa.signature.dio.DDBFulfilmentProjectionListResult;
import se.signa.signature.dio.DashboardInput;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.gen.dba.DealDba;
import se.signa.signature.gen.dbo.Deal;
import se.signa.signature.helpers.DBHelper.ExecuteSelectStamentResult;
import se.signa.signature.helpers.RefDBHelper;
import se.signa.signature.helpers.UsageDBHelper;

@Path("/ddbfulfilmentprojectionlist")
public class DDBFulfilmentProjectionListApi extends StigWebApi
{
	private static Logger logger = Logger.getLogger(DDBFulfilmentProjectionListApi.class);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult ddbFulfilmentProjectionList(DashboardInput input)
	{
		input.validate();
		input.checkRules();

		List<ProjectionData> projectionDataList = new ArrayList<ProjectionData>();

		Deal deal = DealDba.getI().fetchBydelName(input.deal);
		int dealId = deal.getDelId();
		getFulfilmentList(projectionDataList, dealId, "I");
		getFulfilmentList(projectionDataList, dealId, "O");
		String notification = "Fulfilment Projection List Rendered Successfully !!!";
		logger.info(notification);
		return new DDBFulfilmentProjectionListResult(notification, projectionDataList);
	}

	private void getFulfilmentList(List<ProjectionData> projectionDataList, Integer delId, String direction)
	{
		ExecuteSelectStamentResult result = null;
		try
		{
			String query = getFulfilmentQuery(direction);
			result = UsageDBHelper.getDB().execute(query, new Object[] { delId, delId });
			while (result.next())
			{
				FulfilmentModel fulfilmentModel = new FulfilmentModel(result);

				Double avg = fulfilmentModel.averageUsage;
				Double actualUsage = fulfilmentModel.actualUsage;
				Double comittedUsage = fulfilmentModel.committedUsage;
				ProjectionData projectionData = new ProjectionData();
				projectionData.data = fulfilmentModel.bndName;
				Double remainingUsage = 0d;
				if (avg != 0.0)
					remainingUsage = fulfilmentModel.projectedUsage;
				projectionData.period = remainingUsage + " days";
				if (actualUsage == 0d)
					projectionData.period = "Never";
				else
				{
					int days = (int) Math.round(remainingUsage);
					int month = 0;
					if (days > 0)
						month = days / 30;
					if (month > 1)
						projectionData.period = month + " months";
					else
						projectionData.period = days + " days";
				}

				if (direction.contains("I"))
				{
					projectionData.direction = "IN";
					projectionData.icon = "icon-arrow-left";
				}
				else
				{
					projectionData.direction = "OUT";
					projectionData.icon = "icon-arrow-right";
				}
				projectionData.color = "#2C3E50";
				projectionData.labelColor = "#67809F";

				if (remainingUsage > 0)
					projectionDataList.add(projectionData);

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

	private String getFulfilmentQuery(String direction)
	{
		String refName = RefDBHelper.getDB().getUserName();
		StringBuffer query = new StringBuffer();
		query.append("");
		if (direction.equalsIgnoreCase("I"))
		{
			query.append(" select dbg_name,totalvol, oneweekvol, oneweekvol/7 as oneweekvolavg, committ,(committ - totalvol)/(oneweekvol/7) projecteddays ");
			query.append("  from ((select sum(in_rev_billed_usage)/60.00 totalvol, in_dbg_id from margin_summary ");
			query.append(" where in_deal_id= ? and evt_dttm < (sysdate -1) group by in_dbg_id) m1 ");
			query.append(" left join (select dlt_to_val committ, dbg_id from " + refName + ".deal_tier dlt ");
			query.append(" where dlt.dlt_comm_fl='Y') dlt on m1.in_dbg_id=dlt.dbg_id ");
			query.append(" left join (select sum(in_rev_billed_usage)/60.00 oneweekvol, in_dbg_id from margin_summary ");
			query.append(" where in_deal_id= ? and evt_dttm >= (sysdate -8) and evt_dttm <= (sysdate-1) group by in_dbg_id) m2 ");
			query.append(" on m1.in_dbg_id=m2.in_dbg_id left join (select dbg_id, dbg_name from " + refName + ".deal_band_group dbg) ");
			query.append(" dbg on m1.in_dbg_id=dbg.dbg_id ) ");

		}
		else if (direction.equalsIgnoreCase("O"))
		{
			query.append(" select dbg_name,totalvol, oneweekvol, oneweekvol/7 as oneweekvolavg, committ,(committ - totalvol)/ (oneweekvol/7) projecteddays from ");
			query.append(" ((select sum(in_rev_billed_usage)/60.00 totalvol, out_dbg_id from margin_summary where ");
			query.append(" out_deal_id= ? and evt_dttm < (sysdate -1) group by out_dbg_id) m1 ");
			query.append(" left join (select dlt_to_val committ, dbg_id from " + refName + ".deal_tier dlt ");
			query.append(" where dlt.dlt_comm_fl='Y') dlt on m1.out_dbg_id=dlt.dbg_id ");
			query.append(" left join (select sum(in_rev_billed_usage)/60.00 oneweekvol, out_dbg_id from margin_summary ");
			query.append(" where out_deal_id= ? and evt_dttm >= (sysdate -8) and evt_dttm <= (sysdate-1) ");
			query.append(" group by out_dbg_id) m2 on m1.out_dbg_id=m2.out_dbg_id ");
			query.append(" left join (select dbg_id, dbg_name from " + refName + ".deal_band_group dbg) dbg on m1.out_dbg_id=dbg.dbg_id ) ");
		}
		logger.debug("Query for fulfilment projected list : " + query);

		return query.toString();
	}

	public class FulfilmentModel
	{
		public String bndName;
		public Double actualUsage;
		public Double oneWeekUsage;
		public Double averageUsage;
		public Double committedUsage;
		public Double projectedUsage;

		public FulfilmentModel(ExecuteSelectStamentResult rs)
		{
			try
			{
				bndName = rs.getRs().getString("dbg_name");
				actualUsage = rs.getRs().getDouble("totalvol");
				oneWeekUsage = rs.getRs().getDouble("oneweekvol");
				averageUsage = rs.getRs().getDouble("oneweekvolavg");
				committedUsage = rs.getRs().getDouble("committ");
				projectedUsage = rs.getRs().getDouble("projecteddays");
			}
			catch (SQLException e)
			{
				throw new SignatureException(e);
			}
		}
	}

	public class ProjectionData
	{
		public String icon;
		public String data;
		public String period;
		public String direction;
		public String color;
		public String labelColor;
	}
}
