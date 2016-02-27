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
import se.signa.signature.gen.dba.FiletblDba;
import se.signa.signature.gen.dba.UserTblDba;
import se.signa.signature.helpers.DisplayFieldHelper;

public abstract class PendingPollFile extends SignatureDbo
{
	private Integer ppfId;
	private Integer fpsId;
	private Integer filId;
	private String ppfName;
	private String ppfBk;
	private Integer ppfCreatedUsrId;
	private Integer ppfModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime ppfCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime ppfModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			ppfId = getInteger(rs.getInt("ppf_id"));
			fpsId = getInteger(rs.getInt("fps_id"));
			filId = getInteger(rs.getInt("fil_id"));
			ppfName = rs.getString("ppf_name");
			ppfBk = rs.getString("ppf_bk");
			ppfCreatedUsrId = getInteger(rs.getInt("ppf_created_usr_id"));
			ppfModifiedUsrId = getInteger(rs.getInt("ppf_modified_usr_id"));
			ppfCreatedDttm = getDateTime(rs.getTimestamp("ppf_created_dttm"));
			ppfModifiedDttm = getDateTime(rs.getTimestamp("ppf_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return ppfId;
	}

	public void setPk(int pk)
	{
		ppfId = pk;
	}

	public String getBk()
	{
		return ppfBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(ppfId.toString());
		data.add(fpsId.toString());
		data.add(filId.toString());
		data.add(ppfName.toString());
		data.add(getPpfCreatedUsr());
		data.add(Constants.dttf.print(ppfCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("fps", getFps());
		data.put("fil", getFil());
		data.put("ppfName", getString(ppfName));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setFpsId(DisplayFieldHelper.getI().getPk(FilePollerSettingsDba.class, input.getString("fps")));
		setFilId(DisplayFieldHelper.getI().getPk(FiletblDba.class, input.getString("fil")));
		setPpfName(input.getString("ppfName"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(ppfBk.toString());
		auditData.add(getPpfCreatedUsr());
		auditData.add(ppfModifiedUsrId == null ? Constants.NULLSTRING : getPpfModifiedUsr());
		auditData.add(Constants.dttf.print(ppfCreatedDttm));
		auditData.add(ppfModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(ppfModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return ppfName;
	}

	public String getFps()
	{
		return DisplayFieldHelper.getI().getDisplayField(FilePollerSettingsDba.class, fpsId);
	}

	public String getFil()
	{
		return DisplayFieldHelper.getI().getDisplayField(FiletblDba.class, filId);
	}

	public String getPpfCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, ppfCreatedUsrId);
	}

	public String getPpfModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, ppfModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("fps"))
			return getFps();
		if (propertyName.equals("fil"))
			return getFil();
		if (propertyName.equals("ppfCreatedUsr"))
			return getPpfCreatedUsr();
		if (propertyName.equals("ppfModifiedUsr"))
			return getPpfModifiedUsr();
		if (propertyName.equals("ppfId"))
			return getPpfId();
		if (propertyName.equals("fpsId"))
			return getFpsId();
		if (propertyName.equals("filId"))
			return getFilId();
		if (propertyName.equals("ppfName"))
			return getPpfName();
		if (propertyName.equals("ppfBk"))
			return getPpfBk();
		if (propertyName.equals("ppfCreatedUsrId"))
			return getPpfCreatedUsrId();
		if (propertyName.equals("ppfModifiedUsrId"))
			return getPpfModifiedUsrId();
		if (propertyName.equals("ppfCreatedDttm"))
			return getPpfCreatedDttm();
		if (propertyName.equals("ppfModifiedDttm"))
			return getPpfModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getPpfId()
	{
		return ppfId;
	}

	public void setPpfId(Integer ppfId)
	{
		this.ppfId = ppfId;
	}

	public Integer getFpsId()
	{
		return fpsId;
	}

	public void setFpsId(Integer fpsId)
	{
		this.fpsId = fpsId;
	}

	public Integer getFilId()
	{
		return filId;
	}

	public void setFilId(Integer filId)
	{
		this.filId = filId;
	}

	public String getPpfName()
	{
		return ppfName;
	}

	public void setPpfName(String ppfName)
	{
		this.ppfName = ppfName;
	}

	public String getPpfBk()
	{
		return ppfBk;
	}

	public void setPpfBk(String ppfBk)
	{
		this.ppfBk = ppfBk;
	}

	public Integer getPpfCreatedUsrId()
	{
		return ppfCreatedUsrId;
	}

	public void setPpfCreatedUsrId(Integer ppfCreatedUsrId)
	{
		this.ppfCreatedUsrId = ppfCreatedUsrId;
	}

	public Integer getPpfModifiedUsrId()
	{
		return ppfModifiedUsrId;
	}

	public void setPpfModifiedUsrId(Integer ppfModifiedUsrId)
	{
		this.ppfModifiedUsrId = ppfModifiedUsrId;
	}

	public DateTime getPpfCreatedDttm()
	{
		return ppfCreatedDttm;
	}

	public void setPpfCreatedDttm(DateTime ppfCreatedDttm)
	{
		this.ppfCreatedDttm = ppfCreatedDttm;
	}

	public DateTime getPpfModifiedDttm()
	{
		return ppfModifiedDttm;
	}

	public void setPpfModifiedDttm(DateTime ppfModifiedDttm)
	{
		this.ppfModifiedDttm = ppfModifiedDttm;
	}

}
