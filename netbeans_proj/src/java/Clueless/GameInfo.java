/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clueless;

import java.util.List;

/**
 *
 * @author davis_gigogne
 */
public class GameInfo {
   public Long id;
   public Integer creatorId, currentPlayer;
   public String name, playStyle, password;
   public PlayerInfo[] players;
   public boolean started;
   
   public GameInfo(Game g){
      id = g.Id;
      started = g.getStarted();
      creatorId = g.creatorId;
      Player p = g.getCurrentPlayer();
      currentPlayer = p == null ? -1 : p.getId();
      name = g.getName();
      playStyle = g.getPlayStyle();
      password = g.getPassword();
      players = new PlayerInfo[g.getPlayerCount()];
      
      List<Player> tPlayers = g.getPlayers();
      for(int i = 0; i < tPlayers.size(); ++i)
         players[i] = new PlayerInfo(tPlayers.get(i));
   }
}
