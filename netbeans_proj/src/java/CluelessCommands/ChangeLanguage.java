/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CluelessCommands;

/**
 *
 * @author davis_gigogne
 */
public class ChangeLanguage extends Command {
   public Long gameId;
   public String language;
   
   public ChangeLanguage(){
      super(ChangeLanguage.class.getSimpleName());
   }
}
