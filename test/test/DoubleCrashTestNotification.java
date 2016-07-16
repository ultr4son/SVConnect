package test;

import com.mentor.systems.svx.ISVXComponentSignalConsumerDouble;
import com.mentor.systems.svx.ISVXFactory;
import com.mentor.systems.svx.ISVXNotification;
import com.mentor.systems.svx.SVXStatusException;
import com.mentor.systems.svx.SVXTemporalSpecSignalConsumer;
import com.mentor.systems.svx.SVXTimeRange;

public class DoubleCrashTestNotification implements ISVXNotification{
	ISVXComponentSignalConsumerDouble consumer;
	@Override
	public void notification() throws SVXStatusException {
		double value = consumer.get();
	}
	public DoubleCrashTestNotification(ISVXFactory factory) {
		SVXTemporalSpecSignalConsumer consumerSpec = new SVXTemporalSpecSignalConsumer(
				SVXTimeRange.SVX_MILLI_TIME_RANGE, 1,
				SVXTimeRange.SVX_MILLI_TIME_RANGE, 1,
				SVXTimeRange.SVX_ZERO_TIME_RANGE, 0, 
				SVXTimeRange.SVX_ZERO_TIME_RANGE, 0);

		try {
			consumer = factory.createSVXComponentSignalConsumerDouble("TEST","DEFAULT_CHANNEL", consumerSpec , this, 0.0);
		} catch (SVXStatusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
