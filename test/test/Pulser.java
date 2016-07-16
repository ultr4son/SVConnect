package test;

import ccre.channel.AbstractUpdatingInput;
import ccre.channel.FloatIO;

public class Pulser extends AbstractUpdatingInput implements FloatIO
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2237927711880375811L;

	// state variable for pulse generation
	int pulseGeneratorCount;

	// pulse amplitude
	float amplitude;

	// pulse count parameters
	int pulseOnCount;
	int pulseOffCount;
	
	float currentValue;
	@Override
	public float get() {
		float modifiedValue = currentValue;
		// add pulse if necessary
		if ( pulseGeneratorCount < pulseOnCount )
		{
			modifiedValue = currentValue + amplitude;
		}

		// reset state data if necessary
		if((++pulseGeneratorCount) >= (pulseOnCount + pulseOffCount))
		{
			pulseGeneratorCount = 0;
		}
		return modifiedValue;
	}
	@Override
	public void set(float value) {
		currentValue = value;
		perform();
	}
	public Pulser(float pulseAmplitude, float pulseDuty){
		pulseGeneratorCount = 0;
		amplitude = pulseAmplitude;
		pulseOnCount = (int)(10*pulseDuty);
		pulseOffCount = ( 10 - ((int)(10*pulseDuty)) );

	}
}