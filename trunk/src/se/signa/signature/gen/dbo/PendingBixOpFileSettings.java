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

public abstract class PendingBixOpFileSettings extends SignatureDbo
{
	private Integer pbsId;
	private String pbsName;
	private Integer pbsBatchSize;
	private Integer pbsFrequencySecs;
	private Integer pbsTimeoutSecs;
	private Integer pbsPollerJbtId;
	private Integer pbsBixExportJbtId;
	private String pbsBk;
	private Integer pbsCreatedUsrId;
	private Integer pbsModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime pbsCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime pbsModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			pbsId = getInteger(rs.getInt("pbs_id"));
			pbsName = rs.getString("pbs_name");
			pbsBatchSize = rs.getInt("pbs_batch_size");
			pbsFrequencySecs = rs.getInt("pbs_frequency_secs");
			pbsTimeoutSecs = rs.getInt("pbs_timeout_secs");
			pbsPollerJbtId = getInteger(rs.getInt("pbs_poller_jbt_id"));
			pbsBixExportJbtId = getInteger(rs.getInt("pbs_bix_export_jbt_id"));
			pbsBk = rs.getString("pbs_bk");
			pbsCreatedUsrId = getInteger(rs.getInt("pbs_created_usr_id"));
			pbsModifiedUsrId = getInteger(rs.getInt("pbs_modified_usr_id"));
			pbsCreatedDttm = getDateTime(rs.getTimestamp("pbs_created_dttm"));
			pbsModifiedDttm = getDateTime(rs.getTimestamp("pbs_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return pbsId;
	}

	public void setPk(int pk)
	{
		pbsId = pk;
	}

	public String getBk()
	{
		return pbsBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(pbsId.toString());
		data.add(pbsName.toString());
		data.add(pbsBatchSize == null ? Constants.NULLSTRING : pbsBatchSize.toString());
		data.add(pbsFrequencySecs.toString());
		data.add(pbsTimeoutSecs.toString());
		data.add(pbsPollerJbtId.toString());
		data.add(pbsBixExportJbtId.toString());
		data.add(getPbsCreatedUsr());
		data.add(Constants.dttf.print(pbsCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("pbsName", getString(pbsName));
		data.put("pbsBatchSize", getString(pbsBatchSize));
		data.put("pbsFrequencySecs", getString(pbsFrequencySecs));
		data.put("pbsTimeoutSecs", getString(pbsTimeoutSecs));
		data.put("pbsPollerJbt", getPbsPollerJbt());
		data.put("pbsBixExportJbt", getPbsBixExportJbt());
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setPbsName(input.getString("pbsName"));
		setPbsBatchSize(input.getInteger("pbsBatchSize"));
		setPbsFrequencySecs(input.getInteger("pbsFrequencySecs"));
		setPbsTimeoutSecs(input.getInteger("pbsTimeoutSecs"));
		setPbsPollerJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("pbsPollerJbt")));
		setPbsBixExportJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("pbsBixExportJbt")));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(pbsBk.toString());
		auditData.add(getPbsCreatedUsr());
		auditData.add(pbsModifiedUsrId == null ? Constants.NULLSTRING : getPbsModifiedUsr());
		auditData.add(Constants.dttf.print(pbsCreatedDttm));
		auditData.add(pbsModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(pbsModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return pbsName;
	}

	public String getPbsPollerJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, pbsPollerJbtId);
	}

	public String getPbsBixExportJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, pbsBixExportJbtId);
	}

	public String getPbsCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, pbsCreatedUsrId);
	}

	public String getPbsModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, pbsModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("pbsPollerJbt"))
			return getPbsPollerJbt();
		if (propertyName.equals("pbsBixExportJbt"))
			return getPbsBixExportJbt();
		if (propertyName.equals("pbsCreatedUsr"))
			return getPbsCreatedUsr();
		if (propertyName.equals("pbsModifiedUsr"))
			return getPbsModifiedUsr();
		if (propertyName.equals("pbsId"))
			return getPbsId();
		if (propertyName.equals("pbsName"))
			return getPbsName();
		if (propertyName.equals("pbsBatchSize"))
			return getPbsBatchSize();
		if (propertyName.equals("pbsFrequencySecs"))
			return getPbsFrequencySecs();
		if (propertyName.equals("pbsTimeoutSecs"))
			return getPbsTimeoutSecs();
		if (propertyName.equals("pbsPollerJbtId"))
			return getPbsPollerJbtId();
		if (propertyName.equals("pbsBixExportJbtId"))
			return getPbsBixExportJbtId();
		if (propertyName.equals("pbsBk"))
			return getPbsBk();
		if (propertyName.equals("pbsCreatedUsrId"))
			return getPbsCreatedUsrId();
		if (propertyName.equals("pbsModifiedUsrId"))
			return getPbsModifiedUsrId();
		if (propertyName.equals("pbsCreatedDttm"))
			return getPbsCreatedDttm();
		if (propertyName.equals("pbsModifiedDttm"))
			return getPbsModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getPbsId()
	{
		return pbsId;
	}

	public void setPbsId(Integer pbsId)
	{
		this.pbsId = pbsId;
	}

	public String getPbsName()
	{
		return pbsName;
	}

	public void setPbsName(String pbsName)
	{
		this.pbsName = pbsName;
	}

	public Integer getPbsBatchSize()
	{
		return pbsBatchSize;
	}

	public void setPbsBatchSize(Integer pbsBatchSize)
	{
		this.pbsBatchSize = pbsBatchSize;
	}

	public Integer getPbsFrequencySecs()
	{
		return pbsFrequencySecs;
	}

	public void setPbsFrequencySecs(Integer pbsFrequencySecs)
	{
		this.pbsFrequencySecs = pbsFrequencySecs;
	}

	public Integer getPbsTimeoutSecs()
	{
		return pbsTimeoutSecs;
	}

	public void setPbsTimeoutSecs(Integer pbsTimeoutSecs)
	{
		this.pbsTimeoutSecs = pbsTimeoutSecs;
	}

	public Integer getPbsPollerJbtId()
	{
		return pbsPollerJbtId;
	}

	public void setPbsPollerJbtId(Integer pbsPollerJbtId)
	{
		this.pbsPollerJbtId = pbsPollerJbtId;
	}

	public Integer getPbsBixExportJbtId()
	{
		return pbsBixExportJbtId;
	}

	public void setPbsBixExportJbtId(Integer pbsBixExportJbtId)
	{
		this.pbsBixExportJbtId = pbsBixExportJbtId;
	}

	public String getPbsBk()
	{
		return pbsBk;
	}

	public void setPbsBk(String pbsBk)
	{
		this.pbsBk = pbsBk;
	}

	public Integer getPbsCreatedUsrId()
	{
		return pbsCreatedUsrId;
	}

	public void setPbsCreatedUsrId(Integer pbsCreatedUsrId)
	{
		this.pbsCreatedUsrId = pbsCreatedUsrId;
	}

	public Integer getPbsModifiedUsrId()
	{
		return pbsModifiedUsrId;
	}

	public void setPbsModifiedUsrId(Integer pbsModifiedUsrId)
	{
		this.pbsModifiedUsrId = pbsModifiedUsrId;
	}

	public DateTime getPbsCreatedDttm()
	{
		return pbsCreatedDttm;
	}

	public void setPbsCreatedDttm(DateTime pbsCreatedDttm)
	{
		this.pbsCreatedDttm = pbsCreatedDttm;
	}

	public DateTime getPbsModifiedDttm()
	{
		return pbsModifiedDttm;
	}

	public void setPbsModifiedDttm(DateTime pbsModifiedDttm)
	{
		this.pbsModifiedDttm = pbsModifiedDttm;
	}

}
