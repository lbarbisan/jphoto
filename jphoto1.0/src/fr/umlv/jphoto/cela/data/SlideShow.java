/*
 * Created on 25 oct. 2004
 *
 */
package fr.umlv.jphoto.cela.data;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.table.TableColumn;


/**
 * Store and Manage a list of slides
 * @author Laurent barbisan
 */
public class SlideShow  {
	
	/**
	 * Array using for listener about slideshow changes
	 */
	private ArrayList<SlideShowListener> listeners;
	
	/**
	 * store the slideshow
	 */
	private ArrayList<Slide> slidelist; 
	

	/**
	 * Default constructor
	 */
	public SlideShow()
	{
		slidelist = new ArrayList<Slide>(); 
		listeners = new ArrayList<SlideShowListener>();
	}
	
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Private method to add a  slide, don't fire event
	 * @param index index where slide added
	 * @param slide slide whiwh is added
	 */
	private void p_add(int index, Slide slide) {
		slidelist.add(index, slide);
		
		slide.setPrevious(null);
		slide.setNext(null);
		
		if(index<(slidelist.size()-1))
		{
			slide.setNext((PSlide)slidelist.get(index+1));
			slidelist.get(index+1).setPrevious(slide);
		}
		
		if(index>0)
		{
			slide.setPrevious(slidelist.get(index-1));
			slidelist.get(index-1).setNext(slide);
		}
		TableColumn column = new TableColumn();
	}
	
	/**
	 * Add an element, then fire the event fireSlideAddedEvent
	 * @param slide slide to add
	 * @return true if insert success, false else
	 */
	public boolean add(Slide slide) {
		
		boolean result;

		if(slidelist.size()>0)
		{
			slidelist.get(slidelist.size()-1).setNext(slide);
			slide.setPrevious(slidelist.get(slidelist.size()-1));
		}
		if((result=slidelist.add(slide))==true)
			this.fireSlideAdded(this, slide, slidelist.size()-1);
		
		return result; 
	}
	
	
	/**
	 * Get an slide from slides show
	 * @param index
	 * @return return the slide
	 */
	public Slide get(int index)
	{
		return slidelist.get(index);
	}
	
	/**
	 * return the size of SlidesShow
	 * @return the number of size
	 */
	public int size()
	{
		return slidelist.size();
	}
	
	/**
	 * 
	 * @return an iterator
	 */
	public Iterator iterator()
	{
		return slidelist.iterator();
	}
	/**
	 * Add a Slide
	 * @param index index added
	 * @param slide slide added
	 */
	public void add(int index, Slide slide)
	{
		p_add(index, slide);
		this.fireSlideAdded(this, slide,index);
	}
	
	/**
	 * Move an element 
	 * @param indexSource source of index
	 * @param indexDestination destination index
	 */
	public void move(int indexSource, int indexDestination)
	{
		p_add(indexDestination, p_remove(indexSource));
		fireSlideAdded(this, this.get(indexDestination),  indexDestination);
		fireSlideRemoved(this, this.get(indexSource), indexSource );
		fireSlideMoved(this, get(indexDestination), indexSource, indexDestination);
	}
	
	/**
	 * Private remove methos, don't fire event
	 * @param index
	 * @return
	 */
	private Slide p_remove(int index)
	{
		if(index>0 && index<(slidelist.size()-1))
		{
			slidelist.get(index-1).setNext(slidelist.get(index+1));
			slidelist.get(index+1).setPrevious(slidelist.get(index-1));
		}
		else if(index==0 && slidelist.size()>1) 
		{
			slidelist.get(index+1).setPrevious(null);
		}
		if(index==(slidelist.size()-1) && slidelist.size()>1)
		{
			slidelist.get(index-1).setNext(null);
		}

		return slidelist.remove(index);
	}
	
	/**
	 * REmove a SLide
	 * @param index
	 * @return
	 */
	public Slide remove(int index) {
		return p_remove(index);
	}
	
	/**
	 * Remove a slide
	 * @param slide
	 * @return
	 */
	public boolean remove(PSlide slide) {
		int index = slidelist.indexOf(slide);
		if(slidelist.remove(index)==null)
				return false;
		else
		{
			fireSlideRemoved(this, slide, index);
			return true;
		}
	}
	/**
	 * Class use for listener
	 * @author Administrateur
	 */
	public interface SlideShowListener
	{
		/**
		 * 
		 * @param slideshow
		 * @param slide
		 * @param index
		 */
		public void SlideAdded(SlideShow slideshow, Slide slide, int index);
		/**
		 * 
		 * @param slideshow
		 * @param slide
		 * @param index
		 */
		public void SlideRemoved(SlideShow slideshow, Slide slide, int index);
		/**
		 * 
		 * @param slideshow
		 * @param slide
		 * @param oldIndex
		 * @param newIndex
		 */
		public void SlideMoved(SlideShow slideshow, Slide slide, int oldIndex, int newIndex);
	}

	/**
	 * Add a listener to the slide show
	 * @param listener listener to add
	 */
	public void addSlideShowListener(SlideShowListener listener)
	{
		listeners.add(listener);
	}
	
	/**
	 * remove a listener from the lside show
	 * @param listener listener to remove
	 */
	public void removeSlideShowListener(SlideShowListener listener)
	{
		listeners.remove(listener);
	}
	
	/**
	 * Fire an event to inform that a slide has been added
	 * @param slideshow slidesshow
	 * @param slide slide addes
	 * @param index index wher the slie has been added
	 */
	protected void fireSlideAdded(SlideShow slideshow, Slide slide, int index)
	{
		Iterator i = listeners.iterator();
		while(i.hasNext())
		{
			((SlideShowListener)i.next()).SlideAdded(slideshow, slide,index);
		}
		
	}
	
	/**
	 * Fire an event to inform that a slide has been removed
	 * @param slideshow slidesshow
	 * @param slide slide addes
	 * @param index index wher the slie has been added
	 */
	protected void fireSlideRemoved(SlideShow slideshow, Slide slide, int index)
	{
		Iterator i = listeners.iterator();
		while(i.hasNext())
		{
			((SlideShowListener)i.next()).SlideRemoved(slideshow, slide,index);
		}
		
	}
	
	/**
	 * Fire an event to inform that a slide has been moved
	 * @param slideshow slidesshow
	 * @param slide slide addes
	 * @param index index wher the slie has been added
	 */
	protected void fireSlideMoved(SlideShow slideshow, Slide slide, int oldIndex, int newIndex)
	{
		Iterator i = listeners.iterator();
		while(i.hasNext())
		{
			((SlideShowListener)i.next()).SlideMoved(slideshow, slide,oldIndex, newIndex);
		}
		
	}


	
}