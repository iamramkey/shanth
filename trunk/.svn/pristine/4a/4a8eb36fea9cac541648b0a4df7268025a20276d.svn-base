package se.signa.signature.dio;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import se.signa.signature.rest.DDBFulfilmentChartApi.Series;

@XmlRootElement
public class DDBFulfilmentChartResult extends PositiveResult
{
	public List<String> inCategories;
	public List<String> outCategories;
	public List<Series> inSeries;
	public List<Series> outSeries;

	public DDBFulfilmentChartResult()
	{
	}

	public DDBFulfilmentChartResult(String notification, List<String> inCategories, List<String> outCategories, List<Series> inSeries, List<Series> outSeries)
	{
		super(notification);
		this.inCategories = inCategories;
		this.outCategories = outCategories;
		this.inSeries = inSeries;
		this.outSeries = outSeries;
	}
}
