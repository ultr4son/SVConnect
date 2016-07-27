package svx;

import com.mentor.systems.svx.ISVXComponentSignalConsumerBoolean;
import com.mentor.systems.svx.ISVXFactory;
import com.mentor.systems.svx.SVXStatusException;
import com.mentor.systems.svx.SVXTemporalSpecSignalConsumer;

import ccre.channel.BooleanInput;

/**
 * BooleanInput wrapper around an SVXComponentSignalConsumerBoolean. Handles current value communication and notifications.
 * @author tthompso
 *
 */
public class SVXBooleanConsumerWrapper extends SVXConsumerWrapper implements BooleanInput{

	/**
	 * 
	 */
	private static final long serialVersionUID = 868912002111894621L;
	boolean currentValue = false;
	ISVXComponentSignalConsumerBoolean consumer;
	@Override
	public void notification() throws SVXStatusException {
		currentValue = consumer.get();
		super.notification();
	}
	
	@Override
	public boolean get() {
		return currentValue;
	}
	
	public SVXBooleanConsumerWrapper(ISVXFactory factory, String name, SVXTemporalSpecSignalConsumer spec)
	{
		try {
			consumer = factory.createSVXComponentSignalConsumerBoolean(name, SVXCommunicator.CHANNEL_NAME, spec, this, currentValue);
		} catch (SVXStatusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
