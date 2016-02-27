package se.signa.signature.dio;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import se.signa.signature.gen.dbo.Toolbar;
import se.signa.signature.gen.dbo.UserTbl;

@XmlRootElement
public class LogInResult extends PositiveResult
{

	public int passwordStatus;
	public UserTbl userTbl;
	public int ussSessionCode;
	public List<Toolbar> toolbarList;

	public LogInResult(String notification, int passwordStatus, UserTbl userTbl, int ussSessionCode, List<Toolbar> toolbarList)
	{
		super(notification);
		this.passwordStatus = passwordStatus;
		this.userTbl = userTbl;
		this.ussSessionCode = ussSessionCode;
		this.toolbarList = toolbarList;
	}
}
