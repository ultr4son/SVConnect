package test;

import ccre.channel.BooleanInput;
import ccre.channel.BooleanOutput;
import ccre.frc.FRC;
import ccre.frc.FRCImplementationHolder;
import svconnect.SchematicCommunicatorHolder;
import svx.SVXCommunicator;
import svx.frc.SchematicFRC;

public class DIOSimulationTest {
	public static void setupRobot()
	{
		BooleanInput digitalInput1 = FRC.digitalInput(1);
		
		BooleanOutput digitalOutput1 = FRC.digitalOutput(1);
		
		digitalInput1.send(digitalOutput1);

	}
	public static void main(String[] args) {
		SVXCommunicator client = new SVXCommunicator();
		SchematicCommunicatorHolder.setImplementation(client);
		FRCImplementationHolder.setImplementation(new SchematicFRC());
		setupRobot();
		client.startCommunications();
		
	}

}
