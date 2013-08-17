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
      if(GameManager.getInstance().leaveGame(gId, pId))
         System.out.println("(PlayerDeletingRunnable) Player deleted!");
      else
         System.out.println("(PlayerDeletingRunnable) GameManager failed to delete!");
   }
   
}
