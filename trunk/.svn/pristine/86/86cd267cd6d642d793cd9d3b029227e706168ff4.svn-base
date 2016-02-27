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

public abstract class MasterSearch extends SignatureDbo
{
	private Integer mseId;
	private String mseUrl;
	private String mseBk;
	private Integer mseCreatedUsrId;
	private Integer mseModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime mseCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime mseModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			mseId = getInteger(rs.getInt("mse_id"));
			mseUrl = rs.getString("mse_url");
			mseBk = rs.getString("mse_bk");
			mseCreatedUsrId = getInteger(rs.getInt("mse_created_usr_id"));
			mseModifiedUsrId = getInteger(rs.getInt("mse_modified_usr_id"));
			mseCreatedDttm = getDateTime(rs.getTimestamp("mse_created_dttm"));
			mseModifiedDttm = getDateTime(rs.getTimestamp("mse_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return mseId;
	}

	public void setPk(int pk)
	{
		mseId = pk;
	}

	public String getBk()
	{
		return mseBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(mseId.toString());
		data.add(mseUrl.toString());
		data.add(getMseCreatedUsr());
		data.add(Constants.dttf.print(mseCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("mseUrl", getString(mseUrl));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setMseUrl(input.getString("mseUrl"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(mseBk.toString());
		auditData.add(getMseCreatedUsr());
		auditData.add(mseModifiedUsrId == null ? Constants.NULLSTRING : getMseModifiedUsr());
		auditData.add(Constants.dttf.print(mseCreatedDttm));
		auditData.add(mseModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(mseModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return mseUrl;
	}

	public String getMseCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, mseCreatedUsrId);
	}

	public String getMseModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, mseModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("mseCreatedUsr"))
			return getMseCreatedUsr();
		if (propertyName.equals("mseModifiedUsr"))
			return getMseModifiedUsr();
		if (propertyName.equals("mseId"))
			return getMseId();
		if (propertyName.equals("mseUrl"))
			return getMseUrl();
		if (propertyName.equals("mseBk"))
			return getMseBk();
		if (propertyName.equals("mseCreatedUsrId"))
			return getMseCreatedUsrId();
		if (propertyName.equals("mseModifiedUsrId"))
			return getMseModifiedUsrId();
		if (propertyName.equals("mseCreatedDttm"))
			return getMseCreatedDttm();
		if (propertyName.equals("mseModifiedDttm"))
			return getMseModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getMseId()
	{
		return mseId;
	}

	public void setMseId(Integer mseId)
	{
		this.mseId = mseId;
	}

	public String getMseUrl()
	{
		return mseUrl;
	}

	public void setMseUrl(String mseUrl)
	{
		this.mseUrl = mseUrl;
	}

	public String getMseBk()
	{
		return mseBk;
	}

	public void setMseBk(String mseBk)
	{
		this.mseBk = mseBk;
	}

	public Integer getMseCreatedUsrId()
	{
		return mseCreatedUsrId;
	}

	public void setMseCreatedUsrId(Integer mseCreatedUsrId)
	{
		this.mseCreatedUsrId = mseCreatedUsrId;
	}

	public Integer getMseModifiedUsrId()
	{
		return mseModifiedUsrId;
	}

	public void setMseModifiedUsrId(Integer mseModifiedUsrId)
	{
		this.mseModifiedUsrId = mseModifiedUsrId;
	}

	public DateTime getMseCreatedDttm()
	{
		return mseCreatedDttm;
	}

	public void setMseCreatedDttm(DateTime mseCreatedDttm)
	{
		this.mseCreatedDttm = mseCreatedDttm;
	}

	public DateTime getMseModifiedDttm()
	{
		return mseModifiedDttm;
	}

	public void setMseModifiedDttm(DateTime mseModifiedDttm)
	{
		this.mseModifiedDttm = mseModifiedDttm;
	}

}
