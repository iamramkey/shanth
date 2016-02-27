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

public abstract class UserPassword extends SignatureDbo
{
	private Integer uspId;
	private Integer usrId;
	private String uspPassword;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime uspChangedDttm;
	private String uspName;
	private String uspBk;
	private Integer uspCreatedUsrId;
	private Integer uspModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime uspCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime uspModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			uspId = getInteger(rs.getInt("usp_id"));
			usrId = getInteger(rs.getInt("usr_id"));
			uspPassword = rs.getString("usp_password");
			uspChangedDttm = getDateTime(rs.getTimestamp("usp_changed_dttm"));
			uspName = rs.getString("usp_name");
			uspBk = rs.getString("usp_bk");
			uspCreatedUsrId = getInteger(rs.getInt("usp_created_usr_id"));
			uspModifiedUsrId = getInteger(rs.getInt("usp_modified_usr_id"));
			uspCreatedDttm = getDateTime(rs.getTimestamp("usp_created_dttm"));
			uspModifiedDttm = getDateTime(rs.getTimestamp("usp_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return uspId;
	}

	public void setPk(int pk)
	{
		uspId = pk;
	}

	public String getBk()
	{
		return uspBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(uspId.toString());
		data.add(usrId.toString());
		data.add(uspPassword.toString());
		data.add(Constants.dttf.print(uspChangedDttm));
		data.add(uspName.toString());
		data.add(getUspCreatedUsr());
		data.add(Constants.dttf.print(uspCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("usr", getUsr());
		data.put("uspPassword", getString(uspPassword));
		data.put("uspChangedDttm", Constants.dttf.print(uspChangedDttm));
		data.put("uspName", getString(uspName));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setUsrId(DisplayFieldHelper.getI().getPk(UserTblDba.class, input.getString("usr")));
		setUspPassword(input.getString("uspPassword"));
		setUspChangedDttm(input.getDate("uspChangedDttm"));
		setUspName(input.getString("uspName"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(uspBk.toString());
		auditData.add(getUspCreatedUsr());
		auditData.add(uspModifiedUsrId == null ? Constants.NULLSTRING : getUspModifiedUsr());
		auditData.add(Constants.dttf.print(uspCreatedDttm));
		auditData.add(uspModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(uspModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return uspName;
	}

	public String getUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, usrId);
	}

	public String getUspCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, uspCreatedUsrId);
	}

	public String getUspModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, uspModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("usr"))
			return getUsr();
		if (propertyName.equals("uspCreatedUsr"))
			return getUspCreatedUsr();
		if (propertyName.equals("uspModifiedUsr"))
			return getUspModifiedUsr();
		if (propertyName.equals("uspId"))
			return getUspId();
		if (propertyName.equals("usrId"))
			return getUsrId();
		if (propertyName.equals("uspPassword"))
			return getUspPassword();
		if (propertyName.equals("uspChangedDttm"))
			return getUspChangedDttm();
		if (propertyName.equals("uspName"))
			return getUspName();
		if (propertyName.equals("uspBk"))
			return getUspBk();
		if (propertyName.equals("uspCreatedUsrId"))
			return getUspCreatedUsrId();
		if (propertyName.equals("uspModifiedUsrId"))
			return getUspModifiedUsrId();
		if (propertyName.equals("uspCreatedDttm"))
			return getUspCreatedDttm();
		if (propertyName.equals("uspModifiedDttm"))
			return getUspModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getUspId()
	{
		return uspId;
	}

	public void setUspId(Integer uspId)
	{
		this.uspId = uspId;
	}

	public Integer getUsrId()
	{
		return usrId;
	}

	public void setUsrId(Integer usrId)
	{
		this.usrId = usrId;
	}

	public String getUspPassword()
	{
		return uspPassword;
	}

	public void setUspPassword(String uspPassword)
	{
		this.uspPassword = uspPassword;
	}

	public DateTime getUspChangedDttm()
	{
		return uspChangedDttm;
	}

	public void setUspChangedDttm(DateTime uspChangedDttm)
	{
		this.uspChangedDttm = uspChangedDttm;
	}

	public String getUspName()
	{
		return uspName;
	}

	public void setUspName(String uspName)
	{
		this.uspName = uspName;
	}

	public String getUspBk()
	{
		return uspBk;
	}

	public void setUspBk(String uspBk)
	{
		this.uspBk = uspBk;
	}

	public Integer getUspCreatedUsrId()
	{
		return uspCreatedUsrId;
	}

	public void setUspCreatedUsrId(Integer uspCreatedUsrId)
	{
		this.uspCreatedUsrId = uspCreatedUsrId;
	}

	public Integer getUspModifiedUsrId()
	{
		return uspModifiedUsrId;
	}

	public void setUspModifiedUsrId(Integer uspModifiedUsrId)
	{
		this.uspModifiedUsrId = uspModifiedUsrId;
	}

	public DateTime getUspCreatedDttm()
	{
		return uspCreatedDttm;
	}

	public void setUspCreatedDttm(DateTime uspCreatedDttm)
	{
		this.uspCreatedDttm = uspCreatedDttm;
	}

	public DateTime getUspModifiedDttm()
	{
		return uspModifiedDttm;
	}

	public void setUspModifiedDttm(DateTime uspModifiedDttm)
	{
		this.uspModifiedDttm = uspModifiedDttm;
	}

}
