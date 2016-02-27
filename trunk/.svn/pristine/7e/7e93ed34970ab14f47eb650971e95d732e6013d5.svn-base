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

public abstract class PendingCcSettings extends SignatureDbo
{
	private Integer pcsId;
	private String pcsName;
	private Integer pcsBatchSize;
	private Integer pcsFrequencySecs;
	private Integer pcsTimeoutSecs;
	private Integer pcsPollerJbtId;
	private Integer pcsCcJbtId;
	private String pcsBk;
	private Integer pcsCreatedUsrId;
	private Integer pcsModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime pcsCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime pcsModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			pcsId = getInteger(rs.getInt("pcs_id"));
			pcsName = rs.getString("pcs_name");
			pcsBatchSize = rs.getInt("pcs_batch_size");
			pcsFrequencySecs = rs.getInt("pcs_frequency_secs");
			pcsTimeoutSecs = rs.getInt("pcs_timeout_secs");
			pcsPollerJbtId = getInteger(rs.getInt("pcs_poller_jbt_id"));
			pcsCcJbtId = getInteger(rs.getInt("pcs_cc_jbt_id"));
			pcsBk = rs.getString("pcs_bk");
			pcsCreatedUsrId = getInteger(rs.getInt("pcs_created_usr_id"));
			pcsModifiedUsrId = getInteger(rs.getInt("pcs_modified_usr_id"));
			pcsCreatedDttm = getDateTime(rs.getTimestamp("pcs_created_dttm"));
			pcsModifiedDttm = getDateTime(rs.getTimestamp("pcs_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return pcsId;
	}

	public void setPk(int pk)
	{
		pcsId = pk;
	}

	public String getBk()
	{
		return pcsBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(pcsId.toString());
		data.add(pcsName.toString());
		data.add(pcsBatchSize == null ? Constants.NULLSTRING : pcsBatchSize.toString());
		data.add(pcsFrequencySecs.toString());
		data.add(pcsTimeoutSecs.toString());
		data.add(pcsPollerJbtId.toString());
		data.add(pcsCcJbtId.toString());
		data.add(getPcsCreatedUsr());
		data.add(Constants.dttf.print(pcsCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("pcsName", getString(pcsName));
		data.put("pcsBatchSize", getString(pcsBatchSize));
		data.put("pcsFrequencySecs", getString(pcsFrequencySecs));
		data.put("pcsTimeoutSecs", getString(pcsTimeoutSecs));
		data.put("pcsPollerJbt", getPcsPollerJbt());
		data.put("pcsCcJbt", getPcsCcJbt());
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setPcsName(input.getString("pcsName"));
		setPcsBatchSize(input.getInteger("pcsBatchSize"));
		setPcsFrequencySecs(input.getInteger("pcsFrequencySecs"));
		setPcsTimeoutSecs(input.getInteger("pcsTimeoutSecs"));
		setPcsPollerJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("pcsPollerJbt")));
		setPcsCcJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("pcsCcJbt")));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(pcsBk.toString());
		auditData.add(getPcsCreatedUsr());
		auditData.add(pcsModifiedUsrId == null ? Constants.NULLSTRING : getPcsModifiedUsr());
		auditData.add(Constants.dttf.print(pcsCreatedDttm));
		auditData.add(pcsModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(pcsModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return pcsName;
	}

	public String getPcsPollerJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, pcsPollerJbtId);
	}

	public String getPcsCcJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, pcsCcJbtId);
	}

	public String getPcsCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, pcsCreatedUsrId);
	}

	public String getPcsModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, pcsModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("pcsPollerJbt"))
			return getPcsPollerJbt();
		if (propertyName.equals("pcsCcJbt"))
			return getPcsCcJbt();
		if (propertyName.equals("pcsCreatedUsr"))
			return getPcsCreatedUsr();
		if (propertyName.equals("pcsModifiedUsr"))
			return getPcsModifiedUsr();
		if (propertyName.equals("pcsId"))
			return getPcsId();
		if (propertyName.equals("pcsName"))
			return getPcsName();
		if (propertyName.equals("pcsBatchSize"))
			return getPcsBatchSize();
		if (propertyName.equals("pcsFrequencySecs"))
			return getPcsFrequencySecs();
		if (propertyName.equals("pcsTimeoutSecs"))
			return getPcsTimeoutSecs();
		if (propertyName.equals("pcsPollerJbtId"))
			return getPcsPollerJbtId();
		if (propertyName.equals("pcsCcJbtId"))
			return getPcsCcJbtId();
		if (propertyName.equals("pcsBk"))
			return getPcsBk();
		if (propertyName.equals("pcsCreatedUsrId"))
			return getPcsCreatedUsrId();
		if (propertyName.equals("pcsModifiedUsrId"))
			return getPcsModifiedUsrId();
		if (propertyName.equals("pcsCreatedDttm"))
			return getPcsCreatedDttm();
		if (propertyName.equals("pcsModifiedDttm"))
			return getPcsModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getPcsId()
	{
		return pcsId;
	}

	public void setPcsId(Integer pcsId)
	{
		this.pcsId = pcsId;
	}

	public String getPcsName()
	{
		return pcsName;
	}

	public void setPcsName(String pcsName)
	{
		this.pcsName = pcsName;
	}

	public Integer getPcsBatchSize()
	{
		return pcsBatchSize;
	}

	public void setPcsBatchSize(Integer pcsBatchSize)
	{
		this.pcsBatchSize = pcsBatchSize;
	}

	public Integer getPcsFrequencySecs()
	{
		return pcsFrequencySecs;
	}

	public void setPcsFrequencySecs(Integer pcsFrequencySecs)
	{
		this.pcsFrequencySecs = pcsFrequencySecs;
	}

	public Integer getPcsTimeoutSecs()
	{
		return pcsTimeoutSecs;
	}

	public void setPcsTimeoutSecs(Integer pcsTimeoutSecs)
	{
		this.pcsTimeoutSecs = pcsTimeoutSecs;
	}

	public Integer getPcsPollerJbtId()
	{
		return pcsPollerJbtId;
	}

	public void setPcsPollerJbtId(Integer pcsPollerJbtId)
	{
		this.pcsPollerJbtId = pcsPollerJbtId;
	}

	public Integer getPcsCcJbtId()
	{
		return pcsCcJbtId;
	}

	public void setPcsCcJbtId(Integer pcsCcJbtId)
	{
		this.pcsCcJbtId = pcsCcJbtId;
	}

	public String getPcsBk()
	{
		return pcsBk;
	}

	public void setPcsBk(String pcsBk)
	{
		this.pcsBk = pcsBk;
	}

	public Integer getPcsCreatedUsrId()
	{
		return pcsCreatedUsrId;
	}

	public void setPcsCreatedUsrId(Integer pcsCreatedUsrId)
	{
		this.pcsCreatedUsrId = pcsCreatedUsrId;
	}

	public Integer getPcsModifiedUsrId()
	{
		return pcsModifiedUsrId;
	}

	public void setPcsModifiedUsrId(Integer pcsModifiedUsrId)
	{
		this.pcsModifiedUsrId = pcsModifiedUsrId;
	}

	public DateTime getPcsCreatedDttm()
	{
		return pcsCreatedDttm;
	}

	public void setPcsCreatedDttm(DateTime pcsCreatedDttm)
	{
		this.pcsCreatedDttm = pcsCreatedDttm;
	}

	public DateTime getPcsModifiedDttm()
	{
		return pcsModifiedDttm;
	}

	public void setPcsModifiedDttm(DateTime pcsModifiedDttm)
	{
		this.pcsModifiedDttm = pcsModifiedDttm;
	}

}
