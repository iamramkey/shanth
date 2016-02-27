/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import org.joda.time.DateTime;

import se.signa.signature.gen.dbo.UserPassword;
import se.signa.signature.gen.dbo.UserTbl;

public class UserPasswordImpl extends UserPassword
{
	public UserPasswordImpl()
	{
	}

	public UserPasswordImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

	public UserPasswordImpl(UserTbl user)
	{
		setUsrId(user.getUsrId());
		setUspPassword(user.getUsrPassword());
		setUspChangedDttm(new DateTime());
		setUspName("User :" + user.getUsrDisplayName() + " changed password at " + getUspChangedDttm());
	}
}