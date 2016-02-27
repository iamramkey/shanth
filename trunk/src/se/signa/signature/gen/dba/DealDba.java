/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/
package se.signa.signature.gen.dba;

import se.signa.signature.common.SignatureDba;
import se.signa.signature.dba.DealDbaImpl;

public abstract class DealDba extends SignatureDba
{
	private static DealDbaImpl INSTANCE;

	public static DealDbaImpl getI()
	{
		if (INSTANCE == null)
			INSTANCE = new DealDbaImpl();
		return INSTANCE;
	}

}