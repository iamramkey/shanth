package se.signa.signature.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.common.SignatureDbo;
import se.signa.signature.dio.AuditDataCell;
import se.signa.signature.dio.StigWebResult;
import se.signa.signature.dio.ViewAuditInput;
import se.signa.signature.dio.ViewAuditResult;
import se.signa.signature.helpers.DbaReflectionHelper;

@Path("/viewauditinfo")
public class ViewAuditInfoApi
{
	private static Logger logger = Logger.getLogger(ViewAuditInfoApi.class);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StigWebResult viewAuditInfo(ViewAuditInput input)
	{
		long start = System.currentTimeMillis();
		logger.debug("View Audit Info api started!!!!");

		input.validate();
		input.checkRules();

		SignatureDba dba = DbaReflectionHelper.getI(input.tableName);
		List<String> columns = dba.getColumns();
		SignatureDbo dbo = dba.fetchByPk(input.id);
		List<String> auditStringList = dbo.toAuditStringList();

		List<SignatureDbo> auditRows = (List<SignatureDbo>) dba.fetchAuditRowsByPk(input.id);
		List<List<AuditDataCell>> data = new ArrayList<List<AuditDataCell>>();

		//first row to be handled seperately ( ALL YELLOW )
		List<String> row = auditRows.get(0).toStringList();
		List<AuditDataCell> auditRow = new ArrayList<AuditDataCell>();
		for (String cell : row)
		{
			AuditDataCell x = new AuditDataCell(cell, "blue");
			auditRow.add(x);
		}
		data.add(auditRow);

		List<String> previousRow;
		for (int i = 1; i < auditRows.size(); i++)
		{
			row = auditRows.get(i).toStringList();
			previousRow = auditRows.get(i - 1).toStringList();
			auditRow = new ArrayList<AuditDataCell>();
			for (int k = 0; k < row.size(); k++)
			{
				if (!previousRow.get(k).equalsIgnoreCase(row.get(k)))
					auditRow.add(new AuditDataCell(row.get(k), "red"));
				else
					auditRow.add(new AuditDataCell(row.get(k)));
			}
			data.add(auditRow);
		}

		long end = System.currentTimeMillis();
		logger.debug("Audit Info search took " + (end - start) + " ms");
		String notification = "Audit View fetched : " + data.size() + " rows";
		logger.info(notification);

		return new ViewAuditResult(notification, auditStringList, columns, data);
	}

}
