package se.signa.signature.dio;

import javax.xml.bind.annotation.XmlRootElement;

import se.signa.signature.common.Constants;
import se.signa.signature.common.NegativeResultException;
import se.signa.signature.gen.dba.NodeDba;
import se.signa.signature.gen.dbo.Node;

@XmlRootElement
public class NodeInput extends StigWebInput
{
	public Integer id;

	public void validate()
	{
		super.validate();
		mandate(id, "NodId", "nodId");
	}

	public void checkRules()
	{
		Node node = NodeDba.getI().fetchByPk(id);
		if (node == null)
		{
			NegativeResult nr = new NegativeResult(Constants.ErrCode.INPUT_FIELD_INVALID, "Node Id does not point to a valid node", "nodId");
			throw new NegativeResultException(nr);
		}

		if (!node.getNodStatus().equalsIgnoreCase(Constants.NODSTATUS_RUNNING))
		{
			NegativeResult nr = new NegativeResult(Constants.ErrCode.INPUT_FIELD_INVALID, "Node Id is not running, Cannot Shutdown", "nodId");
			throw new NegativeResultException(nr);
		}
	}

	/*@Override
	protected String getEntity()
	{
		return "node";
	}

	@Override
	protected String getAction()
	{
		return "update";
	}*/
}
