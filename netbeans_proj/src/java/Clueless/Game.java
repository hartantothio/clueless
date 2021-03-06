/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clueless;

import CluelessCommands.Command;
import CluelessCommands.GameUpdate;
import CluelessCommands.PlayerMustDisprove;
import CluelessCommands.PlayerQuit;
import CluelessCommands.PlayerSuggest;
import CluelessCommands.PlayerUpdate;
import java.util.ArrayList;
import java.util.HashSet;
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
   public final int creatorId;
   private boolean started;
   private String _playStyle, _name, _password;
   private Set<Card> _solution;
   private List<Player> _players;
   private Player _lastSuggestor;
   private int _currentPlayer;
   
   public Game(long id, int creatorId, String playStyle, String name, String password){
      Id = id;
      this.creatorId = creatorId;
      started = false;
      _playStyle = playStyle;
      _name = name;
      _password = password;
      _players = new ArrayList<Player>(6);
      _lastSuggestor = null;
      _solution = new HashSet<Card>();
   }
   
   private void generateSolution(){
      Random r = new Random(System.currentTimeMillis());
      Set<Character> chars = new HashSet<Character>((Set)GameManager.getCharacters());
      for(Iterator<Character> i = chars.iterator(); i.hasNext();)
         if(i.next().getName().equals("-Random-"))
            i.remove();
      _solution.add((Card)chars.toArray()[r.nextInt(6)]);
      _solution.add((Card)GameManager.getWeapons().toArray()[r.nextInt(6)]);
      _solution.add((Card)GameManager.getRooms().toArray()[r.nextInt(9)]);
      
      System.out.println("(Game) solution:");
      for(Card c : _solution)System.out.println(c);
      System.out.println("(Game) END solution");
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
      Set<Character> available = new HashSet<Character>((Set)GameManager.getCharacters());
      for(Player p : _players)
         if(!p.getCharacter().getName().equals("-Random-"))
         available.remove(p.getCharacter());
      return available;
   }
   
   public boolean addPlayer(Player p){
      if(_players.size() > 5) return false;
      p.setCharacter(new Character("-Random-"));
      p.setActive(true);
      p.setGame(this.Id);
      return _players.add(p);
   }
   
   public void updatePlayer(Player p){
      for(int i = 0; i < _players.size(); ++i){
         if(_players.get(i).getId() == p.getId()){
            _players.remove(i);
            _players.add(i, p);
            break;
         }
      }
   }
   
   public boolean removePlayer(Player p){
      if(p == null){
         return false;
      }
      
      List<Card> args = new ArrayList<Card>();
      args.add(p.getCharacter());
      notifyAllPlayers(NotificationEnum.PlayerQuitNotice, args);
      boolean retVal = processEndTurn(p, true);
      if(retVal) System.out.println("(Game/removePlayer) Player deleted!");
      if(_players.size() < 3){
         for(Player p2 : _players){
            PlayerQuit pq = new PlayerQuit();
            pq.gameId = Id;
            pq.playerId = p2.getId();
            pq.quit = true;
            p2.alert(pq);
         }
         
         _players.clear();
      }
      if(_players.isEmpty()) GameManager.getInstance().deleteGame(Id);
      return retVal;
   }
   
   public Player getPlayer(WsOutbound wso){
      for(Player p : _players){
         if(p.getSocket().equals(wso))
            return p;
      }
      
      return null;
   }
   
   public Player getPlayer(Integer id){
      for(Player p : _players){
         if(p.getId().longValue() == id.longValue())
            return p;
      }
      
      return null;
   }
   
   public Player getCurrentPlayer(){
      if(_players.isEmpty()) return null;
      return _players.get(_currentPlayer);
   }
   
   public List<Player> getPlayers(){
      return _players;
   }
   
   public int getPlayerCount(){
      return _players.size();
   }
   
   public boolean getStarted(){
      return started;
   }
   
   public void alertAllPlayers(Command c){
      for(Player p : _players)
         p.alert(c);
   }
   
   public void notifyAllPlayers(NotificationEnum notice, List args){
      for(Player p : _players){
         p.notify(notice, args);
      }
   }
   
   public boolean start(){
      if(_players.size() < 3) return false;
      
      //Determine a solution
      generateSolution();
      
      //Get clues for players
      Set<Card> deck = GameManager.getCharacters();
      for(Iterator<Card> i = deck.iterator(); i.hasNext();)
         if(i.next().equals(new Character("-Random-")))
            i.remove();
      deck.addAll(GameManager.getRooms());
      deck.addAll(GameManager.getWeapons());
      deck.removeAll(_solution);
      System.out.println("(start) possibilities:");
      for(Card c : deck) System.out.println(c);
      
      //Determine a turn order and give players clues
      List<Player> reorder = new ArrayList<Player>(_players);
      Random r = new Random(System.currentTimeMillis());
      
      _players.clear();
      int i;
      Iterator<Card> clueIndex = deck.iterator();
      Set<Card> clues = new HashSet<Card>();
      while(reorder.size() > 0){
         i = r.nextInt(reorder.size());
         
         clues.add(clueIndex.next());
         clueIndex.remove();
         clues.add(clueIndex.next());
         clueIndex.remove();
         clues.add(clueIndex.next());
         clueIndex.remove();
         
         reorder.get(i).setClues(new HashSet<Card>(clues));
         clues.clear();
         _players.add(reorder.get(i));
         reorder.remove(i);
      }
      _currentPlayer = 0;
      
      //Make sure there are no more "random" characters
      List<Character> available = new ArrayList<Character>(getAvailableCharacters());
      if(available.size() != 1){
         available.remove(new Character("-Random-"));
         for(Player p : _players)
            if(p.getCharacter().getName().equals("-Random-")){
               p.setCharacter(available.get(r.nextInt(available.size())));
               available.remove(p.getCharacter());
            }
      }
      
      //Notify for turn
      List args = new ArrayList();
      args.add(_players.get(_currentPlayer).getCharacter());
      notifyAllPlayers(NotificationEnum.PlayerGetTurn, args);
      started = true;
      return true;
   }
   
   public void processSuggestion(WsOutbound pConn, Character murderer,
           Room scene, Weapon weapon){
      //Determine the suggestor from the socket and remove them
      //from the list of possible disproving players
      List<Player> others = new ArrayList<Player>(_players);
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
         for(Card c : p.getClues()){
            if(disprover != null) break;
            for(Card s : noticeArgs){
               if(disprover != null) break;
               if(c.equals(s)){
                  disprover = p.getCharacter();
                  PlayerMustDisprove ps = new PlayerMustDisprove();
                  ps.gameId = Id;
                  ps.playerId = getPlayer(pConn).getId();
                  ps.character = murderer.getName();
                  ps.room = scene.getName();
                  ps.weapon = weapon.getName();
                  p.alert(ps);
               }
            }
         }
      }
      
      //Send out the appropriate notifications
      noticeArgs.clear();
