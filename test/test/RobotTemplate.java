package test;

import ccre.channel.EventInput;
import ccre.channel.FloatInput;
import ccre.channel.FloatOutput;
import ccre.ctrl.PIDController;
import ccre.frc.FRC;
import ccre.frc.FRCApplication;

public class RobotTemplate implements FRCApplication {

    /**
     * This is where you specify your team number. It is used to find your
     * roboRIO when you download code.
     */
    public static final int TEAM_NUMBER = 2635;

    @Override
    public void setupRobot() {
//    	Logger.info("I'm settin up now bruh");
		FloatOutput motorController = FRC.victorSP(0);
		FloatInput joystickSetpoint = FRC.joystick1.axis(1);
		FloatInput encoder = FRC.encoder(0, 1, false, EventInput.never, FRC.duringTele);
		PIDController controller = PIDController.createFixed(FRC.duringTele, encoder.multipliedBy(0.25f), joystickSetpoint.multipliedBy(20f), 0.025f, 0.0f, 0.0f);
		controller.send(motorController);
		
//		Logger.info("I'm done settin up now bruh");
//    	FloatInput in = Device.floatInputPin("TEST_CONS");
//    	FloatOutput out = Device.floatOutputPin("TEST_GEN");
//    	in.plus(1.0f).send(out);
    }
}

