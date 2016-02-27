package se.signa.signature.dio;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import se.signa.signature.rest.CDBUncommittedTrafficChartApi.ObjectArray;

@XmlRootElement
public class CDBUncommittedTrafficChartResult extends PositiveResult
{
	public List<ObjectArray> inUncommittedTrafficChartList;
	public List<ObjectArray> outUncommittedTrafficChartList;

	public CDBUncommittedTrafficChartResult()
	{
	}

	public CDBUncommittedTrafficChartResult(String notification, List<ObjectArray> inUncommittedTrafficChartList)
	{
		super(notification);
		this.inUncommittedTrafficChartList = inUncommittedTrafficChartList;
		this.outUncommittedTrafficChartList = inUncommittedTrafficChartList;
	}
}
