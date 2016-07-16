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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getDeviceID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BooleanIO asEnable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void enable() throws ExtendedMotorFailureException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disable() throws ExtendedMotorFailureException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FloatOutput asMode(OutputControlMode mode) throws ExtendedMotorFailureException {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setInternalPID(float P, float I, float D) throws ExtendedMotorFailureException {
		// TODO Auto-generated method stub
		
	}

}
