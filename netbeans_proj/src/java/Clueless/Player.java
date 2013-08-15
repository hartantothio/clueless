/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clueless;

import CluelessCommands.PlayerChat;
import com.google.gson.Gson;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.List;
import java.util.Set;
import org.apache.catalina.websocket.WsOutbound;

/**
 *
 * @author davis_gigogne
 */
public class Player {
   private WsOutbound _clientSocket;
   private Game _game;
   private boolean _active;
   private Character _character;
   private Set<Card> _clues;
   private String _selectedLanguage;
   
   public Player(WsOutbound conn, String lang){
      _clientSocket = conn;
      _selectedLanguage = lang;
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
   public Game getGame() {
      return _game;
   }

   /**
    * @param game the _game to set
    */
   public void setGame(Game game) {
      this._game = game;
   }
   
   /**
    * @return the _clientSocket
    */
   public WsOutbound getSocket() {
      return _clientSocket;
   }
   
   public void notify(NotificationEnum notice, List args){
      String msg = NotificationManager.getInstance().getRawNotification(_selectedLanguage, notice);
      
      if(msg.equals(""))
         if(args != null && args.iterator().hasNext())
            msg = args.iterator().next().toString();
      else
         for(Object arg : args)
            msg = msg.replace("\\s", arg.toString());

      try{
         PlayerChat pc = new PlayerChat();
         pc.gameId = _game.Id;
         pc.msg = msg;
         Gson gson = new Gson();
      _clientSocket.writeTextMessage(CharBuffer.wrap(gson.toJson(pc, PlayerChat.class)));
      }
      catch(IOException iox){
         System.out.println(iox);
      }
   }
   
   public void notifyOthers(NotificationEnum notice, List args){
      _game.notifyAllPlayers(notice, args);
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
