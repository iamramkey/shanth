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

public abstract class PendingMerger extends SignatureDbo
{
	private Integer pmeId;
	private Integer accId;
	private String pmeName;
	private String pmePartition;
	private Integer pmsRrpJbtId;
	private String pmeBk;
	private Integer pmeCreatedUsrId;
	private Integer pmeModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime pmeCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime pmeModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			pmeId = getInteger(rs.getInt("pme_id"));
			accId = getInteger(rs.getInt("acc_id"));
			pmeName = rs.getString("pme_name");
			pmePartition = rs.getString("pme_partition");
			pmsRrpJbtId = getInteger(rs.getInt("pms_rrp_jbt_id"));
			pmeBk = rs.getString("pme_bk");
			pmeCreatedUsrId = getInteger(rs.getInt("pme_created_usr_id"));
			pmeModifiedUsrId = getInteger(rs.getInt("pme_modified_usr_id"));
			pmeCreatedDttm = getDateTime(rs.getTimestamp("pme_created_dttm"));
			pmeModifiedDttm = getDateTime(rs.getTimestamp("pme_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return pmeId;
	}

	public void setPk(int pk)
	{
		pmeId = pk;
	}

	public String getBk()
	{
		return pmeBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(pmeId.toString());
		data.add(accId.toString());
		data.add(pmeName.toString());
		data.add(pmePartition.toString());
		data.add(pmsRrpJbtId.toString());
		data.add(getPmeCreatedUsr());
		data.add(Constants.dttf.print(pmeCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("accId", getAccId());
		data.put("pmeName", getString(pmeName));
		data.put("pmePartition", getString(pmePartition));
		data.put("pmsRrpJbt", getPmsRrpJbt());
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setPmeName(input.getString("pmeName"));
		setPmePartition(input.getString("pmePartition"));
		setPmsRrpJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("pmsRrpJbt")));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(pmeBk.toString());
		auditData.add(getPmeCreatedUsr());
		auditData.add(pmeModifiedUsrId == null ? Constants.NULLSTRING : getPmeModifiedUsr());
		auditData.add(Constants.dttf.print(pmeCreatedDttm));
		auditData.add(pmeModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(pmeModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return pmeName;
	}

	public String getPmsRrpJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, pmsRrpJbtId);
	}

	public String getPmeCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, pmeCreatedUsrId);
	}

	public String getPmeModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, pmeModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("pmsRrpJbt"))
			return getPmsRrpJbt();
		if (propertyName.equals("pmeCreatedUsr"))
			return getPmeCreatedUsr();
		if (propertyName.equals("pmeModifiedUsr"))
			return getPmeModifiedUsr();
		if (propertyName.equals("pmeId"))
			return getPmeId();
		if (propertyName.equals("accId"))
			return getAccId();
		if (propertyName.equals("pmeName"))
			return getPmeName();
		if (propertyName.equals("pmePartition"))
			return getPmePartition();
		if (propertyName.equals("pmsRrpJbtId"))
			return getPmsRrpJbtId();
		if (propertyName.equals("pmeBk"))
			return getPmeBk();
		if (propertyName.equals("pmeCreatedUsrId"))
			return getPmeCreatedUsrId();
		if (propertyName.equals("pmeModifiedUsrId"))
			return getPmeModifiedUsrId();
		if (propertyName.equals("pmeCreatedDttm"))
			return getPmeCreatedDttm();
		if (propertyName.equals("pmeModifiedDttm"))
			return getPmeModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getPmeId()
	{
		return pmeId;
	}

	public void setPmeId(Integer pmeId)
	{
		this.pmeId = pmeId;
	}

	public Integer getAccId()
	{
		return accId;
	}

	public void setAccId(Integer accId)
	{
		this.accId = accId;
	}

	public String getPmeName()
	{
		return pmeName;
	}

	public void setPmeName(String pmeName)
	{
		this.pmeName = pmeName;
	}

	public String getPmePartition()
	{
		return pmePartition;
	}

	public void setPmePartition(String pmePartition)
	{
		this.pmePartition = pmePartition;
	}

	public Integer getPmsRrpJbtId()
	{
		return pmsRrpJbtId;
	}

	public void setPmsRrpJbtId(Integer pmsRrpJbtId)
	{
		this.pmsRrpJbtId = pmsRrpJbtId;
	}

	public String getPmeBk()
	{
		return pmeBk;
	}

	public void setPmeBk(String pmeBk)
	{
		this.pmeBk = pmeBk;
	}

	public Integer getPmeCreatedUsrId()
	{
		return pmeCreatedUsrId;
	}

	public void setPmeCreatedUsrId(Integer pmeCreatedUsrId)
	{
		this.pmeCreatedUsrId = pmeCreatedUsrId;
	}

	public Integer getPmeModifiedUsrId()
	{
		return pmeModifiedUsrId;
	}

	public void setPmeModifiedUsrId(Integer pmeModifiedUsrId)
	{
		this.pmeModifiedUsrId = pmeModifiedUsrId;
	}

	public DateTime getPmeCreatedDttm()
	{
		return pmeCreatedDttm;
	}

	public void setPmeCreatedDttm(DateTime pmeCreatedDttm)
	{
		this.pmeCreatedDttm = pmeCreatedDttm;
	}

	public DateTime getPmeModifiedDttm()
	{
		return pmeModifiedDttm;
	}

	public void setPmeModifiedDttm(DateTime pmeModifiedDttm)
	{
		this.pmeModifiedDttm = pmeModifiedDttm;
	}

}
