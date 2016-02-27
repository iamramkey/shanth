package se.signa.signature.dio;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import se.signa.signature.rest.BDBDealExpiringListApi.DealExpiringData;

@XmlRootElement
public class BDBDealExpiringListResult extends PositiveResult
{
	public List<DealExpiringData> dealExpiringDataList;

	public BDBDealExpiringListResult()
	{
	}

	public BDBDealExpiringListResult(String notification, List<DealExpiringData> dealExpiringDataList)
	{
		super(notification);
		this.dealExpiringDataList = dealExpiringDataList;
	}
}
