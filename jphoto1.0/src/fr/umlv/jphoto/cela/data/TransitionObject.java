/*
 * Créé le 14 nov. 2004
 *
 */
package fr.umlv.jphoto.cela.data;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import javax.swing.ImageIcon;

import java.lang.annotation.*;
import fr.umlv.jphoto.PluginMetaData; 

import fr.umlv.jphoto.Plugin;
import fr.umlv.jphoto.cela.util.JphotoManager;

/**
 *
 *
 * @author cfodouop
 *
 */
public class TransitionObject extends Node implements Picture {
    
	private static final long serialVersionUID = 1L;
	
	private File jar; /* reference vers le fichier jar*/
    private Plugin plugin; /* le plugin associe a la jar */
    private PluginMetaData data; /* PluginMetadata associe au plugin */
    private Class<?> pluginClass;
    
	/**
	 * get a new Transition Object.<br>
	 * @param jar the file associate with this transition
	 * @param plugin plugin associate with Transition Object
	 * @param pluginClass the class's plugin
	 * @param data metadata of plugin <code>plugin</code>
	 * @see Plugin
	 * @see PluginMetaData
	 */
	public TransitionObject(File jar, Plugin plugin, Class<?> pluginClass, PluginMetaData data) {
		super(null,null);
		this.jar = jar;
		this.plugin = plugin;
		this.pluginClass = pluginClass;
		this.data = data;
		setName(data != null?data.name():"Transition Object n°" + Integer.valueOf(i).toString());
		setDescription("Transition");
		viewable = true;
		i++;
	}

    /**
     * get a reference to the jar's file.
     * @return return the file's jar reference.
     */
    public File getJar() {
        return jar;
    }

    /**
     * @return plugin reference.
     */
    public Plugin getPlugin() {
        return plugin;
    }

    /**
     * get the icon's name associate with this transition.
     * @return a icon's file name
     */
    public String iconFileName() {
        if(data == null)
            return DEFAULT_ICON;
        return data.iconFileName();
    }
    
    /**
     * get the name of this transition.
     * @return the name of this transition if it's set by the develepper or 
     * a default name for transition.
     * 
     */
    public String name() {
        if(data == null)
            return getName();
        return data.name();
    }
    
    /**
     * @see Node#toString()
     */
    public String toString() {
    	if(data == null)
            return getName();
        return data.name();
    }
    
    public static TransitionObject getTransitionFromJar(File file){
        if(file.exists() == false) return null;
        JarFile jar = null;
        Plugin p = null;
        PluginMetaData metadata = null;
        Class<?> pluginclass = null;
        Manifest manifest = null;
        URL url = null;
        
        try {
            jar = new JarFile(file);
        } catch (IOException io) {
            io.printStackTrace();
            return null;
        }
        if(jar == null)
            return null;
        
        // Get the manifest
        try {
            manifest = jar.getManifest();
            if(manifest == null)
                return null;
        } catch (IOException io) {
            return null;
        }

        //Get the attributes
        Attributes at = manifest.getMainAttributes();
        if(at == null)
            return null;
        String pl = at.getValue(Attributes.Name.MAIN_CLASS);
        
        try {
            url = new URL("jar:file:" + file.getAbsolutePath()+"!/");
        } catch (MalformedURLException mal) {
            return null;
        }
  
        URLClassLoader classloader = new URLClassLoader(new URL[] {url});
        
        try {
            pluginclass = classloader.loadClass(pl);
            if(pluginclass.isAnnotationPresent(PluginMetaData.class)) {
            	 metadata = pluginclass.getAnnotation(PluginMetaData.class);
            }
            p = (Plugin) pluginclass.newInstance();
        } catch( ClassNotFoundException c) {
            c.printStackTrace();
            return null;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
        
        return new TransitionObject(file, p, pluginclass, metadata);
    }
    
	/**
	 * @see fr.umlv.jphoto.cela.data.Picture#getPictureLastModifiedDate()
	 */
	public long getPictureLastModifiedDate() {
		return jar.lastModified();
	}
	
	/**
	 * @see fr.umlv.jphoto.cela.data.Picture#getPictureName()
	 */
	public String getPictureName() {
		if(data == null)
			return DEFAULT_ICON;
		return data.iconFileName();
	}
	
	/**
	 * @see fr.umlv.jphoto.cela.data.Picture#getPicturePath()
	 */
	public String getPicturePath() {
		if(data == null)
			return DEFAULT_ICON;
		return data.iconFileName(); //TODO le path doit etre relatif au jar
	}
	
	/**
	 * @see fr.umlv.jphoto.cela.data.Picture#getPictureSize()
	 */
	public long getPictureSize() {
		return jar.length();
	}
	
	/**
	 * @see fr.umlv.jphoto.cela.data.Picture#getPictureIcon()
	 */
	public Image getPictureIcon() {
	    URL url = null;
	    
	    if(data == null || data.iconFileName() == "")
	        return default_icon.getImage();
	    
	    try {
            url = new URL("jar:file:" + jar.getAbsolutePath()+"!/"+data.iconFileName());
        } catch (MalformedURLException mal) {
            return null;
        }
        
	    Image icon = new ImageIcon(url).getImage();
		return icon;
	}
	
	private static int i = 0;
	private static final String DEFAULT_ICON = "/resources/icons/default_transition.jpg";
	private static final ImageIcon default_icon = (ImageIcon) JphotoManager.getJphotoManager().getIcon(DEFAULT_ICON);
}
