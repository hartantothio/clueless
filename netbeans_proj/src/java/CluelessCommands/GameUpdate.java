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
public class GameUpdate extends Command {
   public final boolean start;
   public final GameInfo gameInfo;
   public GameUpdate(Game g){
      super(GameUpdate.class.getSimpleName());
      this.start = g.getStarted();
      gameInfo = new GameInfo(g);
   }
}
