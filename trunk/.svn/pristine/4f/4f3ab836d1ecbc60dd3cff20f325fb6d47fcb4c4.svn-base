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
import se.signa.signature.gen.dba.MasterSearchDba;
import se.signa.signature.gen.dba.UserTblDba;
import se.signa.signature.helpers.DisplayFieldHelper;

public abstract class MasterSearchAction extends SignatureDbo
{
	private Integer msaId;
	private Integer mseId;
	private String msaType;
	private String msaAction;
	private String msaName;
	private String msaColour;
	private String msaIcon;
	private String msaVisible;
	private String msaFunction;
	private String msaParam;
	private String msaCode;
	private String msaBk;
	private Integer msaCreatedUsrId;
	private Integer msaModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime msaCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime msaModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			msaId = getInteger(rs.getInt("msa_id"));
			mseId = getInteger(rs.getInt("mse_id"));
			msaType = rs.getString("msa_type");
			msaAction = rs.getString("msa_action");
			msaName = rs.getString("msa_name");
			msaColour = rs.getString("msa_colour");
			msaIcon = rs.getString("msa_icon");
			msaVisible = rs.getString("msa_visible");
			msaFunction = rs.getString("msa_function");
			msaParam = rs.getString("msa_param");
			msaCode = rs.getString("msa_code");
			msaBk = rs.getString("msa_bk");
			msaCreatedUsrId = getInteger(rs.getInt("msa_created_usr_id"));
			msaModifiedUsrId = getInteger(rs.getInt("msa_modified_usr_id"));
			msaCreatedDttm = getDateTime(rs.getTimestamp("msa_created_dttm"));
			msaModifiedDttm = getDateTime(rs.getTimestamp("msa_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return msaId;
	}

	public void setPk(int pk)
	{
		msaId = pk;
	}

	public String getBk()
	{
		return msaBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(msaId.toString());
		data.add(mseId.toString());
		data.add(msaType.toString());
		data.add(msaAction.toString());
		data.add(msaName.toString());
		data.add(msaColour.toString());
		data.add(msaIcon.toString());
		data.add(msaVisible == null ? Constants.NULLSTRING : msaVisible.toString());
		data.add(msaFunction == null ? Constants.NULLSTRING : msaFunction.toString());
		data.add(msaParam == null ? Constants.NULLSTRING : msaParam.toString());
		data.add(msaCode.toString());
		data.add(getMsaCreatedUsr());
		data.add(Constants.dttf.print(msaCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("mse", getMse());
		data.put("msaType", getString(msaType));
		data.put("msaAction", getString(msaAction));
		data.put("msaName", getString(msaName));
		data.put("msaColour", getString(msaColour));
		data.put("msaIcon", getString(msaIcon));
		data.put("msaVisible", getString(msaVisible));
		data.put("msaFunction", getString(msaFunction));
		data.put("msaParam", getString(msaParam));
		data.put("msaCode", getString(msaCode));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setMseId(DisplayFieldHelper.getI().getPk(MasterSearchDba.class, input.getString("mse")));
		setMsaType(input.getString("msaType"));
		setMsaAction(input.getString("msaAction"));
		setMsaName(input.getString("msaName"));
		setMsaColour(input.getString("msaColour"));
		setMsaIcon(input.getString("msaIcon"));
		setMsaVisible(input.getString("msaVisible"));
		setMsaFunction(input.getString("msaFunction"));
		setMsaParam(input.getString("msaParam"));
		setMsaCode(input.getString("msaCode"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(msaBk.toString());
		auditData.add(getMsaCreatedUsr());
		auditData.add(msaModifiedUsrId == null ? Constants.NULLSTRING : getMsaModifiedUsr());
		auditData.add(Constants.dttf.print(msaCreatedDttm));
		auditData.add(msaModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(msaModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return msaCode;
	}

	public String getMse()
	{
		return DisplayFieldHelper.getI().getDisplayField(MasterSearchDba.class, mseId);
	}

	public String getMsaCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, msaCreatedUsrId);
	}

	public String getMsaModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, msaModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("mse"))
			return getMse();
		if (propertyName.equals("msaCreatedUsr"))
			return getMsaCreatedUsr();
		if (propertyName.equals("msaModifiedUsr"))
			return getMsaModifiedUsr();
		if (propertyName.equals("msaId"))
			return getMsaId();
		if (propertyName.equals("mseId"))
			return getMseId();
		if (propertyName.equals("msaType"))
			return getMsaType();
		if (propertyName.equals("msaAction"))
			return getMsaAction();
		if (propertyName.equals("msaName"))
			return getMsaName();
		if (propertyName.equals("msaColour"))
			return getMsaColour();
		if (propertyName.equals("msaIcon"))
			return getMsaIcon();
		if (propertyName.equals("msaVisible"))
			return getMsaVisible();
		if (propertyName.equals("msaFunction"))
			return getMsaFunction();
		if (propertyName.equals("msaParam"))
			return getMsaParam();
		if (propertyName.equals("msaCode"))
			return getMsaCode();
		if (propertyName.equals("msaBk"))
			return getMsaBk();
		if (propertyName.equals("msaCreatedUsrId"))
			return getMsaCreatedUsrId();
		if (propertyName.equals("msaModifiedUsrId"))
			return getMsaModifiedUsrId();
		if (propertyName.equals("msaCreatedDttm"))
			return getMsaCreatedDttm();
		if (propertyName.equals("msaModifiedDttm"))
			return getMsaModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getMsaId()
	{
		return msaId;
	}

	public void setMsaId(Integer msaId)
	{
		this.msaId = msaId;
	}

	public Integer getMseId()
	{
		return mseId;
	}

	public void setMseId(Integer mseId)
	{
		this.mseId = mseId;
	}

	public String getMsaType()
	{
		return msaType;
	}

	public void setMsaType(String msaType)
	{
		this.msaType = msaType;
	}

	public String getMsaAction()
	{
		return msaAction;
	}

	public void setMsaAction(String msaAction)
	{
		this.msaAction = msaAction;
	}

	public String getMsaName()
	{
		return msaName;
	}

	public void setMsaName(String msaName)
	{
		this.msaName = msaName;
	}

	public String getMsaColour()
	{
		return msaColour;
	}

	public void setMsaColour(String msaColour)
	{
		this.msaColour = msaColour;
	}

	public String getMsaIcon()
	{
		return msaIcon;
	}

	public void setMsaIcon(String msaIcon)
	{
		this.msaIcon = msaIcon;
	}

	public String getMsaVisible()
	{
		return msaVisible;
	}

	public void setMsaVisible(String msaVisible)
	{
		this.msaVisible = msaVisible;
	}

	public String getMsaFunction()
	{
		return msaFunction;
	}

	public void setMsaFunction(String msaFunction)
	{
		this.msaFunction = msaFunction;
	}

	public String getMsaParam()
	{
		return msaParam;
	}

	public void setMsaParam(String msaParam)
	{
		this.msaParam = msaParam;
	}

	public String getMsaCode()
	{
		return msaCode;
	}

	public void setMsaCode(String msaCode)
	{
		this.msaCode = msaCode;
	}

	public String getMsaBk()
	{
		return msaBk;
	}

	public void setMsaBk(String msaBk)
	{
		this.msaBk = msaBk;
	}

	public Integer getMsaCreatedUsrId()
	{
		return msaCreatedUsrId;
	}

	public void setMsaCreatedUsrId(Integer msaCreatedUsrId)
	{
		this.msaCreatedUsrId = msaCreatedUsrId;
	}

	public Integer getMsaModifiedUsrId()
	{
		return msaModifiedUsrId;
	}

	public void setMsaModifiedUsrId(Integer msaModifiedUsrId)
	{
		this.msaModifiedUsrId = msaModifiedUsrId;
	}

	public DateTime getMsaCreatedDttm()
	{
		return msaCreatedDttm;
	}

	public void setMsaCreatedDttm(DateTime msaCreatedDttm)
	{
		this.msaCreatedDttm = msaCreatedDttm;
	}

	public DateTime getMsaModifiedDttm()
	{
		return msaModifiedDttm;
	}

	public void setMsaModifiedDttm(DateTime msaModifiedDttm)
	{
		this.msaModifiedDttm = msaModifiedDttm;
	}

}
