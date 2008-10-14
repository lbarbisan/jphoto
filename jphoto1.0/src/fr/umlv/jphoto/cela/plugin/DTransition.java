/*
 * Created on 11 déc. 2004
 *
 */
package fr.umlv.jphoto.cela.plugin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import fr.umlv.jphoto.Transition;
import fr.umlv.jphoto.TransitionConfig;

/**
 *
 * @author Laurent and Cedric 
 *
 */
public class DTransition implements Transition {
	Image imgSrc;
	Image imgDest;
	long duration;
	Dimension dimension;
	
	private BufferedImage buffer; 
	private Graphics2D graph2d = null;
	
	/* (non-Javadoc)
	 * @see fr.umlv.jphoto.Transition#init(fr.umlv.jphoto.TransitionConfig)
	 */
	public void init(TransitionConfig config) {
		this.imgSrc=config.getSourceImage();
		this.imgDest=config.getDestinationImage();
		this.duration=config.getDuration();
		this.dimension=config.getAnimationDimension();

		buffer = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_RGB); 
		graph2d  = (Graphics2D)buffer.getGraphics();
	}

	/* (non-Javadoc)
	 * @see fr.umlv.jphoto.Transition#draw(long, java.awt.Graphics2D)
	 */
	public void draw(long time, Graphics2D graphics) {
		int xpos =(int) ((time*dimension.width));
		int ypos =(int) ((time*dimension.height));
		graph2d  = (Graphics2D)buffer.getGraphics();
		graph2d.setBackground(new Color(0,0,0));
		graph2d.clearRect(0,0,dimension.width, dimension.height);
		graph2d.drawImage(imgSrc,0,0,dimension.width,dimension.height, null);
		graph2d.drawImage(imgDest,0+xpos,0+ypos,(int)(dimension.width-(2*Math.sinh(xpos-1))),(int)(dimension.height-(2*Math.cosh(ypos))),null);
		graph2d.dispose();
		graphics.drawImage(buffer, 0,0, null);
	}

	/* (non-Javadoc)
	 * @see fr.umlv.jphoto.Transition#dispose()
	 */
	public void dispose() {

	}

}
