package se.signa.signature.dio;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import se.signa.signature.rest.BDBMarginApi.Margin;

@XmlRootElement
public class BDBMarginResult extends PositiveResult
{

	public List<String> headers;
	public List<Margin> marginList;

	public BDBMarginResult()
	{
	}

	public BDBMarginResult(String notification, List<String> headers, List<Margin> marginList)
	{
		super(notification);
		this.headers = headers;
		this.marginList = marginList;
	}
}
