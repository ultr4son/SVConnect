package svx.frc;

import ccre.channel.BooleanInput;
import ccre.channel.FloatInput;
import ccre.channel.FloatOutput;
import ccre.ctrl.Joystick;
import svconnect.RoboRioBoard;

/**
 * A joystick that uses RoboRioBoard based IO.
 * @author tthompso
 *
 */
public class SVXJoystick implements Joystick{

	int channel;
	public SVXJoystick(int id) {
		channel = id;
	}

	@Override
	public BooleanInput button(int btn) {
		return RoboRioBoard.joystickButtonPort(channel, btn);
	}

	@Override
	public FloatInput axis(int axis) {
		return RoboRioBoard.joystickAxisPort(channel, axis);
	}
	//TODO: Could theoretically simulate vibrations of the controller.
	@Override
	public FloatOutput rumble(boolean right) {
		return FloatOutput.ignored;
	}

	@Override
	public BooleanInput isPOV(int direction) {
		return RoboRioBoard.joystickPOVPort(channel).inRange((float)direction, (float)direction);
	}

}
