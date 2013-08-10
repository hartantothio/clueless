/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clueless;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author davis_gigogne
 */
public class Game {
   public final long Id;
   private String _playStyle, _name, _password;
   private Set<Card> _solution;
   private Set<Player> _players;
   
   public Game(long id, String playStyle, String name, String password){
      Id = id;
      _playStyle = playStyle;
      _name = name;
      _password = password;
      _players = new HashSet<Player>(6);
      generateSolution();
   }
   
   private void generateSolution(){
      Card[] deck = (Card[]) GameManager.getDeck().toArray();
      Random r = new Random();
      
      Card current;
      
      while(_solution.size() < 1){
         current = deck[r.nextInt(deck.length)];
         if(current instanceof Character)
            _solution.add(current);
      }
      
      while(_solution.size() < 2){
         current = deck[r.nextInt(deck.length)];
         if(current instanceof Room)
            _solution.add(current);
      }
      
      while(_solution.size() < 3){
         current = deck[r.nextInt(deck.length)];
         if(current instanceof Weapon)
            _solution.add(current);
      }
   }

   /**
    * @return the _playStyle
    */
   public String getPlayStyle() {
      return _playStyle;
   }

   /**
    * @param playStyle the _playStyle to set
    */
   public void setPlayStyle(String playStyle) {
      this._playStyle = playStyle;
   }

   /**
    * @return the _name
    */
   public String getName() {
      return _name;
   }

   /**
    * @param name the _name to set
    */
   public void setName(String name) {
      this._name = name;
   }
   
   /**
    * @return the _password
    */
   public String getPassword() {
      return _password;
   }
}
