/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.gen.dbo.ToolbarItem;

public class ToolbarItemImpl extends ToolbarItem
{
	public ToolbarItemImpl()
	{
	}

	public ToolbarItemImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

}