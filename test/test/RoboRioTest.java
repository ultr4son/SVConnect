package test;

import ccre.channel.FloatInput;
import ccre.channel.FloatOutput;
import ccre.frc.FRC;
import ccre.frc.FRCImplementationHolder;
import svconnect.SchematicCommunicatorHolder;
import svx.SVXCommunicator;
import svx.frc.SchematicFRC;

public class RoboRioTest {
	public static void setupRobot()
	{

		FloatInput joystick = FRC.joystick1.axis(1);
		FloatOutput spark = FRC.spark(1);
		joystick.send(spark);
	}
	public static void main(String[] args) {
		SVXCommunicator client = new SVXCommunicator();
		SchematicCommunicatorHolder.setImplementation(client);
		FRCImplementationHolder.setImplementation(new SchematicFRC());
		setupRobot();
		client.startCommunications();
	}

}
