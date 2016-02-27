package se.signa.signature.dio;

import javax.xml.bind.annotation.XmlRootElement;

import se.signa.signature.common.Constants.ErrCode;
import se.signa.signature.common.NegativeResultException;
import se.signa.signature.helpers.DataValidator;

@XmlRootElement
public class LogInInput
{
	public String usrName;
	public String usrPassword;

	public void validate()
	{
		mandate(usrName, "Username", "usrName");
		mandate(usrPassword, "Password", "usrPassword");
	}

	protected void mandate(Integer field, String fieldDisplayName, String fieldName)
	{
		if (!DataValidator.validateMandatoryInteger(field))
		{
			NegativeResult nr = new NegativeResult(ErrCode.INPUT_FIELD_MISSING, fieldDisplayName + " is a mandatory field", fieldName);
			throw new NegativeResultException(nr);
		}
	}

	protected void mandate(String field, String fieldDisplayName, String fieldName)
	{
		if (!DataValidator.validateMandatoryString(field))
		{
			NegativeResult nr = new NegativeResult(ErrCode.INPUT_FIELD_MISSING, fieldDisplayName + " is a mandatory field", fieldName);
			throw new NegativeResultException(nr);
		}
	}
}
