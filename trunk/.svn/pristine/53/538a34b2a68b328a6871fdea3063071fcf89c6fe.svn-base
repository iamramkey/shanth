package se.signa.signature.dio;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MasterSearchInput extends StigWebInput
{
	public String name;
	public boolean fullLoad;

	public void validate()
	{
		super.validate();
		mandate(name, "Name", "name");
	}

}
