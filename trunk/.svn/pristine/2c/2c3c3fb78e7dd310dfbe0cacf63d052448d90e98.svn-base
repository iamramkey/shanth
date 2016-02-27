package se.signa.signature.dio;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlertSearchInput extends StigWebInput
{
	public String nodeName;
	public String alertType;
	public String alertCreatedDttmFrom;
	public String alertCreatedDttmTo;

	public void validate()
	{
		super.validate();
		mandate(alertCreatedDttmFrom, "From Dttm", "alertCreatedDttmFrom");
		mandate(alertCreatedDttmTo, "To Dttm", "alertCreatedDttmTo");
	}

	/*@Override
	protected String getEntity()
	{
		return "alert";
	}
	
	@Override
	protected String getAction()
	{
		return "search";
	}*/
}
