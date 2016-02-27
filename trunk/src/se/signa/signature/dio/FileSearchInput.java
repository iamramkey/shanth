package se.signa.signature.dio;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FileSearchInput extends StigWebInput
{
	public String filName;
	public String filDttmFrom;
	public String filDttmTo;
	public String rowCount;

	public void validate()
	{
		super.validate();
		mandate(filDttmFrom, "From Dttm", "filDttmFrom");
		mandate(filDttmTo, "To Dttm", "filDttmTo");
		mandate(rowCount, "Count", "rowCount");
	}

	/*@Override
	protected String getEntity()
	{
		return "filetbl";
	}
	
	@Override
	protected String getAction()
	{
		return "search";
	}*/

}
