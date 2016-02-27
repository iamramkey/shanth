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
import se.signa.signature.gen.dba.MasterSearchDba;
import se.signa.signature.gen.dba.UserTblDba;
import se.signa.signature.helpers.DisplayFieldHelper;

public abstract class MasterSearchForm extends SignatureDbo
{
	private Integer msfId;
	private Integer mseId;
	private Integer msfOrderNo;
	private String msfIdentifier;
	private String msfName;
	private boolean msfMandatory;
	private String msfType;
	private String msfCode;
	private String msfBk;
	private Integer msfCreatedUsrId;
	private Integer msfModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime msfCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime msfModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			msfId = getInteger(rs.getInt("msf_id"));
			mseId = getInteger(rs.getInt("mse_id"));
			msfOrderNo = rs.getInt("msf_order_no");
			msfIdentifier = rs.getString("msf_identifier");
			msfName = rs.getString("msf_name");
			msfMandatory = getBoolean(rs.getString("msf_mandatory"));
			msfType = rs.getString("msf_type");
			msfCode = rs.getString("msf_code");
			msfBk = rs.getString("msf_bk");
			msfCreatedUsrId = getInteger(rs.getInt("msf_created_usr_id"));
			msfModifiedUsrId = getInteger(rs.getInt("msf_modified_usr_id"));
			msfCreatedDttm = getDateTime(rs.getTimestamp("msf_created_dttm"));
			msfModifiedDttm = getDateTime(rs.getTimestamp("msf_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return msfId;
	}

	public void setPk(int pk)
	{
		msfId = pk;
	}

	public String getBk()
	{
		return msfBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(msfId.toString());
		data.add(mseId.toString());
		data.add(msfOrderNo.toString());
		data.add(msfIdentifier.toString());
		data.add(msfName.toString());
		data.add(String.valueOf(msfMandatory));
		data.add(msfType.toString());
		data.add(msfCode.toString());
		data.add(getMsfCreatedUsr());
		data.add(Constants.dttf.print(msfCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("mse", getMse());
		data.put("msfOrderNo", getString(msfOrderNo));
		data.put("msfIdentifier", getString(msfIdentifier));
		data.put("msfName", getString(msfName));
		data.put("msfMandatory", getString(msfMandatory));
		data.put("msfType", getString(msfType));
		data.put("msfCode", getString(msfCode));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setMseId(DisplayFieldHelper.getI().getPk(MasterSearchDba.class, input.getString("mse")));
		setMsfOrderNo(input.getInteger("msfOrderNo"));
		setMsfIdentifier(input.getString("msfIdentifier"));
		setMsfName(input.getString("msfName"));
		setMsfMandatory(input.getBoolean("msfMandatory"));
		setMsfType(input.getString("msfType"));
		setMsfCode(input.getString("msfCode"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(msfBk.toString());
		auditData.add(getMsfCreatedUsr());
		auditData.add(msfModifiedUsrId == null ? Constants.NULLSTRING : getMsfModifiedUsr());
		auditData.add(Constants.dttf.print(msfCreatedDttm));
		auditData.add(msfModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(msfModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return msfCode;
	}

	public String getMse()
	{
		return DisplayFieldHelper.getI().getDisplayField(MasterSearchDba.class, mseId);
	}

	public String getMsfCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, msfCreatedUsrId);
	}

	public String getMsfModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, msfModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("mse"))
			return getMse();
		if (propertyName.equals("msfCreatedUsr"))
			return getMsfCreatedUsr();
		if (propertyName.equals("msfModifiedUsr"))
			return getMsfModifiedUsr();
		if (propertyName.equals("msfId"))
			return getMsfId();
		if (propertyName.equals("mseId"))
			return getMseId();
		if (propertyName.equals("msfOrderNo"))
			return getMsfOrderNo();
		if (propertyName.equals("msfIdentifier"))
			return getMsfIdentifier();
		if (propertyName.equals("msfName"))
			return getMsfName();
		if (propertyName.equals("msfMandatory"))
			return getMsfMandatory();
		if (propertyName.equals("msfType"))
			return getMsfType();
		if (propertyName.equals("msfCode"))
			return getMsfCode();
		if (propertyName.equals("msfBk"))
			return getMsfBk();
		if (propertyName.equals("msfCreatedUsrId"))
			return getMsfCreatedUsrId();
		if (propertyName.equals("msfModifiedUsrId"))
			return getMsfModifiedUsrId();
		if (propertyName.equals("msfCreatedDttm"))
			return getMsfCreatedDttm();
		if (propertyName.equals("msfModifiedDttm"))
			return getMsfModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getMsfId()
	{
		return msfId;
	}

	public void setMsfId(Integer msfId)
	{
		this.msfId = msfId;
	}

	public Integer getMseId()
	{
		return mseId;
	}

	public void setMseId(Integer mseId)
	{
		this.mseId = mseId;
	}

	public Integer getMsfOrderNo()
	{
		return msfOrderNo;
	}

	public void setMsfOrderNo(Integer msfOrderNo)
	{
		this.msfOrderNo = msfOrderNo;
	}

	public String getMsfIdentifier()
	{
		return msfIdentifier;
	}

	public void setMsfIdentifier(String msfIdentifier)
	{
		this.msfIdentifier = msfIdentifier;
	}

	public String getMsfName()
	{
		return msfName;
	}

	public void setMsfName(String msfName)
	{
		this.msfName = msfName;
	}

	public boolean getMsfMandatory()
	{
		return msfMandatory;
	}

	public void setMsfMandatory(boolean msfMandatory)
	{
		this.msfMandatory = msfMandatory;
	}

	public String getMsfType()
	{
		return msfType;
	}

	public void setMsfType(String msfType)
	{
		this.msfType = msfType;
	}

	public String getMsfCode()
	{
		return msfCode;
	}

	public void setMsfCode(String msfCode)
	{
		this.msfCode = msfCode;
	}

	public String getMsfBk()
	{
		return msfBk;
	}

	public void setMsfBk(String msfBk)
	{
		this.msfBk = msfBk;
	}

	public Integer getMsfCreatedUsrId()
	{
		return msfCreatedUsrId;
	}

	public void setMsfCreatedUsrId(Integer msfCreatedUsrId)
	{
		this.msfCreatedUsrId = msfCreatedUsrId;
	}

	public Integer getMsfModifiedUsrId()
	{
		return msfModifiedUsrId;
	}

	public void setMsfModifiedUsrId(Integer msfModifiedUsrId)
	{
		this.msfModifiedUsrId = msfModifiedUsrId;
	}

	public DateTime getMsfCreatedDttm()
	{
		return msfCreatedDttm;
	}

	public void setMsfCreatedDttm(DateTime msfCreatedDttm)
	{
		this.msfCreatedDttm = msfCreatedDttm;
	}

	public DateTime getMsfModifiedDttm()
	{
		return msfModifiedDttm;
	}

	public void setMsfModifiedDttm(DateTime msfModifiedDttm)
	{
		this.msfModifiedDttm = msfModifiedDttm;
	}

}
