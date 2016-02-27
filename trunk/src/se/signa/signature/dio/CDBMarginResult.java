package se.signa.signature.dio;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import se.signa.signature.rest.CDBMarginApi.Margin;

@XmlRootElement
public class CDBMarginResult extends PositiveResult
{

	public List<String> headers;
	public List<Margin> marginList;

	public CDBMarginResult()
	{
	}

	public CDBMarginResult(String notification, List<String> headers, List<Margin> marginList)
	{
		super(notification);
		this.headers = headers;
		this.marginList = marginList;
	}
}
