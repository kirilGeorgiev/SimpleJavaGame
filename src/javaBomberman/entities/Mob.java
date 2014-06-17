package javaBomberman.entities;

import javaBomberman.level.Level;
import javaBomberman.level.tiles.Tile;

public abstract class Mob extends Entity{

	protected String name;
	protected int speed;
	protected boolean isMoving;
	protected int movingDir = 1;
	protected int numSteps=0;
	protected int scale = 1;
	
	public Mob(Level level,String name, int x,int y,int speed) {
		super(level);
		this.speed = speed;
		this.x = x;
		this.y = y;
		this.name = name;
	}
	
	public void move(int xa, int ya) {
		if (xa!=0&&ya!=0) {
			move(xa, 0);
			move(0, ya);
			numSteps--;
			return;
		}
		numSteps++;
		if (!hasCollided(xa,ya)) {
			if(ya>0)
				movingDir=0; //up
			if(ya<0)
				movingDir=1; //down
			if(xa>0)
				movingDir=2; // right
			if(xa<0)
				movingDir=3; //left
			x+=xa*speed;
			y+=ya*speed;
		}
	}
	
	public abstract boolean hasCollided(int xa,int ya);
	
	protected boolean isSolidTile(int xa,int ya,int x,int y) {
		if(level==null) {
			return false;
		}
		Tile lastTile = level.getTile((this.x+x)>>3,(this.y+y)>>3);
		Tile newTile = level.getTile((this.x+x+xa)>>3,(this.y+y+ya)>>3);
		if (!lastTile.equals(newTile)&&newTile.isSolid()) {
			return true;
		}
		return false;
	}
	
	public String getName() {
		return name;
		
	}
	
	
	
}
