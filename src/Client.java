import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

public class Client extends NetworkGame{
    InetAddress host = InetAddress.getByName("192.168.0.192"); //192.168.1.82 //192.168.0.117.
    int port = 8500;
    Client(NetworkPlayer opponent, Game game) throws UnknownHostException {
        super(game);
        this.opponent = opponent;
    }

    @Override
    public void start() {
        super.start();
        System.out.println(host);
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
