/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import org.joda.time.DateTime;

import se.signa.signature.gen.dbo.UserSession;
import se.signa.signature.gen.dbo.UserTbl;

public class UserSessionImpl extends UserSession
{

	public UserSessionImpl()
	{

	}

	public UserSessionImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

	public UserSessionImpl(UserTbl userTbl, int uslId, int ussSessionCode)
	{
		setUslId(uslId);
		setUssSessionCode(ussSessionCode);
		setUssLastTxnDttm(new DateTime());
		setUssName(userTbl.getUsrName() + " session started at " + getUssLastTxnDttm());
	}

}