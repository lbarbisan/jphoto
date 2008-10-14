/*
 * Created on 10 déc. 2004
 */
package fr.umlv.jphoto.cela.plugin;

import fr.umlv.jphoto.*;

/**
 * @author Administrateur
 */
@PluginMetaData( name="BorderEnterLeft")
public class TBorderEnterLeftPlugin implements Plugin {

	public static void main(String[] args) {
	}

	/* (non-Javadoc)
	 * @see fr.umlv.jphoto.Plugin#createTransition()
	 */
	public Transition createTransition() {
		return new TBorderEnterLeft();
	}
}
