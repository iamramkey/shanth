package se.signa.signature.dio;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import se.signa.signature.rest.CDBCommittedTrafficChartApi.Series;

@XmlRootElement
public class CDBCommittedTrafficChartResult extends PositiveResult
{
	public List<String> categories;
	public List<Series> inVolumeSeries;
	public List<Series> outVolumeSeries;

	public CDBCommittedTrafficChartResult()
	{
	}

	public CDBCommittedTrafficChartResult(String notification, List<String> categories, List<Series> inVolumeSeries, List<Series> outVolumeSeries)
	{
		super(notification);
		this.categories = categories;
		this.inVolumeSeries = inVolumeSeries;
		this.outVolumeSeries = outVolumeSeries;
	}
}
