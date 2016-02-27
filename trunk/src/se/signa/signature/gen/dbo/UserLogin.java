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
import se.signa.signature.gen.dba.UserTblDba;
import se.signa.signature.helpers.DisplayFieldHelper;

public abstract class UserLogin extends SignatureDbo
{
	private Integer uslId;
	private Integer usrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime uslLoginDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime uslLogoutDttm;
	private String uslName;
	private String uslStatus;
	private String uslBk;
	private Integer uslCreatedUsrId;
	private Integer uslModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime uslCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime uslModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			uslId = getInteger(rs.getInt("usl_id"));
			usrId = getInteger(rs.getInt("usr_id"));
			uslLoginDttm = getDateTime(rs.getTimestamp("usl_login_dttm"));
			uslLogoutDttm = getDateTime(rs.getTimestamp("usl_logout_dttm"));
			uslName = rs.getString("usl_name");
			uslStatus = rs.getString("usl_status");
			uslBk = rs.getString("usl_bk");
			uslCreatedUsrId = getInteger(rs.getInt("usl_created_usr_id"));
			uslModifiedUsrId = getInteger(rs.getInt("usl_modified_usr_id"));
			uslCreatedDttm = getDateTime(rs.getTimestamp("usl_created_dttm"));
			uslModifiedDttm = getDateTime(rs.getTimestamp("usl_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return uslId;
	}

	public void setPk(int pk)
	{
		uslId = pk;
	}

	public String getBk()
	{
		return uslBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(uslId.toString());
		data.add(usrId.toString());
		data.add(Constants.dttf.print(uslLoginDttm));
		data.add(uslLogoutDttm == null ? Constants.NULLSTRING : Constants.dttf.print(uslLogoutDttm));
		data.add(uslName.toString());
		data.add(uslStatus.toString());
		data.add(getUslCreatedUsr());
		data.add(Constants.dttf.print(uslCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("usr", getUsr());
		data.put("uslLoginDttm", Constants.dttf.print(uslLoginDttm));
		data.put("uslLogoutDttm", Constants.dttf.print(uslLogoutDttm));
		data.put("uslName", getString(uslName));
		data.put("uslStatus", getString(uslStatus));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setUsrId(DisplayFieldHelper.getI().getPk(UserTblDba.class, input.getString("usr")));
		setUslLoginDttm(input.getDate("uslLoginDttm"));
		setUslLogoutDttm(input.getDate("uslLogoutDttm"));
		setUslName(input.getString("uslName"));
		setUslStatus(input.getString("uslStatus"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(uslBk.toString());
		auditData.add(getUslCreatedUsr());
		auditData.add(uslModifiedUsrId == null ? Constants.NULLSTRING : getUslModifiedUsr());
		auditData.add(Constants.dttf.print(uslCreatedDttm));
		auditData.add(uslModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(uslModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return uslName;
	}

	public String getUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, usrId);
	}

	public String getUslCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, uslCreatedUsrId);
	}

	public String getUslModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, uslModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("usr"))
			return getUsr();
		if (propertyName.equals("uslCreatedUsr"))
			return getUslCreatedUsr();
		if (propertyName.equals("uslModifiedUsr"))
			return getUslModifiedUsr();
		if (propertyName.equals("uslId"))
			return getUslId();
		if (propertyName.equals("usrId"))
			return getUsrId();
		if (propertyName.equals("uslLoginDttm"))
			return getUslLoginDttm();
		if (propertyName.equals("uslLogoutDttm"))
			return getUslLogoutDttm();
		if (propertyName.equals("uslName"))
			return getUslName();
		if (propertyName.equals("uslStatus"))
			return getUslStatus();
		if (propertyName.equals("uslBk"))
			return getUslBk();
		if (propertyName.equals("uslCreatedUsrId"))
			return getUslCreatedUsrId();
		if (propertyName.equals("uslModifiedUsrId"))
			return getUslModifiedUsrId();
		if (propertyName.equals("uslCreatedDttm"))
			return getUslCreatedDttm();
		if (propertyName.equals("uslModifiedDttm"))
			return getUslModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getUslId()
	{
		return uslId;
	}

	public void setUslId(Integer uslId)
	{
		this.uslId = uslId;
	}

	public Integer getUsrId()
	{
		return usrId;
	}

	public void setUsrId(Integer usrId)
	{
		this.usrId = usrId;
	}

	public DateTime getUslLoginDttm()
	{
		return uslLoginDttm;
	}

	public void setUslLoginDttm(DateTime uslLoginDttm)
	{
		this.uslLoginDttm = uslLoginDttm;
	}

	public DateTime getUslLogoutDttm()
	{
		return uslLogoutDttm;
	}

	public void setUslLogoutDttm(DateTime uslLogoutDttm)
	{
		this.uslLogoutDttm = uslLogoutDttm;
	}

	public String getUslName()
	{
		return uslName;
	}

	public void setUslName(String uslName)
	{
		this.uslName = uslName;
	}

	public String getUslStatus()
	{
		return uslStatus;
	}

	public void setUslStatus(String uslStatus)
	{
		this.uslStatus = uslStatus;
	}

	public String getUslBk()
	{
		return uslBk;
	}

	public void setUslBk(String uslBk)
	{
		this.uslBk = uslBk;
	}

	public Integer getUslCreatedUsrId()
	{
		return uslCreatedUsrId;
	}

	public void setUslCreatedUsrId(Integer uslCreatedUsrId)
	{
		this.uslCreatedUsrId = uslCreatedUsrId;
	}

	public Integer getUslModifiedUsrId()
	{
		return uslModifiedUsrId;
	}

	public void setUslModifiedUsrId(Integer uslModifiedUsrId)
	{
		this.uslModifiedUsrId = uslModifiedUsrId;
	}

	public DateTime getUslCreatedDttm()
	{
		return uslCreatedDttm;
	}

	public void setUslCreatedDttm(DateTime uslCreatedDttm)
	{
		this.uslCreatedDttm = uslCreatedDttm;
	}

	public DateTime getUslModifiedDttm()
	{
		return uslModifiedDttm;
	}

	public void setUslModifiedDttm(DateTime uslModifiedDttm)
	{
		this.uslModifiedDttm = uslModifiedDttm;
	}

}
