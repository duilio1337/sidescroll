package platforms;

import mechanics.PlatformController2;
import jgame.ImageCache;

public class Block20160 extends Platform {
	
	public Block20160() {
		super(ImageCache.getImage("block20160.png"));
		
		PlatformController2 pc = new PlatformController2();
		addController(pc);
		setAnchorCenter();
	}

}
