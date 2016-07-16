package svx;

import com.mentor.systems.svx.SVXTemporalSpecSignalConsumer;
import com.mentor.systems.svx.SVXTimeRange;

import ccre.channel.BooleanInput;

/**
 * A workaround to the fact that trying to use get() with a Boolean Consumer will cause the JVM to crash. Uses an Integer Consumer that converts values >= 1 to boolean true
 * and all other values to false.
 * @author tthompso
 *
 */
public class SVXBooleanWorkaroundCommunicator extends SVXCommunicator {
	/**
	 * NOTE: this uses a workaround to avoid using Boolean Consumers, because calling get() with a boolean consumer will cause the JVM to crash. Use Integer Generators that
	 * generate values >= 1 for true and < 1 for false in the schematic.
	 */
	@Override
	public BooleanInput booleanInputPin(String inputName)
	{
		SVXTemporalSpecSignalConsumer spec = new SVXTemporalSpecSignalConsumer
				(
						timeRange, timeStep,
						timeRange, timeStep,
						SVXTimeRange.SVX_ZERO_TIME_RANGE, 0,
						SVXTimeRange.SVX_ZERO_TIME_RANGE, 0
				);
		SVXIntegerToBooleanConsumerWrapper workaround = new SVXIntegerToBooleanConsumerWrapper(factory, inputName, spec);
		registerPin(workaround);
		return workaround;
	}
}
