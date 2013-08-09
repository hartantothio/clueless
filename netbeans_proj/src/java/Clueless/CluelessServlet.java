//This sample is how to use websocket of Tomcat.
package Clueless;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
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

    @Override
    public StreamInbound createWebSocketInbound(String protocol, HttpServletRequest hsr){
        return new MyMessageInbound();
    }

    private class MyMessageInbound extends MessageInbound{
        WsOutbound myoutbound;

        @Override
        public void onOpen(WsOutbound outbound){
            try {
                System.out.println("Client connected");
                this.myoutbound = outbound;
                mmiList.add(this);
                outbound.writeTextMessage(CharBuffer.wrap("ACK"));
            } catch (IOException e) {
                Logger.getLogger(CluelessServlet.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        @Override
        public void onClose(int status){
            System.out.println("Client disconnected");
            mmiList.remove(this);
        }

        @Override
        public void onTextMessage(CharBuffer cb) throws IOException{
        }

        @Override
        public void onBinaryMessage(ByteBuffer bb) throws IOException{
           throw new UnsupportedOperationException("onBinaryMessage");
        }
    }
}