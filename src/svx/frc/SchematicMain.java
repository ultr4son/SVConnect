package svx.frc;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import ccre.cluck.Cluck;
import ccre.frc.FRCApplication;
import ccre.frc.FRCImplementationHolder;
import ccre.log.FileLogger;
import ccre.log.Logger;
import ccre.log.NetworkAutologger;
import svconnect.SchematicCommunicatorHolder;
import svx.SVXBooleanWorkaroundCommunicator;
import svx.SVXCommunicator;


public class SchematicMain {
	public static void simulate(String mainClass, Boolean useBooleanConsumerWorkaround, String pinManifestPath) throws Exception
	{       

		SVXCommunicator client;
		
		if(useBooleanConsumerWorkaround)
		{
			Logger.info("Using boolean workaround. Remeber to use integer generators!");
			client = new SVXBooleanWorkaroundCommunicator();
		}
		else
		{
			client = new SVXCommunicator();
		}
		SchematicCommunicatorHolder.setImplementation(client);
		FRCImplementationHolder.setImplementation(new SVXFRC());
		File pinManifestFile = new File(pinManifestPath);
		preSetup(pinManifestFile);
		//Generate a new FRCApplication
		try {
			Class<? extends FRCApplication> asSubclass = SchematicMain.class.getClassLoader().loadClass(mainClass).asSubclass(FRCApplication.class);
			asSubclass.getConstructor().newInstance().setupRobot();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		Logger.info("Listening for schematic simulation...");
		client.startCommunications();
		Logger.info("Done simulating!");
		
	}
	private static void preSetup(File pinManifest)
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
}
