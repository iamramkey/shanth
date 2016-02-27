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

public abstract class Country extends SignatureDbo
{
	private Integer ctrId;
	private String ctrIsoCd;
	private String ctrName;
	private boolean ctrDeleteFl;
	private Integer ctrDialCode;
	private String ctrBk;
	private Integer ctrCreatedUsrId;
	private Integer ctrModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime ctrCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime ctrModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			ctrId = getInteger(rs.getInt("ctr_id"));
			ctrIsoCd = rs.getString("ctr_iso_cd");
			ctrName = rs.getString("ctr_name");
			ctrDeleteFl = getBoolean(rs.getString("ctr_delete_fl"));
			ctrDialCode = rs.getInt("ctr_dial_code");
			ctrBk = rs.getString("ctr_bk");
			ctrCreatedUsrId = getInteger(rs.getInt("ctr_created_usr_id"));
			ctrModifiedUsrId = getInteger(rs.getInt("ctr_modified_usr_id"));
			ctrCreatedDttm = getDateTime(rs.getTimestamp("ctr_created_dttm"));
			ctrModifiedDttm = getDateTime(rs.getTimestamp("ctr_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return ctrId;
	}

	public void setPk(int pk)
	{
		ctrId = pk;
	}

	public String getBk()
	{
		return ctrBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(ctrId.toString());
		data.add(ctrIsoCd.toString());
		data.add(ctrName.toString());
		data.add(String.valueOf(ctrDeleteFl));
		data.add(ctrDialCode.toString());
		data.add(getCtrCreatedUsr());
		data.add(Constants.dttf.print(ctrCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("ctrIsoCd", getString(ctrIsoCd));
		data.put("ctrName", getString(ctrName));
		data.put("ctrDeleteFl", getString(ctrDeleteFl));
		data.put("ctrDialCode", getString(ctrDialCode));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setCtrIsoCd(input.getString("ctrIsoCd"));
		setCtrName(input.getString("ctrName"));
		setCtrDeleteFl(input.getBoolean("ctrDeleteFl"));
		setCtrDialCode(input.getInteger("ctrDialCode"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(ctrBk.toString());
		auditData.add(getCtrCreatedUsr());
		auditData.add(ctrModifiedUsrId == null ? Constants.NULLSTRING : getCtrModifiedUsr());
		auditData.add(Constants.dttf.print(ctrCreatedDttm));
		auditData.add(ctrModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(ctrModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return ctrName;
	}

	public String getCtrCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, ctrCreatedUsrId);
	}

	public String getCtrModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, ctrModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("ctrCreatedUsr"))
			return getCtrCreatedUsr();
		if (propertyName.equals("ctrModifiedUsr"))
			return getCtrModifiedUsr();
		if (propertyName.equals("ctrId"))
			return getCtrId();
		if (propertyName.equals("ctrIsoCd"))
			return getCtrIsoCd();
		if (propertyName.equals("ctrName"))
			return getCtrName();
		if (propertyName.equals("ctrDeleteFl"))
			return getCtrDeleteFl();
		if (propertyName.equals("ctrDialCode"))
			return getCtrDialCode();
		if (propertyName.equals("ctrBk"))
			return getCtrBk();
		if (propertyName.equals("ctrCreatedUsrId"))
			return getCtrCreatedUsrId();
		if (propertyName.equals("ctrModifiedUsrId"))
			return getCtrModifiedUsrId();
		if (propertyName.equals("ctrCreatedDttm"))
			return getCtrCreatedDttm();
		if (propertyName.equals("ctrModifiedDttm"))
			return getCtrModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getCtrId()
	{
		return ctrId;
	}

	public void setCtrId(Integer ctrId)
	{
		this.ctrId = ctrId;
	}

	public String getCtrIsoCd()
	{
		return ctrIsoCd;
	}

	public void setCtrIsoCd(String ctrIsoCd)
	{
		this.ctrIsoCd = ctrIsoCd;
	}

	public String getCtrName()
	{
		return ctrName;
	}

	public void setCtrName(String ctrName)
	{
		this.ctrName = ctrName;
	}

	public boolean getCtrDeleteFl()
	{
		return ctrDeleteFl;
	}

	public void setCtrDeleteFl(boolean ctrDeleteFl)
	{
		this.ctrDeleteFl = ctrDeleteFl;
	}

	public Integer getCtrDialCode()
	{
		return ctrDialCode;
	}

	public void setCtrDialCode(Integer ctrDialCode)
	{
		this.ctrDialCode = ctrDialCode;
	}

	public String getCtrBk()
	{
		return ctrBk;
	}

	public void setCtrBk(String ctrBk)
	{
		this.ctrBk = ctrBk;
	}

	public Integer getCtrCreatedUsrId()
	{
		return ctrCreatedUsrId;
	}

	public void setCtrCreatedUsrId(Integer ctrCreatedUsrId)
	{
		this.ctrCreatedUsrId = ctrCreatedUsrId;
	}

	public Integer getCtrModifiedUsrId()
	{
		return ctrModifiedUsrId;
	}

	public void setCtrModifiedUsrId(Integer ctrModifiedUsrId)
	{
		this.ctrModifiedUsrId = ctrModifiedUsrId;
	}

	public DateTime getCtrCreatedDttm()
	{
		return ctrCreatedDttm;
	}

	public void setCtrCreatedDttm(DateTime ctrCreatedDttm)
	{
		this.ctrCreatedDttm = ctrCreatedDttm;
	}

	public DateTime getCtrModifiedDttm()
	{
		return ctrModifiedDttm;
	}

	public void setCtrModifiedDttm(DateTime ctrModifiedDttm)
	{
		this.ctrModifiedDttm = ctrModifiedDttm;
	}

}
