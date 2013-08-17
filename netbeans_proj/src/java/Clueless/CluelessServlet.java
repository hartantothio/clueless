//This sample is how to use websocket of Tomcat.
package Clueless;

import CluelessCommands.*;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;

public class CluelessServlet extends WebSocketServlet{
    private static final long serialVersionUID = 1L;
    private static ArrayList<MyMessageInbound> mmiList = new ArrayList<MyMessageInbound>();
    private static Map<Integer, ScheduledFuture> removals = new HashMap<Integer, ScheduledFuture>();
    private static ScheduledThreadPoolExecutor remover =
            new ScheduledThreadPoolExecutor(6, new ThreadFactory(){
               @Override
               public Thread newThread(Runnable r) {
                  Thread t = Executors.defaultThreadFactory().newThread(r);
                  t.setDaemon(true);
                  return t;
               } 
            });

   @Override
   public void destroy() {
      remover.shutdownNow();
      super.destroy();
   }

    @Override
    public StreamInbound createWebSocketInbound(String protocol, HttpServletRequest hsr){
        return new MyMessageInbound();
    }

    private class MyMessageInbound extends MessageInbound{
        WsOutbound myoutbound;
        Long gameId;

        @Override
        public void onOpen(WsOutbound outbound){
//            try {
                System.out.println("Client connected");
                this.myoutbound = outbound;
                mmiList.add(this);
                //Send the client all our commands
//                StringBuilder sb = new StringBuilder();
//                for(NotificationEnum ne : NotificationEnum.values())
//                   sb = sb.append(ne.toString()).append('\n');
//                outbound.writeTextMessage(CharBuffer.wrap(sb.toString()));
//            } catch (IOException e) {
//                Logger.getLogger(CluelessServlet.class.getName()).log(Level.SEVERE, null, e);
//            }
        }

        @Override
        public void onClose(int status){
            System.out.println("Client disconnected");
            if(gameId != null){
               Integer pId = GameManager.getInstance().getGame(gameId).getPlayer(myoutbound).getId();
               PlayerDeletingRunnable pdr = new PlayerDeletingRunnable(pId, gameId);
               ScheduledFuture sf = CluelessServlet.remover.schedule(pdr, 10, TimeUnit.SECONDS);
               CluelessServlet.removals.put(pId, sf);
            }
//            if(gameId != null)
//               GameManager.getInstance().getGame(gameId).removePlayer(
//                       GameManager.getInstance().getGame(gameId)
//                       .getPlayer(myoutbound)
//                       );
            mmiList.remove(this);
        }

