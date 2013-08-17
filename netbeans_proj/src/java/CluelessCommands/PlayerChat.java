/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CluelessCommands;

/**
 *
 * @author davis_gigogne
 */
public class PlayerChat extends Command {
   public Long gameId;
   public Integer playerId;
   public String msg;
   
   public PlayerChat(){
      super(PlayerChat.class.getSimpleName());
   }
}
