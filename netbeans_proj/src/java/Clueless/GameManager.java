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
   private static Set<Room> _rooms;
   private static Set<Hall> _halls;
   private static Set<Character> _characters;
   private static Set<Weapon> _weapons;
   private Map<Long, Game> _games;
   private long _caseNumber;
   
   private GameManager(){
      _gamesLock = new Object();
      _games = new HashMap<Long, Game>(MAX_GAMES);
      _rooms = new HashSet<Room>();
      _halls = new HashSet<Hall>();
      _characters = new HashSet<Character>();
      _weapons = new HashSet<Weapon>();
      _caseNumber = 1;
      makeDeck();
   }
   
   private void makeDeck(){
      //Characters
      _characters.add(new Character("Miss Scarlet"));
      _characters.add(new Character("Colonel Mustard"));
      _characters.add(new Character("Ms. Peacock"));
      _characters.add(new Character("Professor Plum"));
      _characters.add(new Character("Mrs. White"));
      _characters.add(new Character("Mr. Green"));
      _characters.add(new Character("-Random-"));
      
      //Weapons
      _weapons.add(new Weapon("Revolver"));
      _weapons.add(new Weapon("Knife"));
      _weapons.add(new Weapon("Lead Pipe"));
      _weapons.add(new Weapon("Wrench"));
      _weapons.add(new Weapon("Candlestick"));
      _weapons.add(new Weapon("Rope"));
      
      //Rooms
      createBoard();
   }
   
   private void createBoard(){
      Location[][] board  = new Location[5][5];
      String[] roomNames = {"Study", "Hall", "Lounge", "Library",
         "Billiard Room", "Dining Room", "Conservatory", "Ballroom", "Kitchen"};
      
      //Create the board
      for(int i = 0; i < 4; ++i)
         for(int j = 0; j < 4; ++j)
            if(i % 2 == 0 && j % 2 == 0)
               board[i][j] = new Room(roomNames[(3*i+j)/2], new Position(i, j));
            else if(i % 2 == 1 ^ j % 2 == 1)
               board[i][j] = new Hall("", new Position(i, j));
            else
               board[i][j] = null;
      
      //Set neighbors
      Set<Location> neighbors;
      for(int i = 0; i < 4; ++i)
         for(int j = 0; j < 4; ++j){
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
               if(board[i][j] instanceof Room)
                  _rooms.add((Room)board[i][j]);
               else
                  _halls.add((Hall)board[i][j]);
            }
         }
   }
   
   public static synchronized GameManager getInstance(){
      if(_me == null) _me = new GameManager();
      return _me;
   }
   
   public Long createGame(String playStyle, String name, String password,
           WsOutbound playerSoc, String playerLang){
      if(name.equals("")) name = "Case #" + _caseNumber;
      Game g = new Game(_caseNumber++, playStyle, name, password);
      Player p = new Player(playerSoc, playerLang);
      g.addPlayer(p);
      synchronized(_gamesLock){
         _games.put((Long)g.Id, g);
      }
      return g.Id;
   }
   
   public Set<GameInfo> queryGames(String playStyle, Boolean secured){
      Set<GameInfo> retVal = new HashSet<GameInfo>();
      
      synchronized(_gamesLock){
      for(Game g : _games.values())
         if((playStyle.equals("") || g.getPlayStyle().equals(playStyle)) &&
                 (secured == null || g.getPassword().equals("") != secured))
            retVal.add(new GameInfo(g));
      }
      
      return retVal;
   }
   
   public boolean joinGame(Long gameId, WsOutbound playerSoc, String playerLang){
      boolean retVal = false;
      Player p = new Player(playerSoc, playerLang);
      synchronized(_gamesLock){
         if(_games.get(gameId) != null)
            retVal = _games.get(gameId).addPlayer(p);
      }
      return retVal;
   }
   
   public void deleteGame(Long gameId){
      synchronized(_gamesLock){
         _games.remove(gameId);
      }
   }
   
   public Game getGame(Long gameId){
      Game g;
      synchronized(_gamesLock){
         g = _games.get(gameId);
      }
      return g;
   }

   /**
    * @return the _board
    */
   public static Set<Room> getRooms() {
      return _rooms;
   }
   
   public static Set<Hall> getHalls(){
      return _halls;
   }
   
   public static Set<Location> getBoard(){
      Set<Location> board = new HashSet<Location>(_rooms);
      board.addAll(_halls);
      return board;
   }
   
   public static Set<Character> getCharacters(){
      return _characters;
   }
   
   public static Set<Weapon> getWeapons(){
      return _weapons;
   }
}
