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
import svconnect.SchematicCommunicatorHolder;
import svx.SVXCommunicator;
import svx.frc.RoboRioBoard;
import svx.frc.SVXFRC;

public class RoboRioBoardConfigTest {

	public static void setupRobot()
	{
		FloatOutput motorController = FRC.victorSP(0);
		FloatInput joystickSetpoint = FRC.joystick1.axis(1);
		FloatInput encoder = FRC.encoder(0, 1, false, EventInput.never, FRC.duringTele);
		PIDController controller = PIDController.createFixed(FRC.duringTele, encoder.multipliedBy(0.25f), joystickSetpoint.multipliedBy(360f), 0.025f, 0.0f, 0.0f);
		controller.send(motorController);
		
	}
	public static void main(String[] args) {
		SVXCommunicator client = new SVXCommunicator();
		SchematicCommunicatorHolder.setImplementation(client);
		FRCImplementationHolder.setImplementation(new SVXFRC());
		File xmlFile = new File("PinManifest.xml");
		if(xmlFile.exists())
		{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = null;
			try {
				dBuilder = dbFactory.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Document doc = null;
			try {
				doc = dBuilder.parse(xmlFile);
				RoboRioBoard.setupFromFile(doc);
			} catch (SAXException | IOException e) {
				Logger.warning("Could not parse xml file. Will not set up.", e);
			}
		}
		else
		{
			Logger.warning("No PinManifest.xml found. Robot may not simulate properly.");
		}
		setupRobot();
		client.startCommunications();
	}
	

}
