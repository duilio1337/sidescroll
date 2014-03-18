package platforms;

import jgame.GSprite;
import jgame.ImageCache;

public class Base extends GSprite implements SolidGround {
	
	public Base() {
		super(ImageCache.getImage("bottom.png"));
	}

}
