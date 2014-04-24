package mechanics;

import jgame.Context;
import jgame.GObject;
import jgame.controller.Controller;
import jgame.listener.FrameListener;
import mechanics.PlatformController;

/**
 * A controller that controls an objects location based on keyboard input
 * defined by the given {@link PlatformControlScheme}. This controller cannot be
 * shared.
 * 
 * @author Alma Chagrin
 * 
 */
public class PlatformController2 implements Controller{
	/**
	 * These two variables describe the current velocity of the player.
	 */
	private double vx, vy;
	private GObject object;

	public PlatformController2() {

	}

	public void controlObject(final GObject target, Context context) {
		// Ensure that this is locked to one object
		if (object == null) {
			object = target;
		} else if (object != target) {
			throw new IllegalArgumentException(
					"This PlatformController already belongs to " + object);
		}
		FrameListener fl = new FrameListener() {

			@Override
			public void invoke(GObject target2, Context context) {
				vx = getFirstAncestorOf(PlatformController.class).getvx();
				target.setLocation(target.getX() + vx, target.getY() + vy);
			}

		};
		addListener(fl);
	}

}