package se.signa.signature.dio;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import se.signa.signature.rest.CDBFulfilmentProjectionListApi.ProjectionData;

@XmlRootElement
public class CDBFulfilmentProjectionListResult extends PositiveResult
{
	public List<ProjectionData> projectionDataList;

	public CDBFulfilmentProjectionListResult()
	{
	}

	public CDBFulfilmentProjectionListResult(String notification, List<ProjectionData> projectionDataList)
	{
		super(notification);
		this.projectionDataList = projectionDataList;
	}
}
