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

public abstract class PendingFileTransferSettings extends SignatureDbo
{
	private Integer pesId;
	private String pesName;
	private Integer pesBatchSize;
	private Integer pesFrequencySecs;
	private Integer pesTimeoutSecs;
	private Integer ftsId;
	private Integer pesPollerJbtId;
	private Integer pesParserJbtId;
	private String pesBk;
	private Integer pesCreatedUsrId;
	private Integer pesModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime pesCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime pesModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			pesId = getInteger(rs.getInt("pes_id"));
			pesName = rs.getString("pes_name");
			pesBatchSize = rs.getInt("pes_batch_size");
			pesFrequencySecs = rs.getInt("pes_frequency_secs");
			pesTimeoutSecs = rs.getInt("pes_timeout_secs");
			ftsId = getInteger(rs.getInt("fts_id"));
			pesPollerJbtId = getInteger(rs.getInt("pes_poller_jbt_id"));
			pesParserJbtId = getInteger(rs.getInt("pes_parser_jbt_id"));
			pesBk = rs.getString("pes_bk");
			pesCreatedUsrId = getInteger(rs.getInt("pes_created_usr_id"));
			pesModifiedUsrId = getInteger(rs.getInt("pes_modified_usr_id"));
			pesCreatedDttm = getDateTime(rs.getTimestamp("pes_created_dttm"));
			pesModifiedDttm = getDateTime(rs.getTimestamp("pes_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return pesId;
	}

	public void setPk(int pk)
	{
		pesId = pk;
	}

	public String getBk()
	{
		return pesBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(pesId.toString());
		data.add(pesName.toString());
		data.add(pesBatchSize == null ? Constants.NULLSTRING : pesBatchSize.toString());
		data.add(pesFrequencySecs.toString());
		data.add(pesTimeoutSecs.toString());
		data.add(ftsId.toString());
		data.add(pesPollerJbtId.toString());
		data.add(pesParserJbtId.toString());
		data.add(getPesCreatedUsr());
		data.add(Constants.dttf.print(pesCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("pesName", getString(pesName));
		data.put("pesBatchSize", getString(pesBatchSize));
		data.put("pesFrequencySecs", getString(pesFrequencySecs));
		data.put("pesTimeoutSecs", getString(pesTimeoutSecs));
		data.put("fts", getFts());
		data.put("pesPollerJbt", getPesPollerJbt());
		data.put("pesParserJbt", getPesParserJbt());
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setPesName(input.getString("pesName"));
		setPesBatchSize(input.getInteger("pesBatchSize"));
		setPesFrequencySecs(input.getInteger("pesFrequencySecs"));
		setPesTimeoutSecs(input.getInteger("pesTimeoutSecs"));
		setFtsId(DisplayFieldHelper.getI().getPk(FileTransferSettingsDba.class, input.getString("fts")));
		setPesPollerJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("pesPollerJbt")));
		setPesParserJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("pesParserJbt")));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(pesBk.toString());
		auditData.add(getPesCreatedUsr());
		auditData.add(pesModifiedUsrId == null ? Constants.NULLSTRING : getPesModifiedUsr());
		auditData.add(Constants.dttf.print(pesCreatedDttm));
		auditData.add(pesModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(pesModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return pesName;
	}

	public String getFts()
	{
		return DisplayFieldHelper.getI().getDisplayField(FileTransferSettingsDba.class, ftsId);
	}

	public String getPesPollerJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, pesPollerJbtId);
	}

	public String getPesParserJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, pesParserJbtId);
	}

	public String getPesCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, pesCreatedUsrId);
	}

	public String getPesModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, pesModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("fts"))
			return getFts();
		if (propertyName.equals("pesPollerJbt"))
			return getPesPollerJbt();
		if (propertyName.equals("pesParserJbt"))
			return getPesParserJbt();
		if (propertyName.equals("pesCreatedUsr"))
			return getPesCreatedUsr();
		if (propertyName.equals("pesModifiedUsr"))
			return getPesModifiedUsr();
		if (propertyName.equals("pesId"))
			return getPesId();
		if (propertyName.equals("pesName"))
			return getPesName();
		if (propertyName.equals("pesBatchSize"))
			return getPesBatchSize();
		if (propertyName.equals("pesFrequencySecs"))
			return getPesFrequencySecs();
		if (propertyName.equals("pesTimeoutSecs"))
			return getPesTimeoutSecs();
		if (propertyName.equals("ftsId"))
			return getFtsId();
		if (propertyName.equals("pesPollerJbtId"))
			return getPesPollerJbtId();
		if (propertyName.equals("pesParserJbtId"))
			return getPesParserJbtId();
		if (propertyName.equals("pesBk"))
			return getPesBk();
		if (propertyName.equals("pesCreatedUsrId"))
			return getPesCreatedUsrId();
		if (propertyName.equals("pesModifiedUsrId"))
			return getPesModifiedUsrId();
		if (propertyName.equals("pesCreatedDttm"))
			return getPesCreatedDttm();
		if (propertyName.equals("pesModifiedDttm"))
			return getPesModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getPesId()
	{
		return pesId;
	}

	public void setPesId(Integer pesId)
	{
		this.pesId = pesId;
	}

	public String getPesName()
	{
		return pesName;
	}

	public void setPesName(String pesName)
	{
		this.pesName = pesName;
	}

	public Integer getPesBatchSize()
	{
		return pesBatchSize;
	}

	public void setPesBatchSize(Integer pesBatchSize)
	{
		this.pesBatchSize = pesBatchSize;
	}

	public Integer getPesFrequencySecs()
	{
		return pesFrequencySecs;
	}

	public void setPesFrequencySecs(Integer pesFrequencySecs)
	{
		this.pesFrequencySecs = pesFrequencySecs;
	}

	public Integer getPesTimeoutSecs()
	{
		return pesTimeoutSecs;
	}

	public void setPesTimeoutSecs(Integer pesTimeoutSecs)
	{
		this.pesTimeoutSecs = pesTimeoutSecs;
	}

	public Integer getFtsId()
	{
		return ftsId;
	}

	public void setFtsId(Integer ftsId)
	{
		this.ftsId = ftsId;
	}

	public Integer getPesPollerJbtId()
	{
		return pesPollerJbtId;
	}

	public void setPesPollerJbtId(Integer pesPollerJbtId)
	{
		this.pesPollerJbtId = pesPollerJbtId;
	}

	public Integer getPesParserJbtId()
	{
		return pesParserJbtId;
	}

	public void setPesParserJbtId(Integer pesParserJbtId)
	{
		this.pesParserJbtId = pesParserJbtId;
	}

	public String getPesBk()
	{
		return pesBk;
	}

	public void setPesBk(String pesBk)
	{
		this.pesBk = pesBk;
	}

	public Integer getPesCreatedUsrId()
	{
		return pesCreatedUsrId;
	}

	public void setPesCreatedUsrId(Integer pesCreatedUsrId)
	{
		this.pesCreatedUsrId = pesCreatedUsrId;
	}

	public Integer getPesModifiedUsrId()
	{
		return pesModifiedUsrId;
	}

	public void setPesModifiedUsrId(Integer pesModifiedUsrId)
	{
		this.pesModifiedUsrId = pesModifiedUsrId;
	}

	public DateTime getPesCreatedDttm()
	{
		return pesCreatedDttm;
	}

	public void setPesCreatedDttm(DateTime pesCreatedDttm)
	{
		this.pesCreatedDttm = pesCreatedDttm;
	}

	public DateTime getPesModifiedDttm()
	{
		return pesModifiedDttm;
	}

	public void setPesModifiedDttm(DateTime pesModifiedDttm)
	{
		this.pesModifiedDttm = pesModifiedDttm;
	}

}
