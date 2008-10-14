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
 * @author laurent Barbisan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TPicture implements Transition{

	private Image image;
	private long duration;
	private int insetx;
	private int insety;

	
	private long Duration;
	Dimension size;
	/* (non-Javadoc)
	 * @see transition.Transition#init(transition.TransitionConfig)
	 */
	public void init(TransitionConfig config) {
		this.size = config.getAnimationDimension();
		if(config.getDestinationImage()==null)
			if(config.getSourceImage()==null)
			{
			}
			else
			{
				image = config.getSourceImage();
			}
		else
		{
			image = config.getDestinationImage();
		}
		this.duration = config.getDuration();
		insety = (config.getAnimationDimension().height - image.getHeight(null))/2;
		insetx = (config.getAnimationDimension().width - image.getWidth(null))/2;
	}

	/* (non-Javadoc)
	 * @see transition.Transition#draw(long, java.awt.Graphics2D)
	 */
	public void draw(long time, Graphics2D graphics) {
		graphics.setColor(new Color(0,0,0));
		graphics.fillRect(0,0 , size.width, size.height);
		
		graphics.drawImage(image,insetx ,insety, null);
	}

	/* (non-Javadoc)
	 * @see transition.Transition#dispose()
	 */
	public void dispose() {
	}

}
