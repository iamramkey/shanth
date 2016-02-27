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

public abstract class PendingMissingJobSettings extends SignatureDbo
{
	private Integer pemId;
	private String pemName;
	private Integer pemFrequencySecs;
	private Integer pemPendingMissingPJbtId;
	private Integer pemPendingMissingJbtId;
	private String pemBk;
	private Integer pemCreatedUsrId;
	private Integer pemModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime pemCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime pemModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			pemId = getInteger(rs.getInt("pem_id"));
			pemName = rs.getString("pem_name");
			pemFrequencySecs = rs.getInt("pem_frequency_secs");
			pemPendingMissingPJbtId = getInteger(rs.getInt("pem_pending_missing_p_jbt_id"));
			pemPendingMissingJbtId = getInteger(rs.getInt("pem_pending_missing_jbt_id"));
			pemBk = rs.getString("pem_bk");
			pemCreatedUsrId = getInteger(rs.getInt("pem_created_usr_id"));
			pemModifiedUsrId = getInteger(rs.getInt("pem_modified_usr_id"));
			pemCreatedDttm = getDateTime(rs.getTimestamp("pem_created_dttm"));
			pemModifiedDttm = getDateTime(rs.getTimestamp("pem_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return pemId;
	}

	public void setPk(int pk)
	{
		pemId = pk;
	}

	public String getBk()
	{
		return pemBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(pemId.toString());
		data.add(pemName.toString());
		data.add(pemFrequencySecs.toString());
		data.add(pemPendingMissingPJbtId.toString());
		data.add(pemPendingMissingJbtId.toString());
		data.add(getPemCreatedUsr());
		data.add(Constants.dttf.print(pemCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("pemName", getString(pemName));
		data.put("pemFrequencySecs", getString(pemFrequencySecs));
		data.put("pemPendingMissingPJbt", getPemPendingMissingPJbt());
		data.put("pemPendingMissingJbt", getPemPendingMissingJbt());
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setPemName(input.getString("pemName"));
		setPemFrequencySecs(input.getInteger("pemFrequencySecs"));
		setPemPendingMissingPJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("pemPendingMissingPJbt")));
		setPemPendingMissingJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("pemPendingMissingJbt")));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(pemBk.toString());
		auditData.add(getPemCreatedUsr());
		auditData.add(pemModifiedUsrId == null ? Constants.NULLSTRING : getPemModifiedUsr());
		auditData.add(Constants.dttf.print(pemCreatedDttm));
		auditData.add(pemModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(pemModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return pemName;
	}

	public String getPemPendingMissingPJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, pemPendingMissingPJbtId);
	}

	public String getPemPendingMissingJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, pemPendingMissingJbtId);
	}

	public String getPemCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, pemCreatedUsrId);
	}

	public String getPemModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, pemModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("pemPendingMissingPJbt"))
			return getPemPendingMissingPJbt();
		if (propertyName.equals("pemPendingMissingJbt"))
			return getPemPendingMissingJbt();
		if (propertyName.equals("pemCreatedUsr"))
			return getPemCreatedUsr();
		if (propertyName.equals("pemModifiedUsr"))
			return getPemModifiedUsr();
		if (propertyName.equals("pemId"))
			return getPemId();
		if (propertyName.equals("pemName"))
			return getPemName();
		if (propertyName.equals("pemFrequencySecs"))
			return getPemFrequencySecs();
		if (propertyName.equals("pemPendingMissingPJbtId"))
			return getPemPendingMissingPJbtId();
		if (propertyName.equals("pemPendingMissingJbtId"))
			return getPemPendingMissingJbtId();
		if (propertyName.equals("pemBk"))
			return getPemBk();
		if (propertyName.equals("pemCreatedUsrId"))
			return getPemCreatedUsrId();
		if (propertyName.equals("pemModifiedUsrId"))
			return getPemModifiedUsrId();
		if (propertyName.equals("pemCreatedDttm"))
			return getPemCreatedDttm();
		if (propertyName.equals("pemModifiedDttm"))
			return getPemModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getPemId()
	{
		return pemId;
	}

	public void setPemId(Integer pemId)
	{
		this.pemId = pemId;
	}

	public String getPemName()
	{
		return pemName;
	}

	public void setPemName(String pemName)
	{
		this.pemName = pemName;
	}

	public Integer getPemFrequencySecs()
	{
		return pemFrequencySecs;
	}

	public void setPemFrequencySecs(Integer pemFrequencySecs)
	{
		this.pemFrequencySecs = pemFrequencySecs;
	}

	public Integer getPemPendingMissingPJbtId()
	{
		return pemPendingMissingPJbtId;
	}

	public void setPemPendingMissingPJbtId(Integer pemPendingMissingPJbtId)
	{
		this.pemPendingMissingPJbtId = pemPendingMissingPJbtId;
	}

	public Integer getPemPendingMissingJbtId()
	{
		return pemPendingMissingJbtId;
	}

	public void setPemPendingMissingJbtId(Integer pemPendingMissingJbtId)
	{
		this.pemPendingMissingJbtId = pemPendingMissingJbtId;
	}

	public String getPemBk()
	{
		return pemBk;
	}

	public void setPemBk(String pemBk)
	{
		this.pemBk = pemBk;
	}

	public Integer getPemCreatedUsrId()
	{
		return pemCreatedUsrId;
	}

	public void setPemCreatedUsrId(Integer pemCreatedUsrId)
	{
		this.pemCreatedUsrId = pemCreatedUsrId;
	}

	public Integer getPemModifiedUsrId()
	{
		return pemModifiedUsrId;
	}

	public void setPemModifiedUsrId(Integer pemModifiedUsrId)
	{
		this.pemModifiedUsrId = pemModifiedUsrId;
	}

	public DateTime getPemCreatedDttm()
	{
		return pemCreatedDttm;
	}

	public void setPemCreatedDttm(DateTime pemCreatedDttm)
	{
		this.pemCreatedDttm = pemCreatedDttm;
	}

	public DateTime getPemModifiedDttm()
	{
		return pemModifiedDttm;
	}

	public void setPemModifiedDttm(DateTime pemModifiedDttm)
	{
		this.pemModifiedDttm = pemModifiedDttm;
	}

}
