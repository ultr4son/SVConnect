package test;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import ccre.channel.FloatOutput;
import ccre.frc.FRC;
import ccre.frc.FRCImplementationHolder;
import ccre.log.Logger;
import svconnect.SchematicCommunicatorHolder;
import svx.SVXCommunicator;
import svx.frc.RoboRioBoard;
import svx.frc.SchematicFRC;

public class MoreSimpleManifestTest {
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
	public static void setupRobot()
	{
		FloatOutput log = new FloatOutput() {
			
			@Override
			public void set(float value) {
				Logger.info(((Float)value).toString());
				
			}
		};
		FRC.joystick1.axis(1).send(log);
	}
	public static void main(String[] args) {
		SVXCommunicator client = new SVXCommunicator();
		SchematicCommunicatorHolder.setImplementation(client);
		FRCImplementationHolder.setImplementation(new SchematicFRC());
		File moreSimpleManifest = new File("MoreSimpleManifest.xml");
		setupFromManifest(moreSimpleManifest);
		setupRobot();
		client.startCommunications();
	}

}
