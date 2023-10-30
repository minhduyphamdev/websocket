package core.client;

import org.glassfish.tyrus.client.ClientManager;

import javax.websocket.DeploymentException;
import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class Client {
    public static final String server = "ws://localhost:8887/ws/chat/";

    public static void main(String[] args) {
    ClientManager client = ClientManager.createClient();
    String message;
    Scanner scanner = new Scanner(System.in);
    System.out.println("Welcome to Tiny Chat!");
    System.out.println("What's your name?");
    String user = scanner.nextLine();
    try {
        Session session = client.connectToServer(ChatClientPoint.class, new URI(server));
        System.out.println("You are logged in as: " + user);

        // repeatedly read a message and send it to the server (until quit)
        do {
            message = scanner.nextLine();
            session.getBasicRemote().sendObject(message);
        } while (!message.equalsIgnoreCase("quit"));
    } catch (DeploymentException | URISyntaxException | EncodeException | IOException e) {
        throw new RuntimeException(e);
    }



    }
}
