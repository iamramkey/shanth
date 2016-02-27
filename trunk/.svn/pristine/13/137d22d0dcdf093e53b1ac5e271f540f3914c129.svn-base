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

public abstract class RaterSettings extends SignatureDbo
{
	private Integer rasId;
	private String rasName;
	private String rasTargetDir;
	private String rasBixTargetDir;
	private Integer rasFpsId;
	private Integer rasRaterJbtId;
	private Integer rasReRaterJbtId;
	private Integer rasDataloadJbtId;
	private String rasBk;
	private Integer rasCreatedUsrId;
	private Integer rasModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime rasCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime rasModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			rasId = getInteger(rs.getInt("ras_id"));
			rasName = rs.getString("ras_name");
			rasTargetDir = rs.getString("ras_target_dir");
			rasBixTargetDir = rs.getString("ras_bix_target_dir");
			rasFpsId = getInteger(rs.getInt("ras_fps_id"));
			rasRaterJbtId = getInteger(rs.getInt("ras_rater_jbt_id"));
			rasReRaterJbtId = getInteger(rs.getInt("ras_re_rater_jbt_id"));
			rasDataloadJbtId = getInteger(rs.getInt("ras_dataload_jbt_id"));
			rasBk = rs.getString("ras_bk");
			rasCreatedUsrId = getInteger(rs.getInt("ras_created_usr_id"));
			rasModifiedUsrId = getInteger(rs.getInt("ras_modified_usr_id"));
			rasCreatedDttm = getDateTime(rs.getTimestamp("ras_created_dttm"));
			rasModifiedDttm = getDateTime(rs.getTimestamp("ras_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return rasId;
	}

	public void setPk(int pk)
	{
		rasId = pk;
	}

	public String getBk()
	{
		return rasBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(rasId.toString());
		data.add(rasName.toString());
		data.add(rasTargetDir.toString());
		data.add(rasBixTargetDir.toString());
		data.add(rasFpsId.toString());
		data.add(rasRaterJbtId.toString());
		data.add(rasReRaterJbtId.toString());
		data.add(rasDataloadJbtId.toString());
		data.add(getRasCreatedUsr());
		data.add(Constants.dttf.print(rasCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("rasName", getString(rasName));
		data.put("rasTargetDir", getString(rasTargetDir));
		data.put("rasBixTargetDir", getString(rasBixTargetDir));
		data.put("rasFps", getRasFps());
		data.put("rasRaterJbt", getRasRaterJbt());
		data.put("rasReRaterJbt", getRasReRaterJbt());
		data.put("rasDataloadJbt", getRasDataloadJbt());
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setRasName(input.getString("rasName"));
		setRasTargetDir(input.getString("rasTargetDir"));
		setRasBixTargetDir(input.getString("rasBixTargetDir"));
		setRasFpsId(DisplayFieldHelper.getI().getPk(FilePollerSettingsDba.class, input.getString("rasFps")));
		setRasRaterJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("rasRaterJbt")));
		setRasReRaterJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("rasReRaterJbt")));
		setRasDataloadJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("rasDataloadJbt")));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(rasBk.toString());
		auditData.add(getRasCreatedUsr());
		auditData.add(rasModifiedUsrId == null ? Constants.NULLSTRING : getRasModifiedUsr());
		auditData.add(Constants.dttf.print(rasCreatedDttm));
		auditData.add(rasModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(rasModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return rasName;
	}

	public String getRasFps()
	{
		return DisplayFieldHelper.getI().getDisplayField(FilePollerSettingsDba.class, rasFpsId);
	}

	public String getRasRaterJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, rasRaterJbtId);
	}

	public String getRasReRaterJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, rasReRaterJbtId);
	}

	public String getRasDataloadJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, rasDataloadJbtId);
	}

	public String getRasCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, rasCreatedUsrId);
	}

	public String getRasModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, rasModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("rasFps"))
			return getRasFps();
		if (propertyName.equals("rasRaterJbt"))
			return getRasRaterJbt();
		if (propertyName.equals("rasReRaterJbt"))
			return getRasReRaterJbt();
		if (propertyName.equals("rasDataloadJbt"))
			return getRasDataloadJbt();
		if (propertyName.equals("rasCreatedUsr"))
			return getRasCreatedUsr();
		if (propertyName.equals("rasModifiedUsr"))
			return getRasModifiedUsr();
		if (propertyName.equals("rasId"))
			return getRasId();
		if (propertyName.equals("rasName"))
			return getRasName();
		if (propertyName.equals("rasTargetDir"))
			return getRasTargetDir();
		if (propertyName.equals("rasBixTargetDir"))
			return getRasBixTargetDir();
		if (propertyName.equals("rasFpsId"))
			return getRasFpsId();
		if (propertyName.equals("rasRaterJbtId"))
			return getRasRaterJbtId();
		if (propertyName.equals("rasReRaterJbtId"))
			return getRasReRaterJbtId();
		if (propertyName.equals("rasDataloadJbtId"))
			return getRasDataloadJbtId();
		if (propertyName.equals("rasBk"))
			return getRasBk();
		if (propertyName.equals("rasCreatedUsrId"))
			return getRasCreatedUsrId();
		if (propertyName.equals("rasModifiedUsrId"))
			return getRasModifiedUsrId();
		if (propertyName.equals("rasCreatedDttm"))
			return getRasCreatedDttm();
		if (propertyName.equals("rasModifiedDttm"))
			return getRasModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getRasId()
	{
		return rasId;
	}

	public void setRasId(Integer rasId)
	{
		this.rasId = rasId;
	}

	public String getRasName()
	{
		return rasName;
	}

	public void setRasName(String rasName)
	{
		this.rasName = rasName;
	}

	public String getRasTargetDir()
	{
		return rasTargetDir;
	}

	public void setRasTargetDir(String rasTargetDir)
	{
		this.rasTargetDir = rasTargetDir;
	}

	public String getRasBixTargetDir()
	{
		return rasBixTargetDir;
	}

	public void setRasBixTargetDir(String rasBixTargetDir)
	{
		this.rasBixTargetDir = rasBixTargetDir;
	}

	public Integer getRasFpsId()
	{
		return rasFpsId;
	}

	public void setRasFpsId(Integer rasFpsId)
	{
		this.rasFpsId = rasFpsId;
	}

	public Integer getRasRaterJbtId()
	{
		return rasRaterJbtId;
	}

	public void setRasRaterJbtId(Integer rasRaterJbtId)
	{
		this.rasRaterJbtId = rasRaterJbtId;
	}

	public Integer getRasReRaterJbtId()
	{
		return rasReRaterJbtId;
	}

	public void setRasReRaterJbtId(Integer rasReRaterJbtId)
	{
		this.rasReRaterJbtId = rasReRaterJbtId;
	}

	public Integer getRasDataloadJbtId()
	{
		return rasDataloadJbtId;
	}

	public void setRasDataloadJbtId(Integer rasDataloadJbtId)
	{
		this.rasDataloadJbtId = rasDataloadJbtId;
	}

	public String getRasBk()
	{
		return rasBk;
	}

	public void setRasBk(String rasBk)
	{
		this.rasBk = rasBk;
	}

	public Integer getRasCreatedUsrId()
	{
		return rasCreatedUsrId;
	}

	public void setRasCreatedUsrId(Integer rasCreatedUsrId)
	{
		this.rasCreatedUsrId = rasCreatedUsrId;
	}

	public Integer getRasModifiedUsrId()
	{
		return rasModifiedUsrId;
	}

	public void setRasModifiedUsrId(Integer rasModifiedUsrId)
	{
		this.rasModifiedUsrId = rasModifiedUsrId;
	}

	public DateTime getRasCreatedDttm()
	{
		return rasCreatedDttm;
	}

	public void setRasCreatedDttm(DateTime rasCreatedDttm)
	{
		this.rasCreatedDttm = rasCreatedDttm;
	}

	public DateTime getRasModifiedDttm()
	{
		return rasModifiedDttm;
	}

	public void setRasModifiedDttm(DateTime rasModifiedDttm)
	{
		this.rasModifiedDttm = rasModifiedDttm;
	}

}
