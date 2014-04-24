package platforms;

import mechanics.PlatformController2;
import jgame.GSprite;
import jgame.ImageCache;

public class Block2080 extends GSprite implements SolidGround {
	
	public Block2080() {
		super(ImageCache.getImage("block2080.png"));
		
		PlatformController2 pc = new PlatformController2();
		addController(pc);
	}

}
