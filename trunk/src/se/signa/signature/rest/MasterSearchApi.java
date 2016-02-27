package se.signa.signature.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import se.signa.signature.common.Constants;
import se.signa.signature.common.Constants.ErrCode;
import se.signa.signature.common.SignatureDba;
import se.signa.signature.common.SignatureDbo;
import se.signa.signature.dio.ActionDataModel;
import se.signa.signature.dio.FormData;
import se.signa.signature.dio.MasterSearchInput;
import se.signa.signature.dio.MasterSearchResult;
import se.signa.signature.dio.NegativeResult;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.gen.dba.MasterSearchActionDba;
import se.signa.signature.gen.dba.MasterSearchColumnDba;
import se.signa.signature.gen.dba.MasterSearchDba;
import se.signa.signature.gen.dba.MasterSearchFormDba;
import se.signa.signature.gen.dbo.MasterSearch;
import se.signa.signature.gen.dbo.MasterSearchAction;
import se.signa.signature.gen.dbo.MasterSearchColumn;
import se.signa.signature.gen.dbo.MasterSearchForm;
import se.signa.signature.helpers.DbaInstanceHelper;
import se.signa.signature.helpers.headers.BreadCrumHelper;
import se.signa.signature.helpers.headers.BreadCrumModel;
import se.signa.signature.helpers.headers.HistoryHelper;
import se.signa.signature.helpers.headers.UserHistoryModel;

@Path("/mastersearch")
public class MasterSearchApi extends StigWebApi
{
	private static Logger logger = Logger.getLogger(MasterSearchApi.class);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult masterSearch(MasterSearchInput input)
	{
		logger.info("Master Search for " + input.name);
		input.validate();
		input.checkRules();

		String url = Constants.MASTER_URL + input.name;

		logger.info("Quering Master Search Configuration for : " + url);
		MasterSearch masterSearch = MasterSearchDba.getI().fetchByUrl(url);
		if (masterSearch == null)
			return new NegativeResult(ErrCode.INPUT_FIELD_MISSING, "Master Search Configuration missing for " + input.name, "name");

		SignatureDba dba = DbaInstanceHelper.getI().getDba(input.name);
		List<SignatureDbo> dbos = (List<SignatureDbo>) dba.fetchAll();

		List<MasterSearchColumn> mscList = MasterSearchColumnDba.getI().fetchByMse(masterSearch.getMseId());
		List<List<Object>> data = new ArrayList<List<Object>>();
		for (SignatureDbo dbo : dbos)
		{
			List<Object> row = new ArrayList<Object>();
			row.add(dbo.getPk());
			for (MasterSearchColumn msc : mscList)
				row.add(dbo.getProperty(msc.getMcoProperty()));
			data.add(row);
		}

		String notification = input.name + " Search completed with " + dbos.size() + " rows";

		logger.info(notification);

		//	if (input.fullLoad)
		{
			// do tasks other than search also
			List<MasterSearchAction> msaRowList = MasterSearchActionDba.getI().fetchRowByMse(masterSearch.getMseId());
			List<MasterSearchAction> msaHeaderList = MasterSearchActionDba.getI().fetchHeaderByMse(masterSearch.getMseId());
			List<MasterSearchForm> msfList = MasterSearchFormDba.getI().fetchByMse(masterSearch.getMseId());

			List<String> headers = new ArrayList<String>();
			headers.add("Id");
			for (MasterSearchColumn msc : mscList)
				headers.add(msc.getMcoHeader());

			List<ActionDataModel> rowActions = new ArrayList<ActionDataModel>();
			for (MasterSearchAction msa : msaRowList)
				rowActions.add(new ActionDataModel(msa));
			if (rowActions.size() > 0)
				headers.add("Actions");

			List<ActionDataModel> headerActions = new ArrayList<ActionDataModel>();
			for (MasterSearchAction msa : msaHeaderList)
				headerActions.add(new ActionDataModel(msa));

			List<FormData> form = new ArrayList<FormData>();
			for (MasterSearchForm msf : msfList)
				form.add(new FormData(msf));
			List<UserHistoryModel> history = HistoryHelper.getI().getHistory(input.usrId, url);
			List<BreadCrumModel> breadCrum = BreadCrumHelper.getI().getCrums(url);

			logger.info(notification);
			return new MasterSearchResult(notification, headers, data, headerActions, rowActions, form, history, breadCrum);
		}

		//TODO: to use lite api after ui handles full load false result
		//	logger.info(notification);
		//	return new MasterSearchResultLite(notification, headers, data);
	}
}