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
   public String name, playStyle, password;
   public PlayerInfo[] players;
   
   public GameInfo(Game g){
      id = g.Id;
      name = g.getName();
      playStyle = g.getPlayStyle();
      password = g.getPassword();
      players = new PlayerInfo[g.getPlayerCount()];
      
      List<Player> tPlayers = g.getPlayers();
      for(int i = 0; i < tPlayers.size(); ++i)
         players[i] = new PlayerInfo(tPlayers.get(i));
   }
}
