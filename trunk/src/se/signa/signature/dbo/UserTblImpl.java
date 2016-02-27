/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;
import java.util.Map;

import se.signa.signature.common.Constants;
import se.signa.signature.dio.MasterSaveInput;
import se.signa.signature.gen.dbo.UserTbl;

public class UserTblImpl extends UserTbl
{
	private String usrFirstName;
	private String usrLastName;

	public UserTblImpl()
	{
	}

	public UserTblImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = super.populateTo();
		String[] displayName = getUsrDisplayName().split(",");
		data.put("usrFirstName", getString(displayName[0]));
		data.put("usrLastName", getString(displayName[1]));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		String password = getUsrPassword();
		if (password == null)
			password = Constants.USER_DEFAULT_PASSWORD;
		String status = getUsrStatus();
		if (status == null)
			status = Constants.USER_ACTIVE_STATUS;
		super.populateFrom(input);
		setUsrFirstName(input.getString("usrFirstName"));
		setUsrLastName(input.getString("usrLastName"));
		setUsrDisplayName(input.getString("usrFirstName") + "," + input.getString("usrLastName"));
		setUsrPassword(password);
		setUsrStatus(status);
	}

	public String getUsrFirstName()
	{
		return usrFirstName;
	}

	public void setUsrFirstName(String usrFirstName)
	{
		this.usrFirstName = usrFirstName;
	}

	public String getUsrLastName()
	{
		return usrLastName;
	}

	public void setUsrLastName(String usrLastName)
	{
		this.usrLastName = usrLastName;
	}

}