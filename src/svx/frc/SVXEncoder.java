package svx.frc;

import ccre.channel.BooleanInput;
import ccre.channel.CancelOutput;
import ccre.channel.DerivedFloatInput;
import ccre.channel.EventInput;
import ccre.channel.EventOutput;
import ccre.channel.FloatInput;
import svconnect.RoboRioBoard;

/**
 * An encoder that uses RoboRioBoard based BooleanInputs. 
 * @author tthompso
 *
 */
public class SVXEncoder extends DerivedFloatInput{
	BooleanInput aChannel;
	BooleanInput bChannel;
	int stepCount = 0;
	int prevState = 0;
	boolean reverse;
	EventOutput resetEvent = new EventOutput()
			{

				@Override
				public void event() {
					stepCount = 0;
				}
		
			};
	
		
	public SVXEncoder(int aChannel, int bChannel, boolean reverse, EventInput resetWhen, EventInput updateOn)
	{
		super(updateOn);
		this.aChannel = RoboRioBoard.digitalInputPin(aChannel);
		this.bChannel = RoboRioBoard.digitalInputPin(bChannel);
		this.reverse = reverse;
		resetWhen.send(resetEvent);
	}


	@Override
	protected float apply() {	
		int aValue = (int) aChannel.toFloat(0.0f, 1.0f).get();
		int bValue = (int) bChannel.toFloat(0.0f, 1.0f).get();
		int currentState = aValue*2 + bValue;
		
		switch(prevState)
		{
		case 0:
			if(currentState == 1)
			{
				stepCount--;
			}
			else if(currentState == 2)
			{
				stepCount++;
			}
			break;
		case 1:
			if(currentState == 3)
			{
				stepCount--;
			}
			else if(currentState == 0)
			{
				stepCount++;
			}
			break;
		case 2:
			if(currentState == 0)
			{
				stepCount--;
			}
			if(currentState == 3)
			{
				stepCount++;
			}
			break;
		case 3:
			if(currentState == 2)
			{
				stepCount--;
			}
			if(currentState == 1)
			{
				stepCount ++;
			}
			break;
		default: 
		
		}
		prevState = currentState;
		return stepCount;
	}	
	
}
