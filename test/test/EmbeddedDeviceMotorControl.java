package test;

import ccre.channel.FloatInput;
import ccre.channel.FloatOutput;
import ccre.ctrl.PIDController;
import svconnect.Device;
import svconnect.SchematicCommunicator;
import svconnect.SchematicCommunicatorHolder;
import svx.SVXCommunicator;

public class EmbeddedDeviceMotorControl {

	public static void setupDevice()
	{
		float batteryVoltage = 12.0f;
		FloatInput encoderFeedback = Device.floatInputPin("ENCODER");
		FloatOutput desiredApparentVoltage = Device.floatOutputPin("PID_SIGNAL");
		FloatInput apparentVoltageSetpoint = Device.floatInputPin("JOYSTICK").multipliedBy(batteryVoltage);
		PIDController pid = PIDController.createFixed(Device.running(), encoderFeedback, apparentVoltageSetpoint, 0.1f, 0.0f, 0.0f);
		pid.setOutputBounds(batteryVoltage);
		pid.send(desiredApparentVoltage);
	}
	public static void main(String[] args) {
		SVXCommunicator svx = new SVXCommunicator();
		SchematicCommunicatorHolder.setImplementation(svx);
		setupDevice();
		svx.startCommunications();
		
	}

}
