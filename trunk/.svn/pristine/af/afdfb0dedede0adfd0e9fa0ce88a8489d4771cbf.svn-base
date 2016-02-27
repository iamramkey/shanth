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
import se.signa.signature.gen.dba.AuditFileDba;
import se.signa.signature.gen.dba.FiletblDba;
import se.signa.signature.gen.dba.UserTblDba;
import se.signa.signature.helpers.DisplayFieldHelper;

public abstract class AuditFile extends SignatureDbo
{
	private Integer aufId;
	private Integer filId;
	private String aufName;
	private String aufFileType;
	private Integer aufParentAufId;
	private Long aufChecksum;
	private Long aufFilesize;
	private Long aufChildCount;
	private String aufBk;
	private Integer aufCreatedUsrId;
	private Integer aufModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime aufCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime aufModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			aufId = getInteger(rs.getInt("auf_id"));
			filId = getInteger(rs.getInt("fil_id"));
			aufName = rs.getString("auf_name");
			aufFileType = rs.getString("auf_file_type");
			aufParentAufId = getInteger(rs.getInt("auf_parent_auf_id"));
			aufChecksum = rs.getLong("auf_checksum");
			aufFilesize = rs.getLong("auf_filesize");
			aufChildCount = rs.getLong("auf_child_count");
			aufBk = rs.getString("auf_bk");
			aufCreatedUsrId = getInteger(rs.getInt("auf_created_usr_id"));
			aufModifiedUsrId = getInteger(rs.getInt("auf_modified_usr_id"));
			aufCreatedDttm = getDateTime(rs.getTimestamp("auf_created_dttm"));
			aufModifiedDttm = getDateTime(rs.getTimestamp("auf_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return aufId;
	}

	public void setPk(int pk)
	{
		aufId = pk;
	}

	public String getBk()
	{
		return aufBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(aufId.toString());
		data.add(filId.toString());
		data.add(aufName.toString());
		data.add(aufFileType.toString());
		data.add(aufParentAufId == null ? Constants.NULLSTRING : aufParentAufId.toString());
		data.add(aufChecksum.toString());
		data.add(aufFilesize.toString());
		data.add(aufChildCount.toString());
		data.add(getAufCreatedUsr());
		data.add(Constants.dttf.print(aufCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("fil", getFil());
		data.put("aufName", getString(aufName));
		data.put("aufFileType", getString(aufFileType));
		data.put("aufParentAuf", getAufParentAuf());
		data.put("aufChecksum", getString(aufChecksum));
		data.put("aufFilesize", getString(aufFilesize));
		data.put("aufChildCount", getString(aufChildCount));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setFilId(DisplayFieldHelper.getI().getPk(FiletblDba.class, input.getString("fil")));
		setAufName(input.getString("aufName"));
		setAufFileType(input.getString("aufFileType"));
		setAufParentAufId(DisplayFieldHelper.getI().getPk(AuditFileDba.class, input.getString("aufParentAuf")));
		setAufChecksum(input.getLong("aufChecksum"));
		setAufFilesize(input.getLong("aufFilesize"));
		setAufChildCount(input.getLong("aufChildCount"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(aufBk.toString());
		auditData.add(getAufCreatedUsr());
		auditData.add(aufModifiedUsrId == null ? Constants.NULLSTRING : getAufModifiedUsr());
		auditData.add(Constants.dttf.print(aufCreatedDttm));
		auditData.add(aufModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(aufModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return aufName;
	}

	public String getFil()
	{
		return DisplayFieldHelper.getI().getDisplayField(FiletblDba.class, filId);
	}

	public String getAufParentAuf()
	{
		return DisplayFieldHelper.getI().getDisplayField(AuditFileDba.class, aufParentAufId);
	}

	public String getAufCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, aufCreatedUsrId);
	}

	public String getAufModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, aufModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("fil"))
			return getFil();
		if (propertyName.equals("aufParentAuf"))
			return getAufParentAuf();
		if (propertyName.equals("aufCreatedUsr"))
			return getAufCreatedUsr();
		if (propertyName.equals("aufModifiedUsr"))
			return getAufModifiedUsr();
		if (propertyName.equals("aufId"))
			return getAufId();
		if (propertyName.equals("filId"))
			return getFilId();
		if (propertyName.equals("aufName"))
			return getAufName();
		if (propertyName.equals("aufFileType"))
			return getAufFileType();
		if (propertyName.equals("aufParentAufId"))
			return getAufParentAufId();
		if (propertyName.equals("aufChecksum"))
			return getAufChecksum();
		if (propertyName.equals("aufFilesize"))
			return getAufFilesize();
		if (propertyName.equals("aufChildCount"))
			return getAufChildCount();
		if (propertyName.equals("aufBk"))
			return getAufBk();
		if (propertyName.equals("aufCreatedUsrId"))
			return getAufCreatedUsrId();
		if (propertyName.equals("aufModifiedUsrId"))
			return getAufModifiedUsrId();
		if (propertyName.equals("aufCreatedDttm"))
			return getAufCreatedDttm();
		if (propertyName.equals("aufModifiedDttm"))
			return getAufModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getAufId()
	{
		return aufId;
	}

	public void setAufId(Integer aufId)
	{
		this.aufId = aufId;
	}

	public Integer getFilId()
	{
		return filId;
	}

	public void setFilId(Integer filId)
	{
		this.filId = filId;
	}

	public String getAufName()
	{
		return aufName;
	}

	public void setAufName(String aufName)
	{
		this.aufName = aufName;
	}

	public String getAufFileType()
	{
		return aufFileType;
	}

	public void setAufFileType(String aufFileType)
	{
		this.aufFileType = aufFileType;
	}

	public Integer getAufParentAufId()
	{
		return aufParentAufId;
	}

	public void setAufParentAufId(Integer aufParentAufId)
	{
		this.aufParentAufId = aufParentAufId;
	}

	public Long getAufChecksum()
	{
		return aufChecksum;
	}

	public void setAufChecksum(Long aufChecksum)
	{
		this.aufChecksum = aufChecksum;
	}

	public Long getAufFilesize()
	{
		return aufFilesize;
	}

	public void setAufFilesize(Long aufFilesize)
	{
		this.aufFilesize = aufFilesize;
	}

	public Long getAufChildCount()
	{
		return aufChildCount;
	}

	public void setAufChildCount(Long aufChildCount)
	{
		this.aufChildCount = aufChildCount;
	}

	public String getAufBk()
	{
		return aufBk;
	}

	public void setAufBk(String aufBk)
	{
		this.aufBk = aufBk;
	}

	public Integer getAufCreatedUsrId()
	{
		return aufCreatedUsrId;
	}

	public void setAufCreatedUsrId(Integer aufCreatedUsrId)
	{
		this.aufCreatedUsrId = aufCreatedUsrId;
	}

	public Integer getAufModifiedUsrId()
	{
		return aufModifiedUsrId;
	}

	public void setAufModifiedUsrId(Integer aufModifiedUsrId)
	{
		this.aufModifiedUsrId = aufModifiedUsrId;
	}

	public DateTime getAufCreatedDttm()
	{
		return aufCreatedDttm;
	}

	public void setAufCreatedDttm(DateTime aufCreatedDttm)
	{
		this.aufCreatedDttm = aufCreatedDttm;
	}

	public DateTime getAufModifiedDttm()
	{
		return aufModifiedDttm;
	}

	public void setAufModifiedDttm(DateTime aufModifiedDttm)
	{
		this.aufModifiedDttm = aufModifiedDttm;
	}

}
