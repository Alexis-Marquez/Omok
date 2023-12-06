import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends NetworkGame {
    String host;// InetAddress.getByName("192.168.1.82"); // 192.168.1.82
    int port;

    Client(NetworkPlayer opponent, Game game, String host, int port) throws UnknownHostException {
        super(game);
        this.host = host;
        this.port = port;
        this.opponent = opponent;
        try {
            Socket s = new Socket(InetAddress.getByName(host), port);
            System.out.println("Connection successful");
            receiveMessages(s);
            getNetwork().writePlay();
        } catch (IOException e) {
            System.out.println("Connection not established");
            throw new RuntimeException(e);
        }
    }
}
