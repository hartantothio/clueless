/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CluelessCommands;

import Clueless.Game;
import java.util.Set;

/**
 *
 * @author davis_gigogne
 */
public class QueryGames extends Command {
   public String playStyle;
   public Boolean secured;
   public Set<Game> games;
   public QueryGames(){
      super(QueryGames.class.getSimpleName());
   }
}
