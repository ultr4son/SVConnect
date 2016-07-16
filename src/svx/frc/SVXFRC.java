package svx.frc;

import ccre.bus.I2CBus;
import ccre.bus.RS232Bus;
import ccre.bus.SPIBus;
import ccre.channel.BooleanInput;
import ccre.channel.BooleanOutput;
import ccre.channel.CancelOutput;
import ccre.channel.DerivedFloatIO;
import ccre.channel.DerivedFloatInput;
import ccre.channel.EventInput;
import ccre.channel.EventOutput;
import ccre.channel.FloatCell;
import ccre.channel.FloatInput;
import ccre.channel.FloatOperation;
import ccre.channel.FloatOutput;
import ccre.channel.UpdatingInput;
import ccre.ctrl.ExtendedMotor;
import ccre.ctrl.Joystick;
import ccre.ctrl.binding.ControlBindingCreator;
import ccre.discrete.DerivedDiscreteInput;
import ccre.discrete.DiscreteInput;
import ccre.discrete.DiscreteType;
import ccre.drivers.ctre.talon.TalonExtendedMotor;
import ccre.frc.FRCImplementation;
import ccre.frc.FRCMode;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import svconnect.RoboRioBoard;

public class SVXFRC implements FRCImplementation{

	@Override
	public Joystick getJoystick(int id) {
		return new SVXJoystick(id);
	}

	/**
	 * Create a motor. Note that CAN based motor controllers are not yet supported.
	 */
	@Override
	public FloatOutput makeMotor(int id, int type) {
		switch(type)
		{
		//TODO: Figure out how CAN is going to be simulated.
		case JAGUAR:
		case TALONSRX:
			throw new NotImplementedException();
		case VICTORSP:
		case SD540:
		case SPARK:	
		case TALON:	
		case VICTOR:
			return RoboRioBoard.pwmOutputPin(id);
		default:
			throw new IllegalArgumentException("Invalid motor type!");
		}
	}

	@Override
	public ExtendedMotor makeCANJaguar(int deviceNumber) {
		return null;
	}

	@Override
	public TalonExtendedMotor makeCANTalon(int deviceNumber) {
		return null;
	}

	@Override
	public BooleanOutput makeSolenoid(int module, int id) {
		//TODO Determine if we want to simulate CAN and a PCM
		return BooleanOutput.ignored;
	}

	

	@Override
	public FloatInput makeAnalogInput(int id, EventInput updateOn) {
		return RoboRioBoard.analogInputPin(id);
	}

	@Override
	public FloatInput makeAnalogInput(int id, int averageBits, EventInput updateOn) {
		
		//TODO Figure out what averageBits means
		return RoboRioBoard.analogInputPin(id);
	}
	@Override
	public BooleanOutput makeDigitalOutput(int id) {
		return RoboRioBoard.digitalOutputPin(id);
	}
	
	//We want DIO to update when the simulation updates, and at no other time.
	@Override
	public BooleanInput makeDigitalInput(int id, EventInput updateOn) {
		
		return RoboRioBoard.digitalInputPin(id);
	}
	
	@Override
	public BooleanInput makeDigitalInputByInterrupt(int id) {
		return RoboRioBoard.digitalInputPin(id);
	}

	@Override
	public FloatOutput makeServo(int id, float minInput, float maxInput) {
		FloatOutput raw = RoboRioBoard.pwmOutputPin(id);
		FloatOutput constrained = new FloatOutput()
				{

					@Override
					public void set(float value) {
						if(value < minInput)
						{
							raw.set(minInput);
						}
						else if(value > maxInput)
						{
							raw.set(maxInput);
						}
						else
						{
							raw.set(value);
						}
					}
			
				};
		return constrained;
	}
	//TODO: Will probably want to add some schematic side thing to select what mode to simulate.
	@Override
	public BooleanInput getIsDisabled() {
		return RoboRioBoard.started().not();
	}

