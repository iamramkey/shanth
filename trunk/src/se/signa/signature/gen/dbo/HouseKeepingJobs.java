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

public abstract class HouseKeepingJobs extends SignatureDbo
{
	private Integer hkjId;
	private String hkjName;
	private Integer jbtId;
	private String hkjBk;
	private Integer hkjCreatedUsrId;
	private Integer hkjModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime hkjCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime hkjModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			hkjId = getInteger(rs.getInt("hkj_id"));
			hkjName = rs.getString("hkj_name");
			jbtId = getInteger(rs.getInt("jbt_id"));
			hkjBk = rs.getString("hkj_bk");
			hkjCreatedUsrId = getInteger(rs.getInt("hkj_created_usr_id"));
			hkjModifiedUsrId = getInteger(rs.getInt("hkj_modified_usr_id"));
			hkjCreatedDttm = getDateTime(rs.getTimestamp("hkj_created_dttm"));
			hkjModifiedDttm = getDateTime(rs.getTimestamp("hkj_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return hkjId;
	}

	public void setPk(int pk)
	{
		hkjId = pk;
	}

	public String getBk()
	{
		return hkjBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(hkjId.toString());
		data.add(hkjName.toString());
		data.add(jbtId.toString());
		data.add(getHkjCreatedUsr());
		data.add(Constants.dttf.print(hkjCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("hkjName", getString(hkjName));
		data.put("jbt", getJbt());
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setHkjName(input.getString("hkjName"));
		setJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("jbt")));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(hkjBk.toString());
		auditData.add(getHkjCreatedUsr());
		auditData.add(hkjModifiedUsrId == null ? Constants.NULLSTRING : getHkjModifiedUsr());
		auditData.add(Constants.dttf.print(hkjCreatedDttm));
		auditData.add(hkjModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(hkjModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return hkjName;
	}

	public String getJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, jbtId);
	}

	public String getHkjCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, hkjCreatedUsrId);
	}

	public String getHkjModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, hkjModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("jbt"))
			return getJbt();
		if (propertyName.equals("hkjCreatedUsr"))
			return getHkjCreatedUsr();
		if (propertyName.equals("hkjModifiedUsr"))
			return getHkjModifiedUsr();
		if (propertyName.equals("hkjId"))
			return getHkjId();
		if (propertyName.equals("hkjName"))
			return getHkjName();
		if (propertyName.equals("jbtId"))
			return getJbtId();
		if (propertyName.equals("hkjBk"))
			return getHkjBk();
		if (propertyName.equals("hkjCreatedUsrId"))
			return getHkjCreatedUsrId();
		if (propertyName.equals("hkjModifiedUsrId"))
			return getHkjModifiedUsrId();
		if (propertyName.equals("hkjCreatedDttm"))
			return getHkjCreatedDttm();
		if (propertyName.equals("hkjModifiedDttm"))
			return getHkjModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getHkjId()
	{
		return hkjId;
	}

	public void setHkjId(Integer hkjId)
	{
		this.hkjId = hkjId;
	}

	public String getHkjName()
	{
		return hkjName;
	}

	public void setHkjName(String hkjName)
	{
		this.hkjName = hkjName;
	}

	public Integer getJbtId()
	{
		return jbtId;
	}

	public void setJbtId(Integer jbtId)
	{
		this.jbtId = jbtId;
	}

	public String getHkjBk()
	{
		return hkjBk;
	}

	public void setHkjBk(String hkjBk)
	{
		this.hkjBk = hkjBk;
	}

	public Integer getHkjCreatedUsrId()
	{
		return hkjCreatedUsrId;
	}

	public void setHkjCreatedUsrId(Integer hkjCreatedUsrId)
	{
		this.hkjCreatedUsrId = hkjCreatedUsrId;
	}

	public Integer getHkjModifiedUsrId()
	{
		return hkjModifiedUsrId;
	}

	public void setHkjModifiedUsrId(Integer hkjModifiedUsrId)
	{
		this.hkjModifiedUsrId = hkjModifiedUsrId;
	}

	public DateTime getHkjCreatedDttm()
	{
		return hkjCreatedDttm;
	}

	public void setHkjCreatedDttm(DateTime hkjCreatedDttm)
	{
		this.hkjCreatedDttm = hkjCreatedDttm;
	}

	public DateTime getHkjModifiedDttm()
	{
		return hkjModifiedDttm;
	}

	public void setHkjModifiedDttm(DateTime hkjModifiedDttm)
	{
		this.hkjModifiedDttm = hkjModifiedDttm;
	}

}
