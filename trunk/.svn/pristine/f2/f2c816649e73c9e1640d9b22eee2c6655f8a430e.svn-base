package se.signa.signature.dio;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

import se.signa.signature.rest.JobMonitorApi.JobMonitorModel;

@XmlRootElement
public class JobMonitorResult extends PositiveResult
{
	public Collection<JobMonitorModel> jobList;

	public JobMonitorResult()
	{
		super();
	}

	public JobMonitorResult(String notification, Collection<JobMonitorModel> jobList)
	{
		super(notification);
		this.jobList = jobList;
	}
}
