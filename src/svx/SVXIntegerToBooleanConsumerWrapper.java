package svx;

import com.mentor.systems.svx.ISVXComponentSignalConsumerInteger;
import com.mentor.systems.svx.ISVXFactory;
import com.mentor.systems.svx.SVXStatusException;
import com.mentor.systems.svx.SVXTemporalSpecSignalConsumer;

import ccre.channel.BooleanInput;

/**
 * A workaround for the fact that Boolean consumers will crash the JVM if their get() method is called. Uses an integer conusmer whose value is converted to an integer.
 * @author tthompso
 *
 */
public class SVXIntegerToBooleanConsumerWrapper extends SVXConsumerWrapper implements BooleanInput{
	
	boolean currentValue = false;
	ISVXComponentSignalConsumerInteger consumer;
	@Override 
	public void notification()throws SVXStatusException
	{
	
		int value = consumer.get();
		if(value >= 1)
		{
			currentValue = true;
		}
		else
		{
			currentValue = false;
		}
		System.out.println("Bool converter: " + currentValue);
		super.notification();
		
	}
	@Override
	public boolean get() {
		return currentValue;
	}
	public SVXIntegerToBooleanConsumerWrapper(ISVXFactory factory, String name, SVXTemporalSpecSignalConsumer temporalSpec)
	{
		try {
			consumer = factory.createSVXComponentSignalConsumerInteger(name, SVXCommunicator.CHANNEL_NAME, temporalSpec, this, 0);
		} catch (SVXStatusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
