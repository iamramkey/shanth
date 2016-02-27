package se.signa.signature.newdbfilegenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import se.signa.signature.common.SignatureException;
import se.signa.signature.helpers.RefDBHelper;

public class FileGenerator
{
	private static Logger logger = Logger.getLogger(FileGenerator.class);
	private static List<String> ignoredTables;
	private static List<String> specialLongColumns;

	public static void main(String[] args) throws Exception
	{
		Map<String, String> tablesMap = new HashMap<String, String>();
		Map<String, List<TabColumn>> columnsMap = new HashMap<String, List<TabColumn>>();
		Map<String, String> displayFields = new HashMap<String, String>();
		Map<String, List<String>> uniqueColumns = new HashMap<String, List<String>>();
		prepareData(tablesMap, columnsMap, displayFields, uniqueColumns);

		// runForWeb
		generate(tablesMap, columnsMap, displayFields, uniqueColumns);

		// runForNode
		// GeneratorConstants.setForNode();
		// generate(tablesMap, columnsMap, displayFields);
		logger.info("File Generator complete");
	}

	public static void generate(Map<String, String> tablesMap, Map<String, List<TabColumn>> columnsMap, Map<String, String> displayFields, Map<String, List<String>> uniqueColumns) throws Exception
	{

		for (Entry<String, String> entry : tablesMap.entrySet())
		{
			String prefix = entry.getKey();
			String tableName = entry.getValue();
			String displayField = displayFields.get(prefix);
			List<TabColumn> tabColumns = columnsMap.get(prefix);

			logger.info("Processing table: " + tableName + "\n");

			DboGenerator.generateDbo(tableName, prefix, tabColumns, tablesMap, displayField);
			DboImplGenerator.generateDboImpl(tableName);

			DbaGenerator.generateDba(tableName, prefix, tabColumns, displayField, uniqueColumns.get(prefix));
			DbaImplGenerator.generateDbaImpl(tableName);

			logger.debug("Created java files for the table : " + tableName);
		}

		DbaClassMapperGenerator.generateDIMFile(tablesMap);
	}

	private static void prepareData(Map<String, String> tablesMap, Map<String, List<TabColumn>> columnsMap, Map<String, String> displayFields, Map<String, List<String>> uniqueColumnMap)
	{
		List<String> tableNames = RefDBHelper.getDB().fetchStringList("LOWER(tname)", "tab");
		for (String tableName : tableNames)
		{
			logger.info("Collecting Data for " + tableName);
			if (ignoredTables.contains(tableName))
				continue;
			//			logger.info("Collecting Columns for " + tableName);
			List<TabColumn> allTabColumns = getAllTabColumns(tableName);
			// logger.info("Collecting Prefix for " + tableName);
			String prefix = getPrefix(allTabColumns);
			// logger.info("Collecting Display Field for " + tableName);
			UniqueColumns uniqueColumns = getDisplayField(tableName, allTabColumns);

			if (tablesMap.containsKey(prefix))
				throw new SignatureException("Prefix :" + prefix + " already exists in " + tablesMap.get(prefix));
			tablesMap.put(prefix, tableName);
			columnsMap.put(prefix, allTabColumns);
			displayFields.put(prefix, uniqueColumns.displayColumn);
			uniqueColumns.uniqueColumns.add(prefix + "_bk");
			uniqueColumnMap.put(prefix, uniqueColumns.uniqueColumns);
		}
	}

	private static UniqueColumns getDisplayField(String tableName, List<TabColumn> allTabColumns)
	{
		String whereClause = "";
		whereClause = whereClause + " column_name not like '%_BK' and column_name not ";
		whereClause = whereClause + getSpecialNonDisplayFieldColumns();
		whereClause = whereClause + " and  constraint_name in (  SELECT constraint_name FROM user_constraints  WHERE UPPER(table_name) = UPPER( '" + tableName + "' ) AND CONSTRAINT_TYPE = 'U' ) ";
		List<String> displayFields = RefDBHelper.getDB().fetchStringList("LOWER(column_name)", "all_cons_columns", whereClause);
		// for (String displayField : displayFields)
		// {
		// if (getSpecialNonDisplayFieldColumns().contains(displayField))
		// continue;

		// for (TabColumn tabColumn : allTabColumns)
		// {
		// if (tabColumn.dataType.equalsIgnoreCase("VARCHAR2"))
		// if (tabColumn.columnName.equalsIgnoreCase(displayField))
		UniqueColumns uniqueColumns = new FileGenerator().new UniqueColumns();
		uniqueColumns.displayColumn = displayFields.get(0);
		uniqueColumns.uniqueColumns = displayFields;
		return uniqueColumns;
		// }
		// }
		// throw new StigWebException("Unable to find a unique string field");
	}

