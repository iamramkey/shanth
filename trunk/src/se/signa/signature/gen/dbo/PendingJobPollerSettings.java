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

public abstract class PendingJobPollerSettings extends SignatureDbo
{
	private Integer pjsId;
	private String pjsName;
	private Integer pjsBatchSize;
	private Integer pjsFrequencySecs;
	private Integer pjsPollerJbtId;
	private String pjsBk;
	private Integer pjsCreatedUsrId;
	private Integer pjsModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime pjsCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime pjsModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			pjsId = getInteger(rs.getInt("pjs_id"));
			pjsName = rs.getString("pjs_name");
			pjsBatchSize = rs.getInt("pjs_batch_size");
			pjsFrequencySecs = rs.getInt("pjs_frequency_secs");
			pjsPollerJbtId = getInteger(rs.getInt("pjs_poller_jbt_id"));
			pjsBk = rs.getString("pjs_bk");
			pjsCreatedUsrId = getInteger(rs.getInt("pjs_created_usr_id"));
			pjsModifiedUsrId = getInteger(rs.getInt("pjs_modified_usr_id"));
			pjsCreatedDttm = getDateTime(rs.getTimestamp("pjs_created_dttm"));
			pjsModifiedDttm = getDateTime(rs.getTimestamp("pjs_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return pjsId;
	}

	public void setPk(int pk)
	{
		pjsId = pk;
	}

	public String getBk()
	{
		return pjsBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(pjsId.toString());
		data.add(pjsName.toString());
		data.add(pjsBatchSize == null ? Constants.NULLSTRING : pjsBatchSize.toString());
		data.add(pjsFrequencySecs.toString());
		data.add(pjsPollerJbtId.toString());
		data.add(getPjsCreatedUsr());
		data.add(Constants.dttf.print(pjsCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("pjsName", getString(pjsName));
		data.put("pjsBatchSize", getString(pjsBatchSize));
		data.put("pjsFrequencySecs", getString(pjsFrequencySecs));
		data.put("pjsPollerJbt", getPjsPollerJbt());
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setPjsName(input.getString("pjsName"));
		setPjsBatchSize(input.getInteger("pjsBatchSize"));
		setPjsFrequencySecs(input.getInteger("pjsFrequencySecs"));
		setPjsPollerJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("pjsPollerJbt")));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(pjsBk.toString());
		auditData.add(getPjsCreatedUsr());
		auditData.add(pjsModifiedUsrId == null ? Constants.NULLSTRING : getPjsModifiedUsr());
		auditData.add(Constants.dttf.print(pjsCreatedDttm));
		auditData.add(pjsModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(pjsModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return pjsName;
	}

	public String getPjsPollerJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, pjsPollerJbtId);
	}

	public String getPjsCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, pjsCreatedUsrId);
	}

	public String getPjsModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, pjsModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("pjsPollerJbt"))
			return getPjsPollerJbt();
		if (propertyName.equals("pjsCreatedUsr"))
			return getPjsCreatedUsr();
		if (propertyName.equals("pjsModifiedUsr"))
			return getPjsModifiedUsr();
		if (propertyName.equals("pjsId"))
			return getPjsId();
		if (propertyName.equals("pjsName"))
			return getPjsName();
		if (propertyName.equals("pjsBatchSize"))
			return getPjsBatchSize();
		if (propertyName.equals("pjsFrequencySecs"))
			return getPjsFrequencySecs();
		if (propertyName.equals("pjsPollerJbtId"))
			return getPjsPollerJbtId();
		if (propertyName.equals("pjsBk"))
			return getPjsBk();
		if (propertyName.equals("pjsCreatedUsrId"))
			return getPjsCreatedUsrId();
		if (propertyName.equals("pjsModifiedUsrId"))
			return getPjsModifiedUsrId();
		if (propertyName.equals("pjsCreatedDttm"))
			return getPjsCreatedDttm();
		if (propertyName.equals("pjsModifiedDttm"))
			return getPjsModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getPjsId()
	{
		return pjsId;
	}

	public void setPjsId(Integer pjsId)
	{
		this.pjsId = pjsId;
	}

	public String getPjsName()
	{
		return pjsName;
	}

	public void setPjsName(String pjsName)
	{
		this.pjsName = pjsName;
	}

	public Integer getPjsBatchSize()
	{
		return pjsBatchSize;
	}

	public void setPjsBatchSize(Integer pjsBatchSize)
	{
		this.pjsBatchSize = pjsBatchSize;
	}

	public Integer getPjsFrequencySecs()
	{
		return pjsFrequencySecs;
	}

	public void setPjsFrequencySecs(Integer pjsFrequencySecs)
	{
		this.pjsFrequencySecs = pjsFrequencySecs;
	}

	public Integer getPjsPollerJbtId()
	{
		return pjsPollerJbtId;
	}

	public void setPjsPollerJbtId(Integer pjsPollerJbtId)
	{
		this.pjsPollerJbtId = pjsPollerJbtId;
	}

	public String getPjsBk()
	{
		return pjsBk;
	}

	public void setPjsBk(String pjsBk)
	{
		this.pjsBk = pjsBk;
	}

	public Integer getPjsCreatedUsrId()
	{
		return pjsCreatedUsrId;
	}

	public void setPjsCreatedUsrId(Integer pjsCreatedUsrId)
	{
		this.pjsCreatedUsrId = pjsCreatedUsrId;
	}

	public Integer getPjsModifiedUsrId()
	{
		return pjsModifiedUsrId;
	}

	public void setPjsModifiedUsrId(Integer pjsModifiedUsrId)
	{
		this.pjsModifiedUsrId = pjsModifiedUsrId;
	}

	public DateTime getPjsCreatedDttm()
	{
		return pjsCreatedDttm;
	}

	public void setPjsCreatedDttm(DateTime pjsCreatedDttm)
	{
		this.pjsCreatedDttm = pjsCreatedDttm;
	}

	public DateTime getPjsModifiedDttm()
	{
		return pjsModifiedDttm;
	}

	public void setPjsModifiedDttm(DateTime pjsModifiedDttm)
	{
		this.pjsModifiedDttm = pjsModifiedDttm;
	}

}
