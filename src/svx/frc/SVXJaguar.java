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

public class SVXJaguar extends ExtendedMotor {
	int pin;
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
		return RoboRioBoard.CANJaguar(pin).OutControlMode(mode);
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
							return RoboRioBoard.CANJaguar(pin).InBusVoltage().get();uu
						case OUTPUT_CURRENT:
							return RoboRioBoard.CANJaguar(pin).InOutputCurrent().get();
						case OUTPUT_VOLTAGE:
							return RoboRioBoard.CANJaguar(pin).InOutputVoltage().get(); //TODO: Calculate off current and bus voltage?
						case TEMPERATURE:
							return RoboRioBoard.CANJaguar(pin).InTemperature().get();
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
		RoboRioBoard.CANJaguar(pin).PID(P, I, D);
	}
	public SVXJaguar(int id)
	{
		pin = id;
	}

}
