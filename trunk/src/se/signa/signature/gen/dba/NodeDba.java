/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.NodeDbaImpl;
import se.signa.signature.dbo.NodeImpl;
import se.signa.signature.gen.dbo.Node;
import se.signa.signature.helpers.AudDBHelper;
import se.signa.signature.helpers.PrimaryKeyHelper;
import se.signa.signature.helpers.RefDBHelper;

public abstract class NodeDba extends SignatureDba<Node>
{
	private static NodeDbaImpl INSTANCE;

	public static NodeDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new NodeDbaImpl();
		return INSTANCE;
	}

	public NodeDba()
	{
		tableName = "node";
		tablePrefix = "nod";
	}

	public List<String> getColumns()
	{
		List<String> columns = new ArrayList<String>();
		columns.add("Nod Id");
		columns.add("Nod Name");
		columns.add("Nod Datadir");
		columns.add("Nod Status");
		columns.add("Nod Extra1");
		columns.add("Nod Extra2");
		columns.add("Nod Caches");
		columns.add("Nod User Name");
		columns.add("Nod Dttm");
		return columns;
	}

	@Override
	public Node createEmptyDbo()
	{
		return new NodeImpl();
	}

	@Override
	public void checkDuplicates(Node dbo)
	{
		checkDuplicateGM(dbo.getNodName(), "nod_name", dbo.getPk());
		checkDuplicateGM(dbo.getNodBk(), "nod_bk", dbo.getPk());
	}

	@Override
	public int create(Node node, int usrId)
	{
		return create(null, node, usrId);
	}

	public int create(Connection connection, Node node, int usrId)
	{
		int maxId = PrimaryKeyHelper.getPKH().getPrimaryKey("nod_id", "node");
		String bk = node.getDisplayField();

		String query = " insert into node values(?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramValues = new Object[] { maxId, node.getNodName(), node.getNodDatadir(), node.getNodStatus(), node.getNodExtra1(), node.getNodExtra2(), node.getNodCaches(), bk, usrId, null, new DateTime(), null };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);
		AudDBHelper.getDB().executeUpdateOneRow(query, paramValues);
		return maxId;
	}

	@Override
	public void update(Node node, int usrId)
	{
		update(null, node, usrId);
	}

	public void update(Connection connection, Node node, int usrId)
	{
		String query = " update node set  nod_name = ? , nod_datadir = ? , nod_status = ? , nod_extra1 = ? , nod_extra2 = ? , nod_caches = ? , nod_modified_usr_id = ? , nod_modified_dttm = ?  where nod_id = ?  ";
		Object[] paramValues = new Object[] { node.getNodName(), node.getNodDatadir(), node.getNodStatus(), node.getNodExtra1(), node.getNodExtra2(), node.getNodCaches(), usrId, new DateTime(), node.getNodId() };
		RefDBHelper.getDB().executeUpdateOneRow(connection, query, paramValues);

		String insertQuery = " insert into node values(?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] insertParamValues = new Object[] { node.getNodId(), node.getNodName(), node.getNodDatadir(), node.getNodStatus(), node.getNodExtra1(), node.getNodExtra2(), node.getNodCaches(), node.getNodBk(), usrId, usrId, new DateTime(), new DateTime() };
		AudDBHelper.getDB().executeUpdateOneRow(insertQuery, insertParamValues);
	}

	public List<Node> fetchAll()
	{
		String query = " select * from node ";
		return RefDBHelper.getDB().fetchList(query, NodeImpl.class);
	}

	public List<Node> fetchAuditRowsByPk(int id)
	{
		String query = " select * from node where nod_id=? order by nod_created_dttm ";
		return AudDBHelper.getDB().fetchList(query, id, NodeImpl.class);
	}

	public Node fetchByPk(int nodId)
	{
		return fetchByPk(null, nodId);
	}

	public Node fetchByPk(Connection connection, int nodId)
	{
		return RefDBHelper.getDB().fetchObject(connection, " select * from node where nod_id=? ", nodId, NodeImpl.class);
	}

	public Node fetchByBk(String nodBk)
	{
		return RefDBHelper.getDB().fetchObject(" select * from node where nod_bk=? ", nodBk, NodeImpl.class);
	}

	public Node fetchByDisplayField(String displayField)
	{
		return RefDBHelper.getDB().fetchObject(" select * from node where nod_name=? ", displayField, NodeImpl.class);
	}

	public List<String> fetchStringList()
	{
		return RefDBHelper.getDB().fetchStringList("nod_name", "node");
	}

}