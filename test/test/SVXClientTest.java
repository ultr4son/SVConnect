package test;

import ccre.channel.FloatInput;
import ccre.channel.FloatOutput;
import svconnect.Device;
import svconnect.SchematicCommunicatorHolder;
import svx.SVXCommunicator;

public class SVXClientTest {
	public static void setupDevice()
	{
		FloatInput in = Device.floatInputPin("SV_OUTPUT");
		FloatOutput out = Device.floatOutputPin("SV_INPUT");
		in.plus(1.0f).send(out);
	}
	public static void main(String[] args) {
		SVXCommunicator client = new SVXCommunicator();
		SchematicCommunicatorHolder.setImplementation(client);
		setupDevice();
		client.startCommunications();
		
	}

}
