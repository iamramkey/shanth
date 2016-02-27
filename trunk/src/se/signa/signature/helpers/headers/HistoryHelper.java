/**
 * Copyright SIGNA AB, STOCKHOLM, SWEDEN
 */

package se.signa.signature.helpers.headers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import se.signa.signature.common.SignatureException;
import se.signa.signature.gen.dba.ToolbarItemDba;
import se.signa.signature.gen.dbo.ToolbarItem;

public class HistoryHelper
{

	private static HistoryHelper INSTANCE = null;
	private static final Logger logger = Logger.getLogger(HistoryHelper.class);

	private Map<Integer, List<UserHistoryModel>> allHistory = null;
	private Map<String, UserHistoryModel> ushModels = null;

	// this is a singleton class- single entry point
	public synchronized static HistoryHelper getI() throws SignatureException
	{
		if (INSTANCE == null)
			INSTANCE = new HistoryHelper();
		return INSTANCE;
	}

	private HistoryHelper() throws SignatureException
	{
		allHistory = new HashMap<Integer, List<UserHistoryModel>>();
		prepareUshModels();
		logger.info("History Helper initialised");
	}

	private void prepareUshModels()
	{
		ushModels = new HashMap<String, UserHistoryModel>();
		List<ToolbarItem> toolbarItems = ToolbarItemDba.getI().fetchAll();
		for (ToolbarItem toolbarItem : toolbarItems)
			ushModels.put(toolbarItem.getTbiUrl(), new UserHistoryModel(toolbarItem.getTbiIcon(), toolbarItem.getTbiName(), toolbarItem.getTbiUrl()));
	}

	public List<UserHistoryModel> getHistory(int usrId, String currentUrl)
	{
		List<UserHistoryModel> history = allHistory.get(usrId);
		if (history == null)
			history = prepareHistory(usrId);

		addCurrentToHistory(usrId, currentUrl, history);

		return history;
	}

	private List<UserHistoryModel> prepareHistory(int usrId)
	{
		List<UserHistoryModel> history = new LinkedList<UserHistoryModel>();
		/*List<UserHistory> userHistoryList = UserHistoryDba.getI().fetchByUsrId(usrId);
		for (UserHistory userHistory : userHistoryList)
			history.add(new UserHistoryModel(userHistory.getUshIcon(), userHistory.getUshName(), userHistory.getUshUrl()));
		allHistory.put(usrId, history);*/
		return history;
	}

	private void addCurrentToHistory(int usrId, String currentUrl, List<UserHistoryModel> history)
	{
		UserHistoryModel ushModel = ushModels.get(currentUrl);
		if (ushModel == null)
			return;

		history.add(ushModel);

		boolean shouldRemoveFromHisory = true;
		for (int i = 0; i < history.size() - 1; i++)
		{
			UserHistoryModel historyModel = history.get(i);
			if (currentUrl.equalsIgnoreCase(historyModel.ushUrl))
			{
				shouldRemoveFromHisory = false;
				history.remove(i);
				break;
			}
		}

		if (shouldRemoveFromHisory && history.size() > 5)
			history.remove(0);
	}

	public void logout(int usrId)
	{
		List<UserHistoryModel> history = allHistory.get(usrId);
		if (history == null)
			return;

		/*UserHistoryDba.getI().remove(usrId);
		for (UserHistoryModel tbiModel : history)
		{
			UserHistory userHistory = new UserHistoryImpl(tbiModel, usrId);
			UserHistoryDba.getI().create(userHistory, usrId);
		}*/

		allHistory.remove(usrId);
	}

}
