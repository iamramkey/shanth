package se.signa.signature.helpers;

import java.util.List;

import org.joda.time.DateTime;

public class DataValidator
{

	public static boolean validateMandatoryString(String field)
	{
		if (field == null)
			return false;
		if (field.length() <= 0)
			return false;
		return true;
	}

	public static boolean validateMandatoryDateTime(DateTime field)
	{
		if (field == null)
			return false;
		if (field.equals(0))
			return false;
		return true;
	}

	public static Integer getMandatoryInteger(String field)
	{
		try
		{
			return Integer.parseInt(field);
		}
		catch (NumberFormatException nfe)
		{
			return null;
		}
	}

	public static boolean validateMandatoryInteger(Integer field)
	{
		if (field == null)
			return false;
		if (field <= 0)
			return false;
		return true;
	}

	public static boolean validateMandatoryBoolean(Boolean field)
	{
		if (field == null)
			return false;
		return true;
	}

	public static boolean validateMandatoryList(List<?> field)
	{
		if (field == null)
			return false;
		if (field.size() <= 0)
			return false;
		return true;
	}

	public static boolean validateMandatoryDouble(Double field)
	{
		if (field == null)
			return false;
		if (field.intValue() < 0)
			return false;
		return true;
	}
}