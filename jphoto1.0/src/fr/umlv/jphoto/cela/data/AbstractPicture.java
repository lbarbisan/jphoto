/*
 * Created on 10 déc. 2004
 *
 */
package fr.umlv.jphoto.cela.data;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import fr.umlv.jphoto.Plugin;


/**
 * A class which implements interface Picture.
 * This class is use to represents the picture's files found on the filesystem.
 * 
 * @author cfodouop
 */
public class AbstractPicture implements Picture {

	/**
	 * The file picture.
	 */
	private File pictureFile;
	private Image icon;
	
	/**
	 * get a new instance of AbstractPicture.
	 * @param pictureFile file to associate with this instance.
	 */
	public AbstractPicture(File pictureFile) {
		this.pictureFile = pictureFile;
		Image image = Toolkit.getDefaultToolkit().getImage(pictureFile.getAbsolutePath());
		icon =  image.getScaledInstance(100,100,0);
	}
	
	/** (non-Javadoc)
	 * @see fr.umlv.jphoto.cela.data.Picture#getPictureLastModifiedDate()
	 */
	public long getPictureLastModifiedDate() {
		return pictureFile.lastModified();
	}

	/** (non-Javadoc)
	 * @see fr.umlv.jphoto.cela.data.Picture#getPictureName()
	 */
	public String getPictureName() {
		return pictureFile.getName();
	}

	/** (non-Javadoc)
	 * @see fr.umlv.jphoto.cela.data.Picture#getPicturePath()
	 */
	public String getPicturePath() {
		return pictureFile.getAbsolutePath();
	}

	/** (non-Javadoc)
	 * @see fr.umlv.jphoto.cela.data.Picture#getPictureSize()
	 */
	public long getPictureSize() {
		return pictureFile.length();
	}

	/** (non-Javadoc)
	 * @see fr.umlv.jphoto.cela.data.Picture#getPictureIcon()
	 */
	public Image getPictureIcon() {
		return icon;
	}

	/** (non-Javadoc)
	 * @see fr.umlv.jphoto.cela.data.Picture#getPlugin()
	 */
	public Plugin getPlugin() {
		return null;
	}
	
}
