package se.signa.signature.rest;

import java.math.RoundingMode;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import se.signa.signature.dio.CDBAgreementsForCountryResult;
import se.signa.signature.dio.CountryDashboardInput;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.helpers.DBHelper.ExecuteSelectStamentResult;
import se.signa.signature.helpers.RefDBHelper;
import se.signa.signature.helpers.UsageDBHelper;

@Path("/cdbagreementsforcountry")
public class CDBAgreementsForCountryApi extends StigWebApi
{
	private static Logger logger = Logger.getLogger(CDBAgreementsForCountryApi.class);
	private static DecimalFormat dfr = new DecimalFormat("#.####");
	private static DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(Locale.US);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult cdbAgreementsForCountry(CountryDashboardInput input)
	{
		input.validate();
		input.checkRules();

		dfr.setRoundingMode(RoundingMode.CEILING);
		DecimalFormatSymbols symbols = dfr.getDecimalFormatSymbols();

		symbols.setGroupingSeparator(' ');
		df.setDecimalFormatSymbols(symbols);

		/*	List<String> headers = new LinkedList<String>();
			headers.add("Deal Destination");
			headers.add("Direction");
			headers.add("Start date");
			headers.add("End date");
			headers.add("Committed");
			headers.add("Total volume");
			headers.add("Trend");
			headers.add("Required");*/

		List<VolumeDetails> volumeDetailsList = new LinkedList<VolumeDetails>();
		/*volumeDetailsList.add(new VolumeDetails("1025 - AT&T", "In", "2015-04-01", "2015-09-30", "4 200 000", "2 069 107", "10 770", "50 736", "#F97574"));
		VolumeDetails volumeDetails = new VolumeDetails("Sweden", "In", "2015-04-01", "2015-09-30", "2 500 000", "1 145 281", "5 848", "32 255", "#F97574");
		volumeDetails.volumeDetailsList = new LinkedList<VolumeDetails>();
		volumeDetails.volumeDetailsList.add(new VolumeDetails("Sweden", "", "2015-04-01", "2015-09-30", "", "1 145 281", "5 848", "", ""));
		volumeDetailsList.add(volumeDetails);*/

		populateVolumeDetails(volumeDetailsList, "I", input.countryName);
		populateVolumeDetails(volumeDetailsList, "O", input.countryName);

		String notification = "Volume Details Rendered Successfully !!!";
		logger.info(notification);
		return new CDBAgreementsForCountryResult(notification, volumeDetailsList);
	}

	private void populateVolumeDetails(List<VolumeDetails> volumeDetailsList, String direction, String country)
	{
		List<DealBandGroupLite> dbgs = getDealBandGroups(country, direction);
		DateFormat dateFormat = new SimpleDateFormat(Constants.DASH_BOARD_DTTM_FORMAT);
		for (DealBandGroupLite dbg : dbgs)
		{
			DealTierLite dlt = getDealTiers(country, direction, dbg);
			Double totalActual = getTrafficCount(country, direction, dbg);
			
			//TODO
			VolumeTrendLite volumeTrendLite = getVolumeTrendQuery(direction, country, dbg.dbgId);
			List<VolumeDetails> volumeDetails = getBandVolumeDetails(country, direction, dbg, dlt);

			VolumeDetails volumeDetailsOuter = new VolumeDetails(dbg.dbgName, dlt.dltDirection, dateFormat.format(dlt.delFromDttm.toDate()), dateFormat.format(dlt.delToDttm.toDate()), dlt.dltToVol, totalActual, volumeTrendLite.volTrend, volumeTrendLite.requiredVol, "#F97574");
			volumeDetailsOuter.volumeDetailsList = volumeDetails;
			volumeDetailsList.add(volumeDetailsOuter);
		}
	}

