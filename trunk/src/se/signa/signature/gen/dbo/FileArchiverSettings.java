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
import se.signa.signature.gen.dba.UserTblDba;
import se.signa.signature.helpers.DisplayFieldHelper;

public abstract class FileArchiverSettings extends SignatureDbo
{
	private Integer fiaId;
	private String fiaName;
	private String fiaSourceDir;
	private String fiaDestDir;
	private String fiaType;
	private Integer fiaRetentionDays;
	private String fiaBk;
	private Integer fiaCreatedUsrId;
	private Integer fiaModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime fiaCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime fiaModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			fiaId = getInteger(rs.getInt("fia_id"));
			fiaName = rs.getString("fia_name");
			fiaSourceDir = rs.getString("fia_source_dir");
			fiaDestDir = rs.getString("fia_dest_dir");
			fiaType = rs.getString("fia_type");
			fiaRetentionDays = rs.getInt("fia_retention_days");
			fiaBk = rs.getString("fia_bk");
			fiaCreatedUsrId = getInteger(rs.getInt("fia_created_usr_id"));
			fiaModifiedUsrId = getInteger(rs.getInt("fia_modified_usr_id"));
			fiaCreatedDttm = getDateTime(rs.getTimestamp("fia_created_dttm"));
			fiaModifiedDttm = getDateTime(rs.getTimestamp("fia_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return fiaId;
	}

	public void setPk(int pk)
	{
		fiaId = pk;
	}

	public String getBk()
	{
		return fiaBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(fiaId.toString());
		data.add(fiaName.toString());
		data.add(fiaSourceDir.toString());
		data.add(fiaDestDir.toString());
		data.add(fiaType.toString());
		data.add(fiaRetentionDays.toString());
		data.add(getFiaCreatedUsr());
		data.add(Constants.dttf.print(fiaCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("fiaName", getString(fiaName));
		data.put("fiaSourceDir", getString(fiaSourceDir));
		data.put("fiaDestDir", getString(fiaDestDir));
		data.put("fiaType", getString(fiaType));
		data.put("fiaRetentionDays", getString(fiaRetentionDays));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setFiaName(input.getString("fiaName"));
		setFiaSourceDir(input.getString("fiaSourceDir"));
		setFiaDestDir(input.getString("fiaDestDir"));
		setFiaType(input.getString("fiaType"));
		setFiaRetentionDays(input.getInteger("fiaRetentionDays"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(fiaBk.toString());
		auditData.add(getFiaCreatedUsr());
		auditData.add(fiaModifiedUsrId == null ? Constants.NULLSTRING : getFiaModifiedUsr());
		auditData.add(Constants.dttf.print(fiaCreatedDttm));
		auditData.add(fiaModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(fiaModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return fiaName;
	}

	public String getFiaCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, fiaCreatedUsrId);
	}

	public String getFiaModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, fiaModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("fiaCreatedUsr"))
			return getFiaCreatedUsr();
		if (propertyName.equals("fiaModifiedUsr"))
			return getFiaModifiedUsr();
		if (propertyName.equals("fiaId"))
			return getFiaId();
		if (propertyName.equals("fiaName"))
			return getFiaName();
		if (propertyName.equals("fiaSourceDir"))
			return getFiaSourceDir();
		if (propertyName.equals("fiaDestDir"))
			return getFiaDestDir();
		if (propertyName.equals("fiaType"))
			return getFiaType();
		if (propertyName.equals("fiaRetentionDays"))
			return getFiaRetentionDays();
		if (propertyName.equals("fiaBk"))
			return getFiaBk();
		if (propertyName.equals("fiaCreatedUsrId"))
			return getFiaCreatedUsrId();
		if (propertyName.equals("fiaModifiedUsrId"))
			return getFiaModifiedUsrId();
		if (propertyName.equals("fiaCreatedDttm"))
			return getFiaCreatedDttm();
		if (propertyName.equals("fiaModifiedDttm"))
			return getFiaModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getFiaId()
	{
		return fiaId;
	}

	public void setFiaId(Integer fiaId)
	{
		this.fiaId = fiaId;
	}

	public String getFiaName()
	{
		return fiaName;
	}

	public void setFiaName(String fiaName)
	{
		this.fiaName = fiaName;
	}

	public String getFiaSourceDir()
	{
		return fiaSourceDir;
	}

	public void setFiaSourceDir(String fiaSourceDir)
	{
		this.fiaSourceDir = fiaSourceDir;
	}

	public String getFiaDestDir()
	{
		return fiaDestDir;
	}

	public void setFiaDestDir(String fiaDestDir)
	{
		this.fiaDestDir = fiaDestDir;
	}

	public String getFiaType()
	{
		return fiaType;
	}

	public void setFiaType(String fiaType)
	{
		this.fiaType = fiaType;
	}

	public Integer getFiaRetentionDays()
	{
		return fiaRetentionDays;
	}

	public void setFiaRetentionDays(Integer fiaRetentionDays)
	{
		this.fiaRetentionDays = fiaRetentionDays;
	}

	public String getFiaBk()
	{
		return fiaBk;
	}

	public void setFiaBk(String fiaBk)
	{
		this.fiaBk = fiaBk;
	}

	public Integer getFiaCreatedUsrId()
	{
		return fiaCreatedUsrId;
	}

	public void setFiaCreatedUsrId(Integer fiaCreatedUsrId)
	{
		this.fiaCreatedUsrId = fiaCreatedUsrId;
	}

	public Integer getFiaModifiedUsrId()
	{
		return fiaModifiedUsrId;
	}

	public void setFiaModifiedUsrId(Integer fiaModifiedUsrId)
	{
		this.fiaModifiedUsrId = fiaModifiedUsrId;
	}

	public DateTime getFiaCreatedDttm()
	{
		return fiaCreatedDttm;
	}

	public void setFiaCreatedDttm(DateTime fiaCreatedDttm)
	{
		this.fiaCreatedDttm = fiaCreatedDttm;
	}

	public DateTime getFiaModifiedDttm()
	{
		return fiaModifiedDttm;
	}

	public void setFiaModifiedDttm(DateTime fiaModifiedDttm)
	{
		this.fiaModifiedDttm = fiaModifiedDttm;
	}

}
