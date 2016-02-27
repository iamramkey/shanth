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

public abstract class Filetbl extends SignatureDbo
{
	private Integer filId;
	private String filPath;
	private String filName;
	private String filIdentifier;
	private String filType;
	private Integer jobId;
	private String filExtra1;
	private String filBk;
	private Integer filCreatedUsrId;
	private Integer filModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime filCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime filModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			filId = getInteger(rs.getInt("fil_id"));
			filPath = rs.getString("fil_path");
			filName = rs.getString("fil_name");
			filIdentifier = rs.getString("fil_identifier");
			filType = rs.getString("fil_type");
			jobId = getInteger(rs.getInt("job_id"));
			filExtra1 = rs.getString("fil_extra1");
			filBk = rs.getString("fil_bk");
			filCreatedUsrId = getInteger(rs.getInt("fil_created_usr_id"));
			filModifiedUsrId = getInteger(rs.getInt("fil_modified_usr_id"));
			filCreatedDttm = getDateTime(rs.getTimestamp("fil_created_dttm"));
			filModifiedDttm = getDateTime(rs.getTimestamp("fil_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return filId;
	}

	public void setPk(int pk)
	{
		filId = pk;
	}

	public String getBk()
	{
		return filBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(filId.toString());
		data.add(filPath.toString());
		data.add(filName.toString());
		data.add(filIdentifier.toString());
		data.add(filType.toString());
		data.add(jobId == null ? Constants.NULLSTRING : jobId.toString());
		data.add(filExtra1 == null ? Constants.NULLSTRING : filExtra1.toString());
		data.add(getFilCreatedUsr());
		data.add(Constants.dttf.print(filCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("filPath", getString(filPath));
		data.put("filName", getString(filName));
		data.put("filIdentifier", getString(filIdentifier));
		data.put("filType", getString(filType));
		data.put("job", getJob());
		data.put("filExtra1", getString(filExtra1));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setFilPath(input.getString("filPath"));
		setFilName(input.getString("filName"));
		setFilIdentifier(input.getString("filIdentifier"));
		setFilType(input.getString("filType"));
		setJobId(DisplayFieldHelper.getI().getPk(JobDba.class, input.getString("job")));
		setFilExtra1(input.getString("filExtra1"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(filBk.toString());
		auditData.add(getFilCreatedUsr());
		auditData.add(filModifiedUsrId == null ? Constants.NULLSTRING : getFilModifiedUsr());
		auditData.add(Constants.dttf.print(filCreatedDttm));
		auditData.add(filModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(filModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return filIdentifier;
	}

	public String getJob()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobDba.class, jobId);
	}

	public String getFilCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, filCreatedUsrId);
	}

	public String getFilModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, filModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("job"))
			return getJob();
		if (propertyName.equals("filCreatedUsr"))
			return getFilCreatedUsr();
		if (propertyName.equals("filModifiedUsr"))
			return getFilModifiedUsr();
		if (propertyName.equals("filId"))
			return getFilId();
		if (propertyName.equals("filPath"))
			return getFilPath();
		if (propertyName.equals("filName"))
			return getFilName();
		if (propertyName.equals("filIdentifier"))
			return getFilIdentifier();
		if (propertyName.equals("filType"))
			return getFilType();
		if (propertyName.equals("jobId"))
			return getJobId();
		if (propertyName.equals("filExtra1"))
			return getFilExtra1();
		if (propertyName.equals("filBk"))
			return getFilBk();
		if (propertyName.equals("filCreatedUsrId"))
			return getFilCreatedUsrId();
		if (propertyName.equals("filModifiedUsrId"))
			return getFilModifiedUsrId();
		if (propertyName.equals("filCreatedDttm"))
			return getFilCreatedDttm();
		if (propertyName.equals("filModifiedDttm"))
			return getFilModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getFilId()
	{
		return filId;
	}

	public void setFilId(Integer filId)
	{
		this.filId = filId;
	}

	public String getFilPath()
	{
		return filPath;
	}

	public void setFilPath(String filPath)
	{
		this.filPath = filPath;
	}

	public String getFilName()
	{
		return filName;
	}

	public void setFilName(String filName)
	{
		this.filName = filName;
	}

	public String getFilIdentifier()
	{
		return filIdentifier;
	}

	public void setFilIdentifier(String filIdentifier)
	{
		this.filIdentifier = filIdentifier;
	}

	public String getFilType()
	{
		return filType;
	}

	public void setFilType(String filType)
	{
		this.filType = filType;
	}

	public Integer getJobId()
	{
		return jobId;
	}

	public void setJobId(Integer jobId)
	{
		this.jobId = jobId;
	}

	public String getFilExtra1()
	{
		return filExtra1;
	}

	public void setFilExtra1(String filExtra1)
	{
		this.filExtra1 = filExtra1;
	}

	public String getFilBk()
	{
		return filBk;
	}

	public void setFilBk(String filBk)
	{
		this.filBk = filBk;
	}

	public Integer getFilCreatedUsrId()
	{
		return filCreatedUsrId;
	}

	public void setFilCreatedUsrId(Integer filCreatedUsrId)
	{
		this.filCreatedUsrId = filCreatedUsrId;
	}

	public Integer getFilModifiedUsrId()
	{
		return filModifiedUsrId;
	}

	public void setFilModifiedUsrId(Integer filModifiedUsrId)
	{
		this.filModifiedUsrId = filModifiedUsrId;
	}

	public DateTime getFilCreatedDttm()
	{
		return filCreatedDttm;
	}

	public void setFilCreatedDttm(DateTime filCreatedDttm)
	{
		this.filCreatedDttm = filCreatedDttm;
	}

	public DateTime getFilModifiedDttm()
	{
		return filModifiedDttm;
	}

	public void setFilModifiedDttm(DateTime filModifiedDttm)
	{
		this.filModifiedDttm = filModifiedDttm;
	}

}
