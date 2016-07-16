package test;
import com.mentor.systems.svx.ISVXComponentSignalConsumerBoolean;
import com.mentor.systems.svx.ISVXFactory;
import com.mentor.systems.svx.ISVXNotification;
import com.mentor.systems.svx.SVXStatusException;
import com.mentor.systems.svx.SVXTemporalSpecSignalConsumer;
import com.mentor.systems.svx.SVXTimeRange;

public class BooleanCrashTestNotification implements ISVXNotification
{
	String CHANNEL_NAME = "DEFAULT_CHANNEL";
	ISVXComponentSignalConsumerBoolean consumer;
	
	@Override
	public void notification() throws SVXStatusException {
		boolean value = consumer.get();
	}
	public BooleanCrashTestNotification(ISVXFactory factory)
	{
		SVXTemporalSpecSignalConsumer consumerSpec = new SVXTemporalSpecSignalConsumer(
				SVXTimeRange.SVX_MILLI_TIME_RANGE, 1,
				SVXTimeRange.SVX_MILLI_TIME_RANGE, 1,
				SVXTimeRange.SVX_ZERO_TIME_RANGE, 0, 
				SVXTimeRange.SVX_ZERO_TIME_RANGE, 0);
		try {
			consumer = factory.createSVXComponentSignalConsumerBoolean("TEST", CHANNEL_NAME, consumerSpec, this, false);
			
		} catch (SVXStatusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
	