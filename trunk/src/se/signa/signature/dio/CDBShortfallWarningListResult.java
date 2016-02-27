package se.signa.signature.dio;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import se.signa.signature.rest.CDBShortfallWarningListApi.ShortfallWarningData;

@XmlRootElement
public class CDBShortfallWarningListResult extends PositiveResult
{
	public List<ShortfallWarningData> shortfallWarningDataList;

	public CDBShortfallWarningListResult()
	{
	}

	public CDBShortfallWarningListResult(String notification, List<ShortfallWarningData> shortfallWarningDataList)
	{
		super(notification);
		this.shortfallWarningDataList = shortfallWarningDataList;
	}
}
