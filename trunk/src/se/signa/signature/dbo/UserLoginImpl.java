/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import org.joda.time.DateTime;

import se.signa.signature.common.Constants;
import se.signa.signature.gen.dbo.UserLogin;
import se.signa.signature.gen.dbo.UserTbl;

public class UserLoginImpl extends UserLogin
{
	public UserLoginImpl()
	{

	}

	public UserLoginImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

	public UserLoginImpl(UserTbl user)
	{
		setUsrId(user.getUsrId());
		setUslLoginDttm(new DateTime());
		setUslStatus(Constants.LOGIN_SUCCESS);
		setUslName(user.getUsrName() + " logged in at " + getUslLoginDttm().toString());
	}

	public UserLoginImpl(UserTbl user, String reason)
	{
		setUsrId(user.getUsrId());
		setUslLoginDttm(new DateTime());
		setUslStatus("Failed");
		setUslName(user.getUsrName() + " could not log in at " + getUslLoginDttm().toString() + " because " + reason);
	}

}