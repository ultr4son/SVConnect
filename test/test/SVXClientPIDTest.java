package test;

import ccre.channel.FloatInput;
import ccre.channel.FloatOutput;
import ccre.ctrl.PIDController;
import svx.SVXCommunicator;

public class SVXClientPIDTest {

	public static void main(String[] args) {
		SVXCommunicator client = new SVXCommunicator();
		FloatInput in = client.floatInputPin("SV_FEEDBACK_OUTPUT");
		FloatInput setpointIn = client.floatInputPin("SV_SETPOINT_OUTPUT");
		FloatOutput out = client.floatOutputPin("SV_INPUT");
		PIDController controller = PIDController.createFixed(client.simulationRunning(), in, setpointIn, 1.0f, 0.5f, 0.0f);
		out.setWhen(controller, client.simulationRunning()); //out needs to be updated every clock tick
		client.startCommunications();
		
	}

}
