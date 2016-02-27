package se.signa.signature.dio;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AuditFileSearchInput extends StigWebInput
{
	public String filName;
	public String parentFilName;
	public String aufFilType;
	public String aufDttmFrom;
	public String aufDttmTo;
	public String rowCount;

	public void validate()
	{
		super.validate();
		mandate(rowCount, "Count", "rowCount");
		mandate(aufDttmFrom, "Dttm", "aufDttmFrom");
		mandate(aufDttmTo, "Dttm", "aufDttmTo");
	}

	/*@Override
	protected String getEntity()
	{
		return "audit_file";
	}
	
	@Override
	protected String getAction()
	{
		return "search";
	}*/

}
