/*
 * Créé le 14 nov. 2004
 *
 */
package fr.umlv.jphoto.cela.data;

import java.util.Iterator;

/**
 *
 * The Class which manage the folders add by the user in a jphoto's project. 
 * @author cfodouop
 *
 */
public class RootDirectory extends AbstractRoot<Directory> {
    
	private static final long serialVersionUID = 1L;
	/**
	 * Default Name for RootDirectory
	 */
    private static final String DEFAULT_NAME = "Photos";
    /**
     * Default Description for RootDirectory
     */
    private static final String DEFAULT_DESCRIPTION = "Liste des répertoires";
    
    /**
     * get A default RootDirectory
     * @see #RootDirectory(String, String)
     */
    public RootDirectory() {
        this(DEFAULT_NAME,DEFAULT_DESCRIPTION);
    }

    /**
     * get A new instance of RootDirectory with a name and a description.
     * @param name the name of this RootDirectory.
     * @param description a description for this RootDirectory.
     */
    public RootDirectory(String name, String description) {
        super(name, description);
    }
    
    /**
     * @see fr.umlv.jphoto.cela.data.AbstractRoot#refresh()
     */
    public void refresh() {
        Iterator i = iterator();
        
        while (i.hasNext()) {
            Directory element = (Directory) i.next();
            if(!element.getDirectory().exists()) { //il n'existe plus
                remove(element);
            }
        }
    }
}
