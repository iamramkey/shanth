package se.signa.signature.rest;

import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import se.signa.signature.common.Constants;
import se.signa.signature.common.SignatureException;
import se.signa.signature.dio.CDBCommittedTrafficChartResult;
import se.signa.signature.dio.CountryDashboardInput;
import se.signa.signature.dio.NegativeResult;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.helpers.DBHelper.ExecuteSelectStamentResult;
import se.signa.signature.helpers.RefDBHelper;

@Path("/cdbcommittedtraffichart")
public class CDBCommittedTrafficChartApi extends StigWebApi
{
	private static Logger logger = Logger.getLogger(CDBCommittedTrafficChartApi.class);
	private static DecimalFormat dfr = new DecimalFormat("#.####");

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult cdbCommittedTrafficChart(CountryDashboardInput input)
	{
		input.validate();
		input.checkRules();

		dfr.setRoundingMode(RoundingMode.CEILING);

		DateTimeFormatter graphFormat = DateTimeFormat.forPattern(Constants.GRAPH_FORMAT);

		List<String> categories = new ArrayList<String>();
		DateTime now = new DateTime();
		DateTime fromDttm = now.minusMonths(2);
		DateTime toDttm = now.plusMonths(4);

		if (fromDttm != null && toDttm != null & fromDttm.isAfter(toDttm))
			return new NegativeResult(Constants.ErrCode.INPUT_FIELD_INVALID, "To date must be > than from date", "toDttm");

		DateTime counter = new DateTime(fromDttm);
		while (counter.isBefore(toDttm))
		{
			String outputDate = counter.toString(graphFormat);
			categories.add(outputDate);
			counter = counter.plusMonths(1);
		}

		/*	Series series = new Series();
			series.name = "Sweden";
			List<Double> list1 = new ArrayList<Double>();
			list1.add(12.3);
			list1.add(15.3);
			list1.add(12.3);
			list1.add(11.3);
			list1.add(15.3);
			list1.add(17.3);
			series.data = list1;
			
			Series series3 = new Series();
			series3.name = "Sweden Mobiles";
			List<Double> list3 = new ArrayList<Double>();
			list3.add(42.3);
			list3.add(5.3);
			list3.add(22.3);
			list3.add(31.3);
			list3.add(55.3);
			list3.add(67.3);
			series3.data = list3;
			
			Series series2 = new Series();
			series2.name = "Sweden Fixed";
			List<Double> list2 = new ArrayList<Double>();
			list2.add(32.3);
			list2.add(25.3);
			list2.add(32.3);
			list2.add(41.3);
			list2.add(15.3);
			list2.add(47.3);
			series2.data = list2;
			
			
			
			List<Series> inVolumeSeriesList = new ArrayList<Series>();
			inVolumeSeriesList.add(series);
			inVolumeSeriesList.add(series2);
			inVolumeSeriesList.add(series3);
			List<Series> outVolumeSeriesList = new ArrayList<Series>();
			outVolumeSeriesList.add(series);
			outVolumeSeriesList.add(series2);*/
		List<Series> inVolumeSeriesList = new ArrayList<Series>();
		List<Series> outVolumeSeriesList = new ArrayList<Series>();

		if (fromDttm.isBefore(toDttm))
		{
			populate(fromDttm, toDttm, "In", inVolumeSeriesList, input.countryName);
			populate(fromDttm, toDttm, "Out", outVolumeSeriesList, input.countryName);
		}

		String notification = "Committed traffic chart rendered successfully !!!";
		logger.info(notification);
		return new CDBCommittedTrafficChartResult(notification, categories, inVolumeSeriesList, outVolumeSeriesList);
	}

