import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends NetworkGame {
    InetAddress host = InetAddress.getLocalHost();// InetAddress.getByName("192.168.1.82"); // 192.168.1.82
    int port = 8500;

    Client(NetworkPlayer opponent, Game game) throws UnknownHostException {
        super(game);
        this.opponent = opponent;
        try {
            Socket s = new Socket(host, port);
            System.out.println("Connection successful");
            receiveMessages(s);
        } catch (IOException e) {
            System.out.println("Connection not established");
            throw new RuntimeException(e);
        }
    }
}
