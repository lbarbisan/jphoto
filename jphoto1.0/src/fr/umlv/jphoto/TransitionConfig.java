/*
 * Created on 17 sept. 2004
 *
 */
package fr.umlv.jphoto;

import java.awt.Dimension;
import java.awt.Image;

/** Object that contains all properties used to initialize
 *  a transition.
 * 
 * @author remi
 * @see TransitionObject#init(TransitionConfig)
 */
public interface TransitionConfig {
  /** returns the source image.
   *  This is the first frame of the transition.
   * @return the source image that can't be null.
   */
  public Image getSourceImage();
  
  /** returns the destination image. 
   *  This is the last frame of the transition.
   * @return the destination image that can't be null.
   */
  public Image getDestinationImage();
  
  /** returns the total duration of the transition.
   * @return the duration in millisecond.
   */
  public long getDuration();
  
  /** returns the screen size of the animation.
   * @return a dimension containing the size of the animation.
   */
  public Dimension getAnimationDimension();
}
