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

public abstract class PendingRerateTransferFile extends SignatureDbo
{
	private Integer prtId;
	private Integer ftsId;
	private Integer filId;
	private String prtName;
	private String prtBk;
	private Integer prtCreatedUsrId;
	private Integer prtModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime prtCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime prtModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			prtId = getInteger(rs.getInt("prt_id"));
			ftsId = getInteger(rs.getInt("fts_id"));
			filId = getInteger(rs.getInt("fil_id"));
			prtName = rs.getString("prt_name");
			prtBk = rs.getString("prt_bk");
			prtCreatedUsrId = getInteger(rs.getInt("prt_created_usr_id"));
			prtModifiedUsrId = getInteger(rs.getInt("prt_modified_usr_id"));
			prtCreatedDttm = getDateTime(rs.getTimestamp("prt_created_dttm"));
			prtModifiedDttm = getDateTime(rs.getTimestamp("prt_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return prtId;
	}

	public void setPk(int pk)
	{
		prtId = pk;
	}

	public String getBk()
	{
		return prtBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(prtId.toString());
		data.add(ftsId.toString());
		data.add(filId.toString());
		data.add(prtName.toString());
		data.add(getPrtCreatedUsr());
		data.add(Constants.dttf.print(prtCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("fts", getFts());
		data.put("fil", getFil());
		data.put("prtName", getString(prtName));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setFtsId(DisplayFieldHelper.getI().getPk(FileTransferSettingsDba.class, input.getString("fts")));
		setFilId(DisplayFieldHelper.getI().getPk(FiletblDba.class, input.getString("fil")));
		setPrtName(input.getString("prtName"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(prtBk.toString());
		auditData.add(getPrtCreatedUsr());
		auditData.add(prtModifiedUsrId == null ? Constants.NULLSTRING : getPrtModifiedUsr());
		auditData.add(Constants.dttf.print(prtCreatedDttm));
		auditData.add(prtModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(prtModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return prtName;
	}

	public String getFts()
	{
		return DisplayFieldHelper.getI().getDisplayField(FileTransferSettingsDba.class, ftsId);
	}

	public String getFil()
	{
		return DisplayFieldHelper.getI().getDisplayField(FiletblDba.class, filId);
	}

	public String getPrtCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, prtCreatedUsrId);
	}

	public String getPrtModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, prtModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("fts"))
			return getFts();
		if (propertyName.equals("fil"))
			return getFil();
		if (propertyName.equals("prtCreatedUsr"))
			return getPrtCreatedUsr();
		if (propertyName.equals("prtModifiedUsr"))
			return getPrtModifiedUsr();
		if (propertyName.equals("prtId"))
			return getPrtId();
		if (propertyName.equals("ftsId"))
			return getFtsId();
		if (propertyName.equals("filId"))
			return getFilId();
		if (propertyName.equals("prtName"))
			return getPrtName();
		if (propertyName.equals("prtBk"))
			return getPrtBk();
		if (propertyName.equals("prtCreatedUsrId"))
			return getPrtCreatedUsrId();
		if (propertyName.equals("prtModifiedUsrId"))
			return getPrtModifiedUsrId();
		if (propertyName.equals("prtCreatedDttm"))
			return getPrtCreatedDttm();
		if (propertyName.equals("prtModifiedDttm"))
			return getPrtModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getPrtId()
	{
		return prtId;
	}

	public void setPrtId(Integer prtId)
	{
		this.prtId = prtId;
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

	public String getPrtName()
	{
		return prtName;
	}

	public void setPrtName(String prtName)
	{
		this.prtName = prtName;
	}

	public String getPrtBk()
	{
		return prtBk;
	}

	public void setPrtBk(String prtBk)
	{
		this.prtBk = prtBk;
	}

	public Integer getPrtCreatedUsrId()
	{
		return prtCreatedUsrId;
	}

	public void setPrtCreatedUsrId(Integer prtCreatedUsrId)
	{
		this.prtCreatedUsrId = prtCreatedUsrId;
	}

	public Integer getPrtModifiedUsrId()
	{
		return prtModifiedUsrId;
	}

	public void setPrtModifiedUsrId(Integer prtModifiedUsrId)
	{
		this.prtModifiedUsrId = prtModifiedUsrId;
	}

	public DateTime getPrtCreatedDttm()
	{
		return prtCreatedDttm;
	}

	public void setPrtCreatedDttm(DateTime prtCreatedDttm)
	{
		this.prtCreatedDttm = prtCreatedDttm;
	}

	public DateTime getPrtModifiedDttm()
	{
		return prtModifiedDttm;
	}

	public void setPrtModifiedDttm(DateTime prtModifiedDttm)
	{
		this.prtModifiedDttm = prtModifiedDttm;
	}

}
