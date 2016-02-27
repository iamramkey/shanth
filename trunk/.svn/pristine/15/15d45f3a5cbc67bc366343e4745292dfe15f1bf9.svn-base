package se.signa.signature.dio;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DealListInput extends StigWebInput
{
	public String accName;

	public void validate()
	{
//		super.validate();
		mandate(accName, "Account Name", "accName");
	}

	/*@Override
	protected String getEntity()
	{
		return "deal";
	}

	@Override
	protected String getAction()
	{
		return "list";
	}*/
}
