package svx.frc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ccre.channel.BooleanInput;
import ccre.channel.BooleanOutput;
import ccre.channel.FloatInput;
import ccre.channel.FloatOutput;
import ccre.ctrl.ExtendedMotor.OutputControlMode;
import ccre.log.Logger;
import svconnect.Device;
//TODO: Add support for MXP
/**
 * A representation of all the pins on a RoboRio board inside a schematic. Names generators and consumers by: [BUS_TYPE]_[PORT_ACCESSOR]_[NUMERICAL_ARGUMENT_1]_...
 * @author tthompso
 *
 */
public class RoboRioBoard extends Device {
	
	static class FloatInputPort
	{
		String name;
		public FloatInputPort(String name)
		{
			this.name = name;
		}
		public FloatInput Input()
		{
			return floatInputPin(name);
		}
	}
	static class FloatOutputPort
	{
		String name;
		public FloatOutputPort(String name)
		{
			this.name = name;
		}
		public FloatOutput Output()
		{
			return floatOutputPin(name);
		}
	}
	//This makes sense from a manifest point of veiw. Any better way to do this?
	static class FloatIOPort 
	{
		String name;
		public FloatIOPort(String name)
		{
			this.name = name;
		}
		public FloatInput Input()
		{
			return floatInputPin(name);
		}
		public FloatOutput Output()
		{
			return floatOutputPin(name);
		}
	}
	
	static class BooleanInputPort
	{
		String name;
		public BooleanInputPort(String name)
		{
			this.name = name;
		}
		public BooleanInput Input()
		{
			return booleanInputPin(name);
		}
	}
	static class BooleanOutputPort
	{
		String name;
		public BooleanOutputPort(String name)
		{
			this.name = name;
		}
		public BooleanOutput Output()
		{
			return booleanOutputPin(name);
		}
	}
	static class BooleanIOPort
	{
		String name;
		
		public BooleanIOPort(String name)
		{
			this.name = name;
		}
		public BooleanInput Input()
		{
			return booleanInputPin(name);
		}
		public BooleanOutput Output()
		{
			return booleanOutputPin(name);
		}
	}
	/**
	 * A common class for objects that have a channel parameter.
	 * @author tthompso
	 *
	 */
	static class Channel
	{
		int channel;
		public Channel(int channel)
		{
			this.channel = channel;
		}
	}
	/**
	 * A representation of all CAN communications of a CANJaguar
	 * @author tthompso
	 *
	 */
	static class CANJaguar extends Channel
	{
		public CANJaguar(int channel) {
			super(channel);
		}
		static final String CAN = "CAN";
		
		static final String TEMPERATURE = "TEMPERATURE";
		static final String BUS_VOLTS = "BUS_VOLTS";
		static final String OUT_VOLTS = "OUT_VOLTS";
		static final String OUT_CURRENT = "OUT_CURRENT";
		static final String OUTPUT_MODE = "CONTROL_MODE";
		static final String P = "P";
		static final String I = "I";
		static final String D = "D";

		/**
		 * Creates a port that represents the bus voltage being returned by the CANJaguar
		 * @return
		 */
		public FloatInputPort InBusVoltage()
		{
			return new FloatInputPort(formatPinName(CAN, BUS_VOLTS, channel));
		}
		/**
		 * Creates a port that represents the output voltage being returned by the CANJaguar
		 * @return
		 */
		public FloatInputPort InOutputVoltage()
		{
			return new FloatInputPort(formatPinName(CAN, OUT_VOLTS, channel));
		}
		/**
		 * Creates a port that represents the output current being returned by the CANJaguar
		 * @return
		 */
		public FloatInputPort InOutputCurrent()
		{
			return new FloatInputPort(formatPinName(CAN, OUT_CURRENT, channel));
		}
		/**
		 * Creates a port that represents the temperature being returned by the CANJaguar
		 * @return
		 */
		public FloatInputPort InTemperature()
		{
			return new FloatInputPort(formatPinName(CAN, TEMPERATURE, channel));
		}
		/**
		 * Creates a port that will output the requested control mode by the RoboRio
		 * @param mode
		 * @return
		 */
		public FloatOutput OutControlMode(OutputControlMode mode)
		{
			FloatOutput pin = floatOutputPin(formatPinName(CAN, OUTPUT_MODE, channel));
			pin.set(mode.ordinal());
			return pin;
		}
		/**
		 * Creates 3 ports that will send a constant P,I, and D value to the CANJaguar
		 * @param P
		 * @param I
		 * @param D
		 */
		public void PID(float P, float I, float D)
		{
			FloatOutput pPin = floatOutputPin(formatPinName(CAN, this.P, channel));
			FloatOutput iPin = floatOutputPin(formatPinName(CAN, this.I, channel));
			FloatOutput dPin = floatOutputPin(formatPinName(CAN, this.D, channel));
			
			pPin.set(P);
			iPin.set(I);
			dPin.set(D);
		}
	}	
	/**
	 * A representation of a DIO port on a RoboRio
	 * @author tthompso
	 *
	 */
	static class Digital extends Channel
	{
		public Digital(int channel) {
			super(channel);
			if(channel > MAX_DIO || channel < 0)
			{
				throw new IllegalArgumentException("Pin number is invalid!");
			}
		}
		static final int MAX_DIO = 9;
		static final String DIGITAL = "D";
		static final String IN = "IN";
		static final String OUT = "OUT";
		/**
		 * A representation of a digital input pin on a RoboRio in a schematic.
		 * @return 
		 */
		public BooleanInput Input()
		{

			return booleanInputPin(formatPinName(DIGITAL, IN, channel));

		}
		/**
		 * A representation of a digital output pin on a RoboRio in a schematic.
		 * @return 
		 */
		public BooleanOutput Output()
		{
			return booleanOutputPin(formatPinName(DIGITAL, OUT, channel));
		}
		
	}
	static class Joystick extends Channel
	{
		static final int MAX_JOYSTICK = 6;

