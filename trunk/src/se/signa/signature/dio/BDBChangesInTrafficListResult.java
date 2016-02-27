package se.signa.signature.dio;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import se.signa.signature.rest.BDBChangeInTrafficListApi.ChangesInTrafficData;

@XmlRootElement
public class BDBChangesInTrafficListResult extends PositiveResult
{
	public List<ChangesInTrafficData> changesInTrafficDataList;

	public BDBChangesInTrafficListResult()
	{
	}

	public BDBChangesInTrafficListResult(String notification, List<ChangesInTrafficData> changesInTrafficDataList)
	{
		super(notification);
		this.changesInTrafficDataList = changesInTrafficDataList;
	}
}
