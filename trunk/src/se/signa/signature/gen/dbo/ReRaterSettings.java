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

public abstract class ReRaterSettings extends SignatureDbo
{
	private Integer rrsId;
	private String rrsName;
	private String rrsTargetDir;
	private String rrsBixTargetDir;
	private Integer rrsFpsId;
	private Integer rrsReRaterJbtId;
	private Integer rrsDataloadJbtId;
	private String rrsBk;
	private Integer rrsCreatedUsrId;
	private Integer rrsModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime rrsCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime rrsModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			rrsId = getInteger(rs.getInt("rrs_id"));
			rrsName = rs.getString("rrs_name");
			rrsTargetDir = rs.getString("rrs_target_dir");
			rrsBixTargetDir = rs.getString("rrs_bix_target_dir");
			rrsFpsId = getInteger(rs.getInt("rrs_fps_id"));
			rrsReRaterJbtId = getInteger(rs.getInt("rrs_re_rater_jbt_id"));
			rrsDataloadJbtId = getInteger(rs.getInt("rrs_dataload_jbt_id"));
			rrsBk = rs.getString("rrs_bk");
			rrsCreatedUsrId = getInteger(rs.getInt("rrs_created_usr_id"));
			rrsModifiedUsrId = getInteger(rs.getInt("rrs_modified_usr_id"));
			rrsCreatedDttm = getDateTime(rs.getTimestamp("rrs_created_dttm"));
			rrsModifiedDttm = getDateTime(rs.getTimestamp("rrs_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return rrsId;
	}

	public void setPk(int pk)
	{
		rrsId = pk;
	}

	public String getBk()
	{
		return rrsBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(rrsId.toString());
		data.add(rrsName.toString());
		data.add(rrsTargetDir.toString());
		data.add(rrsBixTargetDir.toString());
		data.add(rrsFpsId.toString());
		data.add(rrsReRaterJbtId.toString());
		data.add(rrsDataloadJbtId.toString());
		data.add(getRrsCreatedUsr());
		data.add(Constants.dttf.print(rrsCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("rrsName", getString(rrsName));
		data.put("rrsTargetDir", getString(rrsTargetDir));
		data.put("rrsBixTargetDir", getString(rrsBixTargetDir));
		data.put("rrsFps", getRrsFps());
		data.put("rrsReRaterJbt", getRrsReRaterJbt());
		data.put("rrsDataloadJbt", getRrsDataloadJbt());
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setRrsName(input.getString("rrsName"));
		setRrsTargetDir(input.getString("rrsTargetDir"));
		setRrsBixTargetDir(input.getString("rrsBixTargetDir"));
		setRrsFpsId(DisplayFieldHelper.getI().getPk(FilePollerSettingsDba.class, input.getString("rrsFps")));
		setRrsReRaterJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("rrsReRaterJbt")));
		setRrsDataloadJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("rrsDataloadJbt")));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(rrsBk.toString());
		auditData.add(getRrsCreatedUsr());
		auditData.add(rrsModifiedUsrId == null ? Constants.NULLSTRING : getRrsModifiedUsr());
		auditData.add(Constants.dttf.print(rrsCreatedDttm));
		auditData.add(rrsModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(rrsModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return rrsName;
	}

	public String getRrsFps()
	{
		return DisplayFieldHelper.getI().getDisplayField(FilePollerSettingsDba.class, rrsFpsId);
	}

	public String getRrsReRaterJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, rrsReRaterJbtId);
	}

	public String getRrsDataloadJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, rrsDataloadJbtId);
	}

	public String getRrsCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, rrsCreatedUsrId);
	}

	public String getRrsModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, rrsModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("rrsFps"))
			return getRrsFps();
		if (propertyName.equals("rrsReRaterJbt"))
			return getRrsReRaterJbt();
		if (propertyName.equals("rrsDataloadJbt"))
			return getRrsDataloadJbt();
		if (propertyName.equals("rrsCreatedUsr"))
			return getRrsCreatedUsr();
		if (propertyName.equals("rrsModifiedUsr"))
			return getRrsModifiedUsr();
		if (propertyName.equals("rrsId"))
			return getRrsId();
		if (propertyName.equals("rrsName"))
			return getRrsName();
		if (propertyName.equals("rrsTargetDir"))
			return getRrsTargetDir();
		if (propertyName.equals("rrsBixTargetDir"))
			return getRrsBixTargetDir();
		if (propertyName.equals("rrsFpsId"))
			return getRrsFpsId();
		if (propertyName.equals("rrsReRaterJbtId"))
			return getRrsReRaterJbtId();
		if (propertyName.equals("rrsDataloadJbtId"))
			return getRrsDataloadJbtId();
		if (propertyName.equals("rrsBk"))
			return getRrsBk();
		if (propertyName.equals("rrsCreatedUsrId"))
			return getRrsCreatedUsrId();
		if (propertyName.equals("rrsModifiedUsrId"))
			return getRrsModifiedUsrId();
		if (propertyName.equals("rrsCreatedDttm"))
			return getRrsCreatedDttm();
		if (propertyName.equals("rrsModifiedDttm"))
			return getRrsModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getRrsId()
	{
		return rrsId;
	}

	public void setRrsId(Integer rrsId)
	{
		this.rrsId = rrsId;
	}

	public String getRrsName()
	{
		return rrsName;
	}

	public void setRrsName(String rrsName)
	{
		this.rrsName = rrsName;
	}

	public String getRrsTargetDir()
	{
		return rrsTargetDir;
	}

	public void setRrsTargetDir(String rrsTargetDir)
	{
		this.rrsTargetDir = rrsTargetDir;
	}

	public String getRrsBixTargetDir()
	{
		return rrsBixTargetDir;
	}

	public void setRrsBixTargetDir(String rrsBixTargetDir)
	{
		this.rrsBixTargetDir = rrsBixTargetDir;
	}

	public Integer getRrsFpsId()
	{
		return rrsFpsId;
	}

	public void setRrsFpsId(Integer rrsFpsId)
	{
		this.rrsFpsId = rrsFpsId;
	}

	public Integer getRrsReRaterJbtId()
	{
		return rrsReRaterJbtId;
	}

	public void setRrsReRaterJbtId(Integer rrsReRaterJbtId)
	{
		this.rrsReRaterJbtId = rrsReRaterJbtId;
	}

	public Integer getRrsDataloadJbtId()
	{
		return rrsDataloadJbtId;
	}

	public void setRrsDataloadJbtId(Integer rrsDataloadJbtId)
	{
		this.rrsDataloadJbtId = rrsDataloadJbtId;
	}

	public String getRrsBk()
	{
		return rrsBk;
	}

	public void setRrsBk(String rrsBk)
	{
		this.rrsBk = rrsBk;
	}

	public Integer getRrsCreatedUsrId()
	{
		return rrsCreatedUsrId;
	}

	public void setRrsCreatedUsrId(Integer rrsCreatedUsrId)
	{
		this.rrsCreatedUsrId = rrsCreatedUsrId;
	}

	public Integer getRrsModifiedUsrId()
	{
		return rrsModifiedUsrId;
	}

	public void setRrsModifiedUsrId(Integer rrsModifiedUsrId)
	{
		this.rrsModifiedUsrId = rrsModifiedUsrId;
	}

	public DateTime getRrsCreatedDttm()
	{
		return rrsCreatedDttm;
	}

	public void setRrsCreatedDttm(DateTime rrsCreatedDttm)
	{
		this.rrsCreatedDttm = rrsCreatedDttm;
	}

	public DateTime getRrsModifiedDttm()
	{
		return rrsModifiedDttm;
	}

	public void setRrsModifiedDttm(DateTime rrsModifiedDttm)
	{
		this.rrsModifiedDttm = rrsModifiedDttm;
	}

}
