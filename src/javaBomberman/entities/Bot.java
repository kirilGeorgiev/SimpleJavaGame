package javaBomberman.entities;

import java.util.Random;

import javaBomberman.gfx.Colours;
import javaBomberman.gfx.Screen;
import javaBomberman.level.Level;

public class Bot extends Mob {
	
	int botDirection = 0 ;
	int botMoves = 0 ; 
	private int color = Colours.get(-1, 111, 145, 543);
	
	public Bot(Level level, String name, int x, int y, int speed) {
		super(level, "Bot", x, y, 1);
		
	}

	
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

	private int botMovement(){
		
		Random rn = new Random();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(botMoves != 0 ){
			System.out.println("1 "+botMoves);
			botMoves--;
			return botDirection ;
		}
		else{
			botDirection = rn.nextInt(5);
			botMoves = rn.nextInt(10) ;
			System.out.println("2 "+botMoves+" "+botDirection);
			return botDirection ; 
		}
	}
	
	public void tick() {
		
		int xa=0;
		int ya=0;
		
		switch (botMovement()) {
		case 0:ya-- ;break;
		case 1:ya++ ;break;
		case 2:xa-- ;break;
		case 3:xa++ ;break;
		default:break;
		}
		
		
		if (xa!=0||ya!=0) {
			move(xa, ya);
			isMoving = true;
		} else {
			isMoving = false;
		}
	}

	
	public void render(Screen screen) {
		int xTile = 8;
		int yTile = 36; 
		
		int modifier = 8*scale;
		int xOffset = x - modifier/2 - 3;
		int yOffset = y - modifier/2 -4;
		
		screen.render(xOffset, yOffset, xTile+yTile*32, color,0x00, scale);
		screen.render(xOffset+modifier, yOffset, (xTile+1)+yTile*32, color,0x00, scale);
		screen.render(xOffset, yOffset+modifier, xTile+(yTile+1)*32, color,0x00, scale);
		screen.render(xOffset +modifier, yOffset+modifier, (xTile+1)+(yTile+1)*32, color,0x00, scale);
	}

}
