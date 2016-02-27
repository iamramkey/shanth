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

public abstract class FileParserSettings extends SignatureDbo
{
	private Integer fasId;
	private String fasName;
	private String fasTargetDir;
	private Integer fasParserJbtId;
	private boolean fasProcessDuplicate;
	private String fasBk;
	private Integer fasCreatedUsrId;
	private Integer fasModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime fasCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime fasModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			fasId = getInteger(rs.getInt("fas_id"));
			fasName = rs.getString("fas_name");
			fasTargetDir = rs.getString("fas_target_dir");
			fasParserJbtId = getInteger(rs.getInt("fas_parser_jbt_id"));
			fasProcessDuplicate = getBoolean(rs.getString("fas_process_duplicate"));
			fasBk = rs.getString("fas_bk");
			fasCreatedUsrId = getInteger(rs.getInt("fas_created_usr_id"));
			fasModifiedUsrId = getInteger(rs.getInt("fas_modified_usr_id"));
			fasCreatedDttm = getDateTime(rs.getTimestamp("fas_created_dttm"));
			fasModifiedDttm = getDateTime(rs.getTimestamp("fas_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return fasId;
	}

	public void setPk(int pk)
	{
		fasId = pk;
	}

	public String getBk()
	{
		return fasBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(fasId.toString());
		data.add(fasName.toString());
		data.add(fasTargetDir.toString());
		data.add(fasParserJbtId.toString());
		data.add(String.valueOf(fasProcessDuplicate));
		data.add(getFasCreatedUsr());
		data.add(Constants.dttf.print(fasCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("fasName", getString(fasName));
		data.put("fasTargetDir", getString(fasTargetDir));
		data.put("fasParserJbt", getFasParserJbt());
		data.put("fasProcessDuplicate", getString(fasProcessDuplicate));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setFasName(input.getString("fasName"));
		setFasTargetDir(input.getString("fasTargetDir"));
		setFasParserJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("fasParserJbt")));
		setFasProcessDuplicate(input.getBoolean("fasProcessDuplicate"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(fasBk.toString());
		auditData.add(getFasCreatedUsr());
		auditData.add(fasModifiedUsrId == null ? Constants.NULLSTRING : getFasModifiedUsr());
		auditData.add(Constants.dttf.print(fasCreatedDttm));
		auditData.add(fasModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(fasModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return fasName;
	}

	public String getFasParserJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, fasParserJbtId);
	}

	public String getFasCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, fasCreatedUsrId);
	}

	public String getFasModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, fasModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("fasParserJbt"))
			return getFasParserJbt();
		if (propertyName.equals("fasCreatedUsr"))
			return getFasCreatedUsr();
		if (propertyName.equals("fasModifiedUsr"))
			return getFasModifiedUsr();
		if (propertyName.equals("fasId"))
			return getFasId();
		if (propertyName.equals("fasName"))
			return getFasName();
		if (propertyName.equals("fasTargetDir"))
			return getFasTargetDir();
		if (propertyName.equals("fasParserJbtId"))
			return getFasParserJbtId();
		if (propertyName.equals("fasProcessDuplicate"))
			return getFasProcessDuplicate();
		if (propertyName.equals("fasBk"))
			return getFasBk();
		if (propertyName.equals("fasCreatedUsrId"))
			return getFasCreatedUsrId();
		if (propertyName.equals("fasModifiedUsrId"))
			return getFasModifiedUsrId();
		if (propertyName.equals("fasCreatedDttm"))
			return getFasCreatedDttm();
		if (propertyName.equals("fasModifiedDttm"))
			return getFasModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getFasId()
	{
		return fasId;
	}

	public void setFasId(Integer fasId)
	{
		this.fasId = fasId;
	}

	public String getFasName()
	{
		return fasName;
	}

	public void setFasName(String fasName)
	{
		this.fasName = fasName;
	}

	public String getFasTargetDir()
	{
		return fasTargetDir;
	}

	public void setFasTargetDir(String fasTargetDir)
	{
		this.fasTargetDir = fasTargetDir;
	}

	public Integer getFasParserJbtId()
	{
		return fasParserJbtId;
	}

	public void setFasParserJbtId(Integer fasParserJbtId)
	{
		this.fasParserJbtId = fasParserJbtId;
	}

	public boolean getFasProcessDuplicate()
	{
		return fasProcessDuplicate;
	}

	public void setFasProcessDuplicate(boolean fasProcessDuplicate)
	{
		this.fasProcessDuplicate = fasProcessDuplicate;
	}

	public String getFasBk()
	{
		return fasBk;
	}

	public void setFasBk(String fasBk)
	{
		this.fasBk = fasBk;
	}

	public Integer getFasCreatedUsrId()
	{
		return fasCreatedUsrId;
	}

	public void setFasCreatedUsrId(Integer fasCreatedUsrId)
	{
		this.fasCreatedUsrId = fasCreatedUsrId;
	}

	public Integer getFasModifiedUsrId()
	{
		return fasModifiedUsrId;
	}

	public void setFasModifiedUsrId(Integer fasModifiedUsrId)
	{
		this.fasModifiedUsrId = fasModifiedUsrId;
	}

	public DateTime getFasCreatedDttm()
	{
		return fasCreatedDttm;
	}

	public void setFasCreatedDttm(DateTime fasCreatedDttm)
	{
		this.fasCreatedDttm = fasCreatedDttm;
	}

	public DateTime getFasModifiedDttm()
	{
		return fasModifiedDttm;
	}

	public void setFasModifiedDttm(DateTime fasModifiedDttm)
	{
		this.fasModifiedDttm = fasModifiedDttm;
	}

}