		static final String JOYSTICK = "JOY";
		static final String AXIS = "AXIS";
		static final String BUTTON = "BUTTON";
		static final String POV  = "POV";
		
		public Joystick(int channel) {
			super(channel);
			if(channel < 0 || channel > MAX_JOYSTICK)
			{
				throw new IllegalArgumentException("Joystick channel is invalid!");
			}
		}
		/**
		 *  A representation of a joystick button on a specific channel and axis.
		 * @param axis
		 * @return
		 */
		public FloatInput Axis(int axis)
		{
			return floatInputPin(formatPinName(JOYSTICK, AXIS, channel, axis));
		}
		/**
		 * A representation of a joystick button on a specific channel and button.
		 * @param button The button the joystick is transmitting.
		 * @return
		 */
		public BooleanInput Button(int button)
		{
			return booleanInputPin(formatPinName(JOYSTICK, BUTTON, channel, button));
		}
		/**
		 *  A representation of a joystick POV.
		 */
		public FloatInput POV()
		{
			return floatInputPin(formatPinName(JOYSTICK, POV, channel));
		}
	}
	static class Encoder
	{

		public Encoder(int aChannel, int bChannel) {
			if(aChannel < 0 || aChannel > MAX_DIO || bChannel < 0 || bChannel > MAX_DIO)
			{
				throw new IllegalArgumentException("Channel out of range.");
			}
			this.aChannel = aChannel;
			this.bChannel = bChannel;
		}
		int aChannel, bChannel;
		static final int MAX_DIO = 9;
		static final String ENCODER = "ENCODER";
		static final String REVERSE = "REVERSE";
		static final String COUNTS = "COUNTS";
		public void Reverse(boolean reverse)
		{
			BooleanOutput pin = booleanOutputPin(formatPinName(ENCODER, REVERSE, aChannel, bChannel));
			pin.set(reverse);
		}

		public FloatInput Counts()
		{
			
			return floatInputPin(formatPinName(ENCODER, COUNTS, aChannel, bChannel));
		}
	}
	static class PWM extends Channel
	{
		public PWM(int channel) {
			super(channel);
			if(channel > MAX_PWM || channel < 0)
			{
				throw new IllegalArgumentException("Pin number is invalid!");
			}
		}
		static final String PWM = "PWM";
		static final String OUTPUT = "OUTPUT";
		static final int MAX_PWM = 9;
		public FloatOutput Output()
		{
			
			return floatOutputPin(formatPinName(PWM, OUTPUT, channel));
		}

	}
	static class Analog extends Channel
	{	
		static final String ANALOG = "ANALOG";
		static final String INPUT = "INPUT";
		static final int MAX_ANALOG_IN = 4;
		
