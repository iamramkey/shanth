package se.signa.signature.common;

import java.util.HashMap;
import java.util.Map;

public class DbaClassMapper
{
	private static DbaClassMapper INSTANCE;
	private static Map<String, String> prefixes = null;
	private static Map<String, String> classes = null;

	public static DbaClassMapper getI()
	{
		if (INSTANCE == null)
			INSTANCE = new DbaClassMapper();
		return INSTANCE;
	}

	private DbaClassMapper()
	{
		populatePrefixClasses();
		populateClasses();
	}

	protected void populatePrefixClasses()
	{
		prefixes = new HashMap<String, String>();
		prefixes.put("lpo", "se.signa.signature.gen.dba.LastPolledDba");
		prefixes.put("rps", "se.signa.signature.gen.dba.ReferenceFilePollerSettingsDba");
		prefixes.put("pde", "se.signa.signature.gen.dba.ProcessDependancyDba");
		prefixes.put("pdf", "se.signa.signature.gen.dba.PendingDataloadFileDba");
		prefixes.put("rlp", "se.signa.signature.gen.dba.PendingRaterLastPolledDba");
		prefixes.put("pds", "se.signa.signature.gen.dba.PendingDataloadSettingsDba");
		prefixes.put("exs", "se.signa.signature.gen.dba.ExtractionPollerSettingsDba");
		prefixes.put("mco", "se.signa.signature.gen.dba.MasterSearchColumnDba");
		prefixes.put("pcs", "se.signa.signature.gen.dba.PendingCcSettingsDba");
		prefixes.put("pda", "se.signa.signature.gen.dba.PendingDealArchivesDba");
		prefixes.put("rdc", "se.signa.signature.gen.dba.RefDataComparatorSettingsDba");
		prefixes.put("ctr", "se.signa.signature.gen.dba.CountryDba");
		prefixes.put("nod", "se.signa.signature.gen.dba.NodeDba");
		prefixes.put("ctp", "se.signa.signature.gen.dba.CommandTypeDba");
		prefixes.put("tbi", "se.signa.signature.gen.dba.ToolbarItemDba");
		prefixes.put("jbc", "se.signa.signature.gen.dba.JobCapabilityDba");
		prefixes.put("fas", "se.signa.signature.gen.dba.FileParserSettingsDba");
		prefixes.put("pcc", "se.signa.signature.gen.dba.PendingCcDba");
		prefixes.put("rop", "se.signa.signature.gen.dba.RolePrivilegeDba");
		prefixes.put("fps", "se.signa.signature.gen.dba.FilePollerSettingsDba");
		prefixes.put("job", "se.signa.signature.gen.dba.JobDba");
		prefixes.put("pco", "se.signa.signature.gen.dba.PendingCorrelationDba");
		prefixes.put("sfs", "se.signa.signature.gen.dba.SfsSettingsDba");
		prefixes.put("fap", "se.signa.signature.gen.dba.FailedTasksPollerSettingsDba");
		prefixes.put("pcp", "se.signa.signature.gen.dba.PendingCrSettingsDba");
		prefixes.put("msf", "se.signa.signature.gen.dba.MasterSearchFormDba");
		prefixes.put("rcp", "se.signa.signature.gen.dba.RoleCustomDba");
		prefixes.put("fts", "se.signa.signature.gen.dba.FileTransferSettingsDba");
		prefixes.put("cmd", "se.signa.signature.gen.dba.CommandDba");
		prefixes.put("urp", "se.signa.signature.gen.dba.UserRolePermissionDba");
		prefixes.put("ptf", "se.signa.signature.gen.dba.PendingTransferFileDba");
		prefixes.put("etr", "se.signa.signature.gen.dba.ExtractionDba");
		prefixes.put("jbt", "se.signa.signature.gen.dba.JobTypeDba");
		prefixes.put("bes", "se.signa.signature.gen.dba.BixExporterSettingsDba");
		prefixes.put("pbs", "se.signa.signature.gen.dba.PendingBixOpFileSettingsDba");
		prefixes.put("ftp", "se.signa.signature.gen.dba.FtpSettingsDba");
		prefixes.put("rol", "se.signa.signature.gen.dba.RoleDba");
		prefixes.put("ppf", "se.signa.signature.gen.dba.PendingPollFileDba");
		prefixes.put("usl", "se.signa.signature.gen.dba.UserLoginDba");
		prefixes.put("hkj", "se.signa.signature.gen.dba.HouseKeepingJobsDba");
		prefixes.put("pbf", "se.signa.signature.gen.dba.PendingBixOutputFileDba");
		prefixes.put("ses", "se.signa.signature.gen.dba.ServerSettingsDba");
		prefixes.put("hcs", "se.signa.signature.gen.dba.HealthCheckerSettingsDba");
		prefixes.put("fil", "se.signa.signature.gen.dba.FiletblDba");
		prefixes.put("pra", "se.signa.signature.gen.dba.PendingRaterFileDba");
		prefixes.put("pmr", "se.signa.signature.gen.dba.PendingMergerReDba");
		prefixes.put("srt", "se.signa.signature.gen.dba.ScriptTblDba");
		prefixes.put("pjb", "se.signa.signature.gen.dba.PendingJobDba");
		prefixes.put("run", "se.signa.signature.gen.dba.RunnerDba");
		prefixes.put("ras", "se.signa.signature.gen.dba.RaterSettingsDba");
		prefixes.put("fia", "se.signa.signature.gen.dba.FileArchiverSettingsDba");
		prefixes.put("ems", "se.signa.signature.gen.dba.EmailSettingsDba");
		prefixes.put("cac", "se.signa.signature.gen.dba.CachetblDba");
		prefixes.put("hks", "se.signa.signature.gen.dba.HouseKeepingSettingsDba");
		prefixes.put("pmi", "se.signa.signature.gen.dba.PendingMissingJobDba");
		prefixes.put("alt", "se.signa.signature.gen.dba.AlertDba");
		prefixes.put("pme", "se.signa.signature.gen.dba.PendingMergerDba");
		prefixes.put("atp", "se.signa.signature.gen.dba.AlertTypeDba");
		prefixes.put("pfi", "se.signa.signature.gen.dba.ParsedFileDba");
		prefixes.put("msa", "se.signa.signature.gen.dba.MasterSearchActionDba");
		prefixes.put("uss", "se.signa.signature.gen.dba.UserSessionDba");
		prefixes.put("usr", "se.signa.signature.gen.dba.UserTblDba");
		prefixes.put("pjs", "se.signa.signature.gen.dba.PendingJobPollerSettingsDba");
		prefixes.put("hch", "se.signa.signature.gen.dba.HealthCheckDba");
		prefixes.put("usp", "se.signa.signature.gen.dba.UserPasswordDba");
		prefixes.put("tbr", "se.signa.signature.gen.dba.ToolbarDba");
		prefixes.put("mse", "se.signa.signature.gen.dba.MasterSearchDba");
		prefixes.put("prr", "se.signa.signature.gen.dba.PendingRecomputeDba");
		prefixes.put("prs", "se.signa.signature.gen.dba.PendingRaterFileSettingsDba");
		prefixes.put("prt", "se.signa.signature.gen.dba.PendingRerateTransferFileDba");
		prefixes.put("sms", "se.signa.signature.gen.dba.SmsSettingsDba");
		prefixes.put("rea", "se.signa.signature.gen.dba.RecentActivityDba");
		prefixes.put("prf", "se.signa.signature.gen.dba.PendingRerateFtsDba");
		prefixes.put("pem", "se.signa.signature.gen.dba.PendingMissingJobSettingsDba");
		prefixes.put("rec", "se.signa.signature.gen.dba.ReasonCodeDba");
		prefixes.put("prc", "se.signa.signature.gen.dba.PendingRecomputeSettingsDba");
		prefixes.put("pre", "se.signa.signature.gen.dba.PendingRerateDataloadFileDba");
		prefixes.put("prd", "se.signa.signature.gen.dba.PendingRerateDsDba");
		prefixes.put("pes", "se.signa.signature.gen.dba.PendingFileTransferSettingsDba");
		prefixes.put("pef", "se.signa.signature.gen.dba.PendingRpErrorFileDba");
		prefixes.put("auf", "se.signa.signature.gen.dba.AuditFileDba");
		prefixes.put("ups", "se.signa.signature.gen.dba.UserPasswordSettingsDba");
		prefixes.put("rrs", "se.signa.signature.gen.dba.ReRaterSettingsDba");
	}

