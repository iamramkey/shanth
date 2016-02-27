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

public abstract class PendingRaterFileSettings extends SignatureDbo
{
	private Integer prsId;
	private String prsName;
	private Integer prsBatchSize;
	private Integer prsFrequencySecs;
	private Integer prsTimeoutSecs;
	private Integer prsPollerJbtId;
	private Integer prsTransferJbtId;
	private Integer prsRaterJbtId;
	private Integer prsReRaterJbtId;
	private String prsBk;
	private Integer prsCreatedUsrId;
	private Integer prsModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime prsCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime prsModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			prsId = getInteger(rs.getInt("prs_id"));
			prsName = rs.getString("prs_name");
			prsBatchSize = rs.getInt("prs_batch_size");
			prsFrequencySecs = rs.getInt("prs_frequency_secs");
			prsTimeoutSecs = rs.getInt("prs_timeout_secs");
			prsPollerJbtId = getInteger(rs.getInt("prs_poller_jbt_id"));
			prsTransferJbtId = getInteger(rs.getInt("prs_transfer_jbt_id"));
			prsRaterJbtId = getInteger(rs.getInt("prs_rater_jbt_id"));
			prsReRaterJbtId = getInteger(rs.getInt("prs_re_rater_jbt_id"));
			prsBk = rs.getString("prs_bk");
			prsCreatedUsrId = getInteger(rs.getInt("prs_created_usr_id"));
			prsModifiedUsrId = getInteger(rs.getInt("prs_modified_usr_id"));
			prsCreatedDttm = getDateTime(rs.getTimestamp("prs_created_dttm"));
			prsModifiedDttm = getDateTime(rs.getTimestamp("prs_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return prsId;
	}

	public void setPk(int pk)
	{
		prsId = pk;
	}

	public String getBk()
	{
		return prsBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(prsId.toString());
		data.add(prsName.toString());
		data.add(prsBatchSize == null ? Constants.NULLSTRING : prsBatchSize.toString());
		data.add(prsFrequencySecs.toString());
		data.add(prsTimeoutSecs.toString());
		data.add(prsPollerJbtId.toString());
		data.add(prsTransferJbtId.toString());
		data.add(prsRaterJbtId.toString());
		data.add(prsReRaterJbtId.toString());
		data.add(getPrsCreatedUsr());
		data.add(Constants.dttf.print(prsCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("prsName", getString(prsName));
		data.put("prsBatchSize", getString(prsBatchSize));
		data.put("prsFrequencySecs", getString(prsFrequencySecs));
		data.put("prsTimeoutSecs", getString(prsTimeoutSecs));
		data.put("prsPollerJbt", getPrsPollerJbt());
		data.put("prsTransferJbt", getPrsTransferJbt());
		data.put("prsRaterJbt", getPrsRaterJbt());
		data.put("prsReRaterJbt", getPrsReRaterJbt());
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setPrsName(input.getString("prsName"));
		setPrsBatchSize(input.getInteger("prsBatchSize"));
		setPrsFrequencySecs(input.getInteger("prsFrequencySecs"));
		setPrsTimeoutSecs(input.getInteger("prsTimeoutSecs"));
		setPrsPollerJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("prsPollerJbt")));
		setPrsTransferJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("prsTransferJbt")));
		setPrsRaterJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("prsRaterJbt")));
		setPrsReRaterJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("prsReRaterJbt")));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(prsBk.toString());
		auditData.add(getPrsCreatedUsr());
		auditData.add(prsModifiedUsrId == null ? Constants.NULLSTRING : getPrsModifiedUsr());
		auditData.add(Constants.dttf.print(prsCreatedDttm));
		auditData.add(prsModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(prsModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return prsName;
	}

	public String getPrsPollerJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, prsPollerJbtId);
	}

	public String getPrsTransferJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, prsTransferJbtId);
	}

	public String getPrsRaterJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, prsRaterJbtId);
	}

	public String getPrsReRaterJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, prsReRaterJbtId);
	}

	public String getPrsCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, prsCreatedUsrId);
	}

	public String getPrsModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, prsModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("prsPollerJbt"))
			return getPrsPollerJbt();
		if (propertyName.equals("prsTransferJbt"))
			return getPrsTransferJbt();
		if (propertyName.equals("prsRaterJbt"))
			return getPrsRaterJbt();
		if (propertyName.equals("prsReRaterJbt"))
			return getPrsReRaterJbt();
		if (propertyName.equals("prsCreatedUsr"))
			return getPrsCreatedUsr();
		if (propertyName.equals("prsModifiedUsr"))
			return getPrsModifiedUsr();
		if (propertyName.equals("prsId"))
			return getPrsId();
		if (propertyName.equals("prsName"))
			return getPrsName();
		if (propertyName.equals("prsBatchSize"))
			return getPrsBatchSize();
		if (propertyName.equals("prsFrequencySecs"))
			return getPrsFrequencySecs();
		if (propertyName.equals("prsTimeoutSecs"))
			return getPrsTimeoutSecs();
		if (propertyName.equals("prsPollerJbtId"))
			return getPrsPollerJbtId();
		if (propertyName.equals("prsTransferJbtId"))
			return getPrsTransferJbtId();
		if (propertyName.equals("prsRaterJbtId"))
			return getPrsRaterJbtId();
		if (propertyName.equals("prsReRaterJbtId"))
			return getPrsReRaterJbtId();
		if (propertyName.equals("prsBk"))
			return getPrsBk();
		if (propertyName.equals("prsCreatedUsrId"))
			return getPrsCreatedUsrId();
		if (propertyName.equals("prsModifiedUsrId"))
			return getPrsModifiedUsrId();
		if (propertyName.equals("prsCreatedDttm"))
			return getPrsCreatedDttm();
		if (propertyName.equals("prsModifiedDttm"))
			return getPrsModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getPrsId()
	{
		return prsId;
	}

	public void setPrsId(Integer prsId)
	{
		this.prsId = prsId;
	}

	public String getPrsName()
	{
		return prsName;
	}

	public void setPrsName(String prsName)
	{
		this.prsName = prsName;
	}

	public Integer getPrsBatchSize()
	{
		return prsBatchSize;
	}

	public void setPrsBatchSize(Integer prsBatchSize)
	{
		this.prsBatchSize = prsBatchSize;
	}

	public Integer getPrsFrequencySecs()
	{
		return prsFrequencySecs;
	}

	public void setPrsFrequencySecs(Integer prsFrequencySecs)
	{
		this.prsFrequencySecs = prsFrequencySecs;
	}

	public Integer getPrsTimeoutSecs()
	{
		return prsTimeoutSecs;
	}

	public void setPrsTimeoutSecs(Integer prsTimeoutSecs)
	{
		this.prsTimeoutSecs = prsTimeoutSecs;
	}

	public Integer getPrsPollerJbtId()
	{
		return prsPollerJbtId;
	}

	public void setPrsPollerJbtId(Integer prsPollerJbtId)
	{
		this.prsPollerJbtId = prsPollerJbtId;
	}

	public Integer getPrsTransferJbtId()
	{
		return prsTransferJbtId;
	}

	public void setPrsTransferJbtId(Integer prsTransferJbtId)
	{
		this.prsTransferJbtId = prsTransferJbtId;
	}

	public Integer getPrsRaterJbtId()
	{
		return prsRaterJbtId;
	}

	public void setPrsRaterJbtId(Integer prsRaterJbtId)
	{
		this.prsRaterJbtId = prsRaterJbtId;
	}

	public Integer getPrsReRaterJbtId()
	{
		return prsReRaterJbtId;
	}

	public void setPrsReRaterJbtId(Integer prsReRaterJbtId)
	{
		this.prsReRaterJbtId = prsReRaterJbtId;
	}

	public String getPrsBk()
	{
		return prsBk;
	}

	public void setPrsBk(String prsBk)
	{
		this.prsBk = prsBk;
	}

	public Integer getPrsCreatedUsrId()
	{
		return prsCreatedUsrId;
	}

	public void setPrsCreatedUsrId(Integer prsCreatedUsrId)
	{
		this.prsCreatedUsrId = prsCreatedUsrId;
	}

	public Integer getPrsModifiedUsrId()
	{
		return prsModifiedUsrId;
	}

	public void setPrsModifiedUsrId(Integer prsModifiedUsrId)
	{
		this.prsModifiedUsrId = prsModifiedUsrId;
	}

	public DateTime getPrsCreatedDttm()
	{
		return prsCreatedDttm;
	}

	public void setPrsCreatedDttm(DateTime prsCreatedDttm)
	{
		this.prsCreatedDttm = prsCreatedDttm;
	}

	public DateTime getPrsModifiedDttm()
	{
		return prsModifiedDttm;
	}

	public void setPrsModifiedDttm(DateTime prsModifiedDttm)
	{
		this.prsModifiedDttm = prsModifiedDttm;
	}

}
