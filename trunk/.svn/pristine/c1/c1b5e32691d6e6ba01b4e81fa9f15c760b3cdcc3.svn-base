package se.signa.signature.dio;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import se.signa.signature.gen.dbo.AuditFile;

@XmlRootElement
public class AuditFileSearchResult extends PositiveResult
{
	public List<AuditFile> auditFileList;

	public AuditFileSearchResult()
	{
		super();
	}

	public AuditFileSearchResult(String notification, List<AuditFile> auditFileList)
	{
		super(notification);
		this.auditFileList = auditFileList;
	}
}
