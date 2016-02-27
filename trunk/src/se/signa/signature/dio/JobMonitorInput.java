package se.signa.signature.dio;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JobMonitorInput extends StigWebInput
{
	public String nodName;
	public String jobStartDttmFrom;
	public String jobStartDttmTo;

	public void validate()
	{
		super.validate();
		mandate(jobStartDttmFrom, "From Dttm", "jobStartDttmFrom");
		mandate(jobStartDttmTo, "To Dttm", "jobStartDttmTo");
		mandate(nodName, "Node Name", "nodName");
	}

	/*@Override
	protected String getEntity()
	{
		return "job";
	}
	
	@Override
	protected String getAction()
	{
		return "search";
	}*/
}
