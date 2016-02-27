package se.signa.signature.dio;

import javax.xml.bind.annotation.XmlRootElement;

import se.signa.signature.common.Constants;
import se.signa.signature.common.NegativeResultException;
import se.signa.signature.gen.dba.UserPasswordDba;

@XmlRootElement
public class UserChangePasswordInput extends StigWebInput
{
	public String usrPassword;
	public String usrNewPassword;

	public void validate()
	{
		super.validate();
		mandate(usrPassword, "User Password", "usrPassword");
		mandate(usrNewPassword, "User New Password", "usrNewPassword");
	}

	public void checkRules()
	{
		super.checkRules();
		//Default password check 
		if (usrNewPassword.equalsIgnoreCase(Constants.USER_DEFAULT_PASSWORD))
		{
			String notification = "This password has been used before, It cannot be used now";
			NegativeResult nr = new NegativeResult(Constants.ErrCode.INPUT_FIELD_INVALID, notification, "usrNewPassword");
			throw new NegativeResultException(nr);
		}

		//history passwords
		int count = UserPasswordDba.getI().count(usrId, usrNewPassword);
		if (count > 0)
		{
			String notification = "This password has been used before, It cannot be used now";
			NegativeResult nr = new NegativeResult(Constants.ErrCode.INPUT_FIELD_INVALID, notification, "usrNewPassword");
			throw new NegativeResultException(nr);
		}

	}

	/*@Override
	protected String getEntity()
	{
		return "user_tbl";
	}
	
	@Override
	protected String getAction()
	{
		return "change_password";
	}*/

}
