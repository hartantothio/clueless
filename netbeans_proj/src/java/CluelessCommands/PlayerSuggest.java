/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CluelessCommands;

/**
 *
 * @author davis_gigogne
 */
public class PlayerSuggest extends Command {
   public Long gameId;
   public Integer playerId;
   public String character, room, weapon;
   public Boolean disproving;
   public PlayerSuggest(){
      super(PlayerSuggest.class.getSimpleName());
   }
}
