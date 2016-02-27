package se.signa.signature.dio;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import se.signa.signature.rest.DDBDealDetailsApi.DealDetails;

@XmlRootElement
public class DDBDealDetailsResult extends PositiveResult
{
	public String carrier;
	public int dealId;
	public String currency;

	public String startDate;
	public String endDate;
	public String gracePeriod;

	public List<DealDetails> dealDetailsList;

	public DDBDealDetailsResult()
	{
	}

	public DDBDealDetailsResult(String notification, String carrier, int dealId, String currency, String startDate, String endDate, String gracePeriod, List<DealDetails> dealDetailsList)
	{
		super(notification);
		this.carrier = carrier;
		this.dealId = dealId;
		this.currency = currency;
		this.startDate = startDate;
		this.endDate = endDate;
		this.gracePeriod = gracePeriod;
		this.dealDetailsList = dealDetailsList;
	}
}
