/*
 * Créé le 1 nov. 2004
 *
 */
package fr.umlv.jphoto.cela.util;

import java.io.File;
import java.io.FileFilter;

/**
 *
 * A filter for file's type accept by JPHOTO.
 * This class implements <code>FileFilter</code> and allow us to 
 * display only pciture's files as <code>gif, jpg etc...</code> or jar's files when 
 * the user use a fileChooser.
 * @author cfodouop
 *
 */
public final class JphotoFilter implements FileFilter {
   
    private static final String JPEG = "jpeg";
    private static final String JPG = "jpg";
    private static final String GIF = "gif";
    private static final String JAR = "jar";
    
    /**
     * A static reference on the unique reference of JphotoFilter.<br>
     * When we need to use a Filter.
     * @see #getJphotoFilter()  
     */
    private static JphotoFilter filter = null;
    private static final int GOOD_EXTENSION = 0;
    
    /**
     * a private constructor of JphotoFilter
     */
    private JphotoFilter() {
        super();
    }
    
    /**
     * Tests whether or not the specified abstract pathname should be
     * included in a pathname list.
     * 
     * @see java.io.FileFilter#accept(java.io.File)
     */
    public boolean accept(File pathname) {
        if (pathname.isDirectory()) {
            return true;
        }
        
      return endsWithGoodExtension(pathname);
    }
 
    /**
     * Get the extension of a file.
     * @param f the file to use.
     * @return a String wich is empty if the file don"t have
     * an extension<br>
     * e.g :<br>
     * for the file <i>foo</i> this method return "".<br>
     * for the file <i>foo.foo</i> this method return foo.<br>
     */
    public static String getExtension(File f) {
        String ext = " ";
        String s = f.getName();
        int i = s.lastIndexOf('.');
        
        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        
        return ext;
    }

    /**
     * Tests if a file ends with one extension.<br>
     * This method call the method {@link #getExtension(File) getExtension} to get the
     * extension of the file and then make a comparaison between it and 
     * the standard extension. 
     * @param f file to test
     * @return <code>true</code> if the file have a good extension otherwise <code>false</code>
     * @see #getExtension(File)
     */
    public static boolean endsWithGoodExtension(File f) {
        String ext = getExtension(f);
        
        return (ext.compareTo(JPEG) == GOOD_EXTENSION || ext.compareTo(GIF) == GOOD_EXTENSION 
                || ext.compareTo(JPG) == GOOD_EXTENSION || ext.compareTo(JAR) == GOOD_EXTENSION);
    }
    
    /**
     * get a JphotoFilter.
     * This metod assume that we always have one instance of JphotoFilter;
     * @return a reference to a JphotoFileter.
     */
    public static JphotoFilter getJphotoFilter() {
        if(filter == null) {
            filter = new JphotoFilter();
        }
        return filter;
    }   
}
