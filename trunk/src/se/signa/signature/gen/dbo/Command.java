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
import se.signa.signature.gen.dba.CommandTypeDba;
import se.signa.signature.gen.dba.NodeDba;
import se.signa.signature.gen.dba.UserTblDba;
import se.signa.signature.helpers.DisplayFieldHelper;

public abstract class Command extends SignatureDbo
{
	private Integer cmdId;
	private Integer nodId;
	private Integer ctpId;
	private String cmdName;
	private String cmdExtra1;
	private String cmdExtra2;
	private String cmdExtra3;
	private String cmdBk;
	private Integer cmdCreatedUsrId;
	private Integer cmdModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime cmdCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime cmdModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			cmdId = getInteger(rs.getInt("cmd_id"));
			nodId = getInteger(rs.getInt("nod_id"));
			ctpId = getInteger(rs.getInt("ctp_id"));
			cmdName = rs.getString("cmd_name");
			cmdExtra1 = rs.getString("cmd_extra1");
			cmdExtra2 = rs.getString("cmd_extra2");
			cmdExtra3 = rs.getString("cmd_extra3");
			cmdBk = rs.getString("cmd_bk");
			cmdCreatedUsrId = getInteger(rs.getInt("cmd_created_usr_id"));
			cmdModifiedUsrId = getInteger(rs.getInt("cmd_modified_usr_id"));
			cmdCreatedDttm = getDateTime(rs.getTimestamp("cmd_created_dttm"));
			cmdModifiedDttm = getDateTime(rs.getTimestamp("cmd_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return cmdId;
	}

	public void setPk(int pk)
	{
		cmdId = pk;
	}

	public String getBk()
	{
		return cmdBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(cmdId.toString());
		data.add(nodId.toString());
		data.add(ctpId.toString());
		data.add(cmdName.toString());
		data.add(cmdExtra1 == null ? Constants.NULLSTRING : cmdExtra1.toString());
		data.add(cmdExtra2 == null ? Constants.NULLSTRING : cmdExtra2.toString());
		data.add(cmdExtra3 == null ? Constants.NULLSTRING : cmdExtra3.toString());
		data.add(getCmdCreatedUsr());
		data.add(Constants.dttf.print(cmdCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("nod", getNod());
		data.put("ctp", getCtp());
		data.put("cmdName", getString(cmdName));
		data.put("cmdExtra1", getString(cmdExtra1));
		data.put("cmdExtra2", getString(cmdExtra2));
		data.put("cmdExtra3", getString(cmdExtra3));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setNodId(DisplayFieldHelper.getI().getPk(NodeDba.class, input.getString("nod")));
		setCtpId(DisplayFieldHelper.getI().getPk(CommandTypeDba.class, input.getString("ctp")));
		setCmdName(input.getString("cmdName"));
		setCmdExtra1(input.getString("cmdExtra1"));
		setCmdExtra2(input.getString("cmdExtra2"));
		setCmdExtra3(input.getString("cmdExtra3"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(cmdBk.toString());
		auditData.add(getCmdCreatedUsr());
		auditData.add(cmdModifiedUsrId == null ? Constants.NULLSTRING : getCmdModifiedUsr());
		auditData.add(Constants.dttf.print(cmdCreatedDttm));
		auditData.add(cmdModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(cmdModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return cmdName;
	}

	public String getNod()
	{
		return DisplayFieldHelper.getI().getDisplayField(NodeDba.class, nodId);
	}

	public String getCtp()
	{
		return DisplayFieldHelper.getI().getDisplayField(CommandTypeDba.class, ctpId);
	}

	public String getCmdCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, cmdCreatedUsrId);
	}

	public String getCmdModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, cmdModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("nod"))
			return getNod();
		if (propertyName.equals("ctp"))
			return getCtp();
		if (propertyName.equals("cmdCreatedUsr"))
			return getCmdCreatedUsr();
		if (propertyName.equals("cmdModifiedUsr"))
			return getCmdModifiedUsr();
		if (propertyName.equals("cmdId"))
			return getCmdId();
		if (propertyName.equals("nodId"))
			return getNodId();
		if (propertyName.equals("ctpId"))
			return getCtpId();
		if (propertyName.equals("cmdName"))
			return getCmdName();
		if (propertyName.equals("cmdExtra1"))
			return getCmdExtra1();
		if (propertyName.equals("cmdExtra2"))
			return getCmdExtra2();
		if (propertyName.equals("cmdExtra3"))
			return getCmdExtra3();
		if (propertyName.equals("cmdBk"))
			return getCmdBk();
		if (propertyName.equals("cmdCreatedUsrId"))
			return getCmdCreatedUsrId();
		if (propertyName.equals("cmdModifiedUsrId"))
			return getCmdModifiedUsrId();
		if (propertyName.equals("cmdCreatedDttm"))
			return getCmdCreatedDttm();
		if (propertyName.equals("cmdModifiedDttm"))
			return getCmdModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getCmdId()
	{
		return cmdId;
	}

	public void setCmdId(Integer cmdId)
	{
		this.cmdId = cmdId;
	}

	public Integer getNodId()
	{
		return nodId;
	}

	public void setNodId(Integer nodId)
	{
		this.nodId = nodId;
	}

	public Integer getCtpId()
	{
		return ctpId;
	}

	public void setCtpId(Integer ctpId)
	{
		this.ctpId = ctpId;
	}

	public String getCmdName()
	{
		return cmdName;
	}

	public void setCmdName(String cmdName)
	{
		this.cmdName = cmdName;
	}

	public String getCmdExtra1()
	{
		return cmdExtra1;
	}

	public void setCmdExtra1(String cmdExtra1)
	{
		this.cmdExtra1 = cmdExtra1;
	}

	public String getCmdExtra2()
	{
		return cmdExtra2;
	}

	public void setCmdExtra2(String cmdExtra2)
	{
		this.cmdExtra2 = cmdExtra2;
	}

	public String getCmdExtra3()
	{
		return cmdExtra3;
	}

	public void setCmdExtra3(String cmdExtra3)
	{
		this.cmdExtra3 = cmdExtra3;
	}

	public String getCmdBk()
	{
		return cmdBk;
	}

	public void setCmdBk(String cmdBk)
	{
		this.cmdBk = cmdBk;
	}

	public Integer getCmdCreatedUsrId()
	{
		return cmdCreatedUsrId;
	}

	public void setCmdCreatedUsrId(Integer cmdCreatedUsrId)
	{
		this.cmdCreatedUsrId = cmdCreatedUsrId;
	}

	public Integer getCmdModifiedUsrId()
	{
		return cmdModifiedUsrId;
	}

	public void setCmdModifiedUsrId(Integer cmdModifiedUsrId)
	{
		this.cmdModifiedUsrId = cmdModifiedUsrId;
	}

	public DateTime getCmdCreatedDttm()
	{
		return cmdCreatedDttm;
	}

	public void setCmdCreatedDttm(DateTime cmdCreatedDttm)
	{
		this.cmdCreatedDttm = cmdCreatedDttm;
	}

	public DateTime getCmdModifiedDttm()
	{
		return cmdModifiedDttm;
	}

	public void setCmdModifiedDttm(DateTime cmdModifiedDttm)
	{
		this.cmdModifiedDttm = cmdModifiedDttm;
	}

}
