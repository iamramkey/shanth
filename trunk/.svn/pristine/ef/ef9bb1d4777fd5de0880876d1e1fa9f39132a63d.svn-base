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

public abstract class LastPolled extends SignatureDbo
{
	private Integer lpoId;
	private Integer jbtId;
	private String lpoName;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime lpoLastPolledDttm;
	private String lpoBk;
	private Integer lpoCreatedUsrId;
	private Integer lpoModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime lpoCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime lpoModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			lpoId = getInteger(rs.getInt("lpo_id"));
			jbtId = getInteger(rs.getInt("jbt_id"));
			lpoName = rs.getString("lpo_name");
			lpoLastPolledDttm = getDateTime(rs.getTimestamp("lpo_last_polled_dttm"));
			lpoBk = rs.getString("lpo_bk");
			lpoCreatedUsrId = getInteger(rs.getInt("lpo_created_usr_id"));
			lpoModifiedUsrId = getInteger(rs.getInt("lpo_modified_usr_id"));
			lpoCreatedDttm = getDateTime(rs.getTimestamp("lpo_created_dttm"));
			lpoModifiedDttm = getDateTime(rs.getTimestamp("lpo_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return lpoId;
	}

	public void setPk(int pk)
	{
		lpoId = pk;
	}

	public String getBk()
	{
		return lpoBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(lpoId.toString());
		data.add(jbtId.toString());
		data.add(lpoName.toString());
		data.add(lpoLastPolledDttm == null ? Constants.NULLSTRING : Constants.dttf.print(lpoLastPolledDttm));
		data.add(getLpoCreatedUsr());
		data.add(Constants.dttf.print(lpoCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("jbt", getJbt());
		data.put("lpoName", getString(lpoName));
		data.put("lpoLastPolledDttm", Constants.dttf.print(lpoLastPolledDttm));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("jbt")));
		setLpoName(input.getString("lpoName"));
		setLpoLastPolledDttm(input.getDate("lpoLastPolledDttm"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(lpoBk.toString());
		auditData.add(getLpoCreatedUsr());
		auditData.add(lpoModifiedUsrId == null ? Constants.NULLSTRING : getLpoModifiedUsr());
		auditData.add(Constants.dttf.print(lpoCreatedDttm));
		auditData.add(lpoModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(lpoModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return lpoName;
	}

	public String getJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, jbtId);
	}

	public String getLpoCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, lpoCreatedUsrId);
	}

	public String getLpoModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, lpoModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("jbt"))
			return getJbt();
		if (propertyName.equals("lpoCreatedUsr"))
			return getLpoCreatedUsr();
		if (propertyName.equals("lpoModifiedUsr"))
			return getLpoModifiedUsr();
		if (propertyName.equals("lpoId"))
			return getLpoId();
		if (propertyName.equals("jbtId"))
			return getJbtId();
		if (propertyName.equals("lpoName"))
			return getLpoName();
		if (propertyName.equals("lpoLastPolledDttm"))
			return getLpoLastPolledDttm();
		if (propertyName.equals("lpoBk"))
			return getLpoBk();
		if (propertyName.equals("lpoCreatedUsrId"))
			return getLpoCreatedUsrId();
		if (propertyName.equals("lpoModifiedUsrId"))
			return getLpoModifiedUsrId();
		if (propertyName.equals("lpoCreatedDttm"))
			return getLpoCreatedDttm();
		if (propertyName.equals("lpoModifiedDttm"))
			return getLpoModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getLpoId()
	{
		return lpoId;
	}

	public void setLpoId(Integer lpoId)
	{
		this.lpoId = lpoId;
	}

	public Integer getJbtId()
	{
		return jbtId;
	}

	public void setJbtId(Integer jbtId)
	{
		this.jbtId = jbtId;
	}

	public String getLpoName()
	{
		return lpoName;
	}

	public void setLpoName(String lpoName)
	{
		this.lpoName = lpoName;
	}

	public DateTime getLpoLastPolledDttm()
	{
		return lpoLastPolledDttm;
	}

	public void setLpoLastPolledDttm(DateTime lpoLastPolledDttm)
	{
		this.lpoLastPolledDttm = lpoLastPolledDttm;
	}

	public String getLpoBk()
	{
		return lpoBk;
	}

	public void setLpoBk(String lpoBk)
	{
		this.lpoBk = lpoBk;
	}

	public Integer getLpoCreatedUsrId()
	{
		return lpoCreatedUsrId;
	}

	public void setLpoCreatedUsrId(Integer lpoCreatedUsrId)
	{
		this.lpoCreatedUsrId = lpoCreatedUsrId;
	}

	public Integer getLpoModifiedUsrId()
	{
		return lpoModifiedUsrId;
	}

	public void setLpoModifiedUsrId(Integer lpoModifiedUsrId)
	{
		this.lpoModifiedUsrId = lpoModifiedUsrId;
	}

	public DateTime getLpoCreatedDttm()
	{
		return lpoCreatedDttm;
	}

	public void setLpoCreatedDttm(DateTime lpoCreatedDttm)
	{
		this.lpoCreatedDttm = lpoCreatedDttm;
	}

	public DateTime getLpoModifiedDttm()
	{
		return lpoModifiedDttm;
	}

	public void setLpoModifiedDttm(DateTime lpoModifiedDttm)
	{
		this.lpoModifiedDttm = lpoModifiedDttm;
	}

}
