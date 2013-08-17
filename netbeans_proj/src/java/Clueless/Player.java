/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clueless;

import CluelessCommands.Command;
import CluelessCommands.PlayerUpdate;
import com.google.gson.Gson;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.apache.catalina.websocket.WsOutbound;

/**
 *
 * @author davis_gigogne
 */
public class Player {
   private WsOutbound _clientSocket;
   private Long _game;
   private Integer _id;
   private boolean _active;
   private Character _character;
   private Set<Card> _clues;
   private String _selectedLanguage;
   
   public Player(WsOutbound conn, String lang){
      _clientSocket = conn;
      _selectedLanguage = lang;
      
      Random r = new Random(System.currentTimeMillis());
      _id = r.nextInt();
      if(_id < 0) _id = -_id;
   }

   /**
    * @return the _active
    */
   public boolean isActive() {
      return _active;
   }

   /**
    * @param active the _active to set
    */
   public void setActive(boolean active) {
      this._active = active;
   }

   /**
    * @return the _character
    */
   public Character getCharacter() {
      return _character;
   }

   /**
    * @param character the _character to set
    */
   public void setCharacter(Character character) {
      this._character = character;
   }

   /**
    * @return the _clues
    */
   public Set<Card> getClues() {
      return _clues;
   }

   /**
    * @param clues the _clues to set
    */
   public void setClues(Set<Card> clues) {
      this._clues = clues;
   }

   /**
    * @return the _selectedLanguage
    */
   public String getSelectedLanguage() {
      return _selectedLanguage;
   }

   /**
    * @param selectedLanguage the _selectedLanguage to set
    */
   public void setSelectedLanguage(String selectedLanguage) {
      this._selectedLanguage = selectedLanguage;
   }

   /**
    * @return the _game
    */
   public Long getGame() {
      return _game;
   }

   /**
    * @param game the _game to set
    */
   public void setGame(Long game) {
      this._game = game;
   }
   
   /**
    * @return the _clientSocket
    */
   public WsOutbound getSocket() {
      return _clientSocket;
   }
   
   public void setSocket(WsOutbound wso) {
      _clientSocket = wso;
      GameManager.getInstance().getGame(_game).updatePlayer(this);
   }
   
   public Integer getId(){
      return _id;
   }
   
   public void alert(Command c){
      System.out.println("!!!ALERT!!!");
      Gson gson = new Gson();
      try{
         _clientSocket.writeTextMessage(CharBuffer.wrap(gson.toJson(c, c.getClass())));
      }
      catch(Exception e){
         System.out.println(e);
      }
   }
   
   public void notify(NotificationEnum notice, List args){
      System.out.println("???NOTIFY???");
      String msg = NotificationManager.getInstance().getRawNotification(_selectedLanguage, notice);
      
      if(msg.equals("") && args != null && args.iterator().hasNext())
            msg = args.iterator().next().toString();
      else
         for(Object o : args)
            msg = msg.replaceFirst("xx", o.toString());

      try{
         PlayerUpdate pc = new PlayerUpdate(notice);
         pc.gameId = _game;
         pc.msg = msg;
         Gson gson = new Gson();
      _clientSocket.writeTextMessage(CharBuffer.wrap(gson.toJson(pc, PlayerUpdate.class)));
      }
      catch(IOException iox){
         System.out.println(iox);
      }
   }
   
   public void notifyOthers(NotificationEnum notice, List args){
      GameManager.getInstance().getGame(_game).notifyAllPlayers(notice, args);
   }
   
   @Override
   public String toString(){
      return _character.toString();
   }
   
   @Override
   public boolean equals(Object o){
      if(!(o instanceof Player)) return super.equals(o);
      return _clientSocket.equals(((Player) o).getSocket());
   }
   
   @Override
   public int hashCode(){
      return super.hashCode();
   }
}
