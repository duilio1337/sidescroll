package hero;

import mechanics.PlatformController2;
import jgame.Context;
import jgame.GObject;
import jgame.GSprite;
import jgame.ImageCache;
import jgame.SoundManager;
import jgame.listener.FrameListener;

public class Coin extends GSprite {
	
	FrameListener fl;
	
	public Coin() {
		super(ImageCache.getImage("coin.png"));
		addController(new PlatformController2());
		
		fl = new FrameListener() {
			@Override
			public void invoke(GObject target, Context context) {
				if(hitTest(getFirstAncestorOf(ss.ScrollGameView.class).hero)){
					SoundManager.playSound("coin1.wav");
					removeSelf();
					removeListener(fl);
				}
				
			}
		};
		addListener(fl);
	}
}
