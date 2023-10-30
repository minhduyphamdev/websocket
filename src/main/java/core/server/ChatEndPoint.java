package core.server;

import core.Message;
import core.util.MessageDecoder;
import core.util.MessageEncoder;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/chat", decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class ChatEndPoint {

    private Session session;
    private static Set<ChatEndPoint> chatEndPointSet = new CopyOnWriteArraySet<>();
    private static Map<String,String> users = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        this.session = session;
        chatEndPointSet.add(this);
        users.put(session.getId(),username);

        Message message = new Message();
        message.setFrom(username);
        message.setContent("connected!");
        broadcast(message);

    }

    @OnMessage
    public void onMessage(Session session, Message message) {
        message.setFrom(session.getId());
        broadcast(message);
    }

    @OnClose
    public void onClose(Session session) {
        chatEndPointSet.remove(this);
        Message message = new Message();
        message.setFrom(session.getId());
        message.setContent("Disconnected");
        broadcast(message);
    }

    @OnError
    public void onError(Session session) {
        throw new RuntimeException("Error in connecting");
    }
    private static void broadcast(Message message) {
        chatEndPointSet.forEach((ele) -> {
            synchronized(ele) {
                try {
                    ele.session.getBasicRemote().sendObject(message);
                } catch (IOException | EncodeException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}
