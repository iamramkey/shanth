/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.gen.dbo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.joda.time.DateTime;

import se.signa.signature.common.CustomDateSerializer;
import se.signa.signature.common.SignatureDbo;
import se.signa.signature.common.SignatureException;

public abstract class Deal extends SignatureDbo
{
	private Integer delId;
	private Integer accId;
	private Integer curId;
	private boolean delAllBandTypesFl;
	private Integer bdtId;
	private String dlsName;
	private Integer delMgrUsrId;
	private Integer dlsId;
	private String delName;
	private String delContractNo;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime delFromDttm;
	private Integer dlmId;
	@JsonSerialize(using = CustomDateSerializer.class)
	private DateTime delToDttm;
	private boolean delCommittedFl;
	private boolean delBalancedFl;
	private boolean delAdjustmentFl;
	private boolean delGracePeriodFl;
	private boolean delTradeFl;
	private boolean delApplyShortfallPenaltyFl;
	private boolean delApplyMonthlyRebateFl;
	private boolean delRenewAfterExpiryFl;
	private String delGracePeriodDesc;
	private Integer delGracePeriodDays;
	private boolean delProrateMonthlyFl;
	private boolean delClosedFl;
	private boolean delDeleteFl;

	protected void populateFromResultSet(ResultSet rs)
	{
		try
		{
			delId = getInteger(rs.getInt("del_id"));
			accId = getInteger(rs.getInt("acc_id"));
			curId = getInteger(rs.getInt("cur_id"));
			delAllBandTypesFl = getBoolean(rs.getString("del_all_band_types_fl"));
			bdtId = getInteger(rs.getInt("bdt_id"));
			dlsName = rs.getString("dls_name");
			delMgrUsrId = getInteger(rs.getInt("del_mgr_usr_id"));
			dlsId = getInteger(rs.getInt("dls_id"));
			delName = rs.getString("del_name");
			delContractNo = rs.getString("del_contract_no");
			delFromDttm = getDateTime(rs.getTimestamp("del_from_dttm"));
			dlmId = getInteger(rs.getInt("dlm_id"));
			delToDttm = getDateTime(rs.getTimestamp("del_to_dttm"));
			delCommittedFl = getBoolean(rs.getString("del_committed_fl"));
			delBalancedFl = getBoolean(rs.getString("del_balanced_fl"));
			delAdjustmentFl = getBoolean(rs.getString("del_adjustment_fl"));
			delGracePeriodFl = getBoolean(rs.getString("del_grace_period_fl"));
			delTradeFl = getBoolean(rs.getString("del_trade_fl"));
			delApplyShortfallPenaltyFl = getBoolean(rs.getString("del_apply_shortfall_penalty_fl"));
			delApplyMonthlyRebateFl = getBoolean(rs.getString("del_apply_monthly_rebate_fl"));
			delRenewAfterExpiryFl = getBoolean(rs.getString("del_renew_after_expiry_fl"));
			delGracePeriodDesc = rs.getString("del_grace_period_desc");
			delGracePeriodDays = getInteger(rs.getInt("del_grace_period_days"));
			delProrateMonthlyFl = getBoolean(rs.getString("del_prorate_monthly_fl"));
			delClosedFl = getBoolean(rs.getString("del_closed_fl"));
			delDeleteFl = getBoolean(rs.getString("del_delete_fl"));
		}
		catch (SQLException e)
		{
			throw new SignatureException(e);
		}
	}

	public Integer getDelId()
	{
		return delId;
	}

	public void setDelId(Integer delId)
	{
		this.delId = delId;
	}

	public Integer getAccId()
	{
		return accId;
	}

	public void setAccId(Integer accId)
	{
		this.accId = accId;
	}

	public Integer getCurId()
	{
		return curId;
	}

	public void setCurId(Integer curId)
	{
		this.curId = curId;
	}

	public boolean isDelAllBandTypesFl()
	{
		return delAllBandTypesFl;
	}

	public void setDelAllBandTypesFl(boolean delAllBandTypesFl)
	{
		this.delAllBandTypesFl = delAllBandTypesFl;
	}

	public Integer getBdtId()
	{
		return bdtId;
	}

	public void setBdtId(Integer bdtId)
	{
		this.bdtId = bdtId;
	}

	public String getDlsName()
	{
		return dlsName;
	}

	public void setDlsName(String dlsName)
	{
		this.dlsName = dlsName;
	}

