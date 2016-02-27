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

public abstract class UserTbl extends SignatureDbo
{
	private Integer usrId;
	private Integer rolId;
	private String usrName;
	private String usrPassword;
	private String usrDisplayName;
	private String usrEmail;
	private String usrMobilePhone;
	private String usrStatus;
	private String usrBk;
	private Integer usrCreatedUsrId;
	private Integer usrModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime usrCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime usrModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			usrId = getInteger(rs.getInt("usr_id"));
			rolId = getInteger(rs.getInt("rol_id"));
			usrName = rs.getString("usr_name");
			usrPassword = rs.getString("usr_password");
			usrDisplayName = rs.getString("usr_display_name");
			usrEmail = rs.getString("usr_email");
			usrMobilePhone = rs.getString("usr_mobile_phone");
			usrStatus = rs.getString("usr_status");
			usrBk = rs.getString("usr_bk");
			usrCreatedUsrId = getInteger(rs.getInt("usr_created_usr_id"));
			usrModifiedUsrId = getInteger(rs.getInt("usr_modified_usr_id"));
			usrCreatedDttm = getDateTime(rs.getTimestamp("usr_created_dttm"));
			usrModifiedDttm = getDateTime(rs.getTimestamp("usr_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return usrId;
	}

	public void setPk(int pk)
	{
		usrId = pk;
	}

	public String getBk()
	{
		return usrBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(usrId.toString());
		data.add(rolId.toString());
		data.add(usrName.toString());
		data.add(usrPassword.toString());
		data.add(usrDisplayName.toString());
		data.add(usrEmail == null ? Constants.NULLSTRING : usrEmail.toString());
		data.add(usrMobilePhone == null ? Constants.NULLSTRING : usrMobilePhone.toString());
		data.add(usrStatus.toString());
		data.add(getUsrCreatedUsr());
		data.add(Constants.dttf.print(usrCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("rol", getRol());
		data.put("usrName", getString(usrName));
		data.put("usrPassword", getString(usrPassword));
		data.put("usrDisplayName", getString(usrDisplayName));
		data.put("usrEmail", getString(usrEmail));
		data.put("usrMobilePhone", getString(usrMobilePhone));
		data.put("usrStatus", getString(usrStatus));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setRolId(DisplayFieldHelper.getI().getPk(RoleDba.class, input.getString("rol")));
		setUsrName(input.getString("usrName"));
		setUsrPassword(input.getString("usrPassword"));
		setUsrDisplayName(input.getString("usrDisplayName"));
		setUsrEmail(input.getString("usrEmail"));
		setUsrMobilePhone(input.getString("usrMobilePhone"));
		setUsrStatus(input.getString("usrStatus"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(usrBk.toString());
		auditData.add(getUsrCreatedUsr());
		auditData.add(usrModifiedUsrId == null ? Constants.NULLSTRING : getUsrModifiedUsr());
		auditData.add(Constants.dttf.print(usrCreatedDttm));
		auditData.add(usrModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(usrModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return usrName;
	}

	public String getRol()
	{
		return DisplayFieldHelper.getI().getDisplayField(RoleDba.class, rolId);
	}

	public String getUsrCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, usrCreatedUsrId);
	}

	public String getUsrModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, usrModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("rol"))
			return getRol();
		if (propertyName.equals("usrCreatedUsr"))
			return getUsrCreatedUsr();
		if (propertyName.equals("usrModifiedUsr"))
			return getUsrModifiedUsr();
		if (propertyName.equals("usrId"))
			return getUsrId();
		if (propertyName.equals("rolId"))
			return getRolId();
		if (propertyName.equals("usrName"))
			return getUsrName();
		if (propertyName.equals("usrPassword"))
			return getUsrPassword();
		if (propertyName.equals("usrDisplayName"))
			return getUsrDisplayName();
		if (propertyName.equals("usrEmail"))
			return getUsrEmail();
		if (propertyName.equals("usrMobilePhone"))
			return getUsrMobilePhone();
		if (propertyName.equals("usrStatus"))
			return getUsrStatus();
		if (propertyName.equals("usrBk"))
			return getUsrBk();
		if (propertyName.equals("usrCreatedUsrId"))
			return getUsrCreatedUsrId();
		if (propertyName.equals("usrModifiedUsrId"))
			return getUsrModifiedUsrId();
		if (propertyName.equals("usrCreatedDttm"))
			return getUsrCreatedDttm();
		if (propertyName.equals("usrModifiedDttm"))
			return getUsrModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getUsrId()
	{
		return usrId;
	}

	public void setUsrId(Integer usrId)
	{
		this.usrId = usrId;
	}

	public Integer getRolId()
	{
		return rolId;
	}

	public void setRolId(Integer rolId)
	{
		this.rolId = rolId;
	}

	public String getUsrName()
	{
		return usrName;
	}

	public void setUsrName(String usrName)
	{
		this.usrName = usrName;
	}

	public String getUsrPassword()
	{
		return usrPassword;
	}

	public void setUsrPassword(String usrPassword)
	{
		this.usrPassword = usrPassword;
	}

	public String getUsrDisplayName()
	{
		return usrDisplayName;
	}

	public void setUsrDisplayName(String usrDisplayName)
	{
		this.usrDisplayName = usrDisplayName;
	}

	public String getUsrEmail()
	{
		return usrEmail;
	}

	public void setUsrEmail(String usrEmail)
	{
		this.usrEmail = usrEmail;
	}

	public String getUsrMobilePhone()
	{
		return usrMobilePhone;
	}

	public void setUsrMobilePhone(String usrMobilePhone)
	{
		this.usrMobilePhone = usrMobilePhone;
	}

	public String getUsrStatus()
	{
		return usrStatus;
	}

	public void setUsrStatus(String usrStatus)
	{
		this.usrStatus = usrStatus;
	}

	public String getUsrBk()
	{
		return usrBk;
	}

	public void setUsrBk(String usrBk)
	{
		this.usrBk = usrBk;
	}

	public Integer getUsrCreatedUsrId()
	{
		return usrCreatedUsrId;
	}

	public void setUsrCreatedUsrId(Integer usrCreatedUsrId)
	{
		this.usrCreatedUsrId = usrCreatedUsrId;
	}

	public Integer getUsrModifiedUsrId()
	{
		return usrModifiedUsrId;
	}

	public void setUsrModifiedUsrId(Integer usrModifiedUsrId)
	{
		this.usrModifiedUsrId = usrModifiedUsrId;
	}

	public DateTime getUsrCreatedDttm()
	{
		return usrCreatedDttm;
	}

	public void setUsrCreatedDttm(DateTime usrCreatedDttm)
	{
		this.usrCreatedDttm = usrCreatedDttm;
	}

	public DateTime getUsrModifiedDttm()
	{
		return usrModifiedDttm;
	}

	public void setUsrModifiedDttm(DateTime usrModifiedDttm)
	{
		this.usrModifiedDttm = usrModifiedDttm;
	}

}
