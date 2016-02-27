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
import se.signa.signature.gen.dba.UserTblDba;
import se.signa.signature.helpers.DisplayFieldHelper;

public abstract class PendingJob extends SignatureDbo
{
	private Integer pjbId;
	private Integer jobId;
	private Integer jbtId;
	private String pjbName;
	private String pjbExtra1;
	private String pjbExtra2;
	private String pjbExtra3;
	private Integer pjbPriority;
	private String pjbBk;
	private Integer pjbCreatedUsrId;
	private Integer pjbModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime pjbCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime pjbModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			pjbId = getInteger(rs.getInt("pjb_id"));
			jobId = getInteger(rs.getInt("job_id"));
			jbtId = getInteger(rs.getInt("jbt_id"));
			pjbName = rs.getString("pjb_name");
			pjbExtra1 = rs.getString("pjb_extra1");
			pjbExtra2 = rs.getString("pjb_extra2");
			pjbExtra3 = rs.getString("pjb_extra3");
			pjbPriority = rs.getInt("pjb_priority");
			pjbBk = rs.getString("pjb_bk");
			pjbCreatedUsrId = getInteger(rs.getInt("pjb_created_usr_id"));
			pjbModifiedUsrId = getInteger(rs.getInt("pjb_modified_usr_id"));
			pjbCreatedDttm = getDateTime(rs.getTimestamp("pjb_created_dttm"));
			pjbModifiedDttm = getDateTime(rs.getTimestamp("pjb_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return pjbId;
	}

	public void setPk(int pk)
	{
		pjbId = pk;
	}

	public String getBk()
	{
		return pjbBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(pjbId.toString());
		data.add(jobId.toString());
		data.add(jbtId.toString());
		data.add(pjbName.toString());
		data.add(pjbExtra1 == null ? Constants.NULLSTRING : pjbExtra1.toString());
		data.add(pjbExtra2 == null ? Constants.NULLSTRING : pjbExtra2.toString());
		data.add(pjbExtra3 == null ? Constants.NULLSTRING : pjbExtra3.toString());
		data.add(pjbPriority.toString());
		data.add(getPjbCreatedUsr());
		data.add(Constants.dttf.print(pjbCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("job", getJob());
		data.put("jbt", getJbt());
		data.put("pjbName", getString(pjbName));
		data.put("pjbExtra1", getString(pjbExtra1));
		data.put("pjbExtra2", getString(pjbExtra2));
		data.put("pjbExtra3", getString(pjbExtra3));
		data.put("pjbPriority", getString(pjbPriority));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setJobId(DisplayFieldHelper.getI().getPk(JobDba.class, input.getString("job")));
		setJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("jbt")));
		setPjbName(input.getString("pjbName"));
		setPjbExtra1(input.getString("pjbExtra1"));
		setPjbExtra2(input.getString("pjbExtra2"));
		setPjbExtra3(input.getString("pjbExtra3"));
		setPjbPriority(input.getInteger("pjbPriority"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(pjbBk.toString());
		auditData.add(getPjbCreatedUsr());
		auditData.add(pjbModifiedUsrId == null ? Constants.NULLSTRING : getPjbModifiedUsr());
		auditData.add(Constants.dttf.print(pjbCreatedDttm));
		auditData.add(pjbModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(pjbModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return pjbName;
	}

	public String getJob()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobDba.class, jobId);
	}

	public String getJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, jbtId);
	}

	public String getPjbCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, pjbCreatedUsrId);
	}

	public String getPjbModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, pjbModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("job"))
			return getJob();
		if (propertyName.equals("jbt"))
			return getJbt();
		if (propertyName.equals("pjbCreatedUsr"))
			return getPjbCreatedUsr();
		if (propertyName.equals("pjbModifiedUsr"))
			return getPjbModifiedUsr();
		if (propertyName.equals("pjbId"))
			return getPjbId();
		if (propertyName.equals("jobId"))
			return getJobId();
		if (propertyName.equals("jbtId"))
			return getJbtId();
		if (propertyName.equals("pjbName"))
			return getPjbName();
		if (propertyName.equals("pjbExtra1"))
			return getPjbExtra1();
		if (propertyName.equals("pjbExtra2"))
			return getPjbExtra2();
		if (propertyName.equals("pjbExtra3"))
			return getPjbExtra3();
		if (propertyName.equals("pjbPriority"))
			return getPjbPriority();
		if (propertyName.equals("pjbBk"))
			return getPjbBk();
		if (propertyName.equals("pjbCreatedUsrId"))
			return getPjbCreatedUsrId();
		if (propertyName.equals("pjbModifiedUsrId"))
			return getPjbModifiedUsrId();
		if (propertyName.equals("pjbCreatedDttm"))
			return getPjbCreatedDttm();
		if (propertyName.equals("pjbModifiedDttm"))
			return getPjbModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getPjbId()
	{
		return pjbId;
	}

	public void setPjbId(Integer pjbId)
	{
		this.pjbId = pjbId;
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

	public String getPjbName()
	{
		return pjbName;
	}

	public void setPjbName(String pjbName)
	{
		this.pjbName = pjbName;
	}

	public String getPjbExtra1()
	{
		return pjbExtra1;
	}

	public void setPjbExtra1(String pjbExtra1)
	{
		this.pjbExtra1 = pjbExtra1;
	}

	public String getPjbExtra2()
	{
		return pjbExtra2;
	}

	public void setPjbExtra2(String pjbExtra2)
	{
		this.pjbExtra2 = pjbExtra2;
	}

	public String getPjbExtra3()
	{
		return pjbExtra3;
	}

	public void setPjbExtra3(String pjbExtra3)
	{
		this.pjbExtra3 = pjbExtra3;
	}

	public Integer getPjbPriority()
	{
		return pjbPriority;
	}

	public void setPjbPriority(Integer pjbPriority)
	{
		this.pjbPriority = pjbPriority;
	}

	public String getPjbBk()
	{
		return pjbBk;
	}

	public void setPjbBk(String pjbBk)
	{
		this.pjbBk = pjbBk;
	}

	public Integer getPjbCreatedUsrId()
	{
		return pjbCreatedUsrId;
	}

	public void setPjbCreatedUsrId(Integer pjbCreatedUsrId)
	{
		this.pjbCreatedUsrId = pjbCreatedUsrId;
	}

	public Integer getPjbModifiedUsrId()
	{
		return pjbModifiedUsrId;
	}

	public void setPjbModifiedUsrId(Integer pjbModifiedUsrId)
	{
		this.pjbModifiedUsrId = pjbModifiedUsrId;
	}

	public DateTime getPjbCreatedDttm()
	{
		return pjbCreatedDttm;
	}

	public void setPjbCreatedDttm(DateTime pjbCreatedDttm)
	{
		this.pjbCreatedDttm = pjbCreatedDttm;
	}

	public DateTime getPjbModifiedDttm()
	{
		return pjbModifiedDttm;
	}

	public void setPjbModifiedDttm(DateTime pjbModifiedDttm)
	{
		this.pjbModifiedDttm = pjbModifiedDttm;
	}

}
