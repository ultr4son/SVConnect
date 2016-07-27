package svx;

import com.mentor.systems.svx.ISVXComponentSignalGeneratorBoolean;
import com.mentor.systems.svx.ISVXFactory;
import com.mentor.systems.svx.SVXStatusException;
import com.mentor.systems.svx.SVXTemporalSpecSignalGenerator;

import ccre.channel.BooleanOutput;
import ccre.channel.EventInput;
import ccre.channel.EventOutput;
import ccre.time.Time;

/**
 * A BooleanOutput wrapper around a ISVXComponentSignalGeneratorBoolean. 
 * @author tthompso
 *
 */
public class SVXBooleanGeneratorWrapper implements BooleanOutput
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6882597992327515740L;
	ISVXComponentSignalGeneratorBoolean generator;
	boolean currentValue;
	private EventOutput sendEvent = new EventOutput()
			{

				/**
				 * 
				 */
				private static final long serialVersionUID = 7885102682541131323L;

				@Override
				public void event() {
					try {
						//generator.set(currentValue);
						System.out.println("Sending : " + currentValue + " Time is " + Time.currentTimeMillis());
						generator.send();
					} catch (SVXStatusException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		
			};
	/**
	 * Sets the generator. Will not send set value until the next send event.
	 */
	@Override
	public void set(boolean value) {
		try {
			generator.set(value);
			currentValue = value;
		}
		catch (SVXStatusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public SVXBooleanGeneratorWrapper(ISVXFactory factory, String name, SVXTemporalSpecSignalGenerator spec, EventInput sendWhen)
	{
		sendWhen.send(sendEvent);
		try {
			generator = factory.createSVXComponentSignalGeneratorBoolean(name, SVXCommunicator.CHANNEL_NAME, spec, false);
		} catch (SVXStatusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

}
