package se.signa.signature.dio;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import se.signa.signature.rest.DDBFulfilmentProjectionListApi.ProjectionData;

@XmlRootElement
public class DDBFulfilmentProjectionListResult extends PositiveResult
{
	public List<ProjectionData> projectionDataList;

	public DDBFulfilmentProjectionListResult()
	{
	}

	public DDBFulfilmentProjectionListResult(String notification, List<ProjectionData> projectionDataList)
	{
		super(notification);
		this.projectionDataList = projectionDataList;
	}
}
