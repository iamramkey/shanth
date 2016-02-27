package se.signa.signature.dio;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JobLogResult extends PositiveResult
{
	public String log;
	public String jobStatus;
	public String jobLogHeader;

	public JobLogResult()
	{
		super();
	}

	public JobLogResult(String notification, String log, String jobStatus, String jobLogHeader)
	{
		super(notification);
		this.log = log;
		this.jobStatus = jobStatus;
		this.jobLogHeader = jobLogHeader;
	}
}