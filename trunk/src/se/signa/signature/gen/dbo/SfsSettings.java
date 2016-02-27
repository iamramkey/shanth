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
import se.signa.signature.gen.dba.JobTypeDba;
import se.signa.signature.gen.dba.UserTblDba;
import se.signa.signature.helpers.DisplayFieldHelper;

public abstract class SfsSettings extends SignatureDbo
{
	private Integer sfsId;
	private String sfsName;
	private String sfsServerAddress;
	private Integer sfsPort;
	private String sfsUsername;
	private String sfsPassword;
	private String sfsDirectory;
	private Integer jbtId;
	private String sfsBk;
	private Integer sfsCreatedUsrId;
	private Integer sfsModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime sfsCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime sfsModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			sfsId = getInteger(rs.getInt("sfs_id"));
			sfsName = rs.getString("sfs_name");
			sfsServerAddress = rs.getString("sfs_server_address");
			sfsPort = rs.getInt("sfs_port");
			sfsUsername = rs.getString("sfs_username");
			sfsPassword = rs.getString("sfs_password");
			sfsDirectory = rs.getString("sfs_directory");
			jbtId = getInteger(rs.getInt("jbt_id"));
			sfsBk = rs.getString("sfs_bk");
			sfsCreatedUsrId = getInteger(rs.getInt("sfs_created_usr_id"));
			sfsModifiedUsrId = getInteger(rs.getInt("sfs_modified_usr_id"));
			sfsCreatedDttm = getDateTime(rs.getTimestamp("sfs_created_dttm"));
			sfsModifiedDttm = getDateTime(rs.getTimestamp("sfs_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return sfsId;
	}

	public void setPk(int pk)
	{
		sfsId = pk;
	}

	public String getBk()
	{
		return sfsBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(sfsId.toString());
		data.add(sfsName.toString());
		data.add(sfsServerAddress.toString());
		data.add(sfsPort.toString());
		data.add(sfsUsername.toString());
		data.add(sfsPassword.toString());
		data.add(sfsDirectory.toString());
		data.add(jbtId.toString());
		data.add(getSfsCreatedUsr());
		data.add(Constants.dttf.print(sfsCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("sfsName", getString(sfsName));
		data.put("sfsServerAddress", getString(sfsServerAddress));
		data.put("sfsPort", getString(sfsPort));
		data.put("sfsUsername", getString(sfsUsername));
		data.put("sfsPassword", getString(sfsPassword));
		data.put("sfsDirectory", getString(sfsDirectory));
		data.put("jbt", getJbt());
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setSfsName(input.getString("sfsName"));
		setSfsServerAddress(input.getString("sfsServerAddress"));
		setSfsPort(input.getInteger("sfsPort"));
		setSfsUsername(input.getString("sfsUsername"));
		setSfsPassword(input.getString("sfsPassword"));
		setSfsDirectory(input.getString("sfsDirectory"));
		setJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("jbt")));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(sfsBk.toString());
		auditData.add(getSfsCreatedUsr());
		auditData.add(sfsModifiedUsrId == null ? Constants.NULLSTRING : getSfsModifiedUsr());
		auditData.add(Constants.dttf.print(sfsCreatedDttm));
		auditData.add(sfsModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(sfsModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return sfsName;
	}

	public String getJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, jbtId);
	}

	public String getSfsCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, sfsCreatedUsrId);
	}

	public String getSfsModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, sfsModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("jbt"))
			return getJbt();
		if (propertyName.equals("sfsCreatedUsr"))
			return getSfsCreatedUsr();
		if (propertyName.equals("sfsModifiedUsr"))
			return getSfsModifiedUsr();
		if (propertyName.equals("sfsId"))
			return getSfsId();
		if (propertyName.equals("sfsName"))
			return getSfsName();
		if (propertyName.equals("sfsServerAddress"))
			return getSfsServerAddress();
		if (propertyName.equals("sfsPort"))
			return getSfsPort();
		if (propertyName.equals("sfsUsername"))
			return getSfsUsername();
		if (propertyName.equals("sfsPassword"))
			return getSfsPassword();
		if (propertyName.equals("sfsDirectory"))
			return getSfsDirectory();
		if (propertyName.equals("jbtId"))
			return getJbtId();
		if (propertyName.equals("sfsBk"))
			return getSfsBk();
		if (propertyName.equals("sfsCreatedUsrId"))
			return getSfsCreatedUsrId();
		if (propertyName.equals("sfsModifiedUsrId"))
			return getSfsModifiedUsrId();
		if (propertyName.equals("sfsCreatedDttm"))
			return getSfsCreatedDttm();
		if (propertyName.equals("sfsModifiedDttm"))
			return getSfsModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getSfsId()
	{
		return sfsId;
	}

	public void setSfsId(Integer sfsId)
	{
		this.sfsId = sfsId;
	}

	public String getSfsName()
	{
		return sfsName;
	}

	public void setSfsName(String sfsName)
	{
		this.sfsName = sfsName;
	}

	public String getSfsServerAddress()
	{
		return sfsServerAddress;
	}

	public void setSfsServerAddress(String sfsServerAddress)
	{
		this.sfsServerAddress = sfsServerAddress;
	}

	public Integer getSfsPort()
	{
		return sfsPort;
	}

	public void setSfsPort(Integer sfsPort)
	{
		this.sfsPort = sfsPort;
	}

	public String getSfsUsername()
	{
		return sfsUsername;
	}

	public void setSfsUsername(String sfsUsername)
	{
		this.sfsUsername = sfsUsername;
	}

	public String getSfsPassword()
	{
		return sfsPassword;
	}

	public void setSfsPassword(String sfsPassword)
	{
		this.sfsPassword = sfsPassword;
	}

	public String getSfsDirectory()
	{
		return sfsDirectory;
	}

	public void setSfsDirectory(String sfsDirectory)
	{
		this.sfsDirectory = sfsDirectory;
	}

	public Integer getJbtId()
	{
		return jbtId;
	}

	public void setJbtId(Integer jbtId)
	{
		this.jbtId = jbtId;
	}

	public String getSfsBk()
	{
		return sfsBk;
	}

	public void setSfsBk(String sfsBk)
	{
		this.sfsBk = sfsBk;
	}

	public Integer getSfsCreatedUsrId()
	{
		return sfsCreatedUsrId;
	}

	public void setSfsCreatedUsrId(Integer sfsCreatedUsrId)
	{
		this.sfsCreatedUsrId = sfsCreatedUsrId;
	}

	public Integer getSfsModifiedUsrId()
	{
		return sfsModifiedUsrId;
	}

	public void setSfsModifiedUsrId(Integer sfsModifiedUsrId)
	{
		this.sfsModifiedUsrId = sfsModifiedUsrId;
	}

	public DateTime getSfsCreatedDttm()
	{
		return sfsCreatedDttm;
	}

	public void setSfsCreatedDttm(DateTime sfsCreatedDttm)
	{
		this.sfsCreatedDttm = sfsCreatedDttm;
	}

	public DateTime getSfsModifiedDttm()
	{
		return sfsModifiedDttm;
	}

	public void setSfsModifiedDttm(DateTime sfsModifiedDttm)
	{
		this.sfsModifiedDttm = sfsModifiedDttm;
	}

}
