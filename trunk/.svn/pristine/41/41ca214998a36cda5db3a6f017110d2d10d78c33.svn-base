package se.signa.signature.dba;

import java.util.List;

import se.signa.signature.dbo.UserTblImpl;
import se.signa.signature.gen.dba.UserTblDba;
import se.signa.signature.gen.dbo.UserTbl;
import se.signa.signature.helpers.RefDBHelper;

public class UserTblDbaImpl extends UserTblDba
{
	public UserTbl fetchByUsrName(String usrName)
	{
		String query = " select * from USER_TBL where USR_NAME = ? ";
		return RefDBHelper.getDB().fetchObjectIfExists(query, usrName, UserTblImpl.class);
	}

	public UserTbl fetchByUsrEmail(String usrEmail)
	{
		String query = " select * from USER_TBL where USR_EMAIL = ? ";
		return RefDBHelper.getDB().fetchObjectIfExists(query, usrEmail, UserTblImpl.class);
	}

	public UserTbl fetchByUsrEmail(String usrEmail, int updateUsrId)
	{
		String query = " select * from USER_TBL where USR_EMAIL = ? and USR_ID <> ?";
		return RefDBHelper.getDB().fetchObjectIfExists(query, new Object[] { usrEmail, updateUsrId }, UserTblImpl.class);
	}

	@Override
	public List<UserTbl> fetchAll()
	{
		String query = " select * from user_tbl where usr_id >= 10";
		return RefDBHelper.getDB().fetchList(query, UserTblImpl.class);
	}

	public int fetchCountByEmail(String email)
	{
		String query = " from user_tbl where usr_email = ?";
		return RefDBHelper.getDB().fetchCount(query, email);
	}

	public UserTbl fetchByUserId(int usrId)
	{
		return RefDBHelper.getDB().fetchObjectIfExists(" select * from user_tbl where usr_id=? ", usrId, UserTblImpl.class);
	}

}