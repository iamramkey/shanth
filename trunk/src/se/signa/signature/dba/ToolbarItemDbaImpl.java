/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dba;

import java.util.List;

import se.signa.signature.dbo.ToolbarItemImpl;
import se.signa.signature.gen.dba.ToolbarItemDba;
import se.signa.signature.gen.dbo.ToolbarItem;
import se.signa.signature.helpers.RefDBHelper;

public class ToolbarItemDbaImpl extends ToolbarItemDba
{
	public List<ToolbarItem> fetchByTbrId(int tbrId)
	{
		String query = " select * from toolbar_item where tbr_id = ? ";
		return RefDBHelper.getDB().fetchList(query, tbrId, ToolbarItemImpl.class);
	}
}