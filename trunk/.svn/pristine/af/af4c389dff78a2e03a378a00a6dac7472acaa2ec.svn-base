package se.signa.signature.dio;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import se.signa.signature.rest.CDBAgreementsForCountryApi.VolumeDetails;

@XmlRootElement
public class CDBAgreementsForCountryResult extends PositiveResult
{
	public List<VolumeDetails> volumeDetailsList;

	public CDBAgreementsForCountryResult()
	{
	}

	public CDBAgreementsForCountryResult(String notification, List<VolumeDetails> volumeDetailsList)
	{
		super(notification);
		this.volumeDetailsList = volumeDetailsList;
	}
}
