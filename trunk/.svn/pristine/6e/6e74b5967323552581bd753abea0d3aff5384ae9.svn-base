package se.signa.signature.dio;

import javax.xml.bind.annotation.XmlRootElement;

import se.signa.signature.gen.dbo.UserPasswordSettings;

@XmlRootElement
public class PasswordSettingsFetchResult extends PositiveResult
{
	public UserPasswordSettings userPasswordSettings;

	public PasswordSettingsFetchResult()
	{
		super();
	}

	public PasswordSettingsFetchResult(String notification, UserPasswordSettings userPasswordSettings)
	{
		super(notification);
		this.userPasswordSettings = userPasswordSettings;
	}
}