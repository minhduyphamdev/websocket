package core.client;

import core.Message;
import core.util.MessageDecoder;
import core.util.MessageEncoder;
import org.glassfish.tyrus.client.ClientManager;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.text.SimpleDateFormat;

@ClientEndpoint(encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class ChatClientPoint {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
    @OnOpen
    public void onOpen(Session session) {
        System.out.printf("Connection established. session id: %s", session.getId());
    }

    @OnMessage
    public void onMessage(Message message) {
        System.out.printf("[%s:%s] %s", simpleDateFormat.format(message.getFrom()), message.getTo(), message.getContent());
    }
}
