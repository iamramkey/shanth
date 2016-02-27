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

public abstract class ServerSettings extends SignatureDbo
{
	private Integer sesId;
	private String sesCode;
	private String sesValue;
	private String sesBk;
	private Integer sesCreatedUsrId;
	private Integer sesModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime sesCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime sesModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			sesId = getInteger(rs.getInt("ses_id"));
			sesCode = rs.getString("ses_code");
			sesValue = rs.getString("ses_value");
			sesBk = rs.getString("ses_bk");
			sesCreatedUsrId = getInteger(rs.getInt("ses_created_usr_id"));
			sesModifiedUsrId = getInteger(rs.getInt("ses_modified_usr_id"));
			sesCreatedDttm = getDateTime(rs.getTimestamp("ses_created_dttm"));
			sesModifiedDttm = getDateTime(rs.getTimestamp("ses_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return sesId;
	}

	public void setPk(int pk)
	{
		sesId = pk;
	}

	public String getBk()
	{
		return sesBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(sesId.toString());
		data.add(sesCode.toString());
		data.add(sesValue.toString());
		data.add(getSesCreatedUsr());
		data.add(Constants.dttf.print(sesCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("sesCode", getString(sesCode));
		data.put("sesValue", getString(sesValue));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setSesCode(input.getString("sesCode"));
		setSesValue(input.getString("sesValue"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(sesBk.toString());
		auditData.add(getSesCreatedUsr());
		auditData.add(sesModifiedUsrId == null ? Constants.NULLSTRING : getSesModifiedUsr());
		auditData.add(Constants.dttf.print(sesCreatedDttm));
		auditData.add(sesModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(sesModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return sesCode;
	}

	public String getSesCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, sesCreatedUsrId);
	}

	public String getSesModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, sesModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("sesCreatedUsr"))
			return getSesCreatedUsr();
		if (propertyName.equals("sesModifiedUsr"))
			return getSesModifiedUsr();
		if (propertyName.equals("sesId"))
			return getSesId();
		if (propertyName.equals("sesCode"))
			return getSesCode();
		if (propertyName.equals("sesValue"))
			return getSesValue();
		if (propertyName.equals("sesBk"))
			return getSesBk();
		if (propertyName.equals("sesCreatedUsrId"))
			return getSesCreatedUsrId();
		if (propertyName.equals("sesModifiedUsrId"))
			return getSesModifiedUsrId();
		if (propertyName.equals("sesCreatedDttm"))
			return getSesCreatedDttm();
		if (propertyName.equals("sesModifiedDttm"))
			return getSesModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getSesId()
	{
		return sesId;
	}

	public void setSesId(Integer sesId)
	{
		this.sesId = sesId;
	}

	public String getSesCode()
	{
		return sesCode;
	}

	public void setSesCode(String sesCode)
	{
		this.sesCode = sesCode;
	}

	public String getSesValue()
	{
		return sesValue;
	}

	public void setSesValue(String sesValue)
	{
		this.sesValue = sesValue;
	}

	public String getSesBk()
	{
		return sesBk;
	}

	public void setSesBk(String sesBk)
	{
		this.sesBk = sesBk;
	}

	public Integer getSesCreatedUsrId()
	{
		return sesCreatedUsrId;
	}

	public void setSesCreatedUsrId(Integer sesCreatedUsrId)
	{
		this.sesCreatedUsrId = sesCreatedUsrId;
	}

	public Integer getSesModifiedUsrId()
	{
		return sesModifiedUsrId;
	}

	public void setSesModifiedUsrId(Integer sesModifiedUsrId)
	{
		this.sesModifiedUsrId = sesModifiedUsrId;
	}

	public DateTime getSesCreatedDttm()
	{
		return sesCreatedDttm;
	}

	public void setSesCreatedDttm(DateTime sesCreatedDttm)
	{
		this.sesCreatedDttm = sesCreatedDttm;
	}

	public DateTime getSesModifiedDttm()
	{
		return sesModifiedDttm;
	}

	public void setSesModifiedDttm(DateTime sesModifiedDttm)
	{
		this.sesModifiedDttm = sesModifiedDttm;
	}

}
