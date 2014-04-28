package platforms;

import java.awt.Image;

import jgame.GSprite;

public abstract class Platform extends GSprite implements SolidGround {
	
	public Platform(Image image) {
		super(image);
	}
}
