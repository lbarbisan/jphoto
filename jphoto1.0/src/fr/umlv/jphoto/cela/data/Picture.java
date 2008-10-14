/*
 * Créé le 9 déc. 2004
 *
 */
package fr.umlv.jphoto.cela.data;

import java.awt.Image;

import fr.umlv.jphoto.Plugin;

/**
 *
 * This interface define how a picture is represents.<br>
 * it allow us to see {@link fr.umlv.jphoto.cela.data.TransitionObject TransitionObject} 
 * as pictures.<br>
 * Most of methods define here use standard methods of File when we use 
 * an {@link fr.umlv.jphoto.cela.data.AbstractRoot AbstractRoot}.  
 * @author cfodouop
 *
 */
public interface Picture {

    /** 
     * Returns the time that the file denoted by this abstract pathname was
     * last modified.
     *
     * @return  A <code>long</code> value representing the time the file was
     *          last modified, measured in milliseconds since the epoch
     *          (00:00:00 GMT, January 1, 1970), or <code>0L</code> if the
     *          file does not exist or if an I/O error occurs
     */
    public long getPictureLastModifiedDate();
    
    /**
     * Returns the name of the file denoted by picture.
     * This is just the last name in the pathname's name
     * sequence.  If the pathname's name sequence is empty.
     *
     * @return  The name of the file denoted by this picture.
     */
    public String getPictureName();
    
    /**
     * Returns the absolute pathname string of this abstract pathname.
     *
     * @return  The absolute pathname string denoting the same picture
     *
     */
    public String getPicturePath();
    
    /**
     * Returns the length of the file denoted by this picture.
     *
     * @return  The length, in bytes, of this picture, or <code>0L</code> if the file does not exist
     */
    public long getPictureSize();
    
     /**
     * @return an Image which represents this picture
     */
    public Image getPictureIcon();
    
    /**
     * @return plugin associate with this picture
     * @see TransitionObject
     */
    public Plugin getPlugin();
    
}
