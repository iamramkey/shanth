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
import se.signa.signature.gen.dba.JobTypeDba;
import se.signa.signature.gen.dba.UserTblDba;
import se.signa.signature.helpers.DisplayFieldHelper;

public abstract class HouseKeepingSettings extends SignatureDbo
{
	private Integer hksId;
	private String hksName;
	private Integer hksFreqSecs;
	private Integer hksJbtId;
	private String hksBk;
	private Integer hksCreatedUsrId;
	private Integer hksModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime hksCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime hksModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			hksId = getInteger(rs.getInt("hks_id"));
			hksName = rs.getString("hks_name");
			hksFreqSecs = rs.getInt("hks_freq_secs");
			hksJbtId = getInteger(rs.getInt("hks_jbt_id"));
			hksBk = rs.getString("hks_bk");
			hksCreatedUsrId = getInteger(rs.getInt("hks_created_usr_id"));
			hksModifiedUsrId = getInteger(rs.getInt("hks_modified_usr_id"));
			hksCreatedDttm = getDateTime(rs.getTimestamp("hks_created_dttm"));
			hksModifiedDttm = getDateTime(rs.getTimestamp("hks_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return hksId;
	}

	public void setPk(int pk)
	{
		hksId = pk;
	}

	public String getBk()
	{
		return hksBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(hksId.toString());
		data.add(hksName.toString());
		data.add(hksFreqSecs.toString());
		data.add(hksJbtId.toString());
		data.add(getHksCreatedUsr());
		data.add(Constants.dttf.print(hksCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("hksName", getString(hksName));
		data.put("hksFreqSecs", getString(hksFreqSecs));
		data.put("hksJbt", getHksJbt());
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setHksName(input.getString("hksName"));
		setHksFreqSecs(input.getInteger("hksFreqSecs"));
		setHksJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("hksJbt")));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(hksBk.toString());
		auditData.add(getHksCreatedUsr());
		auditData.add(hksModifiedUsrId == null ? Constants.NULLSTRING : getHksModifiedUsr());
		auditData.add(Constants.dttf.print(hksCreatedDttm));
		auditData.add(hksModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(hksModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return hksName;
	}

	public String getHksJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, hksJbtId);
	}

	public String getHksCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, hksCreatedUsrId);
	}

	public String getHksModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, hksModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("hksJbt"))
			return getHksJbt();
		if (propertyName.equals("hksCreatedUsr"))
			return getHksCreatedUsr();
		if (propertyName.equals("hksModifiedUsr"))
			return getHksModifiedUsr();
		if (propertyName.equals("hksId"))
			return getHksId();
		if (propertyName.equals("hksName"))
			return getHksName();
		if (propertyName.equals("hksFreqSecs"))
			return getHksFreqSecs();
		if (propertyName.equals("hksJbtId"))
			return getHksJbtId();
		if (propertyName.equals("hksBk"))
			return getHksBk();
		if (propertyName.equals("hksCreatedUsrId"))
			return getHksCreatedUsrId();
		if (propertyName.equals("hksModifiedUsrId"))
			return getHksModifiedUsrId();
		if (propertyName.equals("hksCreatedDttm"))
			return getHksCreatedDttm();
		if (propertyName.equals("hksModifiedDttm"))
			return getHksModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getHksId()
	{
		return hksId;
	}

	public void setHksId(Integer hksId)
	{
		this.hksId = hksId;
	}

	public String getHksName()
	{
		return hksName;
	}

	public void setHksName(String hksName)
	{
		this.hksName = hksName;
	}

	public Integer getHksFreqSecs()
	{
		return hksFreqSecs;
	}

	public void setHksFreqSecs(Integer hksFreqSecs)
	{
		this.hksFreqSecs = hksFreqSecs;
	}

	public Integer getHksJbtId()
	{
		return hksJbtId;
	}

	public void setHksJbtId(Integer hksJbtId)
	{
		this.hksJbtId = hksJbtId;
	}

	public String getHksBk()
	{
		return hksBk;
	}

	public void setHksBk(String hksBk)
	{
		this.hksBk = hksBk;
	}

	public Integer getHksCreatedUsrId()
	{
		return hksCreatedUsrId;
	}

	public void setHksCreatedUsrId(Integer hksCreatedUsrId)
	{
		this.hksCreatedUsrId = hksCreatedUsrId;
	}

	public Integer getHksModifiedUsrId()
	{
		return hksModifiedUsrId;
	}

	public void setHksModifiedUsrId(Integer hksModifiedUsrId)
	{
		this.hksModifiedUsrId = hksModifiedUsrId;
	}

	public DateTime getHksCreatedDttm()
	{
		return hksCreatedDttm;
	}

	public void setHksCreatedDttm(DateTime hksCreatedDttm)
	{
		this.hksCreatedDttm = hksCreatedDttm;
	}

	public DateTime getHksModifiedDttm()
	{
		return hksModifiedDttm;
	}

	public void setHksModifiedDttm(DateTime hksModifiedDttm)
	{
		this.hksModifiedDttm = hksModifiedDttm;
	}

}
