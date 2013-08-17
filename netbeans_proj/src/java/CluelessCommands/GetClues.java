/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CluelessCommands;

import Clueless.Card;
import java.util.Set;

/**
 *
 * @author davis_gigogne
 */
public class GetClues extends Command {
   public Long gameId;
   public Integer playerId;
   public Set<Card> clues;
   public GetClues(){
      super(GetClues.class.getSimpleName());
   }
}
