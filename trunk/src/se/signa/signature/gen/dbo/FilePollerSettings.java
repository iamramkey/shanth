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

public abstract class FilePollerSettings extends SignatureDbo
{
	private Integer fpsId;
	private String fpsName;
	private String fpsSourceDir;
	private Integer fpsFreqSecs;
	private Integer fpsPollerJbtId;
	private Integer fpsTransferJbtId;
	private String fpsBk;
	private Integer fpsCreatedUsrId;
	private Integer fpsModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime fpsCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime fpsModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			fpsId = getInteger(rs.getInt("fps_id"));
			fpsName = rs.getString("fps_name");
			fpsSourceDir = rs.getString("fps_source_dir");
			fpsFreqSecs = rs.getInt("fps_freq_secs");
			fpsPollerJbtId = getInteger(rs.getInt("fps_poller_jbt_id"));
			fpsTransferJbtId = getInteger(rs.getInt("fps_transfer_jbt_id"));
			fpsBk = rs.getString("fps_bk");
			fpsCreatedUsrId = getInteger(rs.getInt("fps_created_usr_id"));
			fpsModifiedUsrId = getInteger(rs.getInt("fps_modified_usr_id"));
			fpsCreatedDttm = getDateTime(rs.getTimestamp("fps_created_dttm"));
			fpsModifiedDttm = getDateTime(rs.getTimestamp("fps_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return fpsId;
	}

	public void setPk(int pk)
	{
		fpsId = pk;
	}

	public String getBk()
	{
		return fpsBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(fpsId.toString());
		data.add(fpsName.toString());
		data.add(fpsSourceDir.toString());
		data.add(fpsFreqSecs.toString());
		data.add(fpsPollerJbtId.toString());
		data.add(fpsTransferJbtId.toString());
		data.add(getFpsCreatedUsr());
		data.add(Constants.dttf.print(fpsCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("fpsName", getString(fpsName));
		data.put("fpsSourceDir", getString(fpsSourceDir));
		data.put("fpsFreqSecs", getString(fpsFreqSecs));
		data.put("fpsPollerJbt", getFpsPollerJbt());
		data.put("fpsTransferJbt", getFpsTransferJbt());
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setFpsName(input.getString("fpsName"));
		setFpsSourceDir(input.getString("fpsSourceDir"));
		setFpsFreqSecs(input.getInteger("fpsFreqSecs"));
		setFpsPollerJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("fpsPollerJbt")));
		setFpsTransferJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("fpsTransferJbt")));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(fpsBk.toString());
		auditData.add(getFpsCreatedUsr());
		auditData.add(fpsModifiedUsrId == null ? Constants.NULLSTRING : getFpsModifiedUsr());
		auditData.add(Constants.dttf.print(fpsCreatedDttm));
		auditData.add(fpsModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(fpsModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return fpsName;
	}

	public String getFpsPollerJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, fpsPollerJbtId);
	}

	public String getFpsTransferJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, fpsTransferJbtId);
	}

	public String getFpsCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, fpsCreatedUsrId);
	}

	public String getFpsModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, fpsModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("fpsPollerJbt"))
			return getFpsPollerJbt();
		if (propertyName.equals("fpsTransferJbt"))
			return getFpsTransferJbt();
		if (propertyName.equals("fpsCreatedUsr"))
			return getFpsCreatedUsr();
		if (propertyName.equals("fpsModifiedUsr"))
			return getFpsModifiedUsr();
		if (propertyName.equals("fpsId"))
			return getFpsId();
		if (propertyName.equals("fpsName"))
			return getFpsName();
		if (propertyName.equals("fpsSourceDir"))
			return getFpsSourceDir();
		if (propertyName.equals("fpsFreqSecs"))
			return getFpsFreqSecs();
		if (propertyName.equals("fpsPollerJbtId"))
			return getFpsPollerJbtId();
		if (propertyName.equals("fpsTransferJbtId"))
			return getFpsTransferJbtId();
		if (propertyName.equals("fpsBk"))
			return getFpsBk();
		if (propertyName.equals("fpsCreatedUsrId"))
			return getFpsCreatedUsrId();
		if (propertyName.equals("fpsModifiedUsrId"))
			return getFpsModifiedUsrId();
		if (propertyName.equals("fpsCreatedDttm"))
			return getFpsCreatedDttm();
		if (propertyName.equals("fpsModifiedDttm"))
			return getFpsModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getFpsId()
	{
		return fpsId;
	}

	public void setFpsId(Integer fpsId)
	{
		this.fpsId = fpsId;
	}

	public String getFpsName()
	{
		return fpsName;
	}

	public void setFpsName(String fpsName)
	{
		this.fpsName = fpsName;
	}

	public String getFpsSourceDir()
	{
		return fpsSourceDir;
	}

	public void setFpsSourceDir(String fpsSourceDir)
	{
		this.fpsSourceDir = fpsSourceDir;
	}

	public Integer getFpsFreqSecs()
	{
		return fpsFreqSecs;
	}

	public void setFpsFreqSecs(Integer fpsFreqSecs)
	{
		this.fpsFreqSecs = fpsFreqSecs;
	}

	public Integer getFpsPollerJbtId()
	{
		return fpsPollerJbtId;
	}

	public void setFpsPollerJbtId(Integer fpsPollerJbtId)
	{
		this.fpsPollerJbtId = fpsPollerJbtId;
	}

	public Integer getFpsTransferJbtId()
	{
		return fpsTransferJbtId;
	}

	public void setFpsTransferJbtId(Integer fpsTransferJbtId)
	{
		this.fpsTransferJbtId = fpsTransferJbtId;
	}

	public String getFpsBk()
	{
		return fpsBk;
	}

	public void setFpsBk(String fpsBk)
	{
		this.fpsBk = fpsBk;
	}

	public Integer getFpsCreatedUsrId()
	{
		return fpsCreatedUsrId;
	}

	public void setFpsCreatedUsrId(Integer fpsCreatedUsrId)
	{
		this.fpsCreatedUsrId = fpsCreatedUsrId;
	}

	public Integer getFpsModifiedUsrId()
	{
		return fpsModifiedUsrId;
	}

	public void setFpsModifiedUsrId(Integer fpsModifiedUsrId)
	{
		this.fpsModifiedUsrId = fpsModifiedUsrId;
	}

	public DateTime getFpsCreatedDttm()
	{
		return fpsCreatedDttm;
	}

	public void setFpsCreatedDttm(DateTime fpsCreatedDttm)
	{
		this.fpsCreatedDttm = fpsCreatedDttm;
	}

	public DateTime getFpsModifiedDttm()
	{
		return fpsModifiedDttm;
	}

	public void setFpsModifiedDttm(DateTime fpsModifiedDttm)
	{
		this.fpsModifiedDttm = fpsModifiedDttm;
	}

}
