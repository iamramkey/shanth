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

public abstract class Role extends SignatureDbo
{
	private Integer rolId;
	private String rolName;
	private String rolBk;
	private Integer rolCreatedUsrId;
	private Integer rolModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime rolCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime rolModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			rolId = getInteger(rs.getInt("rol_id"));
			rolName = rs.getString("rol_name");
			rolBk = rs.getString("rol_bk");
			rolCreatedUsrId = getInteger(rs.getInt("rol_created_usr_id"));
			rolModifiedUsrId = getInteger(rs.getInt("rol_modified_usr_id"));
			rolCreatedDttm = getDateTime(rs.getTimestamp("rol_created_dttm"));
			rolModifiedDttm = getDateTime(rs.getTimestamp("rol_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return rolId;
	}

	public void setPk(int pk)
	{
		rolId = pk;
	}

	public String getBk()
	{
		return rolBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(rolId.toString());
		data.add(rolName.toString());
		data.add(getRolCreatedUsr());
		data.add(Constants.dttf.print(rolCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("rolName", getString(rolName));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setRolName(input.getString("rolName"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(rolBk.toString());
		auditData.add(getRolCreatedUsr());
		auditData.add(rolModifiedUsrId == null ? Constants.NULLSTRING : getRolModifiedUsr());
		auditData.add(Constants.dttf.print(rolCreatedDttm));
		auditData.add(rolModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(rolModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return rolName;
	}

	public String getRolCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, rolCreatedUsrId);
	}

	public String getRolModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, rolModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("rolCreatedUsr"))
			return getRolCreatedUsr();
		if (propertyName.equals("rolModifiedUsr"))
			return getRolModifiedUsr();
		if (propertyName.equals("rolId"))
			return getRolId();
		if (propertyName.equals("rolName"))
			return getRolName();
		if (propertyName.equals("rolBk"))
			return getRolBk();
		if (propertyName.equals("rolCreatedUsrId"))
			return getRolCreatedUsrId();
		if (propertyName.equals("rolModifiedUsrId"))
			return getRolModifiedUsrId();
		if (propertyName.equals("rolCreatedDttm"))
			return getRolCreatedDttm();
		if (propertyName.equals("rolModifiedDttm"))
			return getRolModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getRolId()
	{
		return rolId;
	}

	public void setRolId(Integer rolId)
	{
		this.rolId = rolId;
	}

	public String getRolName()
	{
		return rolName;
	}

	public void setRolName(String rolName)
	{
		this.rolName = rolName;
	}

	public String getRolBk()
	{
		return rolBk;
	}

	public void setRolBk(String rolBk)
	{
		this.rolBk = rolBk;
	}

	public Integer getRolCreatedUsrId()
	{
		return rolCreatedUsrId;
	}

	public void setRolCreatedUsrId(Integer rolCreatedUsrId)
	{
		this.rolCreatedUsrId = rolCreatedUsrId;
	}

	public Integer getRolModifiedUsrId()
	{
		return rolModifiedUsrId;
	}

	public void setRolModifiedUsrId(Integer rolModifiedUsrId)
	{
		this.rolModifiedUsrId = rolModifiedUsrId;
	}

	public DateTime getRolCreatedDttm()
	{
		return rolCreatedDttm;
	}

	public void setRolCreatedDttm(DateTime rolCreatedDttm)
	{
		this.rolCreatedDttm = rolCreatedDttm;
	}

	public DateTime getRolModifiedDttm()
	{
		return rolModifiedDttm;
	}

	public void setRolModifiedDttm(DateTime rolModifiedDttm)
	{
		this.rolModifiedDttm = rolModifiedDttm;
	}

}
