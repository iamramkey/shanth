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

public abstract class Node extends SignatureDbo
{
	private Integer nodId;
	private String nodName;
	private String nodDatadir;
	private String nodStatus;
	private String nodExtra1;
	private String nodExtra2;
	private boolean nodCaches;
	private String nodBk;
	private Integer nodCreatedUsrId;
	private Integer nodModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime nodCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime nodModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			nodId = getInteger(rs.getInt("nod_id"));
			nodName = rs.getString("nod_name");
			nodDatadir = rs.getString("nod_datadir");
			nodStatus = rs.getString("nod_status");
			nodExtra1 = rs.getString("nod_extra1");
			nodExtra2 = rs.getString("nod_extra2");
			nodCaches = getBoolean(rs.getString("nod_caches"));
			nodBk = rs.getString("nod_bk");
			nodCreatedUsrId = getInteger(rs.getInt("nod_created_usr_id"));
			nodModifiedUsrId = getInteger(rs.getInt("nod_modified_usr_id"));
			nodCreatedDttm = getDateTime(rs.getTimestamp("nod_created_dttm"));
			nodModifiedDttm = getDateTime(rs.getTimestamp("nod_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return nodId;
	}

	public void setPk(int pk)
	{
		nodId = pk;
	}

	public String getBk()
	{
		return nodBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(nodId.toString());
		data.add(nodName.toString());
		data.add(nodDatadir.toString());
		data.add(nodStatus.toString());
		data.add(nodExtra1 == null ? Constants.NULLSTRING : nodExtra1.toString());
		data.add(nodExtra2 == null ? Constants.NULLSTRING : nodExtra2.toString());
		data.add(String.valueOf(nodCaches));
		data.add(getNodCreatedUsr());
		data.add(Constants.dttf.print(nodCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("nodName", getString(nodName));
		data.put("nodDatadir", getString(nodDatadir));
		data.put("nodStatus", getString(nodStatus));
		data.put("nodExtra1", getString(nodExtra1));
		data.put("nodExtra2", getString(nodExtra2));
		data.put("nodCaches", getString(nodCaches));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setNodName(input.getString("nodName"));
		setNodDatadir(input.getString("nodDatadir"));
		setNodStatus(input.getString("nodStatus"));
		setNodExtra1(input.getString("nodExtra1"));
		setNodExtra2(input.getString("nodExtra2"));
		setNodCaches(input.getBoolean("nodCaches"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(nodBk.toString());
		auditData.add(getNodCreatedUsr());
		auditData.add(nodModifiedUsrId == null ? Constants.NULLSTRING : getNodModifiedUsr());
		auditData.add(Constants.dttf.print(nodCreatedDttm));
		auditData.add(nodModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(nodModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return nodName;
	}

	public String getNodCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, nodCreatedUsrId);
	}

	public String getNodModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, nodModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("nodCreatedUsr"))
			return getNodCreatedUsr();
		if (propertyName.equals("nodModifiedUsr"))
			return getNodModifiedUsr();
		if (propertyName.equals("nodId"))
			return getNodId();
		if (propertyName.equals("nodName"))
			return getNodName();
		if (propertyName.equals("nodDatadir"))
			return getNodDatadir();
		if (propertyName.equals("nodStatus"))
			return getNodStatus();
		if (propertyName.equals("nodExtra1"))
			return getNodExtra1();
		if (propertyName.equals("nodExtra2"))
			return getNodExtra2();
		if (propertyName.equals("nodCaches"))
			return getNodCaches();
		if (propertyName.equals("nodBk"))
			return getNodBk();
		if (propertyName.equals("nodCreatedUsrId"))
			return getNodCreatedUsrId();
		if (propertyName.equals("nodModifiedUsrId"))
			return getNodModifiedUsrId();
		if (propertyName.equals("nodCreatedDttm"))
			return getNodCreatedDttm();
		if (propertyName.equals("nodModifiedDttm"))
			return getNodModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getNodId()
	{
		return nodId;
	}

	public void setNodId(Integer nodId)
	{
		this.nodId = nodId;
	}

	public String getNodName()
	{
		return nodName;
	}

	public void setNodName(String nodName)
	{
		this.nodName = nodName;
	}

	public String getNodDatadir()
	{
		return nodDatadir;
	}

	public void setNodDatadir(String nodDatadir)
	{
		this.nodDatadir = nodDatadir;
	}

	public String getNodStatus()
	{
		return nodStatus;
	}

	public void setNodStatus(String nodStatus)
	{
		this.nodStatus = nodStatus;
	}

	public String getNodExtra1()
	{
		return nodExtra1;
	}

	public void setNodExtra1(String nodExtra1)
	{
		this.nodExtra1 = nodExtra1;
	}

	public String getNodExtra2()
	{
		return nodExtra2;
	}

	public void setNodExtra2(String nodExtra2)
	{
		this.nodExtra2 = nodExtra2;
	}

	public boolean getNodCaches()
	{
		return nodCaches;
	}

	public void setNodCaches(boolean nodCaches)
	{
		this.nodCaches = nodCaches;
	}

	public String getNodBk()
	{
		return nodBk;
	}

	public void setNodBk(String nodBk)
	{
		this.nodBk = nodBk;
	}

	public Integer getNodCreatedUsrId()
	{
		return nodCreatedUsrId;
	}

	public void setNodCreatedUsrId(Integer nodCreatedUsrId)
	{
		this.nodCreatedUsrId = nodCreatedUsrId;
	}

	public Integer getNodModifiedUsrId()
	{
		return nodModifiedUsrId;
	}

	public void setNodModifiedUsrId(Integer nodModifiedUsrId)
	{
		this.nodModifiedUsrId = nodModifiedUsrId;
	}

	public DateTime getNodCreatedDttm()
	{
		return nodCreatedDttm;
	}

	public void setNodCreatedDttm(DateTime nodCreatedDttm)
	{
		this.nodCreatedDttm = nodCreatedDttm;
	}

	public DateTime getNodModifiedDttm()
	{
		return nodModifiedDttm;
	}

	public void setNodModifiedDttm(DateTime nodModifiedDttm)
	{
		this.nodModifiedDttm = nodModifiedDttm;
	}

}
