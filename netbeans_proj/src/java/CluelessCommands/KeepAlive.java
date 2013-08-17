/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CluelessCommands;

/**
 *
 * @author davis_gigogne
 */
public class KeepAlive extends Command {
   public Long playerId, gameId;
   public KeepAlive(){
      super(KeepAlive.class.getSimpleName());
   }
}
