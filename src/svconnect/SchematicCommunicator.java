package svconnect;

import ccre.channel.BooleanInput;
import ccre.channel.BooleanOutput;
import ccre.channel.CancelOutput;
import ccre.channel.EventInput;
import ccre.channel.FloatInput;
import ccre.channel.FloatOutput;

/**
 * An interface for something that communicates between Java code and a system schematic.
 * @author tthompso
 *
 */
public interface SchematicCommunicator 
{
	/**
	 * Get a float output representing an output node on a systemvision schematic.
	 * @param outputName
	 * @return FloatOutput that will return what is being outputted
	 */
	public FloatOutput floatOutputPin(String outputName);
	
	/**
	 * Get a FloatInput representing an input node on a systemvision schematic.
	 * @param inputName
	 * @return FloatInput that will send float values to the systemvision schematic
	 */
	public FloatInput floatInputPin(String inputName);
	
	/**
	 * Get a BooleanOutput representing an output node on a systemvision schematic.
	 * @param outputName
	 * @return
	 */
	public BooleanOutput booleanOutputPin(String outputName);
	
	/**
	 * Get a BooleanInput representing an input node on a systemvision schematic.
	 * @param inputName
	 * @return
	 */
	public BooleanInput booleanInputPin(String inputName);
	

	/**
	 * Event fired when the simulation has started.
	 * @return
	 */
	public BooleanInput simulationBegan();
	/**
	 * Event fired every time the simulation has gone one time step.
	 * @return
	 */
	public EventInput simulationRunning();
	/**
	 * Event fired when the simulation has ended.
	 * @return
	 */
	public BooleanInput simulationEnded();
	/**
	 * Tells the client to start listening for simulation data on a certain event input
	 * @param listener
	 * @return
	 */
	public CancelOutput startCommunicatingOn(EventInput listener);
	/**
	 * Tells the client to start listening for simulation data.
	 */
	public void startCommunications();
	/**
	 * Specify how often input and output values are updated.
	 * @param timeNanos
	 */
	public void setTimeStepMillis(int timeMillis);
}
