package se.signa.signature.dio;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import se.signa.signature.common.Constants;
import se.signa.signature.common.Constants.ErrCode;
import se.signa.signature.common.NegativeResultException;
import se.signa.signature.helpers.DataValidator;

@XmlRootElement
public class StigWebInput
{
	public int usrId;
	public int ussSessionCode;

	public StigWebInput()
	{
	}

	public void validate()
	{
		if (!DataValidator.validateMandatoryInteger(usrId))
		{
			NegativeResult nr = new NegativeResult(ErrCode.INPUT_FIELD_MISSING, "User Id is a mandatory field", "usrId");
			throw new NegativeResultException(nr);
		}
		if (!DataValidator.validateMandatoryInteger(ussSessionCode))
		{
			NegativeResult nr = new NegativeResult(ErrCode.INPUT_FIELD_MISSING, "User Session Code is a mandatory field", "ussSessionCode");
			throw new NegativeResultException(nr);
		}
	}

	protected void mandate(Integer field, String fieldDisplayName, String fieldName)
	{
		if (!DataValidator.validateMandatoryInteger(field))
		{
			NegativeResult nr = new NegativeResult(ErrCode.INPUT_FIELD_MISSING, fieldDisplayName + " is a mandatory field(positive) ", fieldName);
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

	protected void mandate(double field, String fieldDisplayName, String fieldName)
	{
		if (!DataValidator.validateMandatoryDouble(field))
		{
			NegativeResult nr = new NegativeResult(ErrCode.INPUT_FIELD_MISSING, fieldDisplayName + " is a mandatory field", fieldName);
			throw new NegativeResultException(nr);
		}
	}

	protected void mandate(List<?> field, String fieldDisplayName, String fieldName)
	{
		if (!DataValidator.validateMandatoryList(field))
		{
			NegativeResult nr = new NegativeResult(ErrCode.INPUT_FIELD_MISSING, fieldDisplayName + " is a mandatory field", fieldName);
			throw new NegativeResultException(nr);
		}
	}

	protected void mandate(boolean field, String fieldDisplayName, String fieldName)
	{
		if (!DataValidator.validateMandatoryBoolean(field))
		{
			NegativeResult nr = new NegativeResult(ErrCode.INPUT_FIELD_MISSING, fieldDisplayName + " is a mandatory field", fieldName);
			throw new NegativeResultException(nr);
		}
	}

	protected void checkEmail(String email, String fieldName)
	{
		int indexOfAt = email.indexOf('@');
		int indexOfDot = email.lastIndexOf('.');
		if (indexOfAt < 0 || indexOfDot < 0 || indexOfAt > indexOfDot)
		{
			NegativeResult nr = new NegativeResult(ErrCode.INPUT_FIELD_INVALID, "The email should be in proper format name@server.domain ", fieldName);
			throw new NegativeResultException(nr);
		}
	}

	protected void checkMobilePhone(String mobilePhone, String fieldName)
	{
		if (mobilePhone.length() != 10)
		{
			NegativeResult nr = new NegativeResult(ErrCode.INPUT_FIELD_INVALID, "The mobile no should be 10 digits", fieldName);
			throw new NegativeResultException(nr);
		}
	}

	public DateTime getDate(String dateValue, String displayField, String field)
	{
		try
		{
			DateTimeFormatter dtf = DateTimeFormat.forPattern(Constants.PARSE_INPUT_DTTM_FORMAT);
			return DateTime.parse(dateValue, dtf);
		}
		catch (Exception e)
		{
			throw new NegativeResultException(new NegativeResult(ErrCode.INPUT_FIELD_INVALID, "Entered DateTime format is invalid,Use the following Format" + Constants.PARSE_INPUT_DTTM_FORMAT, field));
		}
	}

	public void checkRules()
	{

	}
}
