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
import se.signa.signature.gen.dba.FiletblDba;
import se.signa.signature.gen.dba.JobDba;
import se.signa.signature.gen.dba.UserTblDba;
import se.signa.signature.helpers.DisplayFieldHelper;

public abstract class PendingBixOutputFile extends SignatureDbo
{
	private Integer pbfId;
	private Integer filId;
	private Integer accId;
	private String pbfName;
	private Integer pbfExportedJobId;
	private String pbfBk;
	private Integer pbfCreatedUsrId;
	private Integer pbfModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime pbfCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime pbfModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			pbfId = getInteger(rs.getInt("pbf_id"));
			filId = getInteger(rs.getInt("fil_id"));
			accId = getInteger(rs.getInt("acc_id"));
			pbfName = rs.getString("pbf_name");
			pbfExportedJobId = getInteger(rs.getInt("pbf_exported_job_id"));
			pbfBk = rs.getString("pbf_bk");
			pbfCreatedUsrId = getInteger(rs.getInt("pbf_created_usr_id"));
			pbfModifiedUsrId = getInteger(rs.getInt("pbf_modified_usr_id"));
			pbfCreatedDttm = getDateTime(rs.getTimestamp("pbf_created_dttm"));
			pbfModifiedDttm = getDateTime(rs.getTimestamp("pbf_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return pbfId;
	}

	public void setPk(int pk)
	{
		pbfId = pk;
	}

	public String getBk()
	{
		return pbfBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(pbfId.toString());
		data.add(filId.toString());
		data.add(accId.toString());
		data.add(pbfName.toString());
		data.add(pbfExportedJobId == null ? Constants.NULLSTRING : pbfExportedJobId.toString());
		data.add(getPbfCreatedUsr());
		data.add(Constants.dttf.print(pbfCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("fil", getFil());
		data.put("accId", getAccId());
		data.put("pbfName", getString(pbfName));
		data.put("pbfExportedJob", getPbfExportedJob());
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setFilId(DisplayFieldHelper.getI().getPk(FiletblDba.class, input.getString("fil")));
		setPbfName(input.getString("pbfName"));
		setPbfExportedJobId(DisplayFieldHelper.getI().getPk(JobDba.class, input.getString("pbfExportedJob")));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(pbfBk.toString());
		auditData.add(getPbfCreatedUsr());
		auditData.add(pbfModifiedUsrId == null ? Constants.NULLSTRING : getPbfModifiedUsr());
		auditData.add(Constants.dttf.print(pbfCreatedDttm));
		auditData.add(pbfModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(pbfModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return pbfName;
	}

	public String getFil()
	{
		return DisplayFieldHelper.getI().getDisplayField(FiletblDba.class, filId);
	}

	public String getPbfExportedJob()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobDba.class, pbfExportedJobId);
	}

	public String getPbfCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, pbfCreatedUsrId);
	}

	public String getPbfModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, pbfModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("fil"))
			return getFil();
		if (propertyName.equals("pbfExportedJob"))
			return getPbfExportedJob();
		if (propertyName.equals("pbfCreatedUsr"))
			return getPbfCreatedUsr();
		if (propertyName.equals("pbfModifiedUsr"))
			return getPbfModifiedUsr();
		if (propertyName.equals("pbfId"))
			return getPbfId();
		if (propertyName.equals("filId"))
			return getFilId();
		if (propertyName.equals("accId"))
			return getAccId();
		if (propertyName.equals("pbfName"))
			return getPbfName();
		if (propertyName.equals("pbfExportedJobId"))
			return getPbfExportedJobId();
		if (propertyName.equals("pbfBk"))
			return getPbfBk();
		if (propertyName.equals("pbfCreatedUsrId"))
			return getPbfCreatedUsrId();
		if (propertyName.equals("pbfModifiedUsrId"))
			return getPbfModifiedUsrId();
		if (propertyName.equals("pbfCreatedDttm"))
			return getPbfCreatedDttm();
		if (propertyName.equals("pbfModifiedDttm"))
			return getPbfModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getPbfId()
	{
		return pbfId;
	}

	public void setPbfId(Integer pbfId)
	{
		this.pbfId = pbfId;
	}

	public Integer getFilId()
	{
		return filId;
	}

	public void setFilId(Integer filId)
	{
		this.filId = filId;
	}

	public Integer getAccId()
	{
		return accId;
	}

	public void setAccId(Integer accId)
	{
		this.accId = accId;
	}

	public String getPbfName()
	{
		return pbfName;
	}

	public void setPbfName(String pbfName)
	{
		this.pbfName = pbfName;
	}

	public Integer getPbfExportedJobId()
	{
		return pbfExportedJobId;
	}

	public void setPbfExportedJobId(Integer pbfExportedJobId)
	{
		this.pbfExportedJobId = pbfExportedJobId;
	}

	public String getPbfBk()
	{
		return pbfBk;
	}

	public void setPbfBk(String pbfBk)
	{
		this.pbfBk = pbfBk;
	}

	public Integer getPbfCreatedUsrId()
	{
		return pbfCreatedUsrId;
	}

	public void setPbfCreatedUsrId(Integer pbfCreatedUsrId)
	{
		this.pbfCreatedUsrId = pbfCreatedUsrId;
	}

	public Integer getPbfModifiedUsrId()
	{
		return pbfModifiedUsrId;
	}

	public void setPbfModifiedUsrId(Integer pbfModifiedUsrId)
	{
		this.pbfModifiedUsrId = pbfModifiedUsrId;
	}

	public DateTime getPbfCreatedDttm()
	{
		return pbfCreatedDttm;
	}

	public void setPbfCreatedDttm(DateTime pbfCreatedDttm)
	{
		this.pbfCreatedDttm = pbfCreatedDttm;
	}

	public DateTime getPbfModifiedDttm()
	{
		return pbfModifiedDttm;
	}

	public void setPbfModifiedDttm(DateTime pbfModifiedDttm)
	{
		this.pbfModifiedDttm = pbfModifiedDttm;
	}

}
