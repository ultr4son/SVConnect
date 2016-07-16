package test;

import java.util.function.Consumer;

import com.mentor.systems.svx.ISVXComponentSignalConsumerBoolean;
import com.mentor.systems.svx.ISVXFactory;
import com.mentor.systems.svx.ISVXNotification;
import com.mentor.systems.svx.SVXComponentTarget;
import com.mentor.systems.svx.SVXStatusException;
import com.mentor.systems.svx.SVXTemporalSpecChannel;
import com.mentor.systems.svx.SVXTemporalSpecSignalConsumer;
import com.mentor.systems.svx.SVXTimeRange;

public class SVXBooleanCrashTest {
	String CHANNEL_NAME = "DEFAULT_CHANNEL";

	
	public static void main(String[] args) {
		try {
			
			SVXComponentTarget target = new SVXComponentTarget(false);
			
			ISVXFactory factory = target.getFactory();
			
			new BooleanCrashTestNotification(factory);
			
			SVXTemporalSpecChannel channelSpec= new SVXTemporalSpecChannel(SVXTimeRange.SVX_ZERO_TIME_RANGE, 0, SVXTimeRange.SVX_ZERO_TIME_RANGE, 0,SVXTimeRange.SVX_ZERO_TIME_RANGE, 0);
			
			factory.createSVXComponentChannel("DEFAULT_CHANNEL", channelSpec);
			factory.createSVXComponentConnectionAcceptorSockets(7818);
			
			target.execute();
		} catch (SVXStatusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
	}

}
