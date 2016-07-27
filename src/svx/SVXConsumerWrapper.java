package svx;

import com.mentor.systems.svx.ISVXNotification;
import com.mentor.systems.svx.SVXStatusException;

import ccre.channel.AbstractUpdatingInput;
import ccre.channel.BooleanCell;

/**
 * A base class for wrappers around SVX Consumers. The notification() method of this class should be called after the extending class's notification().
 * @author tthompso
 *
 */
public class SVXConsumerWrapper extends AbstractUpdatingInput implements ISVXNotification {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7015985946585770444L;
	BooleanCell done = new BooleanCell();
	@Override
	public void notification() throws SVXStatusException {
		perform();
		done.set(true);
	}
	
}
