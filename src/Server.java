import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends NetworkGame {
    Socket socket;

    public Server(NetworkPlayer opponent, Game game) {
        super(game);
        this.opponent = opponent;
    }

    @Override
    public void start() {
        super.start();
        try {
            System.out.println("Server started at port: "+game.port);
            socket = new ServerSocket(game.port).accept();
            game.menu.setVisibility(false);
            receiveMessages(socket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
