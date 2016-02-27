/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import se.signa.signature.common.Constants;
import se.signa.signature.common.NegativeResultException;
import se.signa.signature.dio.AlertSearchInput;
import se.signa.signature.dio.AlertSearchResult;
import se.signa.signature.dio.NegativeResult;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.gen.dba.AlertDba;
import se.signa.signature.gen.dbo.Alert;
import se.signa.signature.helpers.DataValidator;

@Path("/alertsearch")
public class AlertSearchApi extends StigWebApi
{
	private static Logger logger = Logger.getLogger(AlertSearchApi.class);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult alertSearch(AlertSearchInput input)
	{
		input.validate();
		input.checkRules();

		StringBuffer whereClause = new StringBuffer();
		whereClause.append(AlertDba.getI().getDefaultWhereClause());
		List<Object> whereParams = new ArrayList<Object>();

		if (DataValidator.validateMandatoryString(input.nodeName))
			AlertDba.getI().addNodeNameFilter(input.nodeName, whereClause, whereParams);

		if (DataValidator.validateMandatoryString(input.alertType))
			AlertDba.getI().addAlertTypeFilter(input.alertType, whereClause, whereParams);

		DateTime fromDttm = addFromDttmFilter(input, whereClause, whereParams);
		DateTime toDttm = addtoDttmFilter(input, whereClause, whereParams);

		if (fromDttm != null && toDttm != null & fromDttm.isAfter(toDttm))
			return new NegativeResult(Constants.ErrCode.INPUT_FIELD_INVALID, "To date must be > than from date", "alertCreatedDttmTo");

		String whereClauseString = whereClause.toString();
		Object[] whereParamsArray = whereParams.toArray();

		List<Alert> alertList = AlertDba.getI().fetchAllAlerts(whereClauseString, whereParamsArray);

		String notification = "Alert Search is complete.  Fetched : " + alertList.size() + " Rows.";
		logger.info(notification);
		return new AlertSearchResult(notification, alertList);
	}

	private DateTime addtoDttmFilter(AlertSearchInput input, StringBuffer whereClause, List<Object> whereParams)
	{
		DateTime createdToDttm = null;
		try
		{

			createdToDttm = DateTime.parse(input.alertCreatedDttmTo, Constants.dtf);
			createdToDttm = createdToDttm.plusDays(1).minusSeconds(1);
			AlertDba.getI().addAlertCreatedDttmTo(createdToDttm, whereClause, whereParams);

		}
		catch (Exception e)
		{
			String message = "Entered To DateTime format is invalid,Use the following Format" + Constants.PARSE_INPUT_DTTM_FORMAT;
			NegativeResult nr = new NegativeResult(Constants.ErrCode.INPUT_FIELD_INVALID, message, "alertCreatedDttmTo");
			throw new NegativeResultException(nr);
		}
		return createdToDttm;
	}

	private DateTime addFromDttmFilter(AlertSearchInput input, StringBuffer whereClause, List<Object> whereParams)
	{
		DateTime createdFromDttm = null;
		try
		{
			createdFromDttm = DateTime.parse(input.alertCreatedDttmFrom, Constants.dtf);
			AlertDba.getI().addAlertCreatedDttmFrom(createdFromDttm, whereClause, whereParams);
		}
		catch (Exception e)
		{
			String message = "Entered From DateTime format is invalid,Use the following Format " + Constants.PARSE_INPUT_DTTM_FORMAT;
			NegativeResult nr = new NegativeResult(Constants.ErrCode.INPUT_FIELD_INVALID, message, "alertCreatedDttmFrom");
			throw new NegativeResultException(nr);
		}
		return createdFromDttm;
	}
}
