package se.signa.signature.common;

import java.util.List;

import se.signa.signature.common.Constants.ErrCode;
import se.signa.signature.dio.NegativeResult;
import se.signa.signature.helpers.RefDBHelper;

public abstract class SignatureDba<T extends SignatureDbo>
{

	public String tableName = null;

	public String tablePrefix = null;

	public abstract List<T> fetchAll();

	public abstract List<String> fetchStringList();

	public abstract List<T> fetchAuditRowsByPk(int id);

	public abstract T fetchByPk(int id);

	public abstract T fetchByBk(String bk);

	public abstract T fetchByDisplayField(String displayField);

	public abstract List<String> getColumns();

	public void checkDuplicate(String fieldValue, String displayName, String fieldName, String columnName)
	{
		String countQuery = " from " + tableName + " where " + columnName + " = ? ";
		int count = RefDBHelper.getDB().fetchCount(countQuery, fieldValue);
		if (count > 0)
			throw new NegativeResultException(new NegativeResult(ErrCode.INPUT_FIELD_INVALID, displayName + " with value '" + fieldValue + "' already exists in the system.", fieldName));
	}

	public void checkDuplicate(String fieldValue, String displayName, String fieldName, String columnName, int pk)
	{
		String countQuery = " from " + tableName + " where " + columnName + " = ? and " + tablePrefix + "_id != ?";
		int count = RefDBHelper.getDB().fetchCount(countQuery, new Object[] { fieldValue, pk });
		if (count > 0)
			throw new NegativeResultException(new NegativeResult(ErrCode.INPUT_FIELD_INVALID, displayName + " with value '" + fieldValue + "' already exists in the system.", fieldName));
	}

	public int count()
	{
		String countQuery = " from " + tableName;
		return RefDBHelper.getDB().fetchCount(countQuery);
	}

	public void checkDuplicateGM(String columnValue, String columnName, Integer pk)
	{
		if (pk == null || pk == 0)
			checkDuplicate(columnValue, columnName, columnName, columnName);//TODO: use proper display name
		else
			checkDuplicate(columnValue, columnName, columnName, columnName, pk);//TODO: use proper display name
	}

	public void checkDuplicates(T dbo)
	{
		throw new SignatureException(" Check Duplicates not implemented ");
	}

	public abstract T createEmptyDbo();

	public abstract int create(T dbo, int usrId);

	public abstract void update(T dbo, int usrId);

}
