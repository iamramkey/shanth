package se.signa.signature.helpers;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import se.signa.signature.common.DbaClassMapper;
import se.signa.signature.common.SignatureDba;
import se.signa.signature.common.SignatureException;

public class DbaInstanceHelper
{

	private static DbaInstanceHelper INSTANCE = null;
	private static final Logger logger = Logger.getLogger(DbaInstanceHelper.class);

	private Map<String, SignatureDba> dbaInstances = null;
	private Map<String, SignatureDba> dbaPrefixInstances = null;

	// this is a singleton class- single entry point
	public synchronized static DbaInstanceHelper getI() throws SignatureException
	{
		if (INSTANCE == null)
			INSTANCE = new DbaInstanceHelper();
		return INSTANCE;
	}

	private DbaInstanceHelper() throws SignatureException
	{
		dbaInstances = new HashMap<String, SignatureDba>();
		dbaPrefixInstances = new HashMap<String, SignatureDba>();
		logger.info("Dba Instance Helper initialised");
	}

	public SignatureDba getDba(String name)
	{
		if (dbaInstances.containsKey(name))
			return dbaInstances.get(name);

		SignatureDba newInstance = createDbaInstance(name);
		dbaInstances.put(name, newInstance);
		return newInstance;
	}

	private SignatureDba createDbaInstance(String name)
	{
		try
		{
			String className = "se.signa.signature.gen.dba." + name + "Dba";
			logger.debug("Searching for class " + className);
			Class<?> dbaClazz = Class.forName(className);

			Method method = dbaClazz.getMethod("getI");
			SignatureDba dbaObject = (SignatureDba) method.invoke(null, null);
			return dbaObject;
		}
		catch (Throwable e)
		{
			throw new SignatureException("Unable to get Dba Impl instance for :" + name, e);
		}
	}

	public SignatureDba getDbaByPrefix(String prefix)
	{
		if (dbaPrefixInstances.containsKey(prefix))
			return dbaPrefixInstances.get(prefix);

		SignatureDba newInstance = createDbaInstanceByPrefix(prefix);
		dbaPrefixInstances.put(prefix, newInstance);
		return newInstance;
	}

	private SignatureDba createDbaInstanceByPrefix(String prefix)
	{
		try
		{
			Class<?> dbaClazz = Class.forName(DbaClassMapper.getI().getClassByPrefix(prefix));
			Method method = dbaClazz.getMethod("getI", null);
			SignatureDba dbaObject = (SignatureDba) method.invoke(null, null);
			return dbaObject;
		}
		catch (Throwable e)
		{
			throw new SignatureException("Unable to get Dba Impl instance for :" + prefix, e);
		}
	}

}
