package javaBomberman.entities;

import javaBomberman.InPutHandler;
import javaBomberman.gfx.Colours;
import javaBomberman.gfx.Screen;
import javaBomberman.level.Level;

public class Player extends Mob{

	private InPutHandler input;
	private int color = Colours.get(-1, 111, 145, 543);
	
	public Player(Level level,int x,int y, InPutHandler input ) {
		super(level,"Player", x, y, 1);
		this.input=input;
	}

	

	public void tick() {
		
		int xa=0;
		int ya=0;
		
		if (input.up.isPressed()) {
			ya--;
		}
		if (input.down.isPressed()) {
			ya++;
		}
		if (input.left.isPressed()) {
			xa--;
		}
		if (input.right.isPressed()) {
			xa++;
		}
		
		if (xa!=0||ya!=0) {
			move(xa, ya);
			isMoving = true;
		} else {
			isMoving = false;
		}

	}

	public void render(Screen screen) {
		int xTile = 0;
		int yTile = 36; 
		
		int modifier = 8*scale;
		int xOffset = x - modifier/2 - 3;
		int yOffset = y - modifier/2 -4;
		
		screen.render(xOffset, yOffset, xTile+yTile*32, color,0x00, scale);
		screen.render(xOffset+modifier, yOffset, (xTile+1)+yTile*32, color,0x00, scale);
		screen.render(xOffset, yOffset+modifier, xTile+(yTile+1)*32, color,0x00, scale);
		screen.render(xOffset +modifier, yOffset+modifier, (xTile+1)+(yTile+1)*32, color,0x00, scale);
	}
	//tuk moje da ne e 0 i 7/ 3 i 7
	public boolean hasCollided(int xa, int ya) {
		int xMin = 0;
		int xMax= 3;
		int yMin = 4;
		int yMax= 6;
		for (int x = xMin; x < xMax; x++) {
			if (isSolidTile(xa, ya, x, yMin)) {
				return true;
			}
		}
		for (int x = xMin; x < xMax; x++) {
			if (isSolidTile(xa, ya, x, yMax)) {
				return true;
			}
		}
		for (int y = yMin; y < yMax; y++) {
			if (isSolidTile(xa, ya, xMin, y)) {
				return true;
			}
		}
		for (int y = yMin; y < yMax; y++) {
			if (isSolidTile(xa, ya, xMax, y)) {
				return true;
			}
		}
		return false;
	}

}
