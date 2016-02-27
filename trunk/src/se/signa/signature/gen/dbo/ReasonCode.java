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

public abstract class ReasonCode extends SignatureDbo
{
	private Integer recId;
	private String recName;
	private Integer recCode;
	private String recBk;
	private Integer recCreatedUsrId;
	private Integer recModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime recCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime recModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			recId = getInteger(rs.getInt("rec_id"));
			recName = rs.getString("rec_name");
			recCode = rs.getInt("rec_code");
			recBk = rs.getString("rec_bk");
			recCreatedUsrId = getInteger(rs.getInt("rec_created_usr_id"));
			recModifiedUsrId = getInteger(rs.getInt("rec_modified_usr_id"));
			recCreatedDttm = getDateTime(rs.getTimestamp("rec_created_dttm"));
			recModifiedDttm = getDateTime(rs.getTimestamp("rec_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return recId;
	}

	public void setPk(int pk)
	{
		recId = pk;
	}

	public String getBk()
	{
		return recBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(recId.toString());
		data.add(recName.toString());
		data.add(recCode.toString());
		data.add(getRecCreatedUsr());
		data.add(Constants.dttf.print(recCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("recName", getString(recName));
		data.put("recCode", getString(recCode));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setRecName(input.getString("recName"));
		setRecCode(input.getInteger("recCode"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(recBk.toString());
		auditData.add(getRecCreatedUsr());
		auditData.add(recModifiedUsrId == null ? Constants.NULLSTRING : getRecModifiedUsr());
		auditData.add(Constants.dttf.print(recCreatedDttm));
		auditData.add(recModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(recModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return recName;
	}

	public String getRecCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, recCreatedUsrId);
	}

	public String getRecModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, recModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("recCreatedUsr"))
			return getRecCreatedUsr();
		if (propertyName.equals("recModifiedUsr"))
			return getRecModifiedUsr();
		if (propertyName.equals("recId"))
			return getRecId();
		if (propertyName.equals("recName"))
			return getRecName();
		if (propertyName.equals("recCode"))
			return getRecCode();
		if (propertyName.equals("recBk"))
			return getRecBk();
		if (propertyName.equals("recCreatedUsrId"))
			return getRecCreatedUsrId();
		if (propertyName.equals("recModifiedUsrId"))
			return getRecModifiedUsrId();
		if (propertyName.equals("recCreatedDttm"))
			return getRecCreatedDttm();
		if (propertyName.equals("recModifiedDttm"))
			return getRecModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getRecId()
	{
		return recId;
	}

	public void setRecId(Integer recId)
	{
		this.recId = recId;
	}

	public String getRecName()
	{
		return recName;
	}

	public void setRecName(String recName)
	{
		this.recName = recName;
	}

	public Integer getRecCode()
	{
		return recCode;
	}

	public void setRecCode(Integer recCode)
	{
		this.recCode = recCode;
	}

	public String getRecBk()
	{
		return recBk;
	}

	public void setRecBk(String recBk)
	{
		this.recBk = recBk;
	}

	public Integer getRecCreatedUsrId()
	{
		return recCreatedUsrId;
	}

	public void setRecCreatedUsrId(Integer recCreatedUsrId)
	{
		this.recCreatedUsrId = recCreatedUsrId;
	}

	public Integer getRecModifiedUsrId()
	{
		return recModifiedUsrId;
	}

	public void setRecModifiedUsrId(Integer recModifiedUsrId)
	{
		this.recModifiedUsrId = recModifiedUsrId;
	}

	public DateTime getRecCreatedDttm()
	{
		return recCreatedDttm;
	}

	public void setRecCreatedDttm(DateTime recCreatedDttm)
	{
		this.recCreatedDttm = recCreatedDttm;
	}

	public DateTime getRecModifiedDttm()
	{
		return recModifiedDttm;
	}

	public void setRecModifiedDttm(DateTime recModifiedDttm)
	{
		this.recModifiedDttm = recModifiedDttm;
	}

}
