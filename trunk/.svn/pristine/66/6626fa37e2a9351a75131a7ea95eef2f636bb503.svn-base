package se.signa.signature.dio;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JobCapabilityInput extends StigWebInput
{
	public int id;

	public void validate()
	{
		super.validate();
		mandate(id, "Jbc Id", "jbcId");
	}

	public void checkRules()
	{
	}

	/*@Override
	protected String getEntity()
	{
		return "job_capability";
	}

	@Override
	protected String getAction()
	{
		return "update";
	}*/
}
