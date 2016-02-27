package se.signa.signature.dio;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BDBMarginTrendChartResult extends PositiveResult
{
	public List<Object[]> dataList;

	public BDBMarginTrendChartResult()
	{
	}

	public BDBMarginTrendChartResult(String notification, List<Object[]> dataList)
	{
		super(notification);
		this.dataList = dataList;
	}
}
