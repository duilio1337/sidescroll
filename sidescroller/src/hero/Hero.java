package hero;

import mechanics.PlatformControlScheme;
import mechanics.PlatformController;
import jgame.GSprite;
import jgame.ImageCache;

public class Hero extends GSprite {
	
	public Hero() {
		super(ImageCache.getSequentialImages("chickenr/cr00", 01, 69, ".png"));
		
		PlatformController hc = new PlatformController(PlatformControlScheme.WASD, 10, -40, 1.5, 2);
		addController(hc);
	}
}