		public Analog(int channel) {
			super(channel);
			if(channel > MAX_ANALOG_IN || channel < 0)
			{
				throw new IllegalArgumentException("Pin number is invalid!");
			}

		}
		/**
		 * A representation of a analog input pin on a RoboRio in a schematic.
		 * @param pinNumber Number of the analog input pin. Must be between 0-{@value #MAX_ANALOG_IN}
		 * @return
		 */
		public FloatInput Input()
		{
			return floatInputPin(formatPinName(ANALOG, INPUT, channel));
		}		
	}
	/**
	 * A representation of a Relay bus
	 * @author tthompso
	 *
	 */
	static class Relay extends Channel
	{
		static final int MAX_RELAY = 4;
		static final String RELAY = "RELAY";
		public Relay(int channel) {
			super(channel);
			if(channel > MAX_RELAY || channel < 0)
			{
				throw new IllegalArgumentException("Pin number is invalid!");
			}
		}
		/**
		 * Create an output port on the relay bus
		 * @return
		 */
		public BooleanOutput Output()
		{
			return booleanOutputPin(formatPinName(RELAY, new String[]{}, new int[]{channel}));
		}
	}
	static class SolenoidModule
	{
		static final String SOLENOID = "SOLENOID";
		static final String PRESSURE_SWITCH = "PRESSURE_SWITCH";
		static final String COMPRESSOR_RUNNING = "COMPRESSOR_RUNNING";
		static final String COMPRESSOR_CURRENT = "COMPRESSOR_CURRENT";
		
		/**
		 * Create a port that gives the pressure switch state coming from the Solenoid Module
		 * @return
		 */
		public BooleanInput PressureSwitch()
		{
			return booleanInputPin(formatPinName(SOLENOID, PRESSURE_SWITCH));
		}
		/**
		 * Create a port that gives the state of the compressor coming from the solenoid module
		 * @return
		 */
		public BooleanInput CompressorRunning()
		{
			return booleanInputPin(formatPinName(SOLENOID, COMPRESSOR_RUNNING));
		}
		/**
		 * Create a port that gives the compressor current coming from the solenoid module
		 * @return
		 */
		public FloatInput CompressorCurrent()
		{
			return floatInputPin(formatPinName(SOLENOID, COMPRESSOR_CURRENT));
		}
		
	}
	static class PDB
	{
		static final String POWER_DISTRIBUTION_BOARD = "PDB";
		static final String POWER_CHANNEL = "POWER_CHANNEL";
		public FloatInput PowerChannelVolts(int channel)
		{
			return floatInputPin(formatPinName(POWER_DISTRIBUTION_BOARD, POWER_CHANNEL, channel));
		}
		public FloatInput PDBVolts()
		{
			return floatInputPin(formatPinName(POWER_DISTRIBUTION_BOARD, new String[]{}, new int[]{}));
		}
	}
	
