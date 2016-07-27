package svx.frc;

import ccre.channel.BooleanIO;
import ccre.channel.EventInput;
import ccre.channel.FloatInput;
import ccre.channel.FloatOutput;
import ccre.ctrl.ExtendedMotorFailureException;
import ccre.ctrl.Faultable;
import ccre.drivers.ctre.talon.TalonAnalog;
import ccre.drivers.ctre.talon.TalonEncoder;
import ccre.drivers.ctre.talon.TalonExtendedMotor;
import ccre.drivers.ctre.talon.TalonFeedback;
import ccre.drivers.ctre.talon.TalonGeneralConfig;
import ccre.drivers.ctre.talon.TalonHardLimits;
import ccre.drivers.ctre.talon.TalonPIDConfiguration;
import ccre.drivers.ctre.talon.TalonPulseWidth;
import ccre.drivers.ctre.talon.TalonSoftLimits;

public class SVXTalonCAN extends TalonExtendedMotor{
	int channel;
	@Override
	public Faultable<Faults> modFaults() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TalonAnalog modAnalog() {
		return null;
	}

	@Override
	public TalonEncoder modEncoder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TalonHardLimits modHardLimits() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TalonPIDConfiguration modPID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TalonPulseWidth modPulseWidth() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TalonSoftLimits modSoftLimits() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TalonFeedback modFeedback() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TalonGeneralConfig modGeneralConfig() {
		return new TalonGeneralConfig() {
			
			@Override
			public BooleanIO getBrakeNotCoast() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void configureReversed(boolean flipSensor, boolean flipOutput) {
				
			}
			
			@Override
			public void configureNominalOutputVoltage(float forwardVoltage, float reverseVoltage) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void configureMaximumOutputVoltage(float forwardVoltage, float reverseVoltage) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void configureGeneralFeedbackUpdateRate(int millisGeneral, int millisFeedback) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void configureAllowableClosedLoopError(float allowableCloseLoopError) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void activateFollowerMode(int talonID) {
				// TODO Auto-generated method stub
				
			}
		};
	}

	@Override
	public int getDeviceID() {
		return channel;
	}

	@Override
	public BooleanIO asEnable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void enable() throws ExtendedMotorFailureException {
		
	}

	@Override
	public void disable() throws ExtendedMotorFailureException {
		
	}

	@Override
	public FloatOutput asMode(OutputControlMode mode) throws ExtendedMotorFailureException {
		return RoboRioBoard.CANJaguar(channel).OutControlMode(mode);
	}

	@Override
	public FloatInput asStatus(StatusType type, EventInput updateOn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getDiagnostics(DiagnosticType type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasInternalPID() {
		return true;
	}

	@Override
	public void setInternalPID(float P, float I, float D) throws ExtendedMotorFailureException {
		RoboRioBoard.CANJaguar(channel).PID(P, I, D);
		
	}

}
