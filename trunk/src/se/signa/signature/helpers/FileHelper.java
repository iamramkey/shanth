package se.signa.signature.helpers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

import se.signa.signature.common.Constants;
import se.signa.signature.common.SignatureException;

public class FileHelper
{
	// store the char set the server uses
	public static final Charset platformCharset = Charset.defaultCharset();
	public static final String fileSeperator = System.getProperty("file.separator");
	public static final String pathSeperator = System.getProperty("path.separator");

	public static BufferedReader getFileReader(File file, Charset charset) throws SignatureException
	{
		try
		{
			return new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
		}
		catch (FileNotFoundException e)
		{
			throw new SignatureException(e);
		}
	}

	public static BufferedWriter getFileWriter(File file, Charset charset) throws SignatureException
	{
		try
		{
			return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
		}
		catch (FileNotFoundException e)
		{
			throw new SignatureException(e);
		}
	}

	public static String getFileContents(File file) throws SignatureException
	{
		Reader reader = getFileReader(file, platformCharset);

		StringBuilder sb;
		try
		{
			char[] buffer = new char[8 * 1024];
			int read = -1;

			sb = new StringBuilder();
			while ((read = reader.read(buffer)) != -1)
			{
				sb.append(buffer, 0, read);
			}
		}
		catch (IOException e)
		{
			throw new SignatureException(e);
		}
		finally
		{
			try
			{
				reader.close();
			}
			catch (IOException e)
			{
				throw new SignatureException(e);
			}
		}
		return sb.toString();
	}

	public static void wirteFileContents(String contents, File file) throws SignatureException
	{
		Writer writer = getFileWriter(file, platformCharset);

		try
		{
			writer.write(contents);
		}
		catch (IOException e)
		{
			throw new SignatureException(e);
		}
		finally
		{
			try
			{
				writer.close();
			}
			catch (IOException e)
			{
				throw new SignatureException(e);
			}
		}
	}

	public String getFileContentsForUi(File file) throws SignatureException
	{
		StringBuffer stringBuffer = new StringBuffer();
		try
		{
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null)
			{
				stringBuffer.append(line);
				stringBuffer.append(Constants.APPEND_BR);
			}
			fileReader.close();
		}
		catch (IOException e)
		{
			throw new SignatureException(e);
		}
		return (stringBuffer.toString());
	}

}