	protected void populateClasses()
	{
		classes = new HashMap<String, String>();
		classes.put("last_polled", "se.signa.signature.gen.dba.LastPolledDba");
		classes.put("reference_file_poller_settings", "se.signa.signature.gen.dba.ReferenceFilePollerSettingsDba");
		classes.put("process_dependancy", "se.signa.signature.gen.dba.ProcessDependancyDba");
		classes.put("pending_dataload_file", "se.signa.signature.gen.dba.PendingDataloadFileDba");
		classes.put("pending_rater_last_polled", "se.signa.signature.gen.dba.PendingRaterLastPolledDba");
		classes.put("pending_dataload_settings", "se.signa.signature.gen.dba.PendingDataloadSettingsDba");
		classes.put("extraction_poller_settings", "se.signa.signature.gen.dba.ExtractionPollerSettingsDba");
		classes.put("master_search_column", "se.signa.signature.gen.dba.MasterSearchColumnDba");
		classes.put("pending_cc_settings", "se.signa.signature.gen.dba.PendingCcSettingsDba");
		classes.put("pending_deal_archives", "se.signa.signature.gen.dba.PendingDealArchivesDba");
		classes.put("ref_data_comparator_settings", "se.signa.signature.gen.dba.RefDataComparatorSettingsDba");
		classes.put("country", "se.signa.signature.gen.dba.CountryDba");
		classes.put("node", "se.signa.signature.gen.dba.NodeDba");
		classes.put("command_type", "se.signa.signature.gen.dba.CommandTypeDba");
		classes.put("toolbar_item", "se.signa.signature.gen.dba.ToolbarItemDba");
		classes.put("job_capability", "se.signa.signature.gen.dba.JobCapabilityDba");
		classes.put("file_parser_settings", "se.signa.signature.gen.dba.FileParserSettingsDba");
		classes.put("pending_cc", "se.signa.signature.gen.dba.PendingCcDba");
		classes.put("role_privilege", "se.signa.signature.gen.dba.RolePrivilegeDba");
		classes.put("file_poller_settings", "se.signa.signature.gen.dba.FilePollerSettingsDba");
		classes.put("job", "se.signa.signature.gen.dba.JobDba");
		classes.put("pending_correlation", "se.signa.signature.gen.dba.PendingCorrelationDba");
		classes.put("sfs_settings", "se.signa.signature.gen.dba.SfsSettingsDba");
		classes.put("failed_tasks_poller_settings", "se.signa.signature.gen.dba.FailedTasksPollerSettingsDba");
		classes.put("pending_cr_settings", "se.signa.signature.gen.dba.PendingCrSettingsDba");
		classes.put("master_search_form", "se.signa.signature.gen.dba.MasterSearchFormDba");
		classes.put("role_custom", "se.signa.signature.gen.dba.RoleCustomDba");
		classes.put("file_transfer_settings", "se.signa.signature.gen.dba.FileTransferSettingsDba");
		classes.put("command", "se.signa.signature.gen.dba.CommandDba");
		classes.put("user_role_permission", "se.signa.signature.gen.dba.UserRolePermissionDba");
		classes.put("pending_transfer_file", "se.signa.signature.gen.dba.PendingTransferFileDba");
		classes.put("extraction", "se.signa.signature.gen.dba.ExtractionDba");
		classes.put("job_type", "se.signa.signature.gen.dba.JobTypeDba");
		classes.put("bix_exporter_settings", "se.signa.signature.gen.dba.BixExporterSettingsDba");
		classes.put("pending_bix_op_file_settings", "se.signa.signature.gen.dba.PendingBixOpFileSettingsDba");
		classes.put("ftp_settings", "se.signa.signature.gen.dba.FtpSettingsDba");
		classes.put("role", "se.signa.signature.gen.dba.RoleDba");
		classes.put("pending_poll_file", "se.signa.signature.gen.dba.PendingPollFileDba");
		classes.put("user_login", "se.signa.signature.gen.dba.UserLoginDba");
		classes.put("house_keeping_jobs", "se.signa.signature.gen.dba.HouseKeepingJobsDba");
		classes.put("pending_bix_output_file", "se.signa.signature.gen.dba.PendingBixOutputFileDba");
		classes.put("server_settings", "se.signa.signature.gen.dba.ServerSettingsDba");
		classes.put("health_checker_settings", "se.signa.signature.gen.dba.HealthCheckerSettingsDba");
		classes.put("filetbl", "se.signa.signature.gen.dba.FiletblDba");
		classes.put("pending_rater_file", "se.signa.signature.gen.dba.PendingRaterFileDba");
		classes.put("pending_merger_re", "se.signa.signature.gen.dba.PendingMergerReDba");
		classes.put("script_tbl", "se.signa.signature.gen.dba.ScriptTblDba");
		classes.put("pending_job", "se.signa.signature.gen.dba.PendingJobDba");
		classes.put("runner", "se.signa.signature.gen.dba.RunnerDba");
		classes.put("rater_settings", "se.signa.signature.gen.dba.RaterSettingsDba");
		classes.put("file_archiver_settings", "se.signa.signature.gen.dba.FileArchiverSettingsDba");
		classes.put("email_settings", "se.signa.signature.gen.dba.EmailSettingsDba");
		classes.put("cachetbl", "se.signa.signature.gen.dba.CachetblDba");
		classes.put("house_keeping_settings", "se.signa.signature.gen.dba.HouseKeepingSettingsDba");
		classes.put("pending_missing_job", "se.signa.signature.gen.dba.PendingMissingJobDba");
		classes.put("alert", "se.signa.signature.gen.dba.AlertDba");
		classes.put("pending_merger", "se.signa.signature.gen.dba.PendingMergerDba");
		classes.put("alert_type", "se.signa.signature.gen.dba.AlertTypeDba");
		classes.put("parsed_file", "se.signa.signature.gen.dba.ParsedFileDba");
		classes.put("master_search_action", "se.signa.signature.gen.dba.MasterSearchActionDba");
		classes.put("user_session", "se.signa.signature.gen.dba.UserSessionDba");
		classes.put("user_tbl", "se.signa.signature.gen.dba.UserTblDba");
		classes.put("pending_job_poller_settings", "se.signa.signature.gen.dba.PendingJobPollerSettingsDba");
		classes.put("health_check", "se.signa.signature.gen.dba.HealthCheckDba");
		classes.put("user_password", "se.signa.signature.gen.dba.UserPasswordDba");
		classes.put("toolbar", "se.signa.signature.gen.dba.ToolbarDba");
		classes.put("master_search", "se.signa.signature.gen.dba.MasterSearchDba");
		classes.put("pending_recompute", "se.signa.signature.gen.dba.PendingRecomputeDba");
		classes.put("pending_rater_file_settings", "se.signa.signature.gen.dba.PendingRaterFileSettingsDba");
		classes.put("pending_rerate_transfer_file", "se.signa.signature.gen.dba.PendingRerateTransferFileDba");
		classes.put("sms_settings", "se.signa.signature.gen.dba.SmsSettingsDba");
		classes.put("recent_activity", "se.signa.signature.gen.dba.RecentActivityDba");
		classes.put("pending_rerate_fts", "se.signa.signature.gen.dba.PendingRerateFtsDba");
		classes.put("pending_missing_job_settings", "se.signa.signature.gen.dba.PendingMissingJobSettingsDba");
		classes.put("reason_code", "se.signa.signature.gen.dba.ReasonCodeDba");
		classes.put("pending_recompute_settings", "se.signa.signature.gen.dba.PendingRecomputeSettingsDba");
		classes.put("pending_rerate_dataload_file", "se.signa.signature.gen.dba.PendingRerateDataloadFileDba");
		classes.put("pending_rerate_ds", "se.signa.signature.gen.dba.PendingRerateDsDba");
		classes.put("pending_file_transfer_settings", "se.signa.signature.gen.dba.PendingFileTransferSettingsDba");
		classes.put("pending_rp_error_file", "se.signa.signature.gen.dba.PendingRpErrorFileDba");
		classes.put("audit_file", "se.signa.signature.gen.dba.AuditFileDba");
		classes.put("user_password_settings", "se.signa.signature.gen.dba.UserPasswordSettingsDba");
		classes.put("re_rater_settings", "se.signa.signature.gen.dba.ReRaterSettingsDba");
	}

	public String getClassByTableName(String name)
	{
		return classes.get(name);
	}

	public String getClassByPrefix(String name)
	{
		return prefixes.get(name);
	}

}