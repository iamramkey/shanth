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
import se.signa.signature.gen.dba.NodeDba;
import se.signa.signature.gen.dba.UserTblDba;
import se.signa.signature.helpers.DisplayFieldHelper;

public abstract class Alert extends SignatureDbo
{
	private Integer altId;
	private Integer nodId;
	private Integer atpId;
	private String altName;
	private String altDesc;
	private String altExtra1;
	private String altExtra2;
	private String altExtra3;
	private String altBk;
	private Integer altCreatedUsrId;
	private Integer altModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime altCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime altModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			altId = getInteger(rs.getInt("alt_id"));
			nodId = getInteger(rs.getInt("nod_id"));
			atpId = getInteger(rs.getInt("atp_id"));
			altName = rs.getString("alt_name");
			altDesc = rs.getString("alt_desc");
			altExtra1 = rs.getString("alt_extra1");
			altExtra2 = rs.getString("alt_extra2");
			altExtra3 = rs.getString("alt_extra3");
			altBk = rs.getString("alt_bk");
			altCreatedUsrId = getInteger(rs.getInt("alt_created_usr_id"));
			altModifiedUsrId = getInteger(rs.getInt("alt_modified_usr_id"));
			altCreatedDttm = getDateTime(rs.getTimestamp("alt_created_dttm"));
			altModifiedDttm = getDateTime(rs.getTimestamp("alt_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return altId;
	}

	public void setPk(int pk)
	{
		altId = pk;
	}

	public String getBk()
	{
		return altBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(altId.toString());
		data.add(nodId.toString());
		data.add(atpId.toString());
		data.add(altName.toString());
		data.add(altDesc.toString());
		data.add(altExtra1 == null ? Constants.NULLSTRING : altExtra1.toString());
		data.add(altExtra2 == null ? Constants.NULLSTRING : altExtra2.toString());
		data.add(altExtra3 == null ? Constants.NULLSTRING : altExtra3.toString());
		data.add(getAltCreatedUsr());
		data.add(Constants.dttf.print(altCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("nod", getNod());
		data.put("atp", getAtp());
		data.put("altName", getString(altName));
		data.put("altDesc", getString(altDesc));
		data.put("altExtra1", getString(altExtra1));
		data.put("altExtra2", getString(altExtra2));
		data.put("altExtra3", getString(altExtra3));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setNodId(DisplayFieldHelper.getI().getPk(NodeDba.class, input.getString("nod")));
		setAtpId(DisplayFieldHelper.getI().getPk(AlertTypeDba.class, input.getString("atp")));
		setAltName(input.getString("altName"));
		setAltDesc(input.getString("altDesc"));
		setAltExtra1(input.getString("altExtra1"));
		setAltExtra2(input.getString("altExtra2"));
		setAltExtra3(input.getString("altExtra3"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(altBk.toString());
		auditData.add(getAltCreatedUsr());
		auditData.add(altModifiedUsrId == null ? Constants.NULLSTRING : getAltModifiedUsr());
		auditData.add(Constants.dttf.print(altCreatedDttm));
		auditData.add(altModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(altModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return altName;
	}

	public String getNod()
	{
		return DisplayFieldHelper.getI().getDisplayField(NodeDba.class, nodId);
	}

	public String getAtp()
	{
		return DisplayFieldHelper.getI().getDisplayField(AlertTypeDba.class, atpId);
	}

	public String getAltCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, altCreatedUsrId);
	}

	public String getAltModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, altModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("nod"))
			return getNod();
		if (propertyName.equals("atp"))
			return getAtp();
		if (propertyName.equals("altCreatedUsr"))
			return getAltCreatedUsr();
		if (propertyName.equals("altModifiedUsr"))
			return getAltModifiedUsr();
		if (propertyName.equals("altId"))
			return getAltId();
		if (propertyName.equals("nodId"))
			return getNodId();
		if (propertyName.equals("atpId"))
			return getAtpId();
		if (propertyName.equals("altName"))
			return getAltName();
		if (propertyName.equals("altDesc"))
			return getAltDesc();
		if (propertyName.equals("altExtra1"))
			return getAltExtra1();
		if (propertyName.equals("altExtra2"))
			return getAltExtra2();
		if (propertyName.equals("altExtra3"))
			return getAltExtra3();
		if (propertyName.equals("altBk"))
			return getAltBk();
		if (propertyName.equals("altCreatedUsrId"))
			return getAltCreatedUsrId();
		if (propertyName.equals("altModifiedUsrId"))
			return getAltModifiedUsrId();
		if (propertyName.equals("altCreatedDttm"))
			return getAltCreatedDttm();
		if (propertyName.equals("altModifiedDttm"))
			return getAltModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getAltId()
	{
		return altId;
	}

	public void setAltId(Integer altId)
	{
		this.altId = altId;
	}

	public Integer getNodId()
	{
		return nodId;
	}

	public void setNodId(Integer nodId)
	{
		this.nodId = nodId;
	}

	public Integer getAtpId()
	{
		return atpId;
	}

	public void setAtpId(Integer atpId)
	{
		this.atpId = atpId;
	}

	public String getAltName()
	{
		return altName;
	}

	public void setAltName(String altName)
	{
		this.altName = altName;
	}

	public String getAltDesc()
	{
		return altDesc;
	}

	public void setAltDesc(String altDesc)
	{
		this.altDesc = altDesc;
	}

	public String getAltExtra1()
	{
		return altExtra1;
	}

	public void setAltExtra1(String altExtra1)
	{
		this.altExtra1 = altExtra1;
	}

	public String getAltExtra2()
	{
		return altExtra2;
	}

	public void setAltExtra2(String altExtra2)
	{
		this.altExtra2 = altExtra2;
	}

	public String getAltExtra3()
	{
		return altExtra3;
	}

	public void setAltExtra3(String altExtra3)
	{
		this.altExtra3 = altExtra3;
	}

	public String getAltBk()
	{
		return altBk;
	}

	public void setAltBk(String altBk)
	{
		this.altBk = altBk;
	}

	public Integer getAltCreatedUsrId()
	{
		return altCreatedUsrId;
	}

	public void setAltCreatedUsrId(Integer altCreatedUsrId)
	{
		this.altCreatedUsrId = altCreatedUsrId;
	}

	public Integer getAltModifiedUsrId()
	{
		return altModifiedUsrId;
	}

	public void setAltModifiedUsrId(Integer altModifiedUsrId)
	{
		this.altModifiedUsrId = altModifiedUsrId;
	}

	public DateTime getAltCreatedDttm()
	{
		return altCreatedDttm;
	}

	public void setAltCreatedDttm(DateTime altCreatedDttm)
	{
		this.altCreatedDttm = altCreatedDttm;
	}

	public DateTime getAltModifiedDttm()
	{
		return altModifiedDttm;
	}

	public void setAltModifiedDttm(DateTime altModifiedDttm)
	{
		this.altModifiedDttm = altModifiedDttm;
	}

}
