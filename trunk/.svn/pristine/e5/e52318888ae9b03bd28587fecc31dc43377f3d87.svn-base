package se.signa.signature.dio;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ServerSettingsFetchInput extends StigWebInput
{
	public String sesCode;

	public void validate()
	{
		super.validate();
		mandate(sesCode, "Settings Code", "sesCode");
	}

	/*@Override
	protected String getEntity()
	{
		return "server_settings";
	}
	
	@Override
	protected String getAction()
	{
		return "view";
	}*/
}
