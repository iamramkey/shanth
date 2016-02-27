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

public abstract class PendingMergerRe extends SignatureDbo
{
	private Integer pmrId;
	private String pmrName;
	private String pmrPartition;
	private Integer pmrJbtId;
	private String pmrBk;
	private Integer pmrCreatedUsrId;
	private Integer pmrModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime pmrCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime pmrModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			pmrId = getInteger(rs.getInt("pmr_id"));
			pmrName = rs.getString("pmr_name");
			pmrPartition = rs.getString("pmr_partition");
			pmrJbtId = getInteger(rs.getInt("pmr_jbt_id"));
			pmrBk = rs.getString("pmr_bk");
			pmrCreatedUsrId = getInteger(rs.getInt("pmr_created_usr_id"));
			pmrModifiedUsrId = getInteger(rs.getInt("pmr_modified_usr_id"));
			pmrCreatedDttm = getDateTime(rs.getTimestamp("pmr_created_dttm"));
			pmrModifiedDttm = getDateTime(rs.getTimestamp("pmr_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return pmrId;
	}

	public void setPk(int pk)
	{
		pmrId = pk;
	}

	public String getBk()
	{
		return pmrBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(pmrId.toString());
		data.add(pmrName.toString());
		data.add(pmrPartition.toString());
		data.add(pmrJbtId.toString());
		data.add(getPmrCreatedUsr());
		data.add(Constants.dttf.print(pmrCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("pmrName", getString(pmrName));
		data.put("pmrPartition", getString(pmrPartition));
		data.put("pmrJbt", getPmrJbt());
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setPmrName(input.getString("pmrName"));
		setPmrPartition(input.getString("pmrPartition"));
		setPmrJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("pmrJbt")));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(pmrBk.toString());
		auditData.add(getPmrCreatedUsr());
		auditData.add(pmrModifiedUsrId == null ? Constants.NULLSTRING : getPmrModifiedUsr());
		auditData.add(Constants.dttf.print(pmrCreatedDttm));
		auditData.add(pmrModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(pmrModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return pmrName;
	}

	public String getPmrJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, pmrJbtId);
	}

	public String getPmrCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, pmrCreatedUsrId);
	}

	public String getPmrModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, pmrModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("pmrJbt"))
			return getPmrJbt();
		if (propertyName.equals("pmrCreatedUsr"))
			return getPmrCreatedUsr();
		if (propertyName.equals("pmrModifiedUsr"))
			return getPmrModifiedUsr();
		if (propertyName.equals("pmrId"))
			return getPmrId();
		if (propertyName.equals("pmrName"))
			return getPmrName();
		if (propertyName.equals("pmrPartition"))
			return getPmrPartition();
		if (propertyName.equals("pmrJbtId"))
			return getPmrJbtId();
		if (propertyName.equals("pmrBk"))
			return getPmrBk();
		if (propertyName.equals("pmrCreatedUsrId"))
			return getPmrCreatedUsrId();
		if (propertyName.equals("pmrModifiedUsrId"))
			return getPmrModifiedUsrId();
		if (propertyName.equals("pmrCreatedDttm"))
			return getPmrCreatedDttm();
		if (propertyName.equals("pmrModifiedDttm"))
			return getPmrModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getPmrId()
	{
		return pmrId;
	}

	public void setPmrId(Integer pmrId)
	{
		this.pmrId = pmrId;
	}

	public String getPmrName()
	{
		return pmrName;
	}

	public void setPmrName(String pmrName)
	{
		this.pmrName = pmrName;
	}

	public String getPmrPartition()
	{
		return pmrPartition;
	}

	public void setPmrPartition(String pmrPartition)
	{
		this.pmrPartition = pmrPartition;
	}

	public Integer getPmrJbtId()
	{
		return pmrJbtId;
	}

	public void setPmrJbtId(Integer pmrJbtId)
	{
		this.pmrJbtId = pmrJbtId;
	}

	public String getPmrBk()
	{
		return pmrBk;
	}

	public void setPmrBk(String pmrBk)
	{
		this.pmrBk = pmrBk;
	}

	public Integer getPmrCreatedUsrId()
	{
		return pmrCreatedUsrId;
	}

	public void setPmrCreatedUsrId(Integer pmrCreatedUsrId)
	{
		this.pmrCreatedUsrId = pmrCreatedUsrId;
	}

	public Integer getPmrModifiedUsrId()
	{
		return pmrModifiedUsrId;
	}

	public void setPmrModifiedUsrId(Integer pmrModifiedUsrId)
	{
		this.pmrModifiedUsrId = pmrModifiedUsrId;
	}

	public DateTime getPmrCreatedDttm()
	{
		return pmrCreatedDttm;
	}

	public void setPmrCreatedDttm(DateTime pmrCreatedDttm)
	{
		this.pmrCreatedDttm = pmrCreatedDttm;
	}

	public DateTime getPmrModifiedDttm()
	{
		return pmrModifiedDttm;
	}

	public void setPmrModifiedDttm(DateTime pmrModifiedDttm)
	{
		this.pmrModifiedDttm = pmrModifiedDttm;
	}

}
