package se.signa.signature.dio;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import se.signa.signature.rest.BDBRecentActivityListApi.RecentActivityData;

@XmlRootElement
public class BDBRecentActivityListResult extends PositiveResult
{
	public List<String> bandGroupList;
	public List<RecentActivityData> recentActivityList;

	public BDBRecentActivityListResult()
	{
	}

	public BDBRecentActivityListResult(String notification, List<String> bandGroupList, List<RecentActivityData> recentActivityList)
	{
		super(notification);
		this.bandGroupList = bandGroupList;
		this.recentActivityList = recentActivityList;
	}
}
