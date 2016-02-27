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
import se.signa.signature.gen.dba.FiletblDba;
import se.signa.signature.gen.dba.UserTblDba;
import se.signa.signature.helpers.DisplayFieldHelper;

public abstract class PendingRpErrorFile extends SignatureDbo
{
	private Integer pefId;
	private Integer filId;
	private Integer fasId;
	private Integer pefErrorCount;
	private boolean pefPollFl;
	private String pefName;
	private String pefBk;
	private Integer pefCreatedUsrId;
	private Integer pefModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime pefCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime pefModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			pefId = getInteger(rs.getInt("pef_id"));
			filId = getInteger(rs.getInt("fil_id"));
			fasId = getInteger(rs.getInt("fas_id"));
			pefErrorCount = rs.getInt("pef_error_count");
			pefPollFl = getBoolean(rs.getString("pef_poll_fl"));
			pefName = rs.getString("pef_name");
			pefBk = rs.getString("pef_bk");
			pefCreatedUsrId = getInteger(rs.getInt("pef_created_usr_id"));
			pefModifiedUsrId = getInteger(rs.getInt("pef_modified_usr_id"));
			pefCreatedDttm = getDateTime(rs.getTimestamp("pef_created_dttm"));
			pefModifiedDttm = getDateTime(rs.getTimestamp("pef_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return pefId;
	}

	public void setPk(int pk)
	{
		pefId = pk;
	}

	public String getBk()
	{
		return pefBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(pefId.toString());
		data.add(filId.toString());
		data.add(fasId.toString());
		data.add(pefErrorCount.toString());
		data.add(String.valueOf(pefPollFl));
		data.add(pefName.toString());
		data.add(getPefCreatedUsr());
		data.add(Constants.dttf.print(pefCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("fil", getFil());
		data.put("fas", getFas());
		data.put("pefErrorCount", getString(pefErrorCount));
		data.put("pefPollFl", getString(pefPollFl));
		data.put("pefName", getString(pefName));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setFilId(DisplayFieldHelper.getI().getPk(FiletblDba.class, input.getString("fil")));
		setFasId(DisplayFieldHelper.getI().getPk(FileParserSettingsDba.class, input.getString("fas")));
		setPefErrorCount(input.getInteger("pefErrorCount"));
		setPefPollFl(input.getBoolean("pefPollFl"));
		setPefName(input.getString("pefName"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(pefBk.toString());
		auditData.add(getPefCreatedUsr());
		auditData.add(pefModifiedUsrId == null ? Constants.NULLSTRING : getPefModifiedUsr());
		auditData.add(Constants.dttf.print(pefCreatedDttm));
		auditData.add(pefModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(pefModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return pefName;
	}

	public String getFil()
	{
		return DisplayFieldHelper.getI().getDisplayField(FiletblDba.class, filId);
	}

	public String getFas()
	{
		return DisplayFieldHelper.getI().getDisplayField(FileParserSettingsDba.class, fasId);
	}

	public String getPefCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, pefCreatedUsrId);
	}

	public String getPefModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, pefModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("fil"))
			return getFil();
		if (propertyName.equals("fas"))
			return getFas();
		if (propertyName.equals("pefCreatedUsr"))
			return getPefCreatedUsr();
		if (propertyName.equals("pefModifiedUsr"))
			return getPefModifiedUsr();
		if (propertyName.equals("pefId"))
			return getPefId();
		if (propertyName.equals("filId"))
			return getFilId();
		if (propertyName.equals("fasId"))
			return getFasId();
		if (propertyName.equals("pefErrorCount"))
			return getPefErrorCount();
		if (propertyName.equals("pefPollFl"))
			return getPefPollFl();
		if (propertyName.equals("pefName"))
			return getPefName();
		if (propertyName.equals("pefBk"))
			return getPefBk();
		if (propertyName.equals("pefCreatedUsrId"))
			return getPefCreatedUsrId();
		if (propertyName.equals("pefModifiedUsrId"))
			return getPefModifiedUsrId();
		if (propertyName.equals("pefCreatedDttm"))
			return getPefCreatedDttm();
		if (propertyName.equals("pefModifiedDttm"))
			return getPefModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getPefId()
	{
		return pefId;
	}

	public void setPefId(Integer pefId)
	{
		this.pefId = pefId;
	}

	public Integer getFilId()
	{
		return filId;
	}

	public void setFilId(Integer filId)
	{
		this.filId = filId;
	}

	public Integer getFasId()
	{
		return fasId;
	}

	public void setFasId(Integer fasId)
	{
		this.fasId = fasId;
	}

	public Integer getPefErrorCount()
	{
		return pefErrorCount;
	}

	public void setPefErrorCount(Integer pefErrorCount)
	{
		this.pefErrorCount = pefErrorCount;
	}

	public boolean getPefPollFl()
	{
		return pefPollFl;
	}

	public void setPefPollFl(boolean pefPollFl)
	{
		this.pefPollFl = pefPollFl;
	}

	public String getPefName()
	{
		return pefName;
	}

	public void setPefName(String pefName)
	{
		this.pefName = pefName;
	}

	public String getPefBk()
	{
		return pefBk;
	}

	public void setPefBk(String pefBk)
	{
		this.pefBk = pefBk;
	}

	public Integer getPefCreatedUsrId()
	{
		return pefCreatedUsrId;
	}

	public void setPefCreatedUsrId(Integer pefCreatedUsrId)
	{
		this.pefCreatedUsrId = pefCreatedUsrId;
	}

	public Integer getPefModifiedUsrId()
	{
		return pefModifiedUsrId;
	}

	public void setPefModifiedUsrId(Integer pefModifiedUsrId)
	{
		this.pefModifiedUsrId = pefModifiedUsrId;
	}

	public DateTime getPefCreatedDttm()
	{
		return pefCreatedDttm;
	}

	public void setPefCreatedDttm(DateTime pefCreatedDttm)
	{
		this.pefCreatedDttm = pefCreatedDttm;
	}

	public DateTime getPefModifiedDttm()
	{
		return pefModifiedDttm;
	}

	public void setPefModifiedDttm(DateTime pefModifiedDttm)
	{
		this.pefModifiedDttm = pefModifiedDttm;
	}

}
