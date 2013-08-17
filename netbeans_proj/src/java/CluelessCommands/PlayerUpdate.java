/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CluelessCommands;

import Clueless.NotificationEnum;

/**
 *
 * @author davis_gigogne
 */
public class PlayerUpdate extends Command {
   public Long gameId;
   public Integer playerId;
   public String msg;
   
   public PlayerUpdate(NotificationEnum ne){
      super(ne.toString());
   }
}
