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
import se.signa.signature.gen.dba.FileTransferSettingsDba;
import se.signa.signature.gen.dba.JobTypeDba;
import se.signa.signature.gen.dba.UserTblDba;
import se.signa.signature.helpers.DisplayFieldHelper;

public abstract class PendingRerateFts extends SignatureDbo
{
	private Integer prfId;
	private String prfName;
	private Integer prfBatchSize;
	private Integer prfFrequencySecs;
	private Integer prfTimeoutSecs;
	private Integer ftsId;
	private Integer prfReratePollerJbtId;
	private Integer prfRerateParserJbtId;
	private String prfBk;
	private Integer prfCreatedUsrId;
	private Integer prfModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime prfCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime prfModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			prfId = getInteger(rs.getInt("prf_id"));
			prfName = rs.getString("prf_name");
			prfBatchSize = rs.getInt("prf_batch_size");
			prfFrequencySecs = rs.getInt("prf_frequency_secs");
			prfTimeoutSecs = rs.getInt("prf_timeout_secs");
			ftsId = getInteger(rs.getInt("fts_id"));
			prfReratePollerJbtId = getInteger(rs.getInt("prf_rerate_poller_jbt_id"));
			prfRerateParserJbtId = getInteger(rs.getInt("prf_rerate_parser_jbt_id"));
			prfBk = rs.getString("prf_bk");
			prfCreatedUsrId = getInteger(rs.getInt("prf_created_usr_id"));
			prfModifiedUsrId = getInteger(rs.getInt("prf_modified_usr_id"));
			prfCreatedDttm = getDateTime(rs.getTimestamp("prf_created_dttm"));
			prfModifiedDttm = getDateTime(rs.getTimestamp("prf_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return prfId;
	}

	public void setPk(int pk)
	{
		prfId = pk;
	}

	public String getBk()
	{
		return prfBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(prfId.toString());
		data.add(prfName.toString());
		data.add(prfBatchSize == null ? Constants.NULLSTRING : prfBatchSize.toString());
		data.add(prfFrequencySecs.toString());
		data.add(prfTimeoutSecs.toString());
		data.add(ftsId.toString());
		data.add(prfReratePollerJbtId.toString());
		data.add(prfRerateParserJbtId.toString());
		data.add(getPrfCreatedUsr());
		data.add(Constants.dttf.print(prfCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("prfName", getString(prfName));
		data.put("prfBatchSize", getString(prfBatchSize));
		data.put("prfFrequencySecs", getString(prfFrequencySecs));
		data.put("prfTimeoutSecs", getString(prfTimeoutSecs));
		data.put("fts", getFts());
		data.put("prfReratePollerJbt", getPrfReratePollerJbt());
		data.put("prfRerateParserJbt", getPrfRerateParserJbt());
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setPrfName(input.getString("prfName"));
		setPrfBatchSize(input.getInteger("prfBatchSize"));
		setPrfFrequencySecs(input.getInteger("prfFrequencySecs"));
		setPrfTimeoutSecs(input.getInteger("prfTimeoutSecs"));
		setFtsId(DisplayFieldHelper.getI().getPk(FileTransferSettingsDba.class, input.getString("fts")));
		setPrfReratePollerJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("prfReratePollerJbt")));
		setPrfRerateParserJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("prfRerateParserJbt")));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(prfBk.toString());
		auditData.add(getPrfCreatedUsr());
		auditData.add(prfModifiedUsrId == null ? Constants.NULLSTRING : getPrfModifiedUsr());
		auditData.add(Constants.dttf.print(prfCreatedDttm));
		auditData.add(prfModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(prfModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return prfName;
	}

	public String getFts()
	{
		return DisplayFieldHelper.getI().getDisplayField(FileTransferSettingsDba.class, ftsId);
	}

	public String getPrfReratePollerJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, prfReratePollerJbtId);
	}

	public String getPrfRerateParserJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, prfRerateParserJbtId);
	}

	public String getPrfCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, prfCreatedUsrId);
	}

	public String getPrfModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, prfModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("fts"))
			return getFts();
		if (propertyName.equals("prfReratePollerJbt"))
			return getPrfReratePollerJbt();
		if (propertyName.equals("prfRerateParserJbt"))
			return getPrfRerateParserJbt();
		if (propertyName.equals("prfCreatedUsr"))
			return getPrfCreatedUsr();
		if (propertyName.equals("prfModifiedUsr"))
			return getPrfModifiedUsr();
		if (propertyName.equals("prfId"))
			return getPrfId();
		if (propertyName.equals("prfName"))
			return getPrfName();
		if (propertyName.equals("prfBatchSize"))
			return getPrfBatchSize();
		if (propertyName.equals("prfFrequencySecs"))
			return getPrfFrequencySecs();
		if (propertyName.equals("prfTimeoutSecs"))
			return getPrfTimeoutSecs();
		if (propertyName.equals("ftsId"))
			return getFtsId();
		if (propertyName.equals("prfReratePollerJbtId"))
			return getPrfReratePollerJbtId();
		if (propertyName.equals("prfRerateParserJbtId"))
			return getPrfRerateParserJbtId();
		if (propertyName.equals("prfBk"))
			return getPrfBk();
		if (propertyName.equals("prfCreatedUsrId"))
			return getPrfCreatedUsrId();
		if (propertyName.equals("prfModifiedUsrId"))
			return getPrfModifiedUsrId();
		if (propertyName.equals("prfCreatedDttm"))
			return getPrfCreatedDttm();
		if (propertyName.equals("prfModifiedDttm"))
			return getPrfModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getPrfId()
	{
		return prfId;
	}

	public void setPrfId(Integer prfId)
	{
		this.prfId = prfId;
	}

	public String getPrfName()
	{
		return prfName;
	}

	public void setPrfName(String prfName)
	{
		this.prfName = prfName;
	}

	public Integer getPrfBatchSize()
	{
		return prfBatchSize;
	}

	public void setPrfBatchSize(Integer prfBatchSize)
	{
		this.prfBatchSize = prfBatchSize;
	}

	public Integer getPrfFrequencySecs()
	{
		return prfFrequencySecs;
	}

	public void setPrfFrequencySecs(Integer prfFrequencySecs)
	{
		this.prfFrequencySecs = prfFrequencySecs;
	}

	public Integer getPrfTimeoutSecs()
	{
		return prfTimeoutSecs;
	}

	public void setPrfTimeoutSecs(Integer prfTimeoutSecs)
	{
		this.prfTimeoutSecs = prfTimeoutSecs;
	}

	public Integer getFtsId()
	{
		return ftsId;
	}

	public void setFtsId(Integer ftsId)
	{
		this.ftsId = ftsId;
	}

	public Integer getPrfReratePollerJbtId()
	{
		return prfReratePollerJbtId;
	}

	public void setPrfReratePollerJbtId(Integer prfReratePollerJbtId)
	{
		this.prfReratePollerJbtId = prfReratePollerJbtId;
	}

	public Integer getPrfRerateParserJbtId()
	{
		return prfRerateParserJbtId;
	}

	public void setPrfRerateParserJbtId(Integer prfRerateParserJbtId)
	{
		this.prfRerateParserJbtId = prfRerateParserJbtId;
	}

	public String getPrfBk()
	{
		return prfBk;
	}

	public void setPrfBk(String prfBk)
	{
		this.prfBk = prfBk;
	}

	public Integer getPrfCreatedUsrId()
	{
		return prfCreatedUsrId;
	}

	public void setPrfCreatedUsrId(Integer prfCreatedUsrId)
	{
		this.prfCreatedUsrId = prfCreatedUsrId;
	}

	public Integer getPrfModifiedUsrId()
	{
		return prfModifiedUsrId;
	}

	public void setPrfModifiedUsrId(Integer prfModifiedUsrId)
	{
		this.prfModifiedUsrId = prfModifiedUsrId;
	}

	public DateTime getPrfCreatedDttm()
	{
		return prfCreatedDttm;
	}

	public void setPrfCreatedDttm(DateTime prfCreatedDttm)
	{
		this.prfCreatedDttm = prfCreatedDttm;
	}

	public DateTime getPrfModifiedDttm()
	{
		return prfModifiedDttm;
	}

	public void setPrfModifiedDttm(DateTime prfModifiedDttm)
	{
		this.prfModifiedDttm = prfModifiedDttm;
	}

}