	private List<VolumeDetails> getBandVolumeDetails(String country, String direction, DealBandGroupLite dbg, DealTierLite dlt)
	{
		String query = getBandVolumeDetailQuery(direction);
		DateFormat dateFormat = new SimpleDateFormat(Constants.DASH_BOARD_DTTM_FORMAT);

		ExecuteSelectStamentResult result = null;
		List<VolumeDetails> volumeDetails = new LinkedList<VolumeDetails>();
		try
		{
			result = UsageDBHelper.getDB().execute(query, new Object[] { dbg.dbgId, country });
			while (result.next())
			{
				Double totalActual = result.getRs().getDouble("rev_billed_usage");
				String bandName = result.getRs().getString("bnd_name");
				int bndId = result.getRs().getInt("bnd_id");

				Double volumeTrend = getVolumeTrendQueryForBand(direction, country, bndId);

				volumeDetails.add(new VolumeDetails(bandName, "", dateFormat.format(dlt.delFromDttm.toDate()), dateFormat.format(dlt.delToDttm.toDate()), null, totalActual, volumeTrend, null, ""));
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
		return volumeDetails;
	}

	private String getBandVolumeDetailQuery(String direction)
	{
		String query = "";
		String refName = RefDBHelper.getDB().getUserName();

		if (direction.contains("I"))
		{

			query = "select  bnd.bnd_id as bnd_id, bnd.bnd_name  as bnd_name ,  round(sum(in_rev_billed_usage)/60.00) ";
			query = query + " as rev_billed_usage from margin_summary mrc, " + refName + ".band bnd where mrc.in_bnd_id = bnd.bnd_id and mrc.in_dbg_id = ? ";
			query = query + "and mrc.in_bnd_id in (select bnd_id from ";
			query = query + " " + refName + ".area_connection arc, " + refName + ".country con, " + refName + ".area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ";
			query = query + " arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?) ";
			query = query + "and mrc.evt_dttm <= sysdate ";
			query = query + "group by bnd.bnd_id ,bnd.bnd_name ";

			logger.debug("Query for getting in band volume details : " + query);

		}
		else if (direction.contains("O"))
		{

			query = "select  bnd.bnd_id as bnd_id, bnd.bnd_name  as bnd_name ,  round(sum(out_rev_billed_usage)/60.00) ";
			query = query + " as rev_billed_usage from margin_summary mrc, " + refName + ".band bnd where mrc.out_bnd_id = bnd.bnd_id and mrc.out_dbg_id = ? ";
			query = query + "and mrc.out_bnd_id in (select bnd_id from ";
			query = query + " " + refName + ".area_connection arc, " + refName + ".country con, " + refName + ".area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ";
			query = query + " arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?) ";
			query = query + "and mrc.evt_dttm <= sysdate ";
			query = query + "group by bnd.bnd_id ,bnd.bnd_name ";

			logger.debug("Query for getting out band volume details : " + query);
		}

		return query;
	}

	private Double getTrafficCount(String country, String direction, DealBandGroupLite dbg)
	{
		String query = getTrafficCountQuery(direction);
		ExecuteSelectStamentResult result = null;
		Double totalActual = 0d;
		try
		{
			result = UsageDBHelper.getDB().execute(query, new Object[] { country, dbg.dbgName });
			if (result.next())
			{
				totalActual = result.getRs().getDouble("rev_billed_usage");
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
		return totalActual;
	}

	private String getTrafficCountQuery(String direction)
	{
		String query = "";
		String refName = RefDBHelper.getDB().getUserName();
		if (direction.contains("I"))
		{
			query = "select round(sum(in_rev_billed_usage)/60.00) as rev_billed_usage from margin_summary mrc, deal_band_group dbg ";
			query = query + " where mrc.in_bnd_id in (select bnd_id from ";
			query = query + " " + refName + ".area_connection arc, " + refName + ".country con, " + refName + ".area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ";
			query = query + " arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?) and mrc.in_dbg_id= dbg.dbg_id and dbg_name = ? and evt_dttm <=sysdate ";

			logger.debug("Query for getting in traffic count(usage) : " + query);

		}
		else if (direction.contains("O"))
		{

			query = "select round(sum(out_rev_billed_usage)/60.00) as rev_billed_usage from margin_summary mrc, deal_band_group dbg ";
			query = query + " where mrc.out_bnd_id in (select bnd_id from ";
			query = query + " " + refName + ".area_connection arc, " + refName + ".country con, " + refName + ".area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ";
			query = query + " arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?) and mrc.out_dbg_id= dbg.dbg_id and dbg_name = ? and evt_dttm <=sysdate ";

			logger.debug("Query for getting out traffic count(usage) : " + query);
		}
		return query;
	}

	private List<DealBandGroupLite> getDealBandGroups(String country, String direction)
	{
		String query = "select dbg.dbg_name from deal_band_group dbg ";
		query = query + " where dbg.dbg_id in (select dbg_id from deal_item where bnd_id in (select bnd_id from ";
		query = query + " area_connection arc, country con, area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ";
		query = query + " arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?)) and dbg.dbg_direction like ? group by dbg.dbg_name";

		logger.debug("Query for getting deal band group :  " + query);

		ExecuteSelectStamentResult result = null;
		List<DealBandGroupLite> dbgs = new ArrayList<DealBandGroupLite>();
		try
		{
			result = RefDBHelper.getDB().execute(query, new Object[] { country, "%" + direction + "%" });
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

	private DealTierLite getDealTiers(String country, String direction, DealBandGroupLite dbg)
	{
		String query = "select dlt.dlt_direction as dlt_direction, dlt.dlt_to_val as dlt_to_val,del.del_from_dttm as del_from_dttm, del.del_to_dttm as del_to_dttm from " +
				" deal_tier dlt,deal del, deal_band_group dbg where dlt.del_id = del.del_id and dlt.del_id in (select del_id from deal_item where bnd_id in (select bnd_id from ";
		query = query + " area_connection arc, country con, area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ";
		query = query + " arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?)) and dlt.dlt_direction like ? and dlt.dbg_id = dbg.dbg_id = dbg.dbg_name = ? and dlt.dlt_comm_fl = 'Y' order by dlt.dlt_id ";

		logger.debug("Query for getting deal tiers : " + query);
		ExecuteSelectStamentResult result = null;
		DealTierLite dealTier = null;
		try
		{
			result = RefDBHelper.getDB().execute(query, new Object[] { country, "%" + direction + "%", dbg.dbgName });
			if (result.next())
			{
				dealTier = new DealTierLite(result);
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
		return dealTier;
	}

	public class VolumeDetails
	{
		public String destinationGroup;
		public String direction = "";
		public String startDate = "";
		public String endDate = "";
		public String committed = "";
		public String totalVolume = "";
		public String trend = "";
		public String required = "";
		public String blockColor = "";

		public VolumeDetails(String destinationGroup, String direction, String startDate, String endDate, Double committed, Double totalVolume, Double trend, Double required, String blockColor)
		{
			this.destinationGroup = destinationGroup;
			this.direction = direction;
			this.blockColor = blockColor;

			if (startDate != null)
				this.startDate = startDate;
			if (endDate != null)
				this.endDate = endDate;
			if (committed != null)
				this.committed = String.valueOf(df.format(committed));
			if (totalVolume != null)
				this.totalVolume = String.valueOf(df.format(totalVolume));
			if (trend != null)
				this.trend = String.valueOf(df.format(trend));
			if (required != null)
				this.required = String.valueOf(df.format(required));

		}

		public List<VolumeDetails> volumeDetailsList;
	}

	private VolumeTrendLite getVolumeTrendQuery(String direction, String country, int bndId)
	{
		String refName = RefDBHelper.getDB().getUserName();
		StringBuffer query = new StringBuffer();
		if (direction.contains("I"))
		{
			query.append(" select round(reqVol/avgLast) as volume_trend , reqVol as required_vol from ");
			query.append(" (select (sum(in_rev_billed_usage)/60.00  -  dlt_to_val )/ round(to_date (to_char(del_to_dttm,'DD-MON-YYYY'),'DD-MON-YYYY') - sysdate) ");
			query.append(" as reqVol, in_deal_id as del_id from margin_summary m left join (select sum(dlt_to_val) dlt_to_val, del_id from ");
			query.append(" " + refName + ".deal_tier where del_id in (select del_id from " + refName + ".deal_item where bnd_id in (select bnd_id from ");
			query.append(" area_connection arc, country con, area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ");
			query.append(" arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?)) and dlt_comm_fl='Y' and dlt_direction='In' group by del_id) dlt ");
			query.append(" on m.in_deal_id=dlt.DEL_ID left join " + refName + ".deal del on m.in_deal_id=del.del_id ");
			query.append(" where in_bnd_id = ? and in_bnd_id in (select bnd_id from ");
			query.append(" " + refName + ".area_connection arc, " + refName + ".country con, " + refName + ".area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ");
			query.append(" arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?) group by dlt_to_val,in_deal_id, round(to_date (to_char(del_to_dttm,'DD-MON-YYYY'),'DD-MON-YYYY') - sysdate)) m1 ");
			query.append(" left join (select round((sum(in_rev_billed_usage)/60.00)/7) as avgLast, in_deal_id as del_id from margin_summary where in_bnd_id = ? and  in_bnd_id in (select bnd_id from ");
			query.append(" " + refName + ".area_connection arc, " + refName + ".country con, " + refName + ".area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ");
			query.append(" arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?) and evt_dttm >= (sysdate -7) ");
			query.append(" and evt_dttm <=(sysdate -1 ) group by in_deal_id) m2 on m1.del_id=m2.del_id");
		}
		else if (direction.contains("O"))
		{
			query.append(" select round(reqVol/avgLast) as volume_trend , reqVol as required_vol from ");
			query.append(" (select (sum(out_rev_billed_usage)/60.00  -  dlt_to_val )/ round(to_date (to_char(del_to_dttm,'DD-MON-YYYY'),'DD-MON-YYYY') - sysdate) ");
			query.append(" as reqVol, out_deal_id as del_id from margin_summary m left join (select sum(dlt_to_val) dlt_to_val, del_id from ");
			query.append(" " + refName + ".deal_tier where del_id in (select del_id from " + refName + ".deal_item where bnd_id in (select bnd_id from ");
			query.append(" area_connection arc, country con, area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ");
			query.append(" arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?)) and dlt_comm_fl='Y' and dlt_direction='In' group by del_id) dlt ");
			query.append(" on m.out_deal_id=dlt.DEL_ID left join " + refName + ".deal del on m.out_deal_id=del.del_id ");
			query.append(" where  out_bnd_id = ? and out_bnd_id in (select bnd_id from ");
			query.append(" " + refName + ".area_connection arc, " + refName + ".country con, " + refName + ".area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ");
			query.append(" arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?) group by dlt_to_val,out_deal_id, round(to_date (to_char(del_to_dttm,'DD-MON-YYYY'),'DD-MON-YYYY') - sysdate)) m1 ");
			query.append(" left join (select round((sum(out_rev_billed_usage)/60.00)/7) as avgLast, out_deal_id as del_id from margin_summary where out_bnd_id = ? and  out_bnd_id in (select bnd_id from ");
			query.append(" " + refName + ".area_connection arc, " + refName + ".country con, " + refName + ".area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ");
			query.append(" arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?) and evt_dttm >= (sysdate -7) ");
			query.append(" and evt_dttm <=(sysdate -1 ) group by out_deal_id) m2 on m1.del_id=m2.del_id");
		}
		logger.debug("Query for getting usage : " + query);

		ExecuteSelectStamentResult result = null;
		VolumeTrendLite volumeTrendLite = new VolumeTrendLite();

		try
		{
			result = UsageDBHelper.getDB().execute(query.toString(), new Object[] { country, bndId, country, bndId, country });
			if (result.next())
			{
				volumeTrendLite = new VolumeTrendLite(result);
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

		return volumeTrendLite;
	}

	private Double getVolumeTrendQueryForBand(String direction, String country, int bndId)
	{
		String refName = RefDBHelper.getDB().getUserName();
		StringBuffer query = new StringBuffer();
		if (direction.contains("I"))
		{
			query.append(" select round(reqVol/avgLast) as volume_trend from ");
			query.append(" (select (sum(in_rev_billed_usage)/60.00  -  dlt_to_val )/ round(to_date (to_char(del_to_dttm,'DD-MON-YYYY'),'DD-MON-YYYY') - sysdate) ");
			query.append(" as reqVol, in_deal_id as del_id from margin_summary m left join (select sum(dlt_to_val) dlt_to_val, del_id from ");
			query.append(" " + refName + ".deal_tier where del_id in (select del_id from " + refName + ".deal_item where bnd_id in (select bnd_id from ");
			query.append(" area_connection arc, country con, area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ");
			query.append(" arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?)) and dlt_comm_fl='Y' and dlt_direction='In' group by del_id) dlt ");
			query.append(" on m.in_deal_id=dlt.DEL_ID left join " + refName + ".deal del on m.in_deal_id=del.del_id ");
			query.append(" where in_bnd_id = ? and in_bnd_id in (select bnd_id from ");
			query.append(" " + refName + ".area_connection arc, " + refName + ".country con, " + refName + ".area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ");
			query.append(" arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?) group by dlt_to_val,in_deal_id, round(to_date (to_char(del_to_dttm,'DD-MON-YYYY'),'DD-MON-YYYY') - sysdate)) m1 ");
			query.append(" left join (select round((sum(in_rev_billed_usage)/60.00)/7) as avgLast, in_deal_id as del_id from margin_summary where in_bnd_id = ? and  in_bnd_id in (select bnd_id from ");
			query.append(" " + refName + ".area_connection arc, " + refName + ".country con, " + refName + ".area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ");
			query.append(" arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?) and evt_dttm >= (sysdate -7) ");
			query.append(" and evt_dttm <=(sysdate -1 ) group by in_deal_id) m2 on m1.del_id=m2.del_id");
		}
		else if (direction.contains("O"))
		{
			query.append(" select round(reqVol/avgLast) as volume_trend from ");
			query.append(" (select (sum(out_rev_billed_usage)/60.00  -  dlt_to_val )/ round(to_date (to_char(del_to_dttm,'DD-MON-YYYY'),'DD-MON-YYYY') - sysdate) ");
			query.append(" as reqVol, out_deal_id as del_id from margin_summary m left join (select sum(dlt_to_val) dlt_to_val, del_id from ");
			query.append(" " + refName + ".deal_tier where del_id in (select del_id from  " + refName + ".deal_item where bnd_id in (select bnd_id from ");
			query.append(" area_connection arc, country con, area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ");
			query.append(" arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?)) and dlt_comm_fl='Y' and dlt_direction='In' group by del_id) dlt ");
			query.append(" on m.out_deal_id=dlt.DEL_ID left join " + refName + ".deal del on m.out_deal_id=del.del_id ");
			query.append(" where  out_bnd_id = ? and out_bnd_id in (select bnd_id from ");
			query.append(" " + refName + ".area_connection arc, " + refName + ".country con, " + refName + ".area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ");
			query.append(" arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?) group by dlt_to_val,out_deal_id, round(to_date (to_char(del_to_dttm,'DD-MON-YYYY'),'DD-MON-YYYY') - sysdate)) m1 ");
			query.append(" left join (select round((sum(out_rev_billed_usage)/60.00)/7) as avgLast, out_deal_id as del_id from margin_summary where out_bnd_id = ? and  out_bnd_id in (select bnd_id from ");
			query.append(" " + refName + ".area_connection arc, " + refName + ".country con, " + refName + ".area ar where con.CTR_ISO_CD=ar.CTR_ISO_CD and ");
			query.append(" arc.ect_to_elt_id=ar.elt_id and con.ctr_name = ?) and evt_dttm >= (sysdate -7) ");
			query.append(" and evt_dttm <=(sysdate -1 ) group by out_deal_id) m2 on m1.del_id=m2.del_id");
		}
		logger.debug("Query for getting usage : " + query);

		ExecuteSelectStamentResult result = null;
		Double volTrend = 0d;

		try
		{
			result = UsageDBHelper.getDB().execute(query.toString(), new Object[] { country, bndId, country, bndId, country });
			if (result.next())
			{
				volTrend = result.getRs().getDouble("volume_trend");
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

		return volTrend;
	}

	public class DealBandGroupLite
	{
		public String dbgName;
		public int dbgId;

		public DealBandGroupLite(ExecuteSelectStamentResult rs) throws SQLException
		{
			dbgName = rs.getRs().getString("dbg_name");
			//dbgId = rs.getRs().getInt("dbg_id");
		}
	}

	public class VolumeTrendLite
	{
		public Double volTrend;
		public Double requiredVol;

		public VolumeTrendLite(ExecuteSelectStamentResult rs) throws SQLException
		{
			volTrend = rs.getRs().getDouble("volume_trend");
			requiredVol = rs.getRs().getDouble("required_vol");
		}

		public VolumeTrendLite()
		{
			// TODO Auto-generated constructor stub
		}
	}

	public class DealTierLite
	{
		public Double dltToVol;
		public String dltDirection;
		public DateTime delFromDttm;
		public DateTime delToDttm;

		public DealTierLite(ExecuteSelectStamentResult rs) throws SQLException
		{
			dltToVol = rs.getRs().getDouble("dlt_to_val");
			dltDirection = rs.getRs().getString("dlt_direction");
			delFromDttm = getDateTime(rs.getRs().getTimestamp("del_from_dttm"));
			delToDttm = getDateTime(rs.getRs().getTimestamp("del_to_dttm"));
		}

		protected DateTime getDateTime(Timestamp timestamp)
		{
			if (timestamp == null)
				return null;
			return new DateTime(timestamp);
		}
	}
}
