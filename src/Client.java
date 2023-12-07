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
        game.menu.setMessage("Connecting...");
        try {
            Socket s = new Socket(InetAddress.getByName(host), port);
            game.menu.setMessage("Connection successful");
            game.menu.setValid(true);
            receiveMessages(s);
            getNetwork().writePlay();
            startGame();

        } catch (IOException e) {
            System.out.println("Connection not established");
            game.menu.setMessage("Connection Refused, try again");
            game.menu.setValid(false);
            game.setHost(true);
            game.init(1, true);
        }
    }
    private void startGame(){
        game.menu.setMessage("Connected as host at: " + host + " with port: " + port);
        game.menu.gameOngoing = true;
        game.menu.newGameButton.setText("Continue");
        game.menu.connectionFrame.setVisible(false);
        game.menu.setVisibility(false);
        game.gui.setVisibility(true);
    }
}
