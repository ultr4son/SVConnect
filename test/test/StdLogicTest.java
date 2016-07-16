package test;

import ccre.channel.BooleanInput;
import ccre.channel.BooleanOutput;
import ccre.channel.EventOutput;
import svconnect.Device;
import svconnect.SchematicCommunicatorHolder;
import svx.SVXCommunicator;

public class StdLogicTest {

	public static void setupRobot()
	{
		BooleanInput clock = Device.booleanInputPin("SV_CLOCK");
		BooleanOutput outA = Device.booleanOutputPin("SV_INPUT_A");
		BooleanOutput outB = Device.booleanOutputPin("SV_INPUT_B");
		EventOutput clockTrue = new EventOutput()
				{
					@Override
					public void event() {
						outA.set(true);
						outB.set(true);
					}
				};
		EventOutput clockFalse = new EventOutput()
				{

					@Override
					public void event() {
						outA.set(true);
						outB.set(false);
					}
				
				};
		clock.onPress().send(clockTrue);
		clock.onRelease().send(clockFalse);
	}
	public static void main(String[] args) {
		SVXCommunicator client = new SVXCommunicator();
		SchematicCommunicatorHolder.setImplementation(client);
		setupRobot();
		client.startCommunications();
	}

}
