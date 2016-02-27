package se.signa.signature.dio;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import se.signa.signature.rest.DDBVolumeDetailsApi.VolumeDetails;

@XmlRootElement
public class DDBVolumeDetailsResult extends PositiveResult
{
	public String carrier;
	public int dealId;
	public String currency;

	public String startDate;
	public String endDate;
	public String gracePeriod;
	public List<String> headers;
	public List<VolumeDetails> volumeDetailsList;

	public DDBVolumeDetailsResult()
	{
	}

	public DDBVolumeDetailsResult(String notification, String carrier, int dealId, String currency, String startDate, String endDate, String gracePeriod, List<String> headers, List<VolumeDetails> volumeDetailsList)
	{
		super(notification);
		this.headers = headers;
		this.volumeDetailsList = volumeDetailsList;
		this.carrier = carrier;
		this.dealId = dealId;
		this.currency = currency;
		this.startDate = startDate;
		this.endDate = endDate;
		this.gracePeriod = gracePeriod;
	}
}
