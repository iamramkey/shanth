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

public abstract class SmsSettings extends SignatureDbo
{
	private Integer smsId;
	private String smsName;
	private String smsSmtpHost;
	private Integer smsSmtpPort;
	private String smsUserName;
	private String smsPassword;
	private String smsBk;
	private Integer smsCreatedUsrId;
	private Integer smsModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime smsCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime smsModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			smsId = getInteger(rs.getInt("sms_id"));
			smsName = rs.getString("sms_name");
			smsSmtpHost = rs.getString("sms_smtp_host");
			smsSmtpPort = rs.getInt("sms_smtp_port");
			smsUserName = rs.getString("sms_user_name");
			smsPassword = rs.getString("sms_password");
			smsBk = rs.getString("sms_bk");
			smsCreatedUsrId = getInteger(rs.getInt("sms_created_usr_id"));
			smsModifiedUsrId = getInteger(rs.getInt("sms_modified_usr_id"));
			smsCreatedDttm = getDateTime(rs.getTimestamp("sms_created_dttm"));
			smsModifiedDttm = getDateTime(rs.getTimestamp("sms_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return smsId;
	}

	public void setPk(int pk)
	{
		smsId = pk;
	}

	public String getBk()
	{
		return smsBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(smsId.toString());
		data.add(smsName.toString());
		data.add(smsSmtpHost.toString());
		data.add(smsSmtpPort.toString());
		data.add(smsUserName.toString());
		data.add(smsPassword.toString());
		data.add(getSmsCreatedUsr());
		data.add(Constants.dttf.print(smsCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("smsName", getString(smsName));
		data.put("smsSmtpHost", getString(smsSmtpHost));
		data.put("smsSmtpPort", getString(smsSmtpPort));
		data.put("smsUserName", getString(smsUserName));
		data.put("smsPassword", getString(smsPassword));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setSmsName(input.getString("smsName"));
		setSmsSmtpHost(input.getString("smsSmtpHost"));
		setSmsSmtpPort(input.getInteger("smsSmtpPort"));
		setSmsUserName(input.getString("smsUserName"));
		setSmsPassword(input.getString("smsPassword"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(smsBk.toString());
		auditData.add(getSmsCreatedUsr());
		auditData.add(smsModifiedUsrId == null ? Constants.NULLSTRING : getSmsModifiedUsr());
		auditData.add(Constants.dttf.print(smsCreatedDttm));
		auditData.add(smsModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(smsModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return smsName;
	}

	public String getSmsCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, smsCreatedUsrId);
	}

	public String getSmsModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, smsModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("smsCreatedUsr"))
			return getSmsCreatedUsr();
		if (propertyName.equals("smsModifiedUsr"))
			return getSmsModifiedUsr();
		if (propertyName.equals("smsId"))
			return getSmsId();
		if (propertyName.equals("smsName"))
			return getSmsName();
		if (propertyName.equals("smsSmtpHost"))
			return getSmsSmtpHost();
		if (propertyName.equals("smsSmtpPort"))
			return getSmsSmtpPort();
		if (propertyName.equals("smsUserName"))
			return getSmsUserName();
		if (propertyName.equals("smsPassword"))
			return getSmsPassword();
		if (propertyName.equals("smsBk"))
			return getSmsBk();
		if (propertyName.equals("smsCreatedUsrId"))
			return getSmsCreatedUsrId();
		if (propertyName.equals("smsModifiedUsrId"))
			return getSmsModifiedUsrId();
		if (propertyName.equals("smsCreatedDttm"))
			return getSmsCreatedDttm();
		if (propertyName.equals("smsModifiedDttm"))
			return getSmsModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getSmsId()
	{
		return smsId;
	}

	public void setSmsId(Integer smsId)
	{
		this.smsId = smsId;
	}

	public String getSmsName()
	{
		return smsName;
	}

	public void setSmsName(String smsName)
	{
		this.smsName = smsName;
	}

	public String getSmsSmtpHost()
	{
		return smsSmtpHost;
	}

	public void setSmsSmtpHost(String smsSmtpHost)
	{
		this.smsSmtpHost = smsSmtpHost;
	}

	public Integer getSmsSmtpPort()
	{
		return smsSmtpPort;
	}

	public void setSmsSmtpPort(Integer smsSmtpPort)
	{
		this.smsSmtpPort = smsSmtpPort;
	}

	public String getSmsUserName()
	{
		return smsUserName;
	}

	public void setSmsUserName(String smsUserName)
	{
		this.smsUserName = smsUserName;
	}

	public String getSmsPassword()
	{
		return smsPassword;
	}

	public void setSmsPassword(String smsPassword)
	{
		this.smsPassword = smsPassword;
	}

	public String getSmsBk()
	{
		return smsBk;
	}

	public void setSmsBk(String smsBk)
	{
		this.smsBk = smsBk;
	}

	public Integer getSmsCreatedUsrId()
	{
		return smsCreatedUsrId;
	}

	public void setSmsCreatedUsrId(Integer smsCreatedUsrId)
	{
		this.smsCreatedUsrId = smsCreatedUsrId;
	}

	public Integer getSmsModifiedUsrId()
	{
		return smsModifiedUsrId;
	}

	public void setSmsModifiedUsrId(Integer smsModifiedUsrId)
	{
		this.smsModifiedUsrId = smsModifiedUsrId;
	}

	public DateTime getSmsCreatedDttm()
	{
		return smsCreatedDttm;
	}

	public void setSmsCreatedDttm(DateTime smsCreatedDttm)
	{
		this.smsCreatedDttm = smsCreatedDttm;
	}

	public DateTime getSmsModifiedDttm()
	{
		return smsModifiedDttm;
	}

	public void setSmsModifiedDttm(DateTime smsModifiedDttm)
	{
		this.smsModifiedDttm = smsModifiedDttm;
	}

}
