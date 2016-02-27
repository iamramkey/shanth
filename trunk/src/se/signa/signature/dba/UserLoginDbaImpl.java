package se.signa.signature.dba;

import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.Constants;
import se.signa.signature.dbo.UserLoginImpl;
import se.signa.signature.gen.dba.UserLoginDba;
import se.signa.signature.gen.dbo.UserLogin;
import se.signa.signature.gen.dbo.UserTbl;
import se.signa.signature.helpers.RefDBHelper;

public class UserLoginDbaImpl extends UserLoginDba
{

	public int countByUsrId(int usrId)
	{
		String query = " from user_login where usr_id = ? and usl_logout_dttm is null ";
		return RefDBHelper.getDB().fetchCount(query, usrId);
	}

	public void updateLogOutTimeForUslId(int uslId)
	{
		String query = " update user_login set usl_logout_dttm=? where usl_id=? ";
		Object[] paramValues = new Object[] { new DateTime(), uslId };
		RefDBHelper.getDB().executeUpdate(query, paramValues);
	}

	public void updateLogOutTime(int usrId)
	{
		String query = " update user_login set usl_logout_dttm=? where usr_id=? and usl_logout_dttm is null ";
		Object[] paramValues = new Object[] { new DateTime(), usrId };
		RefDBHelper.getDB().executeUpdate(query, paramValues);
	}

	public List<UserLogin> fetchRecentLogins(Integer batchSize, UserTbl user)
	{
		String query = " select * from ( select * from user_login where usr_id  = ? order by usl_created_dttm desc ) usl where rownum <= ? ";
		Object[] paramValues = new Object[] { user.getUsrId(), batchSize };
		return RefDBHelper.getDB().fetchList(query, paramValues, UserLoginImpl.class);
	}

	public UserLogin fetchRecentLogin(UserTbl user)
	{
		String query = " select * from ( select * from user_login where usl_status = ? and usr_id  = ? order by usl_created_dttm desc ) usl where rownum <= ?";
		Object[] paramValues = new Object[] { Constants.LOGIN_SUCCESS, user.getUsrId(), 1 };
		return RefDBHelper.getDB().fetchObjectIfExists(query, paramValues, UserLoginImpl.class);
	}

	public UserLogin fetchByUserId(int usrId)
	{
		return RefDBHelper.getDB().fetchObjectIfExists(" select * from user_login where usr_id=? and usl_logout_dttm is null", usrId, UserLoginImpl.class);
	}
}