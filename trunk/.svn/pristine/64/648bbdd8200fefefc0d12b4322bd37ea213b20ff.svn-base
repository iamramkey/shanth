/**
 * Copyright SIGNA AB, STOCKHOLM, SWEDEN
 */

package se.signa.signature.helpers;

import se.signa.signature.common.SignatureException;

import sun.misc.BASE64Decoder;

@SuppressWarnings("restriction")
public class CryptographicHelper
{
	public static String hashDecrypt(String encryptedData)
	{
		if (encryptedData.length() <= 12)
			throw new SignatureException("The ecrypted data length must be greater than 12 characters");

		try
		{
			String cipher = encryptedData.substring(12);
			BASE64Decoder decoder = new BASE64Decoder();
			return new String(decoder.decodeBuffer(cipher));

		}
		catch (Exception e)
		{
			throw new SignatureException(e);
		}
	}
}
