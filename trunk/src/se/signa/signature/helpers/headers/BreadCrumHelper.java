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
import se.signa.signature.gen.dba.ToolbarDba;
import se.signa.signature.gen.dba.ToolbarItemDba;
import se.signa.signature.gen.dbo.Toolbar;
import se.signa.signature.gen.dbo.ToolbarItem;

public class BreadCrumHelper
{

	private static BreadCrumHelper INSTANCE = null;
	private static final Logger logger = Logger.getLogger(BreadCrumHelper.class);

	private Map<String, List<BreadCrumModel>> allCrums = null;

	// this is a singleton class- single entry point
	public synchronized static BreadCrumHelper getI() throws SignatureException
	{
		if (INSTANCE == null)
			INSTANCE = new BreadCrumHelper();
		return INSTANCE;
	}

	private BreadCrumHelper() throws SignatureException
	{
		allCrums = new HashMap<String, List<BreadCrumModel>>();
		populateTbiModels();
		logger.info("Breadcrum Helper initialised");
	}

	private void populateTbiModels()
	{
		allCrums = new HashMap<String, List<BreadCrumModel>>();
		List<ToolbarItem> toolbarItems = ToolbarItemDba.getI().fetchAll();
		for (ToolbarItem toolbarItem : toolbarItems)
		{
			List<BreadCrumModel> crum = new LinkedList<BreadCrumModel>();
			Toolbar toolbar = ToolbarDba.getI().fetchByPk(toolbarItem.getTbrId());
			crum.add(new BreadCrumModel(toolbar.getTbrIcon(), toolbar.getTbrName()));
			crum.add(new BreadCrumModel(toolbarItem.getTbiIcon(), toolbarItem.getTbiName()));
			allCrums.put(toolbarItem.getTbiUrl(), crum);
		}
	}

	public List<BreadCrumModel> getCrums(String url)
	{
		return allCrums.get(url);
	}
}
