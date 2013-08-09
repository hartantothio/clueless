/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clueless;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author davis_gigogne
 */
public class GameManager {
   private static GameManager _me;
   private static final int MAX_GAMES = 10;
   private Map<Integer, Game> _games;
   private long _caseNumber;
   private Set<Card> _deck;
   
   private GameManager(){
      _games = new HashMap<Integer, Game>(MAX_GAMES);
      _caseNumber = 1;
      makeDeck();
   }
   
   private void makeDeck(){
      //Characters
      _deck.add(new Character("Miss Scarlet"));
      _deck.add(new Character("Colonel Mustard"));
      _deck.add(new Character("Ms. Peacock"));
      _deck.add(new Character("Professor Plum"));
      _deck.add(new Character("Mrs. White"));
      _deck.add(new Character("Mr. Green"));
      
      //Weapons
      _deck.add(new Weapon("Revolver"));
      _deck.add(new Weapon("Knife"));
      _deck.add(new Weapon("Lead Pipe"));
      _deck.add(new Weapon("Wrench"));
      _deck.add(new Weapon("Candlestick"));
      _deck.add(new Weapon("Rope"));
      
      //Rooms
      for(Room r : createRooms())
         _deck.add(r);
   }
   
   private Set<Room> createRooms(){
      
   }
   
   public static GameManager getInstance(){
      if(_me == null) _me = new GameManager();
      return _me;
   }
}
