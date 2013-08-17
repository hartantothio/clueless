/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clueless;

/**
 *
 * @author davis_gigogne
 */
public class PlayerDeletingRunnable implements Runnable {
   private long pId, gId;
   
   public PlayerDeletingRunnable(long pId, long gId){
      this.pId = pId;
      this.gId = gId;
   }

   @Override
   public void run() {
      GameManager.getInstance().getGame(gId).removePlayer(
              GameManager.getInstance().getGame(gId).getPlayer(pId));
      System.out.println("Player deleted!");
   }
   
}
