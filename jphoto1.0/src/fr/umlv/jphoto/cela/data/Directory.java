/*
 * Créé le 14 nov. 2004
 *
 */
package fr.umlv.jphoto.cela.data;

import java.io.File;

/**
 * A representation for folder's filesystem.<br>
 * The class <code>Directory</code> containts a reference in the folder to
 * scan when the user wants to display pictures or uses for the SlideShow.
 * <br>
 * In a normal use, a <code>Directory</code> represents a folder
 * which exists on the filesystem'user.
 *  
 * @author cfodouop
 *
 */
public class Directory extends Node {
  
    private static final long serialVersionUID = 1L;
    /**
     * A default description for <code>Directory<code> class
     */
    private static final String DEFAULT_DIRECTORY_DESCRIPTION = "Album photo";
    
    /**
     * <code>directory</code> is a reference to the <code>File</code> that we can display later.
     */
    private File directory; 
    
    /**
     * Get a new Directory and set the File that it's represents.<br>
     * 
     * @param directory a file in the filesystem's user.
     */
    public Directory(File directory) {
        this(directory.getAbsolutePath(),DEFAULT_DIRECTORY_DESCRIPTION);
        this.directory = directory;
        viewable = true;
    }
    
    /**
     * Get a new Directory. <br>
     * @param name - The directory's name
	 * @param description - The directory's description
     */
    public Directory(String name, String description) {
        super(name, description);
    }

    /**
     * Get a reference to the <code>File</code> 
     * @return the directory's file object.
     */
    public File getDirectory() {
        return directory;
    }
    
    /**
     * set the file object represents by a Directory.
     * 
     * @param directory directory à définir.
     */
    public void setDirectory(File directory) {
        this.directory = directory;
    }
    
    /**
     * get a string representation of this directory.<br>
     * this method call the method <code>getName()</code> of <code>File</code>.
     * @return the name of this directory
     */
    public String toString() {
        return directory.getName();
    }
}
