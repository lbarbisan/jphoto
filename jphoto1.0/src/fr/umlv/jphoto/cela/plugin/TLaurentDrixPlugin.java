/*
 * Created on 11 déc. 2004
 *
 */
package fr.umlv.jphoto.cela.plugin;

import fr.umlv.jphoto.*;

/**
 *
 * @author Laurent and Cedric 
 *
 */
@PluginMetaData(name = "TLaurentDrix")
public class TLaurentDrixPlugin implements Plugin {
	
	
	/* (non-Javadoc)
	 * @see fr.umlv.jphoto.Plugin#createTransition()
	 */
	public Transition createTransition() {
		return new TLaurentDrix();
	}

	public static void main(String[] args) {
	}
}
