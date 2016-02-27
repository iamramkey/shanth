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

public abstract class PendingDataloadSettings extends SignatureDbo
{
	private Integer pdsId;
	private String pdsName;
	private Integer pdsBatchSize;
	private Integer pdsFrequencySecs;
	private Integer pdsTimeoutSecs;
	private Integer pdsPollerJbtId;
	private Integer pdsDataloadJbtId;
	private String pdsBk;
	private Integer pdsCreatedUsrId;
	private Integer pdsModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime pdsCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime pdsModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			pdsId = getInteger(rs.getInt("pds_id"));
			pdsName = rs.getString("pds_name");
			pdsBatchSize = rs.getInt("pds_batch_size");
			pdsFrequencySecs = rs.getInt("pds_frequency_secs");
			pdsTimeoutSecs = rs.getInt("pds_timeout_secs");
			pdsPollerJbtId = getInteger(rs.getInt("pds_poller_jbt_id"));
			pdsDataloadJbtId = getInteger(rs.getInt("pds_dataload_jbt_id"));
			pdsBk = rs.getString("pds_bk");
			pdsCreatedUsrId = getInteger(rs.getInt("pds_created_usr_id"));
			pdsModifiedUsrId = getInteger(rs.getInt("pds_modified_usr_id"));
			pdsCreatedDttm = getDateTime(rs.getTimestamp("pds_created_dttm"));
			pdsModifiedDttm = getDateTime(rs.getTimestamp("pds_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return pdsId;
	}

	public void setPk(int pk)
	{
		pdsId = pk;
	}

	public String getBk()
	{
		return pdsBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(pdsId.toString());
		data.add(pdsName.toString());
		data.add(pdsBatchSize == null ? Constants.NULLSTRING : pdsBatchSize.toString());
		data.add(pdsFrequencySecs.toString());
		data.add(pdsTimeoutSecs.toString());
		data.add(pdsPollerJbtId.toString());
		data.add(pdsDataloadJbtId.toString());
		data.add(getPdsCreatedUsr());
		data.add(Constants.dttf.print(pdsCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("pdsName", getString(pdsName));
		data.put("pdsBatchSize", getString(pdsBatchSize));
		data.put("pdsFrequencySecs", getString(pdsFrequencySecs));
		data.put("pdsTimeoutSecs", getString(pdsTimeoutSecs));
		data.put("pdsPollerJbt", getPdsPollerJbt());
		data.put("pdsDataloadJbt", getPdsDataloadJbt());
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setPdsName(input.getString("pdsName"));
		setPdsBatchSize(input.getInteger("pdsBatchSize"));
		setPdsFrequencySecs(input.getInteger("pdsFrequencySecs"));
		setPdsTimeoutSecs(input.getInteger("pdsTimeoutSecs"));
		setPdsPollerJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("pdsPollerJbt")));
		setPdsDataloadJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("pdsDataloadJbt")));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(pdsBk.toString());
		auditData.add(getPdsCreatedUsr());
		auditData.add(pdsModifiedUsrId == null ? Constants.NULLSTRING : getPdsModifiedUsr());
		auditData.add(Constants.dttf.print(pdsCreatedDttm));
		auditData.add(pdsModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(pdsModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return pdsName;
	}

	public String getPdsPollerJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, pdsPollerJbtId);
	}

	public String getPdsDataloadJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, pdsDataloadJbtId);
	}

	public String getPdsCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, pdsCreatedUsrId);
	}

	public String getPdsModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, pdsModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("pdsPollerJbt"))
			return getPdsPollerJbt();
		if (propertyName.equals("pdsDataloadJbt"))
			return getPdsDataloadJbt();
		if (propertyName.equals("pdsCreatedUsr"))
			return getPdsCreatedUsr();
		if (propertyName.equals("pdsModifiedUsr"))
			return getPdsModifiedUsr();
		if (propertyName.equals("pdsId"))
			return getPdsId();
		if (propertyName.equals("pdsName"))
			return getPdsName();
		if (propertyName.equals("pdsBatchSize"))
			return getPdsBatchSize();
		if (propertyName.equals("pdsFrequencySecs"))
			return getPdsFrequencySecs();
		if (propertyName.equals("pdsTimeoutSecs"))
			return getPdsTimeoutSecs();
		if (propertyName.equals("pdsPollerJbtId"))
			return getPdsPollerJbtId();
		if (propertyName.equals("pdsDataloadJbtId"))
			return getPdsDataloadJbtId();
		if (propertyName.equals("pdsBk"))
			return getPdsBk();
		if (propertyName.equals("pdsCreatedUsrId"))
			return getPdsCreatedUsrId();
		if (propertyName.equals("pdsModifiedUsrId"))
			return getPdsModifiedUsrId();
		if (propertyName.equals("pdsCreatedDttm"))
			return getPdsCreatedDttm();
		if (propertyName.equals("pdsModifiedDttm"))
			return getPdsModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getPdsId()
	{
		return pdsId;
	}

	public void setPdsId(Integer pdsId)
	{
		this.pdsId = pdsId;
	}

	public String getPdsName()
	{
		return pdsName;
	}

	public void setPdsName(String pdsName)
	{
		this.pdsName = pdsName;
	}

	public Integer getPdsBatchSize()
	{
		return pdsBatchSize;
	}

	public void setPdsBatchSize(Integer pdsBatchSize)
	{
		this.pdsBatchSize = pdsBatchSize;
	}

	public Integer getPdsFrequencySecs()
	{
		return pdsFrequencySecs;
	}

	public void setPdsFrequencySecs(Integer pdsFrequencySecs)
	{
		this.pdsFrequencySecs = pdsFrequencySecs;
	}

	public Integer getPdsTimeoutSecs()
	{
		return pdsTimeoutSecs;
	}

	public void setPdsTimeoutSecs(Integer pdsTimeoutSecs)
	{
		this.pdsTimeoutSecs = pdsTimeoutSecs;
	}

	public Integer getPdsPollerJbtId()
	{
		return pdsPollerJbtId;
	}

	public void setPdsPollerJbtId(Integer pdsPollerJbtId)
	{
		this.pdsPollerJbtId = pdsPollerJbtId;
	}

	public Integer getPdsDataloadJbtId()
	{
		return pdsDataloadJbtId;
	}

	public void setPdsDataloadJbtId(Integer pdsDataloadJbtId)
	{
		this.pdsDataloadJbtId = pdsDataloadJbtId;
	}

	public String getPdsBk()
	{
		return pdsBk;
	}

	public void setPdsBk(String pdsBk)
	{
		this.pdsBk = pdsBk;
	}

	public Integer getPdsCreatedUsrId()
	{
		return pdsCreatedUsrId;
	}

	public void setPdsCreatedUsrId(Integer pdsCreatedUsrId)
	{
		this.pdsCreatedUsrId = pdsCreatedUsrId;
	}

	public Integer getPdsModifiedUsrId()
	{
		return pdsModifiedUsrId;
	}

	public void setPdsModifiedUsrId(Integer pdsModifiedUsrId)
	{
		this.pdsModifiedUsrId = pdsModifiedUsrId;
	}

	public DateTime getPdsCreatedDttm()
	{
		return pdsCreatedDttm;
	}

	public void setPdsCreatedDttm(DateTime pdsCreatedDttm)
	{
		this.pdsCreatedDttm = pdsCreatedDttm;
	}

	public DateTime getPdsModifiedDttm()
	{
		return pdsModifiedDttm;
	}

	public void setPdsModifiedDttm(DateTime pdsModifiedDttm)
	{
		this.pdsModifiedDttm = pdsModifiedDttm;
	}

}
