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

public abstract class ToolbarItem extends SignatureDbo
{
	private Integer tbiId;
	private Integer tbrId;
	private Integer tbiOrderNo;
	private String tbiName;
	private String tbiUrl;
	private String tbiIcon;
	private String tbiBk;
	private Integer tbiCreatedUsrId;
	private Integer tbiModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime tbiCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime tbiModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			tbiId = getInteger(rs.getInt("tbi_id"));
			tbrId = getInteger(rs.getInt("tbr_id"));
			tbiOrderNo = rs.getInt("tbi_order_no");
			tbiName = rs.getString("tbi_name");
			tbiUrl = rs.getString("tbi_url");
			tbiIcon = rs.getString("tbi_icon");
			tbiBk = rs.getString("tbi_bk");
			tbiCreatedUsrId = getInteger(rs.getInt("tbi_created_usr_id"));
			tbiModifiedUsrId = getInteger(rs.getInt("tbi_modified_usr_id"));
			tbiCreatedDttm = getDateTime(rs.getTimestamp("tbi_created_dttm"));
			tbiModifiedDttm = getDateTime(rs.getTimestamp("tbi_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return tbiId;
	}

	public void setPk(int pk)
	{
		tbiId = pk;
	}

	public String getBk()
	{
		return tbiBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(tbiId.toString());
		data.add(tbrId.toString());
		data.add(tbiOrderNo.toString());
		data.add(tbiName.toString());
		data.add(tbiUrl.toString());
		data.add(tbiIcon.toString());
		data.add(getTbiCreatedUsr());
		data.add(Constants.dttf.print(tbiCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("tbr", getTbr());
		data.put("tbiOrderNo", getString(tbiOrderNo));
		data.put("tbiName", getString(tbiName));
		data.put("tbiUrl", getString(tbiUrl));
		data.put("tbiIcon", getString(tbiIcon));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setTbrId(DisplayFieldHelper.getI().getPk(ToolbarDba.class, input.getString("tbr")));
		setTbiOrderNo(input.getInteger("tbiOrderNo"));
		setTbiName(input.getString("tbiName"));
		setTbiUrl(input.getString("tbiUrl"));
		setTbiIcon(input.getString("tbiIcon"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(tbiBk.toString());
		auditData.add(getTbiCreatedUsr());
		auditData.add(tbiModifiedUsrId == null ? Constants.NULLSTRING : getTbiModifiedUsr());
		auditData.add(Constants.dttf.print(tbiCreatedDttm));
		auditData.add(tbiModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(tbiModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return tbiName;
	}

	public String getTbr()
	{
		return DisplayFieldHelper.getI().getDisplayField(ToolbarDba.class, tbrId);
	}

	public String getTbiCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, tbiCreatedUsrId);
	}

	public String getTbiModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, tbiModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("tbr"))
			return getTbr();
		if (propertyName.equals("tbiCreatedUsr"))
			return getTbiCreatedUsr();
		if (propertyName.equals("tbiModifiedUsr"))
			return getTbiModifiedUsr();
		if (propertyName.equals("tbiId"))
			return getTbiId();
		if (propertyName.equals("tbrId"))
			return getTbrId();
		if (propertyName.equals("tbiOrderNo"))
			return getTbiOrderNo();
		if (propertyName.equals("tbiName"))
			return getTbiName();
		if (propertyName.equals("tbiUrl"))
			return getTbiUrl();
		if (propertyName.equals("tbiIcon"))
			return getTbiIcon();
		if (propertyName.equals("tbiBk"))
			return getTbiBk();
		if (propertyName.equals("tbiCreatedUsrId"))
			return getTbiCreatedUsrId();
		if (propertyName.equals("tbiModifiedUsrId"))
			return getTbiModifiedUsrId();
		if (propertyName.equals("tbiCreatedDttm"))
			return getTbiCreatedDttm();
		if (propertyName.equals("tbiModifiedDttm"))
			return getTbiModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getTbiId()
	{
		return tbiId;
	}

	public void setTbiId(Integer tbiId)
	{
		this.tbiId = tbiId;
	}

	public Integer getTbrId()
	{
		return tbrId;
	}

	public void setTbrId(Integer tbrId)
	{
		this.tbrId = tbrId;
	}

	public Integer getTbiOrderNo()
	{
		return tbiOrderNo;
	}

	public void setTbiOrderNo(Integer tbiOrderNo)
	{
		this.tbiOrderNo = tbiOrderNo;
	}

	public String getTbiName()
	{
		return tbiName;
	}

	public void setTbiName(String tbiName)
	{
		this.tbiName = tbiName;
	}

	public String getTbiUrl()
	{
		return tbiUrl;
	}

	public void setTbiUrl(String tbiUrl)
	{
		this.tbiUrl = tbiUrl;
	}

	public String getTbiIcon()
	{
		return tbiIcon;
	}

	public void setTbiIcon(String tbiIcon)
	{
		this.tbiIcon = tbiIcon;
	}

	public String getTbiBk()
	{
		return tbiBk;
	}

	public void setTbiBk(String tbiBk)
	{
		this.tbiBk = tbiBk;
	}

	public Integer getTbiCreatedUsrId()
	{
		return tbiCreatedUsrId;
	}

	public void setTbiCreatedUsrId(Integer tbiCreatedUsrId)
	{
		this.tbiCreatedUsrId = tbiCreatedUsrId;
	}

	public Integer getTbiModifiedUsrId()
	{
		return tbiModifiedUsrId;
	}

	public void setTbiModifiedUsrId(Integer tbiModifiedUsrId)
	{
		this.tbiModifiedUsrId = tbiModifiedUsrId;
	}

	public DateTime getTbiCreatedDttm()
	{
		return tbiCreatedDttm;
	}

	public void setTbiCreatedDttm(DateTime tbiCreatedDttm)
	{
		this.tbiCreatedDttm = tbiCreatedDttm;
	}

	public DateTime getTbiModifiedDttm()
	{
		return tbiModifiedDttm;
	}

	public void setTbiModifiedDttm(DateTime tbiModifiedDttm)
	{
		this.tbiModifiedDttm = tbiModifiedDttm;
	}

}
