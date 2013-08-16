/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CluelessCommands;

import Clueless.Game;
import Clueless.GameInfo;

/**
 *
 * @author davis_gigogne
 */
public class PlayerJoined extends Command {
   public final GameInfo gameInfo;
   public PlayerJoined(Game g){
      super(PlayerJoined.class.getSimpleName());
      gameInfo = new GameInfo(g);
   }
}
