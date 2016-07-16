package test;

import ccre.channel.FloatInput;
import ccre.channel.FloatOutput;
import svconnect.Device;

/**
 * A representation RoboRio shaped PID controller on a schematic.
 * @author tthompso
 *
 */
public class SimplifiedRoboRio extends Device {
	static final String SETPOINT_PIN_NAME = "SETPOINT";
	static final String FEEDBACK_PIN_NAME = "FEEDBACK";
	static final String CONTROL_PIN_NAME = "CONTROL";
	/**
	 * 
	 * @return A FloatInput representation of the setpoint input of a RoboRio shaped PID controller.
	 */
	public static FloatInput setpointPin()
	{
		return floatInputPin(SETPOINT_PIN_NAME);
	}
	/**
	 * 
	 * @return A FloatInput representation of the feedback input of a RoboRio shaped PID controller.
	 */
	public static FloatInput feedbackPin()
	{
		return floatInputPin(FEEDBACK_PIN_NAME);
	}
	/**
	 * 
	 * @return A FloatOutput representation of the control output of a RoboRio shaped PID controller
	 */
	public static FloatOutput controlPin()
	{
		return floatOutputPin(CONTROL_PIN_NAME);
	}
	private SimplifiedRoboRio()
	{
		super();
	}
}
