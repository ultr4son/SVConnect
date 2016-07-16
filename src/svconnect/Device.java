package svconnect;

import java.util.HashMap;
import java.util.HashSet;

import ccre.channel.BooleanInput;
import ccre.channel.BooleanOutput;
import ccre.channel.EventInput;
import ccre.channel.FloatInput;
import ccre.channel.FloatOutput;

/**
 * A representation of an arbitrary system on a schematic. All pins are instantiated only once.
 * @author tthompso
 *
 */
public class Device {
	static final SchematicCommunicator impl = SchematicCommunicatorHolder.getImplementation();
	static HashMap<String, Object> pins = new HashMap<>();
	
	/**
	 * Connect to a pin that will output a value at the latest opportunity.
	 * <b>NOTE: SVX requires that a value is sent to it every tick of simulation time. This means that even if a value being sent to it hasn't changed, it will be sent anyway.</b>
	 * @param pinName The name of the pin to connect to.
	 * @return An output representing an input to the schematic.
	 */
	public static FloatOutput floatOutputPin(String pinName)
	{
		if(!pins.containsKey(pinName))
		{
			pins.put(pinName, impl.floatOutputPin(pinName));
		}
		return (FloatOutput) pins.get(pinName);
	}
	
	/**
	 * Connect to a pin that will notify outputs when it has gotten new data. 
	 * <b>NOTE: Time will not advance untill all pins have been notified of new data and sent data to all its outputs.<b/>
	 * @param pinName The name of the pin to connect to.
	 * @return An input representing an output from the schematic.
	 */
	public static FloatInput floatInputPin(String pinName)
	{
		if(!pins.containsKey(pinName))
		{
			pins.put(pinName, impl.floatInputPin(pinName));
		}
		return (FloatInput) pins.get(pinName);

	}
	
	/**
	 * Connect to a pin that will output a value at the latest opprotunity.
	 * <b>NOTE: SVX requires that a value is sent to it every tick of simulation time. This means that even if a value being sent to it hasn't changed, something will be sent anyway.</b>
	 * @param pinName The name of the pin to connect to.
	 * @return An output representing an input to the schematic.
	 */
	public static BooleanOutput booleanOutputPin(String pinName)
	{
		if(!pins.containsKey(pinName))
		{
			pins.put(pinName, impl.booleanOutputPin(pinName));
		}
		return (BooleanOutput) pins.get(pinName);
	}
	
	/**
	 * Connect to a pin that will notify outputs when it has gotten new data. 
	 * <b>NOTE: Time will not advance untill all pins have been notified of new data and sent data to all its outputs.<b/>
	 * @param pinName The name of the pin to connect to.
	 * @return An input representing an output from the schematic.
	 */
	public static BooleanInput booleanInputPin(String pinName)
	{
		if(!pins.containsKey(pinName))
		{
			pins.put(pinName, impl.booleanInputPin(pinName));
		}
		return (BooleanInput) pins.get(pinName);
	}
	
	/**
	 * An event that will fire every time time updates, or more precisely, every time all of the float input pins have finished being notified.
	 * @return
	 */
	public static EventInput running()
	{
		return impl.simulationRunning();
	}
	/**
	 * An input that will turn true once the simulation has started, and then will remain true after that.
	 * @return
	 */
	public static BooleanInput started()
	{
		return impl.simulationBegan();
	}
	/**
	 * An input that will turn true once the simulation has ended and will remain true after that.
	 * @return
	 */
	public static BooleanInput ended()
	{
		return impl.simulationEnded();
	}
	
	
	protected Device(){
		
	}
}
