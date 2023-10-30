package core.server;

import org.glassfish.tyrus.server.Server;

import javax.websocket.DeploymentException;
import javax.websocket.server.ServerEndpoint;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class WebSocketServer {

    public static void main(String[] args) {
    Server server = new Server("localhost",8887,"/ws", ServerEndpoint.class);
    Scanner scanner = new Scanner(System.in);
    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    try {
        server.start();
        System.out.println("Press any key to stop the server...");
        scheduledExecutorService.scheduleAtFixedRate(()-> {
            System.out.println("Running");
        },2000,5000, TimeUnit.MILLISECONDS);
        scanner.nextLine();
    } catch (DeploymentException e) {
        throw new RuntimeException(e);
    } finally {
        server.stop();
    }


    }

}