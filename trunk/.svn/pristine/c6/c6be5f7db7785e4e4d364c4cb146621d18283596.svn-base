package se.signa.signature.dio;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import se.signa.signature.gen.dbo.Job;

@XmlRootElement
public class JobSearchResult extends PositiveResult
{
	public List<Job> jobList;

	public JobSearchResult()
	{
		super();
	}

	public JobSearchResult(String notification, List<Job> jobList)
	{
		super(notification);
		this.jobList = jobList;
	}
}
