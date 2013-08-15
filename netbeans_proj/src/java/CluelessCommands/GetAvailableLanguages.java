/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CluelessCommands;

import java.util.Set;

/**
 *
 * @author davis_gigogne
 */
public class GetAvailableLanguages extends Command {
   public Set<String> languages;
   
   public GetAvailableLanguages(){
      super(GetAvailableLanguages.class.getSimpleName());
   }
}
