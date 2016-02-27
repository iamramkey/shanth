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
import se.signa.signature.gen.dba.UserLoginDba;
import se.signa.signature.gen.dba.UserTblDba;
import se.signa.signature.helpers.DisplayFieldHelper;

public abstract class UserSession extends SignatureDbo
{
	private Integer ussId;
	private Integer uslId;
	private Integer ussSessionCode;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime ussLastTxnDttm;
	private String ussName;
	private String ussBk;
	private Integer ussCreatedUsrId;
	private Integer ussModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime ussCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime ussModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			ussId = getInteger(rs.getInt("uss_id"));
			uslId = getInteger(rs.getInt("usl_id"));
			ussSessionCode = rs.getInt("uss_session_code");
			ussLastTxnDttm = getDateTime(rs.getTimestamp("uss_last_txn_dttm"));
			ussName = rs.getString("uss_name");
			ussBk = rs.getString("uss_bk");
			ussCreatedUsrId = getInteger(rs.getInt("uss_created_usr_id"));
			ussModifiedUsrId = getInteger(rs.getInt("uss_modified_usr_id"));
			ussCreatedDttm = getDateTime(rs.getTimestamp("uss_created_dttm"));
			ussModifiedDttm = getDateTime(rs.getTimestamp("uss_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return ussId;
	}

	public void setPk(int pk)
	{
		ussId = pk;
	}

	public String getBk()
	{
		return ussBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(ussId.toString());
		data.add(uslId.toString());
		data.add(ussSessionCode.toString());
		data.add(Constants.dttf.print(ussLastTxnDttm));
		data.add(ussName.toString());
		data.add(getUssCreatedUsr());
		data.add(Constants.dttf.print(ussCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("usl", getUsl());
		data.put("ussSessionCode", getString(ussSessionCode));
		data.put("ussLastTxnDttm", Constants.dttf.print(ussLastTxnDttm));
		data.put("ussName", getString(ussName));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setUslId(DisplayFieldHelper.getI().getPk(UserLoginDba.class, input.getString("usl")));
		setUssSessionCode(input.getInteger("ussSessionCode"));
		setUssLastTxnDttm(input.getDate("ussLastTxnDttm"));
		setUssName(input.getString("ussName"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(ussBk.toString());
		auditData.add(getUssCreatedUsr());
		auditData.add(ussModifiedUsrId == null ? Constants.NULLSTRING : getUssModifiedUsr());
		auditData.add(Constants.dttf.print(ussCreatedDttm));
		auditData.add(ussModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(ussModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return ussName;
	}

	public String getUsl()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserLoginDba.class, uslId);
	}

	public String getUssCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, ussCreatedUsrId);
	}

	public String getUssModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, ussModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("usl"))
			return getUsl();
		if (propertyName.equals("ussCreatedUsr"))
			return getUssCreatedUsr();
		if (propertyName.equals("ussModifiedUsr"))
			return getUssModifiedUsr();
		if (propertyName.equals("ussId"))
			return getUssId();
		if (propertyName.equals("uslId"))
			return getUslId();
		if (propertyName.equals("ussSessionCode"))
			return getUssSessionCode();
		if (propertyName.equals("ussLastTxnDttm"))
			return getUssLastTxnDttm();
		if (propertyName.equals("ussName"))
			return getUssName();
		if (propertyName.equals("ussBk"))
			return getUssBk();
		if (propertyName.equals("ussCreatedUsrId"))
			return getUssCreatedUsrId();
		if (propertyName.equals("ussModifiedUsrId"))
			return getUssModifiedUsrId();
		if (propertyName.equals("ussCreatedDttm"))
			return getUssCreatedDttm();
		if (propertyName.equals("ussModifiedDttm"))
			return getUssModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getUssId()
	{
		return ussId;
	}

	public void setUssId(Integer ussId)
	{
		this.ussId = ussId;
	}

	public Integer getUslId()
	{
		return uslId;
	}

	public void setUslId(Integer uslId)
	{
		this.uslId = uslId;
	}

	public Integer getUssSessionCode()
	{
		return ussSessionCode;
	}

	public void setUssSessionCode(Integer ussSessionCode)
	{
		this.ussSessionCode = ussSessionCode;
	}

	public DateTime getUssLastTxnDttm()
	{
		return ussLastTxnDttm;
	}

	public void setUssLastTxnDttm(DateTime ussLastTxnDttm)
	{
		this.ussLastTxnDttm = ussLastTxnDttm;
	}

	public String getUssName()
	{
		return ussName;
	}

	public void setUssName(String ussName)
	{
		this.ussName = ussName;
	}

	public String getUssBk()
	{
		return ussBk;
	}

	public void setUssBk(String ussBk)
	{
		this.ussBk = ussBk;
	}

	public Integer getUssCreatedUsrId()
	{
		return ussCreatedUsrId;
	}

	public void setUssCreatedUsrId(Integer ussCreatedUsrId)
	{
		this.ussCreatedUsrId = ussCreatedUsrId;
	}

	public Integer getUssModifiedUsrId()
	{
		return ussModifiedUsrId;
	}

	public void setUssModifiedUsrId(Integer ussModifiedUsrId)
	{
		this.ussModifiedUsrId = ussModifiedUsrId;
	}

	public DateTime getUssCreatedDttm()
	{
		return ussCreatedDttm;
	}

	public void setUssCreatedDttm(DateTime ussCreatedDttm)
	{
		this.ussCreatedDttm = ussCreatedDttm;
	}

	public DateTime getUssModifiedDttm()
	{
		return ussModifiedDttm;
	}

	public void setUssModifiedDttm(DateTime ussModifiedDttm)
	{
		this.ussModifiedDttm = ussModifiedDttm;
	}

}
