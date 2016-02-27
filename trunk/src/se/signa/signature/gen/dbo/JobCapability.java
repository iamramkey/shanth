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
import se.signa.signature.gen.dba.NodeDba;
import se.signa.signature.gen.dba.UserTblDba;
import se.signa.signature.helpers.DisplayFieldHelper;

public abstract class JobCapability extends SignatureDbo
{
	private Integer jbcId;
	private Integer jbtId;
	private Integer nodId;
	private String jbcName;
	private Integer jbcCount;
	private String jbcStatus;
	private String jbcBk;
	private Integer jbcCreatedUsrId;
	private Integer jbcModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime jbcCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime jbcModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			jbcId = getInteger(rs.getInt("jbc_id"));
			jbtId = getInteger(rs.getInt("jbt_id"));
			nodId = getInteger(rs.getInt("nod_id"));
			jbcName = rs.getString("jbc_name");
			jbcCount = rs.getInt("jbc_count");
			jbcStatus = rs.getString("jbc_status");
			jbcBk = rs.getString("jbc_bk");
			jbcCreatedUsrId = getInteger(rs.getInt("jbc_created_usr_id"));
			jbcModifiedUsrId = getInteger(rs.getInt("jbc_modified_usr_id"));
			jbcCreatedDttm = getDateTime(rs.getTimestamp("jbc_created_dttm"));
			jbcModifiedDttm = getDateTime(rs.getTimestamp("jbc_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return jbcId;
	}

	public void setPk(int pk)
	{
		jbcId = pk;
	}

	public String getBk()
	{
		return jbcBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(jbcId.toString());
		data.add(jbtId.toString());
		data.add(nodId.toString());
		data.add(jbcName.toString());
		data.add(jbcCount.toString());
		data.add(jbcStatus.toString());
		data.add(getJbcCreatedUsr());
		data.add(Constants.dttf.print(jbcCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("jbt", getJbt());
		data.put("nod", getNod());
		data.put("jbcName", getString(jbcName));
		data.put("jbcCount", getString(jbcCount));
		data.put("jbcStatus", getString(jbcStatus));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("jbt")));
		setNodId(DisplayFieldHelper.getI().getPk(NodeDba.class, input.getString("nod")));
		setJbcName(input.getString("jbcName"));
		setJbcCount(input.getInteger("jbcCount"));
		setJbcStatus(input.getString("jbcStatus"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(jbcBk.toString());
		auditData.add(getJbcCreatedUsr());
		auditData.add(jbcModifiedUsrId == null ? Constants.NULLSTRING : getJbcModifiedUsr());
		auditData.add(Constants.dttf.print(jbcCreatedDttm));
		auditData.add(jbcModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(jbcModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return jbcName;
	}

	public String getJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, jbtId);
	}

	public String getNod()
	{
		return DisplayFieldHelper.getI().getDisplayField(NodeDba.class, nodId);
	}

	public String getJbcCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, jbcCreatedUsrId);
	}

	public String getJbcModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, jbcModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("jbt"))
			return getJbt();
		if (propertyName.equals("nod"))
			return getNod();
		if (propertyName.equals("jbcCreatedUsr"))
			return getJbcCreatedUsr();
		if (propertyName.equals("jbcModifiedUsr"))
			return getJbcModifiedUsr();
		if (propertyName.equals("jbcId"))
			return getJbcId();
		if (propertyName.equals("jbtId"))
			return getJbtId();
		if (propertyName.equals("nodId"))
			return getNodId();
		if (propertyName.equals("jbcName"))
			return getJbcName();
		if (propertyName.equals("jbcCount"))
			return getJbcCount();
		if (propertyName.equals("jbcStatus"))
			return getJbcStatus();
		if (propertyName.equals("jbcBk"))
			return getJbcBk();
		if (propertyName.equals("jbcCreatedUsrId"))
			return getJbcCreatedUsrId();
		if (propertyName.equals("jbcModifiedUsrId"))
			return getJbcModifiedUsrId();
		if (propertyName.equals("jbcCreatedDttm"))
			return getJbcCreatedDttm();
		if (propertyName.equals("jbcModifiedDttm"))
			return getJbcModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getJbcId()
	{
		return jbcId;
	}

	public void setJbcId(Integer jbcId)
	{
		this.jbcId = jbcId;
	}

	public Integer getJbtId()
	{
		return jbtId;
	}

	public void setJbtId(Integer jbtId)
	{
		this.jbtId = jbtId;
	}

	public Integer getNodId()
	{
		return nodId;
	}

	public void setNodId(Integer nodId)
	{
		this.nodId = nodId;
	}

	public String getJbcName()
	{
		return jbcName;
	}

	public void setJbcName(String jbcName)
	{
		this.jbcName = jbcName;
	}

	public Integer getJbcCount()
	{
		return jbcCount;
	}

	public void setJbcCount(Integer jbcCount)
	{
		this.jbcCount = jbcCount;
	}

	public String getJbcStatus()
	{
		return jbcStatus;
	}

	public void setJbcStatus(String jbcStatus)
	{
		this.jbcStatus = jbcStatus;
	}

	public String getJbcBk()
	{
		return jbcBk;
	}

	public void setJbcBk(String jbcBk)
	{
		this.jbcBk = jbcBk;
	}

	public Integer getJbcCreatedUsrId()
	{
		return jbcCreatedUsrId;
	}

	public void setJbcCreatedUsrId(Integer jbcCreatedUsrId)
	{
		this.jbcCreatedUsrId = jbcCreatedUsrId;
	}

	public Integer getJbcModifiedUsrId()
	{
		return jbcModifiedUsrId;
	}

	public void setJbcModifiedUsrId(Integer jbcModifiedUsrId)
	{
		this.jbcModifiedUsrId = jbcModifiedUsrId;
	}

	public DateTime getJbcCreatedDttm()
	{
		return jbcCreatedDttm;
	}

	public void setJbcCreatedDttm(DateTime jbcCreatedDttm)
	{
		this.jbcCreatedDttm = jbcCreatedDttm;
	}

	public DateTime getJbcModifiedDttm()
	{
		return jbcModifiedDttm;
	}

	public void setJbcModifiedDttm(DateTime jbcModifiedDttm)
	{
		this.jbcModifiedDttm = jbcModifiedDttm;
	}

}
