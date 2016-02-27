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
import se.signa.signature.gen.dba.FiletblDba;
import se.signa.signature.gen.dba.JobDba;
import se.signa.signature.gen.dba.JobTypeDba;
import se.signa.signature.gen.dba.UserTblDba;
import se.signa.signature.helpers.DisplayFieldHelper;

public abstract class Extraction extends SignatureDbo
{
	private Integer etrId;
	private Integer delId;
	private Integer accId;
	private Integer filId;
	private Integer jobId;
	private Integer jbtId;
	private Integer etrRowNo;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime etrScheduleDttm;
	private String etrName;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime etrFromDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime etrToDttm;
	private String status;
	private String etrBk;
	private Integer etrCreatedUsrId;
	private Integer etrModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime etrCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime etrModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			etrId = getInteger(rs.getInt("etr_id"));
			delId = getInteger(rs.getInt("del_id"));
			accId = getInteger(rs.getInt("acc_id"));
			filId = getInteger(rs.getInt("fil_id"));
			jobId = getInteger(rs.getInt("job_id"));
			jbtId = getInteger(rs.getInt("jbt_id"));
			etrRowNo = rs.getInt("etr_row_no");
			etrScheduleDttm = getDateTime(rs.getTimestamp("etr_schedule_dttm"));
			etrName = rs.getString("etr_name");
			etrFromDttm = getDateTime(rs.getTimestamp("etr_from_dttm"));
			etrToDttm = getDateTime(rs.getTimestamp("etr_to_dttm"));
			status = rs.getString("status");
			etrBk = rs.getString("etr_bk");
			etrCreatedUsrId = getInteger(rs.getInt("etr_created_usr_id"));
			etrModifiedUsrId = getInteger(rs.getInt("etr_modified_usr_id"));
			etrCreatedDttm = getDateTime(rs.getTimestamp("etr_created_dttm"));
			etrModifiedDttm = getDateTime(rs.getTimestamp("etr_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return etrId;
	}

	public void setPk(int pk)
	{
		etrId = pk;
	}

	public String getBk()
	{
		return etrBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(etrId.toString());
		data.add(delId == null ? Constants.NULLSTRING : delId.toString());
		data.add(accId == null ? Constants.NULLSTRING : accId.toString());
		data.add(filId == null ? Constants.NULLSTRING : filId.toString());
		data.add(jobId == null ? Constants.NULLSTRING : jobId.toString());
		data.add(jbtId == null ? Constants.NULLSTRING : jbtId.toString());
		data.add(etrRowNo == null ? Constants.NULLSTRING : etrRowNo.toString());
		data.add(etrScheduleDttm == null ? Constants.NULLSTRING : Constants.dttf.print(etrScheduleDttm));
		data.add(etrName.toString());
		data.add(etrFromDttm == null ? Constants.NULLSTRING : Constants.dttf.print(etrFromDttm));
		data.add(etrToDttm == null ? Constants.NULLSTRING : Constants.dttf.print(etrToDttm));
		data.add(status.toString());
		data.add(getEtrCreatedUsr());
		data.add(Constants.dttf.print(etrCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("delId", getDelId());
		data.put("accId", getAccId());
		data.put("fil", getFil());
		data.put("job", getJob());
		data.put("jbt", getJbt());
		data.put("etrRowNo", getString(etrRowNo));
		data.put("etrScheduleDttm", Constants.dttf.print(etrScheduleDttm));
		data.put("etrName", getString(etrName));
		data.put("etrFromDttm", Constants.dttf.print(etrFromDttm));
		data.put("etrToDttm", Constants.dttf.print(etrToDttm));
		data.put("status", getString(status));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setFilId(DisplayFieldHelper.getI().getPk(FiletblDba.class, input.getString("fil")));
		setJobId(DisplayFieldHelper.getI().getPk(JobDba.class, input.getString("job")));
		setJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("jbt")));
		setEtrRowNo(input.getInteger("etrRowNo"));
		setEtrScheduleDttm(input.getDate("etrScheduleDttm"));
		setEtrName(input.getString("etrName"));
		setEtrFromDttm(input.getDate("etrFromDttm"));
		setEtrToDttm(input.getDate("etrToDttm"));
		setStatus(input.getString("status"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(etrBk.toString());
		auditData.add(getEtrCreatedUsr());
		auditData.add(etrModifiedUsrId == null ? Constants.NULLSTRING : getEtrModifiedUsr());
		auditData.add(Constants.dttf.print(etrCreatedDttm));
		auditData.add(etrModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(etrModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return etrName;
	}

	public String getFil()
	{
		return DisplayFieldHelper.getI().getDisplayField(FiletblDba.class, filId);
	}

	public String getJob()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobDba.class, jobId);
	}

	public String getJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, jbtId);
	}

	public String getEtrCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, etrCreatedUsrId);
	}

	public String getEtrModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, etrModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("fil"))
			return getFil();
		if (propertyName.equals("job"))
			return getJob();
		if (propertyName.equals("jbt"))
			return getJbt();
		if (propertyName.equals("etrCreatedUsr"))
			return getEtrCreatedUsr();
		if (propertyName.equals("etrModifiedUsr"))
			return getEtrModifiedUsr();
		if (propertyName.equals("etrId"))
			return getEtrId();
		if (propertyName.equals("delId"))
			return getDelId();
		if (propertyName.equals("accId"))
			return getAccId();
		if (propertyName.equals("filId"))
			return getFilId();
		if (propertyName.equals("jobId"))
			return getJobId();
		if (propertyName.equals("jbtId"))
			return getJbtId();
		if (propertyName.equals("etrRowNo"))
			return getEtrRowNo();
		if (propertyName.equals("etrScheduleDttm"))
			return getEtrScheduleDttm();
		if (propertyName.equals("etrName"))
			return getEtrName();
		if (propertyName.equals("etrFromDttm"))
			return getEtrFromDttm();
		if (propertyName.equals("etrToDttm"))
			return getEtrToDttm();
		if (propertyName.equals("status"))
			return getStatus();
		if (propertyName.equals("etrBk"))
			return getEtrBk();
		if (propertyName.equals("etrCreatedUsrId"))
			return getEtrCreatedUsrId();
		if (propertyName.equals("etrModifiedUsrId"))
			return getEtrModifiedUsrId();
		if (propertyName.equals("etrCreatedDttm"))
			return getEtrCreatedDttm();
		if (propertyName.equals("etrModifiedDttm"))
			return getEtrModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getEtrId()
	{
		return etrId;
	}

	public void setEtrId(Integer etrId)
	{
		this.etrId = etrId;
	}

	public Integer getDelId()
	{
		return delId;
	}

	public void setDelId(Integer delId)
	{
		this.delId = delId;
	}

	public Integer getAccId()
	{
		return accId;
	}

	public void setAccId(Integer accId)
	{
		this.accId = accId;
	}

	public Integer getFilId()
	{
		return filId;
	}

	public void setFilId(Integer filId)
	{
		this.filId = filId;
	}

	public Integer getJobId()
	{
		return jobId;
	}

	public void setJobId(Integer jobId)
	{
		this.jobId = jobId;
	}

	public Integer getJbtId()
	{
		return jbtId;
	}

	public void setJbtId(Integer jbtId)
	{
		this.jbtId = jbtId;
	}

	public Integer getEtrRowNo()
	{
		return etrRowNo;
	}

	public void setEtrRowNo(Integer etrRowNo)
	{
		this.etrRowNo = etrRowNo;
	}

	public DateTime getEtrScheduleDttm()
	{
		return etrScheduleDttm;
	}

	public void setEtrScheduleDttm(DateTime etrScheduleDttm)
	{
		this.etrScheduleDttm = etrScheduleDttm;
	}

	public String getEtrName()
	{
		return etrName;
	}

	public void setEtrName(String etrName)
	{
		this.etrName = etrName;
	}

	public DateTime getEtrFromDttm()
	{
		return etrFromDttm;
	}

	public void setEtrFromDttm(DateTime etrFromDttm)
	{
		this.etrFromDttm = etrFromDttm;
	}

	public DateTime getEtrToDttm()
	{
		return etrToDttm;
	}

	public void setEtrToDttm(DateTime etrToDttm)
	{
		this.etrToDttm = etrToDttm;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getEtrBk()
	{
		return etrBk;
	}

	public void setEtrBk(String etrBk)
	{
		this.etrBk = etrBk;
	}

	public Integer getEtrCreatedUsrId()
	{
		return etrCreatedUsrId;
	}

	public void setEtrCreatedUsrId(Integer etrCreatedUsrId)
	{
		this.etrCreatedUsrId = etrCreatedUsrId;
	}

	public Integer getEtrModifiedUsrId()
	{
		return etrModifiedUsrId;
	}

	public void setEtrModifiedUsrId(Integer etrModifiedUsrId)
	{
		this.etrModifiedUsrId = etrModifiedUsrId;
	}

	public DateTime getEtrCreatedDttm()
	{
		return etrCreatedDttm;
	}

	public void setEtrCreatedDttm(DateTime etrCreatedDttm)
	{
		this.etrCreatedDttm = etrCreatedDttm;
	}

	public DateTime getEtrModifiedDttm()
	{
		return etrModifiedDttm;
	}

	public void setEtrModifiedDttm(DateTime etrModifiedDttm)
	{
		this.etrModifiedDttm = etrModifiedDttm;
	}

}
