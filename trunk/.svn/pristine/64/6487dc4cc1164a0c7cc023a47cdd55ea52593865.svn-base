/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dba;

import java.util.List;

import se.signa.signature.dbo.ToolbarImpl;
import se.signa.signature.gen.dba.ToolbarDba;
import se.signa.signature.gen.dbo.Toolbar;
import se.signa.signature.helpers.RefDBHelper;

public class ToolbarDbaImpl extends ToolbarDba
{

	public List<Toolbar> fetchByParentTbrId(Toolbar toolBar)
	{
		String query = " select * from toolbar where tbr_parent_tbr_id = ?";
		return RefDBHelper.getDB().fetchList(query, toolBar.getTbrId(), ToolbarImpl.class);
	}

	public List<Toolbar> fetchAllFirstLevel()
	{
		String query = " select * from toolbar where tbr_parent_tbr_id is null order by tbr_order_no asc ";
		return RefDBHelper.getDB().fetchList(query, ToolbarImpl.class);
	}

}