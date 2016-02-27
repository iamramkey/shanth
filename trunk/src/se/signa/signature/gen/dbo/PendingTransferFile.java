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
import se.signa.signature.gen.dba.FiletblDba;
import se.signa.signature.gen.dba.UserTblDba;
import se.signa.signature.helpers.DisplayFieldHelper;

public abstract class PendingTransferFile extends SignatureDbo
{
	private Integer ptfId;
	private Integer ftsId;
	private Integer filId;
	private String ptfName;
	private String ptfBk;
	private Integer ptfCreatedUsrId;
	private Integer ptfModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime ptfCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime ptfModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			ptfId = getInteger(rs.getInt("ptf_id"));
			ftsId = getInteger(rs.getInt("fts_id"));
			filId = getInteger(rs.getInt("fil_id"));
			ptfName = rs.getString("ptf_name");
			ptfBk = rs.getString("ptf_bk");
			ptfCreatedUsrId = getInteger(rs.getInt("ptf_created_usr_id"));
			ptfModifiedUsrId = getInteger(rs.getInt("ptf_modified_usr_id"));
			ptfCreatedDttm = getDateTime(rs.getTimestamp("ptf_created_dttm"));
			ptfModifiedDttm = getDateTime(rs.getTimestamp("ptf_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return ptfId;
	}

	public void setPk(int pk)
	{
		ptfId = pk;
	}

	public String getBk()
	{
		return ptfBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(ptfId.toString());
		data.add(ftsId.toString());
		data.add(filId.toString());
		data.add(ptfName.toString());
		data.add(getPtfCreatedUsr());
		data.add(Constants.dttf.print(ptfCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("fts", getFts());
		data.put("fil", getFil());
		data.put("ptfName", getString(ptfName));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setFtsId(DisplayFieldHelper.getI().getPk(FileTransferSettingsDba.class, input.getString("fts")));
		setFilId(DisplayFieldHelper.getI().getPk(FiletblDba.class, input.getString("fil")));
		setPtfName(input.getString("ptfName"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(ptfBk.toString());
		auditData.add(getPtfCreatedUsr());
		auditData.add(ptfModifiedUsrId == null ? Constants.NULLSTRING : getPtfModifiedUsr());
		auditData.add(Constants.dttf.print(ptfCreatedDttm));
		auditData.add(ptfModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(ptfModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return ptfName;
	}

	public String getFts()
	{
		return DisplayFieldHelper.getI().getDisplayField(FileTransferSettingsDba.class, ftsId);
	}

	public String getFil()
	{
		return DisplayFieldHelper.getI().getDisplayField(FiletblDba.class, filId);
	}

	public String getPtfCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, ptfCreatedUsrId);
	}

	public String getPtfModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, ptfModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("fts"))
			return getFts();
		if (propertyName.equals("fil"))
			return getFil();
		if (propertyName.equals("ptfCreatedUsr"))
			return getPtfCreatedUsr();
		if (propertyName.equals("ptfModifiedUsr"))
			return getPtfModifiedUsr();
		if (propertyName.equals("ptfId"))
			return getPtfId();
		if (propertyName.equals("ftsId"))
			return getFtsId();
		if (propertyName.equals("filId"))
			return getFilId();
		if (propertyName.equals("ptfName"))
			return getPtfName();
		if (propertyName.equals("ptfBk"))
			return getPtfBk();
		if (propertyName.equals("ptfCreatedUsrId"))
			return getPtfCreatedUsrId();
		if (propertyName.equals("ptfModifiedUsrId"))
			return getPtfModifiedUsrId();
		if (propertyName.equals("ptfCreatedDttm"))
			return getPtfCreatedDttm();
		if (propertyName.equals("ptfModifiedDttm"))
			return getPtfModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getPtfId()
	{
		return ptfId;
	}

	public void setPtfId(Integer ptfId)
	{
		this.ptfId = ptfId;
	}

	public Integer getFtsId()
	{
		return ftsId;
	}

	public void setFtsId(Integer ftsId)
	{
		this.ftsId = ftsId;
	}

	public Integer getFilId()
	{
		return filId;
	}

	public void setFilId(Integer filId)
	{
		this.filId = filId;
	}

	public String getPtfName()
	{
		return ptfName;
	}

	public void setPtfName(String ptfName)
	{
		this.ptfName = ptfName;
	}

	public String getPtfBk()
	{
		return ptfBk;
	}

	public void setPtfBk(String ptfBk)
	{
		this.ptfBk = ptfBk;
	}

	public Integer getPtfCreatedUsrId()
	{
		return ptfCreatedUsrId;
	}

	public void setPtfCreatedUsrId(Integer ptfCreatedUsrId)
	{
		this.ptfCreatedUsrId = ptfCreatedUsrId;
	}

	public Integer getPtfModifiedUsrId()
	{
		return ptfModifiedUsrId;
	}

	public void setPtfModifiedUsrId(Integer ptfModifiedUsrId)
	{
		this.ptfModifiedUsrId = ptfModifiedUsrId;
	}

	public DateTime getPtfCreatedDttm()
	{
		return ptfCreatedDttm;
	}

	public void setPtfCreatedDttm(DateTime ptfCreatedDttm)
	{
		this.ptfCreatedDttm = ptfCreatedDttm;
	}

	public DateTime getPtfModifiedDttm()
	{
		return ptfModifiedDttm;
	}

	public void setPtfModifiedDttm(DateTime ptfModifiedDttm)
	{
		this.ptfModifiedDttm = ptfModifiedDttm;
	}

}
