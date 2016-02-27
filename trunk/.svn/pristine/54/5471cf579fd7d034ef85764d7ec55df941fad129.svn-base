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

public abstract class FtpSettings extends SignatureDbo
{
	private Integer ftpId;
	private String ftpServerAddress;
	private String ftpUsername;
	private String ftpPassword;
	private String ftpDirectory;
	private String ftpBk;
	private Integer ftpCreatedUsrId;
	private Integer ftpModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime ftpCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime ftpModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			ftpId = getInteger(rs.getInt("ftp_id"));
			ftpServerAddress = rs.getString("ftp_server_address");
			ftpUsername = rs.getString("ftp_username");
			ftpPassword = rs.getString("ftp_password");
			ftpDirectory = rs.getString("ftp_directory");
			ftpBk = rs.getString("ftp_bk");
			ftpCreatedUsrId = getInteger(rs.getInt("ftp_created_usr_id"));
			ftpModifiedUsrId = getInteger(rs.getInt("ftp_modified_usr_id"));
			ftpCreatedDttm = getDateTime(rs.getTimestamp("ftp_created_dttm"));
			ftpModifiedDttm = getDateTime(rs.getTimestamp("ftp_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return ftpId;
	}

	public void setPk(int pk)
	{
		ftpId = pk;
	}

	public String getBk()
	{
		return ftpBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(ftpId.toString());
		data.add(ftpServerAddress.toString());
		data.add(ftpUsername.toString());
		data.add(ftpPassword.toString());
		data.add(ftpDirectory.toString());
		data.add(getFtpCreatedUsr());
		data.add(Constants.dttf.print(ftpCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("ftpServerAddress", getString(ftpServerAddress));
		data.put("ftpUsername", getString(ftpUsername));
		data.put("ftpPassword", getString(ftpPassword));
		data.put("ftpDirectory", getString(ftpDirectory));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setFtpServerAddress(input.getString("ftpServerAddress"));
		setFtpUsername(input.getString("ftpUsername"));
		setFtpPassword(input.getString("ftpPassword"));
		setFtpDirectory(input.getString("ftpDirectory"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(ftpBk.toString());
		auditData.add(getFtpCreatedUsr());
		auditData.add(ftpModifiedUsrId == null ? Constants.NULLSTRING : getFtpModifiedUsr());
		auditData.add(Constants.dttf.print(ftpCreatedDttm));
		auditData.add(ftpModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(ftpModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return ftpUsername;
	}

	public String getFtpCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, ftpCreatedUsrId);
	}

	public String getFtpModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, ftpModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("ftpCreatedUsr"))
			return getFtpCreatedUsr();
		if (propertyName.equals("ftpModifiedUsr"))
			return getFtpModifiedUsr();
		if (propertyName.equals("ftpId"))
			return getFtpId();
		if (propertyName.equals("ftpServerAddress"))
			return getFtpServerAddress();
		if (propertyName.equals("ftpUsername"))
			return getFtpUsername();
		if (propertyName.equals("ftpPassword"))
			return getFtpPassword();
		if (propertyName.equals("ftpDirectory"))
			return getFtpDirectory();
		if (propertyName.equals("ftpBk"))
			return getFtpBk();
		if (propertyName.equals("ftpCreatedUsrId"))
			return getFtpCreatedUsrId();
		if (propertyName.equals("ftpModifiedUsrId"))
			return getFtpModifiedUsrId();
		if (propertyName.equals("ftpCreatedDttm"))
			return getFtpCreatedDttm();
		if (propertyName.equals("ftpModifiedDttm"))
			return getFtpModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getFtpId()
	{
		return ftpId;
	}

	public void setFtpId(Integer ftpId)
	{
		this.ftpId = ftpId;
	}

	public String getFtpServerAddress()
	{
		return ftpServerAddress;
	}

	public void setFtpServerAddress(String ftpServerAddress)
	{
		this.ftpServerAddress = ftpServerAddress;
	}

	public String getFtpUsername()
	{
		return ftpUsername;
	}

	public void setFtpUsername(String ftpUsername)
	{
		this.ftpUsername = ftpUsername;
	}

	public String getFtpPassword()
	{
		return ftpPassword;
	}

	public void setFtpPassword(String ftpPassword)
	{
		this.ftpPassword = ftpPassword;
	}

	public String getFtpDirectory()
	{
		return ftpDirectory;
	}

	public void setFtpDirectory(String ftpDirectory)
	{
		this.ftpDirectory = ftpDirectory;
	}

	public String getFtpBk()
	{
		return ftpBk;
	}

	public void setFtpBk(String ftpBk)
	{
		this.ftpBk = ftpBk;
	}

	public Integer getFtpCreatedUsrId()
	{
		return ftpCreatedUsrId;
	}

	public void setFtpCreatedUsrId(Integer ftpCreatedUsrId)
	{
		this.ftpCreatedUsrId = ftpCreatedUsrId;
	}

	public Integer getFtpModifiedUsrId()
	{
		return ftpModifiedUsrId;
	}

	public void setFtpModifiedUsrId(Integer ftpModifiedUsrId)
	{
		this.ftpModifiedUsrId = ftpModifiedUsrId;
	}

	public DateTime getFtpCreatedDttm()
	{
		return ftpCreatedDttm;
	}

	public void setFtpCreatedDttm(DateTime ftpCreatedDttm)
	{
		this.ftpCreatedDttm = ftpCreatedDttm;
	}

	public DateTime getFtpModifiedDttm()
	{
		return ftpModifiedDttm;
	}

	public void setFtpModifiedDttm(DateTime ftpModifiedDttm)
	{
		this.ftpModifiedDttm = ftpModifiedDttm;
	}

}
