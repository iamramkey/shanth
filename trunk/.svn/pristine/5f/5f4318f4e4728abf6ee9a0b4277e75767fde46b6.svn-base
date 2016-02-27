package se.signa.signature.dio;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MasterFetchInput extends StigWebInput
{
	public int id;
	public String name;

	public void validate()
	{
		super.validate();
		mandate(id, "Id", "id");
		mandate(name, "Name", "name");
	}

	public void checkRules()
	{
		super.checkRules();
	}

}
