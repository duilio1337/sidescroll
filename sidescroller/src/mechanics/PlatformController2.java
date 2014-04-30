package mechanics;

import jgame.Context;
import jgame.GObject;
import jgame.controller.Controller;

/**
 * A controller that controls an objects location based on keyboard input
 * defined by the given {@link PlatformControlScheme}. This controller cannot be
 * shared.
 * 
 * @author Alma Chagrin
 * 
 */
public class PlatformController2 implements Controller {
	/**
	 * These two variables describe the current velocity of the player.
	 */
	private double vx;
	private GObject object;

	public PlatformController2() {
		super();
	}

	@Override
	public void controlObject(final GObject target, Context context) {
		// Ensure that this is locked to one object
		if (object == null) {
			object = target;
		} else if (object != target) {
			throw new IllegalArgumentException(
					"This PlatformController already belongs to " + object);
		}
		vx = PlatformController.getvx();
		
		target.setLocation(target.getX() + vx, target.getY());

	}

}