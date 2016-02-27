/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.gen.dbo.Node;

public class NodeImpl extends Node
{
	public NodeImpl()
	{
	}

	public NodeImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}

}