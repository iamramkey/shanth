package se.signa.signature.dio;

import javax.xml.bind.annotation.XmlRootElement;

import se.signa.signature.gen.dbo.MasterSearchForm;

@XmlRootElement
public class FormData
{

	public class FDType
	{
		public static final String ID = "id";
		public static final String INT = "int";
		public static final String HIDDEN = "hidden";
		public static final String STRING = "string";
		public static final String SELECT = "select";
		public static final String DECIMAL = "decimal";
		public static final String BOOLEAN = "boolean";
		public static final String DTTM = "dttm";
	}

	public class FDRegex
	{
		public static final String INT_MAN = "^[0-9]+$";
		public static final String INT_OPT = "^[0-9]+$";
		public static final String DECIMAL_MAN = "^[0-9]+$";
		public static final String DECIMAL_OPT = "^[0-9]+$";
		public static final String BOOLEAN = "^[0-9]+$";
		public static final String DTTM_MAN = "^[0-9]+$";
		public static final String DTTM_OPT = "^[0-9]+$";
		public static final String STRING_MAN = ".+";
		public static final String STRING_OPT = ".*";
	}

	public String id;
	public String label;
	public boolean isMandatory;
	public String type;
	public String regex;

	public FormData(String id, String label, boolean isMandatory, String type, String regex)
	{
		super();
		this.id = id;
		this.label = label;
		this.isMandatory = isMandatory;
		this.type = type;
		this.regex = regex;
	}

	public FormData(MasterSearchForm msf)
	{
		super();
		this.id = msf.getMsfIdentifier();
		this.label = msf.getMsfName();
		this.isMandatory = msf.getMsfMandatory();
		this.type = msf.getMsfType();
		this.regex = "TODO:using this ?";
	}
}
