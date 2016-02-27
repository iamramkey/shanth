package se.signa.signature.rest;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import se.signa.signature.common.SignatureException;
import se.signa.signature.dio.CDBUncommittedTrafficChartResult;
import se.signa.signature.dio.CountryDashboardInput;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.helpers.DBHelper.ExecuteSelectStamentResult;
import se.signa.signature.helpers.RefDBHelper;
import se.signa.signature.helpers.UsageDBHelper;

@Path("/cdbuncommittedtraffichart")
public class CDBUncommittedTrafficChartApi extends StigWebApi
{
	private static Logger logger = Logger.getLogger(CDBUncommittedTrafficChartApi.class);

	//	private static DecimalFormat dfr = new DecimalFormat("#.####");

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult cdbUncommittedTrafficChart(CountryDashboardInput input)
	{
		input.validate();
		input.checkRules();

		//		dfr.setRoundingMode(RoundingMode.CEILING);

		List<ObjectArray> uncommittedTrafficChartList = new ArrayList<ObjectArray>();
		populate(uncommittedTrafficChartList, input.countryName, "I");
		populate(uncommittedTrafficChartList, input.countryName, "O");

		String notification = "Uncommitted traffic chart rendered successfully !!!";
		logger.info(notification);
		return new CDBUncommittedTrafficChartResult(notification, uncommittedTrafficChartList);

	}

	public DateTime getDateTime(Timestamp timestamp)
	{
		if (timestamp == null)
			return null;
		return new DateTime(timestamp);
	}

	private void populate(List<ObjectArray> uncommittedTrafficChartList, String country, String direction)
	{
		ExecuteSelectStamentResult result = null;
		try
		{
			//DateTime counter = new DateTime(fromDate);

			//List<DealBandGroupLite> dbgs = getDbgNamesForCountry(country, direction);
			//for (DealBandGroupLite dbg : dbgs)
			//{
			List<Object[]> dataList = new LinkedList<Object[]>();
			String query = getVolQuery(direction);
			logger.debug("Query for non deal data : " + query);
			result = UsageDBHelper.getDB().execute(query, new Object[] { country });
			while (result.next())
			{
				DateTime evtDttm = getDateTime(result.getRs().getTimestamp("evt_dttm"));
				Double usage = result.getRs().getDouble("usage");
				//					String curName = result.getRs().getString("cur_name");
				Object[] parsedDataArray = new Object[] { evtDttm.getMillis(), Math.round(usage) };
				dataList.add(parsedDataArray);
			}

			if (dataList.size() != 0)
				uncommittedTrafficChartList.add(new ObjectArray(dataList, country));
			//}

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

	private List<DealBandGroupLite> getDbgNamesForCountry(String country, String direction)
	{
		String query = "select dbg.dbg_id as dbg_id , dbg.dbg_name from deal_band_group dbg ";
		query = query + " where dbg.del_id in (select del_id from deal_item where bnd_id in (select bnd_id from ";
		query = query + " area_connection arc, country con, area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ";
		query = query + " arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?)) and dbg.dbg_direction = ? order by dbg.dbg_id ";

		logger.debug("Query for getting deal band groups : " + query);

		ExecuteSelectStamentResult result = null;
		List<DealBandGroupLite> dbgs = new ArrayList<DealBandGroupLite>();
		try
		{
			result = RefDBHelper.getDB().execute(query, new Object[] { country, direction });
			while (result.next())
			{
				dbgs.add(new DealBandGroupLite(result));
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
		return dbgs;
	}

	private String getVolQuery(String direction)
	{
		String query = "";
		String refName = RefDBHelper.getDB().getUserName();
		if (direction.equalsIgnoreCase("I"))
		{

			query = " select round(sum(IN_REV_BILLED_USAGE)/60.00) as usage , evt_dttm from margin_summary m1  ";
			query = query + " left join " + refName + ".band b1 on m1.in_bnd_id=b1.bnd_id ";
			query = query + " left join " + refName + ".deal d1 on m1.in_deal_id=d1.del_id ";
			query = query + " left join " + refName + ".customer_vendor c1 on d1.acc_id=c1.acc_id ";
			query = query + " where (m1.in_deal_id is null or m1.in_deal_id = 0) and (m1.out_deal_id is null or m1.out_deal_id = 0)  and in_bnd_id in (select bnd_id from ";
			query = query + " " + refName + ".area_connection arc, " + refName + ".country con, " + refName + ".area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ";
			query = query + " arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?) and evt_dttm <=sysdate  ";
			query = query + " group by evt_dttm order by evt_dttm";

			logger.debug("Query for uncommitted traffic in volume : " + query);
		}
		else if (direction.equalsIgnoreCase("O"))
		{
			query = " select round(sum(OUT_REV_BILLED_USAGE)/60.00) as usage, evt_dttm  from margin_summary m1 ";
			query = query + " left join " + refName + ".band b1 on m1.out_bnd_id=b1.bnd_id ";
			query = query + " left join " + refName + ".deal d1 on m1.out_deal_id=d1.del_id ";
			query = query + " left join " + refName + ".customer_vendor c1 on d1.acc_id=c1.acc_id ";
			query = query + " where (m1.out_deal_id is null or m1.out_deal_id = 0) and (m1.in_deal_id is null or m1.in_deal_id = 0) and out_bnd_id in (select bnd_id from ";
			query = query + " " + refName + ".area_connection arc, " + refName + ".country con, " + refName + ".area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ";
			query = query + " arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?) and evt_dttm <=sysdate ";
			query = query + " group by evt_dttm order by evt_dttm";

			logger.debug("Query for uncommitted traffic out volume : " + query);
		}
		return query;
	}

	public class ObjectArray
	{
		public List<Object[]> objectArray;
		public String destination;

		public ObjectArray(List<Object[]> objectArray, String destination)
		{
			this.objectArray = objectArray;
			this.destination = destination;
		}
	}

	public class DealBandGroupLite
	{
		public String dbgName;
		public int dbgId;

		public DealBandGroupLite(ExecuteSelectStamentResult rs) throws SQLException
		{
			dbgName = rs.getRs().getString("dbg_name");
			dbgId = rs.getRs().getInt("dbg_id");
		}
	}
}
