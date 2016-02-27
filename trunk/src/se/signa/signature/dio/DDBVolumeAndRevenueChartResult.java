package se.signa.signature.dio;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import se.signa.signature.rest.DDBVolumeAndRevenueChartApi.Series;

@XmlRootElement
public class DDBVolumeAndRevenueChartResult extends PositiveResult
{
	public List<String> categories;
	public List<Series> volumeSeries;
	public List<Series> revenueSeries;

	public DDBVolumeAndRevenueChartResult()
	{
	}

	public DDBVolumeAndRevenueChartResult(String notification, List<String> categories, List<Series> volumeSeries, List<Series> revenueSeries)
	{
		super(notification);
		this.categories = categories;
		this.volumeSeries = volumeSeries;
		this.revenueSeries = revenueSeries;
	}
}