        @Override
        public void onTextMessage(CharBuffer cb) throws IOException{
           JsonReader jr = new JsonReader(new StringReader(cb.toString()));
           Gson gson = new Gson();
           Class<? extends Command> c = null;
           
           //Read the string for the command type
           try{
              jr.beginObject();
            while(jr.hasNext()){
               String name = jr.nextName();
               String val = jr.nextString();
               if(name.equals("cmd")){
                  c = (Class<? extends Command>) Class.forName("CluelessCommands."+val);
                  break;
               }
            }
            while(jr.hasNext()){jr.nextName(); jr.nextString();}
            jr.endObject();
           }
           //If invalid command, "return" exception
           catch(ClassNotFoundException cnfe){
              Logger.getLogger(CluelessServlet.class.getName()).log(Level.SEVERE, null, cnfe);
              this.myoutbound.writeTextMessage(CharBuffer.wrap(gson.toJson(cnfe)));
           }
           
           //Begin call logic
           Command cmd = gson.fromJson(cb.toString(), c);
           if(!(cmd instanceof QueryGames))
            System.out.println("Raw JSON: " + new String(cb.append('\0').array()));
           
           if(cmd instanceof CreateGame){
              CreateGame cg = (CreateGame) cmd;
              cg.id = GameManager.getInstance().createGame(cg.playStyle,
                      cg.name, cg.password, myoutbound, cg.playerLanguage);
              cg.playerId = GameManager.getInstance().getGame(cg.id).getPlayer(myoutbound).getId();
              gameId = cg.id;
              cmd = cg;
           }
           else if(cmd instanceof QueryGames){
              QueryGames qg = (QueryGames) cmd;
              qg.games = GameManager.getInstance()
                      .queryGames(qg.playStyle, qg.secured);
              cmd = qg;
           }
           else if(cmd instanceof JoinGame){
              JoinGame jg = (JoinGame) cmd;
              jg.joined = GameManager.getInstance().joinGame(jg.gameId,
                      myoutbound, jg.playerLanguage);
              if(jg.joined){
                 gameId = jg.gameId;
                 jg.playerId = GameManager.getInstance().getGame(jg.gameId)
                         .getPlayer(myoutbound).getId();
                 GameUpdate gu = new GameUpdate(GameManager.getInstance().getGame(jg.gameId), false);
                 GameManager.getInstance().getGame(jg.gameId).alertAllPlayers(gu);
                 
              }
              cmd = jg;
           }
           else if(cmd instanceof GetAvailableCharacters){
              GetAvailableCharacters gac = (GetAvailableCharacters) cmd;
              gac.characters = GameManager.getInstance().getGame(gac.gameId)
                      .getAvailableCharacters();
              cmd = gac;
           }
           else if(cmd instanceof ChangeCharacter){
              ChangeCharacter cc = (ChangeCharacter) cmd;
              Set<Character> suspects = GameManager.getCharacters();
              Character ch = null;
              for(Character ch2 : suspects)
                 if(ch2.getName().equals(cc.identity))
                    ch = ch2;
              if(ch == null) ch = new Character("<Random>");
              GameManager.getInstance().getGame(cc.gameId)
                      .getPlayer(myoutbound).setCharacter(ch);
              cc.identity = GameManager.getInstance().getGame(cc.gameId)
                      .getPlayer(myoutbound).getCharacter().getName();
              GameUpdate gu = new GameUpdate(GameManager.getInstance().getGame(cc.gameId), false);
              GameManager.getInstance().getGame(cc.gameId).alertAllPlayers(gu);
              cmd = cc;
           }
           else if(cmd instanceof GetAvailableLanguages){
              GetAvailableLanguages gal = (GetAvailableLanguages) cmd;
              gal.languages = NotificationManager.getInstance().getSupportedLanguages();
              cmd = gal;
           }
           else if(cmd instanceof ChangeLanguage){
              ChangeLanguage cl = (ChangeLanguage) cmd;
              GameManager.getInstance().getGame(cl.gameId).getPlayer(myoutbound)
                      .setSelectedLanguage(cl.language);
              cl.language = GameManager.getInstance().getGame(cl.gameId)
                      .getPlayer(myoutbound).getSelectedLanguage();
              cmd = cl;
           }
           else if(cmd instanceof StartGame){
              StartGame sg = (StartGame) cmd;
              sg.started = GameManager.getInstance().getGame(sg.gameId).start();
              if(sg.started){
                  GameUpdate gu = new GameUpdate(GameManager.getInstance().getGame(sg.gameId), true);
                  GameManager.getInstance().getGame(sg.gameId).alertAllPlayers(gu);
              }
           }
           else if(cmd instanceof PlayerChat){
              PlayerChat pc = (PlayerChat) cmd;
              List args = new ArrayList();
              args.add(GameManager.getInstance().getGame(pc.gameId)
                      .getPlayer(pc.playerId).getCharacter());
              args.add(pc.msg);
              GameManager.getInstance().getGame(pc.gameId)
                      .notifyAllPlayers(NotificationEnum.PlayerChat, args);
           }
           else if(cmd instanceof PlayerQuit){
              PlayerQuit pq = (PlayerQuit) cmd;
              pq.quit = GameManager.getInstance().leaveGame(pq.gameId, pq.playerId);
              GameUpdate gu = new GameUpdate(GameManager.getInstance().getGame(pq.gameId), false);
              GameManager.getInstance().getGame(pq.gameId).alertAllPlayers(gu);
              
              if(GameManager.getInstance().getGame(pq.gameId).getPlayerCount() == 0)
                 GameManager.getInstance().deleteGame(pq.gameId);
              cmd = pq;
           }
           else if(cmd instanceof KeepAlive){
              KeepAlive ka = (KeepAlive) cmd;
              ScheduledFuture sf = CluelessServlet.removals.get(ka.playerId);
              if(sf != null)
                  sf.cancel(true);
              CluelessServlet.removals.remove(ka.playerId);
              GameManager.getInstance().getGame(ka.gameId).getPlayer(ka.playerId).setSocket(myoutbound);
           }
           
           //Return result
           String retVal = gson.toJson(cmd, c);
           if(!(cmd instanceof QueryGames))
            System.out.println("Return JSON: " + retVal);
           myoutbound.writeTextMessage(CharBuffer.wrap(retVal));
        }

        @Override
        public void onBinaryMessage(ByteBuffer bb) throws IOException{
           throw new UnsupportedOperationException("onBinaryMessage");
        }
    }
}