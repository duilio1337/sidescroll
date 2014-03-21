package ss;

import hero.Hero;
import mechanics.PlatformControlScheme;
import mechanics.PlatformController;
import platforms.Base;
import platforms.Block;
import platforms.Block2080;
import jgame.GContainer;
import jgame.GSprite;
import jgame.ImageCache;

public class ScrollGameView extends GContainer {
	
	Block2080 block2080 = new Block2080();
	
	public ScrollGameView() {
		setSize(1280, 720);
	}
	
	public void viewShown() {
		initScrollGameView();
	}
	
	public void initScrollGameView() {
		removeAllChildren();
		
		GSprite bk = new GSprite(ImageCache.getImage("background.png"));
		bk.setAnchorTopLeft();
		addAt(bk, 0, 0);
		
		PlatformController bgc = new PlatformController(PlatformControlScheme.WASD, 10, 0, 0);
		bk.addController(bgc);
		
		addAt(new Base(), 1280/2, 700);
		
		Hero hero = new Hero();
		hero.setAnchorCenter();
		addAt(hero, 1280/2, 720/2);
		
		PlatformController hc = new PlatformController(PlatformControlScheme.WASD, 0, -40, 2 );
		hero.addController(hc);
		
		createPlatforms();
	}
	
	public void createPlatforms() {
		PlatformController pc = new PlatformController(PlatformControlScheme.WASD, 10, 0, 0);
		
		addAt(block2080, 1280/2, 720/1.5);
		block2080.setAnchorCenter();
		block2080.addController(pc);
	}

}
