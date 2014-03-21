package mechanics;

import java.util.List;
import java.util.Set;

import platforms.SolidGround;
import jgame.Context;
import jgame.GObject;
import jgame.controller.Controller;

/**
 * A controller that controls an objects location based on keyboard input
 * defined by the given {@link PlatformControlScheme}. This controller cannot be
 * shared.
 * 
 * @author William Chargin
 * 
 */
public class PlatformController implements Controller {
	/**
	 * The default gravitational acceleration, in px/frame<sup>2</sup>.
	 */
	public static final double DEFAULT_GRAVITY = 1;
	/**
	 * The control scheme for this platform controller.
	 */
	private final PlatformControlScheme controlScheme;

	/**
	 * The object to be controlled by this platform controller.
	 */
	private GObject object;

	/**
	 * These two variables describe the current velocity of the player.
	 */
	private double vx, vy;

	/**
	 * The maximum horizontal movement speed.
	 */
	private double maxSpeed;

	/**
	 * The maximum jump speed.
	 */
	private double maxJump;

	/**
	 * The gravitational acceleration, in px/frame<sup>2</sup>.
	 */
	private double gravity;
	
	/**
	 * used to test if char in on solid ground
	 */
	private boolean onSolidGround;

	/**
	 * Creates the controller with the given parameters. The default
	 * gravitational acceleration will be used.
	 * 
	 * @param controlScheme
	 *            the control scheme to use
	 * @param maxSpeed
	 *            the maximum horizontal speed (in px/frame)
	 * @param maxJump
	 *            the maximum jump speed (in px/frame)
	 */
	public PlatformController(PlatformControlScheme controlScheme,
			double maxSpeed, double maxJump) {
		this(controlScheme, maxSpeed, maxJump, DEFAULT_GRAVITY); // ++++++++++++++
																	// ??????????????????????????????
	}

	/**
	 * Creates the controller with the given parameters.
	 * 
	 * @param controlScheme
	 *            the control scheme to use
	 * @param maxSpeed
	 *            the maximum horizontal speed (in px/frame)
	 * @param maxJump
	 *            the maximum jump speed (in px/frame)
	 * @param gravity
	 *            the gravitation acceleration (in px/frame<sup>2</sup>)
	 */
	public PlatformController(PlatformControlScheme controlScheme,
			double maxSpeed, double maxJump, double gravity) {
		super();
		this.controlScheme = controlScheme;
		vx = vy = 0;
		this.maxSpeed = maxSpeed;
		this.maxJump = maxJump;
		this.gravity = gravity;
	}

	@Override
	public void controlObject(GObject target, Context context) {
		// Ensure that this is locked to one object
		if (object == null) {
			object = target;
		} else if (object != target) {
			throw new IllegalArgumentException(
					"This PlatformController already belongs to " + object);
		}

		Set<Integer> keys = context.getKeyCodesPressed();

		int horizontal = 0;
		boolean jump = false;

		for (int key : keys) {
			if (key == controlScheme.lt) {
				horizontal += 1;
			} else if (key == controlScheme.rt) {
				horizontal -= 1;
			} else if (key == controlScheme.jump) {
				jump = true;
			}
		}

		onSolidGround = false;

		List<SolidGround> solidGrounds = context
				.getInstancesOfClass(SolidGround.class);

		for (SolidGround solidGround : solidGrounds) {

			GObject groundObject = (GObject) solidGround;
			if (target.hitTest(groundObject)) {
				if (target.getY() > groundObject.getY()) {
					if (vy < 0) {
						vy = 0;
					}
					onSolidGround = false;
				} else {
					onSolidGround = true;
				}

				//WIP: TRYING TO MAKE YOU NOT ABLE TO WALK THROUGH PLATFORMS, MAY NEED TO COPY CONTROLLER FOR HERO
//				if ((target.getY() - (target.getHeight() / 2)) <= (groundObject
//						.getY() + (groundObject.getHeight() / 2))
//						&& (target.getY() + (target.getHeight() / 2)) >= (groundObject
//								.getY() - (groundObject.getHeight() / 2))) {
//
//					if ((target.getX() - (target.getWidth() / 2)) <= (groundObject
//							.getX() + (groundObject.getWidth() / 2))
//							&& (target.getX() + (target.getWidth() / 2)) >= (groundObject
//									.getX() - (groundObject.getWidth() / 2))) {
//						horizontal = 0;
//						vx = 0;
//						System.out.println("STOPET");
//					}
//					System.out.println("BLOOP");
//				} 
				break;
			}

		}

		if (onSolidGround) {
			if (jump) {

				vy = maxJump;

			} else {

				vy = 0;
			}
		} else {
			vy += gravity;
			if (vy > target.getHeight()) {
				vy = target.getHeight();
			}

			/*
			 * if (vy + gravity < target.getHeight()) { vy += gravity; }
			 */

			// vy = Math.min(vy, target.getHeight());
			// vy += gravity;
		}

		if (onSolidGround) {
			vx = horizontal * maxSpeed;
		}

		target.setLocation(target.getX() + vx, target.getY() + vy);
	}

	/**
	 * Gets the current maximum jump.
	 * 
	 * @return the maximum jump, in px/frame
	 */
	public double getMaxJump() {
		return maxJump;
	}

	/**
	 * Gets the current maximum horizontal speed.
	 * 
	 * @return the maximum horizontal speed, in px/frame
	 */
	public double getMaxSpeed() {
		return maxSpeed;
	}
	
	public boolean getOnSolidGround() {
		return onSolidGround;
	}
	
	/**
	 * Sets the new maximum jump.
	 * 
	 * @param maxJump
	 *            the maximum jump, in px/frame
	 */
	public void setMaxJump(double maxJump) {
		this.maxJump = maxJump;
	}

	/**
	 * Sets the maximum horizontal speed.
	 * 
	 * @param maxSpeed
	 *            the new maximum horizontal speed, in px/frame
	 */
	public void setMaxSpeed(double maxSpeed) {
		if (maxSpeed < 0) {
			throw new IllegalArgumentException("maxSpeed must be nonnegative: "
					+ maxSpeed);
		}
		this.maxSpeed = maxSpeed;
	}

}