	private List<DealBandGroupLite> getDbgNamesForCountry(String country, String direction)
	{
		String query = "select dbg.dbg_name from deal_band_group dbg ";
		query = query + " where dbg.dbg_id in (select dbg_id from deal_item where bnd_id in (select bnd_id from ";
		query = query + " area_connection arc, country con, area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ";
		query = query + " arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?)) and dbg.dbg_direction = ? group by dbg.dbg_name ";

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

	public class DealBandGroupLite
	{
		public String dbgName;

		public DealBandGroupLite(ExecuteSelectStamentResult rs) throws SQLException
		{
			dbgName = rs.getRs().getString("dbg_name");
		}
	}

	private void populate(DateTime fromDate, DateTime ToDate, String revCode, List<Series> volumeSeriesList, String country)
	{
		ExecuteSelectStamentResult result = null;
		try
		{
			DateTime counter = new DateTime(fromDate);
			List<DealBandGroupLite> dbgs = getDbgNamesForCountry(country, revCode);
			for (DealBandGroupLite dbg : dbgs)
			{
				Series volumeSeries = new Series();
				volumeSeries.data = new ArrayList<Double>();
				volumeSeries.name = dbg.dbgName;
				counter = new DateTime(fromDate);
				while (counter.isBefore(ToDate))
				{
					counter = counter.plusMonths(1);

					//				String query = "select dlt.dlt_to_val as committed from deal_tier dlt , deal del ,deal_band_group dbg where dlt.del_id = del.del_id and dlt.dbg_id = ? ";
					//				query = query + " and dlt.del_id in (select del_id from deal_item where bnd_id in (select bnd_id from ";
					//				query = query + " area_connection arc, country con, area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ";
					//				query = query + " arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ? group by bnd_id order by bnd_id)) and dlt.dlt_direction like ?  and ";
					//				query = query + " del.del_from_dttm <=? or del.del_to_dttm >= ? and dlt.dlt_comm_fl = 'Y' order by dlt.dlt_id ";

					/*String query = "select dlt.dlt_to_val as committed from deal_tier dlt , deal del where dlt.del_id = del.del_id and dlt.dbg_id = ? ";
					query = query + " and dlt.del_id in (select del_id from deal_item where bnd_id in (select bnd_id from ";
					query = query + " area_connection arc, country con, area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ";
					query = query + " arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?)) and dlt.dlt_direction like ?  and ";
					query = query + " del.del_from_dttm <=? or del.del_to_dttm >= ?  and (del.del_to_dttm < ? and ? > del.del_from_dttm) ";
					query = query + " or (del.del_from_dttm < ? and ? > del.del_to_dttm) and dlt.dlt_comm_fl = 'Y' order by dlt.dlt_id ";*/
					//(s2 < e1 and e2 > s1) or (s1 < e2 and e1 > s2);

					logger.info("Running for dbg_id " + dbg.dbgName + " CDBCommittedTrafficChart from date " + fromDate + " counter " + counter);

					String query = "select dlt.dlt_to_val as committed from deal_tier dlt , deal del, DEAL_BAND_GROUP dbg  where dlt.del_id = del.del_id  and dlt.dbg_id = dbg.DBG_ID  and dbg.dbg_name = ? ";
					query = query + " and dlt.del_id   in (select del_id from deal_item where bnd_id in (select bnd_id from ";
					query = query + " area_connection arc, country con, area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ";
					query = query + " arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?)) and dlt.dlt_direction = ?  and ";
					query = query + " del.del_from_dttm <=? or del.del_to_dttm >= ? and dlt.dlt_comm_fl = 'Y' order by dlt.dlt_id ";

					logger.info("from date " + fromDate);
					logger.info("counter " + counter);

					//This query needs to be changed

					logger.debug("Executing select Query : " + query);
					Object[] paramValues = new Object[] { dbg.dbgName, country, revCode, counter, fromDate };
					result = RefDBHelper.getDB().execute(query, paramValues);
					if (result.next())
					{
						double committed = result.getRs().getDouble("committed");

						logger.debug(dbg.dbgName);
						logger.debug(committed);

						volumeSeries.data.add(committed);
					}
					else
					{
						volumeSeries.data.add(0d);
					}
					fromDate = counter;
				}

				volumeSeriesList.add(volumeSeries);
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

	public class Series
	{
		public String name;
		public List<Double> data;
	}
}
