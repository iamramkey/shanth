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
import se.signa.signature.gen.dba.JobDba;
import se.signa.signature.gen.dba.UserTblDba;
import se.signa.signature.helpers.DisplayFieldHelper;

public abstract class RecentActivity extends SignatureDbo
{
	private Integer reaId;
	private Integer delId;
	private Integer jobId;
	private String reaType;
	private String reaDesc;
	private String reaName;
	private String reaBk;
	private Integer reaCreatedUsrId;
	private Integer reaModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime reaCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime reaModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			reaId = getInteger(rs.getInt("rea_id"));
			delId = getInteger(rs.getInt("del_id"));
			jobId = getInteger(rs.getInt("job_id"));
			reaType = rs.getString("rea_type");
			reaDesc = rs.getString("rea_desc");
			reaName = rs.getString("rea_name");
			reaBk = rs.getString("rea_bk");
			reaCreatedUsrId = getInteger(rs.getInt("rea_created_usr_id"));
			reaModifiedUsrId = getInteger(rs.getInt("rea_modified_usr_id"));
			reaCreatedDttm = getDateTime(rs.getTimestamp("rea_created_dttm"));
			reaModifiedDttm = getDateTime(rs.getTimestamp("rea_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return reaId;
	}

	public void setPk(int pk)
	{
		reaId = pk;
	}

	public String getBk()
	{
		return reaBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(reaId.toString());
		data.add(delId.toString());
		data.add(jobId.toString());
		data.add(reaType.toString());
		data.add(reaDesc.toString());
		data.add(reaName.toString());
		data.add(getReaCreatedUsr());
		data.add(Constants.dttf.print(reaCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("delId", getDelId());
		data.put("job", getJob());
		data.put("reaType", getString(reaType));
		data.put("reaDesc", getString(reaDesc));
		data.put("reaName", getString(reaName));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setJobId(DisplayFieldHelper.getI().getPk(JobDba.class, input.getString("job")));
		setReaType(input.getString("reaType"));
		setReaDesc(input.getString("reaDesc"));
		setReaName(input.getString("reaName"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(reaBk.toString());
		auditData.add(getReaCreatedUsr());
		auditData.add(reaModifiedUsrId == null ? Constants.NULLSTRING : getReaModifiedUsr());
		auditData.add(Constants.dttf.print(reaCreatedDttm));
		auditData.add(reaModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(reaModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return reaName;
	}

	public String getJob()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobDba.class, jobId);
	}

	public String getReaCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, reaCreatedUsrId);
	}

	public String getReaModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, reaModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("job"))
			return getJob();
		if (propertyName.equals("reaCreatedUsr"))
			return getReaCreatedUsr();
		if (propertyName.equals("reaModifiedUsr"))
			return getReaModifiedUsr();
		if (propertyName.equals("reaId"))
			return getReaId();
		if (propertyName.equals("delId"))
			return getDelId();
		if (propertyName.equals("jobId"))
			return getJobId();
		if (propertyName.equals("reaType"))
			return getReaType();
		if (propertyName.equals("reaDesc"))
			return getReaDesc();
		if (propertyName.equals("reaName"))
			return getReaName();
		if (propertyName.equals("reaBk"))
			return getReaBk();
		if (propertyName.equals("reaCreatedUsrId"))
			return getReaCreatedUsrId();
		if (propertyName.equals("reaModifiedUsrId"))
			return getReaModifiedUsrId();
		if (propertyName.equals("reaCreatedDttm"))
			return getReaCreatedDttm();
		if (propertyName.equals("reaModifiedDttm"))
			return getReaModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getReaId()
	{
		return reaId;
	}

	public void setReaId(Integer reaId)
	{
		this.reaId = reaId;
	}

	public Integer getDelId()
	{
		return delId;
	}

	public void setDelId(Integer delId)
	{
		this.delId = delId;
	}

	public Integer getJobId()
	{
		return jobId;
	}

	public void setJobId(Integer jobId)
	{
		this.jobId = jobId;
	}

	public String getReaType()
	{
		return reaType;
	}

	public void setReaType(String reaType)
	{
		this.reaType = reaType;
	}

	public String getReaDesc()
	{
		return reaDesc;
	}

	public void setReaDesc(String reaDesc)
	{
		this.reaDesc = reaDesc;
	}

	public String getReaName()
	{
		return reaName;
	}

	public void setReaName(String reaName)
	{
		this.reaName = reaName;
	}

	public String getReaBk()
	{
		return reaBk;
	}

	public void setReaBk(String reaBk)
	{
		this.reaBk = reaBk;
	}

	public Integer getReaCreatedUsrId()
	{
		return reaCreatedUsrId;
	}

	public void setReaCreatedUsrId(Integer reaCreatedUsrId)
	{
		this.reaCreatedUsrId = reaCreatedUsrId;
	}

	public Integer getReaModifiedUsrId()
	{
		return reaModifiedUsrId;
	}

	public void setReaModifiedUsrId(Integer reaModifiedUsrId)
	{
		this.reaModifiedUsrId = reaModifiedUsrId;
	}

	public DateTime getReaCreatedDttm()
	{
		return reaCreatedDttm;
	}

	public void setReaCreatedDttm(DateTime reaCreatedDttm)
	{
		this.reaCreatedDttm = reaCreatedDttm;
	}

	public DateTime getReaModifiedDttm()
	{
		return reaModifiedDttm;
	}

	public void setReaModifiedDttm(DateTime reaModifiedDttm)
	{
		this.reaModifiedDttm = reaModifiedDttm;
	}

}
