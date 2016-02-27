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

public abstract class PendingCc extends SignatureDbo
{
	private Integer pccId;
	private String pccName;
	private String pccTableName;
	private boolean pccPollFl;
	private Integer jobId;
	private String pccBk;
	private Integer pccCreatedUsrId;
	private Integer pccModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime pccCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime pccModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			pccId = getInteger(rs.getInt("pcc_id"));
			pccName = rs.getString("pcc_name");
			pccTableName = rs.getString("pcc_table_name");
			pccPollFl = getBoolean(rs.getString("pcc_poll_fl"));
			jobId = getInteger(rs.getInt("job_id"));
			pccBk = rs.getString("pcc_bk");
			pccCreatedUsrId = getInteger(rs.getInt("pcc_created_usr_id"));
			pccModifiedUsrId = getInteger(rs.getInt("pcc_modified_usr_id"));
			pccCreatedDttm = getDateTime(rs.getTimestamp("pcc_created_dttm"));
			pccModifiedDttm = getDateTime(rs.getTimestamp("pcc_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return pccId;
	}

	public void setPk(int pk)
	{
		pccId = pk;
	}

	public String getBk()
	{
		return pccBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(pccId.toString());
		data.add(pccName.toString());
		data.add(pccTableName.toString());
		data.add(String.valueOf(pccPollFl));
		data.add(jobId == null ? Constants.NULLSTRING : jobId.toString());
		data.add(getPccCreatedUsr());
		data.add(Constants.dttf.print(pccCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("pccName", getString(pccName));
		data.put("pccTableName", getString(pccTableName));
		data.put("pccPollFl", getString(pccPollFl));
		data.put("job", getJob());
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setPccName(input.getString("pccName"));
		setPccTableName(input.getString("pccTableName"));
		setPccPollFl(input.getBoolean("pccPollFl"));
		setJobId(DisplayFieldHelper.getI().getPk(JobDba.class, input.getString("job")));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(pccBk.toString());
		auditData.add(getPccCreatedUsr());
		auditData.add(pccModifiedUsrId == null ? Constants.NULLSTRING : getPccModifiedUsr());
		auditData.add(Constants.dttf.print(pccCreatedDttm));
		auditData.add(pccModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(pccModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return pccName;
	}

	public String getJob()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobDba.class, jobId);
	}

	public String getPccCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, pccCreatedUsrId);
	}

	public String getPccModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, pccModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("job"))
			return getJob();
		if (propertyName.equals("pccCreatedUsr"))
			return getPccCreatedUsr();
		if (propertyName.equals("pccModifiedUsr"))
			return getPccModifiedUsr();
		if (propertyName.equals("pccId"))
			return getPccId();
		if (propertyName.equals("pccName"))
			return getPccName();
		if (propertyName.equals("pccTableName"))
			return getPccTableName();
		if (propertyName.equals("pccPollFl"))
			return getPccPollFl();
		if (propertyName.equals("jobId"))
			return getJobId();
		if (propertyName.equals("pccBk"))
			return getPccBk();
		if (propertyName.equals("pccCreatedUsrId"))
			return getPccCreatedUsrId();
		if (propertyName.equals("pccModifiedUsrId"))
			return getPccModifiedUsrId();
		if (propertyName.equals("pccCreatedDttm"))
			return getPccCreatedDttm();
		if (propertyName.equals("pccModifiedDttm"))
			return getPccModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getPccId()
	{
		return pccId;
	}

	public void setPccId(Integer pccId)
	{
		this.pccId = pccId;
	}

	public String getPccName()
	{
		return pccName;
	}

	public void setPccName(String pccName)
	{
		this.pccName = pccName;
	}

	public String getPccTableName()
	{
		return pccTableName;
	}

	public void setPccTableName(String pccTableName)
	{
		this.pccTableName = pccTableName;
	}

	public boolean getPccPollFl()
	{
		return pccPollFl;
	}

	public void setPccPollFl(boolean pccPollFl)
	{
		this.pccPollFl = pccPollFl;
	}

	public Integer getJobId()
	{
		return jobId;
	}

	public void setJobId(Integer jobId)
	{
		this.jobId = jobId;
	}

	public String getPccBk()
	{
		return pccBk;
	}

	public void setPccBk(String pccBk)
	{
		this.pccBk = pccBk;
	}

	public Integer getPccCreatedUsrId()
	{
		return pccCreatedUsrId;
	}

	public void setPccCreatedUsrId(Integer pccCreatedUsrId)
	{
		this.pccCreatedUsrId = pccCreatedUsrId;
	}

	public Integer getPccModifiedUsrId()
	{
		return pccModifiedUsrId;
	}

	public void setPccModifiedUsrId(Integer pccModifiedUsrId)
	{
		this.pccModifiedUsrId = pccModifiedUsrId;
	}

	public DateTime getPccCreatedDttm()
	{
		return pccCreatedDttm;
	}

	public void setPccCreatedDttm(DateTime pccCreatedDttm)
	{
		this.pccCreatedDttm = pccCreatedDttm;
	}

	public DateTime getPccModifiedDttm()
	{
		return pccModifiedDttm;
	}

	public void setPccModifiedDttm(DateTime pccModifiedDttm)
	{
		this.pccModifiedDttm = pccModifiedDttm;
	}

}
