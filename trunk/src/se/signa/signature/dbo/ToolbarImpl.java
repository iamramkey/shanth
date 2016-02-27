/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;
import java.util.List;

import se.signa.signature.gen.dbo.Toolbar;
import se.signa.signature.gen.dbo.ToolbarItem;

public class ToolbarImpl extends Toolbar
{
	public List<Toolbar> toolbars;
	public List<ToolbarItem> toolbarItems;

	public ToolbarImpl()
	{

	}

	public ToolbarImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

	public void setToolbarItems(List<ToolbarItem> toolbarItems)
	{
		this.toolbarItems = toolbarItems;
	}

	public void setToolbars(List<Toolbar> toolbars)
	{
		this.toolbars = toolbars;
	}

}