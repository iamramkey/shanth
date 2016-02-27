/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dba;

import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.dbo.AlertImpl;
import se.signa.signature.gen.dba.AlertDba;
import se.signa.signature.gen.dbo.Alert;
import se.signa.signature.helpers.RefDBHelper;

public class AlertDbaImpl extends AlertDba
{

	public List<Alert> fetchAllAlerts(String whereClause, Object[] whereParameters)
	{
		String query = " select * from alert " + whereClause + " order by ALT_CREATED_DTTM DESC ";
		return RefDBHelper.getDB().fetchList(query, whereParameters, AlertImpl.class);
	}

	public void addAlertCreatedDttmFrom(DateTime createdFromDttm, StringBuffer whereClause, List<Object> whereParams)
	{
		whereClause.append(" and ALT_CREATED_DTTM >= ?");
		whereParams.add(createdFromDttm);
	}

	public void addAlertCreatedDttmTo(DateTime createdToDttm, StringBuffer whereClause, List<Object> whereParams)
	{
		whereClause.append(" and ALT_CREATED_DTTM <= ?");
		whereParams.add(createdToDttm);
	}

	public void addNodeNameFilter(String nodeName, StringBuffer whereClause, List<Object> whereParams)
	{
		whereClause.append(" and NOD_ID in ( select NOD_ID from NODE where NOD_NAME like ? )");
		whereParams.add("%" + nodeName + "%");
	}

	public void addAlertTypeFilter(String alertType, StringBuffer whereClause, List<Object> whereParams)
	{
		whereClause.append(" and ATP_ID in ( select ATP_ID from alert_type where ATP_CODE like ? )");
		whereParams.add("%" + alertType + "%");
	}

	public String getDefaultWhereClause()
	{
		return " where alt_id is not null ";
	}

}