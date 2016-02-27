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
import se.signa.signature.gen.dba.UserTblDba;
import se.signa.signature.helpers.DisplayFieldHelper;

public abstract class ExtractionPollerSettings extends SignatureDbo
{
	private Integer exsId;
	private String exsName;
	private Integer exsDelayMins;
	private Integer exsFrequencySecs;
	private Integer exsPollerJbtId;
	private String exsBk;
	private Integer exsCreatedUsrId;
	private Integer exsModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime exsCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime exsModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			exsId = getInteger(rs.getInt("exs_id"));
			exsName = rs.getString("exs_name");
			exsDelayMins = rs.getInt("exs_delay_mins");
			exsFrequencySecs = rs.getInt("exs_frequency_secs");
			exsPollerJbtId = getInteger(rs.getInt("exs_poller_jbt_id"));
			exsBk = rs.getString("exs_bk");
			exsCreatedUsrId = getInteger(rs.getInt("exs_created_usr_id"));
			exsModifiedUsrId = getInteger(rs.getInt("exs_modified_usr_id"));
			exsCreatedDttm = getDateTime(rs.getTimestamp("exs_created_dttm"));
			exsModifiedDttm = getDateTime(rs.getTimestamp("exs_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return exsId;
	}

	public void setPk(int pk)
	{
		exsId = pk;
	}

	public String getBk()
	{
		return exsBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(exsId.toString());
		data.add(exsName.toString());
		data.add(exsDelayMins.toString());
		data.add(exsFrequencySecs.toString());
		data.add(exsPollerJbtId.toString());
		data.add(getExsCreatedUsr());
		data.add(Constants.dttf.print(exsCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("exsName", getString(exsName));
		data.put("exsDelayMins", getString(exsDelayMins));
		data.put("exsFrequencySecs", getString(exsFrequencySecs));
		data.put("exsPollerJbt", getExsPollerJbt());
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setExsName(input.getString("exsName"));
		setExsDelayMins(input.getInteger("exsDelayMins"));
		setExsFrequencySecs(input.getInteger("exsFrequencySecs"));
		setExsPollerJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("exsPollerJbt")));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(exsBk.toString());
		auditData.add(getExsCreatedUsr());
		auditData.add(exsModifiedUsrId == null ? Constants.NULLSTRING : getExsModifiedUsr());
		auditData.add(Constants.dttf.print(exsCreatedDttm));
		auditData.add(exsModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(exsModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return exsName;
	}

	public String getExsPollerJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, exsPollerJbtId);
	}

	public String getExsCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, exsCreatedUsrId);
	}

	public String getExsModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, exsModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("exsPollerJbt"))
			return getExsPollerJbt();
		if (propertyName.equals("exsCreatedUsr"))
			return getExsCreatedUsr();
		if (propertyName.equals("exsModifiedUsr"))
			return getExsModifiedUsr();
		if (propertyName.equals("exsId"))
			return getExsId();
		if (propertyName.equals("exsName"))
			return getExsName();
		if (propertyName.equals("exsDelayMins"))
			return getExsDelayMins();
		if (propertyName.equals("exsFrequencySecs"))
			return getExsFrequencySecs();
		if (propertyName.equals("exsPollerJbtId"))
			return getExsPollerJbtId();
		if (propertyName.equals("exsBk"))
			return getExsBk();
		if (propertyName.equals("exsCreatedUsrId"))
			return getExsCreatedUsrId();
		if (propertyName.equals("exsModifiedUsrId"))
			return getExsModifiedUsrId();
		if (propertyName.equals("exsCreatedDttm"))
			return getExsCreatedDttm();
		if (propertyName.equals("exsModifiedDttm"))
			return getExsModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getExsId()
	{
		return exsId;
	}

	public void setExsId(Integer exsId)
	{
		this.exsId = exsId;
	}

	public String getExsName()
	{
		return exsName;
	}

	public void setExsName(String exsName)
	{
		this.exsName = exsName;
	}

	public Integer getExsDelayMins()
	{
		return exsDelayMins;
	}

	public void setExsDelayMins(Integer exsDelayMins)
	{
		this.exsDelayMins = exsDelayMins;
	}

	public Integer getExsFrequencySecs()
	{
		return exsFrequencySecs;
	}

	public void setExsFrequencySecs(Integer exsFrequencySecs)
	{
		this.exsFrequencySecs = exsFrequencySecs;
	}

	public Integer getExsPollerJbtId()
	{
		return exsPollerJbtId;
	}

	public void setExsPollerJbtId(Integer exsPollerJbtId)
	{
		this.exsPollerJbtId = exsPollerJbtId;
	}

	public String getExsBk()
	{
		return exsBk;
	}

	public void setExsBk(String exsBk)
	{
		this.exsBk = exsBk;
	}

	public Integer getExsCreatedUsrId()
	{
		return exsCreatedUsrId;
	}

	public void setExsCreatedUsrId(Integer exsCreatedUsrId)
	{
		this.exsCreatedUsrId = exsCreatedUsrId;
	}

	public Integer getExsModifiedUsrId()
	{
		return exsModifiedUsrId;
	}

	public void setExsModifiedUsrId(Integer exsModifiedUsrId)
	{
		this.exsModifiedUsrId = exsModifiedUsrId;
	}

	public DateTime getExsCreatedDttm()
	{
		return exsCreatedDttm;
	}

	public void setExsCreatedDttm(DateTime exsCreatedDttm)
	{
		this.exsCreatedDttm = exsCreatedDttm;
	}

	public DateTime getExsModifiedDttm()
	{
		return exsModifiedDttm;
	}

	public void setExsModifiedDttm(DateTime exsModifiedDttm)
	{
		this.exsModifiedDttm = exsModifiedDttm;
	}

}
