package people_animation;

import java.awt.Graphics2D;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sql.rowset.CachedRowSet;

public class ImageSplit{
	private File file;
	private FileInputStream fis;
	private BufferedImage image;
	private BufferedImage imgs[];
	
	public ImageSplit() throws IOException {
		// TODO Auto-generated constructor stub
		this.file = new File("people.png");
		this.fis = new FileInputStream(file);
		this.image = ImageIO.read(fis);
		this.doSplit();
	}
	
	private void doSplit() throws IOException{
		int row = 4; 
        int col = 4;  
        int chunk = row * col; 
        int chunkHeight = image.getHeight() / row; 
        int chunkWidth = image.getWidth() / col;
        int count = 0;
        this.imgs = new BufferedImage[chunk]; 
        for (int i = 0; i < row; i++) {  
			for (int j = 0; j < col; j++) {  
				imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());  
	                // draws the image chunk  
	                Graphics2D gr = imgs[count++].createGraphics();  
	                gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * j, chunkHeight * i, chunkWidth * j + chunkWidth, chunkHeight * i + chunkHeight, null);  
	                gr.dispose();  
	        }  
	    }

	}
	
	public BufferedImage[] getImages(){ 
		return imgs;
	}
}