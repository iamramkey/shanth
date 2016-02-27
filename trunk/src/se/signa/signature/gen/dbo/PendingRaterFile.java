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
import se.signa.signature.gen.dba.JobTypeDba;
import se.signa.signature.gen.dba.UserTblDba;
import se.signa.signature.helpers.DisplayFieldHelper;

public abstract class PendingRaterFile extends SignatureDbo
{
	private Integer praId;
	private Integer praTransferJbtId;
	private Integer filId;
	private Integer accId;
	private String praName;
	private String praBk;
	private Integer praCreatedUsrId;
	private Integer praModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime praCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime praModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			praId = getInteger(rs.getInt("pra_id"));
			praTransferJbtId = getInteger(rs.getInt("pra_transfer_jbt_id"));
			filId = getInteger(rs.getInt("fil_id"));
			accId = getInteger(rs.getInt("acc_id"));
			praName = rs.getString("pra_name");
			praBk = rs.getString("pra_bk");
			praCreatedUsrId = getInteger(rs.getInt("pra_created_usr_id"));
			praModifiedUsrId = getInteger(rs.getInt("pra_modified_usr_id"));
			praCreatedDttm = getDateTime(rs.getTimestamp("pra_created_dttm"));
			praModifiedDttm = getDateTime(rs.getTimestamp("pra_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return praId;
	}

	public void setPk(int pk)
	{
		praId = pk;
	}

	public String getBk()
	{
		return praBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(praId.toString());
		data.add(praTransferJbtId.toString());
		data.add(filId.toString());
		data.add(accId.toString());
		data.add(praName.toString());
		data.add(getPraCreatedUsr());
		data.add(Constants.dttf.print(praCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("praTransferJbt", getPraTransferJbt());
		data.put("fil", getFil());
		data.put("accId", getAccId());
		data.put("praName", getString(praName));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setPraTransferJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("praTransferJbt")));
		setFilId(DisplayFieldHelper.getI().getPk(FiletblDba.class, input.getString("fil")));
		setPraName(input.getString("praName"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(praBk.toString());
		auditData.add(getPraCreatedUsr());
		auditData.add(praModifiedUsrId == null ? Constants.NULLSTRING : getPraModifiedUsr());
		auditData.add(Constants.dttf.print(praCreatedDttm));
		auditData.add(praModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(praModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return praName;
	}

	public String getPraTransferJbt()
	{
		return DisplayFieldHelper.getI().getDisplayField(JobTypeDba.class, praTransferJbtId);
	}

	public String getFil()
	{
		return DisplayFieldHelper.getI().getDisplayField(FiletblDba.class, filId);
	}

	public String getPraCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, praCreatedUsrId);
	}

	public String getPraModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, praModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("praTransferJbt"))
			return getPraTransferJbt();
		if (propertyName.equals("fil"))
			return getFil();
		if (propertyName.equals("praCreatedUsr"))
			return getPraCreatedUsr();
		if (propertyName.equals("praModifiedUsr"))
			return getPraModifiedUsr();
		if (propertyName.equals("praId"))
			return getPraId();
		if (propertyName.equals("praTransferJbtId"))
			return getPraTransferJbtId();
		if (propertyName.equals("filId"))
			return getFilId();
		if (propertyName.equals("accId"))
			return getAccId();
		if (propertyName.equals("praName"))
			return getPraName();
		if (propertyName.equals("praBk"))
			return getPraBk();
		if (propertyName.equals("praCreatedUsrId"))
			return getPraCreatedUsrId();
		if (propertyName.equals("praModifiedUsrId"))
			return getPraModifiedUsrId();
		if (propertyName.equals("praCreatedDttm"))
			return getPraCreatedDttm();
		if (propertyName.equals("praModifiedDttm"))
			return getPraModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getPraId()
	{
		return praId;
	}

	public void setPraId(Integer praId)
	{
		this.praId = praId;
	}

	public Integer getPraTransferJbtId()
	{
		return praTransferJbtId;
	}

	public void setPraTransferJbtId(Integer praTransferJbtId)
	{
		this.praTransferJbtId = praTransferJbtId;
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

	public String getPraName()
	{
		return praName;
	}

	public void setPraName(String praName)
	{
		this.praName = praName;
	}

	public String getPraBk()
	{
		return praBk;
	}

	public void setPraBk(String praBk)
	{
		this.praBk = praBk;
	}

	public Integer getPraCreatedUsrId()
	{
		return praCreatedUsrId;
	}

	public void setPraCreatedUsrId(Integer praCreatedUsrId)
	{
		this.praCreatedUsrId = praCreatedUsrId;
	}

	public Integer getPraModifiedUsrId()
	{
		return praModifiedUsrId;
	}

	public void setPraModifiedUsrId(Integer praModifiedUsrId)
	{
		this.praModifiedUsrId = praModifiedUsrId;
	}

	public DateTime getPraCreatedDttm()
	{
		return praCreatedDttm;
	}

	public void setPraCreatedDttm(DateTime praCreatedDttm)
	{
		this.praCreatedDttm = praCreatedDttm;
	}

	public DateTime getPraModifiedDttm()
	{
		return praModifiedDttm;
	}

	public void setPraModifiedDttm(DateTime praModifiedDttm)
	{
		this.praModifiedDttm = praModifiedDttm;
	}

}
