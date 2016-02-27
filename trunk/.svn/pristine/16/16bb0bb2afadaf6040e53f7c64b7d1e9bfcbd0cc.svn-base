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

public abstract class EmailSettings extends SignatureDbo
{
	private Integer emsId;
	private String emsSmtpAuth;
	private String emsSmtpHost;
	private Integer emsSmtpPort;
	private String emsSmtpStarttlsEnable;
	private String emsUserName;
	private String emsPassword;
	private String emsFromEmail;
	private String emsBk;
	private Integer emsCreatedUsrId;
	private Integer emsModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime emsCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime emsModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			emsId = getInteger(rs.getInt("ems_id"));
			emsSmtpAuth = rs.getString("ems_smtp_auth");
			emsSmtpHost = rs.getString("ems_smtp_host");
			emsSmtpPort = rs.getInt("ems_smtp_port");
			emsSmtpStarttlsEnable = rs.getString("ems_smtp_starttls_enable");
			emsUserName = rs.getString("ems_user_name");
			emsPassword = rs.getString("ems_password");
			emsFromEmail = rs.getString("ems_from_email");
			emsBk = rs.getString("ems_bk");
			emsCreatedUsrId = getInteger(rs.getInt("ems_created_usr_id"));
			emsModifiedUsrId = getInteger(rs.getInt("ems_modified_usr_id"));
			emsCreatedDttm = getDateTime(rs.getTimestamp("ems_created_dttm"));
			emsModifiedDttm = getDateTime(rs.getTimestamp("ems_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return emsId;
	}

	public void setPk(int pk)
	{
		emsId = pk;
	}

	public String getBk()
	{
		return emsBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(emsId.toString());
		data.add(emsSmtpAuth.toString());
		data.add(emsSmtpHost.toString());
		data.add(emsSmtpPort.toString());
		data.add(emsSmtpStarttlsEnable.toString());
		data.add(emsUserName.toString());
		data.add(emsPassword.toString());
		data.add(emsFromEmail.toString());
		data.add(getEmsCreatedUsr());
		data.add(Constants.dttf.print(emsCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("emsSmtpAuth", getString(emsSmtpAuth));
		data.put("emsSmtpHost", getString(emsSmtpHost));
		data.put("emsSmtpPort", getString(emsSmtpPort));
		data.put("emsSmtpStarttlsEnable", getString(emsSmtpStarttlsEnable));
		data.put("emsUserName", getString(emsUserName));
		data.put("emsPassword", getString(emsPassword));
		data.put("emsFromEmail", getString(emsFromEmail));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setEmsSmtpAuth(input.getString("emsSmtpAuth"));
		setEmsSmtpHost(input.getString("emsSmtpHost"));
		setEmsSmtpPort(input.getInteger("emsSmtpPort"));
		setEmsSmtpStarttlsEnable(input.getString("emsSmtpStarttlsEnable"));
		setEmsUserName(input.getString("emsUserName"));
		setEmsPassword(input.getString("emsPassword"));
		setEmsFromEmail(input.getString("emsFromEmail"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(emsBk.toString());
		auditData.add(getEmsCreatedUsr());
		auditData.add(emsModifiedUsrId == null ? Constants.NULLSTRING : getEmsModifiedUsr());
		auditData.add(Constants.dttf.print(emsCreatedDttm));
		auditData.add(emsModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(emsModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return emsUserName;
	}

	public String getEmsCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, emsCreatedUsrId);
	}

	public String getEmsModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, emsModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("emsCreatedUsr"))
			return getEmsCreatedUsr();
		if (propertyName.equals("emsModifiedUsr"))
			return getEmsModifiedUsr();
		if (propertyName.equals("emsId"))
			return getEmsId();
		if (propertyName.equals("emsSmtpAuth"))
			return getEmsSmtpAuth();
		if (propertyName.equals("emsSmtpHost"))
			return getEmsSmtpHost();
		if (propertyName.equals("emsSmtpPort"))
			return getEmsSmtpPort();
		if (propertyName.equals("emsSmtpStarttlsEnable"))
			return getEmsSmtpStarttlsEnable();
		if (propertyName.equals("emsUserName"))
			return getEmsUserName();
		if (propertyName.equals("emsPassword"))
			return getEmsPassword();
		if (propertyName.equals("emsFromEmail"))
			return getEmsFromEmail();
		if (propertyName.equals("emsBk"))
			return getEmsBk();
		if (propertyName.equals("emsCreatedUsrId"))
			return getEmsCreatedUsrId();
		if (propertyName.equals("emsModifiedUsrId"))
			return getEmsModifiedUsrId();
		if (propertyName.equals("emsCreatedDttm"))
			return getEmsCreatedDttm();
		if (propertyName.equals("emsModifiedDttm"))
			return getEmsModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getEmsId()
	{
		return emsId;
	}

	public void setEmsId(Integer emsId)
	{
		this.emsId = emsId;
	}

	public String getEmsSmtpAuth()
	{
		return emsSmtpAuth;
	}

	public void setEmsSmtpAuth(String emsSmtpAuth)
	{
		this.emsSmtpAuth = emsSmtpAuth;
	}

	public String getEmsSmtpHost()
	{
		return emsSmtpHost;
	}

	public void setEmsSmtpHost(String emsSmtpHost)
	{
		this.emsSmtpHost = emsSmtpHost;
	}

	public Integer getEmsSmtpPort()
	{
		return emsSmtpPort;
	}

	public void setEmsSmtpPort(Integer emsSmtpPort)
	{
		this.emsSmtpPort = emsSmtpPort;
	}

	public String getEmsSmtpStarttlsEnable()
	{
		return emsSmtpStarttlsEnable;
	}

	public void setEmsSmtpStarttlsEnable(String emsSmtpStarttlsEnable)
	{
		this.emsSmtpStarttlsEnable = emsSmtpStarttlsEnable;
	}

	public String getEmsUserName()
	{
		return emsUserName;
	}

	public void setEmsUserName(String emsUserName)
	{
		this.emsUserName = emsUserName;
	}

	public String getEmsPassword()
	{
		return emsPassword;
	}

	public void setEmsPassword(String emsPassword)
	{
		this.emsPassword = emsPassword;
	}

	public String getEmsFromEmail()
	{
		return emsFromEmail;
	}

	public void setEmsFromEmail(String emsFromEmail)
	{
		this.emsFromEmail = emsFromEmail;
	}

	public String getEmsBk()
	{
		return emsBk;
	}

	public void setEmsBk(String emsBk)
	{
		this.emsBk = emsBk;
	}

	public Integer getEmsCreatedUsrId()
	{
		return emsCreatedUsrId;
	}

	public void setEmsCreatedUsrId(Integer emsCreatedUsrId)
	{
		this.emsCreatedUsrId = emsCreatedUsrId;
	}

	public Integer getEmsModifiedUsrId()
	{
		return emsModifiedUsrId;
	}

	public void setEmsModifiedUsrId(Integer emsModifiedUsrId)
	{
		this.emsModifiedUsrId = emsModifiedUsrId;
	}

	public DateTime getEmsCreatedDttm()
	{
		return emsCreatedDttm;
	}

	public void setEmsCreatedDttm(DateTime emsCreatedDttm)
	{
		this.emsCreatedDttm = emsCreatedDttm;
	}

	public DateTime getEmsModifiedDttm()
	{
		return emsModifiedDttm;
	}

	public void setEmsModifiedDttm(DateTime emsModifiedDttm)
	{
		this.emsModifiedDttm = emsModifiedDttm;
	}

}
