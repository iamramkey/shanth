package se.signa.signature.dio;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BDBDealNonDealTrafficChartResult extends PositiveResult
{
	public List<Object[]> dealDataList;
	public List<Object[]> nonDealDataList;

	public BDBDealNonDealTrafficChartResult()
	{
	}

	public BDBDealNonDealTrafficChartResult(String notification, List<Object[]> dealDataList, List<Object[]> nonDealDataList)
	{
		super(notification);
		this.dealDataList = dealDataList;
		this.nonDealDataList = nonDealDataList;
	}
}
