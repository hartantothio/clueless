/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CluelessCommands;

/**
 *
 * @author davis_gigogne
 */
public class EndTurn extends Command {
   public Long gameId;
   public Integer playerId;
   public EndTurn(){
      super(EndTurn.class.getSimpleName());
   }
}
