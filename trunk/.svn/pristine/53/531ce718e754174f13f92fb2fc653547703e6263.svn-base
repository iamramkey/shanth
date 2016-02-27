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

public abstract class PendingRaterLastPolled extends SignatureDbo
{
	private Integer rlpId;
	private Integer jbtId;
	private Integer accId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime rlpLastPolledDttm;
	private String rlpName;
	private String rlpBk;
	private Integer rlpCreatedUsrId;
	private Integer rlpModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime rlpCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime rlpModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			rlpId = getInteger(rs.getInt("rlp_id"));
			jbtId = getInteger(rs.getInt("jbt_id"));
			accId = getInteger(rs.getInt("acc_id"));
			rlpLastPolledDttm = getDateTime(rs.getTimestamp("rlp_last_polled_dttm"));
			rlpName = rs.getString("rlp_name");
			rlpBk = rs.getString("rlp_bk");
			rlpCreatedUsrId = getInteger(rs.getInt("rlp_created_usr_id"));
			rlpModifiedUsrId = getInteger(rs.getInt("rlp_modified_usr_id"));
			rlpCreatedDttm = getDateTime(rs.getTimestamp("rlp_created_dttm"));
			rlpModifiedDttm = getDateTime(rs.getTimestamp("rlp_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return rlpId;
	}

	public void setPk(int pk)
	{
		rlpId = pk;
	}

	public String getBk()
	{
		return rlpBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(rlpId.toString());
		data.add(jbtId.toString());
		data.add(accId.toString());
		data.add(rlpLastPolledDttm == null ? Constants.NULLSTRING : Constants.dttf.print(rlpLastPolledDttm));
		data.add(rlpName.toString());
		data.add(getRlpCreatedUsr());
		data.add(Constants.dttf.print(rlpCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("jbt", getJbt());
		data.put("accId", getAccId());
		data.put("rlpLastPolledDttm", Constants.dttf.print(rlpLastPolledDttm));
		data.put("rlpName", getString(rlpName));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("jbt")));
		setRlpLastPolledDttm(input.getDate("rlpLastPolledDttm"));
		setRlpName(input.getString("rlpName"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(rlpBk.toString());
		auditData.add(getRlpCreatedUsr());
		auditData.add(rlpModifiedUsrId == null ? Constants.NULLSTRING : getRlpModifiedUsr());
		auditData.add(Constants.dttf.print(rlpCreatedDttm));
		auditData.add(rlpModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(rlpModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return rlpName;
	}

	public String getJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, jbtId);
	}

	public String getRlpCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, rlpCreatedUsrId);
	}

	public String getRlpModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, rlpModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("jbt"))
			return getJbt();
		if (propertyName.equals("rlpCreatedUsr"))
			return getRlpCreatedUsr();
		if (propertyName.equals("rlpModifiedUsr"))
			return getRlpModifiedUsr();
		if (propertyName.equals("rlpId"))
			return getRlpId();
		if (propertyName.equals("jbtId"))
			return getJbtId();
		if (propertyName.equals("accId"))
			return getAccId();
		if (propertyName.equals("rlpLastPolledDttm"))
			return getRlpLastPolledDttm();
		if (propertyName.equals("rlpName"))
			return getRlpName();
		if (propertyName.equals("rlpBk"))
			return getRlpBk();
		if (propertyName.equals("rlpCreatedUsrId"))
			return getRlpCreatedUsrId();
		if (propertyName.equals("rlpModifiedUsrId"))
			return getRlpModifiedUsrId();
		if (propertyName.equals("rlpCreatedDttm"))
			return getRlpCreatedDttm();
		if (propertyName.equals("rlpModifiedDttm"))
			return getRlpModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getRlpId()
	{
		return rlpId;
	}

	public void setRlpId(Integer rlpId)
	{
		this.rlpId = rlpId;
	}

	public Integer getJbtId()
	{
		return jbtId;
	}

	public void setJbtId(Integer jbtId)
	{
		this.jbtId = jbtId;
	}

	public Integer getAccId()
	{
		return accId;
	}

	public void setAccId(Integer accId)
	{
		this.accId = accId;
	}

	public DateTime getRlpLastPolledDttm()
	{
		return rlpLastPolledDttm;
	}

	public void setRlpLastPolledDttm(DateTime rlpLastPolledDttm)
	{
		this.rlpLastPolledDttm = rlpLastPolledDttm;
	}

	public String getRlpName()
	{
		return rlpName;
	}

	public void setRlpName(String rlpName)
	{
		this.rlpName = rlpName;
	}

	public String getRlpBk()
	{
		return rlpBk;
	}

	public void setRlpBk(String rlpBk)
	{
		this.rlpBk = rlpBk;
	}

	public Integer getRlpCreatedUsrId()
	{
		return rlpCreatedUsrId;
	}

	public void setRlpCreatedUsrId(Integer rlpCreatedUsrId)
	{
		this.rlpCreatedUsrId = rlpCreatedUsrId;
	}

	public Integer getRlpModifiedUsrId()
	{
		return rlpModifiedUsrId;
	}

	public void setRlpModifiedUsrId(Integer rlpModifiedUsrId)
	{
		this.rlpModifiedUsrId = rlpModifiedUsrId;
	}

	public DateTime getRlpCreatedDttm()
	{
		return rlpCreatedDttm;
	}

	public void setRlpCreatedDttm(DateTime rlpCreatedDttm)
	{
		this.rlpCreatedDttm = rlpCreatedDttm;
	}

	public DateTime getRlpModifiedDttm()
	{
		return rlpModifiedDttm;
	}

	public void setRlpModifiedDttm(DateTime rlpModifiedDttm)
	{
		this.rlpModifiedDttm = rlpModifiedDttm;
	}

}
