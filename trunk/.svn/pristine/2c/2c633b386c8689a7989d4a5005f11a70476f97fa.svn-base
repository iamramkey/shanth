package se.signa.signature.dio;

import javax.xml.bind.annotation.XmlRootElement;

import se.signa.signature.common.Constants;
import se.signa.signature.common.NegativeResultException;
import se.signa.signature.helpers.DataValidator;

@XmlRootElement
public class PasswordSettingsFetchInput
{
	public int usrId;
	public int ussSessionCode;

	public void validate()
	{
		mandate(usrId, "usrId", "usrId");
		mandate(ussSessionCode, "ussSessionCode", "ussSessionCode");
	}

	protected void mandate(Integer field, String fieldDisplayName, String fieldName)
	{
		if (!DataValidator.validateMandatoryInteger(field))
		{
			NegativeResult nr = new NegativeResult(Constants.ErrCode.INPUT_FIELD_INVALID, fieldDisplayName + " is a mandatory field", fieldName);
			throw new NegativeResultException(nr);
		}
	}

	public void checkRules()
	{

	}
}
