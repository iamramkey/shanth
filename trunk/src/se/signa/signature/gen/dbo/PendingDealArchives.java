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

public abstract class PendingDealArchives extends SignatureDbo
{
	private Integer pdaId;
	private Integer delId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime pdaArchiveDate;
	private String pdaFilepath;
	private String pdaName;
	private String pdaBk;
	private Integer pdaCreatedUsrId;
	private Integer pdaModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime pdaCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime pdaModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			pdaId = getInteger(rs.getInt("pda_id"));
			delId = getInteger(rs.getInt("del_id"));
			pdaArchiveDate = getDateTime(rs.getTimestamp("pda_archive_date"));
			pdaFilepath = rs.getString("pda_filepath");
			pdaName = rs.getString("pda_name");
			pdaBk = rs.getString("pda_bk");
			pdaCreatedUsrId = getInteger(rs.getInt("pda_created_usr_id"));
			pdaModifiedUsrId = getInteger(rs.getInt("pda_modified_usr_id"));
			pdaCreatedDttm = getDateTime(rs.getTimestamp("pda_created_dttm"));
			pdaModifiedDttm = getDateTime(rs.getTimestamp("pda_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return pdaId;
	}

	public void setPk(int pk)
	{
		pdaId = pk;
	}

	public String getBk()
	{
		return pdaBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(pdaId.toString());
		data.add(delId.toString());
		data.add(Constants.dttf.print(pdaArchiveDate));
		data.add(pdaFilepath == null ? Constants.NULLSTRING : pdaFilepath.toString());
		data.add(pdaName.toString());
		data.add(getPdaCreatedUsr());
		data.add(Constants.dttf.print(pdaCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("delId", getDelId());
		data.put("pdaArchiveDate", Constants.dttf.print(pdaArchiveDate));
		data.put("pdaFilepath", getString(pdaFilepath));
		data.put("pdaName", getString(pdaName));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setPdaArchiveDate(input.getDate("pdaArchiveDate"));
		setPdaFilepath(input.getString("pdaFilepath"));
		setPdaName(input.getString("pdaName"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(pdaBk.toString());
		auditData.add(getPdaCreatedUsr());
		auditData.add(pdaModifiedUsrId == null ? Constants.NULLSTRING : getPdaModifiedUsr());
		auditData.add(Constants.dttf.print(pdaCreatedDttm));
		auditData.add(pdaModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(pdaModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return pdaName;
	}

	public String getPdaCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, pdaCreatedUsrId);
	}

	public String getPdaModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, pdaModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("pdaCreatedUsr"))
			return getPdaCreatedUsr();
		if (propertyName.equals("pdaModifiedUsr"))
			return getPdaModifiedUsr();
		if (propertyName.equals("pdaId"))
			return getPdaId();
		if (propertyName.equals("delId"))
			return getDelId();
		if (propertyName.equals("pdaArchiveDate"))
			return getPdaArchiveDate();
		if (propertyName.equals("pdaFilepath"))
			return getPdaFilepath();
		if (propertyName.equals("pdaName"))
			return getPdaName();
		if (propertyName.equals("pdaBk"))
			return getPdaBk();
		if (propertyName.equals("pdaCreatedUsrId"))
			return getPdaCreatedUsrId();
		if (propertyName.equals("pdaModifiedUsrId"))
			return getPdaModifiedUsrId();
		if (propertyName.equals("pdaCreatedDttm"))
			return getPdaCreatedDttm();
		if (propertyName.equals("pdaModifiedDttm"))
			return getPdaModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getPdaId()
	{
		return pdaId;
	}

	public void setPdaId(Integer pdaId)
	{
		this.pdaId = pdaId;
	}

	public Integer getDelId()
	{
		return delId;
	}

	public void setDelId(Integer delId)
	{
		this.delId = delId;
	}

	public DateTime getPdaArchiveDate()
	{
		return pdaArchiveDate;
	}

	public void setPdaArchiveDate(DateTime pdaArchiveDate)
	{
		this.pdaArchiveDate = pdaArchiveDate;
	}

	public String getPdaFilepath()
	{
		return pdaFilepath;
	}

	public void setPdaFilepath(String pdaFilepath)
	{
		this.pdaFilepath = pdaFilepath;
	}

	public String getPdaName()
	{
		return pdaName;
	}

	public void setPdaName(String pdaName)
	{
		this.pdaName = pdaName;
	}

	public String getPdaBk()
	{
		return pdaBk;
	}

	public void setPdaBk(String pdaBk)
	{
		this.pdaBk = pdaBk;
	}

	public Integer getPdaCreatedUsrId()
	{
		return pdaCreatedUsrId;
	}

	public void setPdaCreatedUsrId(Integer pdaCreatedUsrId)
	{
		this.pdaCreatedUsrId = pdaCreatedUsrId;
	}

	public Integer getPdaModifiedUsrId()
	{
		return pdaModifiedUsrId;
	}

	public void setPdaModifiedUsrId(Integer pdaModifiedUsrId)
	{
		this.pdaModifiedUsrId = pdaModifiedUsrId;
	}

	public DateTime getPdaCreatedDttm()
	{
		return pdaCreatedDttm;
	}

	public void setPdaCreatedDttm(DateTime pdaCreatedDttm)
	{
		this.pdaCreatedDttm = pdaCreatedDttm;
	}

	public DateTime getPdaModifiedDttm()
	{
		return pdaModifiedDttm;
	}

	public void setPdaModifiedDttm(DateTime pdaModifiedDttm)
	{
		this.pdaModifiedDttm = pdaModifiedDttm;
	}

}
