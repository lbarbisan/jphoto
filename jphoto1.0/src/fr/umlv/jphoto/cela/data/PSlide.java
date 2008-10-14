package fr.umlv.jphoto.cela.data;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import fr.umlv.jphoto.Transition;
import fr.umlv.jphoto.TransitionConfig;
import fr.umlv.jphoto.cela.plugin.TPicture;


/**
 * Slide is used to represent a slide for the {@link fr.umlv.jphoto.cela.model.SlideShowAdapterTableColumnModel}
 * This object implement {@link fr.umlv.jphoto.TransitionConfig}, the slide is gave to the constructor of Transtion.
 * 
 * @author Laurent Barbisan
 *
 */
public class PSlide implements Slide 
{
	
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Next Slide, used by {@link SlideShow} to manage slides (move, remove)
	 */
	private Slide previous;
	
	/**
	 * Previous Slide, used by {@link SlideShow} to manage slides (move, remove)
	 */
	private Slide next;
	
	/**
	 * Transition, transition encapsuled by the slide
	 */
	private Transition transition;

	/**
	 * Destination Image, this is the end image for the transition.
	 * Used to implements {@link TransitionConfig}
	 */
	private Image image;
	
	/**
	 * thumbnail for the timeline, it will be too long to create it on-fly
	 */
	private Image Thumbnail;
	
	/** 
	 * Duration of slide, used to implements {@link TransitionConfig}
	 */
	private long duration = 0;
	
	/**
	 * Dimension that must repect de transition object
	 */
	private Dimension animationDimension;
	
	/**
	 * Slide implements property Change for:
	 * duration, next, previous
	 */
	private PropertyChangeSupport property;

	/**
	 * Store the picture passed by Drag&Drop
	 */
	private Picture picture;
	
	/**
	 * 
	 *Create a default Slide
	 */
	public PSlide()
	{
		property = new PropertyChangeSupport(this);
		animationDimension = new Dimension(1024,768);
		setDuration(5000);
		image = getDefaultImage();	
		transition  = new TPicture();
	}

	
	/**
	 * Create a slide.
	 * @param length Duration of the slide
	 * @param source source image for the slide
	 * @param transition transition
	 */
	public PSlide(long length, Image image)
	{
		this();
		setDuration(length);
		if(image!=null)
			this.image = image;
		else
			this.image = getDefaultImage();
		this.Thumbnail = image.getScaledInstance(60,60, 0);
	}
	
	/**
	 * 
	 * @param previous Set the previous slide
	 */
	public void setPrevious(Slide previous) {
		property.firePropertyChange("previous", this.previous, previous);
		this.previous = previous;
	}
	
	/**
	 * @return Returns the previous slide.
	 */
	public Slide getPrevious() {
		return previous;
	}
	
	/**
	 * @param next The next slide to set.
	 */
	public void setNext(Slide next) {
		property.firePropertyChange("next", this.next, next);
		this.next = next;
	}
	
	/**
	 * @return Returns the next slide.
	 */
	public Slide getNext() {
		return next;
	}
	

	
	/**
	 * 
	 * @param transition set the Transition 
	 */
	public void setTransition(Transition transition) {
		this.transition = transition;
	}
	
	/**
	 * @return Returns the obj.
	 */
	public Transition getTransition() {
		return this.transition;
	}
	
/**
 * get the image source
 */
	public Image getSourceImage() {
		return this.image; 
	}
	
	
	/**
	 * Set the image source, the image source is the destination
	 * image of previous slide
	 */
	public void setSourceImage(Image image) {
		this.image = image;
	}
	
	/**
	 * @param sourceImage The sourceImage to set.
	 */
	public void setDestinationImage(Image image) {	
		this.image = image;
	}
	/**
	 * @return Returns the sourceImage.
	 */
	public Image getDestinationImage() {
		return image;
	}
	
	
	/**
	 * @param duration The duration to set.
	 */
	public void setDuration(long duration) {
		if(duration!=this.duration)
		{
			property.firePropertyChange("duration", new Long(this.duration), new Long(duration));
			this.duration = duration;
		}
	}
	/**
	 * @return Returns the duration.
	 */
	public long getDuration() {
		return duration;
	}
	/**
	 * @param animationDimension The animationDimension to set.
	 */
	public void setAnimationDimension(Dimension animationDimension) {
		if(animationDimension!=null)
		this.animationDimension = animationDimension;
	}
	/**
	 * @return Returns the animationDimension.
	 */
	public Dimension getAnimationDimension() {
		return animationDimension;
	}
	
	/**
	 * Create a default Image
	 * @return return the default
	 */
	protected Image getDefaultImage()
	{
		return Toolkit.getDefaultToolkit().createImage(
				new byte[(int)(getAnimationDimension().getHeight() *
						getAnimationDimension().getWidth())], 
						(int)getAnimationDimension().getHeight(), 
						(int)getAnimationDimension().getWidth());
		
	}

	/** 
	 * Add a property listener
	 * @param listener
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener)
	{
		property.addPropertyChangeListener(listener);
	}
	
	/**
	 * Remove a property listener
	 * @param listener
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener)
	{
		property.removePropertyChangeListener(listener);
	}

    /**
     * @return Returns the thumbnail.
     */
    public Image getThumbnail() {
        return Thumbnail;
    }
    /**
     * @param thumbnail The thumbnail to set.
     */
    public void setThumbnail(Image thumbnail) {
        Thumbnail = thumbnail;
    }

}
