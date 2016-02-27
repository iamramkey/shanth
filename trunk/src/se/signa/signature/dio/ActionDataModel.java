package se.signa.signature.dio;

import se.signa.signature.gen.dbo.MasterSearchAction;

public class ActionDataModel
{

	public String label = null;
	public String function = null;
	public String param = null;
	public String visible = null;
	public String colour = null;
	public String icon = null;

	public ActionDataModel(MasterSearchAction msa)
	{
		super();
		this.colour = msa.getMsaColour();
		this.icon = msa.getMsaIcon();
		this.label = msa.getMsaName();
		this.visible = msa.getMsaVisible();
		this.function = msa.getMsaFunction();
		this.param = msa.getMsaParam();
	}

}
