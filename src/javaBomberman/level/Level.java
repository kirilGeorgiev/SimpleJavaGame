package javaBomberman.level;


import java.awt.image.TileObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.WeakHashMap;

import javaBomberman.entities.Entity;
import javaBomberman.gfx.Screen;
import javaBomberman.level.tiles.Tile;

public class Level {
	private byte[][] tiles;
	public int width;
	public int height;
	public List<Entity> entities = new ArrayList<Entity>();
	
	public Level(int width, int height) {
		tiles = new byte[width][height];
		this.width = width;
		this.height = height;
		this.generateLevel();
	}
	//Generira lvl
	private void generateLevel() {
		
		for(int y = 0 ; y < height ; y++){
			for(int x = 0 ; x < width ; x ++){
				if(x!=0 && y != 0 ){
					tiles[y][x] = Tile.GRASS.getId();
				}
				if(x%2 == 0 && y % 2 == 0 ){
					tiles[y][x] = Tile.STONE.getId();
				}
				if(y == 0 || y == height - 1){
					tiles[y][x] = Tile.STONE.getId();
				}
				if(x == 0 || x == width - 2 ){
					tiles[y][x] = Tile.STONE.getId();
				}
			}
		}
		Random rn = new Random();
		for(int r = 0 ; r < (height*width)/4 ; r++  ){
			int randomX = rn.nextInt(width); 
			int randomY = rn.nextInt(height);
			if (tiles[randomY][randomX] == Tile.GRASS.getId()){
				tiles[randomY][randomX] = Tile.BRICK.getId(); 
			}
			else{
				r--;
			}
		}
		//primeren lvl
		//for (int y = 0; y < height; y++) {
		//	for (int x = 0; x < width; x++) {
		//		if (x * y % 10 < 5) {
		//			tiles[x + y * width] = Tile.GRASS.getId();
		//		} else {
		//			tiles[x + y * width] = Tile.STONE.getId();
		//		}
		//	}
		//}
	}

	public void tick() {
		for (Entity e : entities) {
			e.tick();
		}
	}
	
	public void renderTiles(Screen screen, int xOffset, int yOffset) {
		if (xOffset < 0)xOffset = 0;
		if (xOffset > ((width << 3) - screen.width))xOffset = ((width << 3) - screen.width);
		if (yOffset < 0)yOffset = 0;
		if (yOffset > ((height << 3) - screen.height))yOffset = ((height << 3) - screen.height);
		

		screen.setOffset(xOffset, yOffset-6);

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				getTile(x, y).render(screen, this, x << 3, y << 3);
			}
		}
	}
	
	public void renderEntities(Screen screen) {
		for (Entity e : entities) {
			e.render(screen);
		}
	}

	
	public Tile getTile(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height)
		{
			return Tile.VOID;
		}
		else{
			return Tile.tiles[tiles[x][y]];	
		}
		
	}
	
	public void addEntity(Entity entity) {
		this.entities.add(entity);
	}

}
