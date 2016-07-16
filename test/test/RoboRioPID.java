package test;

import ccre.channel.FloatInput;
import ccre.channel.FloatOutput;
import ccre.ctrl.PIDController;
import svconnect.Device;
import svconnect.SchematicCommunicatorHolder;
import svx.SVXCommunicator;

public class RoboRioPID {
	public static void setupRobot()
	{
		FloatInput commandPin = Device.floatInputPin("COMMAND");
		FloatInput angleFeedbackPin = Device.floatInputPin("ANGLE_FEEDBACK");
		FloatOutput dutyPin = Device.floatOutputPin("DUTY");
		PIDController pidController = PIDController.createFixed(Device.running(), angleFeedbackPin, commandPin, 0.01f, 0.0f, 0.0f);
		pidController.setOutputBounds(1.0f);
		pidController.send(dutyPin);
	}
	public static void main(String[] args) {
		SVXCommunicator client = new SVXCommunicator();
		SchematicCommunicatorHolder.setImplementation(client);
		setupRobot();
		client.startCommunications();
	}

}
