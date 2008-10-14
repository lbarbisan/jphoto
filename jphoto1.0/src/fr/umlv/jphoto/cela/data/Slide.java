/*
 * Created on 6 déc. 2004
 */
package fr.umlv.jphoto.cela.data;

import java.awt.Dimension;
import java.awt.Image;
import java.beans.PropertyChangeListener;

import fr.umlv.jphoto.Transition;
import fr.umlv.jphoto.TransitionConfig;

/**
 * @author Administrateur
 */
public interface Slide extends TransitionConfig{
	/**
	 * 
	 * @param previous Set the previous slide
	 */
	public void setPrevious(Slide previous);

	/**
	 * @return Returns the previous slide.
	 */
	public Slide getPrevious();

	/**
	 * @param next The next slide to set.
	 */
	public void setNext(Slide next);

	/**
	 * @return Returns the next slide.
	 */
	public Slide getNext();

	/**
	 * 
	 * @param transition set the Transition 
	 */
	public void setTransition(Transition transition);

	/**
	 * @return Returns the obj.
	 */
	public Transition getTransition();

	/**
	 * get the image source
	 */
	public Image getSourceImage();

	/**
	 * Set the image source, the image source is the destination
	 * image of previous slide
	 */
	public void setSourceImage(Image sourceImage);

	/**
	 * @param sourceImage The sourceImage to set.
	 */
	public void setDestinationImage(Image destinationImage);

	/**
	 * @return Returns the sourceImage.
	 */
	public Image getDestinationImage();

	/**
	 * @param duration The duration to set.
	 */
	public void setDuration(long duration);

	/**
	 * @return Returns the duration.
	 */
	public long getDuration();

	/**
	 * @param animationDimension The animationDimension to set.
	 */
	public void setAnimationDimension(Dimension animationDimension);

	/**
	 * @return Returns the animationDimension.
	 */
	public Dimension getAnimationDimension();

	/** 
	 * Add a property listener
	 * @param listener
	 */
	public void addPropertyChangeListener(
			PropertyChangeListener listener);

	/**
	 * Remove a property listener
	 * @param listener
	 */
	public void removePropertyChangeListener(
			PropertyChangeListener listener);

	/**
	 * @return Returns the thumbnail.
	 */
	public Image getThumbnail();

	/**
	 * @param thumbnail The thumbnail to set.
	 */
	public void setThumbnail(Image thumbnail);
}