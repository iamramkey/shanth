package se.signa.signature.dio;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DDBMarginTrendChartResult extends PositiveResult
{
	public List<Object[]> dataList;

	public DDBMarginTrendChartResult()
	{
	}

	public DDBMarginTrendChartResult(String notification, List<Object[]> dataList)
	{
		super(notification);
		this.dataList = dataList;
	}
}
