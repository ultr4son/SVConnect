package svx;

import java.util.ArrayList;
import java.util.List;

import com.mentor.systems.svx.ISVXComponentTarget;
import com.mentor.systems.svx.ISVXFactory;
import com.mentor.systems.svx.SVXComponentTarget;
import com.mentor.systems.svx.SVXStatusException;
import com.mentor.systems.svx.SVXTemporalSpecChannel;
import com.mentor.systems.svx.SVXTemporalSpecSignalConsumer;
import com.mentor.systems.svx.SVXTemporalSpecSignalGenerator;
import com.mentor.systems.svx.SVXTimeRange;

import ccre.channel.BooleanCell;
import ccre.channel.BooleanInput;
import ccre.channel.BooleanOutput;
import ccre.channel.CancelOutput;
import ccre.channel.EventCell;
import ccre.channel.EventInput;
import ccre.channel.EventOutput;
import ccre.channel.FloatInput;
import ccre.channel.FloatOutput;
import ccre.log.Logger;
import ccre.time.FakeTime;
import ccre.time.Time;
import svconnect.SchematicCommunicator;

/**
 * SVX based communicator. Note that on instantiaton, the Time class's time implementation will be changed to FakeTime.
 * @author tthompso
 *
 */
public class SVXCommunicator implements SchematicCommunicator
{
	ISVXComponentTarget target;
	ISVXFactory factory;
	
