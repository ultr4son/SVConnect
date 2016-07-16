package test;

import ccre.channel.FloatInput;
import ccre.channel.FloatOutput;
import svx.SVXCommunicator;

public class SVWebTest {

	public static void main(String[] args) {
		SVXCommunicator client = new SVXCommunicator();
		FloatOutput test = client.floatOutputPin("TEST_GEN");
		FloatInput test1 = client.floatInputPin("TEST_CONS");
		test1.plus(1.0f).send(test);
		client.startCommunications();
	}

}
