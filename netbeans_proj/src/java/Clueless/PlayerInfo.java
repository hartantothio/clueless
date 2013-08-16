/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clueless;

/**
 *
 * @author davis_gigogne
 */
public class PlayerInfo {
   public Long id;
   public String character;
   
   public PlayerInfo(Player p){
      id = p.getId();
      character = p.getCharacter().getName();
   }
}
