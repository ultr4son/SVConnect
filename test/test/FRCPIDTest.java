package test;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import ccre.channel.EventInput;
import ccre.channel.FloatInput;
import ccre.channel.FloatOutput;
import ccre.ctrl.PIDController;
import ccre.frc.FRC;
import ccre.frc.FRCImplementationHolder;
import ccre.log.Logger;
import ccre.scheduler.Scheduler;
import svconnect.SchematicCommunicatorHolder;
import svx.SVXCommunicator;
import svx.SVXBooleanWorkaroundCommunicator;
import svx.frc.RoboRioBoard;
import svx.frc.SVXFRC;

public class FRCPIDTest {
	private static void setupFromManifest(File pinManifest)
	{
		if(pinManifest.exists())
		{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = null;
		
			Document doc = null;
			try {
				dBuilder = dbFactory.newDocumentBuilder();
				doc = dBuilder.parse(pinManifest);
			} catch (ParserConfigurationException | SAXException | IOException e) {
				Logger.warning("Could not parse xml file. Robot may not simulate properly.", e);
				return; //Forget about setting up.
			}
			RoboRioBoard.setupFromFile(doc);
		}
		else
		{
			Logger.warning("No PinManifest.xml found. Robot may not simulate properly.");
		}

	}
	//This is what the user sees
	public static void setupRobot(){
		FloatOutput motorController = FRC.victorSP(0);
		FloatInput joystickSetpoint = FRC.joystick1.axis(1);
		FloatInput encoder = FRC.encoder(0, 1, false, EventInput.never, FRC.duringTele);
		PIDController controller = PIDController.createFixed(FRC.duringTele, encoder.multipliedBy(0.25f), joystickSetpoint.multipliedBy(360f), 0.025f, 0.0f, 0.0f);
		controller.send(motorController);
		
	}
	public static void main(String[] args) {
		SVXCommunicator client = new SVXBooleanWorkaroundCommunicator(); //Use integer generators instead of boolean
		SchematicCommunicatorHolder.setImplementation(client);
		FRCImplementationHolder.setImplementation(new SVXFRC());
		setupFromManifest(new File("PinManifest.xml"));
		setupRobot();
		client.startCommunications();
		System.out.println("done");
	}

}
