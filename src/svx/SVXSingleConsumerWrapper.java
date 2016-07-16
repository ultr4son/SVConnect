package svx;

import com.mentor.systems.svx.ISVXComponentSignalConsumerSingle;
import com.mentor.systems.svx.ISVXFactory;
import com.mentor.systems.svx.SVXStatusException;
import com.mentor.systems.svx.SVXTemporalSpecSignalConsumer;

import ccre.channel.FloatInput;
import ccre.log.Logger;

/**
 * A FloatInput wrapper around a ISVXComponentSignalConsumerSingle. Handles getting and notification logic.
 * @author tthompso
 *
 */
public class SVXSingleConsumerWrapper  extends SVXConsumerWrapper implements FloatInput {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5359398362062018397L;
	private float currentValue = 0.0f;
	ISVXComponentSignalConsumerSingle consumer;
	String name;
	
	public SVXSingleConsumerWrapper(ISVXFactory factory, String name, SVXTemporalSpecSignalConsumer temporalSpec)
	{
		try {
			this.name = name;
			
			consumer = factory.createSVXComponentSignalConsumerSingle(name, SVXCommunicator.CHANNEL_NAME, temporalSpec, this, currentValue);
		} catch (SVXStatusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public float get() {
		return currentValue;
	}
	
	@Override
	public void notification() throws SVXStatusException {
		currentValue = consumer.get();
		Logger.info("My name is " + name + " and I just got " + currentValue);
		super.notification();
		
	}

}
