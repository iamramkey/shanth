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
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import se.signa.signature.common.Constants;
import se.signa.signature.dio.AuditFileSearchInput;
import se.signa.signature.dio.AuditFileSearchResult;
import se.signa.signature.dio.NegativeResult;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.gen.dba.AuditFileDba;
import se.signa.signature.gen.dbo.AuditFile;
import se.signa.signature.helpers.DataValidator;

@Path("/auditfilesearch")
public class AuditFileSearchApi
{
	private static Logger logger = Logger.getLogger(AuditFileSearchApi.class);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult auditFileSearch(AuditFileSearchInput input)
	{
		logger.info("Server hitted Audit file search api !!!!");

		input.validate();
		input.checkRules();

		StringBuffer whereClause = new StringBuffer();
		whereClause.append(AuditFileDba.getI().getDefaultWhereClause());
		List<Object> whereParams = new ArrayList<Object>();

		if (DataValidator.validateMandatoryString(input.filName))
			AuditFileDba.getI().addFileNameFilter(input.filName, whereClause, whereParams);

		if (DataValidator.validateMandatoryString(input.parentFilName))
			AuditFileDba.getI().addParentFileNameFilter(input.parentFilName, whereClause, whereParams);

		if (DataValidator.validateMandatoryString(input.aufFilType))
			AuditFileDba.getI().addAufFilTypeFilter(input.aufFilType, whereClause, whereParams);

		int rowCount = Integer.parseInt(input.rowCount);
		AuditFileDba.getI().addRowCountFilter(rowCount + 1, whereClause);

		DateTimeFormatter dtf = DateTimeFormat.forPattern(Constants.PARSE_INPUT_DTTM_FORMAT);
		DateTime fromDttm = null;
		DateTime toDttm = null;
		if (DataValidator.validateMandatoryString(input.aufDttmFrom))
		{
			try
			{
				fromDttm = DateTime.parse(input.aufDttmFrom, dtf);
				AuditFileDba.getI().addAufDttmFrom(fromDttm, whereClause, whereParams);
			}
			catch (Exception e)
			{
				return new NegativeResult(Constants.ErrCode.INPUT_FIELD_INVALID, "Entered DateTime format is invalid,Use the following Format " + Constants.PARSE_INPUT_DTTM_FORMAT, input.aufDttmFrom);
			}
		}

		if (DataValidator.validateMandatoryString(input.aufDttmTo))
		{
			try
			{
				toDttm = DateTime.parse(input.aufDttmTo, dtf);
				toDttm = toDttm.plusDays(1).minusSeconds(1);
				AuditFileDba.getI().addAufDttmTo(toDttm, whereClause, whereParams);
			}
			catch (Exception e)
			{
				return new NegativeResult(Constants.ErrCode.INPUT_FIELD_INVALID, "Entered DateTime format is invalid,Use the following Format" + Constants.PARSE_INPUT_DTTM_FORMAT, input.aufDttmTo);
			}
		}

		if (fromDttm != null && toDttm != null & fromDttm.isAfter(toDttm))
			return new NegativeResult(Constants.ErrCode.INPUT_FIELD_INVALID, "To date must be > than from date", input.aufDttmTo);

		List<AuditFile> auditFileList = AuditFileDba.getI().fetchAllAuditFilesByDttm(whereClause.toString(), whereParams.toArray());

		logger.info("Audit file search api excecuted successfully !!!!");

		return new AuditFileSearchResult("Audit File Search is complete", auditFileList);
	}

}
