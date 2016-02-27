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

public abstract class ScriptTbl extends SignatureDbo
{
	private Integer srtId;
	private String srtFilName;
	private String srtDesc;
	private String srtBk;
	private Integer srtCreatedUsrId;
	private Integer srtModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime srtCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime srtModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			srtId = getInteger(rs.getInt("srt_id"));
			srtFilName = rs.getString("srt_fil_name");
			srtDesc = rs.getString("srt_desc");
			srtBk = rs.getString("srt_bk");
			srtCreatedUsrId = getInteger(rs.getInt("srt_created_usr_id"));
			srtModifiedUsrId = getInteger(rs.getInt("srt_modified_usr_id"));
			srtCreatedDttm = getDateTime(rs.getTimestamp("srt_created_dttm"));
			srtModifiedDttm = getDateTime(rs.getTimestamp("srt_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return srtId;
	}

	public void setPk(int pk)
	{
		srtId = pk;
	}

	public String getBk()
	{
		return srtBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(srtId.toString());
		data.add(srtFilName.toString());
		data.add(srtDesc.toString());
		data.add(getSrtCreatedUsr());
		data.add(Constants.dttf.print(srtCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("srtFilName", getString(srtFilName));
		data.put("srtDesc", getString(srtDesc));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setSrtFilName(input.getString("srtFilName"));
		setSrtDesc(input.getString("srtDesc"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(srtBk.toString());
		auditData.add(getSrtCreatedUsr());
		auditData.add(srtModifiedUsrId == null ? Constants.NULLSTRING : getSrtModifiedUsr());
		auditData.add(Constants.dttf.print(srtCreatedDttm));
		auditData.add(srtModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(srtModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return srtFilName;
	}

	public String getSrtCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, srtCreatedUsrId);
	}

	public String getSrtModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, srtModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("srtCreatedUsr"))
			return getSrtCreatedUsr();
		if (propertyName.equals("srtModifiedUsr"))
			return getSrtModifiedUsr();
		if (propertyName.equals("srtId"))
			return getSrtId();
		if (propertyName.equals("srtFilName"))
			return getSrtFilName();
		if (propertyName.equals("srtDesc"))
			return getSrtDesc();
		if (propertyName.equals("srtBk"))
			return getSrtBk();
		if (propertyName.equals("srtCreatedUsrId"))
			return getSrtCreatedUsrId();
		if (propertyName.equals("srtModifiedUsrId"))
			return getSrtModifiedUsrId();
		if (propertyName.equals("srtCreatedDttm"))
			return getSrtCreatedDttm();
		if (propertyName.equals("srtModifiedDttm"))
			return getSrtModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getSrtId()
	{
		return srtId;
	}

	public void setSrtId(Integer srtId)
	{
		this.srtId = srtId;
	}

	public String getSrtFilName()
	{
		return srtFilName;
	}

	public void setSrtFilName(String srtFilName)
	{
		this.srtFilName = srtFilName;
	}

	public String getSrtDesc()
	{
		return srtDesc;
	}

	public void setSrtDesc(String srtDesc)
	{
		this.srtDesc = srtDesc;
	}

	public String getSrtBk()
	{
		return srtBk;
	}

	public void setSrtBk(String srtBk)
	{
		this.srtBk = srtBk;
	}

	public Integer getSrtCreatedUsrId()
	{
		return srtCreatedUsrId;
	}

	public void setSrtCreatedUsrId(Integer srtCreatedUsrId)
	{
		this.srtCreatedUsrId = srtCreatedUsrId;
	}

	public Integer getSrtModifiedUsrId()
	{
		return srtModifiedUsrId;
	}

	public void setSrtModifiedUsrId(Integer srtModifiedUsrId)
	{
		this.srtModifiedUsrId = srtModifiedUsrId;
	}

	public DateTime getSrtCreatedDttm()
	{
		return srtCreatedDttm;
	}

	public void setSrtCreatedDttm(DateTime srtCreatedDttm)
	{
		this.srtCreatedDttm = srtCreatedDttm;
	}

	public DateTime getSrtModifiedDttm()
	{
		return srtModifiedDttm;
	}

	public void setSrtModifiedDttm(DateTime srtModifiedDttm)
	{
		this.srtModifiedDttm = srtModifiedDttm;
	}

}
