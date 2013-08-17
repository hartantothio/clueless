/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CluelessCommands;

/**
 *
 * @author davis_gigogne
 */
public class PlayerQuit extends Command {
   public Long gameId, playerId;
   public boolean quit;
   
   public PlayerQuit(){
      super(PlayerQuit.class.getSimpleName());
   }
}
