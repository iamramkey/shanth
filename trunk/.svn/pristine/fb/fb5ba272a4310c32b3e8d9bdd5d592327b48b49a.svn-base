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

public abstract class FailedTasksPollerSettings extends SignatureDbo
{
	private Integer fapId;
	private String fapName;
	private Integer fapFreqSecs;
	private Integer fapRetryCount;
	private Integer fapLookbackDays;
	private Integer fapPollerJbtId;
	private String fapBk;
	private Integer fapCreatedUsrId;
	private Integer fapModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime fapCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime fapModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			fapId = getInteger(rs.getInt("fap_id"));
			fapName = rs.getString("fap_name");
			fapFreqSecs = rs.getInt("fap_freq_secs");
			fapRetryCount = rs.getInt("fap_retry_count");
			fapLookbackDays = rs.getInt("fap_lookback_days");
			fapPollerJbtId = getInteger(rs.getInt("fap_poller_jbt_id"));
			fapBk = rs.getString("fap_bk");
			fapCreatedUsrId = getInteger(rs.getInt("fap_created_usr_id"));
			fapModifiedUsrId = getInteger(rs.getInt("fap_modified_usr_id"));
			fapCreatedDttm = getDateTime(rs.getTimestamp("fap_created_dttm"));
			fapModifiedDttm = getDateTime(rs.getTimestamp("fap_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return fapId;
	}

	public void setPk(int pk)
	{
		fapId = pk;
	}

	public String getBk()
	{
		return fapBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(fapId.toString());
		data.add(fapName.toString());
		data.add(fapFreqSecs.toString());
		data.add(fapRetryCount.toString());
		data.add(fapLookbackDays.toString());
		data.add(fapPollerJbtId.toString());
		data.add(getFapCreatedUsr());
		data.add(Constants.dttf.print(fapCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("fapName", getString(fapName));
		data.put("fapFreqSecs", getString(fapFreqSecs));
		data.put("fapRetryCount", getString(fapRetryCount));
		data.put("fapLookbackDays", getString(fapLookbackDays));
		data.put("fapPollerJbt", getFapPollerJbt());
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setFapName(input.getString("fapName"));
		setFapFreqSecs(input.getInteger("fapFreqSecs"));
		setFapRetryCount(input.getInteger("fapRetryCount"));
		setFapLookbackDays(input.getInteger("fapLookbackDays"));
		setFapPollerJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("fapPollerJbt")));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(fapBk.toString());
		auditData.add(getFapCreatedUsr());
		auditData.add(fapModifiedUsrId == null ? Constants.NULLSTRING : getFapModifiedUsr());
		auditData.add(Constants.dttf.print(fapCreatedDttm));
		auditData.add(fapModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(fapModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return fapName;
	}

	public String getFapPollerJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, fapPollerJbtId);
	}

	public String getFapCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, fapCreatedUsrId);
	}

	public String getFapModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, fapModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("fapPollerJbt"))
			return getFapPollerJbt();
		if (propertyName.equals("fapCreatedUsr"))
			return getFapCreatedUsr();
		if (propertyName.equals("fapModifiedUsr"))
			return getFapModifiedUsr();
		if (propertyName.equals("fapId"))
			return getFapId();
		if (propertyName.equals("fapName"))
			return getFapName();
		if (propertyName.equals("fapFreqSecs"))
			return getFapFreqSecs();
		if (propertyName.equals("fapRetryCount"))
			return getFapRetryCount();
		if (propertyName.equals("fapLookbackDays"))
			return getFapLookbackDays();
		if (propertyName.equals("fapPollerJbtId"))
			return getFapPollerJbtId();
		if (propertyName.equals("fapBk"))
			return getFapBk();
		if (propertyName.equals("fapCreatedUsrId"))
			return getFapCreatedUsrId();
		if (propertyName.equals("fapModifiedUsrId"))
			return getFapModifiedUsrId();
		if (propertyName.equals("fapCreatedDttm"))
			return getFapCreatedDttm();
		if (propertyName.equals("fapModifiedDttm"))
			return getFapModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getFapId()
	{
		return fapId;
	}

	public void setFapId(Integer fapId)
	{
		this.fapId = fapId;
	}

	public String getFapName()
	{
		return fapName;
	}

	public void setFapName(String fapName)
	{
		this.fapName = fapName;
	}

	public Integer getFapFreqSecs()
	{
		return fapFreqSecs;
	}

	public void setFapFreqSecs(Integer fapFreqSecs)
	{
		this.fapFreqSecs = fapFreqSecs;
	}

	public Integer getFapRetryCount()
	{
		return fapRetryCount;
	}

	public void setFapRetryCount(Integer fapRetryCount)
	{
		this.fapRetryCount = fapRetryCount;
	}

	public Integer getFapLookbackDays()
	{
		return fapLookbackDays;
	}

	public void setFapLookbackDays(Integer fapLookbackDays)
	{
		this.fapLookbackDays = fapLookbackDays;
	}

	public Integer getFapPollerJbtId()
	{
		return fapPollerJbtId;
	}

	public void setFapPollerJbtId(Integer fapPollerJbtId)
	{
		this.fapPollerJbtId = fapPollerJbtId;
	}

	public String getFapBk()
	{
		return fapBk;
	}

	public void setFapBk(String fapBk)
	{
		this.fapBk = fapBk;
	}

	public Integer getFapCreatedUsrId()
	{
		return fapCreatedUsrId;
	}

	public void setFapCreatedUsrId(Integer fapCreatedUsrId)
	{
		this.fapCreatedUsrId = fapCreatedUsrId;
	}

	public Integer getFapModifiedUsrId()
	{
		return fapModifiedUsrId;
	}

	public void setFapModifiedUsrId(Integer fapModifiedUsrId)
	{
		this.fapModifiedUsrId = fapModifiedUsrId;
	}

	public DateTime getFapCreatedDttm()
	{
		return fapCreatedDttm;
	}

	public void setFapCreatedDttm(DateTime fapCreatedDttm)
	{
		this.fapCreatedDttm = fapCreatedDttm;
	}

	public DateTime getFapModifiedDttm()
	{
		return fapModifiedDttm;
	}

	public void setFapModifiedDttm(DateTime fapModifiedDttm)
	{
		this.fapModifiedDttm = fapModifiedDttm;
	}

}
