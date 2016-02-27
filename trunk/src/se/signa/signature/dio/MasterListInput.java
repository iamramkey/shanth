package se.signa.signature.dio;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MasterListInput extends StigWebInput
{
	public String prefix;

	public void validate()
	{
		super.validate();
		mandate(prefix, "Prefix", "prefix");
	}

	public void checkRules()
	{
		super.checkRules();
	}
}
