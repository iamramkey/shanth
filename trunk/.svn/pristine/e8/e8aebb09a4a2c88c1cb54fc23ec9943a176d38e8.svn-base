package se.signa.signature.dio;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import se.signa.signature.rest.BDBShortfallWarningListApi.ShortfallWarningData;

@XmlRootElement
public class BDBShortfallWarningListResult extends PositiveResult
{
	public List<ShortfallWarningData> shortfallWarningDataList;

	public BDBShortfallWarningListResult()
	{
	}

	public BDBShortfallWarningListResult(String notification, List<ShortfallWarningData> shortfallWarningDataList)
	{
		super(notification);
		this.shortfallWarningDataList = shortfallWarningDataList;
	}
}
