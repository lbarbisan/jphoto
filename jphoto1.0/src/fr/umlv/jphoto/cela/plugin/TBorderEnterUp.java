/*
 * Created on 2 déc. 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package fr.umlv.jphoto.cela.plugin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import fr.umlv.jphoto.Transition;
import fr.umlv.jphoto.TransitionConfig;

/**
 * @author Administrateur
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TBorderEnterUp implements ImageObserver,Transition{

	private Image src;
	private Image dst;
	private long Duration;
	private Dimension dimension;
	private int inset1x;
	private int inset2x;
	private int inset1y;
	private int inset2y;
	private BufferedImage buffer; 
	private Graphics2D gBuffer = null;
	
	
	/* (non-Javadoc)
	 * @see transition.Transition#init(transition.TransitionConfig)
	 */
	public void init(TransitionConfig config) {
		this.src = config.getSourceImage();
		this.dst = config.getDestinationImage();
		this.Duration = config.getDuration(); 
		this.dimension = config.getAnimationDimension();
		inset1y = (dimension.height - src.getHeight(null))/2;
		inset1x = (dimension.width - src.getWidth(null))/2;
		inset2y = (dimension.height - dst.getHeight(null))/2;
		inset2x = (dimension.width - dst.getWidth(null))/2;
		
		buffer = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_RGB); 
		gBuffer  = (Graphics2D)buffer.getGraphics();
		
	}

	/* (non-Javadoc)
	 * @see transition.Transition#draw(long, java.awt.Graphics2D)
	 */
	public void draw(long time, Graphics2D graphics) {

		int startSrcY;
		startSrcY = (int)((time*dimension.height)/(Duration+1));

		gBuffer  = (Graphics2D)buffer.getGraphics();

		gBuffer.setBackground(new Color(0,0,0));
		gBuffer.clearRect(0,0,dimension.width, dimension.height);
		gBuffer.drawImage(dst,inset2x,startSrcY - dimension.height + inset2y, null);
		gBuffer.drawImage(src,inset1x,startSrcY+inset1y,null);
		gBuffer.dispose();
		graphics.drawImage(buffer, 0,0, null);
	}

	/* (non-Javadoc)
	 * @see transition.Transition#dispose()
	 */
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.image.ImageObserver#imageUpdate(java.awt.Image, int, int, int, int, int)
	 */
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		return true;
	}
	
}
