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

public abstract class PendingMissingJob extends SignatureDbo
{
	private Integer pmiId;
	private String pmiName;
	private Integer pmiPendingMissingJbtId;
	private Integer pmiPendingLookbackFromMin;
	private Integer pmiPendingLookbackToMin;
	private Integer pmiMissingLookbackFromMin;
	private Integer pmiMissingLookbackToMin;
	private String pmiBk;
	private Integer pmiCreatedUsrId;
	private Integer pmiModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime pmiCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime pmiModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			pmiId = getInteger(rs.getInt("pmi_id"));
			pmiName = rs.getString("pmi_name");
			pmiPendingMissingJbtId = getInteger(rs.getInt("pmi_pending_missing_jbt_id"));
			pmiPendingLookbackFromMin = rs.getInt("pmi_pending_lookback_from_min");
			pmiPendingLookbackToMin = rs.getInt("pmi_pending_lookback_to_min");
			pmiMissingLookbackFromMin = rs.getInt("pmi_missing_lookback_from_min");
			pmiMissingLookbackToMin = rs.getInt("pmi_missing_lookback_to_min");
			pmiBk = rs.getString("pmi_bk");
			pmiCreatedUsrId = getInteger(rs.getInt("pmi_created_usr_id"));
			pmiModifiedUsrId = getInteger(rs.getInt("pmi_modified_usr_id"));
			pmiCreatedDttm = getDateTime(rs.getTimestamp("pmi_created_dttm"));
			pmiModifiedDttm = getDateTime(rs.getTimestamp("pmi_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return pmiId;
	}

	public void setPk(int pk)
	{
		pmiId = pk;
	}

	public String getBk()
	{
		return pmiBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(pmiId.toString());
		data.add(pmiName.toString());
		data.add(pmiPendingMissingJbtId.toString());
		data.add(pmiPendingLookbackFromMin.toString());
		data.add(pmiPendingLookbackToMin.toString());
		data.add(pmiMissingLookbackFromMin.toString());
		data.add(pmiMissingLookbackToMin.toString());
		data.add(getPmiCreatedUsr());
		data.add(Constants.dttf.print(pmiCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("pmiName", getString(pmiName));
		data.put("pmiPendingMissingJbt", getPmiPendingMissingJbt());
		data.put("pmiPendingLookbackFromMin", getString(pmiPendingLookbackFromMin));
		data.put("pmiPendingLookbackToMin", getString(pmiPendingLookbackToMin));
		data.put("pmiMissingLookbackFromMin", getString(pmiMissingLookbackFromMin));
		data.put("pmiMissingLookbackToMin", getString(pmiMissingLookbackToMin));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setPmiName(input.getString("pmiName"));
		setPmiPendingMissingJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("pmiPendingMissingJbt")));
		setPmiPendingLookbackFromMin(input.getInteger("pmiPendingLookbackFromMin"));
		setPmiPendingLookbackToMin(input.getInteger("pmiPendingLookbackToMin"));
		setPmiMissingLookbackFromMin(input.getInteger("pmiMissingLookbackFromMin"));
		setPmiMissingLookbackToMin(input.getInteger("pmiMissingLookbackToMin"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(pmiBk.toString());
		auditData.add(getPmiCreatedUsr());
		auditData.add(pmiModifiedUsrId == null ? Constants.NULLSTRING : getPmiModifiedUsr());
		auditData.add(Constants.dttf.print(pmiCreatedDttm));
		auditData.add(pmiModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(pmiModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return pmiName;
	}

	public String getPmiPendingMissingJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, pmiPendingMissingJbtId);
	}

	public String getPmiCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, pmiCreatedUsrId);
	}

	public String getPmiModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, pmiModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("pmiPendingMissingJbt"))
			return getPmiPendingMissingJbt();
		if (propertyName.equals("pmiCreatedUsr"))
			return getPmiCreatedUsr();
		if (propertyName.equals("pmiModifiedUsr"))
			return getPmiModifiedUsr();
		if (propertyName.equals("pmiId"))
			return getPmiId();
		if (propertyName.equals("pmiName"))
			return getPmiName();
		if (propertyName.equals("pmiPendingMissingJbtId"))
			return getPmiPendingMissingJbtId();
		if (propertyName.equals("pmiPendingLookbackFromMin"))
			return getPmiPendingLookbackFromMin();
		if (propertyName.equals("pmiPendingLookbackToMin"))
			return getPmiPendingLookbackToMin();
		if (propertyName.equals("pmiMissingLookbackFromMin"))
			return getPmiMissingLookbackFromMin();
		if (propertyName.equals("pmiMissingLookbackToMin"))
			return getPmiMissingLookbackToMin();
		if (propertyName.equals("pmiBk"))
			return getPmiBk();
		if (propertyName.equals("pmiCreatedUsrId"))
			return getPmiCreatedUsrId();
		if (propertyName.equals("pmiModifiedUsrId"))
			return getPmiModifiedUsrId();
		if (propertyName.equals("pmiCreatedDttm"))
			return getPmiCreatedDttm();
		if (propertyName.equals("pmiModifiedDttm"))
			return getPmiModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getPmiId()
	{
		return pmiId;
	}

