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
import svx.frc.SchematicFRC;

public class CSTA {
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
//		FloatInput joystick = FRC.joystick1.axis(1);
		FloatOutput pwm1 = FRC.victor(1);
//		FloatOutput pwm2 = FRC.victor(2);
//		FloatOutput pwm3 = FRC.victor(3);
//		joystick.dividedBy(5.0).send(pwm1);
//		joystick.dividedBy(2.0f).send(pwm2);
//		joystick.send(pwm3);
		pwm1.set(1.0f);
	}
	public static void main(String[] args) {
		SVXCommunicator client = new SVXBooleanWorkaroundCommunicator(); //Use integer generators instead of boolean
		SchematicCommunicatorHolder.setImplementation(client);
		FRCImplementationHolder.setImplementation(new SchematicFRC());
		//setupFromManifest(new File("PinManifest.xml"));
		setupRobot();
		client.startCommunications();
		System.out.println("done");
	}

}
