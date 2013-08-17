/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CluelessCommands;

/**
 *
 * @author davis_gigogne
 */
public class JoinGame extends Command {
   public Long gameId;
   public Integer playerId;
   public String playerLanguage;
   public boolean joined;
   public JoinGame(){
      super(JoinGame.class.getSimpleName());
   }
}
