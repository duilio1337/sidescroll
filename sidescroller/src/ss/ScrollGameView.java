package ss;

import hero.Hero;
import mechanics.PlatformControlScheme;
import mechanics.PlatformController;
import platforms.Base;
import jgame.GContainer;
import jgame.GSprite;
import jgame.ImageCache;

public class ScrollGameView extends GContainer {
	
	public ScrollGameView() {
		setSize(1280, 720);
	}
	
	public void viewShown() {
		initScrollGameView();
	}
	
	public void initScrollGameView() {
		GSprite bk = new GSprite(ImageCache.getImage("background.png"));
		bk.setAnchorTopLeft();
		addAt(bk, 0, 0);
		
		PlatformController bgc = new PlatformController(PlatformControlScheme.WASD, 10, 0, 0);
		bk.addController(bgc);
		
		addAt(new Base(), 1280/2, 700);
		
		Hero hero = new Hero();
		addAt(hero, 1280/2, 720/2);
		PlatformController hc = new PlatformController(PlatformControlScheme.WASD, 0, -40, 2 );
		hero.addController(hc);
	}

}
