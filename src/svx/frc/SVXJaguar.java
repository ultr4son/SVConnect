package svx.frc;

import ccre.channel.BooleanOutput;
import ccre.channel.CancelOutput;
import ccre.channel.DerivedFloatInput;
import ccre.channel.EventInput;
import ccre.channel.EventOutput;
import ccre.channel.FloatCell;
import ccre.channel.FloatIO;
import ccre.channel.FloatInput;
import ccre.channel.FloatOutput;
import ccre.ctrl.ExtendedMotor;
import ccre.ctrl.ExtendedMotorFailureException;
import svconnect.RoboRioBoard;

public class SVXJaguar extends ExtendedMotor {
	int pin;
	FloatIO jaguarOutput;
	@Override
	public void enable() throws ExtendedMotorFailureException {
		
	}

	@Override
	public void disable() throws ExtendedMotorFailureException {
		
	}

	@Override
	public BooleanOutput asEnable() {
		return BooleanOutput.ignored;
	}

	@Override
	public FloatOutput asMode(OutputControlMode mode) throws ExtendedMotorFailureException {
		//Control logic happens at the Jaguar itself, so there's nothing we can do here.
		FloatOutput out = RoboRioBoard.pwmOutputPin(pin);
		jaguarOutput = out.cell(0.0f);
		return out;
	}

	@Override
	public FloatInput asStatus(StatusType type, EventInput updateOn) {
		return new DerivedFloatInput(RoboRioBoard.running())
				{
					@Override
					protected float apply() {
						switch(type)
						{
						case BUS_VOLTAGE:
							return RoboRioBoard.battery().get();
						case OUTPUT_CURRENT:
							return 0.0f; //TODO: Might need to add some hidden feedback system.
						case OUTPUT_VOLTAGE:
							return jaguarOutput.multipliedBy(RoboRioBoard.battery()).get();
						case TEMPERATURE:
							return 20.0f; 
						default:
						throw new IllegalArgumentException("Invalid type!");
						}
					}	
					
				};
	}

	@Override
	public Object getDiagnostics(DiagnosticType type) {
		return null;
	}

	@Override
	public boolean hasInternalPID() {
		return true;
	}

	@Override
	public void setInternalPID(float P, float I, float D) throws ExtendedMotorFailureException {
		//Control logic happens at the Jaguar itself, so there's nothing we can do here.
	}
	public SVXJaguar(int id)
	{
		pin = id;
	}

}
