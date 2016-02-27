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
import se.signa.signature.gen.dba.ParsedFileDba;
import se.signa.signature.gen.dba.UserTblDba;
import se.signa.signature.helpers.DisplayFieldHelper;

public abstract class ParsedFile extends SignatureDbo
{
	private Integer pfiId;
	private Integer filId;
	private Long pfiChecksum;
	private String pfiName;
	private Integer pfiDupPfiId;
	private String pfiBk;
	private Integer pfiCreatedUsrId;
	private Integer pfiModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime pfiCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime pfiModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			pfiId = getInteger(rs.getInt("pfi_id"));
			filId = getInteger(rs.getInt("fil_id"));
			pfiChecksum = rs.getLong("pfi_checksum");
			pfiName = rs.getString("pfi_name");
			pfiDupPfiId = getInteger(rs.getInt("pfi_dup_pfi_id"));
			pfiBk = rs.getString("pfi_bk");
			pfiCreatedUsrId = getInteger(rs.getInt("pfi_created_usr_id"));
			pfiModifiedUsrId = getInteger(rs.getInt("pfi_modified_usr_id"));
			pfiCreatedDttm = getDateTime(rs.getTimestamp("pfi_created_dttm"));
			pfiModifiedDttm = getDateTime(rs.getTimestamp("pfi_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return pfiId;
	}

	public void setPk(int pk)
	{
		pfiId = pk;
	}

	public String getBk()
	{
		return pfiBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(pfiId.toString());
		data.add(filId.toString());
		data.add(pfiChecksum.toString());
		data.add(pfiName.toString());
		data.add(pfiDupPfiId == null ? Constants.NULLSTRING : pfiDupPfiId.toString());
		data.add(getPfiCreatedUsr());
		data.add(Constants.dttf.print(pfiCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("fil", getFil());
		data.put("pfiChecksum", getString(pfiChecksum));
		data.put("pfiName", getString(pfiName));
		data.put("pfiDupPfi", getPfiDupPfi());
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setFilId(DisplayFieldHelper.getI().getPk(FiletblDba.class, input.getString("fil")));
		setPfiChecksum(input.getLong("pfiChecksum"));
		setPfiName(input.getString("pfiName"));
		setPfiDupPfiId(DisplayFieldHelper.getI().getPk(ParsedFileDba.class, input.getString("pfiDupPfi")));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(pfiBk.toString());
		auditData.add(getPfiCreatedUsr());
		auditData.add(pfiModifiedUsrId == null ? Constants.NULLSTRING : getPfiModifiedUsr());
		auditData.add(Constants.dttf.print(pfiCreatedDttm));
		auditData.add(pfiModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(pfiModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return pfiName;
	}

	public String getFil()
	{
		return DisplayFieldHelper.getI().getDisplayField(FiletblDba.class, filId);
	}

	public String getPfiDupPfi()
	{
		return DisplayFieldHelper.getI().getDisplayField(ParsedFileDba.class, pfiDupPfiId);
	}

	public String getPfiCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, pfiCreatedUsrId);
	}

	public String getPfiModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, pfiModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("fil"))
			return getFil();
		if (propertyName.equals("pfiDupPfi"))
			return getPfiDupPfi();
		if (propertyName.equals("pfiCreatedUsr"))
			return getPfiCreatedUsr();
		if (propertyName.equals("pfiModifiedUsr"))
			return getPfiModifiedUsr();
		if (propertyName.equals("pfiId"))
			return getPfiId();
		if (propertyName.equals("filId"))
			return getFilId();
		if (propertyName.equals("pfiChecksum"))
			return getPfiChecksum();
		if (propertyName.equals("pfiName"))
			return getPfiName();
		if (propertyName.equals("pfiDupPfiId"))
			return getPfiDupPfiId();
		if (propertyName.equals("pfiBk"))
			return getPfiBk();
		if (propertyName.equals("pfiCreatedUsrId"))
			return getPfiCreatedUsrId();
		if (propertyName.equals("pfiModifiedUsrId"))
			return getPfiModifiedUsrId();
		if (propertyName.equals("pfiCreatedDttm"))
			return getPfiCreatedDttm();
		if (propertyName.equals("pfiModifiedDttm"))
			return getPfiModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getPfiId()
	{
		return pfiId;
	}

	public void setPfiId(Integer pfiId)
	{
		this.pfiId = pfiId;
	}

	public Integer getFilId()
	{
		return filId;
	}

	public void setFilId(Integer filId)
	{
		this.filId = filId;
	}

	public Long getPfiChecksum()
	{
		return pfiChecksum;
	}

	public void setPfiChecksum(Long pfiChecksum)
	{
		this.pfiChecksum = pfiChecksum;
	}

	public String getPfiName()
	{
		return pfiName;
	}

	public void setPfiName(String pfiName)
	{
		this.pfiName = pfiName;
	}

	public Integer getPfiDupPfiId()
	{
		return pfiDupPfiId;
	}

	public void setPfiDupPfiId(Integer pfiDupPfiId)
	{
		this.pfiDupPfiId = pfiDupPfiId;
	}

	public String getPfiBk()
	{
		return pfiBk;
	}

	public void setPfiBk(String pfiBk)
	{
		this.pfiBk = pfiBk;
	}

	public Integer getPfiCreatedUsrId()
	{
		return pfiCreatedUsrId;
	}

	public void setPfiCreatedUsrId(Integer pfiCreatedUsrId)
	{
		this.pfiCreatedUsrId = pfiCreatedUsrId;
	}

	public Integer getPfiModifiedUsrId()
	{
		return pfiModifiedUsrId;
	}

	public void setPfiModifiedUsrId(Integer pfiModifiedUsrId)
	{
		this.pfiModifiedUsrId = pfiModifiedUsrId;
	}

	public DateTime getPfiCreatedDttm()
	{
		return pfiCreatedDttm;
	}

	public void setPfiCreatedDttm(DateTime pfiCreatedDttm)
	{
		this.pfiCreatedDttm = pfiCreatedDttm;
	}

	public DateTime getPfiModifiedDttm()
	{
		return pfiModifiedDttm;
	}

	public void setPfiModifiedDttm(DateTime pfiModifiedDttm)
	{
		this.pfiModifiedDttm = pfiModifiedDttm;
	}

}
