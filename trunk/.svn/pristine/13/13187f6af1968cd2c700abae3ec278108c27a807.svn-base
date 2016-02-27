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

public abstract class PendingRerateDs extends SignatureDbo
{
	private Integer prdId;
	private String prdName;
	private Integer prdBatchSize;
	private Integer prdFrequencySecs;
	private Integer prdTimeoutSecs;
	private Integer prdReratePollerJbtId;
	private Integer prdRerateDataloadJbtId;
	private String prdBk;
	private Integer prdCreatedUsrId;
	private Integer prdModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime prdCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime prdModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			prdId = getInteger(rs.getInt("prd_id"));
			prdName = rs.getString("prd_name");
			prdBatchSize = rs.getInt("prd_batch_size");
			prdFrequencySecs = rs.getInt("prd_frequency_secs");
			prdTimeoutSecs = rs.getInt("prd_timeout_secs");
			prdReratePollerJbtId = getInteger(rs.getInt("prd_rerate_poller_jbt_id"));
			prdRerateDataloadJbtId = getInteger(rs.getInt("prd_rerate_dataload_jbt_id"));
			prdBk = rs.getString("prd_bk");
			prdCreatedUsrId = getInteger(rs.getInt("prd_created_usr_id"));
			prdModifiedUsrId = getInteger(rs.getInt("prd_modified_usr_id"));
			prdCreatedDttm = getDateTime(rs.getTimestamp("prd_created_dttm"));
			prdModifiedDttm = getDateTime(rs.getTimestamp("prd_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return prdId;
	}

	public void setPk(int pk)
	{
		prdId = pk;
	}

	public String getBk()
	{
		return prdBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(prdId.toString());
		data.add(prdName.toString());
		data.add(prdBatchSize == null ? Constants.NULLSTRING : prdBatchSize.toString());
		data.add(prdFrequencySecs.toString());
		data.add(prdTimeoutSecs.toString());
		data.add(prdReratePollerJbtId.toString());
		data.add(prdRerateDataloadJbtId.toString());
		data.add(getPrdCreatedUsr());
		data.add(Constants.dttf.print(prdCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("prdName", getString(prdName));
		data.put("prdBatchSize", getString(prdBatchSize));
		data.put("prdFrequencySecs", getString(prdFrequencySecs));
		data.put("prdTimeoutSecs", getString(prdTimeoutSecs));
		data.put("prdReratePollerJbt", getPrdReratePollerJbt());
		data.put("prdRerateDataloadJbt", getPrdRerateDataloadJbt());
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setPrdName(input.getString("prdName"));
		setPrdBatchSize(input.getInteger("prdBatchSize"));
		setPrdFrequencySecs(input.getInteger("prdFrequencySecs"));
		setPrdTimeoutSecs(input.getInteger("prdTimeoutSecs"));
		setPrdReratePollerJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("prdReratePollerJbt")));
		setPrdRerateDataloadJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("prdRerateDataloadJbt")));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(prdBk.toString());
		auditData.add(getPrdCreatedUsr());
		auditData.add(prdModifiedUsrId == null ? Constants.NULLSTRING : getPrdModifiedUsr());
		auditData.add(Constants.dttf.print(prdCreatedDttm));
		auditData.add(prdModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(prdModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return prdName;
	}

	public String getPrdReratePollerJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, prdReratePollerJbtId);
	}

	public String getPrdRerateDataloadJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, prdRerateDataloadJbtId);
	}

	public String getPrdCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, prdCreatedUsrId);
	}

	public String getPrdModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, prdModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("prdReratePollerJbt"))
			return getPrdReratePollerJbt();
		if (propertyName.equals("prdRerateDataloadJbt"))
			return getPrdRerateDataloadJbt();
		if (propertyName.equals("prdCreatedUsr"))
			return getPrdCreatedUsr();
		if (propertyName.equals("prdModifiedUsr"))
			return getPrdModifiedUsr();
		if (propertyName.equals("prdId"))
			return getPrdId();
		if (propertyName.equals("prdName"))
			return getPrdName();
		if (propertyName.equals("prdBatchSize"))
			return getPrdBatchSize();
		if (propertyName.equals("prdFrequencySecs"))
			return getPrdFrequencySecs();
		if (propertyName.equals("prdTimeoutSecs"))
			return getPrdTimeoutSecs();
		if (propertyName.equals("prdReratePollerJbtId"))
			return getPrdReratePollerJbtId();
		if (propertyName.equals("prdRerateDataloadJbtId"))
			return getPrdRerateDataloadJbtId();
		if (propertyName.equals("prdBk"))
			return getPrdBk();
		if (propertyName.equals("prdCreatedUsrId"))
			return getPrdCreatedUsrId();
		if (propertyName.equals("prdModifiedUsrId"))
			return getPrdModifiedUsrId();
		if (propertyName.equals("prdCreatedDttm"))
			return getPrdCreatedDttm();
		if (propertyName.equals("prdModifiedDttm"))
			return getPrdModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getPrdId()
	{
		return prdId;
	}

	public void setPrdId(Integer prdId)
	{
		this.prdId = prdId;
	}

	public String getPrdName()
	{
		return prdName;
	}

	public void setPrdName(String prdName)
	{
		this.prdName = prdName;
	}

	public Integer getPrdBatchSize()
	{
		return prdBatchSize;
	}

	public void setPrdBatchSize(Integer prdBatchSize)
	{
		this.prdBatchSize = prdBatchSize;
	}

	public Integer getPrdFrequencySecs()
	{
		return prdFrequencySecs;
	}

	public void setPrdFrequencySecs(Integer prdFrequencySecs)
	{
		this.prdFrequencySecs = prdFrequencySecs;
	}

	public Integer getPrdTimeoutSecs()
	{
		return prdTimeoutSecs;
	}

	public void setPrdTimeoutSecs(Integer prdTimeoutSecs)
	{
		this.prdTimeoutSecs = prdTimeoutSecs;
	}

	public Integer getPrdReratePollerJbtId()
	{
		return prdReratePollerJbtId;
	}

	public void setPrdReratePollerJbtId(Integer prdReratePollerJbtId)
	{
		this.prdReratePollerJbtId = prdReratePollerJbtId;
	}

	public Integer getPrdRerateDataloadJbtId()
	{
		return prdRerateDataloadJbtId;
	}

	public void setPrdRerateDataloadJbtId(Integer prdRerateDataloadJbtId)
	{
		this.prdRerateDataloadJbtId = prdRerateDataloadJbtId;
	}

	public String getPrdBk()
	{
		return prdBk;
	}

	public void setPrdBk(String prdBk)
	{
		this.prdBk = prdBk;
	}

	public Integer getPrdCreatedUsrId()
	{
		return prdCreatedUsrId;
	}

	public void setPrdCreatedUsrId(Integer prdCreatedUsrId)
	{
		this.prdCreatedUsrId = prdCreatedUsrId;
	}

	public Integer getPrdModifiedUsrId()
	{
		return prdModifiedUsrId;
	}

	public void setPrdModifiedUsrId(Integer prdModifiedUsrId)
	{
		this.prdModifiedUsrId = prdModifiedUsrId;
	}

	public DateTime getPrdCreatedDttm()
	{
		return prdCreatedDttm;
	}

	public void setPrdCreatedDttm(DateTime prdCreatedDttm)
	{
		this.prdCreatedDttm = prdCreatedDttm;
	}

	public DateTime getPrdModifiedDttm()
	{
		return prdModifiedDttm;
	}

	public void setPrdModifiedDttm(DateTime prdModifiedDttm)
	{
		this.prdModifiedDttm = prdModifiedDttm;
	}

}
