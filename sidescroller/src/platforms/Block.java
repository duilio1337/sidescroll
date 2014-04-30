package platforms;

import jgame.GSprite;
import jgame.ImageCache;

public class Block extends GSprite implements SolidGround {
	
	public Block() {
		super(ImageCache.getImage("block.png"));
		setAnchorCenter();
	}

}
