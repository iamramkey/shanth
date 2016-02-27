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
import se.signa.signature.dio.CDBFulfilmentProjectionListResult;
import se.signa.signature.dio.CountryDashboardInput;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.helpers.DBHelper.ExecuteSelectStamentResult;
import se.signa.signature.helpers.RefDBHelper;
import se.signa.signature.helpers.UsageDBHelper;

@Path("/cdbfulfilmentprojectionlist")
public class CDBFulfilmentProjectionListApi extends StigWebApi
{
	private static Logger logger = Logger.getLogger(CDBFulfilmentProjectionListApi.class);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult cdbFulfilmentProjectionList(CountryDashboardInput input)
	{
		input.validate();
		input.checkRules();

		List<ProjectionData> projectionDataList = new ArrayList<ProjectionData>();
		getFulfilmentList(projectionDataList, "I", input.countryName);
		getFulfilmentList(projectionDataList, "O", input.countryName);
		String notification = "Fulfilment Projection List Rendered Successfully !!!";
		logger.info(notification);
		return new CDBFulfilmentProjectionListResult(notification, projectionDataList);
	}

	private void getFulfilmentList(List<ProjectionData> projectionDataList, String direction, String country)
	{
		ExecuteSelectStamentResult result = null;
		try
		{
			String query = getFulfilmentQuery(direction);
			result = UsageDBHelper.getDB().execute(query, new Object[] { country, country });
			while (result.next())
			{
				FulfilmentModel fulfilmentModel = new FulfilmentModel(result);

				Double avg = fulfilmentModel.averageUsage;
				Double actualUsage = fulfilmentModel.actualUsage;
				Double comittedUsage = fulfilmentModel.committedUsage;
				ProjectionData projectionData = new ProjectionData();
				projectionData.data = fulfilmentModel.dealId + "-" + fulfilmentModel.bndName;
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
			query.append(" select dbg_name, del_id , totalvol, oneweekvol, oneweekvol/7 as oneweekvolavg, committ,(committ - totalvol)/(oneweekvol/7) projecteddays ");
			query.append("  from ((select sum(in_rev_billed_usage)/60.00 totalvol, in_dbg_id ,in_deal_id as del_id from margin_summary ");
			query.append(" where in_deal_id is not null and in_bnd_id in (select bnd_id from ");
			query.append(" " + refName + ".area_connection arc, " + refName + ".country con, " + refName + ".area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ");
			query.append(" arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?) and evt_dttm < (sysdate -1) group by in_dbg_id,in_deal_id) m1 ");
			query.append(" left join (select dlt_to_val committ, dbg_id from " + refName + ".deal_tier dlt ");
			query.append(" where dlt.dlt_comm_fl='Y') dlt on m1.in_dbg_id=dlt.dbg_id ");
			query.append(" left join (select sum(in_rev_billed_usage)/60.00 oneweekvol, in_dbg_id ,in_deal_id from margin_summary ");
			query.append(" where in_deal_id is not null and in_bnd_id in (select bnd_id from ");
			query.append(" " + refName + ".area_connection arc, " + refName + ".country con, " + refName + ".area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ");
			query.append(" arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?) and  evt_dttm >= (sysdate -8) and evt_dttm <= (sysdate-1) group by in_dbg_id,in_deal_id) m2 ");
			query.append(" on m1.in_dbg_id=m2.in_dbg_id and m1.del_id=m2.in_deal_id left join (select dbg_id, dbg_name from " + refName + ".deal_band_group dbg) ");
			query.append(" dbg on m1.in_dbg_id=dbg.dbg_id ) ");

		}
		else if (direction.equalsIgnoreCase("O"))
		{
			query.append(" select dbg_name, del_id ,totalvol, oneweekvol, oneweekvol/7 as oneweekvolavg, committ,(committ - totalvol)/ (oneweekvol/7) projecteddays from ");
			query.append(" ((select sum(in_rev_billed_usage)/60.00 totalvol, out_dbg_id ,out_deal_id as del_id from margin_summary where ");
			query.append(" out_deal_id is not null and out_bnd_id in (select bnd_id from ");
			query.append(" " + refName + ".area_connection arc, " + refName + ".country con, " + refName + ".area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ");
			query.append(" arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?) and evt_dttm < (sysdate -1) group by out_dbg_id,out_deal_id) m1 ");
			query.append(" left join (select dlt_to_val committ, dbg_id from " + refName + ".deal_tier dlt ");
			query.append(" where dlt.dlt_comm_fl='Y') dlt on m1.out_dbg_id=dlt.dbg_id ");
			query.append(" left join (select sum(in_rev_billed_usage)/60.00 oneweekvol, out_dbg_id ,out_deal_id from margin_summary ");
			query.append(" where out_deal_id is not null and out_bnd_id in (select bnd_id from ");
			query.append(" " + refName + ".area_connection arc, " + refName + ".country con, " + refName + ".area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ");
			query.append(" arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?) and evt_dttm >= (sysdate -8) and evt_dttm <= (sysdate-1) ");
			query.append(" group by out_dbg_id,out_deal_id) m2 on m1.out_dbg_id=m2.out_dbg_id and m1.del_id=m2.out_deal_id ");
			query.append(" left join (select dbg_id, dbg_name from " + refName + ".deal_band_group dbg) dbg on m1.out_dbg_id=dbg.dbg_id ) ");
		}
		logger.debug("Query for fulfilment projected list : " + query);

		return query.toString();
	}

	public class FulfilmentModel
	{
		public String bndName;
		public String dealId;
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
				dealId = rs.getRs().getString("del_id");
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
