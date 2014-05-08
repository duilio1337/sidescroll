package ss;

import hero.Coin;
import hero.Hero;
import mechanics.PlatformController2;
import platforms.Base;
import platforms.Block2080;
import jgame.GContainer;
import jgame.GSprite;
import jgame.ImageCache;

public class ScrollGameView extends GContainer {
	
	public final Hero hero = new Hero();
	Block2080 block2080 = new Block2080();
	PlatformController2 bgc = new PlatformController2();
	
	public ScrollGameView() {
		setSize(1280, 720);
	}
	
	public void viewShown() {
		initScrollGameView();
	}
	
	public void initScrollGameView() {
		removeAllChildren();
		
		GSprite bk = new GSprite(ImageCache.getImage("background2.png"));
		bk.setAnchorTopLeft();
		addAtCenter(bk);
		bk.addController(bgc);
		
		addAt(new Base(), 1280/2, 700);
		
		hero.setAnchorCenter();
		addAt(hero, 1280/2, 720/2);
		
		
		createPlatforms();
	}
	
	public void createPlatforms() {
		addAt(new Block2080(), 1280/2, 640);
		addAt(new Block2080(), 1280/2, 400);
		addAt(new Block2080(), 1280*1.1, 400);
		addAt(new Block2080(), 1280*1.4, 400);
		addAt(new Block2080(), 1280*2.2, 400);
		addAt(new Coin(), 1280/2, 500);
		addAt(new Coin(), 1280*1.1, 500);
		addAt(new Coin(), 1280*1.4, 500);
		addAt(new Coin(), 1280*2.2, 500);
	}

}
