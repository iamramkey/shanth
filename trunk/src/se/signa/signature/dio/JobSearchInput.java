package se.signa.signature.dio;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JobSearchInput extends StigWebInput
{
	public String jobId;
	public String jobStartDttmFrom;
	public String jobStartDttmTo;
	public String rowCount;
	public String nodeName;
	public String jbtName;
	public String jobEndDttmFrom;
	public String jobEndDttmTo;
	public String jobStatus;
	public String jobCreatedDttmFrom;
	public String jobCreatedDttmTo;
	public String jobExtra1;
	public String jobExtra2;
	public String jobExtra3;

	public void validate()
	{
		//		super.validate();
		mandate(jobStartDttmFrom, "From Dttm", "jobStartDttmFrom");
		mandate(jobStartDttmTo, "To Dttm", "jobStartDttmTo");
		mandate(rowCount, "Count", "rowCount");
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
