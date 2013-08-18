/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CluelessCommands;

/**
 *
 * @author davis_gigogne
 */
public class PlayerAccuse extends Command {
   public Long gameId;
   public Integer playerId;
   public String character, room, weapon;
   public PlayerAccuse(){
      super(PlayerAccuse.class.getSimpleName());
   }
}
