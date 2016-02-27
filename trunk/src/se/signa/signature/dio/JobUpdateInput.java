package se.signa.signature.dio;

import javax.xml.bind.annotation.XmlRootElement;

import se.signa.signature.common.Constants;
import se.signa.signature.common.NegativeResultException;
import se.signa.signature.gen.dba.JobDba;
import se.signa.signature.gen.dbo.Job;

@XmlRootElement
public class JobUpdateInput extends StigWebInput
{
	public Integer jobId;
	public Integer jobPriority;

	public void validate()
	{
		super.validate();
		mandate(jobId, "Job Id", "jobId");
		mandate(jobPriority, "Job Priority", "jobPriority");
	}

	public void checkRules()
	{
		Job job = JobDba.getI().fetchByPk(jobId);
		if (job == null)
		{
			NegativeResult nr = new NegativeResult(Constants.ErrCode.INPUT_FIELD_INVALID, "Job Id : " + jobId + " does not exist ", "jobId");
			throw new NegativeResultException(nr);
		}

		if (jobPriority == null)
		{
			NegativeResult nr = new NegativeResult(Constants.ErrCode.INPUT_FIELD_INVALID, "Job Priority : " + jobPriority + " should be a postitive integer ", "jobPriority");
			throw new NegativeResultException(nr);
		}
	}

	/*@Override
	protected String getEntity()
	{
		return "job";
	}

	@Override
	protected String getAction()
	{
		return "update";
	}*/
}
