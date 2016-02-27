package se.signa.signature.dio;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BusinessDashboardInput extends StigWebInput
{
	public String currencyCode;

	public void validate()
	{
		super.validate();
	}

	/*@Override
	protected String getEntity()
	{
		return "chart";
	}
	
	@Override
	protected String getAction()
	{
		return "chart";
	}*/

}
