/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dbo;

import java.sql.ResultSet;

import se.signa.signature.dio.MasterSaveInput;
import se.signa.signature.gen.dba.JobTypeDba;
import se.signa.signature.gen.dbo.ReferenceFilePollerSettings;
import se.signa.signature.helpers.DisplayFieldHelper;

public class ReferenceFilePollerSettingsImpl extends ReferenceFilePollerSettings
{
	public ReferenceFilePollerSettingsImpl()
	{
	}

	public ReferenceFilePollerSettingsImpl(ResultSet rs)
	{
		populateFromResultSet(rs);
	}
	
	@Override
	public void populateFrom(MasterSaveInput input)
	{
		setPk(input.id);
		setRpsName(input.getString("rpsName"));
		setRpsSourceDir(input.getString("rpsSourceDir"));
		setRpsTargetDir(input.getString("rpsTargetDir"));
		setRpsFreqSecs(input.getInteger("rpsFreqSecs"));
		setRpsPollerJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("rpsPollerJbt")));
		setRpsRefDataComparatorJbtId(DisplayFieldHelper.getI().getPk(JobTypeDba.class, input.getString("rpsRefDataComparatorJbt")));
		setRpsStabilitySecs(getRpsStabilitySecs());
		setRpsStabilityRetry(getRpsStabilityRetry());
	}

}