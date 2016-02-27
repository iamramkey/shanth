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

public abstract class ReferenceFilePollerSettings extends SignatureDbo
{
	private Integer rpsId;
	private String rpsName;
	private String rpsSourceDir;
	private String rpsTargetDir;
	private Integer rpsFreqSecs;
	private Integer rpsPollerJbtId;
	private Integer rpsRefDataComparatorJbtId;
	private Integer rpsStabilitySecs;
	private Integer rpsStabilityRetry;
	private String rpsBk;
	private Integer rpsCreatedUsrId;
	private Integer rpsModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime rpsCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime rpsModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			rpsId = getInteger(rs.getInt("rps_id"));
			rpsName = rs.getString("rps_name");
			rpsSourceDir = rs.getString("rps_source_dir");
			rpsTargetDir = rs.getString("rps_target_dir");
			rpsFreqSecs = rs.getInt("rps_freq_secs");
			rpsPollerJbtId = getInteger(rs.getInt("rps_poller_jbt_id"));
			rpsRefDataComparatorJbtId = getInteger(rs.getInt("rps_ref_data_comparator_jbt_id"));
			rpsStabilitySecs = rs.getInt("rps_stability_secs");
			rpsStabilityRetry = rs.getInt("rps_stability_retry");
			rpsBk = rs.getString("rps_bk");
			rpsCreatedUsrId = getInteger(rs.getInt("rps_created_usr_id"));
			rpsModifiedUsrId = getInteger(rs.getInt("rps_modified_usr_id"));
			rpsCreatedDttm = getDateTime(rs.getTimestamp("rps_created_dttm"));
			rpsModifiedDttm = getDateTime(rs.getTimestamp("rps_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return rpsId;
	}

	public void setPk(int pk)
	{
		rpsId = pk;
	}

	public String getBk()
	{
		return rpsBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(rpsId.toString());
		data.add(rpsName.toString());
		data.add(rpsSourceDir.toString());
		data.add(rpsTargetDir.toString());
		data.add(rpsFreqSecs.toString());
		data.add(rpsPollerJbtId.toString());
		data.add(rpsRefDataComparatorJbtId.toString());
		data.add(rpsStabilitySecs.toString());
		data.add(rpsStabilityRetry.toString());
		data.add(getRpsCreatedUsr());
		data.add(Constants.dttf.print(rpsCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("rpsName", getString(rpsName));
		data.put("rpsSourceDir", getString(rpsSourceDir));
		data.put("rpsTargetDir", getString(rpsTargetDir));
		data.put("rpsFreqSecs", getString(rpsFreqSecs));
		data.put("rpsPollerJbt", getRpsPollerJbt());
		data.put("rpsRefDataComparatorJbt", getRpsRefDataComparatorJbt());
		data.put("rpsStabilitySecs", getString(rpsStabilitySecs));
		data.put("rpsStabilityRetry", getString(rpsStabilityRetry));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setRpsName(input.getString("rpsName"));
		setRpsSourceDir(input.getString("rpsSourceDir"));
		setRpsTargetDir(input.getString("rpsTargetDir"));
		setRpsFreqSecs(input.getInteger("rpsFreqSecs"));
		setRpsPollerJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("rpsPollerJbt")));
		setRpsRefDataComparatorJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("rpsRefDataComparatorJbt")));
		setRpsStabilitySecs(input.getInteger("rpsStabilitySecs"));
		setRpsStabilityRetry(input.getInteger("rpsStabilityRetry"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(rpsBk.toString());
		auditData.add(getRpsCreatedUsr());
		auditData.add(rpsModifiedUsrId == null ? Constants.NULLSTRING : getRpsModifiedUsr());
		auditData.add(Constants.dttf.print(rpsCreatedDttm));
		auditData.add(rpsModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(rpsModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return rpsName;
	}

	public String getRpsPollerJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, rpsPollerJbtId);
	}

	public String getRpsRefDataComparatorJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, rpsRefDataComparatorJbtId);
	}

	public String getRpsCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, rpsCreatedUsrId);
	}

	public String getRpsModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, rpsModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("rpsPollerJbt"))
			return getRpsPollerJbt();
		if (propertyName.equals("rpsRefDataComparatorJbt"))
			return getRpsRefDataComparatorJbt();
		if (propertyName.equals("rpsCreatedUsr"))
			return getRpsCreatedUsr();
		if (propertyName.equals("rpsModifiedUsr"))
			return getRpsModifiedUsr();
		if (propertyName.equals("rpsId"))
			return getRpsId();
		if (propertyName.equals("rpsName"))
			return getRpsName();
		if (propertyName.equals("rpsSourceDir"))
			return getRpsSourceDir();
		if (propertyName.equals("rpsTargetDir"))
			return getRpsTargetDir();
		if (propertyName.equals("rpsFreqSecs"))
			return getRpsFreqSecs();
		if (propertyName.equals("rpsPollerJbtId"))
			return getRpsPollerJbtId();
		if (propertyName.equals("rpsRefDataComparatorJbtId"))
			return getRpsRefDataComparatorJbtId();
		if (propertyName.equals("rpsStabilitySecs"))
			return getRpsStabilitySecs();
		if (propertyName.equals("rpsStabilityRetry"))
			return getRpsStabilityRetry();
		if (propertyName.equals("rpsBk"))
			return getRpsBk();
		if (propertyName.equals("rpsCreatedUsrId"))
			return getRpsCreatedUsrId();
		if (propertyName.equals("rpsModifiedUsrId"))
			return getRpsModifiedUsrId();
		if (propertyName.equals("rpsCreatedDttm"))
			return getRpsCreatedDttm();
		if (propertyName.equals("rpsModifiedDttm"))
			return getRpsModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getRpsId()
	{
		return rpsId;
	}

	public void setRpsId(Integer rpsId)
	{
		this.rpsId = rpsId;
	}

	public String getRpsName()
	{
		return rpsName;
	}

	public void setRpsName(String rpsName)
	{
		this.rpsName = rpsName;
	}

	public String getRpsSourceDir()
	{
		return rpsSourceDir;
	}

	public void setRpsSourceDir(String rpsSourceDir)
	{
		this.rpsSourceDir = rpsSourceDir;
	}

	public String getRpsTargetDir()
	{
		return rpsTargetDir;
	}

	public void setRpsTargetDir(String rpsTargetDir)
	{
		this.rpsTargetDir = rpsTargetDir;
	}

	public Integer getRpsFreqSecs()
	{
		return rpsFreqSecs;
	}

	public void setRpsFreqSecs(Integer rpsFreqSecs)
	{
		this.rpsFreqSecs = rpsFreqSecs;
	}

	public Integer getRpsPollerJbtId()
	{
		return rpsPollerJbtId;
	}

	public void setRpsPollerJbtId(Integer rpsPollerJbtId)
	{
		this.rpsPollerJbtId = rpsPollerJbtId;
	}

	public Integer getRpsRefDataComparatorJbtId()
	{
		return rpsRefDataComparatorJbtId;
	}

	public void setRpsRefDataComparatorJbtId(Integer rpsRefDataComparatorJbtId)
	{
		this.rpsRefDataComparatorJbtId = rpsRefDataComparatorJbtId;
	}

	public Integer getRpsStabilitySecs()
	{
		return rpsStabilitySecs;
	}

	public void setRpsStabilitySecs(Integer rpsStabilitySecs)
	{
		this.rpsStabilitySecs = rpsStabilitySecs;
	}

	public Integer getRpsStabilityRetry()
	{
		return rpsStabilityRetry;
	}

	public void setRpsStabilityRetry(Integer rpsStabilityRetry)
	{
		this.rpsStabilityRetry = rpsStabilityRetry;
	}

	public String getRpsBk()
	{
		return rpsBk;
	}

	public void setRpsBk(String rpsBk)
	{
		this.rpsBk = rpsBk;
	}

	public Integer getRpsCreatedUsrId()
	{
		return rpsCreatedUsrId;
	}

	public void setRpsCreatedUsrId(Integer rpsCreatedUsrId)
	{
		this.rpsCreatedUsrId = rpsCreatedUsrId;
	}

	public Integer getRpsModifiedUsrId()
	{
		return rpsModifiedUsrId;
	}

	public void setRpsModifiedUsrId(Integer rpsModifiedUsrId)
	{
		this.rpsModifiedUsrId = rpsModifiedUsrId;
	}

	public DateTime getRpsCreatedDttm()
	{
		return rpsCreatedDttm;
	}

	public void setRpsCreatedDttm(DateTime rpsCreatedDttm)
	{
		this.rpsCreatedDttm = rpsCreatedDttm;
	}

	public DateTime getRpsModifiedDttm()
	{
		return rpsModifiedDttm;
	}

	public void setRpsModifiedDttm(DateTime rpsModifiedDttm)
	{
		this.rpsModifiedDttm = rpsModifiedDttm;
	}

}
