/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clueless;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.apache.catalina.websocket.WsOutbound;

/**
 *
 * @author davis_gigogne
 */
public class Game {
   public final long Id;
   private String _playStyle, _name, _password;
   private Set<Card> _solution;
   private List<Player> _players;
   private Player _lastSuggestor;
   private int _currentPlayer;
   
   public Game(long id, String playStyle, String name, String password){
      Id = id;
      _playStyle = playStyle;
      _name = name;
      _password = password;
      _players = new ArrayList<Player>(6);
      _lastSuggestor = null;
   }
   
   private void generateSolution(){
      Random r = new Random(System.currentTimeMillis());
      _solution.add((Card)GameManager.getCharacters().toArray()[r.nextInt(6)]);
      _solution.add((Card)GameManager.getWeapons().toArray()[r.nextInt(6)]);
      _solution.add((Card)GameManager.getRooms().toArray()[r.nextInt(9)]);
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
   
   /**
    * @return the _lastSuggestor
    */
   public Player getLastSuggestor() {
      return _lastSuggestor;
   }

   /**
    * @param lastSuggestor the _lastSuggestor to set
    */
   public void setLastSuggestor(Player lastSuggestor) {
      this._lastSuggestor = lastSuggestor;
   }
   
   public Set<Character> getAvailableCharacters(){
      //Remember, <Random> should always be available
      Set<Character> available = GameManager.getCharacters();
      for(Player p : _players)
         if(!p.getCharacter().getName().equals("<Random>"))
         available.remove(p.getCharacter());
      return available;
   }
   
   public boolean addPlayer(Player p){
      if(_players.size() > 5) return false;
      p.setCharacter(getAvailableCharacters().iterator().next());
      p.setActive(true);
      p.setGame(this);
      return _players.add(p);
   }
   
   public boolean removePlayer(Player p){
      if(p == null) return false;
      List<Card> args = new ArrayList<Card>();
      args.add(p.getCharacter());
      notifyAllPlayers(NotificationEnum.PlayerQuit, args);
      return _players.remove(p);
   }
   
   public Player getPlayer(WsOutbound wso){
      for(Player p : _players)
         if(p.getSocket() == wso)
            return p;
      
      return null;
   }
   
   public int getPlayerCount(){
      return _players.size();
   }
   
   public void notifyAllPlayers(NotificationEnum notice, List args){
      if(notice == NotificationEnum.PlayerDisprovedSuggestion){
         _lastSuggestor.notify(NotificationEnum.PlayerDisprovedSuggestionWithClue, args);
         _lastSuggestor = null;
      }
      else
         for(Player p : _players)
            p.notify(notice, args);
   }
   
   public boolean start(){
      //if(_players.size() < 3) return false;
      
      //Determine a turn order
      List<Player> reorder = _players;
      Random r = new Random(System.currentTimeMillis());
      
      _players.clear();
      int i;
      while(reorder.size() > 0){
         i = r.nextInt(reorder.size());
         _players.add(reorder.get(i));
         reorder.remove(i);
      }
      _currentPlayer = 0;
      
      //Determine a solution
      generateSolution();
      
      //Make sure there are no more "random" characters
      Set<Character> available = getAvailableCharacters();
      if(available.size() != 1){
         available.remove(new Character("<Random>"));
         for(Player p : _players)
            if(p.getCharacter().getName().equals("<Random>")){
               Iterator<Character> a = available.iterator();
               p.setCharacter(a.next());
               a.remove();
            }
      }
      
      //Notify for turn
      List args = new ArrayList();
      args.add(_players.get(_currentPlayer).getCharacter());
      notifyAllPlayers(NotificationEnum.PlayerGetTurn, args);
      return true;
   }
   
   public void processSuggestion(WsOutbound pConn, Character murderer,
           Room scene, Weapon weapon){
      //Determine the suggestor from the socket and remove them
      //from the list of possible disproving players
      List<Player> others = _players;
      for(Player p : _players)
         if(p.getSocket() == pConn){
            _lastSuggestor = p;
            others.remove(p);
         }
      
      //Notify everyone of the suggestion
      List<Card> noticeArgs = new ArrayList<Card>();
      noticeArgs.add(_lastSuggestor.getCharacter());
      noticeArgs.add(murderer);
      noticeArgs.add(scene);
      noticeArgs.add(weapon);
      notifyAllPlayers(NotificationEnum.PlayerMadeSuggestion, (List)noticeArgs);
      
      //Removing the suggestor leaves a set of the suggested clues
      noticeArgs.remove(_lastSuggestor.getCharacter());
      
      //Determine if there is a player that can disprove the suggestion
      Character disprover = null;
      for(Player p : others){
         if(disprover != null) break;
         for(Iterator<Card> i = noticeArgs.iterator(); disprover == null && i.hasNext();)
            if(p.getClues().contains(i.next())){
               disprover = p.getCharacter();
               p.notify(NotificationEnum.PlayerMustDisproveSuggestion, null);
            }
      }
      
      //Send out the appropriate notifications
      noticeArgs.clear();
      if(disprover != null){
         noticeArgs.add(disprover);
         noticeArgs.add(_lastSuggestor.getCharacter());
         notifyAllPlayers(NotificationEnum.PlayerDisprovedSuggestion, (List)noticeArgs);
      }
      else{
         noticeArgs.add(_lastSuggestor.getCharacter());
         notifyAllPlayers(NotificationEnum.PlayerFailedToDisproveSuggestion, (List)noticeArgs);
      }
   }
   
   public void processAccusation(WsOutbound pConn, Character murderer, Room scene,
           Weapon weapon){
      //Notify all players
      List<Card> args = new ArrayList<Card>();
      Player accuser = null;
      for(Player p : _players)
         if(p.getSocket() == pConn){
            accuser = p;
            break;
         }
      //This 'if' should never happen, but if not here, Netbeans gives a warning
      //that .getCharacters may be called on a null object
      if(accuser == null) return;
      args.add(accuser.getCharacter());
      args.add(murderer);
      args.add(scene);
      args.add(weapon);
      notifyAllPlayers(NotificationEnum.PlayerMadeAccusation, args);
      
      //Determine if the accusation was correct and act appropriately
      args.remove(accuser.getCharacter());
      if(_solution.containsAll(args)){
         args.clear();
         args.add(accuser.getCharacter());
         notifyAllPlayers(NotificationEnum.PlayerAccusationCorrect, args);
      }
      else{
         args.clear();
         args.add(accuser.getCharacter());
         notifyAllPlayers(NotificationEnum.PlayerAccusationIncorrect, args);
         accuser.setActive(false);
         _players.remove(accuser);
         _players.add(accuser);
      }
   }
   
   public void processEndTurn(Player p){
      //Only the current player can end their turn
      if(_players.indexOf(p) == _currentPlayer) {
         List args = new ArrayList();
         args.add(p.getCharacter());
         notifyAllPlayers(NotificationEnum.PlayerEndTurn, args);
         
         if(++_currentPlayer > _players.size()) _currentPlayer = 0;
         
         args.clear();
         args.add(_players.get(_currentPlayer).getCharacter());
         notifyAllPlayers(NotificationEnum.PlayerGetTurn, args);
      }
   }
}
