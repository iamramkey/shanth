package se.signa.signature.helpers;

import org.joda.time.DateTime;

import se.signa.signature.common.Constants;
import se.signa.signature.common.Constants.ErrCode;
import se.signa.signature.common.NegativeResultException;
import se.signa.signature.dio.NegativeResult;
import se.signa.signature.gen.dba.ServerSettingsDba;
import se.signa.signature.gen.dba.UserLoginDba;
import se.signa.signature.gen.dba.UserRolePermissionDba;
import se.signa.signature.gen.dba.UserSessionDba;
import se.signa.signature.gen.dba.UserTblDba;
import se.signa.signature.gen.dbo.ServerSettings;
import se.signa.signature.gen.dbo.UserLogin;
import se.signa.signature.gen.dbo.UserSession;
import se.signa.signature.gen.dbo.UserTbl;

public class PermissionHelper
{
	public static void validate(String entity, String action, int usrId, int ussSessionCode)
	{

		UserTbl user = UserTblDba.getI().fetchByUserId(usrId);
		if (null == user)
		{
			NegativeResult nr = new NegativeResult(ErrCode.PERMISSION_DENIED, "User Id doest not exist", "usrId");
			throw new NegativeResultException(nr);
		}

		if (!Constants.USER_ACTIVE_STATUS.equalsIgnoreCase(user.getUsrStatus()))
		{
			NegativeResult nr = new NegativeResult(ErrCode.PERMISSION_DENIED, "User is InActive", "UsrStatus");
			throw new NegativeResultException(nr);
		}

		int ulhId = validateUserLogin(usrId);
		validateUserSession(usrId, ussSessionCode, ulhId);

		// if he has come till here, usr,login,session are valid
		int permissionCount = UserRolePermissionDba.getI().fetchUserRolePermission(user.getRolId(), entity, action);
		if (permissionCount <= 0)
		{
			NegativeResult nr = new NegativeResult(ErrCode.PERMISSION_DENIED, "User : " + user.getUsrName() + "  doest not have access for this action: " + action + " on entity :" + entity, "usrId");
			throw new NegativeResultException(nr);
		}

	}

	protected static int validateUserLogin(int usrId)
	{
		UserLogin userLogin = UserLoginDba.getI().fetchByUserId(usrId);
		if (null == userLogin)
		{
			NegativeResult nr = new NegativeResult(ErrCode.PERMISSION_DENIED, "User Id has not logged in", "usrId");
			throw new NegativeResultException(nr);
		}
		return userLogin.getUslId();
	}

	protected static void validateUserSession(int usrId, int ussSessionCode, int ulhId)
	{
		ServerSettings serverSettings = ServerSettingsDba.getI().fetchBySesCode(Constants.SES_CODE_SESSION_TIMEOUT);
		UserSession userSession = UserSessionDba.getI().getSessionUsingUlhId(ulhId);
		if (ussSessionCode == userSession.getUssSessionCode())
		{
			DateTime now = new DateTime();
			DateTime validTxTime = now.minusMinutes(Integer.parseInt(serverSettings.getSesValue()));

			boolean inValid = validTxTime.isAfter(userSession.getUssLastTxnDttm());
			if (!inValid)
			{
				userSession.setUssLastTxnDttm(now);
				UserSessionDba.getI().update(userSession, usrId);
				return;
			}
		}

		//if u reach here , either session code is not matching or session has expired, 
		DateTime logOutTime = new DateTime();
		UserLogin userLogin = UserLoginDba.getI().fetchByPk(userSession.getUslId());
		userLogin.setUslLogoutDttm(logOutTime);
		UserLoginDba.getI().update(userLogin, usrId);
		UserSessionDba.getI().deleteByUsrId(usrId);

		NegativeResult nr = new NegativeResult(ErrCode.PERMISSION_DENIED, "User Session is invalid or expired", "ussSessionCode");
		throw new NegativeResultException(nr);
	}

}
