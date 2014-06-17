package javaBomberman;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javaBomberman.entities.Bot;
import javaBomberman.entities.Player;
import javaBomberman.gfx.Colours;
import javaBomberman.gfx.Fonts;
import javaBomberman.gfx.Screen;
import javaBomberman.gfx.SpriteSheet;


import javaBomberman.level.Level;





// g/d qsno obqsnen deap inside:
// http://stackoverflow.com/questions/16440713/how-does-jframe-work-deep-inside-how-does-it-draw-stuff
import javax.swing.JFrame;
import javax.swing.colorchooser.ColorSelectionModel;

// vij board.java za info za "extends"  
// vij komentara nad start/stop funkciite za "implements Runnable"
public class Game extends JFrame implements Runnable {

	public static final long serialVersionUID = 1L;
	public static final int WIDTH = 200;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final int SCALE = 4;
	public static final String NAME = "Bomberman";

	public static boolean running = false;
	public static int tickCount = 0;

	// some gfx stuf
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT,
			BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer())
			.getData();

	private int[] colours = new int[6 * 6 * 6];

	private Screen screen;
	public InPutHandler input;
	public Level level;
	public Player player ;
	public Bot bot1 ; 
	
	public Game() {

		setTitle(NAME);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(WIDTH * SCALE, HEIGHT * SCALE);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
	}

	public void init() {
		int index = 0;

		for (int r = 0; r < 6; r++) {
			for (int g = 0; g < 6; g++) {
				for (int b = 0; b < 6; b++) {
					int rr = r * 255 / 5;
					int gg = g * 255 / 5;
					int bb = b * 255 / 5;

					colours[index++] = rr << 16 | gg << 8 | bb;
				}
			}
		}

		screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("/SpriteSheet-man.png"));
		input = new InPutHandler(this);
		level = new Level(32, 32);
		player = new Player(level, 1, 1, input);
		level.addEntity(player);
		int botSpeed = 1;
		bot1 = new Bot(level,"Bot1", 20,20,1);
		level.addEntity(bot1);
	}

	// https://www.youtube.com/watch?v=VE7ezYCTPe4&feature=youtu.be kakwo i
	// zashto 12 min i 30 sec obqsnqva ako mojete namerete oshte info
	public synchronized void start() {
		running = true;
		new Thread(this).start();
	}

	public synchronized void stop() {
		running = false;
	}

	// the main loop
	public void run() {

		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D / 60D;

		int ticks = 0;
		int frames = 0;

		long lastTimer = System.currentTimeMillis();
		double delta = 0;

		init();

		while (running) {
			long now = System.nanoTime();
			delta = delta + (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shuldRender = true;

			while (delta >= 1) {
				ticks++;
				tick();
				delta = delta - 1;
				shuldRender = true;
			}

			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (shuldRender == true) {
				frames++;
				render();
			}

			if (System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer = lastTimer + 1000;
				System.out.println("frames p/s: " + frames + ", ticks p/s:"
						+ ticks);
				frames = 0;
				ticks = 0;
			}
		}
	}

	//private int x=0,y=0;
	
	// obnovqva logikata na igrata
	public void tick() {
		tickCount++;
		level.tick();
		
		//!!! tova ne trqbva da e tuk
		//for (int i = 0; i < pixels.length; i++) { // test
		//	pixels[i] = i + tickCount;
		//}
	}

	// obnovqva grafikata
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		//for (int y = 0; y < 32; y++) {
		//	for (int x = 0; x < 32; x++) {
		//		screen.render(x << 3, y << 3, 0,Colours.get(555, 500, 050, 005),true,false);
		//	}
		//}
		int xOffset =player.x-(screen.width /2 );
		int yOffset =player.y -(screen.width /2 );
		
		level.renderTiles(screen,xOffset,yOffset);
		
		for(int x = 0 ; x<level.width ; x++){
			int colour = Colours.get( -1, -1, -1, 000);
			if( x%10 == 0 && x!=0 ){
				colour = Colours.get(-1,-1,-1,500);
			}
			//za broene na plo4ki 
			//int msg = (x%10) ; 
			//Fonts.render((msg)+"", screen, 0+(x*8),10, colour) ;
		}
		
		level.renderEntities(screen);
		//String msg = "Java Bomberman" ;
		//Fonts.render(msg, screen, screen.xOffset+screen.width/2-((msg.length()*8)/2), screen.yOffset+screen.width/2-((msg.length()*8)/2), Colours.get(-1, -1, -1, 000));
				
		for (int y = 0; y < screen.height; y++) {
			for (int x = 0; x < screen.width; x++) {
				int colourCode = screen.pixels[x+y*screen.width];
				if(colourCode<255) pixels[x+y*WIDTH]=colours[colourCode];
			}
		}
		

		Graphics g = bs.getDrawGraphics();

		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		new Game().start();
	}

}