	private static List<TabColumn> getAllTabColumns(String tableName)
	{
		String query = "select LOWER(column_name) as COLUMN_NAME,data_type,nullable from user_tab_columns where table_name = UPPER(?)  order by column_id";
		List<TabColumn> allTabColumns = RefDBHelper.getDB().fetchList(query, tableName, TabColumn.class);

		for (TabColumn tabColumn : allTabColumns)
		{
			if (tabColumn.columnName.endsWith("_id"))
				tabColumn.dataType = "NUMBER_FK";

			if (specialLongColumns.contains(tabColumn.columnName))
				tabColumn.dataType = "LONG";
		}

		return allTabColumns;
	}

	// private static List<String> getSpecialLongColumns()
	// {
	// List<String> specialColumns = new ArrayList<String>();
	// specialColumns.add("auf_checksum");
	// specialColumns.add("auf_filesize");
	// specialColumns.add("auf_child_count");
	// specialColumns.add("pfi_checksum");
	//
	// return specialColumns;
	// }

	private static String getSpecialNonDisplayFieldColumns()
	{
		return " in ('USR_EMAIL','TBR_ID','TBI_ORDER_NO','MORECOLS..')";
	}

	// private static boolean isSpecialLongColumn(String columnName)
	// {
	// List<String> specialColumns = getSpecialColumns();
	// for (String specialColumn : specialColumns)
	// if (specialColumn.equalsIgnoreCase(columnName))
	// return true;
	// return false;
	// }

	private static String getPrefix(List<TabColumn> allTabColumns)
	{
		for (TabColumn allTabColumn : allTabColumns)
		{
			if (allTabColumn.columnName.length() == 6 && allTabColumn.columnName.endsWith("_bk"))
				return allTabColumn.columnName.substring(0, 3);
		}
		return null;
	}

	static
	{
		ignoredTables = new ArrayList<String>();
		ignoredTables.add("band");
		ignoredTables.add("band_work");
		ignoredTables.add("account");
		ignoredTables.add("customer_vendor");
		ignoredTables.add("account_work");
		ignoredTables.add("bill_profile");
		ignoredTables.add("bill_profile_work");
		ignoredTables.add("currency");
		ignoredTables.add("customer_vendor_work");
		ignoredTables.add("deal");
		ignoredTables.add("deal_work");
		ignoredTables.add("deal_extra");
		ignoredTables.add("deal_extra_work");
		ignoredTables.add("deal_band_group");
		ignoredTables.add("deal_band_group_work");
		ignoredTables.add("deal_band_group_items");
		ignoredTables.add("deal_band_group_items_work");
		ignoredTables.add("deal_item");
		ignoredTables.add("deal_item_work");
		ignoredTables.add("deal_tier");
		ignoredTables.add("deal_tier_work");
		ignoredTables.add("deal_period");
		ignoredTables.add("deal_period_work");
		ignoredTables.add("deal_base_rate");
		ignoredTables.add("deal_base_rate_work");
		ignoredTables.add("deal_threshold_rate");
		ignoredTables.add("deal_threshold_rate_work");
		ignoredTables.add("settlement_service");
		ignoredTables.add("settlement_service_work");
		ignoredTables.add("area");
		ignoredTables.add("area_connection");

		ignoredTables.add("next_id");
		ignoredTables.add("traffic_summary");
		ignoredTables.add("traffic_tier");
		ignoredTables.add("traffic_count");
		ignoredTables.add("bin$a72ea4lsql2yxotecawgrg==$0");

		specialLongColumns = new ArrayList<String>();
		specialLongColumns.add("auf_checksum");
		specialLongColumns.add("auf_filesize");
		specialLongColumns.add("auf_child_count");
		specialLongColumns.add("pfi_checksum");

	}

	// private static boolean isIgnoredTable(String tableName)
	// {
	// List<String> ignoredTables = getIgnoredTables();
	// for (String ignoredTable : ignoredTables)
	// if (ignoredTable.equalsIgnoreCase(tableName))
	// return true;
	// return false;
	// }

	public class UniqueColumns
	{
		public String displayColumn;
		public List<String> uniqueColumns;

	}
}
