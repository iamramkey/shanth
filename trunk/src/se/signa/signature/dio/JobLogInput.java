package se.signa.signature.dio;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JobLogInput extends StigWebInput
{
	public Integer jobId;

	public void validate()
	{
		super.validate();
		mandate(jobId, "JobId", "jobId");
	}

	/*@Override
	protected String getEntity()
	{
		return "job";
	}

	@Override
	protected String getAction()
	{
		return "view";
	}*/
}
