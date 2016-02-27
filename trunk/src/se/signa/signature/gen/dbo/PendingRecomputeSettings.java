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

public abstract class PendingRecomputeSettings extends SignatureDbo
{
	private Integer prcId;
	private String prcName;
	private Integer prcBatchSize;
	private Integer prcFrequencySecs;
	private Integer prcPollerJbtId;
	private Integer prcReraterJbtId;
	private String prcBk;
	private Integer prcCreatedUsrId;
	private Integer prcModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime prcCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime prcModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			prcId = getInteger(rs.getInt("prc_id"));
			prcName = rs.getString("prc_name");
			prcBatchSize = rs.getInt("prc_batch_size");
			prcFrequencySecs = rs.getInt("prc_frequency_secs");
			prcPollerJbtId = getInteger(rs.getInt("prc_poller_jbt_id"));
			prcReraterJbtId = getInteger(rs.getInt("prc_rerater_jbt_id"));
			prcBk = rs.getString("prc_bk");
			prcCreatedUsrId = getInteger(rs.getInt("prc_created_usr_id"));
			prcModifiedUsrId = getInteger(rs.getInt("prc_modified_usr_id"));
			prcCreatedDttm = getDateTime(rs.getTimestamp("prc_created_dttm"));
			prcModifiedDttm = getDateTime(rs.getTimestamp("prc_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return prcId;
	}

	public void setPk(int pk)
	{
		prcId = pk;
	}

	public String getBk()
	{
		return prcBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(prcId.toString());
		data.add(prcName.toString());
		data.add(prcBatchSize.toString());
		data.add(prcFrequencySecs.toString());
		data.add(prcPollerJbtId.toString());
		data.add(prcReraterJbtId.toString());
		data.add(getPrcCreatedUsr());
		data.add(Constants.dttf.print(prcCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("prcName", getString(prcName));
		data.put("prcBatchSize", getString(prcBatchSize));
		data.put("prcFrequencySecs", getString(prcFrequencySecs));
		data.put("prcPollerJbt", getPrcPollerJbt());
		data.put("prcReraterJbt", getPrcReraterJbt());
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setPrcName(input.getString("prcName"));
		setPrcBatchSize(input.getInteger("prcBatchSize"));
		setPrcFrequencySecs(input.getInteger("prcFrequencySecs"));
		setPrcPollerJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("prcPollerJbt")));
		setPrcReraterJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("prcReraterJbt")));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(prcBk.toString());
		auditData.add(getPrcCreatedUsr());
		auditData.add(prcModifiedUsrId == null ? Constants.NULLSTRING : getPrcModifiedUsr());
		auditData.add(Constants.dttf.print(prcCreatedDttm));
		auditData.add(prcModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(prcModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return prcName;
	}

	public String getPrcPollerJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, prcPollerJbtId);
	}

	public String getPrcReraterJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, prcReraterJbtId);
	}

	public String getPrcCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, prcCreatedUsrId);
	}

	public String getPrcModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, prcModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("prcPollerJbt"))
			return getPrcPollerJbt();
		if (propertyName.equals("prcReraterJbt"))
			return getPrcReraterJbt();
		if (propertyName.equals("prcCreatedUsr"))
			return getPrcCreatedUsr();
		if (propertyName.equals("prcModifiedUsr"))
			return getPrcModifiedUsr();
		if (propertyName.equals("prcId"))
			return getPrcId();
		if (propertyName.equals("prcName"))
			return getPrcName();
		if (propertyName.equals("prcBatchSize"))
			return getPrcBatchSize();
		if (propertyName.equals("prcFrequencySecs"))
			return getPrcFrequencySecs();
		if (propertyName.equals("prcPollerJbtId"))
			return getPrcPollerJbtId();
		if (propertyName.equals("prcReraterJbtId"))
			return getPrcReraterJbtId();
		if (propertyName.equals("prcBk"))
			return getPrcBk();
		if (propertyName.equals("prcCreatedUsrId"))
			return getPrcCreatedUsrId();
		if (propertyName.equals("prcModifiedUsrId"))
			return getPrcModifiedUsrId();
		if (propertyName.equals("prcCreatedDttm"))
			return getPrcCreatedDttm();
		if (propertyName.equals("prcModifiedDttm"))
			return getPrcModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getPrcId()
	{
		return prcId;
	}

	public void setPrcId(Integer prcId)
	{
		this.prcId = prcId;
	}

	public String getPrcName()
	{
		return prcName;
	}

	public void setPrcName(String prcName)
	{
		this.prcName = prcName;
	}

	public Integer getPrcBatchSize()
	{
		return prcBatchSize;
	}

	public void setPrcBatchSize(Integer prcBatchSize)
	{
		this.prcBatchSize = prcBatchSize;
	}

	public Integer getPrcFrequencySecs()
	{
		return prcFrequencySecs;
	}

	public void setPrcFrequencySecs(Integer prcFrequencySecs)
	{
		this.prcFrequencySecs = prcFrequencySecs;
	}

	public Integer getPrcPollerJbtId()
	{
		return prcPollerJbtId;
	}

	public void setPrcPollerJbtId(Integer prcPollerJbtId)
	{
		this.prcPollerJbtId = prcPollerJbtId;
	}

	public Integer getPrcReraterJbtId()
	{
		return prcReraterJbtId;
	}

	public void setPrcReraterJbtId(Integer prcReraterJbtId)
	{
		this.prcReraterJbtId = prcReraterJbtId;
	}

	public String getPrcBk()
	{
		return prcBk;
	}

	public void setPrcBk(String prcBk)
	{
		this.prcBk = prcBk;
	}

	public Integer getPrcCreatedUsrId()
	{
		return prcCreatedUsrId;
	}

	public void setPrcCreatedUsrId(Integer prcCreatedUsrId)
	{
		this.prcCreatedUsrId = prcCreatedUsrId;
	}

	public Integer getPrcModifiedUsrId()
	{
		return prcModifiedUsrId;
	}

	public void setPrcModifiedUsrId(Integer prcModifiedUsrId)
	{
		this.prcModifiedUsrId = prcModifiedUsrId;
	}

	public DateTime getPrcCreatedDttm()
	{
		return prcCreatedDttm;
	}

	public void setPrcCreatedDttm(DateTime prcCreatedDttm)
	{
		this.prcCreatedDttm = prcCreatedDttm;
	}

	public DateTime getPrcModifiedDttm()
	{
		return prcModifiedDttm;
	}

	public void setPrcModifiedDttm(DateTime prcModifiedDttm)
	{
		this.prcModifiedDttm = prcModifiedDttm;
	}

}
