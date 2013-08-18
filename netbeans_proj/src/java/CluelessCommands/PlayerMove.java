/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CluelessCommands;

/**
 *
 * @author davis_gigogne
 */
public class PlayerMove extends Command {
   public Long gameId;
   public Integer playerId;
   public String room;
   
   public PlayerMove(){
      super(PlayerMove.class.getSimpleName());
   }
}
