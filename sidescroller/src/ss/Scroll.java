package ss;

import java.awt.Color;

import jgame.GRootContainer;
import jgame.Game;
import jgame.ImageCache;
import jgame.SoundManager;

public class Scroll extends Game{
	private static int coins;
	
	public static void main(String[] args) {
		ImageCache.create(Scroll.class, "/ss/rsc/");
		SoundManager.create(Scroll.class, "/ss/rsc/");
		Scroll s1 = new Scroll();
		s1.startGame("Side Scroller");
	}
	
	public Scroll() {
		GRootContainer root = new GRootContainer(Color.BLACK);
		setRootContainer(root);
		
		root.addView(Views.GAME, new ScrollGameView());
		
		setTargetFPS(30);
	}
	
	public enum Views {
		GAME
	}
	
	public static int getCoins() {
		return coins;
	}
	
	public static void addCoins(int num) {
		coins += num;
	}
}
