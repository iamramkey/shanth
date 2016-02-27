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

public abstract class PendingCrSettings extends SignatureDbo
{
	private Integer pcpId;
	private String pcpName;
	private Integer pcpBatchSize;
	private Integer pcpFreqSecs;
	private Integer pcpPollerJbtId;
	private Integer pcpCorrelatorJbtId;
	private String pcpBk;
	private Integer pcpCreatedUsrId;
	private Integer pcpModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime pcpCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime pcpModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			pcpId = getInteger(rs.getInt("pcp_id"));
			pcpName = rs.getString("pcp_name");
			pcpBatchSize = rs.getInt("pcp_batch_size");
			pcpFreqSecs = rs.getInt("pcp_freq_secs");
			pcpPollerJbtId = getInteger(rs.getInt("pcp_poller_jbt_id"));
			pcpCorrelatorJbtId = getInteger(rs.getInt("pcp_correlator_jbt_id"));
			pcpBk = rs.getString("pcp_bk");
			pcpCreatedUsrId = getInteger(rs.getInt("pcp_created_usr_id"));
			pcpModifiedUsrId = getInteger(rs.getInt("pcp_modified_usr_id"));
			pcpCreatedDttm = getDateTime(rs.getTimestamp("pcp_created_dttm"));
			pcpModifiedDttm = getDateTime(rs.getTimestamp("pcp_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return pcpId;
	}

	public void setPk(int pk)
	{
		pcpId = pk;
	}

	public String getBk()
	{
		return pcpBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(pcpId.toString());
		data.add(pcpName.toString());
		data.add(pcpBatchSize.toString());
		data.add(pcpFreqSecs.toString());
		data.add(pcpPollerJbtId.toString());
		data.add(pcpCorrelatorJbtId.toString());
		data.add(getPcpCreatedUsr());
		data.add(Constants.dttf.print(pcpCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("pcpName", getString(pcpName));
		data.put("pcpBatchSize", getString(pcpBatchSize));
		data.put("pcpFreqSecs", getString(pcpFreqSecs));
		data.put("pcpPollerJbt", getPcpPollerJbt());
		data.put("pcpCorrelatorJbt", getPcpCorrelatorJbt());
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setPcpName(input.getString("pcpName"));
		setPcpBatchSize(input.getInteger("pcpBatchSize"));
		setPcpFreqSecs(input.getInteger("pcpFreqSecs"));
		setPcpPollerJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("pcpPollerJbt")));
		setPcpCorrelatorJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("pcpCorrelatorJbt")));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(pcpBk.toString());
		auditData.add(getPcpCreatedUsr());
		auditData.add(pcpModifiedUsrId == null ? Constants.NULLSTRING : getPcpModifiedUsr());
		auditData.add(Constants.dttf.print(pcpCreatedDttm));
		auditData.add(pcpModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(pcpModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return pcpName;
	}

	public String getPcpPollerJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, pcpPollerJbtId);
	}

	public String getPcpCorrelatorJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, pcpCorrelatorJbtId);
	}

	public String getPcpCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, pcpCreatedUsrId);
	}

	public String getPcpModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, pcpModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("pcpPollerJbt"))
			return getPcpPollerJbt();
		if (propertyName.equals("pcpCorrelatorJbt"))
			return getPcpCorrelatorJbt();
		if (propertyName.equals("pcpCreatedUsr"))
			return getPcpCreatedUsr();
		if (propertyName.equals("pcpModifiedUsr"))
			return getPcpModifiedUsr();
		if (propertyName.equals("pcpId"))
			return getPcpId();
		if (propertyName.equals("pcpName"))
			return getPcpName();
		if (propertyName.equals("pcpBatchSize"))
			return getPcpBatchSize();
		if (propertyName.equals("pcpFreqSecs"))
			return getPcpFreqSecs();
		if (propertyName.equals("pcpPollerJbtId"))
			return getPcpPollerJbtId();
		if (propertyName.equals("pcpCorrelatorJbtId"))
			return getPcpCorrelatorJbtId();
		if (propertyName.equals("pcpBk"))
			return getPcpBk();
		if (propertyName.equals("pcpCreatedUsrId"))
			return getPcpCreatedUsrId();
		if (propertyName.equals("pcpModifiedUsrId"))
			return getPcpModifiedUsrId();
		if (propertyName.equals("pcpCreatedDttm"))
			return getPcpCreatedDttm();
		if (propertyName.equals("pcpModifiedDttm"))
			return getPcpModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getPcpId()
	{
		return pcpId;
	}

	public void setPcpId(Integer pcpId)
	{
		this.pcpId = pcpId;
	}

	public String getPcpName()
	{
		return pcpName;
	}

	public void setPcpName(String pcpName)
	{
		this.pcpName = pcpName;
	}

	public Integer getPcpBatchSize()
	{
		return pcpBatchSize;
	}

	public void setPcpBatchSize(Integer pcpBatchSize)
	{
		this.pcpBatchSize = pcpBatchSize;
	}

	public Integer getPcpFreqSecs()
	{
		return pcpFreqSecs;
	}

	public void setPcpFreqSecs(Integer pcpFreqSecs)
	{
		this.pcpFreqSecs = pcpFreqSecs;
	}

	public Integer getPcpPollerJbtId()
	{
		return pcpPollerJbtId;
	}

	public void setPcpPollerJbtId(Integer pcpPollerJbtId)
	{
		this.pcpPollerJbtId = pcpPollerJbtId;
	}

	public Integer getPcpCorrelatorJbtId()
	{
		return pcpCorrelatorJbtId;
	}

	public void setPcpCorrelatorJbtId(Integer pcpCorrelatorJbtId)
	{
		this.pcpCorrelatorJbtId = pcpCorrelatorJbtId;
	}

	public String getPcpBk()
	{
		return pcpBk;
	}

	public void setPcpBk(String pcpBk)
	{
		this.pcpBk = pcpBk;
	}

	public Integer getPcpCreatedUsrId()
	{
		return pcpCreatedUsrId;
	}

	public void setPcpCreatedUsrId(Integer pcpCreatedUsrId)
	{
		this.pcpCreatedUsrId = pcpCreatedUsrId;
	}

	public Integer getPcpModifiedUsrId()
	{
		return pcpModifiedUsrId;
	}

	public void setPcpModifiedUsrId(Integer pcpModifiedUsrId)
	{
		this.pcpModifiedUsrId = pcpModifiedUsrId;
	}

	public DateTime getPcpCreatedDttm()
	{
		return pcpCreatedDttm;
	}

	public void setPcpCreatedDttm(DateTime pcpCreatedDttm)
	{
		this.pcpCreatedDttm = pcpCreatedDttm;
	}

	public DateTime getPcpModifiedDttm()
	{
		return pcpModifiedDttm;
	}

	public void setPcpModifiedDttm(DateTime pcpModifiedDttm)
	{
		this.pcpModifiedDttm = pcpModifiedDttm;
	}

}
