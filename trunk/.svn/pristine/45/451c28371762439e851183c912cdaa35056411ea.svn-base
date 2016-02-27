package se.signa.signature.dio;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import se.signa.signature.rest.BDBDealsCountApi.ActiveDeals;
import se.signa.signature.rest.BDBDealsCountApi.NewDeals;

@XmlRootElement
public class BDBDealsCountResult extends PositiveResult
{

	public List<NewDeals> newDealsList;
	public List<ActiveDeals> activeDealsList;

	public BDBDealsCountResult()
	{
	}

	public BDBDealsCountResult(String notification, List<NewDeals> newDealsList, List<ActiveDeals> activeDealsList)
	{
		super(notification);
		this.newDealsList = newDealsList;
		this.activeDealsList = activeDealsList;
	}
}
