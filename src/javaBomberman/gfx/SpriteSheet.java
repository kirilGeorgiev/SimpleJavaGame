package javaBomberman.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;

import javax.imageio.ImageIO;

public class SpriteSheet {
	
	public String imgPath ; 
	public int width ; 
	public int height; 
	public int[] pixels;
	
	public SpriteSheet(String imgPath){
		BufferedImage image = null; 
		
		try {
			image = ImageIO.read(SpriteSheet.class.getResourceAsStream(imgPath));
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		if( image == null ){
			return;
		}
		
		this.imgPath = imgPath ; 
		this.width = image.getWidth(); 
		this.height = image.getHeight() ; 
		
		pixels = image.getRGB(0, 0, width, height, null, 0, width);
		
		for(int i = 0 ; i < pixels.length ; i++){
			pixels[i] = (pixels[i] & 0xff) / 64 ; 
		}
	}
}
