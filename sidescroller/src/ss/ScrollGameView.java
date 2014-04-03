package ss;

import hero.Hero;
import mechanics.PlatformControlScheme;
import mechanics.PlatformController;
import mechanics.PlatformController2;
import platforms.Base;
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
		
		PlatformController2 bgc = new PlatformController2(PlatformControlScheme.WASD, 10, 0, 1.5, 0);
		//bk.addController(bgc);
		
		addAt(new Base(), 1280/2, 700);
		
		Hero hero = new Hero();
		hero.setAnchorCenter();
		addAt(hero, 1280/2, 720/2);
		
		PlatformController hc = new PlatformController(PlatformControlScheme.WASD, 10, -40, 1.5, 2);
		hero.addController(hc);
		
		createPlatforms();
	}
	
	public void createPlatforms() {
		PlatformController2 pc = new PlatformController2(PlatformControlScheme.WASD, 10, 0, 1.5, 0);
		
		addAt(block2080, 1280/2, 630);
		block2080.setAnchorCenter();
		//block2080.addController(pc);
	}

}
