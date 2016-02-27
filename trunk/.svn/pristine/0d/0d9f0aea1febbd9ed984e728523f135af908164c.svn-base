package se.signa.signature.dio;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserModifyInput extends StigWebInput
{
	public Integer id;

	public void validate()
	{
		super.validate();
		mandate(id, "Update User Id", "updateUsrId");
	}

	/*@Override
	protected String getEntity()
	{
		return "user_tbl";
	}

	@Override
	protected String getAction()
	{
		return "deactivate";
	}*/
}
