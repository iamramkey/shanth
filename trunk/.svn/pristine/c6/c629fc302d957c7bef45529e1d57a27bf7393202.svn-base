package se.signa.signature.rest;

import java.util.List;
import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import se.signa.signature.common.Constants;
import se.signa.signature.common.Constants.ErrCode;
import se.signa.signature.common.NegativeResultException;
import se.signa.signature.dbo.ToolbarImpl;
import se.signa.signature.dbo.UserLoginImpl;
import se.signa.signature.dbo.UserSessionImpl;
import se.signa.signature.dio.LogInInput;
import se.signa.signature.dio.LogInResult;
import se.signa.signature.dio.NegativeResult;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.gen.dba.ToolbarDba;
import se.signa.signature.gen.dba.ToolbarItemDba;
import se.signa.signature.gen.dba.UserLoginDba;
import se.signa.signature.gen.dba.UserPasswordDba;
import se.signa.signature.gen.dba.UserPasswordSettingsDba;
import se.signa.signature.gen.dba.UserSessionDba;
import se.signa.signature.gen.dba.UserTblDba;
import se.signa.signature.gen.dbo.Toolbar;
import se.signa.signature.gen.dbo.ToolbarItem;
import se.signa.signature.gen.dbo.UserLogin;
import se.signa.signature.gen.dbo.UserPassword;
import se.signa.signature.gen.dbo.UserPasswordSettings;
import se.signa.signature.gen.dbo.UserSession;
import se.signa.signature.gen.dbo.UserTbl;

@Path("/login")
public class LogInApi extends StigWebApi
{
	private static Logger logger = Logger.getLogger(LogInApi.class);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult login(LogInInput loginInput)
	{
		logger.info("LoginApi");
		loginInput.validate();

		UserTbl user = UserTblDba.getI().fetchByUsrName(loginInput.usrName);
		if (user == null)
			return new NegativeResult(ErrCode.INPUT_FIELD_INVALID, "Entered Username is invalid", "usrName");
		logger.debug("User name is matched " + user.getUsrName());

		if (user.getUsrStatus().toString().equalsIgnoreCase(Constants.USER_DEACTIVED_STATUS))
		{
			createFailedLogin(user, "InActive User");
			return new NegativeResult(ErrCode.INPUT_FIELD_INVALID, "User Status is not in Active", "usrStatus");
		}
		logger.debug("User status is Active");

		UserPasswordSettings userPasswordSettings = UserPasswordSettingsDba.getI().fetchByDisplayField(Constants.STRONG_PASSWORD);

		checkLastLogin(userPasswordSettings, user);
		checkLastLogins(userPasswordSettings, user);

		if (!user.getUsrPassword().toString().equalsIgnoreCase(loginInput.usrPassword))
		{
			createFailedLogin(user, "Incorrect Password");
			return new NegativeResult(ErrCode.INPUT_FIELD_INVALID, "Incorrect password for the User :" + user.getUsrName(), "usrPassword");
		}
		logger.debug("User password is matched");

		int uslId = createLogin(user);
		logger.debug("Created login entry");

		int ussSessionCode = createSessionCode(user, uslId);
		logger.debug("Created session code");

		List<Toolbar> toolbarList = getToolbar(user);
		logger.debug("Fetched toolbars");

		int passwordStatus = checkPasswordStatus(userPasswordSettings, loginInput, user);
		logger.info(user.getUsrName() + " Logged in Successfully");
		return new LogInResult("Logged In Successfully", passwordStatus, user, ussSessionCode, toolbarList);
	}

	private void checkLastLogin(UserPasswordSettings userPasswordSettings, UserTbl user)
	{
		if (user.getUsrStatus().toString().equalsIgnoreCase(Constants.USER_JUSTACTIVED_STATUS))
			return;
		Integer upsMaxInactiveDays = userPasswordSettings.getUpsMaxInactiveDays();
		UserLogin recentLogin = UserLoginDba.getI().fetchRecentLogin(user);
		if (recentLogin == null)
			return;

		DateTime timeOut = new DateTime().minusDays(upsMaxInactiveDays);
		if (recentLogin.getUslLoginDttm().isAfter(timeOut))
			return;

		//if we come here, user hasnt logged in a long time.
		user.setUsrStatus(Constants.USER_DEACTIVED_STATUS);
		UserTblDba.getI().update(user, 1);

		createFailedLogin(user, "Deactivated User");
		String errorMessage = "User " + user.getUsrName() + " deactivated because of more than " + upsMaxInactiveDays + " days of no activity";
		NegativeResult nr = new NegativeResult(ErrCode.INPUT_FIELD_INVALID, errorMessage, "usrPassword");
		throw new NegativeResultException(nr);
	}

