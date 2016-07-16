package svx.frc;

import ccre.channel.CancelOutput;
import ccre.channel.DerivedFloatInput;
import ccre.channel.EventInput;
import ccre.channel.EventOutput;
import ccre.channel.FloatInput;
import svconnect.RoboRioBoard;

/**
 * A simulated encoder that relies on VHDL logic to handle step counting. Creates a port for encoder counts and whether or not to reverse.
 * @author tthompso
 *
 */
public class SVXPortEncoder extends DerivedFloatInput{
	FloatInput port = FloatInput.always(0.0f); 	
	float counts = 0.0f;
	boolean reverse;
	public SVXPortEncoder(int aChannel, int bChannel, boolean reverse, EventInput resetWhen, EventInput updateOn)
	{
		super(updateOn);
		RoboRioBoard.setEncoderReversePort(aChannel, bChannel, reverse);
		port = RoboRioBoard.encoderCountsPort(aChannel, bChannel);
	}

	@Override
	protected float apply() {
		if(port != null)
		{
			counts = port.get();
		}
		else
		{
			counts = 0.0f;
		}
		return counts;
	}

}
