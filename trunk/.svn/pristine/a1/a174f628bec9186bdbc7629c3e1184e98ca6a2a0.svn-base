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

public abstract class PendingCorrelation extends SignatureDbo
{
	private Integer pcoId;
	private String pcoName;
	private String pcoPartition;
	private String pcoBk;
	private Integer pcoCreatedUsrId;
	private Integer pcoModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime pcoCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime pcoModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			pcoId = getInteger(rs.getInt("pco_id"));
			pcoName = rs.getString("pco_name");
			pcoPartition = rs.getString("pco_partition");
			pcoBk = rs.getString("pco_bk");
			pcoCreatedUsrId = getInteger(rs.getInt("pco_created_usr_id"));
			pcoModifiedUsrId = getInteger(rs.getInt("pco_modified_usr_id"));
			pcoCreatedDttm = getDateTime(rs.getTimestamp("pco_created_dttm"));
			pcoModifiedDttm = getDateTime(rs.getTimestamp("pco_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return pcoId;
	}

	public void setPk(int pk)
	{
		pcoId = pk;
	}

	public String getBk()
	{
		return pcoBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(pcoId.toString());
		data.add(pcoName.toString());
		data.add(pcoPartition.toString());
		data.add(getPcoCreatedUsr());
		data.add(Constants.dttf.print(pcoCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("pcoName", getString(pcoName));
		data.put("pcoPartition", getString(pcoPartition));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setPcoName(input.getString("pcoName"));
		setPcoPartition(input.getString("pcoPartition"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(pcoBk.toString());
		auditData.add(getPcoCreatedUsr());
		auditData.add(pcoModifiedUsrId == null ? Constants.NULLSTRING : getPcoModifiedUsr());
		auditData.add(Constants.dttf.print(pcoCreatedDttm));
		auditData.add(pcoModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(pcoModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return pcoName;
	}

	public String getPcoCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, pcoCreatedUsrId);
	}

	public String getPcoModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, pcoModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("pcoCreatedUsr"))
			return getPcoCreatedUsr();
		if (propertyName.equals("pcoModifiedUsr"))
			return getPcoModifiedUsr();
		if (propertyName.equals("pcoId"))
			return getPcoId();
		if (propertyName.equals("pcoName"))
			return getPcoName();
		if (propertyName.equals("pcoPartition"))
			return getPcoPartition();
		if (propertyName.equals("pcoBk"))
			return getPcoBk();
		if (propertyName.equals("pcoCreatedUsrId"))
			return getPcoCreatedUsrId();
		if (propertyName.equals("pcoModifiedUsrId"))
			return getPcoModifiedUsrId();
		if (propertyName.equals("pcoCreatedDttm"))
			return getPcoCreatedDttm();
		if (propertyName.equals("pcoModifiedDttm"))
			return getPcoModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getPcoId()
	{
		return pcoId;
	}

	public void setPcoId(Integer pcoId)
	{
		this.pcoId = pcoId;
	}

	public String getPcoName()
	{
		return pcoName;
	}

	public void setPcoName(String pcoName)
	{
		this.pcoName = pcoName;
	}

	public String getPcoPartition()
	{
		return pcoPartition;
	}

	public void setPcoPartition(String pcoPartition)
	{
		this.pcoPartition = pcoPartition;
	}

	public String getPcoBk()
	{
		return pcoBk;
	}

	public void setPcoBk(String pcoBk)
	{
		this.pcoBk = pcoBk;
	}

	public Integer getPcoCreatedUsrId()
	{
		return pcoCreatedUsrId;
	}

	public void setPcoCreatedUsrId(Integer pcoCreatedUsrId)
	{
		this.pcoCreatedUsrId = pcoCreatedUsrId;
	}

	public Integer getPcoModifiedUsrId()
	{
		return pcoModifiedUsrId;
	}

	public void setPcoModifiedUsrId(Integer pcoModifiedUsrId)
	{
		this.pcoModifiedUsrId = pcoModifiedUsrId;
	}

	public DateTime getPcoCreatedDttm()
	{
		return pcoCreatedDttm;
	}

	public void setPcoCreatedDttm(DateTime pcoCreatedDttm)
	{
		this.pcoCreatedDttm = pcoCreatedDttm;
	}

	public DateTime getPcoModifiedDttm()
	{
		return pcoModifiedDttm;
	}

	public void setPcoModifiedDttm(DateTime pcoModifiedDttm)
	{
		this.pcoModifiedDttm = pcoModifiedDttm;
	}

}
