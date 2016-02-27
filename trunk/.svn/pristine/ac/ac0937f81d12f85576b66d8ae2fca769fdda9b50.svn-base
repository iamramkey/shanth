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

public abstract class CommandType extends SignatureDbo
{
	private Integer ctpId;
	private String ctpCode;
	private String ctpPackage;
	private String ctpBk;
	private Integer ctpCreatedUsrId;
	private Integer ctpModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime ctpCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime ctpModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			ctpId = getInteger(rs.getInt("ctp_id"));
			ctpCode = rs.getString("ctp_code");
			ctpPackage = rs.getString("ctp_package");
			ctpBk = rs.getString("ctp_bk");
			ctpCreatedUsrId = getInteger(rs.getInt("ctp_created_usr_id"));
			ctpModifiedUsrId = getInteger(rs.getInt("ctp_modified_usr_id"));
			ctpCreatedDttm = getDateTime(rs.getTimestamp("ctp_created_dttm"));
			ctpModifiedDttm = getDateTime(rs.getTimestamp("ctp_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return ctpId;
	}

	public void setPk(int pk)
	{
		ctpId = pk;
	}

	public String getBk()
	{
		return ctpBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(ctpId.toString());
		data.add(ctpCode.toString());
		data.add(ctpPackage.toString());
		data.add(getCtpCreatedUsr());
		data.add(Constants.dttf.print(ctpCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("ctpCode", getString(ctpCode));
		data.put("ctpPackage", getString(ctpPackage));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setCtpCode(input.getString("ctpCode"));
		setCtpPackage(input.getString("ctpPackage"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(ctpBk.toString());
		auditData.add(getCtpCreatedUsr());
		auditData.add(ctpModifiedUsrId == null ? Constants.NULLSTRING : getCtpModifiedUsr());
		auditData.add(Constants.dttf.print(ctpCreatedDttm));
		auditData.add(ctpModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(ctpModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return ctpCode;
	}

	public String getCtpCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, ctpCreatedUsrId);
	}

	public String getCtpModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, ctpModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("ctpCreatedUsr"))
			return getCtpCreatedUsr();
		if (propertyName.equals("ctpModifiedUsr"))
			return getCtpModifiedUsr();
		if (propertyName.equals("ctpId"))
			return getCtpId();
		if (propertyName.equals("ctpCode"))
			return getCtpCode();
		if (propertyName.equals("ctpPackage"))
			return getCtpPackage();
		if (propertyName.equals("ctpBk"))
			return getCtpBk();
		if (propertyName.equals("ctpCreatedUsrId"))
			return getCtpCreatedUsrId();
		if (propertyName.equals("ctpModifiedUsrId"))
			return getCtpModifiedUsrId();
		if (propertyName.equals("ctpCreatedDttm"))
			return getCtpCreatedDttm();
		if (propertyName.equals("ctpModifiedDttm"))
			return getCtpModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getCtpId()
	{
		return ctpId;
	}

	public void setCtpId(Integer ctpId)
	{
		this.ctpId = ctpId;
	}

	public String getCtpCode()
	{
		return ctpCode;
	}

	public void setCtpCode(String ctpCode)
	{
		this.ctpCode = ctpCode;
	}

	public String getCtpPackage()
	{
		return ctpPackage;
	}

	public void setCtpPackage(String ctpPackage)
	{
		this.ctpPackage = ctpPackage;
	}

	public String getCtpBk()
	{
		return ctpBk;
	}

	public void setCtpBk(String ctpBk)
	{
		this.ctpBk = ctpBk;
	}

	public Integer getCtpCreatedUsrId()
	{
		return ctpCreatedUsrId;
	}

	public void setCtpCreatedUsrId(Integer ctpCreatedUsrId)
	{
		this.ctpCreatedUsrId = ctpCreatedUsrId;
	}

	public Integer getCtpModifiedUsrId()
	{
		return ctpModifiedUsrId;
	}

	public void setCtpModifiedUsrId(Integer ctpModifiedUsrId)
	{
		this.ctpModifiedUsrId = ctpModifiedUsrId;
	}

	public DateTime getCtpCreatedDttm()
	{
		return ctpCreatedDttm;
	}

	public void setCtpCreatedDttm(DateTime ctpCreatedDttm)
	{
		this.ctpCreatedDttm = ctpCreatedDttm;
	}

	public DateTime getCtpModifiedDttm()
	{
		return ctpModifiedDttm;
	}

	public void setCtpModifiedDttm(DateTime ctpModifiedDttm)
	{
		this.ctpModifiedDttm = ctpModifiedDttm;
	}

}
