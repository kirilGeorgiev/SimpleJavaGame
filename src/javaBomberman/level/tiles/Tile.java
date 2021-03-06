package javaBomberman.level.tiles;

import javaBomberman.gfx.Colours;
import javaBomberman.gfx.Screen;
import javaBomberman.level.Level;

public abstract class Tile{

	public static final Tile[] tiles = new Tile[256];         
	                                        ///// 1 2 3             ////cvetovete na blok4eto/// 1 poziciq v masiva tiles 2 (x) poziciq v grida 3 (y)poziciq mai .. 
	public static final Tile VOID = new BasicSolidTile(0,0,0,Colours.get(000, -1, -1, -1));
	public static final Tile STONE = new BasicSolidTile(1,1,0,Colours.get(111, 222, 444, -1));
	public static final Tile GRASS = new BasicTile(2,2,0,Colours.get(-1, 131, 141, -1));
	public static final Tile BRICK = new BasicSolidTile(3,3,0,Colours.get(111, 333, 111, 111));

	protected byte id;
	protected boolean solid;
	protected boolean emitter;

	public Tile(int id, boolean isSolid, boolean isEmitter) {
		this.id = (byte)id;
		if(tiles[id]!=null) throw new RuntimeException("Duplicate tile id on"+id);
		tiles[id]=this;
		this.solid=isSolid;
		this.emitter=isEmitter;
	}

	public byte getId() {
		return id;
	}
	
	public boolean isSolid() {
		return solid;
	}
	
	public boolean isEmitter() {
		return emitter;
	}
	
	public abstract void render(Screen screen, Level level, int x, int y);

}
