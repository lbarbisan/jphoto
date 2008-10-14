/*
 * Créé le 14 nov. 2004
 *
 */
package fr.umlv.jphoto.cela.data;

import java.util.Iterator;

/**
 *
 *
 * @author cfodouop
 *
 */
public class RootTransition extends AbstractRoot<TransitionObject> {

	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_NAME = "Transitions";
    private static final String DEFAULT_DESCRIPTION = "Liste des transitions";
    
    /**
     * 
     */
    public RootTransition() {
        this(DEFAULT_NAME,DEFAULT_DESCRIPTION);
        viewable = true;
    }

    /**
     * @param name
     * @param description
     */
    public RootTransition(String name, String description) {
        super(name, description);
    }

    /**
     * @see fr.umlv.jphoto.cela.data.AbstractRoot#refresh()
     */
    public void refresh() {
        Iterator i = iterator();
        
        while (i.hasNext()) {
            TransitionObject element = (TransitionObject) i.next();
            if(!element.getJar().exists()) { //il n'existe plus
                remove(element);
            }
        }
    }
}
