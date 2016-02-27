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

public abstract class BixExporterSettings extends SignatureDbo
{
	private Integer besId;
	private String besName;
	private Integer besBixExporterJbtId;
	private String besCompressedDir;
	private String besBk;
	private Integer besCreatedUsrId;
	private Integer besModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime besCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime besModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			besId = getInteger(rs.getInt("bes_id"));
			besName = rs.getString("bes_name");
			besBixExporterJbtId = getInteger(rs.getInt("bes_bix_exporter_jbt_id"));
			besCompressedDir = rs.getString("bes_compressed_dir");
			besBk = rs.getString("bes_bk");
			besCreatedUsrId = getInteger(rs.getInt("bes_created_usr_id"));
			besModifiedUsrId = getInteger(rs.getInt("bes_modified_usr_id"));
			besCreatedDttm = getDateTime(rs.getTimestamp("bes_created_dttm"));
			besModifiedDttm = getDateTime(rs.getTimestamp("bes_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return besId;
	}

	public void setPk(int pk)
	{
		besId = pk;
	}

	public String getBk()
	{
		return besBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(besId.toString());
		data.add(besName.toString());
		data.add(besBixExporterJbtId.toString());
		data.add(besCompressedDir.toString());
		data.add(getBesCreatedUsr());
		data.add(Constants.dttf.print(besCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("besName", getString(besName));
		data.put("besBixExporterJbt", getBesBixExporterJbt());
		data.put("besCompressedDir", getString(besCompressedDir));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setBesName(input.getString("besName"));
		setBesBixExporterJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("besBixExporterJbt")));
		setBesCompressedDir(input.getString("besCompressedDir"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(besBk.toString());
		auditData.add(getBesCreatedUsr());
		auditData.add(besModifiedUsrId == null ? Constants.NULLSTRING : getBesModifiedUsr());
		auditData.add(Constants.dttf.print(besCreatedDttm));
		auditData.add(besModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(besModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return besName;
	}

	public String getBesBixExporterJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, besBixExporterJbtId);
	}

	public String getBesCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, besCreatedUsrId);
	}

	public String getBesModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, besModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("besBixExporterJbt"))
			return getBesBixExporterJbt();
		if (propertyName.equals("besCreatedUsr"))
			return getBesCreatedUsr();
		if (propertyName.equals("besModifiedUsr"))
			return getBesModifiedUsr();
		if (propertyName.equals("besId"))
			return getBesId();
		if (propertyName.equals("besName"))
			return getBesName();
		if (propertyName.equals("besBixExporterJbtId"))
			return getBesBixExporterJbtId();
		if (propertyName.equals("besCompressedDir"))
			return getBesCompressedDir();
		if (propertyName.equals("besBk"))
			return getBesBk();
		if (propertyName.equals("besCreatedUsrId"))
			return getBesCreatedUsrId();
		if (propertyName.equals("besModifiedUsrId"))
			return getBesModifiedUsrId();
		if (propertyName.equals("besCreatedDttm"))
			return getBesCreatedDttm();
		if (propertyName.equals("besModifiedDttm"))
			return getBesModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getBesId()
	{
		return besId;
	}

	public void setBesId(Integer besId)
	{
		this.besId = besId;
	}

	public String getBesName()
	{
		return besName;
	}

	public void setBesName(String besName)
	{
		this.besName = besName;
	}

	public Integer getBesBixExporterJbtId()
	{
		return besBixExporterJbtId;
	}

	public void setBesBixExporterJbtId(Integer besBixExporterJbtId)
	{
		this.besBixExporterJbtId = besBixExporterJbtId;
	}

	public String getBesCompressedDir()
	{
		return besCompressedDir;
	}

	public void setBesCompressedDir(String besCompressedDir)
	{
		this.besCompressedDir = besCompressedDir;
	}

	public String getBesBk()
	{
		return besBk;
	}

	public void setBesBk(String besBk)
	{
		this.besBk = besBk;
	}

	public Integer getBesCreatedUsrId()
	{
		return besCreatedUsrId;
	}

	public void setBesCreatedUsrId(Integer besCreatedUsrId)
	{
		this.besCreatedUsrId = besCreatedUsrId;
	}

	public Integer getBesModifiedUsrId()
	{
		return besModifiedUsrId;
	}

	public void setBesModifiedUsrId(Integer besModifiedUsrId)
	{
		this.besModifiedUsrId = besModifiedUsrId;
	}

	public DateTime getBesCreatedDttm()
	{
		return besCreatedDttm;
	}

	public void setBesCreatedDttm(DateTime besCreatedDttm)
	{
		this.besCreatedDttm = besCreatedDttm;
	}

	public DateTime getBesModifiedDttm()
	{
		return besModifiedDttm;
	}

	public void setBesModifiedDttm(DateTime besModifiedDttm)
	{
		this.besModifiedDttm = besModifiedDttm;
	}

}
