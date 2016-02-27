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
import se.signa.signature.gen.dba.FilePollerSettingsDba;
import se.signa.signature.gen.dba.JobTypeDba;
import se.signa.signature.gen.dba.UserTblDba;
import se.signa.signature.helpers.DisplayFieldHelper;

public abstract class FileTransferSettings extends SignatureDbo
{
	private Integer ftsId;
	private String ftsName;
	private String ftsTargetDir;
	private Integer ftsTransferJbtId;
	private String ftsValidMask;
	private String ftsRerateMask;
	private Integer ftsStabilitySecs;
	private Integer ftsStabilityRetry;
	private Integer fpsId;
	private String ftsBk;
	private Integer ftsCreatedUsrId;
	private Integer ftsModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime ftsCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime ftsModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			ftsId = getInteger(rs.getInt("fts_id"));
			ftsName = rs.getString("fts_name");
			ftsTargetDir = rs.getString("fts_target_dir");
			ftsTransferJbtId = getInteger(rs.getInt("fts_transfer_jbt_id"));
			ftsValidMask = rs.getString("fts_valid_mask");
			ftsRerateMask = rs.getString("fts_rerate_mask");
			ftsStabilitySecs = rs.getInt("fts_stability_secs");
			ftsStabilityRetry = rs.getInt("fts_stability_retry");
			fpsId = getInteger(rs.getInt("fps_id"));
			ftsBk = rs.getString("fts_bk");
			ftsCreatedUsrId = getInteger(rs.getInt("fts_created_usr_id"));
			ftsModifiedUsrId = getInteger(rs.getInt("fts_modified_usr_id"));
			ftsCreatedDttm = getDateTime(rs.getTimestamp("fts_created_dttm"));
			ftsModifiedDttm = getDateTime(rs.getTimestamp("fts_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return ftsId;
	}

	public void setPk(int pk)
	{
		ftsId = pk;
	}

	public String getBk()
	{
		return ftsBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(ftsId.toString());
		data.add(ftsName.toString());
		data.add(ftsTargetDir.toString());
		data.add(ftsTransferJbtId.toString());
		data.add(ftsValidMask.toString());
		data.add(ftsRerateMask.toString());
		data.add(ftsStabilitySecs.toString());
		data.add(ftsStabilityRetry.toString());
		data.add(fpsId == null ? Constants.NULLSTRING : fpsId.toString());
		data.add(getFtsCreatedUsr());
		data.add(Constants.dttf.print(ftsCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("ftsName", getString(ftsName));
		data.put("ftsTargetDir", getString(ftsTargetDir));
		data.put("ftsTransferJbt", getFtsTransferJbt());
		data.put("ftsValidMask", getString(ftsValidMask));
		data.put("ftsRerateMask", getString(ftsRerateMask));
		data.put("ftsStabilitySecs", getString(ftsStabilitySecs));
		data.put("ftsStabilityRetry", getString(ftsStabilityRetry));
		data.put("fps", getFps());
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setFtsName(input.getString("ftsName"));
		setFtsTargetDir(input.getString("ftsTargetDir"));
		setFtsTransferJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("ftsTransferJbt")));
		setFtsValidMask(input.getString("ftsValidMask"));
		setFtsRerateMask(input.getString("ftsRerateMask"));
		setFtsStabilitySecs(input.getInteger("ftsStabilitySecs"));
		setFtsStabilityRetry(input.getInteger("ftsStabilityRetry"));
		setFpsId(DisplayFieldHelper.getI().getPk(FilePollerSettingsDba.class, input.getString("fps")));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(ftsBk.toString());
		auditData.add(getFtsCreatedUsr());
		auditData.add(ftsModifiedUsrId == null ? Constants.NULLSTRING : getFtsModifiedUsr());
		auditData.add(Constants.dttf.print(ftsCreatedDttm));
		auditData.add(ftsModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(ftsModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return ftsName;
	}

	public String getFtsTransferJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, ftsTransferJbtId);
	}

	public String getFps()
	{
		return DisplayFieldHelper.getI().getDisplayField(FilePollerSettingsDba.class, fpsId);
	}

	public String getFtsCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, ftsCreatedUsrId);
	}

	public String getFtsModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, ftsModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("ftsTransferJbt"))
			return getFtsTransferJbt();
		if (propertyName.equals("fps"))
			return getFps();
		if (propertyName.equals("ftsCreatedUsr"))
			return getFtsCreatedUsr();
		if (propertyName.equals("ftsModifiedUsr"))
			return getFtsModifiedUsr();
		if (propertyName.equals("ftsId"))
			return getFtsId();
		if (propertyName.equals("ftsName"))
			return getFtsName();
		if (propertyName.equals("ftsTargetDir"))
			return getFtsTargetDir();
		if (propertyName.equals("ftsTransferJbtId"))
			return getFtsTransferJbtId();
		if (propertyName.equals("ftsValidMask"))
			return getFtsValidMask();
		if (propertyName.equals("ftsRerateMask"))
			return getFtsRerateMask();
		if (propertyName.equals("ftsStabilitySecs"))
			return getFtsStabilitySecs();
		if (propertyName.equals("ftsStabilityRetry"))
			return getFtsStabilityRetry();
		if (propertyName.equals("fpsId"))
			return getFpsId();
		if (propertyName.equals("ftsBk"))
			return getFtsBk();
		if (propertyName.equals("ftsCreatedUsrId"))
			return getFtsCreatedUsrId();
		if (propertyName.equals("ftsModifiedUsrId"))
			return getFtsModifiedUsrId();
		if (propertyName.equals("ftsCreatedDttm"))
			return getFtsCreatedDttm();
		if (propertyName.equals("ftsModifiedDttm"))
			return getFtsModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getFtsId()
	{
		return ftsId;
	}

	public void setFtsId(Integer ftsId)
	{
		this.ftsId = ftsId;
	}

	public String getFtsName()
	{
		return ftsName;
	}

	public void setFtsName(String ftsName)
	{
		this.ftsName = ftsName;
	}

	public String getFtsTargetDir()
	{
		return ftsTargetDir;
	}

	public void setFtsTargetDir(String ftsTargetDir)
	{
		this.ftsTargetDir = ftsTargetDir;
	}

	public Integer getFtsTransferJbtId()
	{
		return ftsTransferJbtId;
	}

	public void setFtsTransferJbtId(Integer ftsTransferJbtId)
	{
		this.ftsTransferJbtId = ftsTransferJbtId;
	}

	public String getFtsValidMask()
	{
		return ftsValidMask;
	}

	public void setFtsValidMask(String ftsValidMask)
	{
		this.ftsValidMask = ftsValidMask;
	}

	public String getFtsRerateMask()
	{
		return ftsRerateMask;
	}

	public void setFtsRerateMask(String ftsRerateMask)
	{
		this.ftsRerateMask = ftsRerateMask;
	}

	public Integer getFtsStabilitySecs()
	{
		return ftsStabilitySecs;
	}

	public void setFtsStabilitySecs(Integer ftsStabilitySecs)
	{
		this.ftsStabilitySecs = ftsStabilitySecs;
	}

	public Integer getFtsStabilityRetry()
	{
		return ftsStabilityRetry;
	}

	public void setFtsStabilityRetry(Integer ftsStabilityRetry)
	{
		this.ftsStabilityRetry = ftsStabilityRetry;
	}

	public Integer getFpsId()
	{
		return fpsId;
	}

	public void setFpsId(Integer fpsId)
	{
		this.fpsId = fpsId;
	}

	public String getFtsBk()
	{
		return ftsBk;
	}

	public void setFtsBk(String ftsBk)
	{
		this.ftsBk = ftsBk;
	}

	public Integer getFtsCreatedUsrId()
	{
		return ftsCreatedUsrId;
	}

	public void setFtsCreatedUsrId(Integer ftsCreatedUsrId)
	{
		this.ftsCreatedUsrId = ftsCreatedUsrId;
	}

	public Integer getFtsModifiedUsrId()
	{
		return ftsModifiedUsrId;
	}

	public void setFtsModifiedUsrId(Integer ftsModifiedUsrId)
	{
		this.ftsModifiedUsrId = ftsModifiedUsrId;
	}

	public DateTime getFtsCreatedDttm()
	{
		return ftsCreatedDttm;
	}

	public void setFtsCreatedDttm(DateTime ftsCreatedDttm)
	{
		this.ftsCreatedDttm = ftsCreatedDttm;
	}

	public DateTime getFtsModifiedDttm()
	{
		return ftsModifiedDttm;
	}

	public void setFtsModifiedDttm(DateTime ftsModifiedDttm)
	{
		this.ftsModifiedDttm = ftsModifiedDttm;
	}

}