	private static String formatPinName(String prefix, String[] modifiers, int[] numerics)
	{
		StringBuilder formatted = new StringBuilder(prefix);
		for(String mod : modifiers)
		{
			appendValue(mod, formatted);
		}
		for(int number : numerics)
		{
			appendValue(number, formatted);
		}
		return formatted.toString();
	}
	private static String formatPinName(String prefix, String modifier, int...numerics)
	{
		return formatPinName(prefix, new String[]{modifier}, numerics);
	}
	private static <ValType> void appendValue(ValType val, StringBuilder builder)
	{
		builder.append("_");
		builder.append(val);
	}
	/**
	 * Create a CAN bus on the given channel
	 * @param channel
	 * @return
	 */
	public static CANJaguar CANJaguar(int channel)
	{
		return new CANJaguar(channel);
	}
	/**
	 * Create a Digital bus on the given channel
	 * @param channel
	 * @return
	 */
	public static Digital Digital(int channel)
	{
		return new Digital(channel);
	}
	/**
	 * Create a joystick bus on the given channel
	 * @param channel
	 * @return
	 */
	public static Joystick Joystick(int channel)
	{
		return new Joystick(channel);
	}
	/**
	 * Create a joystick bus on the given a and b channel
	 * @param aChannel
	 * @param bChannel
	 * @return
	 */
	public static Encoder Encoder(int aChannel, int bChannel)
	{
		return new Encoder(aChannel, bChannel);
	}
	/**
	 * Create a PWM bus on the given channel
	 * @param channel
	 * @return
	 */
	public static PWM PWM(int channel)
	{
		return new PWM(channel);
	}
	/**
	 * Create an analog bus on the given channel
	 * @param channel
	 * @return
	 */
	public static Analog Analog(int channel)
	{
		return new Analog(channel);
	}
	/**
	 * Create a relay bus on the given channel
	 * @param channel
	 * @return
	 */
	public static Relay Relay(int channel)
	{
		return new Relay(channel);
	}
	/**
	 * Create a solenoid module bus
	 * @return
	 */
	public static SolenoidModule SolenoidModule()
	{
		return new SolenoidModule();
	}
	/**
	 * Create a PDB bus
	 * @return
	 */
	public static PDB PDB()
	{
		return new PDB();
	}
	/**
	 * Set up the RoboRio by reading a xml document. Document is formatted as:
	 * <br>
	 * &lt;Robot&gt;<br>
	 * &lt;BusType(The bus type reflects the name of the instantiating method, such as PDB, Digital, Relay, etc.)&gt<br>
	 * &lt;PortMethod(Output, Input, etc. Creates the actual port)&gt;<br>
	 * ...Further instantiating and port closing.
	 * @param xml The xml file to configure from.
	 */
	public static void setupFromFile(Document xml)
	{
		//Get the contents of <Robot>
		NodeList topLevel = xml.getFirstChild().getChildNodes();
		invokeAllChildren(topLevel, RoboRioBoard.class, null ,true);
	}
	private static void invokeAllChildren(NodeList children, Class<?> childrenClass, Object childrenObject, boolean recursive)
	{

		for(int i = 0; i < children.getLength(); i++)
		{
			Node port = children.item(i);
			if(port.getNodeType() == Node.ELEMENT_NODE)
			{
				Object portObject = invokeXML(port, childrenClass, childrenObject); //What object this is only matters if the user specifies a method that is relevant to the class.
				if(!(portObject == null) && portObject.getClass().getMethods().length > 0) //The user will probably want to run something inside the class.
				{
					NodeList portChildren = port.getChildNodes();
					if(recursive)
					{
						invokeAllChildren(portChildren, portObject.getClass(), portObject, recursive);//Invoke the child node's children
					}
				}
			}
		}

	}
	private static Object invokeXML(Node node, Class<?> searchClass, Object invokeObject)
	{
		String portClassName = node.getNodeName();
		LinkedHashMap<String, String> portClassArguments = getAttributes(node); //Get the arguments to instantiate the port
		Method portClassAccessor = null;
		try
		{
			portClassAccessor = searchClass.getMethod(portClassName, getAttributeTypes(portClassArguments).toArray(new Class<?>[0]));//findMethodByParamName(searchClass, portClassArguments.keySet());
			return portClassAccessor.invoke(invokeObject, attributesToArgs(portClassArguments).toArray());

		}
		catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException |NullPointerException | NoSuchMethodException ex)
		{
			Logger.severe("Could not instantiate " + portClassName);
		}
		return null;
	}
	private static LinkedHashMap<String, String> getAttributes(Node node)
	{
		LinkedHashMap<String, String> attributesMap = new LinkedHashMap<String, String>();
		NamedNodeMap attributes = node.getAttributes();
		for(int i = 0; i < attributes.getLength(); i++)
		{
			attributesMap.put(attributes.item(i).getNodeName(), attributes.item(i).getNodeValue());
		}
		return attributesMap;
	}
	private static List<Class<?>> getAttributeTypes(LinkedHashMap<String, String> attributes)
	{
		List<Class<?>> types = new ArrayList<>();
		for(String value : attributes.values())
		{
			if(Pattern.matches("[0-9]*", value))
			{
				types.add(int.class);
			}
			else if(Pattern.matches("true|false", value))
			{
				types.add(boolean.class);
			}
			else
			{
				types.add(String.class);
			}
		}
		return types;
	}
	private static List<Object> attributesToArgs(LinkedHashMap<String, String> attributes)
	{
		List<Object> args = new ArrayList<>();
		for(String value : attributes.values())
		{
			if(Pattern.matches("[0-9]*", value))
			{
				args.add(Integer.parseInt(value));
			}
			else if(Pattern.matches("true|false", value))
			{
				args.add(Boolean.parseBoolean(value));
			}			
			else
			{
				args.add(value);
			}
		}
		return args;
	}
	private static LinkedHashMap<String, String> reorderAttributes(Parameter[] order, LinkedHashMap<String, String> unorderedMap)
	{
		LinkedHashMap<String, String> orderedMap = new LinkedHashMap<>();
		for(Parameter p : order)
		{
			for(String argName : unorderedMap.keySet())
			{
				if(argName == p.getName())
				{
					orderedMap.put(argName, unorderedMap.get(argName));
				}
			}
		}
		return orderedMap;
	}
	private static Method findMethodByParamName(Class<?> methodClass, Set<String> paramNames) throws NoSuchMethodException
	{
		Method[] methods = methodClass.getMethods();
		boolean methodMatch = true;
		for(Method method : methods)
		{
			for(Parameter param : method.getParameters())
			{
				boolean match = false;
				for(String paramName : paramNames)
				{
					match = paramName == param.getName();
					if(match)
					{
						break;
					}
				}
				methodMatch &= match;
			}
			if(methodMatch)
			{
				return method;
			}
		}
		throw new NoSuchMethodException();
	}
}
