package svconnect;

import ccre.channel.BooleanInput;
import ccre.channel.BooleanOutput;
import ccre.channel.FloatInput;
import ccre.channel.FloatOutput;
//TODO: Add support for MXP
/**
 * A representation of all the pins on a RoboRio board inside a schematic. Names generators and consumers by: [PORT_TYPE]_[NUMERICAL_ARGUMENT_1]_...
 * @author tthompso
 *
 */
public class RoboRioBoard extends Device {
	
	static final int MAX_DIO = 9;
	static final int MAX_PWM = 9;
	static final int MAX_RELAY = 4;
	static final int MAX_ANALOG_IN = 4;
	static final int MAX_JOYSTICK = 6;
	
	static final String DIGITAL_INPUT = "D_IN";
	static final String DIGITAL_OUTPUT = "D_OUT";
	static final String PWM = "PWM";
	static final String ANALOG = "ANALOG";
	static final String RELAY = "RELAY";
	static final String BATTERY = "BATTERY";
	static final String JOYSTICK_AXIS = "JOY_AXIS";
	static final String JOYSTICK_BUTTON = "JOY_BUTTON";
	static final String JOYSTICK_POV = "JOY_POV";
	static final String ENCODER = "ENCODER";
	static final String ENCODER_REVERSE = "ENCODER_REVERSE";
	private static String formatPinName(String prefix, int... numerics)
	{
		StringBuilder formatted = new StringBuilder(prefix);
		for(int number : numerics)
		{
			formatted.append("_");
			formatted.append(number);
		}
		return formatted.toString();
	}
	
	public static void setEncoderReversePort(int aChannel, int bChannel ,boolean reverse)
	{
		BooleanOutput pin = booleanOutputPin(formatPinName(ENCODER_REVERSE, aChannel, bChannel));
		pin.set(reverse);
	}
	
	public static FloatInput encoderCountsPort(int aChannel, int bChannel)
	{
		if(aChannel < 0 || aChannel > MAX_DIO || bChannel < 0 || bChannel > MAX_DIO)
		{
			throw new IllegalArgumentException("Channel out of range.");
		}
		return floatInputPin(formatPinName(ENCODER, aChannel, bChannel));
	}
	/**
	 * A representation of a joystick axis on a specific channel and axis. 
	 * @param channel The channel the joystick is on.
	 * @param axis The axis the joystick is transmitting.
	 * @return
	 */
	public static FloatInput joystickAxisPort(int channel, int axis)
	{
		if(channel < 0 || channel > MAX_JOYSTICK)
		{
			throw new IllegalArgumentException("Joystick channel is invalid!");
		}
			
		return floatInputPin(formatPinName(JOYSTICK_AXIS, channel, axis));
	}
	/**
	 * A representation of a joystick button on a specific channel and button.
	 * @param channel The channel the joystick is on.
	 * @param button The button the joystick is transmitting.
	 * @return
	 */
	public static BooleanInput joystickButtonPort(int channel, int button)
	{
		if(channel < 0 || channel > MAX_JOYSTICK)
		{
			throw new IllegalArgumentException("Joystick channel is invalid!");
		}
		return booleanInputPin(formatPinName(JOYSTICK_BUTTON, channel, button));
	}
	
	public static FloatInput joystickPOVPort(int channel)
	{
		return floatInputPin(formatPinName(JOYSTICK_POV, channel));
	}
	/**
	 * A representation of a digital input pin on a RoboRio in a schematic.
	 * @param pinNumber Number of the DI pin. Must be between 0-{@value #MAX_DIO}.
	 * @return 
	 */
	public static BooleanInput digitalInputPin(int pinNumber)
	{
		if(pinNumber > MAX_DIO || pinNumber < 0)
		{
			throw new IllegalArgumentException("Pin number is invalid!");
		}

		return booleanInputPin(formatPinName(DIGITAL_INPUT, pinNumber));

	}
	/**
	 * A representation of a digital output pin on a RoboRio in a schematic.
	 * @param pinNumber Number of the DO pin. Must be between 0-{@value #MAX_DIO}.
	 * @return 
	 */
	public static BooleanOutput digitalOutputPin(int pinNumber)
	{
		if(pinNumber > MAX_DIO || pinNumber < 0)
		{
			throw new IllegalArgumentException("Pin number is invalid!");
		}
		return booleanOutputPin(formatPinName(DIGITAL_OUTPUT, pinNumber));
	}
	/**
	 * A representation of a pwm pin on a RoboRio in a schematic.
	 * @param pinNumber Number of the PWM pin. Must be between 0-{@value #MAX_PWM}
	 * @return
	 */
	public static FloatOutput pwmOutputPin(int pinNumber)
	{
		if(pinNumber > MAX_PWM || pinNumber < 0)
		{
			throw new IllegalArgumentException("Pin number is invalid!");
		}
		return floatOutputPin(formatPinName(PWM, pinNumber));
	}
	/**
	 * A representation of a relay pin on a RoboRio in a schematic.
	 * @param pinNumber Number of the relay pin. Must be between 0-{@value #MAX_RELAY}
	 * @return
	 */
	public static BooleanOutput relayOutputPin(int pinNumber)
	{
		if(pinNumber > MAX_RELAY || pinNumber < 0)
		{
			throw new IllegalArgumentException("Pin number is invalid!");
		}
		return booleanOutputPin(formatPinName(RELAY, pinNumber));
	}
	/**
	 * A representation of a analog input pin on a RoboRio in a schematic.
	 * @param pinNumber Number of the analog input pin. Must be between 0-{@value #MAX_ANALOG_IN}
	 * @return
	 */
	public static FloatInput analogInputPin(int pinNumber)
	{
		if(pinNumber > MAX_ANALOG_IN || pinNumber < 0)
		{
			throw new IllegalArgumentException("Pin number is invalid!");
		}
		return floatInputPin(formatPinName(ANALOG, pinNumber));
	}
	
	/**
	 * A representation of the battery voltage info coming from the PDB.
	 * @return
	 */
	public static FloatInput battery()
	{
		return floatInputPin(formatPinName(BATTERY));
	}
}
