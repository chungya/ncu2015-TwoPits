package people_animation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.security.KeyStore.PrivateKeyEntry;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.omg.CORBA.PUBLIC_MEMBER;

public class PeopleMain implements KeyListener{
	private ImageComponent character;
	private int character_dir = 0;
	
	public static void main(String args[]) {
		new PeopleMain();
    }
	
	public PeopleMain() {
		// TODO Auto-generated constructor stub
		JFrame mainFrame = new JFrame();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize((int)width/2, (int)height/2);
		mainFrame.setLocation((int)(width-width/2)/2, (int)(height-height/2)/2);
		mainFrame.setVisible(true);
		mainFrame.addKeyListener(this);	
		
		BufferedImage[] imgs = null;
		try{	
			// Dividing the image into 16 pieces, and getting them.
			ImageSplit imageSplit = new ImageSplit();   
			imgs = imageSplit.getImages();
		}catch(Exception e){
			e.printStackTrace();
		}
		character = new ImageComponent(imgs);
		character.stop();   // First the character doesn't move.
		mainFrame.setLayout(new BorderLayout());
		mainFrame.add(character,BorderLayout.CENTER);
		mainFrame.pack();
		Thread animate = new Thread(animateThread); 
		animate.start();	
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("keyPressed");
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				character.setDirection(12);  // The character's Direction is up. 
				break;
			case KeyEvent.VK_DOWN:
				character.setDirection(0);   // The character's  Direction is down.
				break;
			case KeyEvent.VK_LEFT:
				character.setDirection(4);   // The character's  Direction is left.
				break;
			case KeyEvent.VK_RIGHT:
				character.setDirection(8);   // The character's  Direction is right.
				break;
			case KeyEvent.VK_SPACE:
				character.stop();   // The character doesn't move.
				break;	
			default:
				break;
		}	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	
	class ImageComponent extends JComponent{
		private BufferedImage[] images;
		private int frame;
		private int frameInit;
		private boolean domove = true;
		private Dimension screenSize;
		private double scr_width;
		private double scr_height;
		private int people_x;
		private int people_y;
		
	    public ImageComponent(BufferedImage[] im){
	    	images = im;
	    	screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    	scr_width = screenSize.getWidth()/2;
	    	scr_height = screenSize.getHeight()/2;
	    	people_x = (int)(scr_width-images[0].getWidth())/2;
			people_y  = (int)(scr_height-images[0].getHeight())/2;
	    }
	    
	    public void update(){
	    	if(domove){
	    		repaint();
		    	incFrame();
	    	}
	    }
	    
	    private void incFrame(){
	    	frame++;
	    	if(frame == images.length || frame % 4  == 0)
	    		frame = frameInit;
	    }
	    
	    public void setDirection(int dir){
	    	domove = true;
	    	// If pressing the key continually before releaseing it, it would not initalize the frame.
	    	if(frameInit != dir){
	    		frame = dir;
	    		frameInit = dir;
	    	}
	    }
	    
	    public void stop(){
	    	domove = false;
	    }
	    
	    @Override
	    public void paintComponent (Graphics g){
	    	super.paintComponent(g);
	        g.drawImage(images[frame], people_x, people_y, this);
	    }
	    
	    @Override
	    public Dimension getPreferredSize() {
	    	return new Dimension((int)(scr_width), (int)(scr_height));
	    }
	    
	    @Override	 
	    public Dimension getMaximumSize() {
	    	return getPreferredSize();
	    }
	    @Override	 
	    public Dimension getMinimumSize() {
	    	return getPreferredSize();
	    }
	}
	
	Runnable animateThread = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			long lastFrameTime = System.currentTimeMillis();
			int delay = 150;
			while (true) {
				character.update();
				try {
					//Setting the delay.
					lastFrameTime += delay;
				    Thread.sleep(Math.max(0, lastFrameTime - System.currentTimeMillis()));   
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	};
	
}
