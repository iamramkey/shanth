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

public abstract class AlertType extends SignatureDbo
{
	private Integer atpId;
	private String atpCode;
	private String atpDesc;
	private String atpPriority;
	private String atpEmail;
	private String atpSms;
	private String atpBk;
	private Integer atpCreatedUsrId;
	private Integer atpModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime atpCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime atpModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			atpId = getInteger(rs.getInt("atp_id"));
			atpCode = rs.getString("atp_code");
			atpDesc = rs.getString("atp_desc");
			atpPriority = rs.getString("atp_priority");
			atpEmail = rs.getString("atp_email");
			atpSms = rs.getString("atp_sms");
			atpBk = rs.getString("atp_bk");
			atpCreatedUsrId = getInteger(rs.getInt("atp_created_usr_id"));
			atpModifiedUsrId = getInteger(rs.getInt("atp_modified_usr_id"));
			atpCreatedDttm = getDateTime(rs.getTimestamp("atp_created_dttm"));
			atpModifiedDttm = getDateTime(rs.getTimestamp("atp_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return atpId;
	}

	public void setPk(int pk)
	{
		atpId = pk;
	}

	public String getBk()
	{
		return atpBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(atpId.toString());
		data.add(atpCode.toString());
		data.add(atpDesc.toString());
		data.add(atpPriority.toString());
		data.add(atpEmail == null ? Constants.NULLSTRING : atpEmail.toString());
		data.add(atpSms == null ? Constants.NULLSTRING : atpSms.toString());
		data.add(getAtpCreatedUsr());
		data.add(Constants.dttf.print(atpCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("atpCode", getString(atpCode));
		data.put("atpDesc", getString(atpDesc));
		data.put("atpPriority", getString(atpPriority));
		data.put("atpEmail", getString(atpEmail));
		data.put("atpSms", getString(atpSms));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setAtpCode(input.getString("atpCode"));
		setAtpDesc(input.getString("atpDesc"));
		setAtpPriority(input.getString("atpPriority"));
		setAtpEmail(input.getString("atpEmail"));
		setAtpSms(input.getString("atpSms"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(atpBk.toString());
		auditData.add(getAtpCreatedUsr());
		auditData.add(atpModifiedUsrId == null ? Constants.NULLSTRING : getAtpModifiedUsr());
		auditData.add(Constants.dttf.print(atpCreatedDttm));
		auditData.add(atpModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(atpModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return atpCode;
	}

	public String getAtpCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, atpCreatedUsrId);
	}

	public String getAtpModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, atpModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("atpCreatedUsr"))
			return getAtpCreatedUsr();
		if (propertyName.equals("atpModifiedUsr"))
			return getAtpModifiedUsr();
		if (propertyName.equals("atpId"))
			return getAtpId();
		if (propertyName.equals("atpCode"))
			return getAtpCode();
		if (propertyName.equals("atpDesc"))
			return getAtpDesc();
		if (propertyName.equals("atpPriority"))
			return getAtpPriority();
		if (propertyName.equals("atpEmail"))
			return getAtpEmail();
		if (propertyName.equals("atpSms"))
			return getAtpSms();
		if (propertyName.equals("atpBk"))
			return getAtpBk();
		if (propertyName.equals("atpCreatedUsrId"))
			return getAtpCreatedUsrId();
		if (propertyName.equals("atpModifiedUsrId"))
			return getAtpModifiedUsrId();
		if (propertyName.equals("atpCreatedDttm"))
			return getAtpCreatedDttm();
		if (propertyName.equals("atpModifiedDttm"))
			return getAtpModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getAtpId()
	{
		return atpId;
	}

	public void setAtpId(Integer atpId)
	{
		this.atpId = atpId;
	}

	public String getAtpCode()
	{
		return atpCode;
	}

	public void setAtpCode(String atpCode)
	{
		this.atpCode = atpCode;
	}

	public String getAtpDesc()
	{
		return atpDesc;
	}

	public void setAtpDesc(String atpDesc)
	{
		this.atpDesc = atpDesc;
	}

	public String getAtpPriority()
	{
		return atpPriority;
	}

	public void setAtpPriority(String atpPriority)
	{
		this.atpPriority = atpPriority;
	}

	public String getAtpEmail()
	{
		return atpEmail;
	}

	public void setAtpEmail(String atpEmail)
	{
		this.atpEmail = atpEmail;
	}

	public String getAtpSms()
	{
		return atpSms;
	}

	public void setAtpSms(String atpSms)
	{
		this.atpSms = atpSms;
	}

	public String getAtpBk()
	{
		return atpBk;
	}

	public void setAtpBk(String atpBk)
	{
		this.atpBk = atpBk;
	}

	public Integer getAtpCreatedUsrId()
	{
		return atpCreatedUsrId;
	}

	public void setAtpCreatedUsrId(Integer atpCreatedUsrId)
	{
		this.atpCreatedUsrId = atpCreatedUsrId;
	}

	public Integer getAtpModifiedUsrId()
	{
		return atpModifiedUsrId;
	}

	public void setAtpModifiedUsrId(Integer atpModifiedUsrId)
	{
		this.atpModifiedUsrId = atpModifiedUsrId;
	}

	public DateTime getAtpCreatedDttm()
	{
		return atpCreatedDttm;
	}

	public void setAtpCreatedDttm(DateTime atpCreatedDttm)
	{
		this.atpCreatedDttm = atpCreatedDttm;
	}

	public DateTime getAtpModifiedDttm()
	{
		return atpModifiedDttm;
	}

	public void setAtpModifiedDttm(DateTime atpModifiedDttm)
	{
		this.atpModifiedDttm = atpModifiedDttm;
	}

}
