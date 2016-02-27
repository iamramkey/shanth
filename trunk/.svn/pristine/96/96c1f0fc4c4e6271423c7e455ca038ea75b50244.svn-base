package se.signa.signature.dio;

import javax.xml.bind.annotation.XmlRootElement;

import se.signa.signature.gen.dbo.ServerSettings;

@XmlRootElement
public class ServerSettingsFetchResult extends PositiveResult
{
	public ServerSettings serverSettings;

	public ServerSettingsFetchResult()
	{
		super();
	}

	public ServerSettingsFetchResult(String notification, ServerSettings serverSettings)
	{
		super(notification);
		this.serverSettings = serverSettings;
	}
}