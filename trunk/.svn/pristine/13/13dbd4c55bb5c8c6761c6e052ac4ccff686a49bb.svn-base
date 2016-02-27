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
import se.signa.signature.gen.dba.JobTypeDba;
import se.signa.signature.gen.dba.NodeDba;
import se.signa.signature.gen.dba.UserTblDba;
import se.signa.signature.helpers.DisplayFieldHelper;

public abstract class Job extends SignatureDbo
{
	private Integer jobId;
	private Integer jbtId;
	private Integer jobParentJobId;
	private Integer nodId;
	private String jobName;
	private String jobStatus;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime jobStartDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime jobEndDttm;
	private Integer jobRetryCount;
	private String jobExtra1;
	private String jobExtra2;
	private String jobExtra3;
	private Integer jobPriority;
	private String jobBk;
	private Integer jobCreatedUsrId;
	private Integer jobModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime jobCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime jobModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			jobId = getInteger(rs.getInt("job_id"));
			jbtId = getInteger(rs.getInt("jbt_id"));
			jobParentJobId = getInteger(rs.getInt("job_parent_job_id"));
			nodId = getInteger(rs.getInt("nod_id"));
			jobName = rs.getString("job_name");
			jobStatus = rs.getString("job_status");
			jobStartDttm = getDateTime(rs.getTimestamp("job_start_dttm"));
			jobEndDttm = getDateTime(rs.getTimestamp("job_end_dttm"));
			jobRetryCount = rs.getInt("job_retry_count");
			jobExtra1 = rs.getString("job_extra1");
			jobExtra2 = rs.getString("job_extra2");
			jobExtra3 = rs.getString("job_extra3");
			jobPriority = rs.getInt("job_priority");
			jobBk = rs.getString("job_bk");
			jobCreatedUsrId = getInteger(rs.getInt("job_created_usr_id"));
			jobModifiedUsrId = getInteger(rs.getInt("job_modified_usr_id"));
			jobCreatedDttm = getDateTime(rs.getTimestamp("job_created_dttm"));
			jobModifiedDttm = getDateTime(rs.getTimestamp("job_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return jobId;
	}

	public void setPk(int pk)
	{
		jobId = pk;
	}

	public String getBk()
	{
		return jobBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(jobId.toString());
		data.add(jbtId.toString());
		data.add(jobParentJobId == null ? Constants.NULLSTRING : jobParentJobId.toString());
		data.add(nodId == null ? Constants.NULLSTRING : nodId.toString());
		data.add(jobName.toString());
		data.add(jobStatus.toString());
		data.add(jobStartDttm == null ? Constants.NULLSTRING : Constants.dttf.print(jobStartDttm));
		data.add(jobEndDttm == null ? Constants.NULLSTRING : Constants.dttf.print(jobEndDttm));
		data.add(jobRetryCount.toString());
		data.add(jobExtra1 == null ? Constants.NULLSTRING : jobExtra1.toString());
		data.add(jobExtra2 == null ? Constants.NULLSTRING : jobExtra2.toString());
		data.add(jobExtra3 == null ? Constants.NULLSTRING : jobExtra3.toString());
		data.add(jobPriority.toString());
		data.add(getJobCreatedUsr());
		data.add(Constants.dttf.print(jobCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("jbt", getJbt());
		data.put("jobParentJob", getJobParentJob());
		data.put("nod", getNod());
		data.put("jobName", getString(jobName));
		data.put("jobStatus", getString(jobStatus));
		data.put("jobStartDttm", Constants.dttf.print(jobStartDttm));
		data.put("jobEndDttm", Constants.dttf.print(jobEndDttm));
		data.put("jobRetryCount", getString(jobRetryCount));
		data.put("jobExtra1", getString(jobExtra1));
		data.put("jobExtra2", getString(jobExtra2));
		data.put("jobExtra3", getString(jobExtra3));
		data.put("jobPriority", getString(jobPriority));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("jbt")));
		setJobParentJobId(DisplayFieldHelper.getI().getPk(JobDba.class, input.getString("jobParentJob")));
		setNodId(DisplayFieldHelper.getI().getPk(NodeDba.class, input.getString("nod")));
		setJobName(input.getString("jobName"));
		setJobStatus(input.getString("jobStatus"));
		setJobStartDttm(input.getDate("jobStartDttm"));
		setJobEndDttm(input.getDate("jobEndDttm"));
		setJobRetryCount(input.getInteger("jobRetryCount"));
		setJobExtra1(input.getString("jobExtra1"));
		setJobExtra2(input.getString("jobExtra2"));
		setJobExtra3(input.getString("jobExtra3"));
		setJobPriority(input.getInteger("jobPriority"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(jobBk.toString());
		auditData.add(getJobCreatedUsr());
		auditData.add(jobModifiedUsrId == null ? Constants.NULLSTRING : getJobModifiedUsr());
		auditData.add(Constants.dttf.print(jobCreatedDttm));
		auditData.add(jobModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(jobModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return jobName;
	}

	public String getJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, jbtId);
	}

	public String getJobParentJob()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobDba.class, jobParentJobId);
	}

	public String getNod()
	{
		return DisplayFieldHelper.getI().getDisplayField(NodeDba.class, nodId);
	}

	public String getJobCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, jobCreatedUsrId);
	}

	public String getJobModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, jobModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("jbt"))
			return getJbt();
		if (propertyName.equals("jobParentJob"))
			return getJobParentJob();
		if (propertyName.equals("nod"))
			return getNod();
		if (propertyName.equals("jobCreatedUsr"))
			return getJobCreatedUsr();
		if (propertyName.equals("jobModifiedUsr"))
			return getJobModifiedUsr();
		if (propertyName.equals("jobId"))
			return getJobId();
		if (propertyName.equals("jbtId"))
			return getJbtId();
		if (propertyName.equals("jobParentJobId"))
			return getJobParentJobId();
		if (propertyName.equals("nodId"))
			return getNodId();
		if (propertyName.equals("jobName"))
			return getJobName();
		if (propertyName.equals("jobStatus"))
			return getJobStatus();
		if (propertyName.equals("jobStartDttm"))
			return getJobStartDttm();
		if (propertyName.equals("jobEndDttm"))
			return getJobEndDttm();
		if (propertyName.equals("jobRetryCount"))
			return getJobRetryCount();
		if (propertyName.equals("jobExtra1"))
			return getJobExtra1();
		if (propertyName.equals("jobExtra2"))
			return getJobExtra2();
		if (propertyName.equals("jobExtra3"))
			return getJobExtra3();
		if (propertyName.equals("jobPriority"))
			return getJobPriority();
		if (propertyName.equals("jobBk"))
			return getJobBk();
		if (propertyName.equals("jobCreatedUsrId"))
			return getJobCreatedUsrId();
		if (propertyName.equals("jobModifiedUsrId"))
			return getJobModifiedUsrId();
		if (propertyName.equals("jobCreatedDttm"))
			return getJobCreatedDttm();
		if (propertyName.equals("jobModifiedDttm"))
			return getJobModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
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

	public Integer getJobParentJobId()
	{
		return jobParentJobId;
	}

	public void setJobParentJobId(Integer jobParentJobId)
	{
		this.jobParentJobId = jobParentJobId;
	}

	public Integer getNodId()
	{
		return nodId;
	}

	public void setNodId(Integer nodId)
	{
		this.nodId = nodId;
	}

	public String getJobName()
	{
		return jobName;
	}

	public void setJobName(String jobName)
	{
		this.jobName = jobName;
	}

	public String getJobStatus()
	{
		return jobStatus;
	}

	public void setJobStatus(String jobStatus)
	{
		this.jobStatus = jobStatus;
	}

	public DateTime getJobStartDttm()
	{
		return jobStartDttm;
	}

	public void setJobStartDttm(DateTime jobStartDttm)
	{
		this.jobStartDttm = jobStartDttm;
	}

	public DateTime getJobEndDttm()
	{
		return jobEndDttm;
	}

	public void setJobEndDttm(DateTime jobEndDttm)
	{
		this.jobEndDttm = jobEndDttm;
	}

	public Integer getJobRetryCount()
	{
		return jobRetryCount;
	}

	public void setJobRetryCount(Integer jobRetryCount)
	{
		this.jobRetryCount = jobRetryCount;
	}

	public String getJobExtra1()
	{
		return jobExtra1;
	}

	public void setJobExtra1(String jobExtra1)
	{
		this.jobExtra1 = jobExtra1;
	}

	public String getJobExtra2()
	{
		return jobExtra2;
	}

	public void setJobExtra2(String jobExtra2)
	{
		this.jobExtra2 = jobExtra2;
	}

	public String getJobExtra3()
	{
		return jobExtra3;
	}

	public void setJobExtra3(String jobExtra3)
	{
		this.jobExtra3 = jobExtra3;
	}

	public Integer getJobPriority()
	{
		return jobPriority;
	}

	public void setJobPriority(Integer jobPriority)
	{
		this.jobPriority = jobPriority;
	}

	public String getJobBk()
	{
		return jobBk;
	}

	public void setJobBk(String jobBk)
	{
		this.jobBk = jobBk;
	}

	public Integer getJobCreatedUsrId()
	{
		return jobCreatedUsrId;
	}

	public void setJobCreatedUsrId(Integer jobCreatedUsrId)
	{
		this.jobCreatedUsrId = jobCreatedUsrId;
	}

	public Integer getJobModifiedUsrId()
	{
		return jobModifiedUsrId;
	}

	public void setJobModifiedUsrId(Integer jobModifiedUsrId)
	{
		this.jobModifiedUsrId = jobModifiedUsrId;
	}

	public DateTime getJobCreatedDttm()
	{
		return jobCreatedDttm;
	}

	public void setJobCreatedDttm(DateTime jobCreatedDttm)
	{
		this.jobCreatedDttm = jobCreatedDttm;
	}

	public DateTime getJobModifiedDttm()
	{
		return jobModifiedDttm;
	}

	public void setJobModifiedDttm(DateTime jobModifiedDttm)
	{
		this.jobModifiedDttm = jobModifiedDttm;
	}

}
