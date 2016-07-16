package test;

import ccre.channel.EventInput;
import ccre.channel.FloatInput;
import ccre.channel.FloatOutput;
import ccre.ctrl.PIDController;
import ccre.frc.FRC;
import ccre.frc.FRCImplementationHolder;
import svconnect.SchematicCommunicatorHolder;
import svx.SVXCommunicator;
import svx.SVXBooleanWorkaroundCommunicator;
import svx.frc.SVXFRC;

public class FRCPIDTest {
	public static void setupRobot(){
		FloatOutput motorController = FRC.victorSP(0);
		FloatInput joystickSetpoint = FRC.joystick1.axis(1);
		FloatInput encoder = FRC.encoder(0, 1, false, EventInput.never);
		PIDController controller = PIDController.createFixed(FRC.duringTele, encoder.multipliedBy(0.25f), joystickSetpoint.multipliedBy(360f), 0.025f, 0.0f, 0.0f);
		controller.send(motorController);
		
	}
	public static void main(String[] args) {
		SVXCommunicator client = new SVXBooleanWorkaroundCommunicator(); //Use integer generators instead of boolean
		SchematicCommunicatorHolder.setImplementation(client);
		FRCImplementationHolder.setImplementation(new SVXFRC());
		setupRobot();
		client.startCommunications();
		System.out.println("done");
	}

}
