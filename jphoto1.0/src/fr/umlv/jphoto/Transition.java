/*
 * Created on 17 sept. 2004
 *
 */
package fr.umlv.jphoto;

import java.awt.Graphics2D;

/** Represents a transition in the animation.
 *  The life cycle of a transition followed these steps :
 *  <ul>
 *   <li>step 1: initialization, call init, process ressources
 *               needed during step 2
 *   <li>step 2: draw the transition in a loop,
 *               draw is called at fixed rate during the duration
 *                of the transition.
 *              <br>
 *               each time, the elapsed time between when the transition
 *               beginning and the current time is provided.
 *   <li>step 3: free ressources calculated during step 1
 *   <li>step 4: could replay step 1 to 3.
 * <ul>
 *               
 * @author Remi Forax
 *
 */
public interface Transition {
  
  /** init the transition.
   * @param config contains all properties needed by the transition.
   * @see #dispose()
   */
  void init(TransitionConfig config);
  
  /**
   * draw a frame of the transition.
   * This method render only one frame of the transition.
   * @param time a time is millisecond between 0 and the duration of the transition
   * @param graphics graphics which to draw on
   */
  void draw(long time, Graphics2D graphics);
  
  /** free ressources allocated in {@link #init(TransitionConfig) init}.
   *  @see #init(TransitionConfig)
   */
  void dispose();
}
