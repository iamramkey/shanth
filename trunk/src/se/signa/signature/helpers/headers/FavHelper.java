/**
 * Copyright SIGNA AB, STOCKHOLM, SWEDEN
 */

package se.signa.signature.helpers.headers;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import se.signa.signature.common.SignatureException;

public class FavHelper
{

	private static FavHelper INSTANCE = null;
	private static final Logger logger = Logger.getLogger(FavHelper.class);

	// this is a singleton class- single entry point
	public synchronized static FavHelper getI() throws SignatureException
	{
		if (INSTANCE == null)
			INSTANCE = new FavHelper();
		return INSTANCE;
	}

	private FavHelper() throws SignatureException
	{
		logger.info("Fav Helper initialised");
	}

	public List<FavModel> getFavs(int usrId)
	{
		List<FavModel> favs = new LinkedList<FavModel>();
		/*List<UserFavourite> userFavouriteList = UserFavouriteDba.getI().fetchAllByUsrId(usrId);
		for (UserFavourite userFav : userFavouriteList)
		{
			ToolbarItem tbi = ToolbarItemDba.getI().fetchByPk(userFav.getTbiId());
			favs.add(new FavModel(tbi.getTbiIcon(), tbi.getTbiName(), tbi.getTbiUrl()));
		}*/
		return favs;
	}

}
