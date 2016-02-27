package se.signa.signature.dio;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import se.signa.signature.rest.CDBTopUncommittedListApi.TopUncommittedData;

@XmlRootElement
public class CDBTopUncommittedListResult extends PositiveResult
{
	public List<TopUncommittedData> topUncommittedDataList;

	public CDBTopUncommittedListResult()
	{
	}

	public CDBTopUncommittedListResult(String notification, List<TopUncommittedData> topUncommittedDataList)
	{
		super(notification);
		this.topUncommittedDataList = topUncommittedDataList;
	}
}
