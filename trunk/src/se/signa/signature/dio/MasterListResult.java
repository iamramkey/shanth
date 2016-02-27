package se.signa.signature.dio;

import java.util.List;

public class MasterListResult extends PositiveResult
{
	public List<String> list;

	public MasterListResult()
	{
		super();
	}

	public MasterListResult(String notification, List<String> list)
	{
		super(notification);
		this.list = list;
	}
}
