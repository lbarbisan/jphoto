/*
 * Created on 10 déc. 2004
 */
package fr.umlv.jphoto.cela.plugin;

import fr.umlv.jphoto.*;
/**
 * @author Administrateur
 */
@PluginMetaData( name="BorderEnterRight")
public class TBorderEnterRightPlugin implements Plugin {

	/* (non-Javadoc)
	 * @see fr.umlv.jphoto.Plugin#createTransition()
	 */
	public Transition createTransition() {
		return new TBorderEnterRight();
	}
	
	public static void main(String[] args) {
		
	}
}