	@Override
	public BooleanInput getIsAutonomous() {
		return BooleanInput.alwaysFalse;
	}

	@Override
	public BooleanInput getIsTest() {
		return BooleanInput.alwaysFalse;
	}

	@Override
	public DiscreteInput<FRCMode> getMode() {
		return new DerivedDiscreteInput<FRCMode>(FRCMode.discreteType, null)
				{

					@Override
					protected FRCMode apply() {
						if(RoboRioBoard.started().get())
						{
							return FRCMode.TELEOP;
						}
						else
						{
							return FRCMode.DISABLED;
						}
					}
			
				};
	}

	@Override
	public BooleanInput getIsFMS() {
		return RoboRioBoard.started();
	}

	@Override
	public FloatInput makeEncoder(int aChannel, int bChannel, boolean reverse, EventInput resetWhen,
			EventInput updateOn) {
		return new SVXPortEncoder(aChannel, bChannel, reverse, resetWhen, updateOn);
	}

	@Override
	public FloatInput makeCounter(int upChannel, int downChannel, EventInput resetWhen, EventInput updateOn, int mode) {
		return null;
	}

	@Override
	public BooleanOutput makeRelayForwardOutput(int channel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BooleanOutput makeRelayReverseOutput(int channel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FloatInput makeGyro(int port, double sensitivity, EventInput resetWhen, EventInput updateOn) {
		FloatInput rawVolts = RoboRioBoard.analogInputPin(port);
		FloatCell accumulator = new FloatCell(0.0f);
		accumulator.accumulateWhen(RoboRioBoard.running(), rawVolts.multipliedBy((float)sensitivity));
		
		return accumulator;
	
	}

	@Override
	public FloatInput getBatteryVoltage(EventInput updateOn) {
 		return RoboRioBoard.battery();
	}

	@Override
	public EventInput getGlobalPeriodic() {
		return RoboRioBoard.running();
	}

	@Override
	public EventInput getStartAuto() {
		return EventInput.never;
	}

	@Override
	public EventInput getDuringAuto() {
		return EventInput.never;
	}

	@Override
	public EventInput getStartTele() {
 		return RoboRioBoard.started().onPress();
	}

	@Override
	public EventInput getDuringTele() {
		return RoboRioBoard.running();
	}

	@Override
	public EventInput getStartTest() {
		return EventInput.never;
	}

	@Override
	public EventInput getDuringTest() {
		return EventInput.never;
	}

	@Override
	public EventInput getStartDisabled() {
		return RoboRioBoard.ended().onPress();
	}

	@Override
	public EventInput getDuringDisabled() {
		//TODO: Make a not running periodic.
		return EventInput.never;
	}

	@Override
	public BooleanOutput usePCMCompressor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BooleanInput getPCMPressureSwitch(EventInput updateOn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BooleanInput getPCMCompressorRunning(EventInput updateOn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FloatInput getPCMCompressorCurrent(EventInput updateOn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FloatInput getPDPTotalCurrent(EventInput updateOn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FloatInput getPDPChannelCurrent(int channel, EventInput updateOn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FloatInput getPDPVoltage(EventInput updateOn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RS232Bus makeRS232_Onboard(String deviceName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RS232Bus makeRS232_MXP(String deviceName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RS232Bus makeRS232_USB(String deviceName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public I2CBus makeI2C_Onboard(String deviceName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public I2CBus makeI2C_MXP(String deviceName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SPIBus makeSPI_Onboard(int cs, String deviceName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SPIBus makeSPI_MXP(String deviceName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FloatInput getChannelVoltage(int powerChannel, EventInput updateOn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FloatInput getChannelCurrent(int powerChannel, EventInput updateOn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BooleanInput getChannelEnabled(int powerChannel, EventInput updateOn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ControlBindingCreator tryMakeControlBindingCreator(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventInput getOnInitComplete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUniqueIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

}
