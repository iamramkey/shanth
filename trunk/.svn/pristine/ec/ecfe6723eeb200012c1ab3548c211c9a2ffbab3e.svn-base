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

public abstract class PendingRecompute extends SignatureDbo
{
	private Integer prrId;
	private Integer accId;
	private Integer delId;
	private String prrType;
	private Integer prrPriority;
	private String prrName;
	private String prrBk;
	private Integer prrCreatedUsrId;
	private Integer prrModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime prrCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime prrModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			prrId = getInteger(rs.getInt("prr_id"));
			accId = getInteger(rs.getInt("acc_id"));
			delId = getInteger(rs.getInt("del_id"));
			prrType = rs.getString("prr_type");
			prrPriority = rs.getInt("prr_priority");
			prrName = rs.getString("prr_name");
			prrBk = rs.getString("prr_bk");
			prrCreatedUsrId = getInteger(rs.getInt("prr_created_usr_id"));
			prrModifiedUsrId = getInteger(rs.getInt("prr_modified_usr_id"));
			prrCreatedDttm = getDateTime(rs.getTimestamp("prr_created_dttm"));
			prrModifiedDttm = getDateTime(rs.getTimestamp("prr_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return prrId;
	}

	public void setPk(int pk)
	{
		prrId = pk;
	}

	public String getBk()
	{
		return prrBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(prrId.toString());
		data.add(accId.toString());
		data.add(delId.toString());
		data.add(prrType.toString());
		data.add(prrPriority.toString());
		data.add(prrName.toString());
		data.add(getPrrCreatedUsr());
		data.add(Constants.dttf.print(prrCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("accId", getAccId());
		data.put("delId", getDelId());
		data.put("prrType", getString(prrType));
		data.put("prrPriority", getString(prrPriority));
		data.put("prrName", getString(prrName));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setPrrType(input.getString("prrType"));
		setPrrPriority(input.getInteger("prrPriority"));
		setPrrName(input.getString("prrName"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(prrBk.toString());
		auditData.add(getPrrCreatedUsr());
		auditData.add(prrModifiedUsrId == null ? Constants.NULLSTRING : getPrrModifiedUsr());
		auditData.add(Constants.dttf.print(prrCreatedDttm));
		auditData.add(prrModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(prrModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return prrName;
	}

	public String getPrrCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, prrCreatedUsrId);
	}

	public String getPrrModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, prrModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("prrCreatedUsr"))
			return getPrrCreatedUsr();
		if (propertyName.equals("prrModifiedUsr"))
			return getPrrModifiedUsr();
		if (propertyName.equals("prrId"))
			return getPrrId();
		if (propertyName.equals("accId"))
			return getAccId();
		if (propertyName.equals("delId"))
			return getDelId();
		if (propertyName.equals("prrType"))
			return getPrrType();
		if (propertyName.equals("prrPriority"))
			return getPrrPriority();
		if (propertyName.equals("prrName"))
			return getPrrName();
		if (propertyName.equals("prrBk"))
			return getPrrBk();
		if (propertyName.equals("prrCreatedUsrId"))
			return getPrrCreatedUsrId();
		if (propertyName.equals("prrModifiedUsrId"))
			return getPrrModifiedUsrId();
		if (propertyName.equals("prrCreatedDttm"))
			return getPrrCreatedDttm();
		if (propertyName.equals("prrModifiedDttm"))
			return getPrrModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getPrrId()
	{
		return prrId;
	}

	public void setPrrId(Integer prrId)
	{
		this.prrId = prrId;
	}

	public Integer getAccId()
	{
		return accId;
	}

	public void setAccId(Integer accId)
	{
		this.accId = accId;
	}

	public Integer getDelId()
	{
		return delId;
	}

	public void setDelId(Integer delId)
	{
		this.delId = delId;
	}

	public String getPrrType()
	{
		return prrType;
	}

	public void setPrrType(String prrType)
	{
		this.prrType = prrType;
	}

	public Integer getPrrPriority()
	{
		return prrPriority;
	}

	public void setPrrPriority(Integer prrPriority)
	{
		this.prrPriority = prrPriority;
	}

	public String getPrrName()
	{
		return prrName;
	}

	public void setPrrName(String prrName)
	{
		this.prrName = prrName;
	}

	public String getPrrBk()
	{
		return prrBk;
	}

	public void setPrrBk(String prrBk)
	{
		this.prrBk = prrBk;
	}

	public Integer getPrrCreatedUsrId()
	{
		return prrCreatedUsrId;
	}

	public void setPrrCreatedUsrId(Integer prrCreatedUsrId)
	{
		this.prrCreatedUsrId = prrCreatedUsrId;
	}

	public Integer getPrrModifiedUsrId()
	{
		return prrModifiedUsrId;
	}

	public void setPrrModifiedUsrId(Integer prrModifiedUsrId)
	{
		this.prrModifiedUsrId = prrModifiedUsrId;
	}

	public DateTime getPrrCreatedDttm()
	{
		return prrCreatedDttm;
	}

	public void setPrrCreatedDttm(DateTime prrCreatedDttm)
	{
		this.prrCreatedDttm = prrCreatedDttm;
	}

	public DateTime getPrrModifiedDttm()
	{
		return prrModifiedDttm;
	}

	public void setPrrModifiedDttm(DateTime prrModifiedDttm)
	{
		this.prrModifiedDttm = prrModifiedDttm;
	}

}
