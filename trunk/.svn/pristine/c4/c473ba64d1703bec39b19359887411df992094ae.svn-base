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
import se.signa.signature.gen.dba.AlertTypeDba;
import se.signa.signature.gen.dba.JobTypeDba;
import se.signa.signature.gen.dba.UserTblDba;
import se.signa.signature.helpers.DisplayFieldHelper;

public abstract class HealthCheck extends SignatureDbo
{
	private Integer hchId;
	private String hchName;
	private String hchPackage;
	private Integer hchRepeatIntervalHrs;
	private Integer jbtId;
	private Integer atpId;
	private String hchExtra1;
	private String hchExtra2;
	private String hchExtra3;
	private String hchBk;
	private Integer hchCreatedUsrId;
	private Integer hchModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime hchCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime hchModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			hchId = getInteger(rs.getInt("hch_id"));
			hchName = rs.getString("hch_name");
			hchPackage = rs.getString("hch_package");
			hchRepeatIntervalHrs = rs.getInt("hch_repeat_interval_hrs");
			jbtId = getInteger(rs.getInt("jbt_id"));
			atpId = getInteger(rs.getInt("atp_id"));
			hchExtra1 = rs.getString("hch_extra1");
			hchExtra2 = rs.getString("hch_extra2");
			hchExtra3 = rs.getString("hch_extra3");
			hchBk = rs.getString("hch_bk");
			hchCreatedUsrId = getInteger(rs.getInt("hch_created_usr_id"));
			hchModifiedUsrId = getInteger(rs.getInt("hch_modified_usr_id"));
			hchCreatedDttm = getDateTime(rs.getTimestamp("hch_created_dttm"));
			hchModifiedDttm = getDateTime(rs.getTimestamp("hch_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return hchId;
	}

	public void setPk(int pk)
	{
		hchId = pk;
	}

	public String getBk()
	{
		return hchBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(hchId.toString());
		data.add(hchName.toString());
		data.add(hchPackage.toString());
		data.add(hchRepeatIntervalHrs == null ? Constants.NULLSTRING : hchRepeatIntervalHrs.toString());
		data.add(jbtId.toString());
		data.add(atpId.toString());
		data.add(hchExtra1 == null ? Constants.NULLSTRING : hchExtra1.toString());
		data.add(hchExtra2 == null ? Constants.NULLSTRING : hchExtra2.toString());
		data.add(hchExtra3 == null ? Constants.NULLSTRING : hchExtra3.toString());
		data.add(getHchCreatedUsr());
		data.add(Constants.dttf.print(hchCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("hchName", getString(hchName));
		data.put("hchPackage", getString(hchPackage));
		data.put("hchRepeatIntervalHrs", getString(hchRepeatIntervalHrs));
		data.put("jbt", getJbt());
		data.put("atp", getAtp());
		data.put("hchExtra1", getString(hchExtra1));
		data.put("hchExtra2", getString(hchExtra2));
		data.put("hchExtra3", getString(hchExtra3));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setHchName(input.getString("hchName"));
		setHchPackage(input.getString("hchPackage"));
		setHchRepeatIntervalHrs(input.getInteger("hchRepeatIntervalHrs"));
		setJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("jbt")));
		setAtpId(DisplayFieldHelper.getI().getPk(AlertTypeDba.class, input.getString("atp")));
		setHchExtra1(input.getString("hchExtra1"));
		setHchExtra2(input.getString("hchExtra2"));
		setHchExtra3(input.getString("hchExtra3"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(hchBk.toString());
		auditData.add(getHchCreatedUsr());
		auditData.add(hchModifiedUsrId == null ? Constants.NULLSTRING : getHchModifiedUsr());
		auditData.add(Constants.dttf.print(hchCreatedDttm));
		auditData.add(hchModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(hchModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return hchName;
	}

	public String getJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, jbtId);
	}

	public String getAtp()
	{
		return DisplayFieldHelper.getI().getDisplayField(AlertTypeDba.class, atpId);
	}

	public String getHchCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, hchCreatedUsrId);
	}

	public String getHchModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, hchModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("jbt"))
			return getJbt();
		if (propertyName.equals("atp"))
			return getAtp();
		if (propertyName.equals("hchCreatedUsr"))
			return getHchCreatedUsr();
		if (propertyName.equals("hchModifiedUsr"))
			return getHchModifiedUsr();
		if (propertyName.equals("hchId"))
			return getHchId();
		if (propertyName.equals("hchName"))
			return getHchName();
		if (propertyName.equals("hchPackage"))
			return getHchPackage();
		if (propertyName.equals("hchRepeatIntervalHrs"))
			return getHchRepeatIntervalHrs();
		if (propertyName.equals("jbtId"))
			return getJbtId();
		if (propertyName.equals("atpId"))
			return getAtpId();
		if (propertyName.equals("hchExtra1"))
			return getHchExtra1();
		if (propertyName.equals("hchExtra2"))
			return getHchExtra2();
		if (propertyName.equals("hchExtra3"))
			return getHchExtra3();
		if (propertyName.equals("hchBk"))
			return getHchBk();
		if (propertyName.equals("hchCreatedUsrId"))
			return getHchCreatedUsrId();
		if (propertyName.equals("hchModifiedUsrId"))
			return getHchModifiedUsrId();
		if (propertyName.equals("hchCreatedDttm"))
			return getHchCreatedDttm();
		if (propertyName.equals("hchModifiedDttm"))
			return getHchModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getHchId()
	{
		return hchId;
	}

