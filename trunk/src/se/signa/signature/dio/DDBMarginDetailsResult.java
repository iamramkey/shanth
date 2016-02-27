package se.signa.signature.dio;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import se.signa.signature.rest.DDBMarginDetailsApi.MarginDetails;

@XmlRootElement
public class DDBMarginDetailsResult extends PositiveResult
{

	public String carrier;
	public int dealId;
	public String currency;

	public String startDate;
	public String endDate;
	public String gracePeriod;

	public List<String> headers;
	public List<MarginDetails> marginDetailsList;

	public DDBMarginDetailsResult()
	{
	}

	public DDBMarginDetailsResult(String notification, String carrier, int dealId, String currency, String startDate, String endDate, String gracePeriod, List<String> headers, List<MarginDetails> marginDetailsList)
	{
		super(notification);
		this.carrier = carrier;
		this.dealId = dealId;
		this.currency = currency;
		this.startDate = startDate;
		this.endDate = endDate;
		this.gracePeriod = gracePeriod;
		this.headers = headers;
		this.marginDetailsList = marginDetailsList;
	}
}
