package hero;

import jgame.GSprite;
import jgame.ImageCache;

public class Hero extends GSprite {
	
	public Hero() {
		super(ImageCache.getSequentialImages("chickenr/cr00", 01, 69, ".png"));
	}
}
