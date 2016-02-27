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
import se.signa.signature.gen.dba.ToolbarItemDba;
import se.signa.signature.gen.dba.UserTblDba;
import se.signa.signature.helpers.DisplayFieldHelper;

public abstract class RoleCustom extends SignatureDbo
{
	private Integer rcpId;
	private Integer rolId;
	private Integer tbiId;
	private String rcpCode;
	private String rcpBackgroundAction;
	private String rcpBk;
	private Integer rcpCreatedUsrId;
	private Integer rcpModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime rcpCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime rcpModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			rcpId = getInteger(rs.getInt("rcp_id"));
			rolId = getInteger(rs.getInt("rol_id"));
			tbiId = getInteger(rs.getInt("tbi_id"));
			rcpCode = rs.getString("rcp_code");
			rcpBackgroundAction = rs.getString("rcp_background_action");
			rcpBk = rs.getString("rcp_bk");
			rcpCreatedUsrId = getInteger(rs.getInt("rcp_created_usr_id"));
			rcpModifiedUsrId = getInteger(rs.getInt("rcp_modified_usr_id"));
			rcpCreatedDttm = getDateTime(rs.getTimestamp("rcp_created_dttm"));
			rcpModifiedDttm = getDateTime(rs.getTimestamp("rcp_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return rcpId;
	}

	public void setPk(int pk)
	{
		rcpId = pk;
	}

	public String getBk()
	{
		return rcpBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(rcpId.toString());
		data.add(rolId.toString());
		data.add(tbiId.toString());
		data.add(rcpCode.toString());
		data.add(rcpBackgroundAction.toString());
		data.add(getRcpCreatedUsr());
		data.add(Constants.dttf.print(rcpCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("rol", getRol());
		data.put("tbi", getTbi());
		data.put("rcpCode", getString(rcpCode));
		data.put("rcpBackgroundAction", getString(rcpBackgroundAction));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setRolId(DisplayFieldHelper.getI().getPk(RoleDba.class, input.getString("rol")));
		setTbiId(DisplayFieldHelper.getI().getPk(ToolbarItemDba.class, input.getString("tbi")));
		setRcpCode(input.getString("rcpCode"));
		setRcpBackgroundAction(input.getString("rcpBackgroundAction"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(rcpBk.toString());
		auditData.add(getRcpCreatedUsr());
		auditData.add(rcpModifiedUsrId == null ? Constants.NULLSTRING : getRcpModifiedUsr());
		auditData.add(Constants.dttf.print(rcpCreatedDttm));
		auditData.add(rcpModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(rcpModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return rcpCode;
	}

	public String getRol()
	{
		return DisplayFieldHelper.getI().getDisplayField(RoleDba.class, rolId);
	}

	public String getTbi()
	{
		return DisplayFieldHelper.getI().getDisplayField(ToolbarItemDba.class, tbiId);
	}

	public String getRcpCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, rcpCreatedUsrId);
	}

	public String getRcpModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, rcpModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("rol"))
			return getRol();
		if (propertyName.equals("tbi"))
			return getTbi();
		if (propertyName.equals("rcpCreatedUsr"))
			return getRcpCreatedUsr();
		if (propertyName.equals("rcpModifiedUsr"))
			return getRcpModifiedUsr();
		if (propertyName.equals("rcpId"))
			return getRcpId();
		if (propertyName.equals("rolId"))
			return getRolId();
		if (propertyName.equals("tbiId"))
			return getTbiId();
		if (propertyName.equals("rcpCode"))
			return getRcpCode();
		if (propertyName.equals("rcpBackgroundAction"))
			return getRcpBackgroundAction();
		if (propertyName.equals("rcpBk"))
			return getRcpBk();
		if (propertyName.equals("rcpCreatedUsrId"))
			return getRcpCreatedUsrId();
		if (propertyName.equals("rcpModifiedUsrId"))
			return getRcpModifiedUsrId();
		if (propertyName.equals("rcpCreatedDttm"))
			return getRcpCreatedDttm();
		if (propertyName.equals("rcpModifiedDttm"))
			return getRcpModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getRcpId()
	{
		return rcpId;
	}

	public void setRcpId(Integer rcpId)
	{
		this.rcpId = rcpId;
	}

	public Integer getRolId()
	{
		return rolId;
	}

	public void setRolId(Integer rolId)
	{
		this.rolId = rolId;
	}

	public Integer getTbiId()
	{
		return tbiId;
	}

	public void setTbiId(Integer tbiId)
	{
		this.tbiId = tbiId;
	}

	public String getRcpCode()
	{
		return rcpCode;
	}

	public void setRcpCode(String rcpCode)
	{
		this.rcpCode = rcpCode;
	}

	public String getRcpBackgroundAction()
	{
		return rcpBackgroundAction;
	}

	public void setRcpBackgroundAction(String rcpBackgroundAction)
	{
		this.rcpBackgroundAction = rcpBackgroundAction;
	}

	public String getRcpBk()
	{
		return rcpBk;
	}

	public void setRcpBk(String rcpBk)
	{
		this.rcpBk = rcpBk;
	}

	public Integer getRcpCreatedUsrId()
	{
		return rcpCreatedUsrId;
	}

	public void setRcpCreatedUsrId(Integer rcpCreatedUsrId)
	{
		this.rcpCreatedUsrId = rcpCreatedUsrId;
	}

	public Integer getRcpModifiedUsrId()
	{
		return rcpModifiedUsrId;
	}

	public void setRcpModifiedUsrId(Integer rcpModifiedUsrId)
	{
		this.rcpModifiedUsrId = rcpModifiedUsrId;
	}

	public DateTime getRcpCreatedDttm()
	{
		return rcpCreatedDttm;
	}

	public void setRcpCreatedDttm(DateTime rcpCreatedDttm)
	{
		this.rcpCreatedDttm = rcpCreatedDttm;
	}

	public DateTime getRcpModifiedDttm()
	{
		return rcpModifiedDttm;
	}

	public void setRcpModifiedDttm(DateTime rcpModifiedDttm)
	{
		this.rcpModifiedDttm = rcpModifiedDttm;
	}

}
