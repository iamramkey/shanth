package se.signa.signature.dba;

import se.signa.signature.dbo.UserSessionImpl;
import se.signa.signature.gen.dba.UserSessionDba;
import se.signa.signature.gen.dbo.UserSession;
import se.signa.signature.helpers.RefDBHelper;

public class UserSessionDbaImpl extends UserSessionDba
{

	public void deleteByUsrId(int usrId)
	{
		String query = "delete from user_session where usl_id in ( select usl_id from user_login where usr_id = ? ) ";
		RefDBHelper.getDB().executeUpdate(query, usrId);
	}

	public void deleteByUssId(int ussId)
	{
		String query = "delete from user_session where uss_id = ? ";
		RefDBHelper.getDB().executeUpdate(query, ussId);
	}

	public UserSession getSessionUsingUlhId(int ulhId)
	{
		return RefDBHelper.getDB().fetchObjectIfExists(" select * from user_session where usl_id=? ", ulhId, UserSessionImpl.class);
	}

	public UserSession getSessionUsingByCode(int ussSessionCode)
	{
		return RefDBHelper.getDB().fetchObjectIfExists(" select * from user_session where uss_session_code=? ", ussSessionCode, UserSessionImpl.class);
	}

}