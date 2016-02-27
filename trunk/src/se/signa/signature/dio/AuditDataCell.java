package se.signa.signature.dio;

public class AuditDataCell
{
	public String value;
	public String colour = "black";

	public AuditDataCell(String value, String colour)
	{
		this.value = value;
		this.colour = colour;
	}

	public AuditDataCell(String value)
	{
		this.value = value;
	}

}
