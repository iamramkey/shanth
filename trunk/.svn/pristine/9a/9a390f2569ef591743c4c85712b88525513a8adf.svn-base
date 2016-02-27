package se.signa.signature.dio;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import se.signa.signature.gen.dbo.Alert;

@XmlRootElement
public class AlertSearchResult extends PositiveResult
{
	public List<Alert> alertList;

	public AlertSearchResult()
	{
		super();
	}

	public AlertSearchResult(String notification, List<Alert> alertList)
	{
		super(notification);
		this.alertList = alertList;
	}
}