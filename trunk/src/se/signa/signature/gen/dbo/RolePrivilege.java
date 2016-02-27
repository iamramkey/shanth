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
import se.signa.signature.gen.dba.RoleDba;
import se.signa.signature.gen.dba.ToolbarItemDba;
import se.signa.signature.gen.dba.UserTblDba;
import se.signa.signature.helpers.DisplayFieldHelper;

public abstract class RolePrivilege extends SignatureDbo
{
	private Integer ropId;
	private Integer rolId;
	private Integer tbiId;
	private String ropName;
	private String ropIndex;
	private String ropView;
	private String ropNew;
	private String ropEdit;
	private String ropDelete;
	private String ropBk;
	private Integer ropCreatedUsrId;
	private Integer ropModifiedUsrId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime ropCreatedDttm;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime ropModifiedDttm;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			ropId = getInteger(rs.getInt("rop_id"));
			rolId = getInteger(rs.getInt("rol_id"));
			tbiId = getInteger(rs.getInt("tbi_id"));
			ropName = rs.getString("rop_name");
			ropIndex = rs.getString("rop_index");
			ropView = rs.getString("rop_view");
			ropNew = rs.getString("rop_new");
			ropEdit = rs.getString("rop_edit");
			ropDelete = rs.getString("rop_delete");
			ropBk = rs.getString("rop_bk");
			ropCreatedUsrId = getInteger(rs.getInt("rop_created_usr_id"));
			ropModifiedUsrId = getInteger(rs.getInt("rop_modified_usr_id"));
			ropCreatedDttm = getDateTime(rs.getTimestamp("rop_created_dttm"));
			ropModifiedDttm = getDateTime(rs.getTimestamp("rop_modified_dttm"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public int getPk()
	{
		return ropId;
	}

	public void setPk(int pk)
	{
		ropId = pk;
	}

	public String getBk()
	{
		return ropBk;
	}

	public List<String> toStringList()
	{
		List<String> data = new ArrayList<String>();
		data.add(ropId.toString());
		data.add(rolId.toString());
		data.add(tbiId.toString());
		data.add(ropName.toString());
		data.add(ropIndex.toString());
		data.add(ropView.toString());
		data.add(ropNew.toString());
		data.add(ropEdit.toString());
		data.add(ropDelete.toString());
		data.add(getRopCreatedUsr());
		data.add(Constants.dttf.print(ropCreatedDttm));
		return data;
	}

	@Override
	public Map<String, Object> populateTo()
	{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", getPk());
		data.put("rol", getRol());
		data.put("tbi", getTbi());
		data.put("ropName", getString(ropName));
		data.put("ropIndex", getString(ropIndex));
		data.put("ropView", getString(ropView));
		data.put("ropNew", getString(ropNew));
		data.put("ropEdit", getString(ropEdit));
		data.put("ropDelete", getString(ropDelete));
		return data;
	}

	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setRolId(DisplayFieldHelper.getI().getPk(RoleDba.class, input.getString("rol")));
		setTbiId(DisplayFieldHelper.getI().getPk(ToolbarItemDba.class, input.getString("tbi")));
		setRopName(input.getString("ropName"));
		setRopIndex(input.getString("ropIndex"));
		setRopView(input.getString("ropView"));
		setRopNew(input.getString("ropNew"));
		setRopEdit(input.getString("ropEdit"));
		setRopDelete(input.getString("ropDelete"));
	}

	public List<String> toAuditStringList()
	{
		List<String> auditData = new ArrayList<String>();
		auditData.add(ropBk.toString());
		auditData.add(getRopCreatedUsr());
		auditData.add(ropModifiedUsrId == null ? Constants.NULLSTRING : getRopModifiedUsr());
		auditData.add(Constants.dttf.print(ropCreatedDttm));
		auditData.add(ropModifiedDttm == null ? Constants.NULLSTRING : Constants.dttf.print(ropModifiedDttm));
		return auditData;
	}

	public String getDisplayField()
	{
		return ropName;
	}

	public String getRol()
	{
		return DisplayFieldHelper.getI().getDisplayField(RoleDba.class, rolId);
	}

	public String getTbi()
	{
		return DisplayFieldHelper.getI().getDisplayField(ToolbarItemDba.class, tbiId);
	}

	public String getRopCreatedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, ropCreatedUsrId);
	}

	public String getRopModifiedUsr()
	{
		return DisplayFieldHelper.getI().getDisplayField(UserTblDba.class, ropModifiedUsrId);
	}

	public Object getProperty(String propertyName)
	{
		if (propertyName.equals("id"))
			return getPk();
		if (propertyName.equals("rol"))
			return getRol();
		if (propertyName.equals("tbi"))
			return getTbi();
		if (propertyName.equals("ropCreatedUsr"))
			return getRopCreatedUsr();
		if (propertyName.equals("ropModifiedUsr"))
			return getRopModifiedUsr();
		if (propertyName.equals("ropId"))
			return getRopId();
		if (propertyName.equals("rolId"))
			return getRolId();
		if (propertyName.equals("tbiId"))
			return getTbiId();
		if (propertyName.equals("ropName"))
			return getRopName();
		if (propertyName.equals("ropIndex"))
			return getRopIndex();
		if (propertyName.equals("ropView"))
			return getRopView();
		if (propertyName.equals("ropNew"))
			return getRopNew();
		if (propertyName.equals("ropEdit"))
			return getRopEdit();
		if (propertyName.equals("ropDelete"))
			return getRopDelete();
		if (propertyName.equals("ropBk"))
			return getRopBk();
		if (propertyName.equals("ropCreatedUsrId"))
			return getRopCreatedUsrId();
		if (propertyName.equals("ropModifiedUsrId"))
			return getRopModifiedUsrId();
		if (propertyName.equals("ropCreatedDttm"))
			return getRopCreatedDttm();
		if (propertyName.equals("ropModifiedDttm"))
			return getRopModifiedDttm();
		throw new SignatureException("Property :" + propertyName + " not found ");
	}

	public Integer getRopId()
	{
		return ropId;
	}

	public void setRopId(Integer ropId)
	{
		this.ropId = ropId;
	}

	public Integer getRolId()
	{
		return rolId;
	}

	public void setRolId(Integer rolId)
	{
		this.rolId = rolId;
	}

	public Integer getTbiId()
	{
		return tbiId;
	}

	public void setTbiId(Integer tbiId)
	{
		this.tbiId = tbiId;
	}

	public String getRopName()
	{
		return ropName;
	}

	public void setRopName(String ropName)
	{
		this.ropName = ropName;
	}

	public String getRopIndex()
	{
		return ropIndex;
	}

	public void setRopIndex(String ropIndex)
	{
		this.ropIndex = ropIndex;
	}

	public String getRopView()
	{
		return ropView;
	}

	public void setRopView(String ropView)
	{
		this.ropView = ropView;
	}

	public String getRopNew()
	{
		return ropNew;
	}

	public void setRopNew(String ropNew)
	{
		this.ropNew = ropNew;
	}

	public String getRopEdit()
	{
		return ropEdit;
	}

	public void setRopEdit(String ropEdit)
	{
		this.ropEdit = ropEdit;
	}

	public String getRopDelete()
	{
		return ropDelete;
	}

	public void setRopDelete(String ropDelete)
	{
		this.ropDelete = ropDelete;
	}

	public String getRopBk()
	{
		return ropBk;
	}

	public void setRopBk(String ropBk)
	{
		this.ropBk = ropBk;
	}

	public Integer getRopCreatedUsrId()
	{
		return ropCreatedUsrId;
	}

	public void setRopCreatedUsrId(Integer ropCreatedUsrId)
	{
		this.ropCreatedUsrId = ropCreatedUsrId;
	}

	public Integer getRopModifiedUsrId()
	{
		return ropModifiedUsrId;
	}

	public void setRopModifiedUsrId(Integer ropModifiedUsrId)
	{
		this.ropModifiedUsrId = ropModifiedUsrId;
	}

	public DateTime getRopCreatedDttm()
	{
		return ropCreatedDttm;
	}

	public void setRopCreatedDttm(DateTime ropCreatedDttm)
	{
		this.ropCreatedDttm = ropCreatedDttm;
	}

	public DateTime getRopModifiedDttm()
	{
		return ropModifiedDttm;
	}

	public void setRopModifiedDttm(DateTime ropModifiedDttm)
	{
		this.ropModifiedDttm = ropModifiedDttm;
	}

}