//      if(disprover != null){
//         noticeArgs.add(disprover);
//         noticeArgs.add(_lastSuggestor.getCharacter());
//         notifyAllPlayers(NotificationEnum.PlayerDisprovedSuggestion, (List)noticeArgs);
//      }
//      else{
      if(disprover == null){
         noticeArgs.add(_lastSuggestor.getCharacter());
         notifyAllPlayers(NotificationEnum.PlayerFailedToDisproveSuggestion, (List)noticeArgs);
      }
   }
   
   public void processDisproveSuggestion(PlayerSuggest ps){
      List args = new ArrayList();
      args.add(getPlayer(ps.playerId));
      if(ps.character == null || ps.character.equals("")){
         if(ps.room == null || ps.room.equals("")){
            args.add(new Weapon(ps.weapon));
         }
         else{
            args.add(new Room(ps.room, new Position(0,0)));
         }
      }
      else{
         args.add(new Character(ps.character));
      }
      
      _lastSuggestor.notify(NotificationEnum.PlayerDisprovedSuggestionWithClue, args);
      args.clear();
      args.add(getPlayer(ps.playerId));
      args.add(_lastSuggestor);
      notifyAllPlayers(NotificationEnum.PlayerDisprovedSuggestion, args);
      _lastSuggestor = null;
   }
   
   public boolean processAccusation(WsOutbound pConn, Character murderer, Room scene,
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
      if(accuser == null) return false;
      args.add(accuser.getCharacter());
      args.add(murderer);
      args.add(scene);
      args.add(weapon);
      notifyAllPlayers(NotificationEnum.PlayerMadeAccusation, args);
      
      //Determine if the accusation was correct and act appropriately
      args.remove(accuser.getCharacter());
      for(Card c : _solution) System.out.println("(accuse) sol: " + c);
      for(Card c : args) System.out.println("(accuse) given: " + c);
      boolean right = true;
      for(Card c : _solution){
         if(c instanceof Character && !c.getName().equals(murderer.getName())){
            right = false;
            break;
         }
         else if(c instanceof Room && !c.getName().equals(scene.getName())){
            right = false;
            break;
         }
         else if(c instanceof Weapon && !c.getName().equals(weapon.getName())){
            right = false;
            break;
         }
      }
      if(right){
         args.clear();
         args.add(accuser.getCharacter());
         notifyAllPlayers(NotificationEnum.PlayerAccusationCorrect, args);
         return true;
      }
      else{
         args.clear();
         args.add(accuser.getCharacter());
         notifyAllPlayers(NotificationEnum.PlayerAccusationIncorrect, args);
         accuser.setActive(false);
         int i = _players.indexOf(accuser);
         _players.get(i).setActive(false);
         processEndTurn(accuser, false);
         return false;
      }
   }
   
   public boolean processEndTurn(Player p, boolean quitting){
      //Only the current player can end their turn
      if(_players.indexOf(p) == _currentPlayer) {
         boolean retVal = true;
         List args = new ArrayList();
         args.add(p.getCharacter());
         notifyAllPlayers(NotificationEnum.PlayerEndTurn, args);
         
         if(quitting)
            retVal = _players.remove(p);
         else
            ++_currentPlayer;
         
         if(_currentPlayer >= _players.size())
            _currentPlayer = 0;
         
         int start = _currentPlayer;
         while(!_players.get(_currentPlayer).isActive()){
            if(++_currentPlayer >= _players.size())
               _currentPlayer = 0;
            if(_currentPlayer == start){
               PlayerQuit pq = new PlayerQuit();
               pq.gameId = Id;
               for(Player p2 : _players){
                  pq.playerId = p2.getId();
                  pq.quit = true;
                  p2.alert(pq);
               }
            }
         }
         
         args.clear();
         args.add(_players.get(_currentPlayer).getCharacter());
         notifyAllPlayers(NotificationEnum.PlayerGetTurn, args);
         GameUpdate gu = new GameUpdate(this);
         alertAllPlayers(gu);
         return retVal;
      }
      
      return false;
   }
}
