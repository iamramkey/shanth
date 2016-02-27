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
import se.signa.signature.gen.dba.ToolbarDba;
import se.signa.signature.gen.dba.UserTblDba;
import se.signa.signature.helpers.DisplayFieldHelper;

public abstract class Toolbar extends SignatureDbo
{
	private Integer tbrId;
	private Integer tbrParentTbrId;
	private Integer tbrOrderNo;
	private String tbrName;
	private String tbrIcon;
	private String tbrBk;
	private Integer tbrCreatedUsrId;
	private Integer tbrModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime tbrCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime tbrModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			tbrId = getInteger(rs.getInt("tbr_id"));
			tbrParentTbrId = getInteger(rs.getInt("tbr_parent_tbr_id"));
			tbrOrderNo = rs.getInt("tbr_order_no");
			tbrName = rs.getString("tbr_name");
			tbrIcon = rs.getString("tbr_icon");
			tbrBk = rs.getString("tbr_bk");
			tbrCreatedUsrId = getInteger(rs.getInt("tbr_created_usr_id"));
			tbrModifiedUsrId = getInteger(rs.getInt("tbr_modified_usr_id"));
			tbrCreatedDttm = getDateTime(rs.getTimestamp("tbr_created_dttm"));
			tbrModifiedDttm = getDateTime(rs.getTimestamp("tbr_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return tbrId;
	}

	public void setPk(int pk)
	{
		tbrId = pk;
	}

	public String getBk()
	{
		return tbrBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(tbrId.toString());
		data.add(tbrParentTbrId == null ? Constants.NULLSTRING : tbrParentTbrId.toString());
		data.add(tbrOrderNo.toString());
		data.add(tbrName.toString());
		data.add(tbrIcon.toString());
		data.add(getTbrCreatedUsr());
		data.add(Constants.dttf.print(tbrCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("tbrParentTbr", getTbrParentTbr());
		data.put("tbrOrderNo", getString(tbrOrderNo));
		data.put("tbrName", getString(tbrName));
		data.put("tbrIcon", getString(tbrIcon));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setTbrParentTbrId(DisplayFieldHelper.getI().getPk(ToolbarDba.class, input.getString("tbrParentTbr")));
		setTbrOrderNo(input.getInteger("tbrOrderNo"));
		setTbrName(input.getString("tbrName"));
		setTbrIcon(input.getString("tbrIcon"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(tbrBk.toString());
		auditData.add(getTbrCreatedUsr());
		auditData.add(tbrModifiedUsrId == null ? Constants.NULLSTRING : getTbrModifiedUsr());
		auditData.add(Constants.dttf.print(tbrCreatedDttm));
		auditData.add(tbrModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(tbrModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return tbrName;
	}

	public String getTbrParentTbr()
	{
		return DisplayFieldHelper.getI().getDisplayField(ToolbarDba.class, tbrParentTbrId);
	}

	public String getTbrCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, tbrCreatedUsrId);
	}

	public String getTbrModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, tbrModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("tbrParentTbr"))
			return getTbrParentTbr();
		if (propertyName.equals("tbrCreatedUsr"))
			return getTbrCreatedUsr();
		if (propertyName.equals("tbrModifiedUsr"))
			return getTbrModifiedUsr();
		if (propertyName.equals("tbrId"))
			return getTbrId();
		if (propertyName.equals("tbrParentTbrId"))
			return getTbrParentTbrId();
		if (propertyName.equals("tbrOrderNo"))
			return getTbrOrderNo();
		if (propertyName.equals("tbrName"))
			return getTbrName();
		if (propertyName.equals("tbrIcon"))
			return getTbrIcon();
		if (propertyName.equals("tbrBk"))
			return getTbrBk();
		if (propertyName.equals("tbrCreatedUsrId"))
			return getTbrCreatedUsrId();
		if (propertyName.equals("tbrModifiedUsrId"))
			return getTbrModifiedUsrId();
		if (propertyName.equals("tbrCreatedDttm"))
			return getTbrCreatedDttm();
		if (propertyName.equals("tbrModifiedDttm"))
			return getTbrModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getTbrId()
	{
		return tbrId;
	}

	public void setTbrId(Integer tbrId)
	{
		this.tbrId = tbrId;
	}

	public Integer getTbrParentTbrId()
	{
		return tbrParentTbrId;
	}

	public void setTbrParentTbrId(Integer tbrParentTbrId)
	{
		this.tbrParentTbrId = tbrParentTbrId;
	}

	public Integer getTbrOrderNo()
	{
		return tbrOrderNo;
	}

	public void setTbrOrderNo(Integer tbrOrderNo)
	{
		this.tbrOrderNo = tbrOrderNo;
	}

	public String getTbrName()
	{
		return tbrName;
	}

	public void setTbrName(String tbrName)
	{
		this.tbrName = tbrName;
	}

	public String getTbrIcon()
	{
		return tbrIcon;
	}

	public void setTbrIcon(String tbrIcon)
	{
		this.tbrIcon = tbrIcon;
	}

	public String getTbrBk()
	{
		return tbrBk;
	}

	public void setTbrBk(String tbrBk)
	{
		this.tbrBk = tbrBk;
	}

	public Integer getTbrCreatedUsrId()
	{
		return tbrCreatedUsrId;
	}

	public void setTbrCreatedUsrId(Integer tbrCreatedUsrId)
	{
		this.tbrCreatedUsrId = tbrCreatedUsrId;
	}

	public Integer getTbrModifiedUsrId()
	{
		return tbrModifiedUsrId;
	}

	public void setTbrModifiedUsrId(Integer tbrModifiedUsrId)
	{
		this.tbrModifiedUsrId = tbrModifiedUsrId;
	}

	public DateTime getTbrCreatedDttm()
	{
		return tbrCreatedDttm;
	}

	public void setTbrCreatedDttm(DateTime tbrCreatedDttm)
	{
		this.tbrCreatedDttm = tbrCreatedDttm;
	}

	public DateTime getTbrModifiedDttm()
	{
		return tbrModifiedDttm;
	}

	public void setTbrModifiedDttm(DateTime tbrModifiedDttm)
	{
		this.tbrModifiedDttm = tbrModifiedDttm;
	}

}
