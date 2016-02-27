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

public abstract class Cachetbl extends SignatureDbo
{
	private Integer cacId;
	private String cacName;
	private String cacType;
	private String cacPackage;
	private String cacBk;
	private Integer cacCreatedUsrId;
	private Integer cacModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime cacCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime cacModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			cacId = getInteger(rs.getInt("cac_id"));
			cacName = rs.getString("cac_name");
			cacType = rs.getString("cac_type");
			cacPackage = rs.getString("cac_package");
			cacBk = rs.getString("cac_bk");
			cacCreatedUsrId = getInteger(rs.getInt("cac_created_usr_id"));
			cacModifiedUsrId = getInteger(rs.getInt("cac_modified_usr_id"));
			cacCreatedDttm = getDateTime(rs.getTimestamp("cac_created_dttm"));
			cacModifiedDttm = getDateTime(rs.getTimestamp("cac_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return cacId;
	}

	public void setPk(int pk)
	{
		cacId = pk;
	}

	public String getBk()
	{
		return cacBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(cacId.toString());
		data.add(cacName.toString());
		data.add(cacType.toString());
		data.add(cacPackage.toString());
		data.add(getCacCreatedUsr());
		data.add(Constants.dttf.print(cacCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("cacName", getString(cacName));
		data.put("cacType", getString(cacType));
		data.put("cacPackage", getString(cacPackage));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setCacName(input.getString("cacName"));
		setCacType(input.getString("cacType"));
		setCacPackage(input.getString("cacPackage"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(cacBk.toString());
		auditData.add(getCacCreatedUsr());
		auditData.add(cacModifiedUsrId == null ? Constants.NULLSTRING : getCacModifiedUsr());
		auditData.add(Constants.dttf.print(cacCreatedDttm));
		auditData.add(cacModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(cacModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return cacName;
	}

	public String getCacCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, cacCreatedUsrId);
	}

	public String getCacModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, cacModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("cacCreatedUsr"))
			return getCacCreatedUsr();
		if (propertyName.equals("cacModifiedUsr"))
			return getCacModifiedUsr();
		if (propertyName.equals("cacId"))
			return getCacId();
		if (propertyName.equals("cacName"))
			return getCacName();
		if (propertyName.equals("cacType"))
			return getCacType();
		if (propertyName.equals("cacPackage"))
			return getCacPackage();
		if (propertyName.equals("cacBk"))
			return getCacBk();
		if (propertyName.equals("cacCreatedUsrId"))
			return getCacCreatedUsrId();
		if (propertyName.equals("cacModifiedUsrId"))
			return getCacModifiedUsrId();
		if (propertyName.equals("cacCreatedDttm"))
			return getCacCreatedDttm();
		if (propertyName.equals("cacModifiedDttm"))
			return getCacModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getCacId()
	{
		return cacId;
	}

	public void setCacId(Integer cacId)
	{
		this.cacId = cacId;
	}

	public String getCacName()
	{
		return cacName;
	}

	public void setCacName(String cacName)
	{
		this.cacName = cacName;
	}

	public String getCacType()
	{
		return cacType;
	}

	public void setCacType(String cacType)
	{
		this.cacType = cacType;
	}

	public String getCacPackage()
	{
		return cacPackage;
	}

	public void setCacPackage(String cacPackage)
	{
		this.cacPackage = cacPackage;
	}

	public String getCacBk()
	{
		return cacBk;
	}

	public void setCacBk(String cacBk)
	{
		this.cacBk = cacBk;
	}

	public Integer getCacCreatedUsrId()
	{
		return cacCreatedUsrId;
	}

	public void setCacCreatedUsrId(Integer cacCreatedUsrId)
	{
		this.cacCreatedUsrId = cacCreatedUsrId;
	}

	public Integer getCacModifiedUsrId()
	{
		return cacModifiedUsrId;
	}

	public void setCacModifiedUsrId(Integer cacModifiedUsrId)
	{
		this.cacModifiedUsrId = cacModifiedUsrId;
	}

	public DateTime getCacCreatedDttm()
	{
		return cacCreatedDttm;
	}

	public void setCacCreatedDttm(DateTime cacCreatedDttm)
	{
		this.cacCreatedDttm = cacCreatedDttm;
	}

	public DateTime getCacModifiedDttm()
	{
		return cacModifiedDttm;
	}

	public void setCacModifiedDttm(DateTime cacModifiedDttm)
	{
		this.cacModifiedDttm = cacModifiedDttm;
	}

}
