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

public abstract class PendingDataloadFile extends SignatureDbo
{
	private Integer pdfId;
	private Integer filId;
	private Integer accId;
	private String pdfName;
	private String pdfTableName;
	private boolean pdfPollFl;
	private Integer jobId;
	private String pdfBk;
	private Integer pdfCreatedUsrId;
	private Integer pdfModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime pdfCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime pdfModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			pdfId = getInteger(rs.getInt("pdf_id"));
			filId = getInteger(rs.getInt("fil_id"));
			accId = getInteger(rs.getInt("acc_id"));
			pdfName = rs.getString("pdf_name");
			pdfTableName = rs.getString("pdf_table_name");
			pdfPollFl = getBoolean(rs.getString("pdf_poll_fl"));
			jobId = getInteger(rs.getInt("job_id"));
			pdfBk = rs.getString("pdf_bk");
			pdfCreatedUsrId = getInteger(rs.getInt("pdf_created_usr_id"));
			pdfModifiedUsrId = getInteger(rs.getInt("pdf_modified_usr_id"));
			pdfCreatedDttm = getDateTime(rs.getTimestamp("pdf_created_dttm"));
			pdfModifiedDttm = getDateTime(rs.getTimestamp("pdf_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return pdfId;
	}

	public void setPk(int pk)
	{
		pdfId = pk;
	}

	public String getBk()
	{
		return pdfBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(pdfId.toString());
		data.add(filId.toString());
		data.add(accId.toString());
		data.add(pdfName.toString());
		data.add(pdfTableName.toString());
		data.add(String.valueOf(pdfPollFl));
		data.add(jobId.toString());
		data.add(getPdfCreatedUsr());
		data.add(Constants.dttf.print(pdfCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("fil", getFil());
		data.put("accId", getAccId());
		data.put("pdfName", getString(pdfName));
		data.put("pdfTableName", getString(pdfTableName));
		data.put("pdfPollFl", getString(pdfPollFl));
		data.put("job", getJob());
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setFilId(DisplayFieldHelper.getI().getPk(FiletblDba.class, input.getString("fil")));
		setPdfName(input.getString("pdfName"));
		setPdfTableName(input.getString("pdfTableName"));
		setPdfPollFl(input.getBoolean("pdfPollFl"));
		setJobId(DisplayFieldHelper.getI().getPk(JobDba.class, input.getString("job")));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(pdfBk.toString());
		auditData.add(getPdfCreatedUsr());
		auditData.add(pdfModifiedUsrId == null ? Constants.NULLSTRING : getPdfModifiedUsr());
		auditData.add(Constants.dttf.print(pdfCreatedDttm));
		auditData.add(pdfModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(pdfModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return pdfName;
	}

	public String getFil()
	{
		return DisplayFieldHelper.getI().getDisplayField(FiletblDba.class, filId);
	}

	public String getJob()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobDba.class, jobId);
	}

	public String getPdfCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, pdfCreatedUsrId);
	}

	public String getPdfModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, pdfModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("fil"))
			return getFil();
		if (propertyName.equals("job"))
			return getJob();
		if (propertyName.equals("pdfCreatedUsr"))
			return getPdfCreatedUsr();
		if (propertyName.equals("pdfModifiedUsr"))
			return getPdfModifiedUsr();
		if (propertyName.equals("pdfId"))
			return getPdfId();
		if (propertyName.equals("filId"))
			return getFilId();
		if (propertyName.equals("accId"))
			return getAccId();
		if (propertyName.equals("pdfName"))
			return getPdfName();
		if (propertyName.equals("pdfTableName"))
			return getPdfTableName();
		if (propertyName.equals("pdfPollFl"))
			return getPdfPollFl();
		if (propertyName.equals("jobId"))
			return getJobId();
		if (propertyName.equals("pdfBk"))
			return getPdfBk();
		if (propertyName.equals("pdfCreatedUsrId"))
			return getPdfCreatedUsrId();
		if (propertyName.equals("pdfModifiedUsrId"))
			return getPdfModifiedUsrId();
		if (propertyName.equals("pdfCreatedDttm"))
			return getPdfCreatedDttm();
		if (propertyName.equals("pdfModifiedDttm"))
			return getPdfModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getPdfId()
	{
		return pdfId;
	}

	public void setPdfId(Integer pdfId)
	{
		this.pdfId = pdfId;
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

	public String getPdfName()
	{
		return pdfName;
	}

	public void setPdfName(String pdfName)
	{
		this.pdfName = pdfName;
	}

	public String getPdfTableName()
	{
		return pdfTableName;
	}

	public void setPdfTableName(String pdfTableName)
	{
		this.pdfTableName = pdfTableName;
	}

	public boolean getPdfPollFl()
	{
		return pdfPollFl;
	}

	public void setPdfPollFl(boolean pdfPollFl)
	{
		this.pdfPollFl = pdfPollFl;
	}

	public Integer getJobId()
	{
		return jobId;
	}

	public void setJobId(Integer jobId)
	{
		this.jobId = jobId;
	}

	public String getPdfBk()
	{
		return pdfBk;
	}

	public void setPdfBk(String pdfBk)
	{
		this.pdfBk = pdfBk;
	}

	public Integer getPdfCreatedUsrId()
	{
		return pdfCreatedUsrId;
	}

	public void setPdfCreatedUsrId(Integer pdfCreatedUsrId)
	{
		this.pdfCreatedUsrId = pdfCreatedUsrId;
	}

	public Integer getPdfModifiedUsrId()
	{
		return pdfModifiedUsrId;
	}

	public void setPdfModifiedUsrId(Integer pdfModifiedUsrId)
	{
		this.pdfModifiedUsrId = pdfModifiedUsrId;
	}

	public DateTime getPdfCreatedDttm()
	{
		return pdfCreatedDttm;
	}

	public void setPdfCreatedDttm(DateTime pdfCreatedDttm)
	{
		this.pdfCreatedDttm = pdfCreatedDttm;
	}

	public DateTime getPdfModifiedDttm()
	{
		return pdfModifiedDttm;
	}

	public void setPdfModifiedDttm(DateTime pdfModifiedDttm)
	{
		this.pdfModifiedDttm = pdfModifiedDttm;
	}

}
