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
import se.signa.signature.gen.dba.FileParserSettingsDba;
import se.signa.signature.gen.dba.FilePollerSettingsDba;
import se.signa.signature.gen.dba.JobTypeDba;
import se.signa.signature.gen.dba.UserTblDba;
import se.signa.signature.helpers.DisplayFieldHelper;

public abstract class RefDataComparatorSettings extends SignatureDbo
{
	private Integer rdcId;
	private String rdcName;
	private Integer jbtId;
	private Integer rdcReRaterJbtId;
	private Integer fasId;
	private Integer fpsId;
	private String rdcBk;
	private Integer rdcCreatedUsrId;
	private Integer rdcModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime rdcCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime rdcModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			rdcId = getInteger(rs.getInt("rdc_id"));
			rdcName = rs.getString("rdc_name");
			jbtId = getInteger(rs.getInt("jbt_id"));
			rdcReRaterJbtId = getInteger(rs.getInt("rdc_re_rater_jbt_id"));
			fasId = getInteger(rs.getInt("fas_id"));
			fpsId = getInteger(rs.getInt("fps_id"));
			rdcBk = rs.getString("rdc_bk");
			rdcCreatedUsrId = getInteger(rs.getInt("rdc_created_usr_id"));
			rdcModifiedUsrId = getInteger(rs.getInt("rdc_modified_usr_id"));
			rdcCreatedDttm = getDateTime(rs.getTimestamp("rdc_created_dttm"));
			rdcModifiedDttm = getDateTime(rs.getTimestamp("rdc_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return rdcId;
	}

	public void setPk(int pk)
	{
		rdcId = pk;
	}

	public String getBk()
	{
		return rdcBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(rdcId.toString());
		data.add(rdcName.toString());
		data.add(jbtId.toString());
		data.add(rdcReRaterJbtId.toString());
		data.add(fasId.toString());
		data.add(fpsId.toString());
		data.add(getRdcCreatedUsr());
		data.add(Constants.dttf.print(rdcCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("rdcName", getString(rdcName));
		data.put("jbt", getJbt());
		data.put("rdcReRaterJbt", getRdcReRaterJbt());
		data.put("fas", getFas());
		data.put("fps", getFps());
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setRdcName(input.getString("rdcName"));
		setJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("jbt")));
		setRdcReRaterJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("rdcReRaterJbt")));
		setFasId(DisplayFieldHelper.getI().getPk(FileParserSettingsDba.class, input.getString("fas")));
		setFpsId(DisplayFieldHelper.getI().getPk(FilePollerSettingsDba.class, input.getString("fps")));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(rdcBk.toString());
		auditData.add(getRdcCreatedUsr());
		auditData.add(rdcModifiedUsrId == null ? Constants.NULLSTRING : getRdcModifiedUsr());
		auditData.add(Constants.dttf.print(rdcCreatedDttm));
		auditData.add(rdcModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(rdcModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return rdcName;
	}

	public String getJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, jbtId);
	}

	public String getRdcReRaterJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, rdcReRaterJbtId);
	}

	public String getFas()
	{
		return DisplayFieldHelper.getI().getDisplayField(FileParserSettingsDba.class, fasId);
	}

	public String getFps()
	{
		return DisplayFieldHelper.getI().getDisplayField(FilePollerSettingsDba.class, fpsId);
	}

	public String getRdcCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, rdcCreatedUsrId);
	}

	public String getRdcModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, rdcModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("jbt"))
			return getJbt();
		if (propertyName.equals("rdcReRaterJbt"))
			return getRdcReRaterJbt();
		if (propertyName.equals("fas"))
			return getFas();
		if (propertyName.equals("fps"))
			return getFps();
		if (propertyName.equals("rdcCreatedUsr"))
			return getRdcCreatedUsr();
		if (propertyName.equals("rdcModifiedUsr"))
			return getRdcModifiedUsr();
		if (propertyName.equals("rdcId"))
			return getRdcId();
		if (propertyName.equals("rdcName"))
			return getRdcName();
		if (propertyName.equals("jbtId"))
			return getJbtId();
		if (propertyName.equals("rdcReRaterJbtId"))
			return getRdcReRaterJbtId();
		if (propertyName.equals("fasId"))
			return getFasId();
		if (propertyName.equals("fpsId"))
			return getFpsId();
		if (propertyName.equals("rdcBk"))
			return getRdcBk();
		if (propertyName.equals("rdcCreatedUsrId"))
			return getRdcCreatedUsrId();
		if (propertyName.equals("rdcModifiedUsrId"))
			return getRdcModifiedUsrId();
		if (propertyName.equals("rdcCreatedDttm"))
			return getRdcCreatedDttm();
		if (propertyName.equals("rdcModifiedDttm"))
			return getRdcModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getRdcId()
	{
		return rdcId;
	}

	public void setRdcId(Integer rdcId)
	{
		this.rdcId = rdcId;
	}

	public String getRdcName()
	{
		return rdcName;
	}

	public void setRdcName(String rdcName)
	{
		this.rdcName = rdcName;
	}

	public Integer getJbtId()
	{
		return jbtId;
	}

	public void setJbtId(Integer jbtId)
	{
		this.jbtId = jbtId;
	}

	public Integer getRdcReRaterJbtId()
	{
		return rdcReRaterJbtId;
	}

	public void setRdcReRaterJbtId(Integer rdcReRaterJbtId)
	{
		this.rdcReRaterJbtId = rdcReRaterJbtId;
	}

	public Integer getFasId()
	{
		return fasId;
	}

	public void setFasId(Integer fasId)
	{
		this.fasId = fasId;
	}

	public Integer getFpsId()
	{
		return fpsId;
	}

	public void setFpsId(Integer fpsId)
	{
		this.fpsId = fpsId;
	}

	public String getRdcBk()
	{
		return rdcBk;
	}

	public void setRdcBk(String rdcBk)
	{
		this.rdcBk = rdcBk;
	}

	public Integer getRdcCreatedUsrId()
	{
		return rdcCreatedUsrId;
	}

	public void setRdcCreatedUsrId(Integer rdcCreatedUsrId)
	{
		this.rdcCreatedUsrId = rdcCreatedUsrId;
	}

	public Integer getRdcModifiedUsrId()
	{
		return rdcModifiedUsrId;
	}

	public void setRdcModifiedUsrId(Integer rdcModifiedUsrId)
	{
		this.rdcModifiedUsrId = rdcModifiedUsrId;
	}

	public DateTime getRdcCreatedDttm()
	{
		return rdcCreatedDttm;
	}

	public void setRdcCreatedDttm(DateTime rdcCreatedDttm)
	{
		this.rdcCreatedDttm = rdcCreatedDttm;
	}

	public DateTime getRdcModifiedDttm()
	{
		return rdcModifiedDttm;
	}

	public void setRdcModifiedDttm(DateTime rdcModifiedDttm)
	{
		this.rdcModifiedDttm = rdcModifiedDttm;
	}

}
