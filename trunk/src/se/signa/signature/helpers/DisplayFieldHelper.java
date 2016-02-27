package se.signa.signature.helpers;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.common.SignatureDbo;
import se.signa.signature.common.SignatureException;

public class DisplayFieldHelper
{

	private static DisplayFieldHelper INSTANCE;
	private Map<String, Map<Integer, String>> displayFieldData;
	private Map<String, Map<String, Integer>> pkData;

	public static DisplayFieldHelper getI()
	{
		if (INSTANCE == null)
			INSTANCE = new DisplayFieldHelper();
		return INSTANCE;
	}

	private DisplayFieldHelper()
	{
		displayFieldData = new HashMap<String, Map<Integer, String>>();
		pkData = new HashMap<String, Map<String, Integer>>();
	}

	public synchronized void clearAll()
	{
		displayFieldData.clear();
	}

	@SuppressWarnings("rawtypes")
	public synchronized String getDisplayField(Class dbaClazz, Integer id)
	{
		if (id == null || id <= 0)
			return null;
		Map<Integer, String> displayFieldDataForThisTable = displayFieldData.get(dbaClazz.getSimpleName());
		if (displayFieldDataForThisTable == null)
		{
			displayFieldDataForThisTable = new HashMap<Integer, String>();
			displayFieldData.put(dbaClazz.getSimpleName(), displayFieldDataForThisTable);
		}

		String displayField = displayFieldDataForThisTable.get(id);
		if (displayField == null)
		{
			displayField = fetchDisplayField(dbaClazz, id);
			displayFieldDataForThisTable.put(id, displayField);
		}
		return displayField;
	}

	@SuppressWarnings("rawtypes")
	private String fetchDisplayField(Class dbaClazz, int id)
	{
		try
		{
			Method method = dbaClazz.getMethod("getI", null);
			Object dbaObject = method.invoke(null, null);

			SignatureDbo dbo = ((SignatureDba) dbaObject).fetchByPk(id);
			return dbo.getDisplayField();
		}
		catch (Exception e)
		{
			throw new SignatureException(e);
		}

	}

	@SuppressWarnings("rawtypes")
	public Integer getPk(Class dbaClazz, String displayField)
	{
		if (displayField == null || displayField.length() <= 0)
			return null;
		Map<String, Integer> pkDataForThisTable = pkData.get(dbaClazz.getSimpleName());
		if (pkDataForThisTable == null)
		{
			pkDataForThisTable = new HashMap<String, Integer>();
			pkData.put(dbaClazz.getSimpleName(), pkDataForThisTable);
		}

		Integer pk = pkDataForThisTable.get(displayField);
		if (pk == null)
		{
			pk = fetchPk(dbaClazz, displayField);
			pkDataForThisTable.put(displayField, pk);
		}
		return pk;
	}

	@SuppressWarnings("rawtypes")
	private int fetchPk(Class dbaClazz, String displayField)
	{
		try
		{
			Method method = dbaClazz.getMethod("getI", null);
			Object dbaObject = method.invoke(null, null);

			SignatureDbo dbo = ((SignatureDba) dbaObject).fetchByDisplayField(displayField);
			return dbo.getPk();
		}
		catch (Exception e)
		{
			throw new SignatureException(e);
		}

	}
}
