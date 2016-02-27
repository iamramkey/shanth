package se.signa.signature.dio;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import se.signa.signature.gen.dbo.Filetbl;

@XmlRootElement
public class FileSearchResult extends PositiveResult
{
	public List<Filetbl> fileTblList;

	public FileSearchResult()
	{
		super();
	}

	public FileSearchResult(String notification, List<Filetbl> fileTblList)
	{
		super(notification);
		this.fileTblList = fileTblList;
	}
}
