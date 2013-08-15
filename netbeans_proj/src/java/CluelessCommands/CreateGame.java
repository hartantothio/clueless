/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CluelessCommands;

/**
 *
 * @author davis_gigogne
 */
public class CreateGame extends Command {
   public String playStyle, name, password, playerLanguage;
   public Long id;
   public CreateGame(){
      super(CreateGame.class.getSimpleName());
   }
}
