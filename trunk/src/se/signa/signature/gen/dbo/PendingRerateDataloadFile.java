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
import se.signa.signature.gen.dba.UserTblDba;
import se.signa.signature.helpers.DisplayFieldHelper;

public abstract class PendingRerateDataloadFile extends SignatureDbo
{
	private Integer preId;
	private Integer filId;
	private Integer accId;
	private String preTableName;
	private boolean prePollFl;
	private Integer jobId;
	private String preName;
	private String preBk;
	private Integer preCreatedUsrId;
	private Integer preModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime preCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime preModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			preId = getInteger(rs.getInt("pre_id"));
			filId = getInteger(rs.getInt("fil_id"));
			accId = getInteger(rs.getInt("acc_id"));
			preTableName = rs.getString("pre_table_name");
			prePollFl = getBoolean(rs.getString("pre_poll_fl"));
			jobId = getInteger(rs.getInt("job_id"));
			preName = rs.getString("pre_name");
			preBk = rs.getString("pre_bk");
			preCreatedUsrId = getInteger(rs.getInt("pre_created_usr_id"));
			preModifiedUsrId = getInteger(rs.getInt("pre_modified_usr_id"));
			preCreatedDttm = getDateTime(rs.getTimestamp("pre_created_dttm"));
			preModifiedDttm = getDateTime(rs.getTimestamp("pre_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return preId;
	}

	public void setPk(int pk)
	{
		preId = pk;
	}

	public String getBk()
	{
		return preBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(preId.toString());
		data.add(filId.toString());
		data.add(accId.toString());
		data.add(preTableName.toString());
		data.add(String.valueOf(prePollFl));
		data.add(jobId.toString());
		data.add(preName.toString());
		data.add(getPreCreatedUsr());
		data.add(Constants.dttf.print(preCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("fil", getFil());
		data.put("accId", getAccId());
		data.put("preTableName", getString(preTableName));
		data.put("prePollFl", getString(prePollFl));
		data.put("job", getJob());
		data.put("preName", getString(preName));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setFilId(DisplayFieldHelper.getI().getPk(FiletblDba.class, input.getString("fil")));
		setPreTableName(input.getString("preTableName"));
		setPrePollFl(input.getBoolean("prePollFl"));
		setJobId(DisplayFieldHelper.getI().getPk(JobDba.class, input.getString("job")));
		setPreName(input.getString("preName"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(preBk.toString());
		auditData.add(getPreCreatedUsr());
		auditData.add(preModifiedUsrId == null ? Constants.NULLSTRING : getPreModifiedUsr());
		auditData.add(Constants.dttf.print(preCreatedDttm));
		auditData.add(preModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(preModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return preName;
	}

	public String getFil()
	{
		return DisplayFieldHelper.getI().getDisplayField(FiletblDba.class, filId);
	}

	public String getJob()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobDba.class, jobId);
	}

	public String getPreCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, preCreatedUsrId);
	}

	public String getPreModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, preModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("fil"))
			return getFil();
		if (propertyName.equals("job"))
			return getJob();
		if (propertyName.equals("preCreatedUsr"))
			return getPreCreatedUsr();
		if (propertyName.equals("preModifiedUsr"))
			return getPreModifiedUsr();
		if (propertyName.equals("preId"))
			return getPreId();
		if (propertyName.equals("filId"))
			return getFilId();
		if (propertyName.equals("accId"))
			return getAccId();
		if (propertyName.equals("preTableName"))
			return getPreTableName();
		if (propertyName.equals("prePollFl"))
			return getPrePollFl();
		if (propertyName.equals("jobId"))
			return getJobId();
		if (propertyName.equals("preName"))
			return getPreName();
		if (propertyName.equals("preBk"))
			return getPreBk();
		if (propertyName.equals("preCreatedUsrId"))
			return getPreCreatedUsrId();
		if (propertyName.equals("preModifiedUsrId"))
			return getPreModifiedUsrId();
		if (propertyName.equals("preCreatedDttm"))
			return getPreCreatedDttm();
		if (propertyName.equals("preModifiedDttm"))
			return getPreModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getPreId()
	{
		return preId;
	}

	public void setPreId(Integer preId)
	{
		this.preId = preId;
	}

	public Integer getFilId()
	{
		return filId;
	}

	public void setFilId(Integer filId)
	{
		this.filId = filId;
	}

	public Integer getAccId()
	{
		return accId;
	}

	public void setAccId(Integer accId)
	{
		this.accId = accId;
	}

	public String getPreTableName()
	{
		return preTableName;
	}

	public void setPreTableName(String preTableName)
	{
		this.preTableName = preTableName;
	}

	public boolean getPrePollFl()
	{
		return prePollFl;
	}

	public void setPrePollFl(boolean prePollFl)
	{
		this.prePollFl = prePollFl;
	}

	public Integer getJobId()
	{
		return jobId;
	}

	public void setJobId(Integer jobId)
	{
		this.jobId = jobId;
	}

	public String getPreName()
	{
		return preName;
	}

	public void setPreName(String preName)
	{
		this.preName = preName;
	}

	public String getPreBk()
	{
		return preBk;
	}

	public void setPreBk(String preBk)
	{
		this.preBk = preBk;
	}

	public Integer getPreCreatedUsrId()
	{
		return preCreatedUsrId;
	}

	public void setPreCreatedUsrId(Integer preCreatedUsrId)
	{
		this.preCreatedUsrId = preCreatedUsrId;
	}

	public Integer getPreModifiedUsrId()
	{
		return preModifiedUsrId;
	}

	public void setPreModifiedUsrId(Integer preModifiedUsrId)
	{
		this.preModifiedUsrId = preModifiedUsrId;
	}

	public DateTime getPreCreatedDttm()
	{
		return preCreatedDttm;
	}

	public void setPreCreatedDttm(DateTime preCreatedDttm)
	{
		this.preCreatedDttm = preCreatedDttm;
	}

	public DateTime getPreModifiedDttm()
	{
		return preModifiedDttm;
	}

	public void setPreModifiedDttm(DateTime preModifiedDttm)
	{
		this.preModifiedDttm = preModifiedDttm;
	}

}
