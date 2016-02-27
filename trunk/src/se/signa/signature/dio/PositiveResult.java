package se.signa.signature.dio;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PositiveResult extends StigWebResult
{
	public String notification;

	public PositiveResult()
	{
		super();
	} //JAXB needs this

	public PositiveResult(String notification)
	{
		super();
		this.notification = notification;
	}

}
