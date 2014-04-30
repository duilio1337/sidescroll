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
	private static double vx;
	private static double vy;
	private double outOfGround;

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
	 * bool used to see if char is hitting a wall
	 */
	private boolean onSolidWall;

	/**
	 * The amount of change in vx / frame
	 */
	private double gAcceleration;
	
	private double aAcceleration;

	private double targetX;
	private double targetY;
	private double targetHeight;
	private double targetWidth;
	private double groundObjectX;
	private double groundObjectY;
	private double groundObjectHeight;
	private double groundObjectWidth;

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
			double maxSpeed, double maxJump, double acceleration) {
		this(controlScheme, maxSpeed, maxJump, acceleration, DEFAULT_GRAVITY); // ++++++++++++++
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
			double maxSpeed, double maxJump, double acceleration, double gravity) {
		super();
		this.controlScheme = controlScheme;
		vx = vy = outOfGround = 0;
		this.maxSpeed = maxSpeed;
		this.maxJump = maxJump;
		this.gravity = gravity;
		this.gAcceleration = acceleration;
		this.aAcceleration = 0.1;
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
		outOfGround = 0;
		onSolidGround = false;
		onSolidWall = false;

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

				// increase efficiency?
				targetX = target.getX();
				targetY = target.getY();
				targetHeight = target.getHeight();
				targetWidth = target.getWidth();
				groundObjectX = groundObject.getX();
				groundObjectY = groundObject.getY();
				groundObjectHeight = groundObject.getHeight();
				groundObjectWidth = groundObject.getWidth();

				// WIP: TRYING TO MAKE YOU NOT ABLE TO WALK THROUGH PLATFORMS,
				// will NEED TO COPY CONTROLLER FOR HERO
				if ((targetY - (targetHeight / 2)) < (groundObjectY + (groundObjectHeight / 2) - 50)
						&& (targetY + (targetHeight / 2)) > (groundObjectY - (groundObjectHeight / 2)) + 5) {

					if ((targetX - (targetWidth / 2)) < (groundObjectX + (groundObjectWidth / 2) - 20)
							&& (targetX + (targetWidth / 2)) > (groundObjectX - (groundObjectWidth / 2) + 20)) {

						outOfGround = -(targetY + (targetHeight / 2))
								+ (groundObjectY - (groundObjectHeight / 2));

					} else if ((targetX - (targetWidth / 2)) < (groundObjectX + (groundObjectWidth / 2))
							&& (targetX + (targetWidth / 2)) > (groundObjectX - (groundObjectWidth / 2))) {
						
						vx = 0;
						onSolidWall = true;
					}
				}
				
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
			

			/*
			 * if (vy + gravity < target.getHeight()) { vy += gravity; }
			 */

			// vy = Math.min(vy, target.getHeight());
			// vy += gravity;
		}
		
		if (onSolidGround) {
			if (!onSolidWall && vx < maxSpeed && horizontal > 0) {
				vx += horizontal * gAcceleration;
			} else if (!onSolidWall && vx > -maxSpeed && horizontal < 0) {
				vx += horizontal * gAcceleration;
			} else if (!onSolidWall && horizontal == 0 && vx > -1 && vx < 1) {
				vx = 0;
			} else if (!onSolidWall && horizontal == 0 && vx > 0) {
				vx -= gAcceleration;
			} else if (!onSolidWall && horizontal == 0 && vx < 0) {
				vx += gAcceleration;
			} else if (onSolidWall && targetX < groundObjectX && horizontal > 0) {
				vx += horizontal * gAcceleration;
			} else if (onSolidWall && targetX > groundObjectX && horizontal < 0) {
				vx += horizontal * gAcceleration;
			}
		} else {
			if (!onSolidWall && vx < maxSpeed && horizontal > 0) {
				vx += horizontal * aAcceleration;
			} else if (!onSolidWall && vx > -maxSpeed && horizontal < 0) {
				vx += horizontal * aAcceleration;
			} else if (!onSolidWall && vx > 0 && horizontal == 0) {
				vx -= aAcceleration;
			} else if (!onSolidWall && vx < 0 && horizontal == 0) {
				vx += aAcceleration;
			}
		}
		System.out.println(vx);
		target.setLocation(target.getX() , target.getY() + vy + outOfGround);
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
	
	public static double getvx() {
		return vx;
	}
	
	public static double getvy() {
		return vy;
	}

	public boolean isOnSolidGround() {
		return onSolidGround;
	}
	
	public boolean isOnSolidWall() {
		return onSolidWall;
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