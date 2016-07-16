package test;

import com.mentor.systems.svx.ISVXFactory;
import com.mentor.systems.svx.ISVXNotification;
import com.mentor.systems.svx.SVXComponentTarget;
import com.mentor.systems.svx.SVXNativeJNI;
import com.mentor.systems.svx.SVXStatusException;
import com.mentor.systems.svx.SVXTemporalSpecSignalConsumer;
import com.mentor.systems.svx.SVXTemporalSpecSignalGenerator;
import com.mentor.systems.svx.SVXTimeRange;

public class SVXErrorTest {

	public static void main(String[] args) {
		try
		{
			
			SVXComponentTarget target = new SVXComponentTarget(false); //Target is not the first node in the stack.
			ISVXFactory factory = target.getFactory();
			ISVXNotification dummyNotification = new ISVXNotification()
					{
						@Override
						public void notification() throws SVXStatusException {
						
						}
				
					};
			SVXTemporalSpecSignalConsumer consumerSpec = new SVXTemporalSpecSignalConsumer(
					SVXTimeRange.SVX_MILLI_TIME_RANGE, 1, 
					SVXTimeRange.SVX_MILLI_TIME_RANGE, 1, 
					SVXTimeRange.SVX_ZERO_TIME_RANGE, 0, 
					SVXTimeRange.SVX_ZERO_TIME_RANGE, 0);
			
			SVXTemporalSpecSignalGenerator generatorSpec = new SVXTemporalSpecSignalGenerator(SVXTimeRange.SVX_MILLI_TIME_RANGE, 1, 
					SVXTimeRange.SVX_MILLI_TIME_RANGE, 1, 
					SVXTimeRange.SVX_ZERO_TIME_RANGE, 0);
			
			Character name = 'a'; 
			String CHANNEL_NAME = "DEFAULT_CHANNEL";
			
			
			factory.createSVXComponentSignalConsumerBoolean(CHANNEL_NAME, name.toString(), consumerSpec, dummyNotification, false);
			name++;
			
			factory.createSVXComponentSignalConsumerDouble(CHANNEL_NAME, name.toString(), consumerSpec, dummyNotification, 0.0);
			name++;
			
			factory.createSVXComponentSignalConsumerInteger(CHANNEL_NAME, name.toString(), consumerSpec, dummyNotification, 0);	
			name++;
			
			factory.createSVXComponentSignalConsumerSingle(CHANNEL_NAME, name.toString(), consumerSpec, dummyNotification, 0.0f);
			name++;
			
			factory.createSVXComponentSignalGeneratorDouble(CHANNEL_NAME, name.toString(), generatorSpec, 0.0);
			name++;
			
			factory.createSVXComponentSignalGeneratorSingle(CHANNEL_NAME, name.toString(), generatorSpec, 0.0f);
			name++;
			
			factory.createSVXComponentSignalGeneratorInteger(CHANNEL_NAME, name.toString(), generatorSpec, 0);
			name++;
			
			SVXNativeJNI svxNative = new SVXNativeJNI();
			svxNative.constructSVXComponentSignalGeneratorBooleanDyn(name.toString(), CHANNEL_NAME, generatorSpec, false); // This one failed.
			name++;
			
			factory.createSVXComponentSignalGeneratorBoolean(CHANNEL_NAME, name.toString(), generatorSpec, false); // This one failed.
			name++;
		}
		catch(SVXStatusException ex)
		{
			ex.printStackTrace();
		}
	}

}
