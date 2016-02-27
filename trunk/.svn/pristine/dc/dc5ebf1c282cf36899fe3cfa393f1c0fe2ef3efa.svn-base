package se.signa.signature.common;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.joda.time.DateTime;

import se.signa.signature.dio.MasterSaveInput;

public abstract class SignatureDbo
{
	private int n = 7;
	private int startNum = (int) Math.pow(10, n - 1);;
	private int range = (int) (Math.pow(10, n) - startNum + 1);
	private Random randGen = new Random();

	protected DateTime getDateTime(Timestamp timestamp)
	{
		if (timestamp == null)
			return null;
		return new DateTime(timestamp);
	}

	protected boolean getBoolean(String value)
	{
		if (value.equalsIgnoreCase(Constants.BOOLEANTRUE) || value.equalsIgnoreCase(Constants.BOOLEANONE))
			return true;
		return false;
	}

	protected Integer getInteger(Integer val)
	{
		if (val <= 0)
			return null;
		return val;
	}

	protected String getString(Long val)
	{
		if (val == null)
			return null;
		return String.valueOf(val);
	}

	protected String getString(String value)
	{
		if (value == null)
			return null;
		return value;
	}

	protected String getString(Integer value)
	{
		if (value == null)
			return null;
		return String.valueOf(value);
	}

	protected Object getString(boolean value)
	{
		return String.valueOf(value);
	}

	public abstract String getDisplayField();

	public abstract int getPk();

	public abstract void setPk(int pk);

	public abstract String getBk();

	public abstract List<String> toStringList();

	public abstract List<String> toAuditStringList();

	public abstract Object getProperty(String propertyName);

	public abstract void populateFrom(MasterSaveInput input);

	public abstract Map<String, Object> populateTo();

	protected int getRandomNumber()
	{
		int randomNum = randGen.nextInt(range) + startNum;
		return randomNum;
	}

}
