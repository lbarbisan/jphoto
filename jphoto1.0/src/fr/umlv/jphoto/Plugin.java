package fr.umlv.jphoto;



/** This class represents a jPhoto plugin.
 *  This class acts as a factory of transitions.
 * 
 * @author Remi Forax
 * @see Transition
 */
public interface Plugin {
	
	/**
     * Create a new instance of a transition
     */
    public Transition createTransition();
}
