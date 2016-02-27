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

public abstract class HealthCheckerSettings extends SignatureDbo
{
	private Integer hcsId;
	private String hcsName;
	private Integer jbtId;
	private Integer hcsFreqSecs;
	private String hcsBk;
	private Integer hcsCreatedUsrId;
	private Integer hcsModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime hcsCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime hcsModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			hcsId = getInteger(rs.getInt("hcs_id"));
			hcsName = rs.getString("hcs_name");
			jbtId = getInteger(rs.getInt("jbt_id"));
			hcsFreqSecs = rs.getInt("hcs_freq_secs");
			hcsBk = rs.getString("hcs_bk");
			hcsCreatedUsrId = getInteger(rs.getInt("hcs_created_usr_id"));
			hcsModifiedUsrId = getInteger(rs.getInt("hcs_modified_usr_id"));
			hcsCreatedDttm = getDateTime(rs.getTimestamp("hcs_created_dttm"));
			hcsModifiedDttm = getDateTime(rs.getTimestamp("hcs_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return hcsId;
	}

	public void setPk(int pk)
	{
		hcsId = pk;
	}

	public String getBk()
	{
		return hcsBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(hcsId.toString());
		data.add(hcsName.toString());
		data.add(jbtId.toString());
		data.add(hcsFreqSecs.toString());
		data.add(getHcsCreatedUsr());
		data.add(Constants.dttf.print(hcsCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("hcsName", getString(hcsName));
		data.put("jbt", getJbt());
		data.put("hcsFreqSecs", getString(hcsFreqSecs));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setHcsName(input.getString("hcsName"));
		setJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("jbt")));
		setHcsFreqSecs(input.getInteger("hcsFreqSecs"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(hcsBk.toString());
		auditData.add(getHcsCreatedUsr());
		auditData.add(hcsModifiedUsrId == null ? Constants.NULLSTRING : getHcsModifiedUsr());
		auditData.add(Constants.dttf.print(hcsCreatedDttm));
		auditData.add(hcsModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(hcsModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return hcsName;
	}

	public String getJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, jbtId);
	}

	public String getHcsCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, hcsCreatedUsrId);
	}

	public String getHcsModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, hcsModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("jbt"))
			return getJbt();
		if (propertyName.equals("hcsCreatedUsr"))
			return getHcsCreatedUsr();
		if (propertyName.equals("hcsModifiedUsr"))
			return getHcsModifiedUsr();
		if (propertyName.equals("hcsId"))
			return getHcsId();
		if (propertyName.equals("hcsName"))
			return getHcsName();
		if (propertyName.equals("jbtId"))
			return getJbtId();
		if (propertyName.equals("hcsFreqSecs"))
			return getHcsFreqSecs();
		if (propertyName.equals("hcsBk"))
			return getHcsBk();
		if (propertyName.equals("hcsCreatedUsrId"))
			return getHcsCreatedUsrId();
		if (propertyName.equals("hcsModifiedUsrId"))
			return getHcsModifiedUsrId();
		if (propertyName.equals("hcsCreatedDttm"))
			return getHcsCreatedDttm();
		if (propertyName.equals("hcsModifiedDttm"))
			return getHcsModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getHcsId()
	{
		return hcsId;
	}

	public void setHcsId(Integer hcsId)
	{
		this.hcsId = hcsId;
	}

	public String getHcsName()
	{
		return hcsName;
	}

	public void setHcsName(String hcsName)
	{
		this.hcsName = hcsName;
	}

	public Integer getJbtId()
	{
		return jbtId;
	}

	public void setJbtId(Integer jbtId)
	{
		this.jbtId = jbtId;
	}

	public Integer getHcsFreqSecs()
	{
		return hcsFreqSecs;
	}

	public void setHcsFreqSecs(Integer hcsFreqSecs)
	{
		this.hcsFreqSecs = hcsFreqSecs;
	}

	public String getHcsBk()
	{
		return hcsBk;
	}

	public void setHcsBk(String hcsBk)
	{
		this.hcsBk = hcsBk;
	}

	public Integer getHcsCreatedUsrId()
	{
		return hcsCreatedUsrId;
	}

	public void setHcsCreatedUsrId(Integer hcsCreatedUsrId)
	{
		this.hcsCreatedUsrId = hcsCreatedUsrId;
	}

	public Integer getHcsModifiedUsrId()
	{
		return hcsModifiedUsrId;
	}

	public void setHcsModifiedUsrId(Integer hcsModifiedUsrId)
	{
		this.hcsModifiedUsrId = hcsModifiedUsrId;
	}

	public DateTime getHcsCreatedDttm()
	{
		return hcsCreatedDttm;
	}

	public void setHcsCreatedDttm(DateTime hcsCreatedDttm)
	{
		this.hcsCreatedDttm = hcsCreatedDttm;
	}

	public DateTime getHcsModifiedDttm()
	{
		return hcsModifiedDttm;
	}

	public void setHcsModifiedDttm(DateTime hcsModifiedDttm)
	{
		this.hcsModifiedDttm = hcsModifiedDttm;
	}

}
