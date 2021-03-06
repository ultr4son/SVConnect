package svx;

import com.mentor.systems.svx.ISVXComponentSignalGeneratorSingle;
import com.mentor.systems.svx.ISVXFactory;
import com.mentor.systems.svx.SVXStatusException;
import com.mentor.systems.svx.SVXTemporalSpecSignalGenerator;

import ccre.channel.EventInput;
import ccre.channel.EventOutput;
import ccre.channel.FloatOutput;
import ccre.log.Logger;
import ccre.time.Time;

/** 
 * A FloatOutput wrapper around a ISVXComponentSignalGeneratorSingle. Makes sure to send 
 * @author tthompso
 *
 */
public class SVXSingleGeneratorWrapper implements FloatOutput
{

	ISVXComponentSignalGeneratorSingle generator;
	float currentValue = 0.0f;
	/**
	 * 
	 */
	private static final long serialVersionUID = -9020607683263595506L;
	private EventOutput sendEvent = new EventOutput()
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 778319740206921193L;

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
	@Override
	public void set(float value) 
	{
		try 
		{
			currentValue = value;
			generator.set(value);
			
			
		} catch (SVXStatusException e) 
		{
			Logger.severe("SVX Error");
			Logger.severe(e.getStackTrace().toString());
		}
	}
	public void setWhenToSend(EventInput sendWhen)
	{
		sendWhen.send(sendEvent);
	}
	public SVXSingleGeneratorWrapper(ISVXFactory factory, String name, SVXTemporalSpecSignalGenerator generatorTemporalSpec, EventInput sendWhen)
	{
	
		sendWhen.send(sendEvent);
		try 
		{
			generator = factory.createSVXComponentSignalGeneratorSingle(name, SVXCommunicator.CHANNEL_NAME, generatorTemporalSpec, 0.0f); 
			
		} catch (SVXStatusException e) 
		{
			Logger.severe("SVX Error");
			Logger.severe(e.getStackTrace().toString());
		}
	}
	

}
