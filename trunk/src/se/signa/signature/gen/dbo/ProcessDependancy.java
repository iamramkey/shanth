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

public abstract class ProcessDependancy extends SignatureDbo
{
	private Integer pdeId;
	private String pdeName;
	private Integer pdeMasterJbtId;
	private Integer pdeDependantJbtId;
	private String pdeBk;
	private Integer pdeCreatedUsrId;
	private Integer pdeModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime pdeCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime pdeModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			pdeId = getInteger(rs.getInt("pde_id"));
			pdeName = rs.getString("pde_name");
			pdeMasterJbtId = getInteger(rs.getInt("pde_master_jbt_id"));
			pdeDependantJbtId = getInteger(rs.getInt("pde_dependant_jbt_id"));
			pdeBk = rs.getString("pde_bk");
			pdeCreatedUsrId = getInteger(rs.getInt("pde_created_usr_id"));
			pdeModifiedUsrId = getInteger(rs.getInt("pde_modified_usr_id"));
			pdeCreatedDttm = getDateTime(rs.getTimestamp("pde_created_dttm"));
			pdeModifiedDttm = getDateTime(rs.getTimestamp("pde_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return pdeId;
	}

	public void setPk(int pk)
	{
		pdeId = pk;
	}

	public String getBk()
	{
		return pdeBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(pdeId.toString());
		data.add(pdeName.toString());
		data.add(pdeMasterJbtId.toString());
		data.add(pdeDependantJbtId.toString());
		data.add(getPdeCreatedUsr());
		data.add(Constants.dttf.print(pdeCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("pdeName", getString(pdeName));
		data.put("pdeMasterJbt", getPdeMasterJbt());
		data.put("pdeDependantJbt", getPdeDependantJbt());
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setPdeName(input.getString("pdeName"));
		setPdeMasterJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("pdeMasterJbt")));
		setPdeDependantJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("pdeDependantJbt")));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(pdeBk.toString());
		auditData.add(getPdeCreatedUsr());
		auditData.add(pdeModifiedUsrId == null ? Constants.NULLSTRING : getPdeModifiedUsr());
		auditData.add(Constants.dttf.print(pdeCreatedDttm));
		auditData.add(pdeModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(pdeModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return pdeName;
	}

	public String getPdeMasterJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, pdeMasterJbtId);
	}

	public String getPdeDependantJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, pdeDependantJbtId);
	}

	public String getPdeCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, pdeCreatedUsrId);
	}

	public String getPdeModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, pdeModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("pdeMasterJbt"))
			return getPdeMasterJbt();
		if (propertyName.equals("pdeDependantJbt"))
			return getPdeDependantJbt();
		if (propertyName.equals("pdeCreatedUsr"))
			return getPdeCreatedUsr();
		if (propertyName.equals("pdeModifiedUsr"))
			return getPdeModifiedUsr();
		if (propertyName.equals("pdeId"))
			return getPdeId();
		if (propertyName.equals("pdeName"))
			return getPdeName();
		if (propertyName.equals("pdeMasterJbtId"))
			return getPdeMasterJbtId();
		if (propertyName.equals("pdeDependantJbtId"))
			return getPdeDependantJbtId();
		if (propertyName.equals("pdeBk"))
			return getPdeBk();
		if (propertyName.equals("pdeCreatedUsrId"))
			return getPdeCreatedUsrId();
		if (propertyName.equals("pdeModifiedUsrId"))
			return getPdeModifiedUsrId();
		if (propertyName.equals("pdeCreatedDttm"))
			return getPdeCreatedDttm();
		if (propertyName.equals("pdeModifiedDttm"))
			return getPdeModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getPdeId()
	{
		return pdeId;
	}

	public void setPdeId(Integer pdeId)
	{
		this.pdeId = pdeId;
	}

	public String getPdeName()
	{
		return pdeName;
	}

	public void setPdeName(String pdeName)
	{
		this.pdeName = pdeName;
	}

	public Integer getPdeMasterJbtId()
	{
		return pdeMasterJbtId;
	}

	public void setPdeMasterJbtId(Integer pdeMasterJbtId)
	{
		this.pdeMasterJbtId = pdeMasterJbtId;
	}

	public Integer getPdeDependantJbtId()
	{
		return pdeDependantJbtId;
	}

	public void setPdeDependantJbtId(Integer pdeDependantJbtId)
	{
		this.pdeDependantJbtId = pdeDependantJbtId;
	}

	public String getPdeBk()
	{
		return pdeBk;
	}

	public void setPdeBk(String pdeBk)
	{
		this.pdeBk = pdeBk;
	}

	public Integer getPdeCreatedUsrId()
	{
		return pdeCreatedUsrId;
	}

	public void setPdeCreatedUsrId(Integer pdeCreatedUsrId)
	{
		this.pdeCreatedUsrId = pdeCreatedUsrId;
	}

	public Integer getPdeModifiedUsrId()
	{
		return pdeModifiedUsrId;
	}

	public void setPdeModifiedUsrId(Integer pdeModifiedUsrId)
	{
		this.pdeModifiedUsrId = pdeModifiedUsrId;
	}

	public DateTime getPdeCreatedDttm()
	{
		return pdeCreatedDttm;
	}

	public void setPdeCreatedDttm(DateTime pdeCreatedDttm)
	{
		this.pdeCreatedDttm = pdeCreatedDttm;
	}

	public DateTime getPdeModifiedDttm()
	{
		return pdeModifiedDttm;
	}

	public void setPdeModifiedDttm(DateTime pdeModifiedDttm)
	{
		this.pdeModifiedDttm = pdeModifiedDttm;
	}

}
