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

public abstract class Runner extends SignatureDbo
{
	private Integer runId;
	private String runName;
	private String runType;
	private String runPackage;
	private String runBk;
	private Integer runCreatedUsrId;
	private Integer runModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime runCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime runModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			runId = getInteger(rs.getInt("run_id"));
			runName = rs.getString("run_name");
			runType = rs.getString("run_type");
			runPackage = rs.getString("run_package");
			runBk = rs.getString("run_bk");
			runCreatedUsrId = getInteger(rs.getInt("run_created_usr_id"));
			runModifiedUsrId = getInteger(rs.getInt("run_modified_usr_id"));
			runCreatedDttm = getDateTime(rs.getTimestamp("run_created_dttm"));
			runModifiedDttm = getDateTime(rs.getTimestamp("run_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return runId;
	}

	public void setPk(int pk)
	{
		runId = pk;
	}

	public String getBk()
	{
		return runBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(runId.toString());
		data.add(runName.toString());
		data.add(runType.toString());
		data.add(runPackage.toString());
		data.add(getRunCreatedUsr());
		data.add(Constants.dttf.print(runCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("runName", getString(runName));
		data.put("runType", getString(runType));
		data.put("runPackage", getString(runPackage));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setRunName(input.getString("runName"));
		setRunType(input.getString("runType"));
		setRunPackage(input.getString("runPackage"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(runBk.toString());
		auditData.add(getRunCreatedUsr());
		auditData.add(runModifiedUsrId == null ? Constants.NULLSTRING : getRunModifiedUsr());
		auditData.add(Constants.dttf.print(runCreatedDttm));
		auditData.add(runModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(runModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return runName;
	}

	public String getRunCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, runCreatedUsrId);
	}

	public String getRunModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, runModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("runCreatedUsr"))
			return getRunCreatedUsr();
		if (propertyName.equals("runModifiedUsr"))
			return getRunModifiedUsr();
		if (propertyName.equals("runId"))
			return getRunId();
		if (propertyName.equals("runName"))
			return getRunName();
		if (propertyName.equals("runType"))
			return getRunType();
		if (propertyName.equals("runPackage"))
			return getRunPackage();
		if (propertyName.equals("runBk"))
			return getRunBk();
		if (propertyName.equals("runCreatedUsrId"))
			return getRunCreatedUsrId();
		if (propertyName.equals("runModifiedUsrId"))
			return getRunModifiedUsrId();
		if (propertyName.equals("runCreatedDttm"))
			return getRunCreatedDttm();
		if (propertyName.equals("runModifiedDttm"))
			return getRunModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getRunId()
	{
		return runId;
	}

	public void setRunId(Integer runId)
	{
		this.runId = runId;
	}

	public String getRunName()
	{
		return runName;
	}

	public void setRunName(String runName)
	{
		this.runName = runName;
	}

	public String getRunType()
	{
		return runType;
	}

	public void setRunType(String runType)
	{
		this.runType = runType;
	}

	public String getRunPackage()
	{
		return runPackage;
	}

	public void setRunPackage(String runPackage)
	{
		this.runPackage = runPackage;
	}

	public String getRunBk()
	{
		return runBk;
	}

	public void setRunBk(String runBk)
	{
		this.runBk = runBk;
	}

	public Integer getRunCreatedUsrId()
	{
		return runCreatedUsrId;
	}

	public void setRunCreatedUsrId(Integer runCreatedUsrId)
	{
		this.runCreatedUsrId = runCreatedUsrId;
	}

	public Integer getRunModifiedUsrId()
	{
		return runModifiedUsrId;
	}

	public void setRunModifiedUsrId(Integer runModifiedUsrId)
	{
		this.runModifiedUsrId = runModifiedUsrId;
	}

	public DateTime getRunCreatedDttm()
	{
		return runCreatedDttm;
	}

	public void setRunCreatedDttm(DateTime runCreatedDttm)
	{
		this.runCreatedDttm = runCreatedDttm;
	}

	public DateTime getRunModifiedDttm()
	{
		return runModifiedDttm;
	}

	public void setRunModifiedDttm(DateTime runModifiedDttm)
	{
		this.runModifiedDttm = runModifiedDttm;
	}

}
