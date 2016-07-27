package svx.frc;

import ccre.channel.BooleanInput;
import ccre.channel.FloatInput;
import ccre.channel.FloatOutput;
import ccre.ctrl.Joystick;

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
		return RoboRioBoard.Joystick(channel).Button(btn);
	}

	@Override
	public FloatInput axis(int axis) {
		return RoboRioBoard.Joystick(channel).Axis(axis);
	}
	//TODO: Could theoretically simulate vibrations of the controller.
	@Override
	public FloatOutput rumble(boolean right) {
		return FloatOutput.ignored;
	}

	@Override
	public BooleanInput isPOV(int direction) {
		return RoboRioBoard.Joystick(channel).POV().inRange((float)direction, (float)direction);
	}

}
