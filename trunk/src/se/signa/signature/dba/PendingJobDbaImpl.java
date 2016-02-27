/**
* Copyright SIGNA AB, STOCKHOLM, SWEDEN
*/

package se.signa.signature.dba;

import se.signa.signature.dbo.PendingJobImpl;
import se.signa.signature.gen.dba.PendingJobDba;
import se.signa.signature.gen.dbo.PendingJob;
import se.signa.signature.helpers.RefDBHelper;

public class PendingJobDbaImpl extends PendingJobDba
{
	public PendingJob fetchByJobId(int jobId)
	{
		return RefDBHelper.getDB().fetchObjectIfExists(" select * from pending_job where job_id=? ", jobId, PendingJobImpl.class);
	}
}