	public Integer getDelMgrUsrId()
	{
		return delMgrUsrId;
	}

	public void setDelMgrUsrId(Integer delMgrUsrId)
	{
		this.delMgrUsrId = delMgrUsrId;
	}

	public Integer getDlsId()
	{
		return dlsId;
	}

	public void setDlsId(Integer dlsId)
	{
		this.dlsId = dlsId;
	}

	public String getDelName()
	{
		return delName;
	}

	public void setDelName(String delName)
	{
		this.delName = delName;
	}

	public String getDelContractNo()
	{
		return delContractNo;
	}

	public void setDelContractNo(String delContractNo)
	{
		this.delContractNo = delContractNo;
	}

	public DateTime getDelFromDttm()
	{
		return delFromDttm;
	}

	public void setDelFromDttm(DateTime delFromDttm)
	{
		this.delFromDttm = delFromDttm;
	}

	public Integer getDlmId()
	{
		return dlmId;
	}

	public void setDlmId(Integer dlmId)
	{
		this.dlmId = dlmId;
	}

	public DateTime getDelToDttm()
	{
		return delToDttm;
	}

	public void setDelToDttm(DateTime delToDttm)
	{
		this.delToDttm = delToDttm;
	}

	public boolean isDelCommittedFl()
	{
		return delCommittedFl;
	}

	public void setDelCommittedFl(boolean delCommittedFl)
	{
		this.delCommittedFl = delCommittedFl;
	}

	public boolean isDelBalancedFl()
	{
		return delBalancedFl;
	}

	public void setDelBalancedFl(boolean delBalancedFl)
	{
		this.delBalancedFl = delBalancedFl;
	}

	public boolean isDelAdjustmentFl()
	{
		return delAdjustmentFl;
	}

	public void setDelAdjustmentFl(boolean delAdjustmentFl)
	{
		this.delAdjustmentFl = delAdjustmentFl;
	}

	public boolean isDelGracePeriodFl()
	{
		return delGracePeriodFl;
	}

	public void setDelGracePeriodFl(boolean delGracePeriodFl)
	{
		this.delGracePeriodFl = delGracePeriodFl;
	}

	public boolean isDelTradeFl()
	{
		return delTradeFl;
	}

	public void setDelTradeFl(boolean delTradeFl)
	{
		this.delTradeFl = delTradeFl;
	}

	public boolean isDelApplyShortfallPenaltyFl()
	{
		return delApplyShortfallPenaltyFl;
	}

	public void setDelApplyShortfallPenaltyFl(boolean delApplyShortfallPenaltyFl)
	{
		this.delApplyShortfallPenaltyFl = delApplyShortfallPenaltyFl;
	}

	public boolean isDelApplyMonthlyRebateFl()
	{
		return delApplyMonthlyRebateFl;
	}

	public void setDelApplyMonthlyRebateFl(boolean delApplyMonthlyRebateFl)
	{
		this.delApplyMonthlyRebateFl = delApplyMonthlyRebateFl;
	}

	public boolean isDelRenewAfterExpiryFl()
	{
		return delRenewAfterExpiryFl;
	}

	public void setDelRenewAfterExpiryFl(boolean delRenewAfterExpiryFl)
	{
		this.delRenewAfterExpiryFl = delRenewAfterExpiryFl;
	}

	public String getDelGracePeriodDesc()
	{
		return delGracePeriodDesc;
	}

	public void setDelGracePeriodDesc(String delGracePeriodDesc)
	{
		this.delGracePeriodDesc = delGracePeriodDesc;
	}

	public Integer getDelGracePeriodDays()
	{
		return delGracePeriodDays;
	}

	public void setDelGracePeriodDays(Integer delGracePeriodDays)
	{
		this.delGracePeriodDays = delGracePeriodDays;
	}

	public boolean isDelProrateMonthlyFl()
	{
		return delProrateMonthlyFl;
	}

	public void setDelProrateMonthlyFl(boolean delProrateMonthlyFl)
	{
		this.delProrateMonthlyFl = delProrateMonthlyFl;
	}

	public boolean isDelClosedFl()
	{
		return delClosedFl;
	}

	public void setDelClosedFl(boolean delClosedFl)
	{
		this.delClosedFl = delClosedFl;
	}

	public boolean isDelDeleteFl()
	{
		return delDeleteFl;
	}

	public void setDelDeleteFl(boolean delDeleteFl)
	{
		this.delDeleteFl = delDeleteFl;
	}
}
