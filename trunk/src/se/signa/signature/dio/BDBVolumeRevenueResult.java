package se.signa.signature.dio;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import se.signa.signature.rest.BDBVolumeRevenueApi.DailyRevenue;
import se.signa.signature.rest.BDBVolumeRevenueApi.DailyVolumes;

@XmlRootElement
public class BDBVolumeRevenueResult extends PositiveResult
{

	public List<String> headers;
	public List<DailyVolumes> dailyVolumesList;
	public List<DailyRevenue> dailyRevenueList;

	public BDBVolumeRevenueResult()
	{
	}

	public BDBVolumeRevenueResult(String notification, List<String> headers, List<DailyVolumes> dailyVolumesList, List<DailyRevenue> dailyRevenueList)
	{
		super(notification);
		this.headers = headers;
		this.dailyVolumesList = dailyVolumesList;
		this.dailyRevenueList = dailyRevenueList;
	}
}
