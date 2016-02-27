package se.signa.signature.dio;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import se.signa.signature.rest.CDBDealsCountApi.ActiveDeals;
import se.signa.signature.rest.CDBDealsCountApi.NewDeals;

@XmlRootElement
public class CDBDealsCountResult extends PositiveResult
{

	public List<NewDeals> newDealsList;
	public List<ActiveDeals> activeDealsList;

	public CDBDealsCountResult()
	{
	}

	public CDBDealsCountResult(String notification, List<NewDeals> newDealsList, List<ActiveDeals> activeDealsList)
	{
		super(notification);
		this.newDealsList = newDealsList;
		this.activeDealsList = activeDealsList;
	}
}
