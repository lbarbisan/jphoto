/*
 * Created on 19 sept. 2004
 *
 */
package fr.umlv.jphoto;

import java.lang.annotation.*;

/** MetaData associated to the plugin.
 * @author Remi Forax
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PluginMetaData {
  /** plugin transition name.
   */
  String name();
  
  /** icon filename.
   *  If the filename have a path, this path is relative
   *  to the plugin class.
   */
  String iconFileName() default "";
}