	public void setHchId(Integer hchId)
	{
		this.hchId = hchId;
	}

	public String getHchName()
	{
		return hchName;
	}

	public void setHchName(String hchName)
	{
		this.hchName = hchName;
	}

	public String getHchPackage()
	{
		return hchPackage;
	}

	public void setHchPackage(String hchPackage)
	{
		this.hchPackage = hchPackage;
	}

	public Integer getHchRepeatIntervalHrs()
	{
		return hchRepeatIntervalHrs;
	}

	public void setHchRepeatIntervalHrs(Integer hchRepeatIntervalHrs)
	{
		this.hchRepeatIntervalHrs = hchRepeatIntervalHrs;
	}

	public Integer getJbtId()
	{
		return jbtId;
	}

	public void setJbtId(Integer jbtId)
	{
		this.jbtId = jbtId;
	}

	public Integer getAtpId()
	{
		return atpId;
	}

	public void setAtpId(Integer atpId)
	{
		this.atpId = atpId;
	}

	public String getHchExtra1()
	{
		return hchExtra1;
	}

	public void setHchExtra1(String hchExtra1)
	{
		this.hchExtra1 = hchExtra1;
	}

	public String getHchExtra2()
	{
		return hchExtra2;
	}

	public void setHchExtra2(String hchExtra2)
	{
		this.hchExtra2 = hchExtra2;
	}

	public String getHchExtra3()
	{
		return hchExtra3;
	}

	public void setHchExtra3(String hchExtra3)
	{
		this.hchExtra3 = hchExtra3;
	}

	public String getHchBk()
	{
		return hchBk;
	}

	public void setHchBk(String hchBk)
	{
		this.hchBk = hchBk;
	}

	public Integer getHchCreatedUsrId()
	{
		return hchCreatedUsrId;
	}

	public void setHchCreatedUsrId(Integer hchCreatedUsrId)
	{
		this.hchCreatedUsrId = hchCreatedUsrId;
	}

	public Integer getHchModifiedUsrId()
	{
		return hchModifiedUsrId;
	}

	public void setHchModifiedUsrId(Integer hchModifiedUsrId)
	{
		this.hchModifiedUsrId = hchModifiedUsrId;
	}

	public DateTime getHchCreatedDttm()
	{
		return hchCreatedDttm;
	}

	public void setHchCreatedDttm(DateTime hchCreatedDttm)
	{
		this.hchCreatedDttm = hchCreatedDttm;
	}

	public DateTime getHchModifiedDttm()
	{
		return hchModifiedDttm;
	}

	public void setHchModifiedDttm(DateTime hchModifiedDttm)
	{
		this.hchModifiedDttm = hchModifiedDttm;
	}

}