	public void setPmiId(Integer pmiId)
	{
		this.pmiId = pmiId;
	}

	public String getPmiName()
	{
		return pmiName;
	}

	public void setPmiName(String pmiName)
	{
		this.pmiName = pmiName;
	}

	public Integer getPmiPendingMissingJbtId()
	{
		return pmiPendingMissingJbtId;
	}

	public void setPmiPendingMissingJbtId(Integer pmiPendingMissingJbtId)
	{
		this.pmiPendingMissingJbtId = pmiPendingMissingJbtId;
	}

	public Integer getPmiPendingLookbackFromMin()
	{
		return pmiPendingLookbackFromMin;
	}

	public void setPmiPendingLookbackFromMin(Integer pmiPendingLookbackFromMin)
	{
		this.pmiPendingLookbackFromMin = pmiPendingLookbackFromMin;
	}

	public Integer getPmiPendingLookbackToMin()
	{
		return pmiPendingLookbackToMin;
	}

	public void setPmiPendingLookbackToMin(Integer pmiPendingLookbackToMin)
	{
		this.pmiPendingLookbackToMin = pmiPendingLookbackToMin;
	}

	public Integer getPmiMissingLookbackFromMin()
	{
		return pmiMissingLookbackFromMin;
	}

	public void setPmiMissingLookbackFromMin(Integer pmiMissingLookbackFromMin)
	{
		this.pmiMissingLookbackFromMin = pmiMissingLookbackFromMin;
	}

	public Integer getPmiMissingLookbackToMin()
	{
		return pmiMissingLookbackToMin;
	}

	public void setPmiMissingLookbackToMin(Integer pmiMissingLookbackToMin)
	{
		this.pmiMissingLookbackToMin = pmiMissingLookbackToMin;
	}

	public String getPmiBk()
	{
		return pmiBk;
	}

	public void setPmiBk(String pmiBk)
	{
		this.pmiBk = pmiBk;
	}

	public Integer getPmiCreatedUsrId()
	{
		return pmiCreatedUsrId;
	}

	public void setPmiCreatedUsrId(Integer pmiCreatedUsrId)
	{
		this.pmiCreatedUsrId = pmiCreatedUsrId;
	}

	public Integer getPmiModifiedUsrId()
	{
		return pmiModifiedUsrId;
	}

	public void setPmiModifiedUsrId(Integer pmiModifiedUsrId)
	{
		this.pmiModifiedUsrId = pmiModifiedUsrId;
	}

	public DateTime getPmiCreatedDttm()
	{
		return pmiCreatedDttm;
	}

	public void setPmiCreatedDttm(DateTime pmiCreatedDttm)
	{
		this.pmiCreatedDttm = pmiCreatedDttm;
	}

	public DateTime getPmiModifiedDttm()
	{
		return pmiModifiedDttm;
	}

	public void setPmiModifiedDttm(DateTime pmiModifiedDttm)
	{
		this.pmiModifiedDttm = pmiModifiedDttm;
	}

}
