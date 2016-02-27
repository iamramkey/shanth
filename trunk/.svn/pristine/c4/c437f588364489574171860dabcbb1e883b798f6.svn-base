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

public abstract class MasterSearchColumn extends SignatureDbo
{
	private Integer mcoId;
	private Integer mseId;
	private Integer mcoOrderNo;
	private String mcoHeader;
	private String mcoProperty;
	private String mcoCode;
	private String mcoBk;
	private Integer mcoCreatedUsrId;
	private Integer mcoModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime mcoCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime mcoModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			mcoId = getInteger(rs.getInt("mco_id"));
			mseId = getInteger(rs.getInt("mse_id"));
			mcoOrderNo = rs.getInt("mco_order_no");
			mcoHeader = rs.getString("mco_header");
			mcoProperty = rs.getString("mco_property");
			mcoCode = rs.getString("mco_code");
			mcoBk = rs.getString("mco_bk");
			mcoCreatedUsrId = getInteger(rs.getInt("mco_created_usr_id"));
			mcoModifiedUsrId = getInteger(rs.getInt("mco_modified_usr_id"));
			mcoCreatedDttm = getDateTime(rs.getTimestamp("mco_created_dttm"));
			mcoModifiedDttm = getDateTime(rs.getTimestamp("mco_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return mcoId;
	}

	public void setPk(int pk)
	{
		mcoId = pk;
	}

	public String getBk()
	{
		return mcoBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(mcoId.toString());
		data.add(mseId.toString());
		data.add(mcoOrderNo.toString());
		data.add(mcoHeader.toString());
		data.add(mcoProperty.toString());
		data.add(mcoCode.toString());
		data.add(getMcoCreatedUsr());
		data.add(Constants.dttf.print(mcoCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("mse", getMse());
		data.put("mcoOrderNo", getString(mcoOrderNo));
		data.put("mcoHeader", getString(mcoHeader));
		data.put("mcoProperty", getString(mcoProperty));
		data.put("mcoCode", getString(mcoCode));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setMseId(DisplayFieldHelper.getI().getPk(MasterSearchDba.class, input.getString("mse")));
		setMcoOrderNo(input.getInteger("mcoOrderNo"));
		setMcoHeader(input.getString("mcoHeader"));
		setMcoProperty(input.getString("mcoProperty"));
		setMcoCode(input.getString("mcoCode"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(mcoBk.toString());
		auditData.add(getMcoCreatedUsr());
		auditData.add(mcoModifiedUsrId == null ? Constants.NULLSTRING : getMcoModifiedUsr());
		auditData.add(Constants.dttf.print(mcoCreatedDttm));
		auditData.add(mcoModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(mcoModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return mcoCode;
	}

	public String getMse()
	{
		return DisplayFieldHelper.getI().getDisplayField(MasterSearchDba.class, mseId);
	}

	public String getMcoCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, mcoCreatedUsrId);
	}

	public String getMcoModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, mcoModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("mse"))
			return getMse();
		if (propertyName.equals("mcoCreatedUsr"))
			return getMcoCreatedUsr();
		if (propertyName.equals("mcoModifiedUsr"))
			return getMcoModifiedUsr();
		if (propertyName.equals("mcoId"))
			return getMcoId();
		if (propertyName.equals("mseId"))
			return getMseId();
		if (propertyName.equals("mcoOrderNo"))
			return getMcoOrderNo();
		if (propertyName.equals("mcoHeader"))
			return getMcoHeader();
		if (propertyName.equals("mcoProperty"))
			return getMcoProperty();
		if (propertyName.equals("mcoCode"))
			return getMcoCode();
		if (propertyName.equals("mcoBk"))
			return getMcoBk();
		if (propertyName.equals("mcoCreatedUsrId"))
			return getMcoCreatedUsrId();
		if (propertyName.equals("mcoModifiedUsrId"))
			return getMcoModifiedUsrId();
		if (propertyName.equals("mcoCreatedDttm"))
			return getMcoCreatedDttm();
		if (propertyName.equals("mcoModifiedDttm"))
			return getMcoModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getMcoId()
	{
		return mcoId;
	}

	public void setMcoId(Integer mcoId)
	{
		this.mcoId = mcoId;
	}

	public Integer getMseId()
	{
		return mseId;
	}

	public void setMseId(Integer mseId)
	{
		this.mseId = mseId;
	}

	public Integer getMcoOrderNo()
	{
		return mcoOrderNo;
	}

	public void setMcoOrderNo(Integer mcoOrderNo)
	{
		this.mcoOrderNo = mcoOrderNo;
	}

	public String getMcoHeader()
	{
		return mcoHeader;
	}

	public void setMcoHeader(String mcoHeader)
	{
		this.mcoHeader = mcoHeader;
	}

	public String getMcoProperty()
	{
		return mcoProperty;
	}

	public void setMcoProperty(String mcoProperty)
	{
		this.mcoProperty = mcoProperty;
	}

	public String getMcoCode()
	{
		return mcoCode;
	}

	public void setMcoCode(String mcoCode)
	{
		this.mcoCode = mcoCode;
	}

	public String getMcoBk()
	{
		return mcoBk;
	}

	public void setMcoBk(String mcoBk)
	{
		this.mcoBk = mcoBk;
	}

	public Integer getMcoCreatedUsrId()
	{
		return mcoCreatedUsrId;
	}

	public void setMcoCreatedUsrId(Integer mcoCreatedUsrId)
	{
		this.mcoCreatedUsrId = mcoCreatedUsrId;
	}

	public Integer getMcoModifiedUsrId()
	{
		return mcoModifiedUsrId;
	}

	public void setMcoModifiedUsrId(Integer mcoModifiedUsrId)
	{
		this.mcoModifiedUsrId = mcoModifiedUsrId;
	}

	public DateTime getMcoCreatedDttm()
	{
		return mcoCreatedDttm;
	}

	public void setMcoCreatedDttm(DateTime mcoCreatedDttm)
	{
		this.mcoCreatedDttm = mcoCreatedDttm;
	}

	public DateTime getMcoModifiedDttm()
	{
		return mcoModifiedDttm;
	}

	public void setMcoModifiedDttm(DateTime mcoModifiedDttm)
	{
		this.mcoModifiedDttm = mcoModifiedDttm;
	}

}
