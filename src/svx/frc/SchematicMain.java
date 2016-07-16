package svx.frc;

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
	public static void simulate(String mainClass, Boolean useBooleanConsumerWorkaround) throws Exception
	{
		Logger.info("I'm in");
       

		SVXCommunicator client;
		
		if(useBooleanConsumerWorkaround)
		{
			client = new SVXBooleanWorkaroundCommunicator();
		}
		else
		{
			client = new SVXCommunicator();
		}
		
		SchematicCommunicatorHolder.setImplementation(client);
		FRCImplementationHolder.setImplementation(new SVXFRC());
		
		//Generate a new FRCApplication
		try {
			Class<? extends FRCApplication> asSubclass = SchematicMain.class.getClassLoader().loadClass(mainClass).asSubclass(FRCApplication.class);
			asSubclass.getConstructor().newInstance().setupRobot();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		Logger.info("Listening for schematic simulation...");
		System.out.println("Listening for schematic simulation...");

		client.startCommunications();
	}
}
