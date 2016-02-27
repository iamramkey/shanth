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
import se.signa.signature.gen.dba.RunnerDba;
import se.signa.signature.gen.dba.UserTblDba;
import se.signa.signature.helpers.DisplayFieldHelper;

public abstract class JobType extends SignatureDbo
{
	private Integer jbtId;
	private String jbtName;
	private String jbtType;
	private String jbtCode;
	private Integer runId;
	private Integer jbtPriority;
	private String jbtBk;
	private Integer jbtCreatedUsrId;
	private Integer jbtModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime jbtCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime jbtModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			jbtId = getInteger(rs.getInt("jbt_id"));
			jbtName = rs.getString("jbt_name");
			jbtType = rs.getString("jbt_type");
			jbtCode = rs.getString("jbt_code");
			runId = getInteger(rs.getInt("run_id"));
			jbtPriority = rs.getInt("jbt_priority");
			jbtBk = rs.getString("jbt_bk");
			jbtCreatedUsrId = getInteger(rs.getInt("jbt_created_usr_id"));
			jbtModifiedUsrId = getInteger(rs.getInt("jbt_modified_usr_id"));
			jbtCreatedDttm = getDateTime(rs.getTimestamp("jbt_created_dttm"));
			jbtModifiedDttm = getDateTime(rs.getTimestamp("jbt_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return jbtId;
	}

	public void setPk(int pk)
	{
		jbtId = pk;
	}

	public String getBk()
	{
		return jbtBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(jbtId.toString());
		data.add(jbtName.toString());
		data.add(jbtType.toString());
		data.add(jbtCode.toString());
		data.add(runId.toString());
		data.add(jbtPriority.toString());
		data.add(getJbtCreatedUsr());
		data.add(Constants.dttf.print(jbtCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("jbtName", getString(jbtName));
		data.put("jbtType", getString(jbtType));
		data.put("jbtCode", getString(jbtCode));
		data.put("run", getRun());
		data.put("jbtPriority", getString(jbtPriority));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setJbtName(input.getString("jbtName"));
		setJbtType(input.getString("jbtType"));
		setJbtCode(input.getString("jbtCode"));
		setRunId(DisplayFieldHelper.getI().getPk(RunnerDba.class, input.getString("run")));
		setJbtPriority(input.getInteger("jbtPriority"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(jbtBk.toString());
		auditData.add(getJbtCreatedUsr());
		auditData.add(jbtModifiedUsrId == null ? Constants.NULLSTRING : getJbtModifiedUsr());
		auditData.add(Constants.dttf.print(jbtCreatedDttm));
		auditData.add(jbtModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(jbtModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return jbtName;
	}

	public String getRun()
	{
		return DisplayFieldHelper.getI().getDisplayField(RunnerDba.class, runId);
	}

	public String getJbtCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, jbtCreatedUsrId);
	}

	public String getJbtModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, jbtModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("run"))
			return getRun();
		if (propertyName.equals("jbtCreatedUsr"))
			return getJbtCreatedUsr();
		if (propertyName.equals("jbtModifiedUsr"))
			return getJbtModifiedUsr();
		if (propertyName.equals("jbtId"))
			return getJbtId();
		if (propertyName.equals("jbtName"))
			return getJbtName();
		if (propertyName.equals("jbtType"))
			return getJbtType();
		if (propertyName.equals("jbtCode"))
			return getJbtCode();
		if (propertyName.equals("runId"))
			return getRunId();
		if (propertyName.equals("jbtPriority"))
			return getJbtPriority();
		if (propertyName.equals("jbtBk"))
			return getJbtBk();
		if (propertyName.equals("jbtCreatedUsrId"))
			return getJbtCreatedUsrId();
		if (propertyName.equals("jbtModifiedUsrId"))
			return getJbtModifiedUsrId();
		if (propertyName.equals("jbtCreatedDttm"))
			return getJbtCreatedDttm();
		if (propertyName.equals("jbtModifiedDttm"))
			return getJbtModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getJbtId()
	{
		return jbtId;
	}

	public void setJbtId(Integer jbtId)
	{
		this.jbtId = jbtId;
	}

	public String getJbtName()
	{
		return jbtName;
	}

	public void setJbtName(String jbtName)
	{
		this.jbtName = jbtName;
	}

	public String getJbtType()
	{
		return jbtType;
	}

	public void setJbtType(String jbtType)
	{
		this.jbtType = jbtType;
	}

	public String getJbtCode()
	{
		return jbtCode;
	}

	public void setJbtCode(String jbtCode)
	{
		this.jbtCode = jbtCode;
	}

	public Integer getRunId()
	{
		return runId;
	}

	public void setRunId(Integer runId)
	{
		this.runId = runId;
	}

	public Integer getJbtPriority()
	{
		return jbtPriority;
	}

	public void setJbtPriority(Integer jbtPriority)
	{
		this.jbtPriority = jbtPriority;
	}

	public String getJbtBk()
	{
		return jbtBk;
	}

	public void setJbtBk(String jbtBk)
	{
		this.jbtBk = jbtBk;
	}

	public Integer getJbtCreatedUsrId()
	{
		return jbtCreatedUsrId;
	}

	public void setJbtCreatedUsrId(Integer jbtCreatedUsrId)
	{
		this.jbtCreatedUsrId = jbtCreatedUsrId;
	}

	public Integer getJbtModifiedUsrId()
	{
		return jbtModifiedUsrId;
	}

	public void setJbtModifiedUsrId(Integer jbtModifiedUsrId)
	{
		this.jbtModifiedUsrId = jbtModifiedUsrId;
	}

	public DateTime getJbtCreatedDttm()
	{
		return jbtCreatedDttm;
	}

	public void setJbtCreatedDttm(DateTime jbtCreatedDttm)
	{
		this.jbtCreatedDttm = jbtCreatedDttm;
	}

	public DateTime getJbtModifiedDttm()
	{
		return jbtModifiedDttm;
	}

	public void setJbtModifiedDttm(DateTime jbtModifiedDttm)
	{
		this.jbtModifiedDttm = jbtModifiedDttm;
	}

}