	FakeTime time;
	static final String CHANNEL_NAME = "DEFAULT_CHANNEL";
	// Default to once a millisecond.
	SVXTimeRange timeRange = SVXTimeRange.SVX_MILLI_TIME_RANGE;
	int timeStep = 1;
	List<BooleanCell> inputStates = new ArrayList<>();
	private BooleanCell simulationFinished = new BooleanCell(false);
	private BooleanCell simulationStarted = new BooleanCell(false);
	private EventCell timeUpdated = new EventCell();
	private BooleanInput allDone = new BooleanInput()
			{

				/**
				 * 
				 */
				private static final long serialVersionUID = 3914066924576438695L;

				@Override
				public boolean get() {
					boolean finished = true;
					for(BooleanCell state : inputStates)
					{
						finished &= state.get();
					}
					return finished;
//					boolean temp = inputStates.stream().allMatch((input) -> input.get());
//					return temp;
				}

				@Override
				public CancelOutput onUpdate(EventOutput notify) {
					if(notify == null)
					{
						throw new NullPointerException();
					}
					return CancelOutput.nothing;
				}
		};
	EventOutput updateTimeEvent = () ->
	{
			
					int exponent = 0;
					switch(timeRange)
					{
					case SVX_MILLI_TIME_RANGE:
						exponent = 0;
						break;
					case SVX_SECOND_TIME_RANGE:
						exponent = 3;
						break;
					case SVX_MICRO_TIME_RANGE:
					case SVX_NANO_TIME_RANGE:				
					case SVX_KILO_TIME_RANGE:
					case SVX_MEGA_TIME_RANGE:
					case SVX_PICO_TIME_RANGE:
					case SVX_FEMTO_TIME_RANGE:			
					case SVX_INFINITE_TIME_RANGE:
					case SVX_INVALID_TIME_RANGE:	
					case SVX_ZERO_TIME_RANGE:
					default:
						throw new IllegalStateException("Time range is invalid for time update!");
					}
					int incrementMillis = (int)(timeStep * Math.pow(10, (double)exponent));
					try {
						time.forward(incrementMillis); 
						timeUpdated.event(); 
						System.out.println("Simulated time for: " + Time.currentTimeMillis() + "ms");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
//					resetState();
	};
	
//	protected void register(SVXConsumerWrapper input)
//	{
//		inputState.put(input, false);
//	}
//	protected void finish(SVXConsumerWrapper input)
//	{
//		inputState.put(input, true);
//		updateFinished();
//		
//	}
//	private void updateFinished()
//	{
//		boolean finished = true;
//		for(Boolean state : inputState.values())
//		{
//			finished &= state;
//		}
//		if(finished)
//		{
//			updateTimeEvent.event();
//		}
//	}

	EventOutput runEvent = new EventOutput()
			{
				/**
				 * 
				 */
				private static final long serialVersionUID = 5248229370069521555L;

				@Override
				public void event() {
					simulationFinished.set(false);
					simulationStarted.set(true);
					try {
						
						target.execute();
					} catch (SVXStatusException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					simulationFinished.set(true);
				}
		
			};
	protected void registerPin(SVXConsumerWrapper wrapper)
	{
		inputStates.add(wrapper.done); //Keep track of who is done or not.
		wrapper.done.onPress().and(allDone).send(updateTimeEvent); // Update allDone once an input finishes. Once all the inputs are done, time will tick by one timeIncrement.
		wrapper.done.setFalseWhen(timeUpdated); //Sets false once a tick, after all inputs are done notifying

	}
	@Override
	public FloatOutput floatOutputPin(String outputName) {
		
		SVXTemporalSpecSignalGenerator spec = new SVXTemporalSpecSignalGenerator
				(
						timeRange, timeStep,
						timeRange, timeStep,
						SVXTimeRange.SVX_ZERO_TIME_RANGE, 0
				);			
		return new SVXSingleGeneratorWrapper(factory, outputName, spec, timeUpdated);
		
	}

	@Override
	public FloatInput floatInputPin(String inputName) {
		SVXTemporalSpecSignalConsumer spec = new SVXTemporalSpecSignalConsumer
				(
						timeRange, timeStep,
						timeRange, timeStep,
						SVXTimeRange.SVX_ZERO_TIME_RANGE, 0,
						SVXTimeRange.SVX_ZERO_TIME_RANGE, 0
				);
		SVXSingleConsumerWrapper wrapper = new SVXSingleConsumerWrapper(factory, inputName, spec);
		registerPin(wrapper);
		return wrapper;
	}

	@Override
	public BooleanOutput booleanOutputPin(String outputName) {
		SVXTemporalSpecSignalGenerator spec = new SVXTemporalSpecSignalGenerator
				(
						timeRange, timeStep,
						timeRange, timeStep,
						SVXTimeRange.SVX_ZERO_TIME_RANGE, 0
				);
		
		return new SVXBooleanGeneratorWrapper(factory, outputName, spec, timeUpdated);
	}

	@Override
	public BooleanInput booleanInputPin(String inputName) {
		SVXTemporalSpecSignalConsumer spec = new SVXTemporalSpecSignalConsumer
				(
						timeRange, timeStep,
						timeRange, timeStep,
						SVXTimeRange.SVX_ZERO_TIME_RANGE, 0,
						SVXTimeRange.SVX_ZERO_TIME_RANGE, 0
				);
		SVXBooleanConsumerWrapper wrapper = new SVXBooleanConsumerWrapper(factory, inputName, spec);
		registerPin(wrapper);
		return wrapper;
	}


	@Override
	public BooleanInput simulationBegan() {
		return simulationStarted.asInput();
	}

	@Override
	public BooleanInput simulationEnded() {
		return simulationFinished.asInput();
	}

	@Override
	public EventInput simulationRunning() {
		return timeUpdated;
	}

	@Override
	public CancelOutput startCommunicatingOn(EventInput listener) {
		listener.send(runEvent);
		return CancelOutput.nothing;
	}

	/**
	 * Start communicating with systemvision. 
	 */
	@Override
	public void startCommunications() {
		runEvent.event();
	}
	protected void _SVXClient(int port)
	{
		time = new FakeTime();
		Time.setTimeProvider(time);
		
		try {
	//		inputsFinished.onPress().send(updateTimeEvent);
			target = new SVXComponentTarget(false); //Target is not the first node in the stack.
			factory = target.getFactory();
			SVXTemporalSpecChannel spec = new SVXTemporalSpecChannel
					(SVXTimeRange.SVX_ZERO_TIME_RANGE, 0, SVXTimeRange.SVX_ZERO_TIME_RANGE, 0,SVXTimeRange.SVX_ZERO_TIME_RANGE, 0);
			factory.createSVXComponentChannel(CHANNEL_NAME, spec);
			factory.createSVXComponentConnectionAcceptorSockets(port);
			
		} catch (SVXStatusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Instantiate a new client listening on the given port.
	 * @param port The port to listen on.
	 */
	public SVXCommunicator(int port)
	{
		_SVXClient(port);
		
	}
	/**
	 * Instantiate a new client. Uses default port of 7818.
	 */
	public SVXCommunicator()
	{
		_SVXClient(7818);
	}
	
	@Override
	public void setTimeStepMillis(int timeMillis) {
		int exponent = (int) Math.log10((double)(timeMillis)); 
		//Time values can only range between 0-999 within their time range.
		timeStep = timeMillis;
		switch(exponent)
		{		
		case 0:
		case 1:
		case 2:
			timeRange = SVXTimeRange.SVX_MILLI_TIME_RANGE;
			break;
		case 3:
		case 4:
		case 5:
			timeRange = SVXTimeRange.SVX_SECOND_TIME_RANGE;
			timeStep = timeMillis / (int)Time.MILLISECONDS_PER_SECOND;
			break;
		case 6:
		case 7:
		case 8:
			timeRange = SVXTimeRange.SVX_KILO_TIME_RANGE;
			timeStep = timeMillis / (1000 * (int)Time.MILLISECONDS_PER_SECOND);
		case 9:
			timeRange = SVXTimeRange.SVX_MEGA_TIME_RANGE;
			timeStep = timeMillis / (1000000 * (int)Time.MILLISECONDS_PER_SECOND);
		//Anything larger is larger than MAX_INT
		default:
			throw new IllegalArgumentException("Time value too large!");
		}
	}

}