	private void checkLastLogins(UserPasswordSettings userPasswordSettings, UserTbl user)
	{
		if (user.getUsrStatus().toString().equalsIgnoreCase(Constants.USER_JUSTACTIVED_STATUS))
			return;
		Integer upsMaxAttempts = userPasswordSettings.getUpsMaxAttempts();
		List<UserLogin> recentLogins = UserLoginDba.getI().fetchRecentLogins(upsMaxAttempts, user);
		if (recentLogins == null || recentLogins.size() < upsMaxAttempts)
			return;
		for (UserLogin recentLogin : recentLogins)
		{
			if (recentLogin.getUslStatus().equalsIgnoreCase(Constants.LOGIN_SUCCESS))
				return;
		}

		//if we come here, recent attempts have all been failed..
		user.setUsrStatus(Constants.USER_DEACTIVED_STATUS);
		UserTblDba.getI().update(user, 1);

		createFailedLogin(user, "Deactivated User");
		String errorMessage = "User " + user.getUsrName() + " deactivated because of consequtive " + upsMaxAttempts + " failed login attempts";
		NegativeResult nr = new NegativeResult(ErrCode.INPUT_FIELD_INVALID, errorMessage, "usrPassword");
		throw new NegativeResultException(nr);
	}

	private int createFailedLogin(UserTbl user, String reason)
	{
		//to logout hanging sessions
		UserLoginDba.getI().updateLogOutTime(user.getUsrId());

		UserLogin userLogin = new UserLoginImpl(user, reason);
		return UserLoginDba.getI().create(userLogin, user.getUsrId());
	}

	private int createLogin(UserTbl user)
	{
		//to logout hanging sessions
		UserLoginDba.getI().updateLogOutTime(user.getUsrId());

		UserLogin userLogin = new UserLoginImpl(user);
		user.setUsrStatus(Constants.USER_ACTIVE_STATUS);
		UserTblDba.getI().update(user, 1);
		return UserLoginDba.getI().create(userLogin, user.getUsrId());
	}

	private int createSessionCode(UserTbl userTbl, int uslId)
	{
		//delete old hanging sessions
		UserSessionDba.getI().deleteByUsrId(userTbl.getUsrId());

		//create new session
		int n = 6;
		Random randGen = new Random();
		int startNum = (int) Math.pow(10, n - 1);
		int range = (int) (Math.pow(10, n) - startNum + 1);
		int ussSessionCode = randGen.nextInt(range) + startNum;

		UserSession userSession = new UserSessionImpl(userTbl, uslId, ussSessionCode);
		UserSessionDba.getI().create(userSession, userTbl.getUsrId());
		return ussSessionCode;
	}

	private List<Toolbar> getToolbar(UserTbl userTbl)
	{
		List<Toolbar> toolbars = ToolbarDba.getI().fetchAllFirstLevel();
		for (Toolbar toolbar : toolbars)
		{
			fetchSubToolbars(toolbar, userTbl);
			fetchToolbarItems(toolbar, userTbl);
		}
		return toolbars;
	}

	private void fetchToolbarItems(Toolbar toolbar, UserTbl userTbl)
	{
		List<ToolbarItem> toolbarItems = ToolbarItemDba.getI().fetchByTbrId(toolbar.getTbrId());
		((ToolbarImpl) toolbar).setToolbarItems(toolbarItems);
	}

	private void fetchSubToolbars(Toolbar toolbar, UserTbl userTbl)
	{
		List<Toolbar> toolbars = ToolbarDba.getI().fetchByParentTbrId(toolbar);
		((ToolbarImpl) toolbar).setToolbars(toolbars);
		for (Toolbar toolbarInner : toolbars)
		{
			fetchSubToolbars(toolbarInner, userTbl);
			fetchToolbarItems(toolbarInner, userTbl);
		}
	}

	private int checkPasswordStatus(UserPasswordSettings userPasswordSettings, LogInInput loginInput, UserTbl user)
	{
		if (loginInput.usrPassword.equalsIgnoreCase(Constants.USER_DEFAULT_PASSWORD))
			return Constants.PASSWORD_STATUS_EXPIRED;

		UserPassword recentPassword = UserPasswordDba.getI().fetchRecentPassword(user);
		if (recentPassword == null)
			return Constants.PASSWORD_STATUS_EXPIRED;

		DateTime expiryDttm = new DateTime().minusDays(userPasswordSettings.getUpsResetInterval());
		if (recentPassword.getUspChangedDttm().isBefore(expiryDttm))
			return Constants.PASSWORD_STATUS_EXPIRED;

		DateTime warnDttm = new DateTime().minusDays(userPasswordSettings.getUpsResetWarningInterval());
		if (recentPassword.getUspChangedDttm().isBefore(warnDttm))
			return Constants.PASSWORD_STATUS_NEAREXPIRY;

		return 0;
	}
}
