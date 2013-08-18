/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CluelessCommands;

/**
 *
 * @author davis_gigogne
 */
public class PlayerMustDisprove extends Command {
   public Long gameId;
   public Integer playerId;
   public String character, room, weapon;
   
   public PlayerMustDisprove(){
      super(PlayerMustDisprove.class.getSimpleName());
   }
}
