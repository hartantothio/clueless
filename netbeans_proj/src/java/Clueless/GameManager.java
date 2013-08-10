/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clueless;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.catalina.websocket.WsOutbound;

/**
 *
 * @author davis_gigogne
 */
public class GameManager {
   private static final int MAX_GAMES = 10;
   private static GameManager _me;
   
   private final Object _gamesLock;
   private Map<Integer, Game> _games;
   private Set<Location> _board;
   private static Set<Card> _deck;
   private long _caseNumber;
   
   private GameManager(){
      _gamesLock = new Object();
      _games = new HashMap<Integer, Game>(MAX_GAMES);
      _board = new HashSet<Location>();
      _deck = new HashSet<Card>();
      _caseNumber = 1;
      makeDeck();
   }
   
   private void makeDeck(){
      //Characters
      getDeck().add(new Character("Miss Scarlet"));
      getDeck().add(new Character("Colonel Mustard"));
      getDeck().add(new Character("Ms. Peacock"));
      getDeck().add(new Character("Professor Plum"));
      getDeck().add(new Character("Mrs. White"));
      getDeck().add(new Character("Mr. Green"));
      
      //Weapons
      getDeck().add(new Weapon("Revolver"));
      getDeck().add(new Weapon("Knife"));
      getDeck().add(new Weapon("Lead Pipe"));
      getDeck().add(new Weapon("Wrench"));
      getDeck().add(new Weapon("Candlestick"));
      getDeck().add(new Weapon("Rope"));
      
      //Rooms
      createBoard();
      for(Location l : _board)
         if(l instanceof Room)
            getDeck().add(l);
   }
   
   private void createBoard(){
      Location[][] board  = new Location[5][5];
      String[] roomNames = {"Study", "Hall", "Lounge", "Library",
         "Billiard Room", "Dining Room", "Conservatory", "Ballroom", "Kitchen"};
      
      //Create the board
      for(int i = 0; i < 5; ++i)
         for(int j = 0; j < 5; ++j)
            if(i % 2 == 0 && j % 2 == 0)
               board[i][j] = new Room(roomNames[(3*i+j)/2], new Position(i, j));
            else if(i % 2 == 1 ^ j % 2 == 1)
               board[i][j] = new Hall("", new Position(i, j));
            else
               board[i][j] = null;
      
      //Set neighbors
      Set<Location> neighbors;
      for(int i = 0; i < 5; ++i)
         for(int j = 0; j < 5; ++j){
            neighbors = new HashSet<Location>();
            
            if(board[i][j] != null){
               int a = (i - 1), d = (i + 1), w = (j - 1), s = (j + 1);
               if(a > -1 && board[a][j] != null) neighbors.add(board[a][j]);
               if(d > -1 && board[d][j] != null) neighbors.add(board[d][j]);
               if(w > -1 && board[i][w] != null) neighbors.add(board[i][w]);
               if(s > -1 && board[i][s] != null) neighbors.add(board[i][s]);
               
               if(i == 0){
                  if(j == 0)
                     neighbors.add(board[4][4]);
                  else if(j == 4)
                     neighbors.add(board[4][0]);
               }
               else if(i == 4)
                  if(j == 0)
                     neighbors.add(board[0][4]);
                  else if(j == 4)
                     neighbors.add(board[0][0]);
               
               board[i][j].setNeighbors(neighbors);
               _board.add(board[i][j]);
            }
         }
   }
   
   public static synchronized GameManager getInstance(){
      if(_me == null) _me = new GameManager();
      return _me;
   }
   
   public long createGame(String playStyle, String name, String password,
           WsOutbound playerSoc){
      if(name.equals("")) name = "Case #" + _caseNumber;
      Game g = new Game(_caseNumber++, playStyle, name, password);
      //TODO: Player constructor
   }
   
   public Set<Game> queryGames(String playStyle, boolean secured){
      Set<Game> retVal = new HashSet<Game>();
      
      synchronized(_gamesLock){
      for(Game g : _games.values())
         if(g.getPlayStyle().equals(playStyle) && g.getPassword().equals("") != secured)
            retVal.add(g);
      }
      
      return retVal;
   }
   
   public boolean joinGame(){
      
   }
   
   public void deleteGame(Integer gameId){
      synchronized(_gamesLock){
         _games.remove(gameId);
      }
   }

   /**
    * @return the _deck
    */
   public static Set<Card> getDeck() {
      return _deck;
   }
}
