/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CluelessCommands;

/**
 *
 * @author davis_gigogne
 */
public class StartGame extends Command {
   public Long gameId;
   public boolean started;
   
   public StartGame(){
      super(StartGame.class.getSimpleName());
   }
}
