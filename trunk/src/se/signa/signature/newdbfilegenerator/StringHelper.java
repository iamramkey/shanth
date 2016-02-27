package se.signa.signature.newdbfilegenerator;

public class StringHelper
{

	public static String trimToLength(String input, int length)
	{
		if (input == null)
			return null;
		if (input.length() < length)
			return input;
		return input.substring(0, length - 1);
	}

	public static String underScoreToCamelCase(String input)
	{
		input = input.toLowerCase();
		String[] parts = input.split("_");
		String camelCaseOutput = "";
		for (String part : parts)
		{
			part = part.trim();
			if (part != null && part.trim().length() > 0)
				camelCaseOutput = camelCaseOutput + part.substring(0, 1).toUpperCase() + part.substring(1).toLowerCase();
		}
		return camelCaseOutput;
	}

	public static String underScoreToCamelCaseDisplay(String input)
	{
		input = input.toLowerCase();
		String[] parts = input.split("_");
		String camelCaseOutput = "";
		for (String part : parts)
		{
			part = part.trim();
			if (part != null && part.trim().length() > 0)
				camelCaseOutput = camelCaseOutput + part.substring(0, 1).toUpperCase() + part.substring(1).toLowerCase() + " ";
		}
		return camelCaseOutput.trim();
	}

	public static String underScoreToCamelCaseVariableName(String input)
	{
		String[] parts = input.split("_");
		String convertedInput = "";
		String output = "";

		for (String part : parts)
		{
			part = part.trim();
			if (part != null && part.trim().length() > 0)
				convertedInput = convertedInput + part.substring(0, 1).toUpperCase() + part.substring(1).toLowerCase();
		}
		output = convertedInput.substring(0, 1).toLowerCase() + convertedInput.substring(1);
		return output;
	}

	public static String writeDataTypeName(String input)
	{
		String dataTypeNameOutput = "";
		if (input.equalsIgnoreCase("string"))
			dataTypeNameOutput = input.substring(0, 1).toUpperCase() + input.substring(1);
		else
			dataTypeNameOutput = input;
		return dataTypeNameOutput;
	}

	public static String writeIntegerDataType(String input)
	{
		String output = "";
		output = input.substring(0, 1).toUpperCase() + input.substring(1);
		return output;
	}

	public static String writeDoubleDataType(String input)
	{
		String output = "";
		output = input.substring(0, 1).toUpperCase() + input.substring(1);
		return output;
	}

	public static String writeDataTypeNameForGet(String input)
	{
		String dataTypeNameOutput = "";
		dataTypeNameOutput = input.substring(0, 1).toUpperCase() + input.substring(1);
		dataTypeNameOutput = input;

		return dataTypeNameOutput;
	}
}
