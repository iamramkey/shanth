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

public abstract class UserPasswordSettings extends SignatureDbo
{
	private Integer upsId;
	private String upsName;
	private Integer upsMaxAttempts;
	private Integer upsMaxInactiveDays;
	private Integer upsMinLength;
	private Integer upsResetInterval;
	private Integer upsResetWarningInterval;
	private String upsBk;
	private Integer upsCreatedUsrId;
	private Integer upsModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime upsCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime upsModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			upsId = getInteger(rs.getInt("ups_id"));
			upsName = rs.getString("ups_name");
			upsMaxAttempts = rs.getInt("ups_max_attempts");
			upsMaxInactiveDays = rs.getInt("ups_max_inactive_days");
			upsMinLength = rs.getInt("ups_min_length");
			upsResetInterval = rs.getInt("ups_reset_interval");
			upsResetWarningInterval = rs.getInt("ups_reset_warning_interval");
			upsBk = rs.getString("ups_bk");
			upsCreatedUsrId = getInteger(rs.getInt("ups_created_usr_id"));
			upsModifiedUsrId = getInteger(rs.getInt("ups_modified_usr_id"));
			upsCreatedDttm = getDateTime(rs.getTimestamp("ups_created_dttm"));
			upsModifiedDttm = getDateTime(rs.getTimestamp("ups_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return upsId;
	}

	public void setPk(int pk)
	{
		upsId = pk;
	}

	public String getBk()
	{
		return upsBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(upsId.toString());
		data.add(upsName.toString());
		data.add(upsMaxAttempts.toString());
		data.add(upsMaxInactiveDays.toString());
		data.add(upsMinLength.toString());
		data.add(upsResetInterval.toString());
		data.add(upsResetWarningInterval.toString());
		data.add(getUpsCreatedUsr());
		data.add(Constants.dttf.print(upsCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("upsName", getString(upsName));
		data.put("upsMaxAttempts", getString(upsMaxAttempts));
		data.put("upsMaxInactiveDays", getString(upsMaxInactiveDays));
		data.put("upsMinLength", getString(upsMinLength));
		data.put("upsResetInterval", getString(upsResetInterval));
		data.put("upsResetWarningInterval", getString(upsResetWarningInterval));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setUpsName(input.getString("upsName"));
		setUpsMaxAttempts(input.getInteger("upsMaxAttempts"));
		setUpsMaxInactiveDays(input.getInteger("upsMaxInactiveDays"));
		setUpsMinLength(input.getInteger("upsMinLength"));
		setUpsResetInterval(input.getInteger("upsResetInterval"));
		setUpsResetWarningInterval(input.getInteger("upsResetWarningInterval"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(upsBk.toString());
		auditData.add(getUpsCreatedUsr());
		auditData.add(upsModifiedUsrId == null ? Constants.NULLSTRING : getUpsModifiedUsr());
		auditData.add(Constants.dttf.print(upsCreatedDttm));
		auditData.add(upsModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(upsModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return upsName;
	}

	public String getUpsCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, upsCreatedUsrId);
	}

	public String getUpsModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, upsModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("upsCreatedUsr"))
			return getUpsCreatedUsr();
		if (propertyName.equals("upsModifiedUsr"))
			return getUpsModifiedUsr();
		if (propertyName.equals("upsId"))
			return getUpsId();
		if (propertyName.equals("upsName"))
			return getUpsName();
		if (propertyName.equals("upsMaxAttempts"))
			return getUpsMaxAttempts();
		if (propertyName.equals("upsMaxInactiveDays"))
			return getUpsMaxInactiveDays();
		if (propertyName.equals("upsMinLength"))
			return getUpsMinLength();
		if (propertyName.equals("upsResetInterval"))
			return getUpsResetInterval();
		if (propertyName.equals("upsResetWarningInterval"))
			return getUpsResetWarningInterval();
		if (propertyName.equals("upsBk"))
			return getUpsBk();
		if (propertyName.equals("upsCreatedUsrId"))
			return getUpsCreatedUsrId();
		if (propertyName.equals("upsModifiedUsrId"))
			return getUpsModifiedUsrId();
		if (propertyName.equals("upsCreatedDttm"))
			return getUpsCreatedDttm();
		if (propertyName.equals("upsModifiedDttm"))
			return getUpsModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getUpsId()
	{
		return upsId;
	}

	public void setUpsId(Integer upsId)
	{
		this.upsId = upsId;
	}

	public String getUpsName()
	{
		return upsName;
	}

	public void setUpsName(String upsName)
	{
		this.upsName = upsName;
	}

	public Integer getUpsMaxAttempts()
	{
		return upsMaxAttempts;
	}

	public void setUpsMaxAttempts(Integer upsMaxAttempts)
	{
		this.upsMaxAttempts = upsMaxAttempts;
	}

	public Integer getUpsMaxInactiveDays()
	{
		return upsMaxInactiveDays;
	}

	public void setUpsMaxInactiveDays(Integer upsMaxInactiveDays)
	{
		this.upsMaxInactiveDays = upsMaxInactiveDays;
	}

	public Integer getUpsMinLength()
	{
		return upsMinLength;
	}

	public void setUpsMinLength(Integer upsMinLength)
	{
		this.upsMinLength = upsMinLength;
	}

	public Integer getUpsResetInterval()
	{
		return upsResetInterval;
	}

	public void setUpsResetInterval(Integer upsResetInterval)
	{
		this.upsResetInterval = upsResetInterval;
	}

	public Integer getUpsResetWarningInterval()
	{
		return upsResetWarningInterval;
	}

	public void setUpsResetWarningInterval(Integer upsResetWarningInterval)
	{
		this.upsResetWarningInterval = upsResetWarningInterval;
	}

	public String getUpsBk()
	{
		return upsBk;
	}

	public void setUpsBk(String upsBk)
	{
		this.upsBk = upsBk;
	}

	public Integer getUpsCreatedUsrId()
	{
		return upsCreatedUsrId;
	}

	public void setUpsCreatedUsrId(Integer upsCreatedUsrId)
	{
		this.upsCreatedUsrId = upsCreatedUsrId;
	}

	public Integer getUpsModifiedUsrId()
	{
		return upsModifiedUsrId;
	}

	public void setUpsModifiedUsrId(Integer upsModifiedUsrId)
	{
		this.upsModifiedUsrId = upsModifiedUsrId;
	}

	public DateTime getUpsCreatedDttm()
	{
		return upsCreatedDttm;
	}

	public void setUpsCreatedDttm(DateTime upsCreatedDttm)
	{
		this.upsCreatedDttm = upsCreatedDttm;
	}

	public DateTime getUpsModifiedDttm()
	{
		return upsModifiedDttm;
	}

	public void setUpsModifiedDttm(DateTime upsModifiedDttm)
	{
		this.upsModifiedDttm = upsModifiedDttm;
	}

}
