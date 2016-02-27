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
import se.signa.signature.dio.FileSearchInput;
import se.signa.signature.dio.FileSearchResult;
import se.signa.signature.dio.NegativeResult;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.gen.dba.FiletblDba;
import se.signa.signature.gen.dbo.Filetbl;
import se.signa.signature.helpers.DataValidator;

@Path("/filesearch")
public class FileSearchApi
{
	private static Logger logger = Logger.getLogger(FileSearchApi.class);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult fileSearch(FileSearchInput input)
	{
		long start = System.currentTimeMillis();
		logger.debug("Server started file search api..");

		input.validate();
		input.checkRules();

		StringBuffer whereClause = new StringBuffer();
		whereClause.append(FiletblDba.getI().getDefaultWhereClause());
		List<Object> whereParams = new ArrayList<Object>();

		if (DataValidator.validateMandatoryString(input.filName))
			FiletblDba.getI().addFileNameFilter(input.filName, whereClause, whereParams);

		int rowCount = Integer.parseInt(input.rowCount);
		FiletblDba.getI().addRowCountFilter(rowCount + 1, whereClause);

		DateTime fromDttm = addFromDttmFilter(input, whereClause, whereParams);
		DateTime toDttm = addtoDttmFilter(input, whereClause, whereParams);

		if (fromDttm != null && toDttm != null & fromDttm.isAfter(toDttm))
			return new NegativeResult(Constants.ErrCode.INPUT_FIELD_INVALID, "To date must be > than from date", "filDttmTo");

		String whereClauseString = whereClause.toString();
		Object[] whereParamsArray = whereParams.toArray();
		List<Filetbl> fileTblList = FiletblDba.getI().fetchAllFiles(whereClauseString, whereParamsArray);
		int totalCount = FiletblDba.getI().fetchCount(whereClauseString, whereParamsArray);

		long end = System.currentTimeMillis();
		logger.debug(" File Search returned  " + fileTblList.size() + " Rows in " + (end - start) + " ms ");
		String notification = "File search fetched : " + fileTblList.size() + " rows of " + totalCount + " in database";
		logger.info(notification);
		return new FileSearchResult(notification, fileTblList);
	}

	private DateTime addtoDttmFilter(FileSearchInput input, StringBuffer whereClause, List<Object> whereParams)
	{
		DateTime toDttm = null;
		try
		{
			toDttm = DateTime.parse(input.filDttmTo, Constants.dtf);
			toDttm = toDttm.plusDays(1).minusSeconds(1);
			FiletblDba.getI().addFilDttmTo(toDttm, whereClause, whereParams);
		}
		catch (Exception e)
		{
			String message = "Entered DateTime format is invalid,Use the following Format" + Constants.PARSE_INPUT_DTTM_FORMAT;
			NegativeResult nr = new NegativeResult(Constants.ErrCode.INPUT_FIELD_INVALID, message, "filStartDttmTo");
			throw new NegativeResultException(nr);
		}
		return toDttm;
	}

	private DateTime addFromDttmFilter(FileSearchInput input, StringBuffer whereClause, List<Object> whereParams)
	{
		DateTime fromDttm = null;
		try
		{
			fromDttm = DateTime.parse(input.filDttmFrom, Constants.dtf);
			FiletblDba.getI().addFilDttmFrom(fromDttm, whereClause, whereParams);
		}
		catch (Exception e)
		{
			String message = "Entered From DateTime format is invalid,Use the following Format " + Constants.PARSE_INPUT_DTTM_FORMAT;
			NegativeResult nr = new NegativeResult(Constants.ErrCode.INPUT_FIELD_INVALID, message, "filStartDttmFrom");
			throw new NegativeResultException(nr);
		}
		return fromDttm;
	}
}
