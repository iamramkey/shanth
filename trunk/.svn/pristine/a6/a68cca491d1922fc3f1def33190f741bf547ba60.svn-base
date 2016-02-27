package se.signa.signature.dio;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ViewAuditResult extends PositiveResult
{
	public List<String> auditStringList;
	public List<String> headers;
	public List<List<AuditDataCell>> data;

	public ViewAuditResult()
	{
		super();
	}

	public ViewAuditResult(String notification, List<String> auditStringList, List<String> headers, List<List<AuditDataCell>> data)
	{
		super(notification);
		this.auditStringList = auditStringList;
		this.headers = headers;
		this.data = data;
	}
}