package se.signa.signature.dio;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import se.signa.signature.rest.BDBTopDestinationsListApi.TopDestinationsData;

@XmlRootElement
public class BDBTopDestinationsListResult extends PositiveResult
{
	public List<TopDestinationsData> topDestinationsDataList;

	public BDBTopDestinationsListResult()
	{
	}

	public BDBTopDestinationsListResult(String notification, List<TopDestinationsData> topDestinationsDataList)
	{
		super(notification);
		this.topDestinationsDataList = topDestinationsDataList;
	}
}
