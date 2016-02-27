/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dba;

import se.signa.signature.dbo.UserPasswordImpl;
import se.signa.signature.gen.dba.UserPasswordDba;
import se.signa.signature.gen.dbo.UserPassword;
import se.signa.signature.gen.dbo.UserTbl;
import se.signa.signature.helpers.RefDBHelper;

public class UserPasswordDbaImpl extends UserPasswordDba
{

	public int count(int usrId, String usrNewPassword)
	{
		String query = " from user_password where usr_id = ? and usp_password = ?";
		return RefDBHelper.getDB().fetchCount(query, new Object[] { usrId, usrNewPassword });
	}

	public UserPassword fetchRecentPassword(UserTbl user)
	{
		String query = "select * from user_password where usp_id in (select max(usp_id) from user_password where usr_id = ?) ";
		return RefDBHelper.getDB().fetchObjectIfExists(query, user.getUsrId(), UserPasswordImpl.class);
	}

}