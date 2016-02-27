package se.signa.signature.dio;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DDBStatBoardResult extends PositiveResult
{
	public String currentMargin;
	public String projectedMargin;
	public String volumeTrend;
	public String marginTrend;
	public String volumeTrendIcon;

	public DDBStatBoardResult()
	{
		super();
	}

	public DDBStatBoardResult(String notification, String currentMargin, String projectedMargin, String volumeTrend, String marginTrend, String volumeTrendIcon)
	{
		super(notification);
		this.currentMargin = currentMargin;
		this.projectedMargin = projectedMargin;
		this.volumeTrend = volumeTrend;
		this.marginTrend = marginTrend;
		this.volumeTrendIcon = volumeTrendIcon;
	}
}
