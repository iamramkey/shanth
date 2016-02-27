package se.signa.signature.dio;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import se.signa.signature.helpers.headers.BreadCrumModel;
import se.signa.signature.helpers.headers.UserHistoryModel;

@XmlRootElement
public class MasterSearchResult extends PositiveResult
{
	public List<String> headers;
	public List<List<Object>> data;
	public List<ActionDataModel> headerActions;
	public List<ActionDataModel> rowActions;
	public List<FormData> form;
	public List<UserHistoryModel> history;
	public List<BreadCrumModel> breadCrum;

	public MasterSearchResult()
	{
		super();
	} // JAXB needs this

	public MasterSearchResult(String notification, List<String> headers, List<List<Object>> data, List<ActionDataModel> headerActions, List<ActionDataModel> rowActions, List<FormData> form, List<UserHistoryModel> history, List<BreadCrumModel> breadCrum)
	{
		super(notification);
		this.headers = headers;
		this.data = data;
		this.headerActions = headerActions;
		this.rowActions = rowActions;
		this.form = form;
		this.history = history;
		this.breadCrum = breadCrum;
	}

}