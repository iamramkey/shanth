/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dba;

import java.util.List;

import se.signa.signature.dbo.RecentActivityImpl;
import se.signa.signature.gen.dba.RecentActivityDba;
import se.signa.signature.gen.dbo.RecentActivity;
import se.signa.signature.helpers.RefDBHelper;

public class RecentActivityDbaImpl extends RecentActivityDba
{
	public List<RecentActivity> getRecentActivity(String type)
	{
		String query = " select * from recent_activity where rea_type = ? and rownum < 200";
		return RefDBHelper.getDB().fetchList(query, RecentActivityImpl.class);
	}
}