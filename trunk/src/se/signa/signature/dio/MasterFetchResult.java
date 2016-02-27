package se.signa.signature.dio;

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MasterFetchResult extends PositiveResult
{

	public String name;
	public int id;
	public Map<String, Object> data;

	public MasterFetchResult()
	{
		super();
	} //JAXB needs this

	public MasterFetchResult(String notification, String name, int id, Map<String, Object> data)
	{
		super(notification);
		this.name = name;
		this.id = id;
		this.data = data;
	}
}
