/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.gen.dbo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.joda.time.DateTime;

import se.signa.signature.common.Constants;
import se.signa.signature.common.CustomDateSerializer;
import se.signa.signature.common.SignatureDbo;
import se.signa.signature.common.SignatureException;
import se.signa.signature.dio.MasterSaveInput;
import se.signa.signature.gen.dba.RoleDba;
import se.signa.signature.gen.dba.UserTblDba;
import se.signa.signature.helpers.DisplayFieldHelper;

public abstract class UserRolePermission extends SignatureDbo
{
	private Integer urpId;
	private Integer rolId;
	private String urpUrl;
	private String urpAction;
	private String urpActionType;
	private String urpCode;
	private String urpBk;
	private Integer urpCreatedUsrId;
	private Integer urpModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime urpCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime urpModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			urpId = getInteger(rs.getInt("urp_id"));
			rolId = getInteger(rs.getInt("rol_id"));
			urpUrl = rs.getString("urp_url");
			urpAction = rs.getString("urp_action");
			urpActionType = rs.getString("urp_action_type");
			urpCode = rs.getString("urp_code");
			urpBk = rs.getString("urp_bk");
			urpCreatedUsrId = getInteger(rs.getInt("urp_created_usr_id"));
			urpModifiedUsrId = getInteger(rs.getInt("urp_modified_usr_id"));
			urpCreatedDttm = getDateTime(rs.getTimestamp("urp_created_dttm"));
			urpModifiedDttm = getDateTime(rs.getTimestamp("urp_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return urpId;
	}

	public void setPk(int pk)
	{
		urpId = pk;
	}

	public String getBk()
	{
		return urpBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(urpId.toString());
		data.add(rolId.toString());
		data.add(urpUrl.toString());
		data.add(urpAction.toString());
		data.add(urpActionType.toString());
		data.add(urpCode.toString());
		data.add(getUrpCreatedUsr());
		data.add(Constants.dttf.print(urpCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("rol", getRol());
		data.put("urpUrl", getString(urpUrl));
		data.put("urpAction", getString(urpAction));
		data.put("urpActionType", getString(urpActionType));
		data.put("urpCode", getString(urpCode));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setRolId(DisplayFieldHelper.getI().getPk(RoleDba.class, input.getString("rol")));
		setUrpUrl(input.getString("urpUrl"));
		setUrpAction(input.getString("urpAction"));
		setUrpActionType(input.getString("urpActionType"));
		setUrpCode(input.getString("urpCode"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(urpBk.toString());
		auditData.add(getUrpCreatedUsr());
		auditData.add(urpModifiedUsrId == null ? Constants.NULLSTRING : getUrpModifiedUsr());
		auditData.add(Constants.dttf.print(urpCreatedDttm));
		auditData.add(urpModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(urpModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return urpCode;
	}

	public String getRol()
	{
		return DisplayFieldHelper.getI().getDisplayField(RoleDba.class, rolId);
	}

	public String getUrpCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, urpCreatedUsrId);
	}

	public String getUrpModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, urpModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("rol"))
			return getRol();
		if (propertyName.equals("urpCreatedUsr"))
			return getUrpCreatedUsr();
		if (propertyName.equals("urpModifiedUsr"))
			return getUrpModifiedUsr();
		if (propertyName.equals("urpId"))
			return getUrpId();
		if (propertyName.equals("rolId"))
			return getRolId();
		if (propertyName.equals("urpUrl"))
			return getUrpUrl();
		if (propertyName.equals("urpAction"))
			return getUrpAction();
		if (propertyName.equals("urpActionType"))
			return getUrpActionType();
		if (propertyName.equals("urpCode"))
			return getUrpCode();
		if (propertyName.equals("urpBk"))
			return getUrpBk();
		if (propertyName.equals("urpCreatedUsrId"))
			return getUrpCreatedUsrId();
		if (propertyName.equals("urpModifiedUsrId"))
			return getUrpModifiedUsrId();
		if (propertyName.equals("urpCreatedDttm"))
			return getUrpCreatedDttm();
		if (propertyName.equals("urpModifiedDttm"))
			return getUrpModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getUrpId()
	{
		return urpId;
	}

	public void setUrpId(Integer urpId)
	{
		this.urpId = urpId;
	}

	public Integer getRolId()
	{
		return rolId;
	}

	public void setRolId(Integer rolId)
	{
		this.rolId = rolId;
	}

	public String getUrpUrl()
	{
		return urpUrl;
	}

	public void setUrpUrl(String urpUrl)
	{
		this.urpUrl = urpUrl;
	}

	public String getUrpAction()
	{
		return urpAction;
	}

	public void setUrpAction(String urpAction)
	{
		this.urpAction = urpAction;
	}

	public String getUrpActionType()
	{
		return urpActionType;
	}

	public void setUrpActionType(String urpActionType)
	{
		this.urpActionType = urpActionType;
	}

	public String getUrpCode()
	{
		return urpCode;
	}

	public void setUrpCode(String urpCode)
	{
		this.urpCode = urpCode;
	}

	public String getUrpBk()
	{
		return urpBk;
	}

	public void setUrpBk(String urpBk)
	{
		this.urpBk = urpBk;
	}

	public Integer getUrpCreatedUsrId()
	{
		return urpCreatedUsrId;
	}

	public void setUrpCreatedUsrId(Integer urpCreatedUsrId)
	{
		this.urpCreatedUsrId = urpCreatedUsrId;
	}

	public Integer getUrpModifiedUsrId()
	{
		return urpModifiedUsrId;
	}

	public void setUrpModifiedUsrId(Integer urpModifiedUsrId)
	{
		this.urpModifiedUsrId = urpModifiedUsrId;
	}

	public DateTime getUrpCreatedDttm()
	{
		return urpCreatedDttm;
	}

	public void setUrpCreatedDttm(DateTime urpCreatedDttm)
	{
		this.urpCreatedDttm = urpCreatedDttm;
	}

	public DateTime getUrpModifiedDttm()
	{
		return urpModifiedDttm;
	}

	public void setUrpModifiedDttm(DateTime urpModifiedDttm)
	{
		this.urpModifiedDttm = urpModifiedDttm;
	}

}
