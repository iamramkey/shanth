/**
 * Copyright SIGNA AB, STOCKHOLM, SWEDEN
 */

package se.signa.signature.helpers;

import java.lang.reflect.Method;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.common.SignatureException;

public class DbaReflectionHelper
{
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static SignatureDba getI(String tableName) throws SignatureException
	{
		try
		{
			String className = "se.signa.signature.gen.dba." + tableName + "Dba";
			Class clazz = Class.forName(className);
			Method method = clazz.getMethod("getI", null);
			SignatureDba dba = (SignatureDba) method.invoke(null, null);
			return dba;
		}
		catch (Exception e)
		{
			throw new SignatureException(e);
		}
	}

}
